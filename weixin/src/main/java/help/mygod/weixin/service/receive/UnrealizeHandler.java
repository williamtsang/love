package help.mygod.weixin.service.receive;

import java.util.Map;

import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.IHandler;
import help.mygod.weixin.service.send.passive.PassiveConverter;

/**
 * 未实现方法的提示
 * @author Administrator
 *
 */
public class UnrealizeHandler implements IHandler {
	
	@Override
	public String handle(BaseMessage baseMessage,Map<String, Object> map) throws Exception{
		return PassiveConverter.convertTextMessage(baseMessage, "梦想还是有的，我们帮你实现！");
	}
	
	
}
