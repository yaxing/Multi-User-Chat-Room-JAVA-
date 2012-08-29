package msgbuilder;

import interfaces.*;

public class IdRequestBuilder extends MsgBuilder {
	public void buildHeader(){
		String header = commandHeader.ID_REQUEST;
		this.message = header;
	}
	public void buildId(){
		
	}
	public void buildContent(){
		
	}
}
