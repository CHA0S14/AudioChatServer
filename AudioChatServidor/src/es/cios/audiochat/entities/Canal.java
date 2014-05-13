package es.cios.audiochat.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Canal implements Serializable{
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
	
	public SubCanal getSubCanal(int subCanalNum){
		return this.subCanales.get(subCanalNum);
	}
	
	public List<Cliente> getClientes(){
		return this.clientes;
	}
	
}
