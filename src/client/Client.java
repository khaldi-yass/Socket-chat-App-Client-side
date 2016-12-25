package client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.text.BadLocationException;

import message.Message;
import client.ClientGUI;

public class Client {

	int myId;
	public Socket mySocket;
	ObjectOutputStream output;
	ObjectInputStream input;
	public int port;
	public String host;
	boolean connected=true;
	public boolean isRecording=false;
	
	public String pseudo;
	Message serverMessage;
	
	public Client(int po, String ho, String pse) {
		port=po;
		host=ho;
		pseudo=pse;
	}

	public void clientRun()
	{

		try {
			mySocket = new Socket(host, port);
			
			output = new ObjectOutputStream(mySocket.getOutputStream());						//Used to send object to server
			output.flush(); 																	//flushing the stream
			input = new ObjectInputStream(mySocket.getInputStream());	
			
			//receiving id from server
			myId = (Integer) input.readObject();
			
			//sending pseudo
			output.writeObject(pseudo);	
			output.flush();
			
			//recieving success message
			serverMessage = (Message) input.readObject();
			ClientGUI.write((String)serverMessage.getCont(), "server", "Server");
			
			do
			{
				serverMessage = (Message) input.readObject();
				processMessage(serverMessage);
				
			}while(connected);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		} finally {
			try {
				mySocket.close();
				output.close();
				input.close();
				ClientGUI.write("You disconnected successfully", "server", "Server");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	void sendMessage(Message msg)
	{
		try
		{
			output.writeObject(msg);													//write message to server
	        output.flush();
	        int type = msg.getType();
	        if(type == Message.TEXT_TYPE) ClientGUI.write((String)msg.getCont(), "self","You");
	        else if( type == Message.IMAGE_TYPE)
	        {
	        	String smiley = msg.show();
				ClientGUI.writeImage(smiley, "self", "You");
	        }
	        else if( type == Message.AUDIO_TYPE) ClientGUI.write("Vous avez envoye un audio", "self","You");
	        else if( type == Message.WIZZ_TYPE) ClientGUI.write("Vous avez envoye un wizz", "self","You");  
	    }
	    catch(IOException ioException){
	        ioException.printStackTrace();
	    } catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	
	void processMessage(Message msg) throws BadLocationException
	{
		String sourcePseudo = msg.getSourceClientPseudo();
		int type = msg.getType();
		
		if(type == Message.TEXT_TYPE)
		{
			ClientGUI.write(msg.show(), "other", sourcePseudo);
		}
		else if(type == Message.AUDIO_TYPE)
		{
			ClientGUI.write(msg.show(), "other", sourcePseudo);
		}
		else if(type == Message.IMAGE_TYPE)
		{
			String smiley = msg.show();
			ClientGUI.writeImage(smiley, "other", sourcePseudo);
		}
		else if(type == Message.WIZZ_TYPE)
		{
			ClientGUI.write(msg.show(), "other", sourcePseudo);
			ClientGUI.wizz();
		}
	}
	
}
