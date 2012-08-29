package interfaces;

public abstract class CommandHeader {
	public String separator = "";//to separate header and body of the message
	public String ID_REQUEST = ""; //client -> server, requesting id
	public String ID_ASSIGN = "";//server -> client, assign id
	public String NEW_CLIENT = "";//server -> clients, indicate new connection
	public String S_MSG_ALL = "";//server -> clients, message from client# to all: MSG_ALL>>><<<senderId>>><<<content
	public String C_MSG_ALL = "";//clients -> server, message need to send to all
	public String LIST_REQ = "";//clients -> server, request client list
	public String LIST_RESP = "";//server -> clients, response client list: LIST_RESP>>><<<client#0>>><<<client#6...
	public String C_MSG_PRIV = "";//client -> server, private message: C_MSG_PRIV>>><<<targetId>>><<<content
	public String S_MSG_PRIV = "";//server -> client, private message: S_MSG_PRIV>>><<<senderId>>><<<content
	public String CHG_TGT = "";//client -> client mediator, notify that chatting target changed. CHG_TGT>>><<<targetId(-1: all)
	public String C_MSG_OUT = "";//client sender -> client mediator, indicate this message is sending
											//C_MSG_OUT>>><<<receiverId>>><<<content
	public String MSG_FAIL_PRIV = "";//private MSG send failed. MSG_FAIL_PRIV>>><<<id>>><<<content
}
