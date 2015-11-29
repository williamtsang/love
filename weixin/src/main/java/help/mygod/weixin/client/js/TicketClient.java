package help.mygod.weixin.client.js;

import help.mygod.weixin.common.access.AccessTokenClient;
import help.mygod.weixin.common.util.RestUtil;

/**
 * 获取ticket
 * 
 * @author RD_guowei_liu
 * 
 */
public class TicketClient {

	private TicketRsp ticket = null;

	public final static String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%1$s&type=jsapi";

	private TicketClient() {

	}

	private static class TicketClientHolder {
		static final TicketClient instance = new TicketClient();
	}

	public static TicketClient getInstance() {
		return TicketClientHolder.instance;
	}

	/**
	 * 获取ticket
	 * 
	 * @return
	 */
	public TicketRsp getTicket() {
		if (null == ticket
				|| (System.currentTimeMillis() - ticket.getCreate_time()) / 1000 > (ticket
						.getExpires_in() / 2)) {
			ticket = createTicket();
		}
		return ticket;
	}

	/**
	 * 生成ticket
	 */
	public TicketRsp createTicket() {
		TicketRsp temp = RestUtil.getJson(
				String.format(GET_TICKET_URL, AccessTokenClient.getInstance()
						.getAccessToken()),TicketRsp.class);
		temp.setCreate_time(System.currentTimeMillis());
		return temp;
	}
	
	public static void main(String[] args) {
		System.out.println(TicketClient.getInstance().getTicket().getTicket());
		System.out.println(TicketClient.getInstance().getTicket().getTicket());
	}

}
