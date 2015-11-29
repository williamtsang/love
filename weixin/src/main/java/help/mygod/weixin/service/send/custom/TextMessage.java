package help.mygod.weixin.service.send.custom;

import help.mygod.weixin.service.receive.MsgTypeEnum;



/**
 * weixin响应的Text消息
 * @author RD_guowei_liu
 *
 */
public class TextMessage extends CustomMessage{
	
	private TextItem text;
	
	public TextItem getText() {
		return text;
	}


	public void setText(TextItem text) {
		this.text = text;
	}


	public String getMsgtype() {
		return MsgTypeEnum.text.name();
	}

	public class TextItem{
		/**
		 * 文本消息内容
		 */
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
	}
}
