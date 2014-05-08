package es.cios.audiochat.servicios;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import es.cios.audiochat.Entities.Canal;
import es.cios.audiochat.Entities.Cliente;
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
		mainChanel.addCliente(cliente);		
		cliente.enviarCanales(canales);
	}

}
