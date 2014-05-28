package es.cios.audiochat.gui;

import javax.swing.JFrame;

import es.cios.audiochat.servicios.AudioChatService;

@SuppressWarnings("serial")
public class MainPanel extends JFrame{
	public static void main(String[] args) {
		AudioChatService.iniciar();
	}
}