package client;

import interfaces.*;

import java.io.*;
import java.net.Socket;


public class ClientSocketHandler extends SocketHandler {

	//private ClientMediator mediator;
	
	ClientSocketHandler(Socket socket, DataInputStream dis, DataOutputStream dos, int id){
		super(socket, dis, dos, id);
		this.mediator = Client.getMediator();
	}
	
	public DataOutputStream getOutputStream(){
		return dos;
	}	

}
