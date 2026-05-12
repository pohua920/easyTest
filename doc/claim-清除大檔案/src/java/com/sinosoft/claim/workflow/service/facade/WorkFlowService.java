package com.sinosoft.claim.workflow.service.facade;

import ins.framework.common.Page;

import java.util.List;
import java.util.Map;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案

import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogStore;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.model.SwfPathLogStore;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.StatStatusDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.undwrt.dto.custom.SubmitTaskDto;

/**
 * 工作流处理接口
 * @author 中科软
 *
 */
public interface WorkFlowService {
    /**
     * 获取当前系统工作流模板号，从模板分配表中，利用险种和所属机构
     * @param riskCode 险种
     * @param comCode 机构
     * @throws Exception
     * @return int
     */
    public int getModelNo(String riskCode, String comCode) throws Exception;

    /**
     * 获取swfLog表当前flowID相同的最大的LogNo 的maxNo
     * @param flowID 工作流号
     * @throws Exception
     * @return int
     */
    public int getSwfLogMaxLogNo(String flowID) throws Exception;

    /**
     * 获取swfLog表当前flowID相同的最大的LogNo 的maxNo
     * @param flowID 工作流号
     * @throws Exception
     * @return int
     */
    public int getSwfLogStoreMaxLogNo(String flowID) throws Exception;

    /**
     * 获取swfPathLog表当前最大的PathNo 的maxNo
     * @param flowID 工作流号 String
     * @throws Exception
     * @return int
     */
    public int getSwfPathLogMaxPathNo(String flowID) throws Exception;

    /**
     * 获取swfPathLogStore表当前最大的PathNo 的maxNo
     * @param flowID 工作流号
     * @throws Exception
     * @return int
     */
    public int getSwfPathLogStoreMaxPathNo(String flowID) throws Exception;

    /**
     * 创建工作流程
     * @param workFlowDto
     * @throws Exception
     * @return String
     */
    public String createFlow(WorkFlowDto workFlowDto) throws Exception;

    /**
     * 处理整个工作流程(这个是整个工作流处理的基础)
     * @param workFlowDto 处理的工作流对象
     * @throws Exception
     */
    public void deal(WorkFlowDto workFlowDto) throws Exception;

	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
     * 处理整个工作流程(这个是整个工作流处理的基础)
     * @param workFlowDto 处理的工作流对象
     * @throws Exception
     */
    public void deal4Ws(WorkFlowDto workFlowDto, HttpSession session) throws Exception;
    
    /**
     * 修改工作流本身的状态信息
     * @param swfLog 变量定义和内容
     * @throws Exception
     */
    public void updateFlow(SwfLog swfLog) throws Exception;

    /**
     * 完成工作流节点，並向下一个节点流转
     * @param workFlowDto
     * @throws Exception
     * @return String
     */
    public String submitNode(WorkFlowDto workFlowDto) throws Exception;

    /**
     * 检查工作流是否关闭
     * @param flowID 工作流号
     * @throws Exception
     * @return boolean
     */
    public boolean checkFlowClose(String flowID) throws Exception;

    /**
     * 根据流程节点的流程号和logNo查询具体信息
     * @param flowID 工作流号
     * @param logNo 序号
     * @throws Exception
     * @return SwfLog
     */
    public SwfLog findNodeByPrimaryKey(String flowID, int logNo) throws Exception;

    /**
     * 根据流程号码查询wfflowMain的具体信息
     * @param flowID 工作流号
     * @throws Exception
     * @return SwfFlowMain
     */
    public SwfFlowMain findFlowMainByPrimaryKey(String flowID) throws Exception;

    /**
     * 查找符合条件的流程节点信息
     * @param condition 条件
     * @throws Exception
     * @return List<SwfLog>
     */
    public List<SwfLog> findNodesByConditions(String condition) throws Exception;

    /**
     * 查找符合条件的流程节点信息
     * @param condition 条件
     * @throws Exception
     * @return List<SwfLog>
     */
    public List<SwfLog> findStoreNodesByConditions(String condition) throws Exception;

