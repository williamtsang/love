package help.mygod.weixin.client.template.add;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import help.mygod.weixin.common.access.AccessTokenClient;
import help.mygod.weixin.common.util.RestUtil;
import help.mygod.weixin.common.util.StrUtil;

/**
 * 
 *获得模板ID
 * @author Administrator
 *
 */
public class AddClient {
	private static Logger logger = LogManager.getLogger(AddClient.class.getName());
	/**
	 * API地址
	 */
	public final static String TEMPLATE_ADD_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";
	

	public static String post(Short temp) throws HttpException, IOException{
		if(logger.isInfoEnabled()){
			logger.info("获得模板ID，请求的参数为:" + StrUtil.reflectObj(temp));
		}
		AddRsp addRsp = RestUtil.postJson(TEMPLATE_ADD_URL + AccessTokenClient.getInstance().getAccessToken(), JSONObject.toJSONString(temp),AddRsp.class);
		
		if(null == addRsp)
			return null;
		return addRsp.getTemplate_id();
		
	}
}
