//package test.com.ctrip.persistence.repository;
//
//import com.ctrip.persistence.entity.ModuleHistory;
//import com.ctrip.persistence.entity.ModuleHistoryPK;
//import com.ctrip.persistence.entity.User;
//import com.ctrip.persistence.repository.ModuleHistoryRepository;
//import com.ctrip.persistence.repository.UserRepository;
//
//
//
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//
///**
// * @author jzhang
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:application-context.xml")
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
//public class UserRepositoryTest {
//    @Resource
//    private ModuleHistoryRepository moduleHistoryRepository;
//    @Resource
//    private UserRepository userRepository;
//
//
//    public UserRepositoryTest setUserRepository(ModuleHistoryRepository moduleHistoryRepository, UserRepository userRepository) {
//        this.moduleHistoryRepository = moduleHistoryRepository;
//        this.userRepository = userRepository;
//        return this;
//    }
//
//    @Test
//    public void test() {
//    	ModuleHistory module = new ModuleHistory();
////        user.setUsername("test_123");
////        user.setPassword("test_123");
////        userRepository.save(user)
//      //  module = moduleHistoryRepository.findOne(spec)
//    	ModuleHistoryPK key=new ModuleHistoryPK("1",1);
////        module = moduleHistoryRepository.findOne(key);
//    	module = moduleHistoryRepository.findOne(key);
//    //    module = moduleHistoryRepository.findByUuidAndScheduleTimes("1", 2);
//        System.out.println(module.getLogDir());
//    }
//
//    @Test
//    public void testUser() {
//    	User user = new User();
////        user.setUsername("test_123");
////        user.setPassword("test_123");
////        userRepository.save(user)
//        user = userRepository.findByUsername("test");
//        
//        System.out.println(user.getPassword());
//    }
//}
