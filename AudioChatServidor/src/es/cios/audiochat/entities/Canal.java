package es.cios.audiochat.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class Canal implements Serializable{
	protected String name;
	private List<SubCanal> subCanales = new ArrayList<SubCanal>();
	private List<Cliente> clientes = new ArrayList<Cliente>();
	
	/**
	 * 
	 * @param cliente cliente a añadir
	 */
	public void addCliente(Cliente cliente){
		clientes.add(cliente);
	}
	
	/**
	 * 
	 * @param subCanal subcanal a añadir
	 */
	public void addSubCanal(SubCanal subCanal){
		subCanales.add(subCanal);
	}
	
	/**
	 * 
	 * @return numero de clientes del canal
	 */
	public int getNumClient(){		
		return clientes.size();
	}
	
	/**
	 * numero del subcanal a obtener
	 * @param subCanalNum
	 * @return
	 */
	public SubCanal getSubCanal(int subCanalNum){
		return this.subCanales.get(subCanalNum);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the subCanales
	 */
	public List<SubCanal> getSubCanales() {
		return subCanales;
	}

	/**
	 * @return the clientes
	 */
	public List<Cliente> getClientes() {
		return clientes;
	}

	/**
	 * 
	 * @return numero de subcanales del canal
	 */
	public int getNumSubCanal() {
		return subCanales.size();
	}	
}
