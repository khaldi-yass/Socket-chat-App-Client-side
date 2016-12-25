package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ClientGUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ClientGUI fen;
	public static JTextPane chatArea;
	public static StyledDocument chatDoc;
	public static SimpleAttributeSet style;
	public static ImageIcon sendIcon;
	public static ImageIcon recordIcon;
	public static ImageIcon wizzIcon;
	public static ImageIcon smileyIcon;
	public static JButton send;
	public static JButton record;
	public static JButton wizz;
	public static JButton smiley;
	public static JButton connect;
	public static JButton imageChooser;
	public static JLabel usernameLabel;
	public static JTextField hostField;
	public static JTextField portField;
	public static JTextField pseudoField;
	public static JTextField messageField;
	public static JLabel image;
	public static ImageIcon imageIcon;
	public static boolean isRunning=false;
	
	
	public ClientGUI() {
		setTitle("Client");
		setSize(800,450);
		setMinimumSize(new Dimension(800,450));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	public static void main(String[] args){
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {}
		
		fen = new ClientGUI();
		JPanel container = new JPanel();
		container.setLayout(null);
		fen.setContentPane(container);		
		
		BufferedImage imgLab=null;
		try {imgLab = ImageIO.read(new File("images/user.png"));} catch (IOException e) {e.printStackTrace();}
		imageIcon = new ImageIcon(imgLab.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		
		ScrollPane sp = new ScrollPane();
		chatArea = new JTextPane();
		chatArea.setEditable(false);
		chatArea.setContentType("text/html");
		chatDoc = chatArea.getStyledDocument();
		style = new SimpleAttributeSet();
		messageField = new JTextField();
		image = new JLabel(imageIcon);
		usernameLabel = new JLabel("Username");
		JPanel connectionPanel = new JPanel();
		JLabel hostLabel = new JLabel("Host:");
		JLabel portLabel = new JLabel("Port:");
		JLabel pseudoLabel = new JLabel("Pseudo:");
		hostField = new JTextField("localhost");
		portField = new JTextField("1500");
		pseudoField = new JTextField("Anonymous");
		sendIcon = new ImageIcon("images/components/send.png");
		recordIcon = new ImageIcon("images/components/record.png");
		wizzIcon = new ImageIcon("images/components/wizz.png");
		smileyIcon = new ImageIcon("images/components/smiley.png");
		connect = new JButton("Connect");
		send = new JButton(sendIcon);
		record = new JButton(recordIcon);
		wizz = new JButton(wizzIcon);
		smiley = new JButton(smileyIcon);
		imageChooser = new JButton("Image");
		
		send.setEnabled(false);
		messageField.setEnabled(false);
		record.setEnabled(false);
		wizz.setEnabled(false);
		smiley.setEnabled(false);
		imageChooser.setEnabled(false);
		

		SwingUtilities.getRootPane(fen).setDefaultButton(connect);
		messageField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				SwingUtilities.getRootPane(fen).setDefaultButton(send);
			}
			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);
				SwingUtilities.getRootPane(fen).setDefaultButton(connect);
			}
		});
		
		//----------------------------------------------------------
		sp.setBounds(20, 20, 490, 285);
		chatArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		messageField.setBounds(20, 340, 490, 25);
		send.setBounds(20, 370, 40, 40);
		smiley.setBounds(65, 370, 40, 40);
		record.setBounds(383, 370, 40, 40);
		wizz.setBounds(434, 370, 40, 40);
		image.setBounds(600, 20, 100, 100);
		usernameLabel.setBounds(625, 130, 60, 20);
		imageChooser.setBounds(710, 95, 75, 25);
		connectionPanel.setLayout(null);
		connectionPanel.setBounds(550, 190, 220, 210);
		connectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Connection Area"));
		hostLabel.setBounds(20, 42, 50, 15);
		portLabel.setBounds(20, 78, 50, 15);
		pseudoLabel.setBounds(20, 111, 50, 15);
		hostField.setBounds(100, 42, 100, 20);
		portField.setBounds(100, 78, 100, 20);
		pseudoField.setBounds(100, 111, 100, 20);
		connect.setBounds(100, 155, 100, 25);
		
		//----------------------------------------------------------
		sp.add(chatArea);
		container.add(sp);
		container.add(messageField);
		container.add(send);
		container.add(smiley);
		container.add(record);
		container.add(wizz);
		container.add(image);
		container.add(imageChooser);
		container.add(usernameLabel);
		container.add(connectionPanel);
		connectionPanel.add(hostLabel);
		connectionPanel.add(portLabel);
		connectionPanel.add(pseudoLabel);
		connectionPanel.add(hostField);
		connectionPanel.add(portField);
		connectionPanel.add(pseudoField);
		connectionPanel.add(connect);
		
		//----------------------------------------------------------
		
		ClientControl clc = new ClientControl();
		connect.addActionListener(clc);
		send.addActionListener(clc);
		smiley.addActionListener(clc);
		record.addActionListener(clc);
		wizz.addActionListener(clc);
		imageChooser.addActionListener(clc);
		
		//----------------------------------------------------------
		fen.setVisible(true);
	}
	
	
	/**
	 * N'envoie que des messages textes
	 * @param msg : le contenu
	 * @param type : selon le destinataire
	 * @param name : nom du sender
	 * @throws BadLocationException : generee par insertString
	 */
	public static void write(String msg, String type, String name) throws BadLocationException
	{
		SimpleDateFormat time = new SimpleDateFormat("d-M-Y HH:mm:ss");
		if(type.equals("self"))
		{
			StyleConstants.setForeground(ClientGUI.style, Color.darkGray);
			StyleConstants.setBackground(ClientGUI.style, Color.cyan);
			StyleConstants.setFontFamily(ClientGUI.style, "Calibri");
			StyleConstants.setFontSize(style, 16);
			StyleConstants.setBold(ClientGUI.style, true);
			chatDoc.insertString(chatDoc.getLength(),time.format(new Date())+": "+name+" > "+msg+"\n", ClientGUI.style);
		}
		else if(type.equals("other"))
		{
			StyleConstants.setForeground(ClientGUI.style, Color.white);
			StyleConstants.setBackground(ClientGUI.style, Color.GRAY);
			StyleConstants.setFontFamily(ClientGUI.style, "Calibri");
			StyleConstants.setFontSize(style, 16);
			StyleConstants.setBold(ClientGUI.style, true);
			chatDoc.insertString(chatDoc.getLength(), time.format(new Date())+": "+name+" > "+msg+"\n", ClientGUI.style);
		}
		else if(type.equals("server"))
		{
			StyleConstants.setForeground(ClientGUI.style, Color.green);
			StyleConstants.setBackground(ClientGUI.style, Color.darkGray);
			StyleConstants.setFontSize(style, 14);
			StyleConstants.setBold(ClientGUI.style, true);
			chatDoc.insertString(chatDoc.getLength(), time.format(new Date())+": "+name+" > "+msg+"\n", ClientGUI.style);
		}
	}
	

	/**
	 * N'envoie que des messages images
	 * @param msg : le contenu
	 * @param type : selon le destinataire
	 * @param name : nom du sender
	 * @throws BadLocationException : generee par insertString
	 */
	public static void writeImage(String msg, String type, String name) throws BadLocationException
	{
		SimpleDateFormat time = new SimpleDateFormat("d-M-Y HH:mm:ss");
		if(type.equals("self"))
		{
			StyleConstants.setForeground(ClientGUI.style, Color.darkGray);
			StyleConstants.setBackground(ClientGUI.style, Color.cyan);
			StyleConstants.setFontFamily(ClientGUI.style, "Calibri");
			StyleConstants.setFontSize(style, 16);
			StyleConstants.setBold(ClientGUI.style, true);
			
			StyleContext context = new StyleContext();
			Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
		    Icon icon = new ImageIcon(msg);
		    JLabel label = new JLabel(icon);
		    StyleConstants.setComponent(labelStyle, label);
		    
			chatDoc.insertString(chatDoc.getLength(),time.format(new Date())+": "+name+" > ", ClientGUI.style);
			chatDoc.insertString(chatDoc.getLength(), "Ignored", labelStyle);
			chatDoc.insertString(chatDoc.getLength(), "\n", ClientGUI.style);
		}
		else if(type.equals("other"))
		{
			StyleConstants.setForeground(ClientGUI.style, Color.white);
			StyleConstants.setBackground(ClientGUI.style, Color.gray);
			StyleConstants.setFontFamily(ClientGUI.style, "Calibri");
			StyleConstants.setFontSize(style, 16);
			StyleConstants.setBold(ClientGUI.style, true);
			
			StyleContext context = new StyleContext();
			Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
		    Icon icon = new ImageIcon(msg);
		    JLabel label = new JLabel(icon);
		    StyleConstants.setComponent(labelStyle, label);
		    
			chatDoc.insertString(chatDoc.getLength(), time.format(new Date())+": "+name+" > ", ClientGUI.style);
			chatDoc.insertString(chatDoc.getLength(), "Ignored", labelStyle);
			chatDoc.insertString(chatDoc.getLength(), "\n", ClientGUI.style);


		}
	}
	
	
	public static void wizz()
	{
		playSound("sound/wizz.wav");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Point currLocation;
		int iDisplaceXBy = 20;
		int iDisplaceYBy = -30;
		
		currLocation = fen.getLocationOnScreen();

	    Point position1 = new Point(currLocation.x + iDisplaceXBy, currLocation.y
	        + iDisplaceYBy);
	    Point position2 = new Point(currLocation.x - iDisplaceXBy, currLocation.y
	        - iDisplaceYBy);
	    
	    for (int i = 0; i < 60; i++) {
	      fen.setLocation(position1);
	      fen.setLocation(position2);
	    }
	    
	    fen.setLocation(currLocation);
		
	}
	
	public static void playSound(String sound) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
}
