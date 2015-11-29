package help.mygod.weixin.client.js;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * weixin ticket
 * @author RD_guowei_liu
 *
 */
@XmlRootElement
public class TicketRsp {
	
	private Integer errcode;
	
	private String errmsg;
	
	private String ticket;
	
	private long expires_in;
	
	/**
	 * 凭证获取时间,单位 毫秒
	 */
	private long create_time = 0;
	

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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	
	
	
	
	
}
