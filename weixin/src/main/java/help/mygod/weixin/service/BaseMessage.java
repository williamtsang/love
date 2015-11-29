package help.mygod.weixin.service;

/**
 * 接收消息的基类，包含，菜单点击和用户发送
 * 
 * @author RD_guowei_liu
 * 
 */
public class BaseMessage {
	/**
	 * 开发者微信号
	 */
	protected String toUserName;
	/**
	 * 发送方帐号（一个OpenID）
	 */
	protected String fromUserName;
	/**
	 * 消息创建时间 （整型）
	 */
	protected long createTime;
	/**
	 * 消息类型 参照 MsgTypeEnum
	 */
	protected String msgType;

	
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
