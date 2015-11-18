package help.mygod.user.controller;

import help.mygod.user.entity.User;
import help.mygod.user.service.IUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userController")
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService; 
	
	@RequestMapping("/showUser")
	public String showUser(HttpServletRequest request,Model model){
		
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
		
	}
	

}
