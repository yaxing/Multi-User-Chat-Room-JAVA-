package msgbuilder;

import interfaces.*;

public class ClientSendOutBuilder extends MsgBuilder{
	public void buildHeader(){
		this.message = commandHeader.C_MSG_OUT;
	}
	public void buildId(){
		this.message += commandHeader.separator;
		this.message += Integer.toString(this.socketId);
	}
	public void buildContent(){
		this.message += commandHeader.separator;
		this.message += this.content;
	}
}
