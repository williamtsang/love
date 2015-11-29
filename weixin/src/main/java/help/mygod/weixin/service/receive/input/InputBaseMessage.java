package help.mygod.weixin.service.receive.input;

import help.mygod.weixin.service.BaseMessage;



/**
 * 接受普通消息
 * @author Administrator
 *
 */
public class InputBaseMessage extends BaseMessage{
	
	/**
	 * 消息id，64位整型
	 */
	private String msgId;
	
	public String getMsgId() {
		return msgId;
	}


	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	
}
