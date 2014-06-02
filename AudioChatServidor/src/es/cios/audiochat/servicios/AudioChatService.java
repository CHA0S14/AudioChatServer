package es.cios.audiochat.servicios;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import es.cios.audiochat.entities.Canal;
import es.cios.audiochat.entities.CanalMod;
import es.cios.audiochat.entities.Cliente;
import es.cios.audiochat.entities.Finalizar;
import es.cios.audiochat.entities.MensajeAudio;
import es.cios.audiochat.entities.Nombre;
import es.cios.audiochat.entities.SubCanal;
import es.cios.audiochat.hilos.Conexion;
import es.cios.audiochat.hilos.HiloCliente;

/**
 * Clase que se encarga de ser el pto intermedio entre la conexion y la logica del servidor
 * @author Ismael Ortega
 *
 */
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

	/**
	 * Añade un cliente al main cuando se conecta
	 * @param socket el socket del cliente
	 */
	public static void addCliente(Socket socket) {
		Cliente cliente = new Cliente();
		cliente.setNombre("nuevo cliente");
		cliente.setSocket(socket);
		cliente.setCanal(0);
		cliente.enviarObjeto(canales);
		HiloCliente hilo = new HiloCliente(cliente);
		hilo.start();
		mainChanel.addCliente(cliente);
	}

	/**
	 * escribe el mensaje para el cliente dependiendo que que quiera hacer sera una clase distinta
	 * @param obj objeto a enviar
	 * @param canalNum canal al cual se va a enviar el mensaje
	 * @param subCanalNum si es distinto de -1 entonces se envia aqui en vez de al canal
	 */
	public static void escribirMensaje(Object obj, int canalNum,
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
			cliente.enviarObjeto(obj);
		}

	}

	/**
	 * le coloca un nombre al cliente
	 * @param nombre nombre a poner
	 * @param socketAddress ip del cliente utilizado como id de diferenciacion
	 * @param canalNum canal en el que se encuentra
	 * @param subCanalNum sub canal en el que se encuentra si no esta en ninguno es -1
	 */
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

	/**
	 * se encarga de decidir que haccion hacer dependiendo del objeto que ha enviado el cliente
	 * @param object objeto que ha enviado
	 * @param canal canal en el que esta
	 * @param subCanal sub canal en el que esta si no esta en ninguno es -1
	 * @param socketAddress ip del cliente utilizado como id de diferenciacion
	 */
	public static void recibirObjeto(Object object, int canal, int subCanal,
			SocketAddress socketAddress) {
		if (object instanceof String || object instanceof MensajeAudio) {
			escribirMensaje(object, canal, subCanal);
		} else if (object instanceof Nombre) {
			addNombre((Nombre) object, socketAddress, canal, subCanal);
		}else if (object instanceof Finalizar){
			borrarCliente(canal,subCanal,socketAddress);
		}else if(object instanceof Cliente){	
			moverCliente(canal,subCanal, socketAddress, (Cliente) object);
		}else if (object instanceof CanalMod){
			CanalMod canalMod = (CanalMod) object;
			if(canalMod.isNuevo()){
				cambiarNombreCanal(canalMod);
			}else{
				crearCanal(canalMod);
			}
		}
	}
	
	/**
	 * mueve un cliente de un canal a otro y ademas si el canal esta vacio despues de irse el cliente este se elimina
	 * @param canalNum canal en el que esta
	 * @param subCanalNum subCanal en el que esta si no esta en ninguno es -1
	 * @param socketAddress ip del cliente utilizado como id de diferenciacion
	 * @param clienteMov objeto con el canal y el subcanal al que se quiere mover el cliente
	 */
	private static void moverCliente(int canalNum, int subCanalNum,
			SocketAddress socketAddress, Cliente clienteMov) {
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
					clientes.remove(cliente);	
					Canal canal2 = canal;					
					canal = canales.get(clienteMov.getCanal());
					if (clienteMov.getSubCanal() != -1) {
						canal = canal.getSubCanal(clienteMov.getSubCanal());
					}					
					cliente.setCanal(clienteMov.getCanal());
					cliente.setSubCanal(clienteMov.getSubCanal());
					canal.getClientes().add(cliente);
					eliminarCanalMover(cliente, canal2, canalNum);
					buscar=false;
				}
				cnum++;
			} catch (IndexOutOfBoundsException e) {
				buscar=false;
			}
		}
		actualizar();
	}

	/**
	 * elimina un canal si no tiene clientes ni subcanales con clientes
	 * @param cliente cliente que se ha movido
	 * @param canal canal que se va a comprobar
	 * @param subCanalNum numero del subCanal que se  va a comprobar
	 */
	private static void eliminarCanalMover(Cliente cliente, Canal canal, int subCanalNum) {
		if(canal.getNumClient()==0 && canal.getNumSubCanal()==0){
			if(canal instanceof SubCanal){
				canales.get(subCanalNum).getSubCanales().remove(canal);
				eliminarCanalMover(cliente, canales.get(subCanalNum), subCanalNum);
			}else if (canal instanceof Canal){
				if(!canal.getName().equals("C: Main chanel"))
					canales.remove(canal);
			}
		}
	}

	/**
	 * crea un canal o un subcanal dependiendo de la variable canal del objeto canalMod
	 * @param canalMod objeto con el canal en el cual se quiere crear el subcanal si es un subcanal lo que quieres crear, ademas tiene el nombre del canal en cuestion
	 */
	private static void crearCanal(CanalMod canalMod) {
		Canal canal;
		if (canalMod.getCanal()>-1){
			List<SubCanal> canales = AudioChatService.canales.get(canalMod.getCanal()).getSubCanales();
			SubCanal subCanal = new SubCanal();
			subCanal.setName(canalMod.getNombre());
			canales.add(subCanal);
			canal = subCanal;
		}else{
			Canal canal2 = new Canal();
			canal2.setName(canalMod.getNombre());
			AudioChatService.canales.add(canal2);
			canal = canal2;			
		}
		//TODO cambiar el cliente del canal desde el que creo esto a este canal
		actualizar();
	}

	/**
	 * cambia el nombre de un canal
	 * @param canalMod contiene elnumero del canal y el numero del subcanal si tubiese (si no es -1), ademas del nombre del canal
	 */
	private static void cambiarNombreCanal(CanalMod canalMod) {
		Canal canal = canales.get(canalMod.getCanal());
		if(canalMod.getSubCanal()>-1){
			canal = canal.getSubCanal(canalMod.getSubCanal());
		}
		canal.setName(canalMod.getNombre());
		actualizar();
	}

	/**
	 * borra un cliente de un canal
	 * @param canalNum numero del canal en el que se situa el cliente
	 * @param subCanalNum numero del subCanal en el que se situa el cliente si no esta en uno es -1
	 * @param socketAddress ip del cliente utilizado como id de diferenciacion
	 */
	private static void borrarCliente(int canalNum, int subCanalNum,
			SocketAddress socketAddress) {
		Cliente cliente = null;
		Canal canal = canales.get(canalNum);
		if (subCanalNum != -1) {
			canal = canal.getSubCanal(subCanalNum);
		}
		List<Cliente> clientes = canal.getClientes();
		boolean buscar = true;
		int cnum = 0;
		while (buscar) {
			try {
				cliente = clientes.get(cnum);
				if (cliente.getSocketAddress().equals(socketAddress)) {
					clientes.remove(cliente);
					buscar=false;
				}
				cnum++;
			} catch (IndexOutOfBoundsException e) {
				buscar=false;
			}
		}
		actualizar();		
	}

	/**
	 * metodo que se encarga de actualizar la lista de clientes, canales y subcanales de todos los clientes
	 */
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
