package help.mygod.rest.weixin;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import help.mygod.rest.weixin.handle.impl.SubscribeHandler;
import help.mygod.rest.weixin.handle.impl.TextHandler;
import help.mygod.rest.weixin.handle.impl.UnrealizeHandler;
import help.mygod.rest.weixin.handle.impl.UnsubscribeHandler;
import help.mygod.weixin.service.Handlers;
import help.mygod.weixin.service.receive.MsgTypeEnum;
import help.mygod.weixin.service.receive.event.EventEnum;
import help.mygod.weixin.service.receive.event.menu.EventKeyEnum;

public class InitHandle implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext appctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		// TODO Auto-generated method stub
		Handlers.registerHanlder(EventKeyEnum.KEY_UNREALIZE.name().toUpperCase(), (UnrealizeHandler)appctx.getBean("unrealizeHandler"));//默认回复信息
		Handlers.registerHanlder(MsgTypeEnum.text.name().toUpperCase(), (TextHandler)appctx.getBean("textHandler"));//默认回复文本信息
		Handlers.registerHanlder(EventEnum.SUBSCRIBE.name().toUpperCase(), (SubscribeHandler)appctx.getBean("subscribeHandler"));//微信关注
		Handlers.registerHanlder(EventEnum.UNSUBSCRIBE.name().toUpperCase(), (UnsubscribeHandler)appctx.getBean("unsubscribeHandler"));//微信取消关注
		/*Handlers.registerHanlder(EventEnum.TEMPLATESENDJOBFINISH.name().toUpperCase(), new TemplatesendjobfinishHandler());//微信取消关注
		
		
		Handlers.registerHanlder(EventKeyEnum.KEY_USER_BIND.name().toUpperCase(), new UserBindHandler());//自定义绑定帐号
*/
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
