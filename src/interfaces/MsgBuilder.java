package interfaces;

public abstract class MsgBuilder {

	protected CommandHeader commandHeader = new StandardHeader();
	
	protected String message = "";
	protected String content = "";
	protected int socketId = -1;
	
	public void setId(int id){
		this.socketId = id;
	}
	
	public void setContent(String input){
		this.content = input;
	}
	
	public String getMsg(){
		return message;
	}
	
	public abstract void buildHeader();
	public abstract void buildId();
	public abstract void buildContent();
}
