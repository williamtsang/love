package help.mygod.weixin.service.send.custom;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import help.mygod.weixin.common.Constant;
import help.mygod.weixin.common.entity.Message;
import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.receive.MsgTypeEnum;
import help.mygod.weixin.service.send.custom.NewsMessage.ArticlesItem;

/**
 * 
 * 客服消息转换类
 * 
 * @author RD_guowei_liu
 * 
 */
public class CustomConverter {

	/**
	 * 
	 * @param source
	 * @param target
	 */
	public static void convertBaseMessage(BaseMessage source,CustomMessage target) {
		target.setTouser(source.getFromUserName());
	}
	
	
	public static List<String> convertMessage(BaseMessage message,List<Message> sendMessages){
		List<String> reply = new ArrayList<String>();
		for(Message  temp : sendMessages){
			String msgType = temp.getMsgtype();
			if(MsgTypeEnum.text.name().equals(msgType)){
				reply.add( convertTextMessage(message,temp.getContent()));
			}else if(MsgTypeEnum.image.name().equals(msgType)){
				reply.add( convertImageMessage(message,temp.getMediaid()));
			}else if(MsgTypeEnum.news.name().equals(msgType)){
				reply.add(convertNewsMessage(message, temp.toArticlesItem()));
			}else {
				reply.add( convertTextMessage(message, Constant.EMPTY_STRING));
			}
		}
		return reply;
	}
	
	
	public static String convertTextMessage(BaseMessage message,String content){
		TextMessage textMessage = new TextMessage();
		convertBaseMessage(message,textMessage);
		TextMessage.TextItem textItem = textMessage.new TextItem();
		textItem.setContent(content);
		textMessage.setText(textItem);
		return JSONObject.toJSONString(textMessage);
	}
	public static String convertImageMessage(BaseMessage message,String mediaId){
		ImageMessage imageMessage = new ImageMessage();
		convertBaseMessage(message,imageMessage);
		ImageMessage.ImageItem imageItem = imageMessage.new ImageItem();
		imageItem.setMedia_id(mediaId);
		imageMessage.setImage(imageItem);
		return JSONObject.toJSONString(imageMessage);
	}
	public static String convertNewsMessage(BaseMessage message,List<ArticlesItem> articles){
		NewsMessage  newsMessage = new NewsMessage();
		convertBaseMessage(message,newsMessage);
		
		NewsMessage.NewsItem newsItem = newsMessage.new NewsItem();
		newsItem.setArticles(articles);
		
		newsMessage.setNews(newsItem);
		return JSONObject.toJSONString(newsMessage);
	}
}
