package help.mygod.rest.weixin.handle.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import help.mygod.weixin.common.Constant;
import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.IHandler;

/**
 * 取消关注事件
 * @author Administrator
 *
 */
@Component
public class UnsubscribeHandler implements IHandler {

	/**
	  * 用户取消关注公众号回复
	  */
	@Override
	public String handle(BaseMessage baseMessage, Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return Constant.EMPTY_STRING;
	}


}
