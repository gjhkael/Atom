import com.ctrip.persistence.entity.User;
import com.ctrip.persistence.pojo.DataResult;
import com.ctrip.persistence.pojo.Result;
import com.ctrip.persistence.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author juntao zhang
 */
public class SpringApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:application-context.xml");
        UserService userService = (UserService) context.getBean("userServiceImpl");
        Result result = userService.login("test", "test");
        System.out.println(result.isSuccess());
        System.out.println(((DataResult<User>) result).getData().getCreatedDate());
    }
}
