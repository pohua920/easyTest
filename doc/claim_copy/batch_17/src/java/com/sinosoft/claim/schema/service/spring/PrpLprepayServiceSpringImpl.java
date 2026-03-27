package com.sinosoft.claim.schema.service.spring;

/**
 * 预赔登记接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;

public class PrpLprepayServiceSpringImpl extends GenericDaoHibernate<PrpLprepay, String> implements PrpLprepayService {

	/**
	 * 保存预赔登记信息
	 * @param prpLprepay ：传入的预赔登记
	 */
	@Override
	public void save(PrpLprepay prpLprepay) throws Exception {
		logger.info("保存预赔登记信息");
		super.save(prpLprepay);
	}

	/**
	 * 删除预赔登记信息
	 * @param preCompensateNo ：传入的预赔登记编号
	 */
	@Override
	public void delete(String preCompensateNo) throws Exception {
		super.deleteByPK(PrpLprepay.class, preCompensateNo);
		logger.info("删除预赔登记编号为" + preCompensateNo + "的预赔登记信息");
	}

	/**
	 * 保存预赔登记信息
	 * @param list:保存预赔登记信息
	 */
	@Override
	public void save(List<PrpLprepay> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @description: 预赔登记修改
	 * @param PrpLprepay prpLprepay
	 * @throws Exception
	 */
	@Override
	public void update(PrpLprepay prpLprepay) {
		logger.info("修改预赔登记信息开始");
		super.update(prpLprepay);
		logger.info("修改预赔登记信息结束");
	}

	/**
	 * 根据预赔登记编号查询出预赔登记信息
	 * @param preCompensateNo ：传入的预赔登记编号
	 * @return 返回预赔登记
	 */
	@Override
	public PrpLprepay findPrpLprepay(String preCompensateNo) throws Exception {
		logger.info("查询预赔登记编号为" + preCompensateNo + "的预赔登记信息");
		return super.get(PrpLprepay.class, preCompensateNo);
	}
	/**
	 * @param claimNo
	 * @return
	 * @throws Exception
	 * 根据立案号查询预配信息
	 */
	public List<PrpLprepay> findByClaimNo(String claimNo)throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", claimNo);
		return this.find(queryRule);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的预赔登记页面信息
	 */
	@Override
	public Page findPrpLprepay(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取预赔登记列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLprepay> findPrpLprepay(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public int getCount(String conditions) {
		String sql = "SELECT count(*) FROM PrpLprepay WHERE " + conditions;
		List<?> result = HibernateUtils.findbySql(super.getSession(), sql);
		return Integer.parseInt(result.get(0).toString());
	}

	/**
	 * 复核实赔
	 */
	@Override
	public void approve(String prepayNo, String userCode, String underWriteFlag) throws Exception {
		String statement = " Update PrpLprepay  set ApproverCode = '" + userCode + "',UnderWriteFlag = '" + underWriteFlag + "' where preCompensateNo = '" + prepayNo + "'";
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	@Override
	public List<PrpLprepay> findByApproveQueryConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String statement = "Select prplprepay.preCompensateNo," + "prplprepay.PolicyNo, " + "prplprepay.ClaimNo, " + "prplprepay.OperatorCode, " + "prplprepay.RiskCode, "
				+ "prplprepay.InputDate from prplprepay left join prplclaimstatus on prplclaimstatus.businessno = prplprepay.preCompensateNo and prplclaimstatus.nodetype='prepa' where " + conditions;
		List<?> list = HibernateUtils.findPagebySql(super.getSession(), statement, pageNo, pageSize).getResult();
		List<PrpLprepay> prpLprepayList = new ArrayList<PrpLprepay>();
		if (list != null && !list.isEmpty()) {
			PrpLprepay prpLprepay = null;
			Object[] object = null;
			for (Iterator<?> it = list.iterator(); it.hasNext(); prpLprepayList.add(prpLprepay)) {
				object = (Object[]) it.next();
				prpLprepay = new PrpLprepay();
				prpLprepay.setPreCompensateNo((String)object[0]);
				prpLprepay.setPolicyNo((String)object[1]);
				prpLprepay.setClaimNo((String)object[2]);
				prpLprepay.setOperatorCode((String)object[3]);
				prpLprepay.setRiskCode((String)object[4]);
				prpLprepay.setInputDate(new Date(((Timestamp) object[5]).getTime()));
			}
		}
		return prpLprepayList;
	}

}