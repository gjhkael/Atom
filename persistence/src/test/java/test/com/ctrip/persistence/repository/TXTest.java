package test.com.ctrip.persistence.repository;

import com.ctrip.persistence.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author juntao zhang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class TXTest {

    @Resource
    private UserService userService;

    @Test
    public void test() {
        try {
            userService.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
