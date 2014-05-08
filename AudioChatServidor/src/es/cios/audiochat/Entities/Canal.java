package es.cios.audiochat.Entities;

import java.util.List;
import java.util.ArrayList;

public class Canal {
	private List<SubCanal> subCanales = new ArrayList<SubCanal>();
	private List<Cliente> clientes = new ArrayList<Cliente>();
	
	public void addCliente(Cliente cliente){
		clientes.add(cliente);
	}
	
	public void addSubCanal(SubCanal subCanal){
		subCanales.add(subCanal);
	}
	
	public int getNumClient(){		
		return clientes.size();
	}
	
}
