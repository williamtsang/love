package help.mygod.weixin.client.template.send;


/**
 * 模板消息
 * @author RD_guowei_liu
 *
 */
public class TemplateMessage{
	private String touser;
	private String template_id;
	private String url;
	private String topcolor = "#FF0000";//默认颜色
	private Item data;
	
	
	
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Item getData() {
		return data;
	}

	public void setData(Item data) {
		this.data = data;
	}

	public class Item{
		private NoteItem first;
		private NoteItem keyword1;
		private NoteItem keyword2;
		private NoteItem keyword3;
		private NoteItem keyword4;
		private NoteItem keyword5;
		private NoteItem remark;
		public NoteItem getFirst() {
			return first;
		}
		public void setFirst(NoteItem first) {
			this.first = first;
		}
		public NoteItem getKeyword1() {
			return keyword1;
		}
		public void setKeyword1(NoteItem keyword1) {
			this.keyword1 = keyword1;
		}
		public NoteItem getKeyword2() {
			return keyword2;
		}
		public void setKeyword2(NoteItem keyword2) {
			this.keyword2 = keyword2;
		}
		public NoteItem getKeyword3() {
			return keyword3;
		}
		public void setKeyword3(NoteItem keyword3) {
			this.keyword3 = keyword3;
		}
		public NoteItem getKeyword4() {
			return keyword4;
		}
		public void setKeyword4(NoteItem keyword4) {
			this.keyword4 = keyword4;
		}
		public NoteItem getKeyword5() {
			return keyword5;
		}
		public void setKeyword5(NoteItem keyword5) {
			this.keyword5 = keyword5;
		}
		public NoteItem getRemark() {
			return remark;
		}
		public void setRemark(NoteItem remark) {
			this.remark = remark;
		}
		
		
	}
	
	public class NoteItem{
		private String value;
		private String color = "#173177";//默认颜色
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
	}
}