    /**
     * 查找符合条件的流程节点信息(翻页)
     * @param condition 条件
     * @param pageNo 页数
     * @param pageSize 每页条数
     * @throws Exception
     * @return Page
     */
    public Page findNodesByConditions(String condition, int pageNo, int pageSize) throws Exception;

    /**
     * 查找符合条件的流程节点信息(翻页)
     * @param condition 条件
     * @param pageNo 页数
     * @param pageSize 每页条数
     * @throws Exception
     * @return Page
     */
    public Page findStoreNodesByConditions(String condition, int pageNo, int pageSize) throws Exception;

    /**
     * 查找当前处理的节点的节点信息
     * @param BussinessNo 业务号
     * @param nodeType 节点类型
     * @throws Exception
     * @return List<SwfLog>
     */
    public List<SwfLog> findCurrentNode(String BussinessNo, String nodeType) throws Exception;

    /**
     * 查找当前处理的节点的节点信息
     * @param flowID 工作流号
     * @param logNo 序号
     * @throws Exception
     * @return List<SwfLog>
     */
    public List<SwfLog> findCurrentNode(String flowID, int logNo) throws Exception;

    /**
     * 查找当前流程的节点中是否存在NodeNo相同，並且状态为0未处理的Log节点
     * @param flowID 工作流号
     * @param nodeNo 工作流节点号
     * @param nodeType 节点类型
     * @throws Exception
     * @return List<SwfLog>
     */
    public List<SwfLog> findNoDealNodeByModelNodeNo(String flowID, int nodeNo, String nodeType) throws Exception;

    /**
     * 查找当前流程的节点中是否存在NodeNo相同，並且状态为0未处理的Log节点(人到人的方式下的)
     * @param flowID 工作流号
     * @param nodeNo 工作流节点号
     * @param nodeType 节点类型
     * @param policyNo 保单号
     * @throws Exception
     * @return List<SwfLog>
     */
    public List<SwfLog> findNoDealNodeByModelNodeNoByPerson(String flowID, int nodeNo, String nodeType, String policyNo) throws Exception;

    /**
     * 查找符合一个工作流上的所有节点信息
     * @param null
     * @throws Exception
     * @return String
     */
    public List<SwfLog> findNodesByFlowID(String flowID) throws Exception;

    /**
     * 查找符合一个工作流上的所有节点信息
     * @param flowID 工作流号
     * @throws Exception
     * @return String
     */
    public List<SwfLogStore> findStoreNodesByFlowID(String flowID) throws Exception;

    /**
     * 查找模板的一个节点的详细信息
     * @param modelNo 模板号
     * @param nodeNo 模板上的节点号
     * @throws Exception
     * @return String
     */
    public SwfNode findModelNodeByPrimaryKey(int modelNo, int nodeNo) throws Exception;

    /**
     * 查找模板的第一个符合条件的节点的详细信息
     * @param condition 查询条件
     * @throws Exception
     * @return String
     */
    public SwfNode findModelFirstNodeByCondition(String condition) throws Exception;

    /**
     * 查找模板的符合节点类型的第一个节点的详细信息
     * @param modelNo 模板号
     * @param nodeType 节点类型
     * @throws Exception
     * @return String
     */
    public SwfNode findModelNodeByNodeType(int modelNo, String nodeType) throws Exception;

    /**
     * 查找模板的下多个节点的详细信息
     * @param modelNo int 模板号码
     * @param nodeNo int 模板上的节点号码
     * @param iBusinessNo String 当前业务号码
     * @throws Exception
     * @return Collection
     */
    public List<SwfNode> findModelNextNodes(int modelNo, int nodeNo, String iBusinessNo) throws Exception;

    /**
     * 查找模板的下多个节点的详细信息
     * @param modelNo int 模板号码
     * @param nodeNo int 模板上的节点号码
     * @param iBusinessNo String 当前业务号码
     * @throws Exception
     * @return List<SwfPath>
     */
    public List<SwfPath> findModelPathNodes(String nodeConditions) throws Exception;

