package help.mygod.rest.weixin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import help.mygod.weixin.AbsWebApi;
import help.mygod.weixin.client.js.TicketClient;
import help.mygod.weixin.client.oauth.UserInfoRsp;
import help.mygod.weixin.common.Constant;
import help.mygod.weixin.common.entity.Res;
import help.mygod.weixin.common.util.SignatureUtil;
import help.mygod.weixin.common.util.StrUtil;
import help.mygod.weixin.service.WeixinService;

@Component
@Path("/v1/web/")
public class WebApi extends AbsWebApi {

	private static Logger logger = LogManager.getLogger(WebApi.class);

	@Autowired
	private WeixinService weixinService;

	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	@GET
	@Path("/hello")
	public String hello(@QueryParam("name") String name, @Context UriInfo ui) {
		System.out.println("-------------------");
		System.out.println(request.getParameter("name"));

		System.out.println("-------------------");
		MultivaluedMap<String, String> mm = ui.getQueryParameters();
		for (Map.Entry<String, List<String>> temp : mm.entrySet()) {
			System.out.println(temp.getKey() + " = " + temp.getValue().get(0));
		}

		System.out.println("-------------------");
		System.out.println(mm.get("name"));
		System.out.println(mm.get("number"));
		System.out.println(mm.getFirst("name"));
		System.out.println(mm.getFirst("number"));

		request.getSession().setAttribute("openid", mm.getFirst("openid"));
		try {
			response.sendRedirect("/login.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.hello() + " " + name;
	}

	/**
	 * OAUTH授权时，微信服务器重定向地址
	 * 
	 * @param scheme
	 *            跳转协议 默认为http 可选https
	 * @param code
	 *            微信传递的CODE，用来换取access_token
	 * @param state
	 *            自己填写的校验参数
	 * @param redirectUri
	 *            绑定自动登录后的跳转地址
	 * @param type
	 *            不绑定时的跳转判断，0为跳转到绑定页面，1为跳转到redirectUri（此时没登录）
	 * @throws IOException
	 */
	@GET
	@Path("/weixin")
	@Produces(MediaType.TEXT_PLAIN)
	public void execute(@QueryParam("code") String code,
			@QueryParam("state") String state,
			@QueryParam("redirectUri") String redirectUri,
			@QueryParam("type") int type) throws IOException {
		super.execute(code, state, redirectUri, type, request, response);
	}

	@Override
	public boolean bindByOpenid(String openid) {
		return false;
	}

	/**
	 * OAUTH授权时，微信服务器重定向地址
	 * 
	 * @param scheme
	 *            跳转协议 默认为http 可选https
	 * @param code
	 *            微信传递的CODE，用来换取access_token
	 * @param state
	 *            自己填写的校验参数
	 * @param redirectUri
	 *            绑定自动登录后的跳转地址
	 * @param type
	 *            不绑定时的跳转判断，0为跳转到绑定页面，1为跳转到redirectUri（此时没登录）
	 * @throws IOException
	 */
	@GET
	@Path("/activity")
	@Produces(MediaType.TEXT_PLAIN)
	public void activity(@QueryParam("code") String code,
			@QueryParam("state") String state,
			@QueryParam("redirectUri") String redirectUri, @Context UriInfo ui)
			throws IOException {
		super.activity(code, state, redirectUri, ui.getQueryParameters(),
				request, response);
	}

	@Override
	protected String afterUserInfo(UserInfoRsp userInfo,
			MultivaluedMap<String, String> parameters) {

		return null;
	}


	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Res login(@FormParam("username") String username,
			@FormParam("password") String password) {

		if (!fromWeixin()) {
			return new Res(HttpStatus.SC_BAD_GATEWAY, "请在微信客户端打开!");
		}

		String openid = (String) request.getSession().getAttribute(
				"weixin_openid");

		if (bindByOpenid(openid)) {// 已经绑定 不需要登录 自动登录
			return new Res(HttpStatus.SC_OK, "该微信号已经绑定用户，不需要登录！");
		}

		return new Res(HttpStatus.SC_BAD_REQUEST,"");
	}

	@GET
	@Path("/signature")
	@Produces(MediaType.APPLICATION_JSON)
	public Res signature(@QueryParam("url") String url) {
		try {
			if (StrUtil.isBlank(url)) {
				return new Res(HttpStatus.SC_BAD_REQUEST, "请求参数url不能为空！");
			}

			String jsapi_ticket = TicketClient.getInstance().getTicket()
					.getTicket();
			long timestamp = SignatureUtil.create_timestamp();
			String nonceStr = SignatureUtil.create_nonce_str();
			JSONObject jo = new JSONObject();

			String signature = SignatureUtil.createSignature(nonceStr,
					jsapi_ticket, timestamp, url);
			
			jo.put("appId", Constant.APP_ID);
			jo.put("timestamp", timestamp);
			jo.put("nonceStr", nonceStr);
			jo.put("signature", signature);

			return new Res(HttpStatus.SC_OK,
					"获得jsapi_ticket成功，可以生成JS-SDK权限验证的签名了！", jo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return new Res(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}


	/**
	 * 校验是否来自微信端
	 * 
	 * @return
	 */
	private boolean fromWeixin() {
		return StrUtil.isNotBlank((String) request.getSession().getAttribute(
				"weixin_openid"));
	}

}
