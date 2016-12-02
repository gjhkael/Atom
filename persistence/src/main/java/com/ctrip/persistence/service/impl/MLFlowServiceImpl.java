package com.ctrip.persistence.service.impl;

import com.ctrip.persistence.entity.*;
import com.ctrip.persistence.enums.MLFlowStatus;
import com.ctrip.persistence.pojo.DataResult;
import com.ctrip.persistence.pojo.Result;
import com.ctrip.persistence.repository.*;
import com.ctrip.persistence.service.MLFlowService;
import com.ctrip.persistence.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by juntao on 2/14/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class MLFlowServiceImpl implements MLFlowService {
    private static final Logger LOG = LoggerFactory.getLogger(MLFlowServiceImpl.class);
    @Resource
    private MLFlowRepository flowRepository;
    @Resource
    private ElementRepository elementRepository;
    @Resource
    private ElementParamRepository elementParamRepository;
    @Resource
    private LinkRepository linkRepository;
    @Resource
    private ElementTplGroupRepository elementTplGroupRepository;
    @Resource
    private ElementTplParamGroupRepository elementTplParamGroupRepository;
    @Resource
    private ElementTplParamRepository elementTplParamRepository;
    @Resource
    private ElementTplRepository elementTplRepository;
    @Resource
    private ZeppelinParagraphRepository zeppelinParagraphRepository;
    @Resource
    private UserService userService;
    @Resource
    private MLFlowHistoryRepository mlFlowHistoryRepository;
    @Resource
    private ElementParamHistoryRepository elementParamHistoryRepository;
    @Resource
    private LinkHistoryRepository linkHistoryRepository;
    @Resource
    ElementHistoryRepository elementHistoryRepository;

    @Override
    public Map<String, Object> getConf() {
        Map<String, Object> res = Maps.newHashMap();
        res.put("elementTplParamGroup", elementTplParamGroupRepository.findAll());
        res.put("elementTplGroup", elementTplGroupRepository.findAll());
        res.put("elementTplParam", elementTplParamRepository.findAll());
        res.put("elementTpl", elementTplRepository.findAll());
        return res;
    }
    
    @Override
    public Map<String, Object> getUserConf(Long ownerId) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("elementTplParamGroup", elementTplParamGroupRepository.findAll());
        res.put("elementTplGroup", elementTplGroupRepository.findAll());
        res.put("elementTplParam", elementTplParamRepository.findAll());
        List<ElementTpl> elementTpls = new ArrayList<ElementTpl>();
        elementTpls.addAll(elementTplRepository.findByOwnerId(ownerId));
        elementTpls.addAll(elementTplRepository.findByOwnerId(null));
        res.put("elementTpl", elementTpls);
        return res;
    }
    

    @Override
    public Map<String, Object> getConf(Long flowId) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("flow", flowRepository.findOne(flowId));
        res.put("element", elementRepository.findByMlFlowId(flowId));
        res.put("elementParam", elementParamRepository.findByMlFlowId(flowId));
        res.put("link", linkRepository.findByMlFlowId(flowId));
        res.put("elementTplParamGroup", elementTplParamGroupRepository.findAll());
        res.put("elementTplGroup", elementTplGroupRepository.findAll());
        res.put("elementTplParam", elementTplParamRepository.findAll());
        res.put("elementTpl", elementTplRepository.findAll());
        return res;
    }

    @Override
    public List<MLFlow> getAllFlow() {
        List<MLFlow> res = flowRepository.findAll();
        Collections.sort(res, new Comparator<MLFlow>() {
            @Override
            public int compare(MLFlow o1, MLFlow o2) {
                return (int) (o1.getCreatedDate().getTime() - o2.getCreatedDate().getTime());
            }
        });
        userService.setUserInfo(res);
        return res;
    }

    @Override
    public List<MLFlowHistory> getFlowHistory(Long id) {
        List<MLFlowHistory> res = mlFlowHistoryRepository.findByMlFlowId(id);
        return res;
    }

    //数据库级联删除
    @Override
    public Result deleteMLJob(Long id) {
        flowRepository.delete(id);
        
        return new Result(true);
    }

    @Override
    public Result stopMLJob(Long id) {
        MLFlow flow = flowRepository.findOne(id);
        if (flow.getStatus() != MLFlowStatus.RUNNING) {
            return new Result("this job is not running!", false);
        }
        flow.setStatus(MLFlowStatus.STOP);
        flowRepository.save(flow);
        return new DataResult<>(flow).setSuccess(true);
    }

    @Override
    public Result finishRunningMLJob(Long id) {
        MLFlow flow = flowRepository.findOne(id);
        if (flow.getStatus() != MLFlowStatus.RUNNING) {
            return new Result("this job is not running!", false);
        }
        flow.setStatus(MLFlowStatus.FINISHED);
        flowRepository.save(flow);
        return new DataResult<>(flow).setSuccess(true);
    }

    @Transactional
    @Override
    public void saveCusElementTpl(ElementTpl tpl) {
        ElementTplGroup group = elementTplGroupRepository.findByName("custom");
        tpl.setGroupId(group.getId());
        ElementTplParamGroup g = elementTplParamGroupRepository.findByName("other");
        elementTplRepository.save(tpl);
        ElementTplParam param =
                new ElementTplParam(tpl.getId(), "code", tpl.getCode(), "textarea", g.getId(), "");
        elementTplParamRepository.save(param);
        tpl.setParams(Lists.newArrayList(param));
    }

    @Override
    public Result updateElementStatus(Long id, MLFlowStatus status) {
        Element element = elementRepository.findOne(id);
        element.setStatus(status);
        elementRepository.save(element);
        return new Result(true);
    }

    @Override
    public DataResult<ZeppelinParagraph> saveReadyZeppelinNote(ZeppelinParagraph paragraph) {
        ZeppelinParagraph tmp = zeppelinParagraphRepository.findByUuid(paragraph.getUuid());
        //如果不存在
        if (tmp == null) {
            zeppelinParagraphRepository.save(paragraph);
        }
        return new DataResult<>(true, paragraph);
    }
    
    @Override
    public List<ElementHistory> findByMlFlowId(Long id){
        return elementHistoryRepository.findByMlFlowId(id);
    }

    @Override
    public Result updateZeppelinParagraph(Long elementId, ZeppelinParagraph paragraph) {
        ZeppelinParagraph p = zeppelinParagraphRepository.findByElementId(elementId);
        p.from(paragraph);
        zeppelinParagraphRepository.save(p);
        return new DataResult<ZeppelinParagraph>().setData(p).setSuccess(true);
    }

    @Override
    public Result getZeppelinParagraph(Long elementId) {
        ZeppelinParagraph p = zeppelinParagraphRepository.findByElementId(elementId);
        if (p != null)
            return new DataResult<>(true, p);
        List<ZeppelinParagraph> ps = zeppelinParagraphRepository.findByStatus("READY");
        if (CollectionUtils.isEmpty(ps)) {
            return new Result("ready paragraph is empty", false);
        }
        p = ps.get(0);
        p.setElementId(elementId);
        zeppelinParagraphRepository.save(p);
        return new DataResult<>(true, p);
    }

    @Override
    public Result getElement(Long id) {
        return new DataResult<Element>(true, elementRepository.findOne(id));
    }


    @Transactional
    @Override
    public void saveOrUpdateJobFlow(MLFlow flow) {
        Map<String, Element> uuidEleMap = Maps.newHashMap();
        Map<Long, Link> deletedLinkMap = Maps.newHashMap();
        Map<Long, Element> deletedElementMap = Maps.newHashMap();
        Map<Long, ElementParam> deletedElementParamMap = Maps.newHashMap();
        if (flow.getId() != null) {
            MLFlow entity = flowRepository.findOne(flow.getId());
            entity.from(flow);
            flowRepository.save(entity);
            for (Element e : elementRepository.findByMlFlowId(flow.getId())) {
                deletedElementMap.put(e.getId(), e);
            }
            for (ElementParam e : elementParamRepository.findByMlFlowId(flow.getId())) {
                deletedElementParamMap.put(e.getId(), e);
            }
            for (Link e : linkRepository.findByMlFlowId(flow.getId())) {
                deletedLinkMap.put(e.getId(), e);
            }

        } else {
            flow.setStatus(MLFlowStatus.STOP);
            flowRepository.save(flow);
        }
        for (Element element : flow.getElements()) {
            deletedElementMap.remove(element.getId());
            // associate with MLFlow
            element.setMlFlowId(flow.getId());
          //  element.setText("test");//TODO 需要界面生成
            if (element.getId() != null) {
                Element entity = elementRepository.findOne(element.getId());
                entity.from(element);
                elementRepository.save(entity);
            } else {
                elementRepository.save(element);
            }
            for (ElementParam param : element.getParams()) {
                deletedElementParamMap.remove(param.getId());
                // associate with MLFlow
                param.setMlFlowId(flow.getId());
                param.setElementId(element.getId());
                if (param.getId() != null) {
                    ElementParam entity = elementParamRepository.findOne(param.getId());
                    entity.setValue(param.getValue());
                    elementParamRepository.save(entity);
                    param.from(entity);
                } else {
                    elementParamRepository.save(param);
                }
            }
            uuidEleMap.put(element.getUuid(), element);
        }
        for (Link link : flow.getLinks()) {
            deletedLinkMap.remove(link.getId());
            // associate with MLFlow
            link.setMlFlowId(flow.getId());
            link.setSourceId(uuidEleMap.get(link.getSourceUuid()).getId());
            link.setTargetId(uuidEleMap.get(link.getTargetUuid()).getId());
            if (link.getId() != null) {
                Link entity = linkRepository.findOne(link.getId());
                entity.from(link);
                linkRepository.save(entity);
            } else {
                linkRepository.save(link);
            }
        }
        //删除界面的线 元素 元素属性
        Collection<Link> deletedLinks = deletedLinkMap.values();
        if (CollectionUtils.isNotEmpty(deletedLinks)) {
            linkRepository.delete(deletedLinks);
        }
        Collection<ElementParam> deletedElementParams = deletedElementParamMap.values();
        if (CollectionUtils.isNotEmpty(deletedElementParams)) {
            elementParamRepository.delete(deletedElementParams);
        }
        Collection<Element> deletedElements = deletedElementMap.values();
        if (CollectionUtils.isNotEmpty(deletedElements)) {
            elementRepository.delete(deletedElements);
        }
    }


	@Override
	public String getNameById(Long id) {
		// TODO Auto-generated method stub
		return flowRepository.getNameById(id);
	}

	@Override
	public Long getOwnerIdByMlFlowId(Long id) {
		// TODO Auto-generated method stub
		return flowRepository.getOwnerIdByMlFlowId(id);
	}

	@Override
	public MLFlow getById(Long id) {
		// TODO Auto-generated method stub
		return flowRepository.findOne(id);
	}
	
	@Override
	public MLFlow getByName(String name) {
		// TODO Auto-generated method stub
		return flowRepository.findByName(name);
	}

	@Override
	public void saveFlow(MLFlow flow) {
		// TODO Auto-generated method stub
		flowRepository.save(flow);
	}

}