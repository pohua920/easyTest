package com.sinosoft.claim.schema.service.spring;

/**
 * 人伤跟踪信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyor;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyorId;
import com.sinosoft.claim.schema.service.facade.PrpLInsuranceSurveyorService;

public class PrpLInsuranceSurveyorServiceSpringImpl extends GenericDaoHibernate<PrpLInsuranceSurveyor, PrpLInsuranceSurveyorId> implements PrpLInsuranceSurveyorService {

	@Override
	public void save(PrpLInsuranceSurveyor prpLInsuranceSurveyor) throws Exception {
		logger.info("保存人伤跟踪信息");
		super.save(prpLInsuranceSurveyor);

	}

	@Override
	public void save(List<PrpLInsuranceSurveyor> list) throws Exception {
		logger.info("保存人伤跟踪信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLInsuranceSurveyorId prpLInsuranceSurveyorId) throws Exception {
		logger.info("删除人伤跟踪信息编号为" + prpLInsuranceSurveyorId + "的人伤跟踪信息");
		super.deleteByPK(PrpLInsuranceSurveyor.class, prpLInsuranceSurveyorId);
	}

	@Override
	public PrpLInsuranceSurveyor findPrpLInsuranceSurveyor(PrpLInsuranceSurveyorId prpLInsuranceSurveyorId) throws Exception {
		logger.info("查询人伤跟踪信息编号为" + prpLInsuranceSurveyorId + "的人伤跟踪信息");
		return super.get(PrpLInsuranceSurveyor.class, prpLInsuranceSurveyorId);
	}

	@Override
	public Page findPrpLInsuranceSurveyor(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取人伤跟踪信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLInsuranceSurveyor> findPrpLInsuranceSurveyor(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpLInsuranceSurveyor findPrpLInsuranceSurveyor(String certiNo) throws Exception {
		PrpLInsuranceSurveyor prpLInsuranceSurveyor = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLInsuranceSurveyor> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLInsuranceSurveyor = resultList.get(0);
		}
		return prpLInsuranceSurveyor;
	}

	@Override
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception {
        StringBuffer buffer = new StringBuffer(200);
        //拼SQL语句
        buffer.append("SELECT ");
        buffer.append("a.COMCODE,");
        buffer.append("a.NEWCOMCODE,");
        buffer.append("a.COMCNAME,");
        buffer.append("a.COMENAME,");
        buffer.append("a.TELEPHONE,");
        buffer.append("a.EMAIL,");
        buffer.append("a.VALIDSTATUS,");
        buffer.append("b.COMCNAME as NEWCOMCNAME, ");
        buffer.append("b.COMTYPE ");
        buffer.append("FROM PrpLInsuranceSurveyor a,Prplexternalagency b WHERE ");
        buffer.append("a.NEWCOMCODE = b.COMCODE AND ");
        buffer.append(conditions);
        Page page = HibernateUtils.findPagebySql(super.getSession(), buffer.toString(), pageNo, pageSize);
        List<?> result = page.getResult();
		List<PrpLInsuranceSurveyor> resultList = new ArrayList<PrpLInsuranceSurveyor>();
		if (result!=null && !result.isEmpty()) {
			Object[] object = null;
			PrpLInsuranceSurveyor prpLInsuranceSurveyor = null;
			for (Iterator<?> it = result.iterator(); it.hasNext();) {
				object = (Object[])it.next();
				prpLInsuranceSurveyor = new PrpLInsuranceSurveyor();
	            prpLInsuranceSurveyor.getId().setComCode((String)object[0]);
	            prpLInsuranceSurveyor.setNewComCName((String)object[1]);
	            prpLInsuranceSurveyor.setComcname((String)object[2]);
	            prpLInsuranceSurveyor.setComename((String)object[3]);
	            prpLInsuranceSurveyor.setTelephone((String)object[4]);
	            prpLInsuranceSurveyor.setEmail((String)object[5]);
	            prpLInsuranceSurveyor.setValidStatus((String)object[6]);
	            prpLInsuranceSurveyor.setNewComCName((String)object[7]);
	            prpLInsuranceSurveyor.setComType((String)object[8]);
				resultList.add(prpLInsuranceSurveyor);
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}

}
