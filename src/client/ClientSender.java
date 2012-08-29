package client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import msgbuilder.*;
import interfaces.*;



/**
 * ClientSender
 * 
 * Action listener for text field & key board
 * @author Yaxing Chen
 *
 */
public class ClientSender extends KeyAdapter implements ActionListener,FocusListener, ItemListener{
 
    private ClientSocketHandler socket;
    private Client client;
    private ClientMsgManager msgManager = new ClientMsgManager();
    private MsgBuilder msgBuilder = null;
    private String message = "";
	protected int receiverId = -1;
    
    public ClientSender(Client client, ClientSocketHandler csh){
    	this.client = client;
        this.socket = csh;
    }
    
    private void writeSocket(String s){
    	msgBuilder = new ClientSendOutBuilder();
    	msgBuilder.setId(receiverId);
    	msgBuilder.setContent(s);
    	message = msgManager.constructMsg(msgBuilder);
    	
    	if(!socket.accept(message)){
    		System.exit(-1);
    	}
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    	
        if(e.getKeyChar()==(char)KeyEvent.VK_ENTER){
        	JTextField field = client.getInputField();
        	String s = field.getText();
            field.setText("");
            writeSocket(s);    
        }
    }
     public void actionPerformed(ActionEvent e){
    	JTextField field = client.getInputField();
    	JTextArea area = client.getScreen();
        if(e.getActionCommand().equals("Send")){
            String s = field.getText();
            writeSocket(s);
        } 
        if(e.getActionCommand().equals("Clear")){
            area.setText("");
        }
    }
     
    public void itemStateChanged(ItemEvent e){
    	List clientList = client.getList();
    	//client.display(clientList.getSelectedItem() + "\n");
    	if(clientList.getSelectedIndex() == 0){
    		receiverId = -1;
    	}
    	else{
    		String[] tmp = clientList.getSelectedItem().split("#");
    		receiverId = Integer.parseInt(tmp[1]);
    	}
    	
    	msgBuilder = new ChatTargetChgBuilder();
    	msgBuilder.setId(receiverId);
    	message = msgManager.constructMsg(msgBuilder);
    	
    	socket.accept(message);
    }

    public void focusGained(FocusEvent e) {
    	JTextField field = client.getInputField();
        Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
        field.requestFocusInWindow();
    }

    public void focusLost(FocusEvent e) {}
}