package es.cios.audiochat.entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import es.cios.audiochat.exceptions.ClienteException;
import es.cios.audiochat.servicios.AudioChatService;

@SuppressWarnings("serial")
public class Cliente extends Thread implements Serializable{
	private int canal, subCanal = -1;
	private transient Socket socket;
	private String nombre;
	private InetAddress inetAddress;
	private transient boolean seguir = true;

	public void addSocket(Socket socket) {
		this.socket = socket;
		this.inetAddress = socket.getInetAddress();
	}

	public void setCanal(int canal) {
		this.canal = canal;
	}

	public void setSubCanal(int subCanal) {
		this.subCanal = subCanal;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public void enviarCanales(List<Canal> canales) throws ClienteException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(canales);
		} catch (IOException e) {
			throw new ClienteException("Error al enviar los canales: "
					+ e.getMessage(), e);
		} finally {
			try {
				if (out != null) {
					out.flush();
				}
			} catch (IOException e) {
				throw new ClienteException("Error al enviar los canales: "
						+ e.getMessage(), e);
			}
		}
	}

	public void parar() {
		this.seguir = false;
	}

	@Override
	public void run() throws ClienteException{
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			;
			while (seguir) {
				AudioChatService
						.escribirMensaje(in.readLine(), canal, subCanal);
			}
			in.close();
			socket.close();
		} catch (IOException e) {
			throw new ClienteException("Error al crear la conexion: "
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
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				throw new ClienteException("Error al enviar el mensaje: "
						+ e.getMessage(), e);
			}
		}
	}
}
