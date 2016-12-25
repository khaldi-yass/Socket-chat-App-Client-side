package client;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import message.Message;
import message.Smiley;



public class ClientControl implements ActionListener{

	static Thread runClient;
	static Client client;
	static String path = "C:\\Users\\Yassir";
	
	@Override
	public void actionPerformed(ActionEvent source) {
		
		if (source.getSource() == ClientGUI.connect) {
			
			if(!ClientGUI.isRunning)
			{
				String host = ClientGUI.hostField.getText();
				int port = Integer.parseInt(ClientGUI.portField.getText());
				String pseudo = ClientGUI.pseudoField.getText();
				
				client = new Client(port,host,pseudo);
				
				runClient = new Thread(new Runnable() {	
					@Override
					public void run() {
						client.clientRun();
					}
				});
				runClient.start();
				
				
				ClientGUI.isRunning = true;
				ClientGUI.connect.setText("Deconnect");
				ClientGUI.hostField.setEnabled(false);
				ClientGUI.portField.setEnabled(false);
				ClientGUI.pseudoField.setEnabled(false);
				
				ClientGUI.usernameLabel.setText(pseudo);
				ClientGUI.send.setEnabled(true);
				ClientGUI.smiley.setEnabled(true);
				ClientGUI.messageField.setEnabled(true);
				ClientGUI.record.setEnabled(true);
				ClientGUI.wizz.setEnabled(true);
				ClientGUI.imageChooser.setEnabled(true);
				
			}else
			{
				ClientGUI.isRunning = false;
				ClientGUI.connect.setText("Connect");
				ClientGUI.hostField.setEnabled(true);
				ClientGUI.portField.setEnabled(true);
				ClientGUI.pseudoField.setEnabled(true);
				
				ClientGUI.send.setEnabled(false);
				ClientGUI.smiley.setEnabled(false);
				ClientGUI.messageField.setEnabled(false);
				ClientGUI.record.setEnabled(false);
				ClientGUI.wizz.setEnabled(false);
				ClientGUI.imageChooser.setEnabled(false);
				try {
					client.mySocket.close();
					ClientGUI.write("You disconnected successfuly", "server", "Server");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				client=null;
				runClient=null;
			}
		}
		else if (source.getSource() == ClientGUI.send) {
			
			Message message = new Message(client.pseudo, Message.TEXT_TYPE, ClientGUI.messageField.getText());
			client.sendMessage(message);
		}
		else if (source.getSource() == ClientGUI.smiley) {
			
			Smiley smiley = new Smiley(client);
			Message message = smiley.getMessage();
			if(message != null)	client.sendMessage(message);
		}
		else if (source.getSource() == ClientGUI.record) {
			Message message = new Message(client.pseudo, Message.AUDIO_TYPE, "test");
			client.sendMessage(message);
			
		}
		else if (source.getSource() == ClientGUI.wizz) {
			Message message = new Message(client.pseudo, Message.WIZZ_TYPE, "test");
			client.sendMessage(message);
			
		}
		else if (source.getSource() == ClientGUI.imageChooser) {
			
			//Genere une nouvelle fenetre de choix de fichier
			JFileChooser browse = new JFileChooser();
			browse.setCurrentDirectory(new File(path));
			browse.setFileFilter(new FileNameExtensionFilter("Fichiers d'images", "jpg", "png", "gif", "jpeg", "bmp"));
			int result = browse.showOpenDialog(ClientGUI.fen);
			
			
			//Recuperer le fichier image et l'afficher sur le panel
			if(result == JFileChooser.APPROVE_OPTION)
			{
				File sourceFile = browse.getSelectedFile();
				BufferedImage imgLab=null;
				try {imgLab = ImageIO.read(sourceFile);} catch (IOException e) {e.printStackTrace();}
				ImageIcon newIm = new ImageIcon(imgLab.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				ClientGUI.image.setIcon(newIm);
				path = sourceFile.getAbsolutePath().substring(0, sourceFile.getAbsolutePath().lastIndexOf(File.separator));
			}
			
		}
		
	}

}
