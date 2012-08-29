package server;

import interfaces.*;

import java.util.*;
import java.io.*;
import java.net.*;

import javax.swing.JTextArea;
import msgbuilder.*;


/**
 * Concrete Mediator
 * mediate server and all socket handlers for sockets connected to server
 * 
 * hold server object & a list of all socketHandler object
 * mediate the interaction among sockets and server
 * @author Yaxing Chen
 *
 */
class ServerMediator extends Mediator{
	private ArrayList<ServerSocketHandler> list; //hold client socket handlers
	
	private Server server; //hold server object
	
	private int serverId = -1;
		
	private int nextUserId = 0;
	
    private int socketId; //current socket id
    
    private String toServer; //message to server
    
    private String toClient; //message to client
 
    ServerMediator(){
    	this.msgManager = new ServerMsgManager();
    }
    
    public void setServer(Server server){
    	if(this.server == null){
    		this.server = server;
    	}
    }
	
	public ArrayList<ServerSocketHandler> getList(){
		if(list == null){
			list = new ArrayList<ServerSocketHandler>();
		}
		return list;
	}
	
	/**
	 * add new client
	 * @param newClient
	 */
	public void newClient(Socket s, DataInputStream dis, DataOutputStream dos){
		
		int newUserId = this.getNewUserId();
		
		ServerSocketHandler socketHandler = new ServerSocketHandler(s, dis, dos, newUserId);
		ServerReceiver receiver = new ServerReceiver(newUserId, socketHandler); 
		receiver.start();
		
		getList();
		list.add(socketHandler);
		nextUserId ++;
	}
	
	/**
	 * remove client when client exit
	 * @param id
	 * @return
	 */
	public boolean removeClient(int id){
		if(getList().isEmpty()){
			return false;
		}
		for(ServerSocketHandler e : list){
			if(e.getId() == id){
				list.remove(e);
				break;
			}
		}
		nextUserId --;
		return true;
	}
	
	public int getNewUserId(){
		if(list == null){
			getList();
			nextUserId = 0;
		}
		return getList().size();
	}
	
	public int getUserQty(){
		if(list == null){
			getList();
		}
		return list.size();
	}
	
	/**
	 * when new client connected, dispatch a new ID
	 * @param id
	 * @return true/false
	 */
	public boolean assignClientID(int id){
		for(ServerSocketHandler e : this.getList()){
			if(e.getId() == id){
	          	if(!e.notify(commandHeader.ID_ASSIGN + commandHeader.separator + Integer.toString(id))){
	          		return false;
	          	}
	          	break;
	         }
	     }
		return true;
	}
	
	/**
	 * send message to all colleague
	 * @param senderID
	 * @param content
	 * @return
	 */
	public boolean sendToAll(int senderId, String content){
		for(ServerSocketHandler e : this.getList()){
			if(e.getId() == senderId){
	           	continue;
	        }
	        if(!e.notify(content)){
	        	return false;
	        }
	    }
		return true;
	} 
	
	/**
	 * send to a certain client
	 * @param senderId
	 * @param receiverId
	 * @param content
	 * @return
	 */
	public boolean sendTo(int senderId, int receiverId, String content){
		int count = 0;
		for(ServerSocketHandler e : this.getList()){
			if(e.getId() == receiverId){
				if(!e.notify(content)){
		        	return false;
		        }
				break;
	        }
			count++;
	    }
		if(count == this.getList().size()){
			return false;
		}
		return true;
	}
	
	/**
	 * refresh client list for all clients
	 */
	public void broadcastClientList(){
		toClient = "";
		int size = list.size();
		int counter = 0;
    	for(ServerSocketHandler s : list){
    		toClient += "Client #" + s.getId();
    		if(++counter < size){
    			toClient += commandHeader.separator;
    		}
    	}
    	
    	msgBuilder = new ClientListRespBuilder();
    	msgBuilder.setContent(toClient);
    	toClient = msgManager.constructMsg(msgBuilder);
    	sendToAll(serverId, toClient);
	}
	
	/**
	 * analyze input to server and mediate them
	 * @param String input
	 */
	public boolean analyze(SocketHandler socketH, String input){
		//JTextArea serverScreen = server.getScreen();
		socketId = socketH.getId();
		String[] inputTmp = input.split(commandHeader.separator);
		String command = inputTmp[0];
		String content = new String();
		
		if(command.equals(commandHeader.ID_REQUEST)){
			//new client connect
			
			//1.client require ID, server assign id
       		assignClientID(socketId);
        	server.display("New client: " + socketId +"\n");
        	server.display("Clients Qty:" + getUserQty() + "\n");
        	
        	//2.notify all for new client
        	
        	msgBuilder = new NewClientNotifyBuilder();
        	msgBuilder.setContent("New client: " + socketId);
        	toClient = msgManager.constructMsg(msgBuilder);
        	
        	//toClient = NEW_CLIENT + separator + "New client: " + socketId;
        	sendToAll(socketId, toClient);
        	
        	//3.send new client list to all
        	broadcastClientList();
        }	
		
		else if(command.equals(commandHeader.C_MSG_ALL)){
			//broadcast msg
			content = inputTmp[1];
			toServer = "Client#" + socketId + " to all:" + content + "\n";
			server.display(toServer);
            
			msgBuilder = new ServerBroadCastBuilder();
			msgBuilder.setId(socketId);
			msgBuilder.setContent(content);
			toClient = msgManager.constructMsg(msgBuilder);
           	sendToAll(socketId, toClient);
           	
           	if(content.equals("bye")){
    			toClient = "Client #"+socketId+" Exit!";
    			
    			msgBuilder = new ServerBroadCastBuilder();
    			msgBuilder.setId(socketId);
    			msgBuilder.setContent(toClient);
    			toClient = msgManager.constructMsg(msgBuilder);
    			
    			//toClient = S_MSG_ALL + separator + serverId + separator + toClient;
    			sendToAll(socketId, toClient);
    			
    			removeClient(socketId);
    			socketH.close();
    			//refresh client list;
    			broadcastClientList();
    			
    			toClient = "Client #"+socketId+" Exit!\n";
    			server.display(toClient);
    			server.display("Clients Qty:" + getUserQty() + "\n");
    			
    			return false;
    		}
		}
		
		else if(command.equals(commandHeader.C_MSG_PRIV)){
			int receiver = Integer.parseInt(inputTmp[1]);
			content = inputTmp[2];
			toServer = "Client #" + socketId + " to Client #" + receiver + " :" + content + "\n";
			server.display(toServer);
			msgBuilder = new ServerPrivateMsgBuilder();
			msgBuilder.setId(socketId);
			msgBuilder.setContent(content);
			toClient = msgManager.constructMsg(msgBuilder);
			
			//toClient = S_MSG_PRIV + separator + socketId + separator + content;
			if(!sendTo(socketId, receiver, toClient)){
				msgBuilder = new MsgFailPrivBuilder();
				msgBuilder.setId(receiver);
				msgBuilder.setContent(content);
				toClient = msgManager.constructMsg(msgBuilder);
				sendTo(serverId, socketId, toClient);
			}
		}
		
		return true;
	}
}