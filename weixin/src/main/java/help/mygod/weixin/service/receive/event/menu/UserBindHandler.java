package help.mygod.weixin.service.receive.event.menu;

import java.util.Map;

import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.IHandler;
import help.mygod.weixin.service.send.passive.PassiveConverter;

/**
 * 自定义菜单中绑定帐户点击事件类
 * 
 * @author
 * 
 */
public class UserBindHandler implements IHandler{

	@Override
	public String handle(BaseMessage baseMessage, Map<String, Object> map) throws Exception {
		return PassiveConverter.convertTextMessage(baseMessage, "自定义菜单之用户绑定！");
	}

	
}
