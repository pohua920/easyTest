package com.sinosoft.claim.check.service.facade;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.check.vo.AcciCheckDto;
import com.sinosoft.claim.dto.custom.CaseRelateNodeDto;
import com.sinosoft.claim.dto.custom.WorkFlowDto;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 报案对象Regist
 * <p>Title: 车险理赔样本报案action  </p>
 * <p>Description: 车险理赔样本报案action</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Sinosoft</p>
 * @author 中科软
 * @version 1.0
 */
public interface AcciCheckService {
	/**
	 * 保存报案
	 * @param RegistDto：报案对象DTO
	 * @throws Exception
	 */
	
	public void save(RegistDto registDto) throws SQLException,Exception;
	
	
	/**
	 * 保存报案带工作流
	 * @param RegistDto：报案对象DTO
	 * @throws Exception
	 */
	
	public void save(RegistDto registDto,WorkFlowDto workFlowDto) throws SQLException,Exception;
	/**
	 * 删除报案
	 * @param registNo：报案号
	 * @throws Exception
	 */
	public void delete(String registNo) throws SQLException,Exception;
	
	/**
	 * 获得报案信息
	 * @param  registNo：报案号
	 * @return 意键险调查对象
	 * @throws Exception
	 */
	
	public AcciCheckDto findByPrimaryKey(String registNo) throws SQLException,UserException,Exception;
	
	/**
	 * 判断报案号是否存在
	 * @param registNo:报案号
	 * @return 是/否
	 * @throws Exception
	 */
	
	public boolean isExist(String registNo) throws SQLException,Exception;
	/**
	 * 获得报案信息
	 * @param  conditions：查询条件
	 * @return 报案对象
	 * @throws Exception
	 */
	
	public List<?> findByConditions(String conditions) throws SQLException,Exception;
	
	/**
	 * 获得报案查询信息
	 * @param  conditions：查询条件
	 * @return 报案对象
	 * @throws Exception
	 * Add By sunhao 2004-08-24 Reason:增加新的查询条件
	 */
	
	public List<?> findByQueryConditions(String conditions) throws SQLException,Exception;
	
	public List<?> findSamePolicyRegist(String policyNo) throws Exception;
	/**
	 * 获得报案信息
	 * @param  conditions：查询条件
	 * @return 报案对象
	 * @throws Exception
	 */
	
	public List<?> getWorkFlowList(String conditions) throws SQLException,Exception;
	/**
	 * 获得相关的节点信息
	 * @param  registNo：报案号
	 * @return 报案对象
	 * @throws Exception
	 */
	
	public CaseRelateNodeDto relateNode(String registNo) throws SQLException,UserException,Exception;
	
	/**
	 * 获得一个案件的所有调查信息(意健险独有的方法)
	 * @param conditions 查询条件
	 * @return 调查表对象集合
	 * @throws Exception
	 * */
	public List<PrpLacciCheck> findByConditionsAcciCheck(String conditions) throws Exception;
	
}
