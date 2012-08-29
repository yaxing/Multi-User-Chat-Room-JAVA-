package msgbuilder;

import interfaces.*;

public class NewClientNotifyBuilder extends MsgBuilder{
	public void buildHeader(){
		this.message = commandHeader.NEW_CLIENT;
	}
	public void buildId(){
		
	}
	public void buildContent(){
		this.message += commandHeader.separator;
		this.message += content;
	}
}
