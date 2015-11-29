package help.mygod.weixin.service.send.passive;

import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.receive.MsgTypeEnum;


/**
 * weixin响应的image消息
 * @author RD_guowei_liu
 *
 */
public class ImageMessage extends BaseMessage implements IMessageCreator{
	

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 5854799717360504065L;


	public static final String IMAGEMESSAGE_TEMPLATE= "<xml>\n" + "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
			+ "<FromUserName><![CDATA[%2$s]]></FromUserName>\n" + "<CreateTime>%3$s</CreateTime>\n"
			+ "<MsgType><![CDATA[%4$s]]></MsgType>\n" + "<Image>\n"
			+ "<MediaId><![CDATA[%5$s]]></MediaId>\n" + "</Image>\n" + "</xml>";


	/**
	 * 固定为 text
	 */
	private final static String msgType = MsgTypeEnum.image.name();

	/**
	 * 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 */
	private String mediaId;

	public String getMsgType() {
		return msgType;
	}


	public String getMediaId() {
		return mediaId;
	}


	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}


	/* 
	 * 生成文本响应
	 * (non-Javadoc)
	 * @see com.youshang.kdb.restful.weixin.send.IMessage#toMessage()
	 */
	public String createMessage() {
		return String.format(IMAGEMESSAGE_TEMPLATE, getToUserName(),
					getFromUserName(),
					getCreateTime(),
				MsgTypeEnum.image.name(), getMediaId());

	}

}
