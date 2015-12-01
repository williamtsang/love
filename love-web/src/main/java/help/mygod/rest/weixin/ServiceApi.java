package help.mygod.rest.weixin;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import help.mygod.weixin.AbsServiceApi;

@Path("/v1/service")
public class ServiceApi extends AbsServiceApi {
	
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	@GET
	@Path("/hello")
	public String hello(){
		return super.hello() + " hello";
	}
	
	@GET
	@Path("/weixin")
	@Produces(MediaType.TEXT_PLAIN)
	public String service() {
		return super.service(request);
	}
	
	
	/**微信服务总入口
	 * @param req
	 * @return
	 */
	@POST
	@Path("/weixin")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public String service(String xmlText) {
		return super.service(xmlText);
	}
	
	
}
