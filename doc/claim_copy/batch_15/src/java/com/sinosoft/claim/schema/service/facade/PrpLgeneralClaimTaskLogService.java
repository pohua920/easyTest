package com.sinosoft.claim.schema.service.facade;
/**
 * 代查勘接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTaskLog;


public interface PrpLgeneralClaimTaskLogService {
    
    /**
     * 保存报案信息
     * @param prpLgeneralClaimTaskLog ：传入的报案
     */
    public void save(PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog) throws Exception;
    
    /**
     * 更新报案信息
     * @param prpLgeneralClaimTaskLog :传入需要更新的报案
     */
    public void update(PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog) throws Exception;

    /**
     * @param prpLgeneralClaimTaskLog
     * @throws Exception
     * 保存或修改
     */
    public void saveOrUpdate(PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog)throws Exception;
    
    /**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCount(String conditions) throws Exception;
	/**
	 * 根据查询条件获取Page对象的列表
	 * @param conditions 查询条件
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的代查勘信息
	 */
    public Page findByConditions(String conditions, int strPageNo, int rowsPerPage) throws Exception;
    
    /**
     * 根据报案号取通赔信息
     * @author 中科软
     * @date Mar 29, 2013 4:47:30 PM
     * @param queryRule
     * @return
     */
    public List<PrpLgeneralClaimTaskLog> findPrpLgeneralClaimTaskLog(QueryRule queryRule);
    /**
	 * 查询能够处理某一机构下拥有某项权限的操作员
	 * @throws Exception
	 * @return Page
	 * @author 中科软
	 */
	public Page queryUserHaveRights(String conditions, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据当前机构取得该机构的二级机构
	 * @param workFlowDto 理赔工作流流程处理处理任务取消的对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getLevelTwoComCode(PrpDcompany prpDcompany) throws SQLException, Exception;
}
