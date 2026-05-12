package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.PrpLcetainLoss;
import com.sinosoft.claim.schema.model.PrpLcetainLossId;
import com.sinosoft.claim.schema.service.facade.PrpLcertainLossService;
/**
 * 定损基本信息接口类
 * @ClassName PrpLcetainLossServiceSpringImpl
 * @Description 
 * @author 中科软
 */
public class PrpLcertainLossServiceSpringImpl extends GenericDaoHibernate<PrpLcetainLoss, PrpLcetainLossId>  implements PrpLcertainLossService {

	/**
	 * 按主键删除一条数据
	 */
	@Override
	public void delete(PrpLcetainLossId prpLcetainLossId) throws Exception {
		logger.info("删除信息编号为"+prpLcetainLossId+"的定损基本信息");
		super.deleteByPK(PrpLcetainLoss.class, prpLcetainLossId);
	}
	/**
	 * 按主键查询一条数据
	 */
	@Override
	public PrpLcetainLoss findPrpLcetainLoss(PrpLcetainLossId prpLcetainLossId) throws Exception {
		logger.info("获取信息编号为"+prpLcetainLossId+"的定损基本信息");
		return super.get(PrpLcetainLoss.class, prpLcetainLossId);
	}

	@Override
	public Page findPrpLcetainLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取定损基本信息列表");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcetainLoss> findPrpLcetainLoss(QueryRule queryRule) throws Exception {
		logger.info("获取满足条件的定损基本信息集合");
		return super.find(queryRule);
	}

	@Override
	public void save(PrpLcetainLoss prpLcetainLoss) throws Exception {
		logger.info("保存定损基本信息");
		super.save(prpLcetainLoss);
	}

	@Override
	public void save(List<PrpLcetainLoss> list) throws Exception {
		logger.info("保存定损基本信息集合");
		for (PrpLcetainLoss prpLcetainLoss : list) {
			super.save(prpLcetainLoss);
		}
	}

	@Override
	public void update(PrpLcetainLoss prpLcertainLoss) {
		logger.info("更新定损基本信息");
		super.update(prpLcertainLoss);
	}
	@Override
	public List<PrpLverifyLoss> findPrpLcetainLoss(String conditions,int pageNo,int pageSize) throws Exception {
		String statement = "Select DISTINCT a.RegistNo,a.PolicyNo, b.HandlerCode, a.DefLossDate, a.LossitemCode, b.OperateDate, b.Status, a.RiskCode, a.lossItemName From " +
				"(select * from PrpLClaimStatus) b Right JOIN PrpLverifyLoss a ON a.RegistNo = b.BusinessNo left join prplregist c on a.RegistNo = c.RegistNo,prplregistrpolicy d " +
				"where a.RegistNo = d.RegistNo and a.nodeType=b.nodeType and a.lossitemcode = b.serialNo and " +conditions;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, pageNo, pageSize);
		List<PrpLverifyLoss> resultList = new ArrayList<PrpLverifyLoss>();
		PrpLverifyLoss prpLverifyLoss = null;
		Object[] object = null;
		List<?> tempListSub = null; 
		for (Iterator<?> it = tempList.iterator(); it.hasNext(); resultList.add(prpLverifyLoss)) {
			object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
			prpLverifyLoss = new PrpLverifyLoss();
            prpLverifyLoss.getId().setRegistNo((String)object[0]);
            prpLverifyLoss.setPolicyNo((String)object[1]);
            prpLverifyLoss.setHandlerCode((String)object[2]);
            prpLverifyLoss.setDefLossDate(new Date(((Timestamp) object[3]).getTime()));
            prpLverifyLoss.getId().setLossItemCode((String)object[4]);
            prpLverifyLoss.setOperateDate(new Date(((Timestamp) object[5]).getTime()));
            prpLverifyLoss.setStatus((String)object[6]);
            prpLverifyLoss.setRiskCode((String)object[7]);
            prpLverifyLoss.setLossItemName((String)object[8]);
            prpLverifyLoss.setRelatepolicyNo(new TreeSet<String>());
			statement="select PolicyNo from prplregistrpolicy where RegistNo='"+(String)object[0]+"'";
			tempListSub = HibernateUtils.findbySql(session, statement, 0, 0);
        	for (Iterator<?> itSub = tempListSub.iterator();itSub.hasNext();){
        		prpLverifyLoss.getRelatepolicyNo().add((String)itSub.next());
        	}
		}
		return resultList;
	}

}
