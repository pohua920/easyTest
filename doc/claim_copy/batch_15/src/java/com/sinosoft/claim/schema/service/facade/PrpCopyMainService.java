package com.sinosoft.claim.schema.service.facade;

/**
 * 保单副本信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopyMain;

public interface PrpCopyMainService {

	/**
	 * 保单副本信息
	 * @param PrpCopyMain ：传入的保单副本信息
	 */
	public void save(PrpCopyMain prpCopyMain) throws Exception;

	/**
	 * 保存保单副本信息
	 * @param list :传入的保单副本信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCopyMain> list) throws Exception;

	/**
	 * 删除保单副本信息
	 * @param endorseNo ：传入的保单副本信息编号
	 */
	public void delete(String endorseNo) throws Exception;

	/**
	 * 更新保单副本信息
	 * @param PrpCopyMain :传入需要更新的保单副本信息
	 */
	public void update(PrpCopyMain prpCopyMain) throws Exception;
	
	/**
	 * 根据保单副本信息编号查询出保单副本信息
	 * @param endorseNo ：传入的保单副本信息编号
	 * @return 返回保单副本信息
	 */
	public PrpCopyMain findPrpCopyMain(String endorseNo) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保单副本信息页面信息
	 */
	public Page findPrpCopyMain(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 保单副本信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的保单副本信息  的列表
	 */
	public List<PrpCopyMain> findPrpCopyMain(QueryRule queryRule) throws Exception;
	/**
	 * 更新保单副本信息
	 * @param PrpCopyMain :传入需要更新的保单副本信息
	 */
	public void saveOrUpdate(PrpCopyMain prpCopyMain) throws Exception;
	/**
	 * 更新保单副本信息列表
	 * @param list :传入需要更新的保单副本信息列表
	 */
	public void saveOrUpdate(List<PrpCopyMain> list) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询条件
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保单副本信息页面信息
	 */
	public Page findByConditions(String conditions, int pageNo, int pageSize) throws Exception;

	/**
	 * 获得保单
	 * @param endorseNo 保单号
	 * @return 保单对象
	 * @throws Exception
	 */
	public PrpCopyMain findPrpCopyMainByPrimaryKey(String endorseNo) throws Exception;

	/**
	 * 按条件从prpCopyMain表和prpcitemcar表中查询多条数据(非车报案环节支持模糊查询)
	 * @param conditions String
	 * @param pageNo int
	 * @param rowsPerPage int
	 * @throws Exception
	 * @return List
	 */
	public List<PrpCopyMain> findForRegistConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions conditions
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	public int getCount1(String conditions) throws Exception;

	/**
	 * 二期查询条件的记录数
	 * @param conditions conditions
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	public int getCount2(String conditions) throws Exception;

	/**
	 * 更新一条数据(让表prpCopyMain中的字段claimstatus加1)
	 * @param
	 * @throws Exception
	 */
	public void updateClaimTimesAdd1(String endorseNo) throws Exception;

	/**
     * 更新一条数据(让表prpCopyMain中的字段claimstatus减1)
     * 对於注销和拒赔案件，出现次数要减1 start
	 * @param
	 * @throws Exception
	 */
	public void updateClaimTimesMinus1(String endorseNo) throws Exception;

	/**
	 * 按条件从prpCopyMain表和prpcitemcar表中查询多条数据
	 * @param conditions String
	 * @param operatDate String
	 * @param pageNo int
	 * @param rowsPerPage int
	 * @throws Exception
	 * @return List
	 */
	public List<PrpCopyMain> findForRegistConditions(String conditions, String operatDate, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 按条件从prpCopyMain表和prpcitemcar表中查询多条数据（接口二期取得包括出险时和当前收费状态信息保单信息列表）
	 * @param conditions String
	 * @param operatDate String
	 * @param pageNo int
	 * @param rowsPerPage int
	 * @throws Exception
	 * @return List
	 */
	public List<PrpCopyMain> findForCCRegistConditions(String conditions, String operatDate, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 按条件从prpCopyMain表查询数据
	 * @param conditions
	 * @param operatDate
	 * @return
	 * @throws Exception
	 */
	public PrpCopyMain findForRegistConditions(String conditions, String operatDate) throws Exception;

	/**
	 * 根据车架号，车牌号，发动机号查询保单号
	 */
	public List<String> findPolicyNoForRegistConditions(String conditions) throws Exception;

	/**
	 * 95519二期组织符合条件的保单插叙信息
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public String getPolicyNoConditions(String conditions) throws Exception;

	/**
	 * 按条件从prpCopyMain表和prpcitemcar表中查询多条数据
	 * @param conditions String
	 * @param operatDate String
	 * @param pageNo int
	 * @param rowsPerPage int
	 * @throws Exception
	 * @return List
	 */
	public List<PrpCopyMain> findForRegistConditions(String conditions) throws Exception;
	/**
	 * 根据保单副本信息编号查询出保单副本信息
	 * @param endorseNo ：传入的保单副本信息编号
	 * @return 返回保单副本信息
	 */
	public PrpCopyMain findByPrimaryKey(String endorseNo) throws Exception;
	/**
	 * 检查缴费情况
	 * 
	 * @param conditions
	 *            String
	 * @throws Exception
	 * @return Collection
	 */
//	public int checkPay(String conditions) throws Exception;
}
