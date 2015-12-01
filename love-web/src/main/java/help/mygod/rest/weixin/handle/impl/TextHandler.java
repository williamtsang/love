package help.mygod.rest.weixin.handle.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import help.mygod.rest.weixin.handle.abs.AbsTextHandler;
import help.mygod.weixin.common.util.StrUtil;
import help.mygod.weixin.entity.SendMessage;
import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.Handlers;
import help.mygod.weixin.service.WeixinService;
import help.mygod.weixin.service.receive.event.menu.EventKeyEnum;

/**
 * 接收文本信息的处理方法
 * @author Administrator
 *
 */
@Component
public class TextHandler extends AbsTextHandler {
	
	@Autowired
	private WeixinService weixinService;
	
	@Override
	public String handle(BaseMessage baseMessage,Map<String, Object> map) throws Exception{
		
		String content = (String)map.get("Content");
		
		if(StrUtil.isNotBlank(content)){
			List<SendMessage> messages = weixinService.selectSendMessageList(content);
			if(null != messages && !messages.isEmpty()){
				return sendMessage(baseMessage, messages);
			}
		}
		
		return Handlers.getHandlers().get(EventKeyEnum.KEY_UNREALIZE.name().toUpperCase()).handle(baseMessage,map);
	}
	
}
