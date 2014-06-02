package es.cios.audiochat.entities;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketAddress;

import es.cios.audiochat.exceptions.ClienteException;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class Cliente implements Serializable{
	private int canal, subCanal = -1;
	private transient Socket socket;
	private String nombre;
	private SocketAddress socketAddress;
	private transient ObjectOutputStream out = null;

	/**
	 * da valor al socket y al socketAddress ademas crea un objectOutputStream
	 * @param socket socket a poner en el cliente
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
		this.socketAddress = socket.getRemoteSocketAddress();
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			throw new ClienteException("Error al crear el object out: "
					+ e.getMessage(), e);
		}
	}		

	/**
	 * @return the canal
	 */
	public int getCanal() {
		return canal;
	}	

	/**
	 * @param canal the canal to set
	 */
	public void setCanal(int canal) {
		this.canal = canal;
	}

	/**
	 * @return the subCanal
	 */
	public int getSubCanal() {
		return subCanal;
	}

	/**
	 * @param subCanal the subCanal to set
	 */
	public void setSubCanal(int subCanal) {
		this.subCanal = subCanal;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the socketAddress
	 */
	public SocketAddress getSocketAddress() {
		return socketAddress;
	}

	/**
	 * se encarga de enviar un objeto al cliente en cuestion
	 * @param object objeto a enviar
	 * @throws ClienteException error de cliente
	 */
	public void enviarObjeto(Object object) throws ClienteException {		
		try {	
			out.reset();
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			throw new ClienteException("Error al enviar los canales: "
					+ e.getMessage(), e);
		}
	}
}
