package com.ctrip.persistence.service;

import com.ctrip.persistence.entity.*;
import com.ctrip.persistence.enums.MLFlowStatus;
import com.ctrip.persistence.pojo.DataResult;
import com.ctrip.persistence.pojo.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by juntao on 2/14/16.
 * 算法开发 service
 *
 * @author 张峻滔
 */
public interface MLFlowService {
    Map<String, Object> getConf();

    void saveOrUpdateJobFlow(MLFlow flow);
    
    void saveFlow(MLFlow flow);

    Map<String, Object> getConf(Long flowId);
    
    Map<String, Object> getUserConf(Long ownerId);

    List<MLFlow> getAllFlow();

    List<MLFlowHistory> getFlowHistory(Long id);

    Result deleteMLJob(Long id);
    
    MLFlow getById(Long id);
    
    MLFlow getByName(String name);

    Result stopMLJob(Long id);

    /*正常结束*/
    Result finishRunningMLJob(Long id);

    Result getElement(Long id);

    /*保存自定义*/
    void saveCusElementTpl(ElementTpl tpl);

    Result updateElementStatus(Long id, MLFlowStatus status);

    /*从zeppelin中获取 node book 并持久化*/
    DataResult<ZeppelinParagraph> saveReadyZeppelinNote(ZeppelinParagraph paragraph);

    Result updateZeppelinParagraph(Long elementId, ZeppelinParagraph paragraph);

    Result getZeppelinParagraph(Long elementId);

    List<ElementHistory> findByMlFlowId(Long id);
    
    String getNameById(Long id);
    
    Long getOwnerIdByMlFlowId(Long id);
}
