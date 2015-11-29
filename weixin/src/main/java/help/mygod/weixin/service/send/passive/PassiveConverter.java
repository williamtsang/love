package help.mygod.weixin.service.send.passive;

import java.util.List;

import help.mygod.weixin.common.Constant;
import help.mygod.weixin.common.entity.Message;
import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.receive.MsgTypeEnum;

/**
 * 
 * 被动响应消息转换类
 * 
 * @author RD_guowei_liu
 * 
 */
public class PassiveConverter {
	/**
	 * 基本转换
	 * @param event
	 * @return
	 */
	private static void convertBaseMessage(BaseMessage source,BaseMessage target) {
		target.setFromUserName(source.getToUserName());
		target.setToUserName(source.getFromUserName());
		target.setCreateTime(System.currentTimeMillis());
	}
	
	public static String convertMessage(BaseMessage message,Message sendMessage){
		String msgType = sendMessage.getMsgtype();
		if(MsgTypeEnum.text.name().equals(msgType)){
			return convertTextMessage(message,sendMessage.getContent());
		}else if(MsgTypeEnum.image.name().equals(msgType)){
			return convertImageMessage(message,sendMessage.getMediaid());
		}else if(MsgTypeEnum.news.name().equals(msgType)){
			return convertNewsMessage(message, sendMessage.toNewsItem());
		}else {
			return convertTextMessage(message,Constant.EMPTY_STRING);
		}
	}
	/**
	 * 被动响应文本信息
	 * @param message
	 * @param content
	 * @return
	 */
	public static String convertTextMessage(BaseMessage message,String content){
		TextMessage textMessage = new TextMessage();
		convertBaseMessage(message,textMessage);
		textMessage.setContent(String.format(content,textMessage.getToUserName()));
		return textMessage.createMessage();
	}
	/**
	 * 被动相应文本信息  并把微信OPENID替换内容
	 * @param message
	 * @param content
	 * @return
	 */
	public static String convertTextMessageTo(BaseMessage message,String content){
		TextMessage textMessage = new TextMessage();
		convertBaseMessage(message,textMessage);
		textMessage.setContent(String.format(content,textMessage.getToUserName()));
		return textMessage.createMessage();
	}
	/**
	 * 被动相应图片信息
	 * @param message
	 * @param mediaId
	 * @return
	 */
	public static String convertImageMessage(BaseMessage message,String mediaId){
		ImageMessage imgeMessage = new ImageMessage();
		convertBaseMessage(message,imgeMessage);
		imgeMessage.setMediaId(mediaId);
		return imgeMessage.createMessage();
	}
	/**
	 * 被动相应图文信息
	 * @param message
	 * @param items
	 * @return
	 */
	public static String convertNewsMessage(BaseMessage message,List<NewsItem> items){
		NewsMessage  newsMessage = new NewsMessage();
		convertBaseMessage(message,newsMessage);
		newsMessage.setItems(items);
		return newsMessage.createMessage();
	}
}
