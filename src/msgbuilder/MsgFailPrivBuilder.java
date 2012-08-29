package msgbuilder;

import interfaces.*;

public class MsgFailPrivBuilder extends MsgBuilder{
	public void buildHeader(){
		String header = commandHeader.MSG_FAIL_PRIV;
		this.message = header;
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
