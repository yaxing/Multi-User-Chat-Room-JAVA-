package client;

import interfaces.*;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
/**
 * Client
 * 
 * @author Yaxing Chen
 * 
 */
public class Client extends Colleague{
	
	private JFrame jf;
	private JTextField field;
	private Cursor cursor;
	private JTextArea area;
	private JButton button;
	private JButton clear;
	private Panel panel;
	private Dimension d;
	private Toolkit toolkit;
	private ScrollPane sp;
	private List clientList;
	
	private int clientId;
	
	private static ClientMediator mediator;
	
	public static ClientMediator getMediator(){
		if(mediator == null){
			mediator = new ClientMediator();
		}
		return mediator;
	}
	
	private void startClient(){
        
        try{
            Socket s = new Socket("127.0.0.1",2008);
            
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            DataInputStream dis = new DataInputStream(is);
            DataOutputStream dos = new DataOutputStream(os);
            ClientSocketHandler csh = new ClientSocketHandler(s, dis, dos, -1);
            getMediator();
            
            mediator.setClient(this);
            mediator.setSocket(csh);
            
            
           // retrieve user ID from 
            mediator.retreiveId();            
            createUI();
            
            mediator.showId();
            
            //create writer thread for socket
            ClientSender say = new ClientSender(this, csh);
            
            //create reader thread for socket
            //listening socket
            ClientReceiver receiver = new ClientReceiver(clientId, csh);               
            
            clientList.addItemListener(say);
            button.addActionListener(say);
            clear.addActionListener(say);
            field.addKeyListener(say);
            jf.addFocusListener(say);
            
            receiver.start();
            
        }catch(IOException e){
            e.printStackTrace();
        }
	}
	
	/**
	 * set client id
	 * @param id
	 */
	public void setId(int id){
		this.clientId = id;
	}
	
	public List getList(){
		return clientList;
	}
	
	public JTextArea getScreen(){
		return area;
	}
	
	public JTextField getInputField(){
		return field;
	}
	
	/**
	 * create UI
	 */
	private void createUI(){
		jf = new JFrame("Client " + clientId);
        field = new JTextField(20);
        cursor = new Cursor(Cursor.TEXT_CURSOR);
        field.requestFocusInWindow();
        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Times New Roman",0,14));
        button = new JButton("Send");
        clear = new JButton("Clear");
        
        sp = new ScrollPane();
        sp.add(area);
        
        clientList = new List();
        //clientList.setBounds(0, 0, 100, 327);
        //clientList.add("Client # 1");
        //field.setBounds(50, 0, 50, 20);
        
        panel = new Panel();
        panel.add(field);
        panel.add(button);
        panel.add(clear);
        jf.add(sp,"Center");
        jf.add(panel,"South");
        jf.add(clientList, "East");
        toolkit = jf.getToolkit();
        d = toolkit.getScreenSize();
        jf.setBounds(d.width/2-268,d.height/2-163,650,327);
        
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.validate();
	}
	
	/**
	 * display on client screen
	 * @param content
	 */
	public void display(String content){
		area.append(content);
	}
	
	/**
	 * refresh client list
	 * @param clients
	 * @param curReceiver
	 */
	public void refreshList(String[] clients, String curReceiver){
		clientList.removeAll();
		int receiverIndex = 0;
		int count = 0;
		String[] tmp = null;
		for(String item : clients){
			clientList.add(item);
			tmp = item.split("#");
			if(tmp.length >= 2){
				if(item.split("#")[1].equals(curReceiver)){
					receiverIndex = count;
				}
			}
			count ++;
		}
		clientList.select(receiverIndex);
		clientList.repaint();
	}
	
    public static void main(String args[]){
       Client client = new Client();
       client.startClient();
    }
}
