package help.mygod.weixin.service.receive;
/**
 * wei xin消息类型
 * @author RD_guowei_liu
 *
 */
/**
 * @author Administrator
 *
 */
public enum MsgTypeEnum {
	/**
	 * 文本消息
	 */
	text,
	/**
	 * 图片消息
	 */
	image,
	/**
	 * 语音消息
	 */
	voice,
	/**
	 * 视频消息
	 */
	video,
	/**
	 * 小视频消息
	 */
	shortvideo,
	/**
	 * 地理位置消息
	 */
	location,
	/**
	 * 链接消息
	 */
	link,
	/**
	 * 音乐消息
	 */
	music,
	/**
	 * 图文消息
	 */
	news,
	
	/**
	 * 用户点击自定义菜单
	 */
	event
}
