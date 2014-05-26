package es.cios.audiochat.hilos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

import es.cios.audiochat.entities.Cliente;
import es.cios.audiochat.exceptions.ClienteException;
import es.cios.audiochat.servicios.AudioChatService;

public class HiloCliente extends Thread{
	private Socket socket;
	private boolean seguir = true;
	private int canal, subCanal = -1;
	private ObjectInputStream in =null;
	private InetAddress inetAddress;
	
	public HiloCliente(Cliente cliente){
		actualizar(cliente);
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			throw new ClienteException("Error al crear obbject in: "
					+ e.getMessage(), e);
		}
	}	
	
	public void actualizar(Cliente cliente){
		this.socket=cliente.getSocket();
		this.canal=cliente.getCanal();
		this.subCanal = cliente.getSubCanal();
		this.inetAddress = cliente.getInetAddress();
	}

	public void parar() {
		this.seguir = false;
	}	
	
	@Override
	public void run() throws ClienteException{
		try {				
			while (seguir) {					
				AudioChatService.recibirObjeto(in.readObject(), canal, subCanal, inetAddress);
			}
			if(in!=null){
				in.close();
			}
			socket.close();
		} catch (IOException e) {
			throw new ClienteException("Error al crear la conexion: "
					+ e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new ClienteException("Error al crear la conexion: "
					+ e.getMessage(), e);
		}
	}
}
