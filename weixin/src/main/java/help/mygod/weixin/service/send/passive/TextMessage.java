package help.mygod.weixin.service.send.passive;

import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.receive.MsgTypeEnum;


/**
 * weixin响应的Text消息
 * @author RD_guowei_liu
 *
 */
public class TextMessage extends BaseMessage implements IMessageCreator{
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -7025451860077000321L;

	public static final String TEXTMESSAGE_TEMPLATE= "<xml>\n" + "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
			+ "<FromUserName><![CDATA[%2$s]]></FromUserName>\n" + "<CreateTime>%3$s</CreateTime>\n"
			+ "<MsgType><![CDATA[%4$s]]></MsgType>\n" + "<Content><![CDATA[%5$s]]></Content>\n" + "</xml>";


	/**
	 * 固定为 text
	 */
	private final static String msgType = MsgTypeEnum.text.name();

	/**
	 * 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 */
	private String content;

	public String getMsgType() {
		return msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	/* 
	 * 生成文本响应
	 * (non-Javadoc)
	 * @see com.youshang.kdb.restful.weixin.send.IMessage#toMessage()
	 */
	public String createMessage() {
		return String.format(TEXTMESSAGE_TEMPLATE, getToUserName(),
					getFromUserName(),
					getCreateTime(),
				MsgTypeEnum.text.name(), getContent());

	}

}
