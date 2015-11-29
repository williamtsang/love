package help.mygod.weixin.service.send.custom;

import java.util.List;

import help.mygod.weixin.service.receive.MsgTypeEnum;



/**
 * weixin响应的图文消息
 * @author RD_guowei_liu
 *
 */
public class NewsMessage extends CustomMessage{
	
	private NewsItem news;


	public NewsItem getNews() {
		return news;
	}

	public void setNews(NewsItem news) {
		this.news = news;
	}

	public String getMsgtype() {
		return MsgTypeEnum.news.name();
	}


	public class NewsItem{
		/**
		 * 图文消息
		 */
		private List<ArticlesItem> articles;

		public List<ArticlesItem> getArticles() {
			return articles;
		}

		public void setArticles(List<ArticlesItem> articles) {
			this.articles = articles;
		}

	}
	
	public class ArticlesItem{
		private String title;
		private String description;
		private String url;
		private String picurl;
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
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getPicurl() {
			return picurl;
		}
		public void setPicurl(String picurl) {
			this.picurl = picurl;
		}
		
		
	}
}
