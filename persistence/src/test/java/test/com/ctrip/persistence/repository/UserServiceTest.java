package test.com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.User;
import com.ctrip.persistence.pojo.DataResult;
import com.ctrip.persistence.pojo.Result;
import com.ctrip.persistence.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jzhang
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:application-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void test() {
        User user = new User();
        user.setPassword("jzhangtest");
        user.setUsername("jzhangtest");
        userService.addUser(user);

        Result result = userService.login("jzhangtest", "jzhangtest");
        System.out.println(result.isSuccess());
        System.out.println(((DataResult<User>) result).getData().getCreatedDate());


    }
}