    /**
     * 寻找nodeNo为当前NodeNo的T类型的节点
     * @param modelNo int 模板号码
     * @param nodeNo int 当前的NodeNo的值
     * @throws Exception
     * @return List<SwfNode>
     */
    public List<SwfNode> findModelNextTNodes(int modelNo, int nodeNo) throws Exception;

    /**
     * 查找模板的上多个节点的详细信息
     * @param modelNo 模板号
     * @param nodeNo 模板上的节点号
     * @throws Exception
     * @return List<SwfNode>
     */
    public List<SwfNode> findModelPerviousNodes(int modelNo, int nodeNo) throws Exception;

    /**
     * 查找工作流的某点之前上多个节点的详细信息
     * @param flowID 工作流号
     * @param logNo 序号
     * @throws Exception
     * @return String
     */
    public List<SwfLog> findPerviousNodes(String flowID, int logNo) throws Exception;

    /**
     * 根据业务号查询工作流流程日志信息
     * @param businessNo 序号
     * @throws Exception
     * @return List<SwfLog>
     */
    public List<SwfLog> findFlowLogByBuessionNo(String businessNo) throws Exception;

    /**
     * 根据流程号查询工作流流程路径信息
     * @param flowID 工作流号 String
     * @throws Exception
     * @return List<SwfPathLog>
     */
    public List<SwfPathLog> findFlowPathLogByFlowID(String flowID) throws Exception;

    /**
     * 根据流程号查询工作流流程路径信息
     * @param flowID 工作流号 String
     * @throws Exception
     * @return List<SwfPathLogStore>
     */
    public List<SwfPathLogStore> findStoreFlowPathLogByFlowID(String flowID) throws Exception;

    /**
     * 检验是不是满足路径上的条件
     * @param wfPathDto WfPathDto
     * @param iBusinessNo String
     * @throws Exception
     * @return boolean
     */
    public boolean checkPathCondition(SwfPath swfPath, String iBusinessNo) throws Exception;

    /**
     * 根据流程号和节点进行独占操作
     * @param flowID 工作流号 String
     * @param LogNo int
     * @throws Exception
     * @return SwfLog
     */
    public SwfLog holdNode(String flowID, int logNo, String userCode, String userName) throws Exception;

    /**
     * 根据流程号和节点进行释放操作
     * @param flowID 工作流号 String
     * @param LogNo int
     * @throws Exception
     */
    public void freeNode(String flowID, int logNo) throws Exception;

    /**
     * 根据流程号和节点判断用户是否具有独占操作
     * @param flowID 工作流号 String
     * @param LogNo int
     * @param userCode String
     * @throws Exception
     * @return SwfLog
     */
    public SwfLog findHoldNode(String flowID, int logNo, String userCode) throws Exception;

    /**
     * 统计工作流节点状态数量的功能
     * @param conditions String
     * @throws Exception
     * @return List<StatStatusDto>
     */
    public List<StatStatusDto> getNodeStatusStat(String condition) throws Exception;

    /**
     * 统计工作流节点用户状态数量的功能
     * @param conditions String
     * @throws Exception
     * @return List<StatStatusDto>
     */
    public List<StatStatusDto> getNodeUserStatusStat(String condition) throws Exception;

    /**
     * 根据业务号码查询工作流flowID
     * @param registNo String
     * @throws Exception
     * @return String
     */
    public String findFlowIDByRegistNo(String registNo) throws Exception;
    /**
     * 根据报案号码查询工作流flowID
     * @param registNo String
     * @throws Exception
     * @return String
     */
    public String findFlowIDBybusinessNo(String businessNo) throws Exception;

    /**
     * 获得理赔节点统计信息
     * @param conditions：查询条件
     * @return List<StatStatusDto>
     * @throws Exception
     */
    public List<StatStatusDto> getStatStatus(String conditions) throws Exception;

