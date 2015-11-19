package help.mygod.rest.weixin;

import help.mygod.user.entity.User;
import help.mygod.user.service.IUserService;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;
@Component
@Path("/v1/service")
public class ServiceApi {
	
	@Resource
	private IUserService userService;

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public String hello(){
		User user = userService.getUserById(1);
		
		return "hello world" + user.getUserName();
	}
}
