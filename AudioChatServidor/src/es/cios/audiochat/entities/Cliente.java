package es.cios.audiochat.entities;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

import es.cios.audiochat.exceptions.ClienteException;

@SuppressWarnings("serial")
public class Cliente implements Serializable{
	private int canal, subCanal = -1;
	private transient Socket socket;
	private String nombre;
	private InetAddress inetAddress;
	private transient ObjectOutputStream out = null;

	public void addSocket(Socket socket) {
		this.socket = socket;
		this.inetAddress = socket.getInetAddress();
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			throw new ClienteException("Error al crear el object out: "
					+ e.getMessage(), e);
		}
	}	

	public int getCanal() {
		return canal;
	}

	public void setCanal(int canal) {
		this.canal = canal;
	}

	public int getSubCanal() {
		return subCanal;
	}

	public void setSubCanal(int subCanal) {
		this.subCanal = subCanal;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	public void enviarObjeto(Object object) throws ClienteException {		
		try {			
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			throw new ClienteException("Error al enviar los canales: "
					+ e.getMessage(), e);
		}
	}
}
