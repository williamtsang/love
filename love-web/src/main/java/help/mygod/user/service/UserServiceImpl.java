package help.mygod.user.service;

import help.mygod.user.dao.UserMapper;
import help.mygod.user.entity.User;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("userService") 
public class UserServiceImpl implements IUserService {
	
	@Resource  
    private UserMapper userDao;  

	@Override
	public User getUserById(int userId) {
		return this.userDao.selectByPrimaryKey(userId);  
	}

}
