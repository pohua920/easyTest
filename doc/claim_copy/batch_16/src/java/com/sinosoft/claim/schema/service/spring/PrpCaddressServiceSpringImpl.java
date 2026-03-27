package com.sinosoft.claim.schema.service.spring;

/**
 * 保险地址信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCaddressId;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;

public class PrpCaddressServiceSpringImpl extends GenericDaoHibernate<PrpCaddress, PrpCaddressId> implements PrpCaddressService {
	
	/**
	 * 保存保险地址信息
	 * @param prpCaddress ：传入的保险地址
	 */
	public void save(PrpCaddress prpCaddress) throws Exception {
		logger.info("保险地址信息信息");
		super.save(prpCaddress);

	}

	/**
	 * 保险地址信息
	 * @param list  :传入的保险地址信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCaddress> list) throws Exception {
		logger.info("保险地址信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 删除保险地址信息
	 * @param prpCaddressId ：传入的保险地址编号
	 */
	public void delete(PrpCaddressId prpCaddressId) throws Exception {
		logger.info("删除查勘/代查勘信息编号为" + prpCaddressId + "的查勘/代查勘信息");
		super.deleteByPK(PrpCaddress.class, prpCaddressId);
	}

	/**
	 * 根据保险地址编号查询出保险地址信息
	 * @param prpCaddressId ：传入的保险地址编号
	 * @return 返回保险地址
	 */
	public PrpCaddress findPrpCaddress(PrpCaddressId prpCaddressId) throws Exception {
		logger.info("查询查勘/代查勘信息编号为" + prpCaddressId + "的查勘/代查勘信息");
		return super.get(PrpCaddress.class, prpCaddressId);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保险地址页面信息
	 */
	public Page findPrpCaddress(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取查勘/代查勘信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的保险地址全貌列表信息
	 */
	public List<PrpCaddress> findPrpCaddress(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * 查询保险标的的地址
	 * @param kindCode
	 * @param itemCode
	 * @return
	 * @throws Exception
	 */
	public PrpCaddress findPrpCaddress(String policyNo, PrpCitemKind prpCitemKind) throws Exception {
		PrpCaddress prpCaddress = null;
		if(prpCitemKind==null){
			return prpCaddress;
		}
		String flag = prpCitemKind.getFlag();
		String sql = null;
		// 判断附加险种
		if (flag != null && flag.length() > 1 && "2".equals(flag.substring(1, 2))) {
			sql = "select address.* from prpCaddress address where address.policyNo='" + policyNo + "' and address.addressNo = '" + prpCitemKind.getAddressNo() + "'";
		} else {
			sql = "select address.* from prpCmainProp prop, prpCaddress address where prop.policyNo='" + policyNo + "' and prop.policyNo=address.policyNo " + " and prop.buildingNo='" + prpCitemKind.getBuildingNo()
					+ "' and prop.addressNo = address.addressNo";
		}
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql, PrpCaddress.class);
		if (list.size() > 0) {
			prpCaddress = (PrpCaddress) list.get(0);
		}
		return prpCaddress;
	}
	/**
	 * 根据同险号码查询保单号
	 * @param sameAddressNo 查询对象
	 * @return 保单信息
	 */
	public List<String> findPolicyBySameAddressNo(String sameAddressNo) throws Exception {
		String sql = "select distinct policyNo from prpCaddress where sameAddressno=?";
		List<String> list = super.getSession().createSQLQuery(sql).setString(0, sameAddressNo).list();
		return list;
	}
}
