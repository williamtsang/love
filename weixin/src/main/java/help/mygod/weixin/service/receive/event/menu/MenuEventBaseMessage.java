package help.mygod.weixin.service.receive.event.menu;

import help.mygod.weixin.service.receive.event.EventBaseMessage;



/**
 * 自定义菜单基类
 * 
 */
public class MenuEventBaseMessage extends EventBaseMessage{


	/**
	 * 事件KEY值，与自定义菜单接口中KEY值对应
	 */
	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}
