package es.cios.audiochat.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import es.cios.audiochat.servicios.AudioChatService;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class MainPanel extends JFrame{
	public static void main(String[] args) {
		try{
			AudioChatService.iniciar();
		}catch(Exception e){
			new JOptionPane(e.getMessage());
			//TODO finalizar servidor
			//AudioChatService.finalizar();
		}
	}
}