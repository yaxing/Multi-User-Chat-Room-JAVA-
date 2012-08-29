package server;

import interfaces.*;

import java.awt.*;
import javax.swing.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Server
 * Mediator, Observer
 * @author Yaxing Chen
 *
 */
public class Server extends Colleague{
	
	private JFrame jf;
	private JTextArea area;
	private Panel panel;
	private Dimension d;
	private Toolkit toolkit;
	private ServerSocket ss;
	
	//singleton & observer & mediator
	
	private static ServerMediator chatMediator;
	
	/**
	 * get client mediator
	 * @return chatMediator chatMediator
	 */
	public static ServerMediator getMediator(){
		if(chatMediator == null){
			chatMediator = new ServerMediator();
		}
		return chatMediator;
	}
    
    private void startServer(){
    	
    	createUI();
        
    	getMediator().setServer(this);
    	
        try{
        	try {
        		ss = new ServerSocket(2008);
        	} catch (IOException e) {
        	    System.out.println("Accept failed: 1206");
        	    System.exit(-1);
        	}
            while(true){
	            Socket s = ss.accept();
	            
	            InputStream is = s.getInputStream();
	            OutputStream os = s.getOutputStream();
	            DataInputStream dis = new DataInputStream(is);
	            DataOutputStream dos = new DataOutputStream(os);
	            
	            //register new client to mediator
	            chatMediator.newClient(s, dis, dos);
        	}
        }catch(IOException e){
            //e.printStackTrace();
        	//break;
        }
    }
    
    public void display(String content){
    	area.append(content);
    }
    
    private void createUI(){
    	jf = new JFrame("Chatter Server");
        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Times New Roman",0,14));
        
        ScrollPane sp = new ScrollPane();
        sp.add(area);
        
        panel = new Panel();
        jf.add(sp,"Center");
        jf.add(panel,"South");
        toolkit = jf.getToolkit();
        d = toolkit.getScreenSize();
        jf.setBounds(d.width/2-268,d.height/2-163,536,327);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.validate();
    }
    
    public static void main(String args[]){
        Server server = new Server();
        server.startServer();
    }
}





