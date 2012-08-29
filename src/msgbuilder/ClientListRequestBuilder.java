package msgbuilder;

import interfaces.*;

public class ClientListRequestBuilder extends MsgBuilder{
	public void buildHeader(){
		this.message = commandHeader.LIST_REQ;
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
