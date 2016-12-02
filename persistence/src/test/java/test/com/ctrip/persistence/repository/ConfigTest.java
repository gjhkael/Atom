package test.com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.ElementTpl;
import com.ctrip.persistence.entity.ElementTplGroup;
import com.ctrip.persistence.entity.ElementTplParam;
import com.ctrip.persistence.entity.ElementTplParamGroup;
import com.ctrip.persistence.repository.ElementTplGroupRepository;
import com.ctrip.persistence.repository.ElementTplParamGroupRepository;
import com.ctrip.persistence.repository.ElementTplParamRepository;
import com.ctrip.persistence.repository.ElementTplRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author juntao zhang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ConfigTest {
    @Resource
    private ElementTplGroupRepository elementTplGroupRepository;
    @Resource
    private ElementTplRepository elementTplRepository;
    @Resource
    private ElementTplParamGroupRepository elementTplParamGroupRepository;
    @Resource
    private ElementTplParamRepository elementTplParamRepository;

    @Test
    public void test() {
        ElementTplParamGroup moduleGroup = new ElementTplParamGroup("module", "模块参数", false, 1);
        ElementTplParamGroup inoutGroup = new ElementTplParamGroup("inout", "输入输出参数", false, 2);
        ElementTplParamGroup sparkGroup = new ElementTplParamGroup("spark", "spark参数", false, 3);
        ElementTplParamGroup otherGroup = new ElementTplParamGroup("other", "其他参数", false, 4);

        elementTplParamGroupRepository.save(moduleGroup);
        elementTplParamGroupRepository.save(inoutGroup);
        elementTplParamGroupRepository.save(sparkGroup);
        elementTplParamGroupRepository.save(otherGroup);

        //====================================   基础工具   ====================================
        ElementTplGroup group = new ElementTplGroup("base", "基础工具", false, 1);
        elementTplGroupRepository.save(group);
        ElementTpl e =
            new ElementTpl("devs.StartState", "开始", 20, 20, true, false, "", "", group.getId(), "");
        elementTplRepository.save(e);
        elementTplParamRepository
            .save(new ElementTplParam(e.getId(), "备注", "无", "textarea", otherGroup.getId(), ""));

        e = new ElementTpl("devs.EndState", "结束", 20, 20, false, true, "", "", group.getId(), "");
        elementTplRepository.save(e);
        elementTplParamRepository
            .save(new ElementTplParam(e.getId(), "备注", "无", "textarea", otherGroup.getId(), ""));

        //====================================   特征工具   ====================================
        group = new ElementTplGroup("feature", "特征工具", true, 2);
        elementTplGroupRepository.save(group);
        e = new ElementTpl("devs.Atomic", "数据抽样", 20, 20, false, false, "in1", "out1,out2",
            group.getId(), "");
        elementTplRepository.save(e);
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", moduleGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", moduleGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", moduleGroup.getId(), "参数3"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", inoutGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", inoutGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", inoutGroup.getId(), "参数3"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", sparkGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", sparkGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", sparkGroup.getId(), "参数3"));

        e = (
            new ElementTpl("devs.Atomic", "Meta数据计算", 20, 20, false, false, "in1", "out1",
                group.getId(), ""));
        elementTplRepository.save(e);
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", moduleGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", moduleGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", moduleGroup.getId(), "参数3"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", inoutGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", inoutGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", inoutGroup.getId(), "参数3"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", sparkGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", sparkGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", sparkGroup.getId(), "参数3"));

        e = (
            new ElementTpl("devs.Atomic", "特征选取", 20, 20, false, false, "in1,in2", "out1",
                group.getId(), ""));
        elementTplRepository.save(e);
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", moduleGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", moduleGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", moduleGroup.getId(), "参数3"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", inoutGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", inoutGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", inoutGroup.getId(), "参数3"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", sparkGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", sparkGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", sparkGroup.getId(), "参数3"));

        e = (
            new ElementTpl("devs.Atomic", "特征规范化", 20, 20, false, false, "in1", "out1",
                group.getId(), ""));
        elementTplRepository.save(e);
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", moduleGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", moduleGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", moduleGroup.getId(), "参数3"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", inoutGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", inoutGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", inoutGroup.getId(), "参数3"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k1", "v1", "text", sparkGroup.getId(), "参数1"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k2", "v2", "text", sparkGroup.getId(), "参数2"));
        elementTplParamRepository.save(new ElementTplParam(e.getId(), "k3", "v3", "text", sparkGroup.getId(), "参数3"));

//        //====================================   算法   ====================================
//        group = (new ElementTplGroup("algorithm", "算法", true, 3));
//        elementTplGroupRepository.save(group);
//        elementTplRepository.save(
//            new ElementTpl("devs.Atomic", "LR回归", 20, 20, false, false, "in1", "out1",
//                group.getId(), ""));
//        elementTplRepository.save(
//            new ElementTpl("devs.Atomic", "朴素贝叶斯", 20, 20, false, false, "in1", "out1",
//                group.getId(), ""));
//
//
//        //====================================   模型检验   ====================================
//        group = (new ElementTplGroup("modelchecking", "模型检验", true, 4));
//        elementTplGroupRepository.save(group);
//        elementTplRepository.save(
//            new ElementTpl("devs.Atomic", "二分检验", 20, 20, false, false, "in1,in2", "out1,out2",
//                group.getId(), ""));
//        elementTplRepository.save(
//            new ElementTpl("devs.Atomic", "多分检验", 20, 20, false, false, "in1", "out1",
//                group.getId(), ""));
//        elementTplRepository.save(
//            new ElementTpl("devs.Atomic", "多标签检验", 20, 20, false, false, "in1,in2", "out1",
//                group.getId(), ""));
//
//        elementTplRepository.save(
//            new ElementTpl("devs.Atomic", "回归模型检验", 20, 20, false, false, "in1", "out1",
//                group.getId(), ""));
//
//        //====================================   自定义   ====================================
//        group = (new ElementTplGroup("custom", "自定义", true, 5));
//        elementTplGroupRepository.save(group);
//        elementTplRepository.save(
//            new ElementTpl("devs.Atomic", "spark", 20, 20, false, false, "in1", "out1",
//                group.getId(), ""));

    }


//    @Test
//    public void test1() {
//        ElementTplGroup group = (new ElementTplGroup("custom", "自定义", true, 5));
//        elementTplGroupRepository.save(group);
//    }

}
