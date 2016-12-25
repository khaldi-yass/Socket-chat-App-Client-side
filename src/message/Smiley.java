package message;

import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import client.Client;
import client.ClientGUI;

public class Smiley implements ActionListener{

	JDialog smileyPopUp;
	JButton l0;
	JButton l1;
	JButton l2;
	JButton l3;
	JButton l4;
	Client client;
	Message msg = null;
	
	
	public Smiley(Client cl){
		
		client = cl;
		smileyPopUp= new JDialog(ClientGUI.fen,"Choisir un smiley",ModalityType.APPLICATION_MODAL);
		//smileyPopUp.setPreferredSize(new Dimension(200,200));
		smileyPopUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		smileyPopUp.setResizable(false);
		smileyPopUp.setLocationRelativeTo(null);
		smileyPopUp.setLayout(new FlowLayout());
		
		JButton[] smileys = new JButton[5];
		ImageIcon i0 = new ImageIcon("images/smileys/0.png");
		l0 = new JButton(i0);
        smileys[0] = l0;
		ImageIcon i1 = new ImageIcon("images/smileys/1.png");
		l1 = new JButton(i1);
        smileys[1] = l1;
        ImageIcon i2 = new ImageIcon("images/smileys/2.png");
        l2 = new JButton(i2);
        smileys[2] = l2;
        ImageIcon i3 = new ImageIcon("images/smileys/3.png");
        l3 = new JButton(i3);
        smileys[3] = l3;
        ImageIcon i4 = new ImageIcon("images/smileys/4.png");
        l4 = new JButton(i4);
        smileys[4] = l4;
        
        for(int i=0; i<smileys.length; i++)
        {
        	smileyPopUp.getContentPane().add(smileys[i]);
        	smileys[i].addActionListener(this);
        }

        
        smileyPopUp.pack();
		smileyPopUp.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == l0)
		{
			msg = new Message(client.pseudo, Message.IMAGE_TYPE, "images/smileys/0.png");
			smileyPopUp.dispose();
		}
		else if(e.getSource() == l1)
		{
			msg = new Message(client.pseudo, Message.IMAGE_TYPE, "images/smileys/1.png");
			smileyPopUp.dispose();
		}
		else if(e.getSource() == l2)
		{
			msg = new Message(client.pseudo, Message.IMAGE_TYPE, "images/smileys/2.png");
			smileyPopUp.dispose();
		}
		else if(e.getSource() == l3)
		{
			msg = new Message(client.pseudo, Message.IMAGE_TYPE, "images/smileys/3.png");
			smileyPopUp.dispose();
		}
		else if(e.getSource() == l4)
		{
			msg = new Message(client.pseudo, Message.IMAGE_TYPE, "images/smileys/4.png");
			smileyPopUp.dispose();
		}
	}
	
	public Message getMessage()
	{
		return msg;
	}
}
