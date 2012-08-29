package server;

import interfaces.*;

import java.io.*;
import java.net.Socket;


/**
 * Concrete Collegue
 * Object storing information for one connected socket 
 * @author Yaxing Chen
 *
 */
class ServerSocketHandler extends SocketHandler{
	
	//private ServerMediator mediator;
	
	ServerSocketHandler(Socket socket, DataInputStream dis, DataOutputStream dos, int id){
		super(socket, dis, dos, id);
		this.mediator = Server.getMediator();
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public DataInputStream getInputStream(){
		return dis;
	}
	
	public DataOutputStream getOutputStream(){
		return dos;
	}

}