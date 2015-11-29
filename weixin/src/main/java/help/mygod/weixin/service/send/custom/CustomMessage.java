package help.mygod.weixin.service.send.custom;

/**
 * 发送客服信息的基类
 * 
 */
public class CustomMessage {
	
	/**
	 * 普通用户openid
	 */
	protected String touser;
	
	/**
	 * 消息类型
	 */
	protected String msgtype;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	
	
}
