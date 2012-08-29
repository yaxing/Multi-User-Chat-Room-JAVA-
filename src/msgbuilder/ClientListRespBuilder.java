package msgbuilder;

import interfaces.*;

public class ClientListRespBuilder extends MsgBuilder{
	public void buildHeader(){
		this.message = commandHeader.LIST_RESP;
	}
	public void buildId(){
		
	}
	public void buildContent(){
		this.message += commandHeader.separator;
		this.message += this.content;
	}
}
