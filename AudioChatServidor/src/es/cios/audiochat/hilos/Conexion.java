package es.cios.audiochat.hilos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import es.cios.audiochat.exceptions.ConexionException;
import es.cios.audiochat.servicios.AudioChatService;

public class Conexion extends Thread {
	private ServerSocket server;
	private Socket socket;
	private boolean seguir = true;

	public Conexion() {
		try {
			server = new ServerSocket(9999);
		} catch (IOException e) {
			throw new ConexionException("Error al crear el socket: "
					+ e.getMessage(), e);
		}
	}

	public void pararHilo() {
		this.seguir = false;
	}

	@Override
	public void run() {
		try {
			while (seguir) {
				socket = server.accept();
				AudioChatService.addCliente(socket);
			}
			server.close();
		} catch (IOException e) {
			throw new ConexionException("Error al crear la conexion: "
					+ e.getMessage(), e);
		}
	}
}