    /**
     * 获取swfNotion表当前flowID相同,LogNo相同,lineNo 的最大的的maxNo
     * @param flowID 工作流号 String
     * @throws Exception
     * @return int
     */
    public int getSwfNotionMaxLineNo(String flowID, int logNo) throws Exception;

    public void updateHandlerCode(String flowID, int logNo, String userCode, String userName) throws Exception;

    /**
     * 查找符合条件的节点的个数
     * @param conditon
     * @return int
     * @throws Exception
     */
    public int findFlowNodeCountByConditon(String condition) throws Exception;

    /**
     * 根据报案号和保单号,车牌号，操作时间，案件状态查询报案信息
     * @param httpServletRequest 返回给页面的request
     * @param registNo 报案号
     * @param policyNo 保单号
     * @param licenseNo 车牌号码
     * @param riskCode 险别
     * @param insuredName 被保险人名称
     * @return Page
     * @throws Exception
     */
    public Page getWorkFlowList(HttpServletRequest httpServletRequest, String registNo, String policyNo, String licenseNo, String riskCode, String insuredName, int intPageNo, int intpageSize) throws Exception;

    /**
     * 获取最大LogNo (SwfLog)
     * @author 中科软
     * @param flowID 工作流号
     * @param nodeType 节点类型
     * @param businessNo
     * @return int
     * @throws Exception
     */
    public int getSwfLogMaxNodeLogNo(String flowID, String nodeType, String businessNo) throws Exception;
    
    /**
     * 获取最大LogNo (SwfLogStore)
     * @author 中科软
     * @param flowID 工作流号
     * @param nodeType 节点类型
     * @param businessNo
     * @return int
     * @throws Exception
     */
    public int getSwfLogStoreMaxNodeLogNo(String flowID, String nodeType, String businessNo) throws Exception;

    /**
     * 修改工作流状态为正常流转 未结束
     * @author 中科软
     * @param flowID
     * @throws Exception
     */
    public void updateFlowStatus(String flowID) throws Exception;

    /**
     * 更新工作流主表信息
     * @author 中科软
     * @param swfFlowMain
     * @throws Exception
     */
    public void updateSwfflowMain(SwfFlowMain swfFlowMain) throws Exception;

    /**
     * 在不予立案节点处理完毕後，要将在报案环节生成的工作流信息删除
     * @author 中科软
     * @param conditions
     * @throws Exception
     */
    public void deletWorkFlowForNotGrand(String conditions) throws Exception;
    
    /**
     * 删除注销/拒赔任务（即为退回注销/拒赔任务）
     * @author 中科软
     * @param flowID 工作流号 流程号
     * @param logNo 序号
     * @throws Exception
     */
	public void cancelBack(String flowID, int logNo, JbpmDto jbpmDto) throws Exception;

    /**
     * 核赔节点的通过（手工结案）
     * @author 中科软
     * @param submitTaskDto
     * @return
     * @throws Exception
     */
    public int passVeric(SubmitTaskDto submitTaskDto) throws Exception;

    /**
     * 从视图View_SwfLogAll查询满足条件的数据总数
     * @author 中科软
     * @param condition
     * @return
     * @throws Exception
     */
    public int findViewSwfLogAllCountByConditon(String condition) throws Exception;

    /**
     * 查转储数据SwfLogStore
     * @author 中科软
     * @param flowID 工作流号
     * @param logNo
     * @return
     * @throws Exception
     */
    public SwfLogStore findSwfLogStoreDtoByPrimaryKey(String flowID, int logNo) throws Exception;

    /**
     * 查找符合条件的流程节点信息(翻页)
     * @param condition 条件
     * @param pageNo 页数
     * @param pageSize 每页条数
     * @throws Exception
     * @return Page
     */
    public Page findViewSwfLogAll(String condition, int pageNo, int pageSize) throws Exception;

