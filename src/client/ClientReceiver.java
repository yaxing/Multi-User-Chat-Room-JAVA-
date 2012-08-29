package client;
import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JTextArea;
import interfaces.*;

/**
 * ClientReceiver
 * 
 * Thread for reading socket information
 * @author Yaxing Chen
 *
 */
class ClientReceiver extends Receiver{
   
    public ClientReceiver(int idNum, ClientSocketHandler csh){
        this.id = idNum;
        this.socketHandler = csh;
    }
    
}