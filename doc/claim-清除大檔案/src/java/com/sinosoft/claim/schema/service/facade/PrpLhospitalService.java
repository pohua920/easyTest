package com.sinosoft.claim.schema.service.facade;

/**
 * 医院信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpLhospital;

public interface PrpLhospitalService {

	/**
	 * 保存医院信息
	 * @param prpLcheck ：传入的车辆驾驶员关系
	 */
	public void save(PrpLhospital prpLhospital) throws Exception;

	/**
	 * 医院信息
	 * @param list :传入的医院信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLhospital> list) throws Exception;

	/**
	 * 删除医院信息
	 * @param prpLhospitalId ：传入的医院编号
	 */
	public void delete(String hospitalCode) throws Exception;

	/**
	 * 更新医院信息
	 * @param prpLhospital :传入需要更新的医院
	 */
	public void update(PrpLhospital prpLhospital) throws Exception;

	/**
	 * 根据医院编号查询出医院信息
	 * @param prpLhospitalId ：传入的医院编号
	 * @return 返回医院
	 */
	public PrpLhospital findPrpLhospital(String hospitalCode) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的医院页面信息
	 */
	public Page findPrpLhospital(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**根据查询对象获取医院信息  的集合
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	public List<PrpLhospital> findPrpLhospital(QueryRule queryRule) throws Exception;
	/**
	 * @param hospitalCode
	 * @param hospitalName
	 * @return
	 * @throws Exception
	 * 更具名称和编码查询医院
	 */
	public Page findPrpLhospital(String hospitalCode,String hospitalName, int pageNo, int pageSize) throws Exception ;
	/**
	 * @param hospitalCode
	 * @param hospitalName
	 * @return
	 * @throws Exception
	 * 更具名称和编码查询医院的条数，精确查询
	 */
	public long getCount(String hospitalCode,String hospitalName)throws Exception;
	/**
	 * @param hospitalCode
	 * @param hospitalName
	 * @return
	 * @throws Exception
	 * 更具名称和编码查询医院的条数,模糊查询
	 */
	public long findCount(String hospitalCode,String hospitalName)throws Exception;
}
