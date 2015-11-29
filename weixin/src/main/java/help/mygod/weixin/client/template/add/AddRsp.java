package help.mygod.weixin.client.template.add;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddRsp {

	private Integer errcode;
	private String errmsg;
	private String template_id;
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	
}
