package help.mygod.weixin.service.send.custom;

import help.mygod.weixin.service.receive.MsgTypeEnum;



/**
 * weixin响应的图片消息
 * @author RD_guowei_liu
 *
 */
public class ImageMessage extends CustomMessage{
	
	private ImageItem image;




	public ImageItem getImage() {
		return image;
	}




	public void setImage(ImageItem image) {
		this.image = image;
	}

	public String getMsgtype() {
		return MsgTypeEnum.image.name();
	}


	public class ImageItem{
		/**
		 * 发送的图片的媒体ID
		 */
		private String media_id;

		public String getMedia_id() {
			return media_id;
		}

		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
		
	}
}
