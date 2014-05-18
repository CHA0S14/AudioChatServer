package es.cios.audiochat.entities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import es.cios.audiochat.exceptions.ClienteException;

@SuppressWarnings("serial")
public class Cliente implements Serializable{
	private int canal, subCanal = -1;
	private transient Socket socket;
	private String nombre;
	private InetAddress inetAddress;

	public void addSocket(Socket socket) {
		this.socket = socket;
		this.inetAddress = socket.getInetAddress();
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

	public void enviarCanales(List<Canal> canales) throws ClienteException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(canales);
			out.flush();
		} catch (IOException e) {
			throw new ClienteException("Error al enviar los canales: "
					+ e.getMessage(), e);
		}
	}

	public void escribir(String texto) throws ClienteException{
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			out.write(texto);
			out.newLine();
		} catch (IOException e) {
			throw new ClienteException("Error al enviar el mensaje: "
					+ e.getMessage(), e);
		} 
	}
}
