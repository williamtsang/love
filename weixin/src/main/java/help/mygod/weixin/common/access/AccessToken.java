package help.mygod.weixin.common.access;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccessToken {
	/**
	 * 获取到的凭证 
	 */
	private String access_token;
	
	/**
	 * 凭证有效时间，单位：秒 
	 */
	private long expires_in;
	
	/**
	 * 凭证获取时间,单位 毫秒
	 */
	private long create_time;

	
	
	
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
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