    /**
     * 
     * 获取指定业务号的理赔工作流flowID
     * @Description: 从视图查询
     * @author 中科软
     * @param businessNo 业务号
     * @return String
     * @throws Exception
     */
    public String findViewFlowIDBybusinessNo(String businessNo) throws Exception;

    /**
     * 查询SwfLog获取满足条件的工作流数据
     * @author 中科软
     * @param condition
     * @return List<SwfLog>
     * @throws Exception
     */
    public List<SwfLog> findByConditions(String condition) throws Exception;

    /**
     * 根据工作流号，序号查询工作流节点数据
     * @author 中科软
     * @param flowID 工作流号
     * @param logNo 序号
     * @return SwfLog
     * @throws Exception
     */
    public SwfLog findByPrimaryKey(String flowID, int logNo) throws Exception;

    /**
     * 查SwfLog获取满足条件的节点数
     * @author 中科软
     * @param condition
     * @return int
     * @throws Exception
     */
    public int getCount(String condition) throws Exception;

    /**
     * 理算紧急案件清单查询(翻页)
     * @param condition 查询条件
     * @param pageNo 页数
     * @param pageSize 每页条数
     * @throws Exception
     * @return Page
     */
    public Page getUrgentCaseList(String condition, int pageNo, int pageSize) throws Exception;

    /**
     * 核赔紧急案件清单查询(翻页)
     * @param condition 查询条件
     * @param pageNo 页数
     * @param pageSize 每页条数
     * @throws Exception
     * @return Page
     */
    public Page getUndwrtUrgentCaseList(String condition, int pageNo, int pageSize) throws Exception;
    /***
     * 获取产生当前节点任务的提交节点
     * @param flowID
     * @param logNo
     * @return
     * @throws Exception
     */
    public SwfLog findBackSwfLog(String flowID,int logNo) throws Exception;
    /***
     * 审核工作流处理入口
     * @param workFlowDto
     * @throws Exception
     */
    public void dealAudit(WorkFlowDto workFlowDto)  throws Exception ;
    
    /***
     * 获取当前流程，及其节点意见讯息
     * @param flowID
     * @param flag 流程是否结束
     * @return
     * @throws Exception
     */
    public List<SwfLog> findSwfLogWithNotion(String flowID,boolean flag) throws Exception;
    /**
     * 獲取工作流模板配置的註銷拒賠節點訊息
     * @param modelNo 流程模板號碼
     * @return
     * @throws Exception
     */
    public SwfNode getCancelSwfNode(int modelNo) throws Exception;
    /**
     * 獲取工作流模板配置的第一個節點訊息
     * @param modelNo 流程模板號碼
     * @return
     * @throws Exception
     */
    public SwfNode getFirstSwfNode(int modelNo) throws Exception;
    
    /***
     * 獲取自動節點流轉的條件
     * @param modelNo 工作流模板號碼
     * @param startNodeNo 當前提交處理節點
     * @param endNodeNo 後續節點中 設定TaskType='A'的自動節點的節點號碼（通常指計算書節點）
     * @return
     * @throws Exception
     */
    public List<SwfCondition> getSwfConditionForAutoTask(int modelNo , int startNodeNo , int endNodeNo) throws Exception;

    /***
     * 獲取流程路線流轉的條件
     * @param modelNo 工作流模板號碼
     * @param pathNo 流轉路線號碼
     * @return
     * @throws Exception
     */
    public List<SwfCondition> getSwfConditionForPath(int modelNo , int pathNo) throws Exception;
    /***
     * 獲取流轉路線
     * @param modelNo 工作流模板號碼
     * @param startNodeNo 開始節點
     * @return
     * @throws Exception
     */
    public List<SwfPath> getSwfPath(int modelNo , int startNodeNo) throws Exception;
    
    /***
     * 獲取模板配置節點訊息
     * @param modelNo 工作流模板號碼
     * @param nodeNo 節點號碼
     * @return
     * @throws Exception
     */
    public SwfNode getSwfNode(int modelNo , int nodeNo) throws Exception;
    
}