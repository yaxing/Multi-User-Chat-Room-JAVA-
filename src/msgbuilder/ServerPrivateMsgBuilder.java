package msgbuilder;

import interfaces.*;

public class ServerPrivateMsgBuilder extends MsgBuilder{
	public void buildHeader(){
		this.message = commandHeader.S_MSG_PRIV;
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
