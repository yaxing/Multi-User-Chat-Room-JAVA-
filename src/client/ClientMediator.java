package client;
//
//import java.io.*;
//import java.net.*;
//import java.awt.*;
//import javax.swing.*;

import msgbuilder.*;
import interfaces.*;

public class ClientMediator extends Mediator {
	private Client client;
	private ClientSocketHandler socket;
	private int clientId = -1;
	private int curReceiver = -1;
	
	public ClientMediator(){
		this.msgManager = new ClientMsgManager();
	}
	
	
	public Client getClient() {
		return client;
	}



	public void setClient(Client client) {
		this.client = client;
	}



	public ClientSocketHandler getSocket() {
		return socket;
	}



	public void setSocket(ClientSocketHandler socket) {
		this.socket = socket;
	}



	public int getClientId() {
		return clientId;
	}



	public void setClientId(int clientId) {
		this.clientId = clientId;
	}



	/**
	 * retreive client id from server
	 */
	public void retreiveId(){
		msgBuilder = new IdRequestBuilder();
		String msg = msgManager.constructMsg(msgBuilder);
		socket.notify(msg);
		//socket.notify(ID_REQUEST);
		analyze(socket, socket.listen());
	}
	
	/**
	 * display user id to user
	 */
	public void showId(){
		
		if(clientId < 0){
			client.display("ID retreive error!");
			return;
		}
		client.display("Your ID is: " + clientId + "\n");
	}
	
	
	/**
	 * analyze
	 */
	public boolean analyze(SocketHandler socketH, String input){
       	String[] contentTmp = input.split(commandHeader.separator);
       	String content = new String();      	
       	
       	int sourceClientId = -1;
       	
       	String command = contentTmp[0];
       	
    	if(contentTmp.length < 2){
    		client.display("Retrieving data error");
    	}
    	
    	else if(command.equals(commandHeader.ID_ASSIGN)){
    		//server assign id
    		String id = contentTmp[1];
    		clientId = Integer.parseInt(id);
    		client.setId(clientId);
    		socket.setSocketId(clientId);
    	}
    	
    	else if(command.equals(commandHeader.NEW_CLIENT)){
    		//server notify new client
    		client.display(contentTmp[1] + "\n");
    	}
    	
    	else if(command.equals(commandHeader.S_MSG_ALL)){
    		//get broadcast msg
    		sourceClientId = Integer.parseInt(contentTmp[1]);
        	content = contentTmp[2];
        	if(sourceClientId < 0){
        		client.display("From Server to you(#"+clientId+") :" + content + "\n");
        	}
        	else{
        		client.display("(Broadcast)From client #"+ sourceClientId + " to you(#"+clientId+") :" + content + "\n");
        	}
    	} 
    	
    	else if(command.equals(commandHeader.C_MSG_OUT)){
    		//sending out msg
    		int receiver = Integer.parseInt(contentTmp[1]);
    		content = contentTmp[2];
    		
    		//verify receiver
    		if(receiver < 0){
    			client.display("You said to all: " + content + "\n");
    			msgBuilder = new ClientBroadCastBuilder();
    			msgBuilder.setContent(content);
    			message = msgManager.constructMsg(msgBuilder);
    		}
    		else{
    			content = "You said to Client #" + receiver + ": " + contentTmp[2];
        		msgBuilder = new ClientPrivateMsgBuilder();
        		msgBuilder.setId(receiver);
        		msgBuilder.setContent(contentTmp[2]);
        		message = msgManager.constructMsg(msgBuilder);
        		
        		content += "\n";
        		client.display(content);
    		}
    		
    		socket.notify(message);
    		
    		if(receiver < 0 && content.equals("bye")){
    			socket.close();
    			return false;
    		}
    	}
    	
    	else if(command.equals(commandHeader.S_MSG_PRIV)){
    		//private msg from other client
    		content = "Client #" + Integer.parseInt(contentTmp[1]) + "said to you(#" + clientId + "):" + contentTmp[2] + "\n";
    		client.display(content);
    	}
    	
    	else if(command.equals(commandHeader.LIST_RESP)){
    		//refresh clients list
    		contentTmp[0] = "To all";
    		client.refreshList(contentTmp, Integer.toString(curReceiver));
    	}
    	
    	else if(command.equals(commandHeader.CHG_TGT)){
    		//chatting target changed
    		curReceiver = Integer.parseInt(contentTmp[1]);
    		String target = "";
    		if(curReceiver < 0){
    			target = "all clients";
    		}
    		else{
    			target = "Client #" + curReceiver;
    		}
    		content = "Notice: You are chatting with " + target + " now\n";
    		client.display(content);
    	}
    	
    	else if(command.equals(commandHeader.MSG_FAIL_PRIV)){
    		int receiver = Integer.parseInt(contentTmp[1]);
    		content = "Failed: You said to Client #" + receiver + " :'" + contentTmp[2] + "' is unable to be send.\n";
    		content += "Please check if Client #" + receiver + "exists and try again.\n";
    		client.display(content);
    	}
    	
    	return true;
	}
	
}
