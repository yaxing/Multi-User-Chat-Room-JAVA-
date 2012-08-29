package interfaces;

/**
 * Abstract Receiver
 * 
 * @author Yaxing Chen
 * @date 04/12/2011
 */
public abstract class Receiver extends Thread {
	protected SocketHandler socketHandler;
	protected int id; //id #
	
	public void run(){
		String input;
        while(true){
            input = socketHandler.listen();
            if(input.equals("//")){
            	break;
            }
            if(!socketHandler.accept(input)){
            	break;
            } 
        }
	}
}
