package help.mygod.weixin.common.access;

import help.mygod.weixin.common.Constant;
import help.mygod.weixin.common.util.RestUtil;

public class AccessTokenClient{
	
	private  AccessToken token = null;

	public final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
			+ Constant.APP_ID + "&secret=" + Constant.APP_SECRET;

	private AccessTokenClient(){
		
	}
	private static class AccessTokenClientHolder{
			static final AccessTokenClient instance = new AccessTokenClient();
	}
		
	public static AccessTokenClient getInstance(){
		return AccessTokenClientHolder.instance;
	}
	/**
	 * 获取token
	 * @return
	 */
	public  String getAccessToken(){
		if(null == token || (System.currentTimeMillis() - token.getCreate_time())/1000> (token.getExpires_in()/2)){
			token = createAccessToken();
		}
		return token.getAccess_token();
	}
	
	/**
	 * 生成token
	 */
	private AccessToken createAccessToken() {
		AccessToken temp = RestUtil.getJson(GET_ACCESS_TOKEN_URL,AccessToken.class);
		temp.setCreate_time(System.currentTimeMillis());
		return temp;
	}
	
	public static void main(String[] args) {
		System.out.println(AccessTokenClient.getInstance().getAccessToken());
		System.out.println(AccessTokenClient.getInstance().getAccessToken());
	}
}
