package msgbuilder;

import interfaces.*;

public class ClientBroadCastBuilder extends MsgBuilder {
	public void buildHeader(){
		this.message = commandHeader.C_MSG_ALL;
	}
	public void buildId(){
		
	}
	public void buildContent(){
		this.message += commandHeader.separator;
		this.message += this.content;
	}
}
