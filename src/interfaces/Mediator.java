package interfaces;

import javax.swing.JTextArea;

import interfaces.*;


/**
 * Abstract mediator
 * @author Yaxing Chen
 *
 */
public abstract class Mediator {
	
	protected CommandHeader commandHeader = new StandardHeader(); 
	
	protected MsgManager msgManager = null;
    protected MsgBuilder msgBuilder = null;
    protected String message = "";
	
	public abstract boolean analyze(SocketHandler socketH, String input);
}
