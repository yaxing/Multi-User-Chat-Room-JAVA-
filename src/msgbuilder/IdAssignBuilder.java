package msgbuilder;

import interfaces.*;

public class IdAssignBuilder extends MsgBuilder {
	public void buildHeader(){
		this.message = commandHeader.ID_ASSIGN;
	}
	public void buildId(){
		
	}
	public void buildContent(){
		this.message += commandHeader.separator;
		this.message += this.content;
	}
}
