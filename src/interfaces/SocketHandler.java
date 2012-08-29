package interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import client.Client;


public abstract class SocketHandler extends Colleague {
	protected Socket socket = null;
	protected DataInputStream dis = null;
	protected DataOutputStream dos = null;
	protected int socketId = -1;
	
	public SocketHandler(Socket socket, DataInputStream dis, DataOutputStream dos, int id){
		this.socket = socket;
		this.dis = dis;
		this.dos = dos;
		this.socketId = id;
		//this.mediator = Client.getMediator();
	}
	
	
	public int getId(){
		return socketId;
	}
	
	/**
	 * close socket
	 */
	public void close(){
		try{
			dis.close();
			dos.close();
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * write content to socket
	 * @param content
	 * @return
	 */
	public boolean notify(String content){
		try{
			dos.writeUTF(content);
		}catch(IOException e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 *  accept input from socket--get from receiver
	 * and send to mediator for further analysis.
	 * @param content
	 */
	public boolean accept(String content){
		boolean result = mediator.analyze(this, content);
		return result;
	}
	
	/**
	 * listen to socket
	 * @return
	 */
	public String listen(){
		String input = "";
		try{
			if(socket.isClosed()){
				return "//";
			}
			else{
				input = dis.readUTF();
			}
		}catch(IOException e){
			//e.printStackTrace();
			//return "//";
		}
		return input;
	}
	
	public Socket getSocket() {
		return socket;
	}


	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	public DataInputStream getDis() {
		return dis;
	}


	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}


	public DataOutputStream getDos() {
		return dos;
	}


	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}


	public int getSocketId() {
		return socketId;
	}


	public void setSocketId(int socketId) {
		this.socketId = socketId;
	}


	public Mediator getMediator() {
		return mediator;
	}


	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}
}
