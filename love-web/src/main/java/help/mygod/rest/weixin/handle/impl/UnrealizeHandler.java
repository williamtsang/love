package help.mygod.rest.weixin.handle.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import help.mygod.rest.weixin.handle.KeywordConstant;
import help.mygod.rest.weixin.handle.abs.AbsTextHandler;
import help.mygod.weixin.entity.SendMessage;
import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.WeixinService;
import help.mygod.weixin.service.send.passive.PassiveConverter;

/**
 * 未实现方法的提示
 * @author Administrator
 *
 */
@Component
public class UnrealizeHandler extends AbsTextHandler {
	
	@Autowired
	private WeixinService weixinService;
	
	@Override
	public String handle(BaseMessage baseMessage,Map<String, Object> map) throws Exception{
		
		String temp = "此功能暂未实现，敬请期待！";
		
		List<SendMessage> messages = weixinService.selectSendMessageList(KeywordConstant.REPLY_UNREALIZE);
		if(null != messages && !messages.isEmpty()){
			return sendMessage(baseMessage, messages);
		}
		return PassiveConverter.convertTextMessage(baseMessage, temp);
	}
	
}
