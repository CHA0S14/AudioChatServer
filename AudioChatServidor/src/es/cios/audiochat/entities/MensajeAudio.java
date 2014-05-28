package es.cios.audiochat.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MensajeAudio implements Serializable{
	private String user;
	private byte[] audioFile;
	
	
	public MensajeAudio(String user, byte[] audioFile) {
		this.user = user;
		this.audioFile = audioFile;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public byte[] getAudioFile() {
		return audioFile;
	}
	public void setAudioFile(byte[] audioFile) {
		this.audioFile = audioFile;
	}	
}
