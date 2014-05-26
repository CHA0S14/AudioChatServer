package es.cios.audiochat.servicios;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import es.cios.audiochat.entities.Canal;
import es.cios.audiochat.entities.Cliente;
import es.cios.audiochat.entities.Nombre;
import es.cios.audiochat.entities.SubCanal;
import es.cios.audiochat.hilos.Conexion;
import es.cios.audiochat.hilos.HiloCliente;

public class AudioChatService {
	private static List<Canal> canales = new ArrayList<Canal>();
	private static Canal mainChanel;
	private static Conexion conn;

	public static void iniciar() {
		mainChanel = new Canal();
		mainChanel.setName("Main chanel");
		canales.add(mainChanel);
		conn = new Conexion();
		conn.start();
	}

	public static void addCliente(Socket socket) {
		Cliente cliente = new Cliente();
		cliente.setNombre("nuevo cliente");
		cliente.addSocket(socket);
		cliente.setCanal(0);
		mainChanel.addCliente(cliente);		
		cliente.enviarObjeto(canales);
		HiloCliente hilo = new HiloCliente(cliente);
		hilo.start();
	}

	public static void escribirMensaje(String texto, int canalNum,
			int subCanalNum) {
		Canal canal = canales.get(canalNum);
		List<Cliente> clientes;

		if (subCanalNum == -1) {
			clientes = canal.getClientes();
		} else {
			SubCanal subCanal = canal.getSubCanal(subCanalNum);
			clientes = subCanal.getClientes();
		}

		for (Cliente cliente : clientes) {
			cliente.enviarObjeto(texto);
		}

	}

	private static void addNombre(Nombre nombre, SocketAddress socketAddress,
			int canalNum, int subCanalNum) {
		Canal canal = canales.get(canalNum);
		if (subCanalNum != -1) {
			canal = canal.getSubCanal(subCanalNum);
		}
		List<Cliente> clientes = canal.getClientes();
		boolean buscar = true;
		int cnum = 0;
		while (buscar) {
			try {
				Cliente cliente = clientes.get(cnum);
				if (cliente.getSocketAddress().equals(socketAddress)) {
					cliente.setNombre(nombre.getNombre());
					buscar=false;
				}
				cnum++;
			} catch (IndexOutOfBoundsException e) {
				buscar=false;
			}
		}
		actualizar();
	}

	public static void recibirObjeto(Object object, int canal, int subCanal,
			SocketAddress socketAddress) {
		if (object instanceof String) {
			escribirMensaje((String) object, canal, subCanal);
		} else if (object instanceof Nombre) {
			addNombre((Nombre) object, socketAddress, canal, subCanal);
		}
	}
	
	public static void actualizar(){
		for (Canal canal : canales) {
			List<Cliente> clientes = canal.getClientes();
			for (Cliente cliente : clientes) {
				cliente.enviarObjeto(canales);
			}
			List<SubCanal> subcanales = canal.getSubCanales();
			for (SubCanal subCanal : subcanales) {
				clientes = subCanal.getClientes();
				for (Cliente cliente : clientes) {
					cliente.enviarObjeto(canales);
				}
			}
		}
	}

}
