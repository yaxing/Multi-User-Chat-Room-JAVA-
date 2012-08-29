package msgbuilder;

import interfaces.*;

public class ChatTargetChgBuilder extends MsgBuilder{
	public void buildHeader(){
		this.message = commandHeader.CHG_TGT;
	}
	public void buildId(){
		this.message += commandHeader.separator;
		this.message += Integer.toString(this.socketId);
	}
	public void buildContent(){
		
	}
}
