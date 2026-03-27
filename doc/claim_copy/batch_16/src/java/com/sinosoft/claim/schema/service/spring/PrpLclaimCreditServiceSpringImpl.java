package com.sinosoft.claim.schema.service.spring;

import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLclaimCredit;
import com.sinosoft.claim.schema.model.PrpLclaimCreditId;
import com.sinosoft.claim.schema.service.facade.PrpLclaimCreditService;
/**
 * 卡片信息
 * @author 中科软
 *
 */
public class PrpLclaimCreditServiceSpringImpl extends GenericDaoHibernate<PrpLclaimCredit, PrpLclaimCreditId> implements PrpLclaimCreditService {

	/**
	 * 删除卡片信息
	 * @param prpLclaimCreditId ：传入的卡片编号
	 */
	public void delete(PrpLclaimCreditId prpLclaimCreditId) throws Exception {
		super.deleteByPK(prpLclaimCreditId);
	}
	/**
	 * 更据业务号码和类型删除数据
	 * @param businessNo
	 * @param nodeType
	 * @throws Exception
	 */
	public void delete(String businessNo,String nodeType) throws Exception{
		String sql = "delete from PrpLclaimCredit where businessNo = '"+businessNo+"' and nodeType = '"+nodeType+"'";
		HibernateUtils.executeSql(super.getSession(), sql);
	}

	/**
	 * 根据卡片编号查询出卡片信息
	 * @param prpLclaimCreditId ：传入的卡片编号
	 * @return 返回卡片
	 */
	public PrpLclaimCredit findPrpLclaimCredit(PrpLclaimCreditId prpLclaimCreditId) throws Exception {
		return super.get(prpLclaimCreditId);
	}
	/**
	 * 根据卡片编号查询出卡片信息
	 * @param prpLclaimCreditId ：传入的卡片编号
	 * @return 返回卡片
	 */
	public PrpLclaimCredit findPrpLclaimCredit(String businessNo,String nodeType,Integer serialNo) throws Exception {
		PrpLclaimCreditId prpLclaimCreditId = new PrpLclaimCreditId();
		prpLclaimCreditId.setBusinessNo(businessNo);
		prpLclaimCreditId.setNodeType(nodeType);
		prpLclaimCreditId.setSerialNo(serialNo);
		return super.get(prpLclaimCreditId);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的卡片页面信息
	 */
	public Page findPrpLclaimCredit(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取卡片对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的卡片页面信息
	 */
	public List<PrpLclaimCredit> findPrpLclaimCredit(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 保存卡片信息
	 * @param PrpLclaimCredit ：传入的卡片
	 */
	public void save(PrpLclaimCredit prpLclaimCredit) throws Exception {
		super.save(prpLclaimCredit);
	}

	/**
	 * 卡片信息
	 * @param list  :传入的卡片信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLclaimCredit> list) throws Exception {
		super.saveAll(list);
	}

}
