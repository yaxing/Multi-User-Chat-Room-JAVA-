package interfaces;

public abstract class MsgManager {
	
	private MsgBuilder builder;
	
	public String constructMsg(MsgBuilder builder){
		this.builder = builder;
		this.builder.buildHeader();
		this.builder.buildId();
		this.builder.buildContent();
		return builder.getMsg();
	}
}
