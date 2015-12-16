package help.mygod.weixin.common.util;

import java.util.concurrent.Executors;

import help.mygod.weixin.client.oauth.AccessTokenClient;
import help.mygod.weixin.client.oauth.AccessTokenRsp;
import help.mygod.weixin.client.oauth.RefreshTokenClient;
import help.mygod.weixin.client.oauth.UserInfoClient;
import help.mygod.weixin.client.oauth.UserInfoRsp;





/**
 * oauth
 *
 * @author peiyu
 */
public final class OauthUtil {

    /**
     * 此类不需要实例化
     */
    private OauthUtil() {
    }
    
    public static String getOpenId(String code){
    	
    	if(StrUtil.isBlank(code))
    		return null;
    	
    	return AccessTokenClient.get(code).getOpenid();
    	
    }
    
    public static UserInfoRsp getUserInfo(String code){
    	final AccessTokenRsp at = AccessTokenClient.get(code);
    	
    	Executors.newCachedThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				RefreshTokenClient.get(at.getRefresh_token());
			}
		});
    	
    	return UserInfoClient.get(at.getAccess_token(), at.getOpenid());
    	
    }
}