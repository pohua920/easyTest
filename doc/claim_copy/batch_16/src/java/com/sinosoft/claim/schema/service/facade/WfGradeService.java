package com.sinosoft.claim.schema.service.facade;

/**
 * 双核的业务级别修改轨迹接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.claim.schema.model.WfGrade;


public interface WfGradeService {

    /**
     * 按主键查找数据的前次分级
     * @param flowID 工作流号
     * @throws Exception
     */
	public String getPreGradeCode(String flowID) throws Exception;
    /**
     * 插入一条数据
     * @param wfGradeDto wfGradeDto
     * @throws Exception
     */
	public void insert(WfGrade wfGradeDto) throws Exception;
    /**
     * 按主键删除一条数据
     * @param flowID 工作流号
     * @throws Exception
     */
	public void delete(String flowId, int logNo, String gradeMode) throws Exception;
    /**
     * 按条件删除数据
     * @param conditions 删除条件
     * @throws Exception
     */
	public void deleteByConditions(String conditions) throws Exception;
    /**
     * 按主键查找一条数据
     * @param flowID 工作流号
     * @return  wfGrade
     * @throws Exception
     */
	public WfGrade findByPrimaryKey(String flowId, int logNo, String gradeMode)
			throws Exception;
	/**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Page 查询的一页的结果
     * @throws Exception
     */
	public Page findByConditions(String conditions, int pageNo, int rowsPerPage)
			throws Exception;
    /**
     * 按条件查询多条数据
     * @param queryRule 查询条件
     * @return List 包含WfGrade的集合
     * @throws Exception
     */
	public List<WfGrade> findByConditions(QueryRule queryRule) throws Exception;
    /**
     * 查询满足模糊查询条件的记录数
     * @param queryRule 模糊查询条件
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
	public int getCount(QueryRule queryRule) throws Exception;
    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param  wfGradeDto
     * @throws Exception
     */
	public void update(WfGrade wfGradeDto);
	
    /**
     * 保存定级信息
     * @param dbManager
     * @param iFlowID
     * @param iModelNo
     * @param iNodeNo
     * @param iBusinessType
     * @param iBusinessNo
     * @param iUserCode
     * @param iOpertorCode
     * @param iGradeCode
     * @param iGradeValue
     * @param iMaxUsableRate
     * @param iBrokerRate
     * @param iAgentRate
     * @param iOrgRate
     * @param iBreakevenRate
     * @param iExtRate1
     * @param iExtRate2
     * @param iExtRate3
     * @throws Exception
     */
    public void saveWfGrade(String iFlowID, int iModelNo, int iNodeNo,String iBusinessType, String iBusinessNo, 
    		String iUserCode, String iOpertorCode ,String iGradeCode ,String iGradeValue ,String iMaxUsableRate ,String iBrokerRate ,
    		String iAgentRate ,String iOrgRate ,String iBreakevenRate ,String iExtRate1 ,String iExtRate2 ,String iExtRate3) throws Exception ;
    /**
     * 获取自动定级信息
     * @param dbManager
     * @param iFlowId
     * @param iLogNo
     * @param iModelNo
     * @param iNodeNo
     * @param iOperatorCode
     * @param iOperatorName
     * @param iBusinessType
     * @param iBusinessNo
     * @return WfGradeDto
     * @throws Exception
     */
    public WfGrade getAutoGrade(String iFlowId ,int iLogNo ,int iModelNo ,int iNodeNo ,String iOperatorCode ,
    		String iOperatorName ,String iBusinessType ,String iBusinessNo) throws Exception ;
    /**
     * 获取手工定级信息
     * @param iFlowId
     * @param iLogNo
     * @param iModelNo
     * @param iNodeNo
     * @param iOperatorCode
     * @param iOperatorName
     * @param iBusinessType
     * @param iBusinessNo
     * @param iGradeCode
     * @param iGradeValue
     * @param iMaxUsableRate
     * @param iBrokerRate
     * @param iAgentRate
     * @param iOrgRate
     * @param iBreakevenRate
     * @param iExtRate1
     * @param iExtRate2
     * @param iExtRate3
     * @return WfGradeDto
     * @throws Exception
     */
    public WfGrade getManualGrade(String iFlowId ,int iLogNo ,int iModelNo ,int iNodeNo ,String iOperatorCode ,String iOperatorName ,
    		String iBusinessType ,String iBusinessNo ,String iGradeCode ,String iGradeValue ,String iMaxUsableRate ,String iBrokerRate ,
    		String iAgentRate ,String iOrgRate ,String iBreakevenRate ,String iExtRate1 ,String iExtRate2 ,String iExtRate3) throws Exception ;
    
    /**
     * @desc 核保核批通过的对定级信息的後续处理
     * @param dbManager
     * @param iBusinessType
     * @param iBusinessNo
     * @throws Exception
     */
    public Collection<?> findByConditions(String conditions)throws Exception;
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection 包含wfFlowMain的集合
     * @throws Exception
     */ 
    public List<WfGrade> findListByQueryRule(QueryRule queryRule) throws Exception;



}
