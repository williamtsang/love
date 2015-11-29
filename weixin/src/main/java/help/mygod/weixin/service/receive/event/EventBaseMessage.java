package help.mygod.weixin.service.receive.event;

import help.mygod.weixin.service.BaseMessage;



/**
 * 接受事件推送
 * @author Administrator
 *
 */
public class EventBaseMessage extends BaseMessage{

	/**
	 * 事件类型 class Event
	 */
	private String event;


	public void setEvent(String event) {
		this.event = event;
	}


	public String getEvent() {
		return event;
	}

}
