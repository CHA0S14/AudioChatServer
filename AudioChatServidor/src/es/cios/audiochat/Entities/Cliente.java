package es.cios.audiochat.Entities;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import es.cios.audiochat.exceptions.ClienteException;

public class Cliente {
	private Socket socket;
	private String nombre;
	private InetAddress inetAddress;
	
	public void addSocket(Socket socket) {
		this.socket=socket;
		this.inetAddress = socket.getInetAddress();
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}

	public Socket getSocket() {
		return socket;
	}

	public String getNombre() {
		return nombre;
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void enviarCanales(List<Canal> canales) throws ClienteException{
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(canales);
		} catch (IOException e) {
			throw new ClienteException("Error al enviar los canales");
		}
	}	
}
