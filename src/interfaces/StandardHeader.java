package interfaces;

public class StandardHeader extends CommandHeader{
	
	public StandardHeader(){
		super();
		this.separator = ">>><<<";
		this.ID_REQUEST = "__ID"; 
		this.ID_ASSIGN = "__IDA";
		this.NEW_CLIENT = "__NEW";
		this.S_MSG_ALL = "__MSG";
		this.C_MSG_ALL = "__OUTA";
		this.LIST_REQ = "__LISTREQ";
		this.LIST_RESP = "__LISTRES";
		this.C_MSG_PRIV = "__MSGPC";
		this.S_MSG_PRIV = "__MSGPS";
		this.CHG_TGT = "__TGTCHG";
		this.C_MSG_OUT = "__MSGOUT";
		this.MSG_FAIL_PRIV = "__MSGPRVF";
	}
	
}
