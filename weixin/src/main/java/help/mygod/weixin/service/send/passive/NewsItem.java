package help.mygod.weixin.service.send.passive;


/**
 * 图文消息的内部类
 * 
 * @author RD_guowei_liu
 * 
 */
public class NewsItem {

	public static final String NEWSITEMMESSAGE_FORMAT = "<item>\n" + "<Title><![CDATA[%1$s]]></Title>\n"
			+ "<Description><![CDATA[%2$s]]></Description>\n" + "<PicUrl><![CDATA[%3$s]]></PicUrl>\n"
			+ "<Url><![CDATA[%4$s]]></Url>\n" + "</item>\n";
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息描述
	 */
	private String description;
	/**
	 * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 */
	private String picUrl;
	/**
	 * 点击图文消息跳转链接
	 */
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String createMessage() {
		return String.format(NEWSITEMMESSAGE_FORMAT
				,getTitle()
				,getDescription()
				,getPicUrl()
				,getUrl());
	}

}
