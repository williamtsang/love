package help.mygod.weixin.service.send.passive;

import java.util.ArrayList;
import java.util.List;

import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.receive.MsgTypeEnum;

/**
 * weixin响应的news消息
 * @author RD_guowei_liu
 *
 */
public class NewsMessage extends BaseMessage implements IMessageCreator{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 840592402558593221L;

	public static final String  NEWSMESSAGE_FORMAT=	 "<xml>\n" + "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
			+ "<FromUserName><![CDATA[%2$s]]></FromUserName>\n" + "<CreateTime>%3$s</CreateTime>\n"
			+ "<MsgType><![CDATA[news]]></MsgType>\n" + "<ArticleCount>%4$s</ArticleCount>\n"
			+ "<Articles>\n" + "%5$s"
			+ "</Articles>\n" + "</xml>\n";
	
	/**
	 * 消息类型 固定为news
	 */
	private final String msgType = MsgTypeEnum.news.name();
	
	/**
	 * 消息数
	 */
	private List<NewsItem> items= new ArrayList<NewsItem>();
	


	public String getMsgType() {
		return msgType;
	}

	public List<NewsItem> getItems() {
		return items;
	}

	public void setItems(List<NewsItem> items) {
		this.items = items;
	}
	
	public String getItemString(){
		StringBuilder sb = new StringBuilder("");
		List<NewsItem> items = getItems();
		for(NewsItem temp : items){
			sb.append(temp.createMessage());
		}
		return sb.toString();
	}
	

	public String createMessage() {
		return String.format(NEWSMESSAGE_FORMAT, getToUserName(),
				getFromUserName(),
				getCreateTime(), getItems().size(),getItemString());
	}
}
