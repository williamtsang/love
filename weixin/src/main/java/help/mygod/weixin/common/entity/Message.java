package help.mygod.weixin.common.entity;

import java.util.ArrayList;
import java.util.List;

import help.mygod.weixin.service.send.custom.NewsMessage;
import help.mygod.weixin.service.send.custom.NewsMessage.ArticlesItem;
import help.mygod.weixin.service.send.passive.NewsItem;





public class Message {
    private String createtime;
    private String msgtype;
    /**
	 * 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 */
	private String content;
	
	/**
	 * 通过上传多媒体文件，得到的id。
	 */
	private String mediaid;

	/**
	 * 视频消息、音乐的标题
	 */
	private String title;
	
	/**
	 * 视频消息、音乐的描述
	 */
	private String description;
	
	/**
	 * 音乐链接
	 */
	private String musicurl;
	
	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 */
	private String hqmusicurl;
	
	/**
	 * 缩略图的媒体id，通过上传多媒体文件，得到的id
	 */
	private String thumbmediaid;

	/**
	 * MsgType为news时，多条图文消息，不能超过10条
	 */
	private List<MessageItem> items = new ArrayList<MessageItem>();
	
    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaid() {
        return mediaid;
    }

    public void setMediaid(String mediaid) {
        this.mediaid = mediaid;
    }

    public String getThumbmediaid() {
        return thumbmediaid;
    }

    public void setThumbmediaid(String thumbmediaid) {
        this.thumbmediaid = thumbmediaid;
    }

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

    public String getMusicurl() {
        return musicurl;
    }

    public void setMusicurl(String musicurl) {
        this.musicurl = musicurl;
    }

    public String getHqmusicurl() {
        return hqmusicurl;
    }

    public void setHqmusicurl(String hqmusicurl) {
        this.hqmusicurl = hqmusicurl;
    }
    
    

	public List<MessageItem> getItems() {
		return items;
	}

	public void setItems(List<MessageItem> items) {
		this.items = items;
	}

	/**
     * 
     * 客服信息时，组建图文信息子项
     * 
     * @return
     */
    public List<ArticlesItem> toArticlesItem(){
    	List<ArticlesItem> temps = new ArrayList<NewsMessage.ArticlesItem>();
    	NewsMessage.ArticlesItem temp;
    	for(MessageItem item : items){
    		temp = new NewsMessage().new ArticlesItem();
        	temp.setTitle(item.getTitle());
        	temp.setDescription(item.getDescription());
        	temp.setPicurl(item.getPicUrl());
        	temp.setUrl(item.getUrl());
    		temps.add(temp);
    	}
    	return temps;
    	
    }
    
    /**
     * 
     * 被动相应信息时，组建图文信息子项
     * @return
     */
    public List<NewsItem> toNewsItem(){
    	List<NewsItem> temps = new ArrayList<NewsItem>();
    	NewsItem temp;
    	for(MessageItem item : items){
    		temp = new NewsItem();
        	temp.setTitle(item.getTitle());
        	temp.setDescription(item.getDescription());
        	temp.setPicUrl(item.getPicUrl());
        	temp.setUrl(item.getUrl());
    		temps.add(temp);
    	}
    	return temps;
    }
}