package help.mygod.weixin.service.receive.event;

/**
 * 事件推送event的值
 * @author Administrator
 *
 */
public enum EventEnum {
	/**
	 * 用户未关注时，进行关注后的事件推送
	 */
	SUBSCRIBE,
	/**
	 * 取消关注
	 */
	UNSUBSCRIBE,
	
	/**
	 * 模板信息发送状态
	 */
	TEMPLATESENDJOBFINISH,
	/**
	 * 用户已关注时的事件推送
	 */
	SCAN,
	/**
	 * 上报地理位置事件
	 */
	LOCATION,
	/**
	 * 菜单拉取消息时的事件推送 
	 */
	CLICK,
	/**
	 * 单跳转链接时的事件推送 
	 */
	VIEW,
	/**
	 * 扫码推事件的事件推送 
	 */
	scancode_push,
	/**
	 * 扫码推事件且弹出“消息接收中”提示框的事件推送 
	 */
	scancode_waitmsg ,
	/**
	 * 弹出系统拍照发图的事件推送 
	 */
	pic_sysphoto ,
	/**
	 * 弹出拍照或者相册发图的事件推送 
	 */
	pic_photo_or_album ,
	/**
	 * 弹出微信相册发图器的事件推送 
	 */
	pic_weixin ,
	/**
	 * 地理位置选择器的事件推送
	 */
	location_select
	
}
