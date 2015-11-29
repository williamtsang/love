package help.mygod.weixin.client.oauth;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccessTokenRsp extends RefreshTokenRsp{
	
	private String unionid;
	
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	
}
