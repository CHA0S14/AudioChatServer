package es.cios.audiochat.entities;

import java.io.Serializable;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class MensajeAudio implements Serializable{
	private String user;
	private byte[] audioFile;
	
	/**
	 * @param user
	 * @param audioFile
	 */
	public MensajeAudio(String user, byte[] audioFile) {
		super();
		this.user = user;
		this.audioFile = audioFile;
	}
	
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * @return the audioFile
	 */
	public byte[] getAudioFile() {
		return audioFile;
	}
	
	/**
	 * @param audioFile the audioFile to set
	 */
	public void setAudioFile(byte[] audioFile) {
		this.audioFile = audioFile;
	}		
}
