package es.cios.audiochat.servicios;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import es.cios.audiochat.entities.Canal;
import es.cios.audiochat.entities.Cliente;
import es.cios.audiochat.entities.SubCanal;
import es.cios.audiochat.hilos.Conexion;

public class AudioChatService {
	private static List<Canal> canales = new ArrayList<Canal>();
	private static Canal mainChanel;
	private static Conexion conn;
	
	public static void iniciar(){
		mainChanel = new Canal();
		canales.add(mainChanel);
		conn = new Conexion();
		conn.start();
	}

	public static void addCliente(Socket socket) {
		Cliente cliente = new Cliente();
		cliente.addSocket(socket);
		cliente.setCanal(0);
		mainChanel.addCliente(cliente);		
		cliente.enviarCanales(canales);
		cliente.start();
	}

	public static void escribirMensaje(String texto, int canalNum, int subCanalNum) {
		Canal canal = canales.get(canalNum);
		List<Cliente> clientes;
		
		if (subCanalNum != -1){
			clientes = canal.getClientes();
		}else{
			SubCanal subCanal = canal.getSubCanal(subCanalNum);
			clientes = subCanal.getClientes();					
		}
		
		for (Cliente cliente : clientes) {
			cliente.escribir(texto);
		}
		
	}

}
