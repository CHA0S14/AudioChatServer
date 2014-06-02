package es.cios.audiochat.gui;

import javax.swing.JFrame;

import es.cios.audiochat.servicios.AudioChatService;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class MainPanel extends JFrame{
	public static void main(String[] args) {
		AudioChatService.iniciar();
	}
}