package help.mygod.weixin.client.template.send;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SendRsp {

	private Integer errcode;
	private String errmsg;
	private Long msgid;
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
	public Long getMsgid() {
		return msgid;
	}
	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}
	
}
