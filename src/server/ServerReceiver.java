package server;

import interfaces.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

import javax.swing.JTextArea;

/**
 * ServerReceiver
 * receiver thread
 * Thread for reading socket information
 * 
 * pass input message to socketHandler--the mediator colleague
 * @author Yaxing Chen
 *
 */
class ServerReceiver extends Receiver{
    
    public ServerReceiver(int idNum, ServerSocketHandler socketHandler){
        this.id = idNum;
        this.socketHandler = socketHandler;
    }
}