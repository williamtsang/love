package help.mygod.user;

import help.mygod.user.entity.User;
import help.mygod.user.service.IUserService;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath*:spring-mybatis.xml" })
public class TestMyBatis {
	private static Logger logger = LogManager.getLogger(TestMyBatis.class.getName());
	// private ApplicationContext ac = null;
	@Resource
	private IUserService userService = null;

	/*
	 * @Before public void before() { ac = new
	 * ClassPathXmlApplicationContext("applicationContext.xml"); userService =
	 * (IUserService) ac.getBean("userService"); }
	 */

	@Test
	public void test1() {
		User user = userService.getUserById(1);
		System.out.println(user.getUserName());
		logger.entry(); // Log entry to a method
		logger.error("Did it again!"); // Log a message object with the ERROR
										// level
		logger.exit();
		logger.info("值：" + user.getUserName());
		// logger.info(JSONUtils.toJSONString(user));
	}
}
