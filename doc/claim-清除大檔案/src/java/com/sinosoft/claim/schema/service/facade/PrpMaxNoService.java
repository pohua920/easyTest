package com.sinosoft.claim.schema.service.facade;

/**
 * 单证号接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpMaxNo;
import com.sinosoft.claim.schema.model.PrpMaxNoId;

public interface PrpMaxNoService {

	/**
	 * 保存单证号信息
	 * @param prpMaxNo ：传入的单证号
	 */
	public void save(PrpMaxNo prpMaxNo) throws Exception;
	/**
	 * 保存单证号信息
	 * @param prpMaxNo ：传入的单证号
	 */
	//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 */
	public boolean saveByNewTransaction(String oldNo , PrpMaxNo prpMaxNo) throws Exception;
	/**
	 * 删除单证号信息
	 * @param prpMaxNoId ：传入的单证号编号
	 */
	public void deleteByNewTransaction(PrpMaxNoId prpMaxNoId) throws Exception;
	/**
	 * 从单号表PrpMaxNo获取最大和最小单号
	 * @param iGroupNo
	 * @param iTableName
	 * @return String[] String[1]:最大号MaxNo,String[2]:最小号MinNo,String[3]:记录数Count
	 * @throws Exception
	 * @throws SQLException
	 */
	public String[] findByNewTransaction(String groupNo, String tableName) throws Exception, SQLException;
	

	/**
	 * 单证号信息
	 * @param list :传入的单证号信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpMaxNo> list) throws Exception;

	/**
	 * 删除单证号信息
	 * @param prpMaxNoId ：传入的单证号编号
	 */
	public void delete(PrpMaxNoId prpMaxNoId) throws Exception;

	/**
	 * 更新单证号信息
	 * @param prpMaxNo :传入需要更新的单证号
	 */
	public void update(PrpMaxNo prpMaxNo) throws Exception;

	/**
	 * 根据单证号编号查询出单证号信息
	 * @param prpMaxNoId ：传入的单证号编号
	 * @return 返回单证号
	 */
	public PrpMaxNo findPrpMaxNo(PrpMaxNoId prpMaxNoId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的单证号页面信息
	 */
	public Page findPrpMaxNo(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 单证号信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 单证号信息 的集合
	 */
	public List<PrpMaxNo> findPrpMaxNo(QueryRule queryRule) throws Exception;

	/**
	 * 从单号表PrpMaxNo获取最大和最小单号
	 * @param iGroupNo
	 * @param iTableName
	 * @return String[] String[1]:最大号MaxNo,String[2]:最小号MinNo,String[3]:记录数Count
	 * @throws Exception
	 * @throws SQLException
	 */
	public String[] getMaxMinNo(String groupNo, String tableName) throws Exception, SQLException;
}
