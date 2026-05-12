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
import com.sinosoft.claim.schema.model.PrpLverifyLossId;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;

/**
 * 定核损信息接口实现类
 * @ClassName PrpLverifyLossServiceSpringImpl
 * @Description
 * @author 中科软
 */
@SuppressWarnings("unchecked")
public class PrpLverifyLossServiceSpringImpl extends GenericDaoHibernate<PrpLverifyLoss, PrpLverifyLossId> implements PrpLverifyLossService {

	@Override
	public void delete(String registNo,String lossItemCode,String nodeType) throws Exception {
		PrpLverifyLossId prpLverifyLossId = new PrpLverifyLossId(registNo,lossItemCode,nodeType);
		logger.info("删除定核损信息编号为" + prpLverifyLossId + "的定核损信息");
		super.deleteByPK(prpLverifyLossId);
	}
	/**
	 * 按条件从prplverifyLoss表,prplregist表和prplclaimstatus表中查询多条数据
	 */
	@Override
	public List<PrpLverifyLoss> findByQueryConditions(String conditions,int pageNo, int pageSize) throws Exception {
		String statement = "Select DISTINCT a.RegistNo,a.PolicyNo, a.HandlerCode, a.DefLossDate, b.OperateDate, b.Status, a.lossItemCode, b.RiskCode, a.LicenseNo, a.UnderWriteEndDate,a.insureCarFlag From " +
				"(select * from PrpLClaimStatus) b Right JOIN PrpLverifyLoss a ON a.RegistNo = b.BusinessNo LEFT JOIN prplregist c ON b.BusinessNo = c.RegistNo,prplregistrpolicy d " +
				"where a.RegistNo=d.RegistNo and a.lossitemcode = b.serialNo and a.nodeType=b.nodeType and " + conditions;
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
            prpLverifyLoss.setOperateDate(new Date(((Timestamp) object[4]).getTime()));
            prpLverifyLoss.setStatus((String)object[5]);
            prpLverifyLoss.getId().setLossItemCode((String)object[6]);
            prpLverifyLoss.setRiskCode((String)object[7]);
            prpLverifyLoss.setLicenseNo((String)object[8]);
            prpLverifyLoss.setUnderWriteEndDate(new Date(((Timestamp) object[9]).getTime()));
            prpLverifyLoss.setInsureCarFlag((String)object[10]);
            prpLverifyLoss.setRelatepolicyNo(new TreeSet());
			statement="select PolicyNo from prplregistrpolicy where RegistNo='"+(String)object[0]+"'";
			tempListSub = HibernateUtils.findbySql(session, statement, 0, 0);
        	for (Iterator itSub = tempListSub.iterator();itSub.hasNext();){
        		prpLverifyLoss.getRelatepolicyNo().add((String)itSub.next());
        	}
		}
		return resultList;
	}

	@Override
	public PrpLverifyLoss findPrpLverifyLoss(String registNo,String lossItemCode,String nodeType) throws Exception {
		PrpLverifyLossId prpLverifyLossId = new PrpLverifyLossId(registNo,lossItemCode,nodeType);
		logger.info("获取定核损信息编号为" + prpLverifyLossId + "的定核损信息");
		return super.get(prpLverifyLossId);
	}

	@Override
	public Page findPrpLverifyLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取定核损信息列表");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLverifyLoss> findPrpLverifyLoss(QueryRule queryRule) throws Exception {
		logger.info("获取满足条件的定核损信息集合");
		return super.find(queryRule);
	}

	@Override
	public void save(PrpLverifyLoss prpLverifyLoss) throws Exception {
		logger.info("保存定核损信息");
		super.save(prpLverifyLoss);
	}

	@Override
	public void save(List<PrpLverifyLoss> list) throws Exception {
		logger.info("保存定核损信息集合");
		for(PrpLverifyLoss prpLverifyLoss : list){
			super.save(prpLverifyLoss);
		}
	}

	@Override
	public void update(PrpLverifyLoss prpLverifyLoss) {
		logger.info("更新定核损信息集合");
		super.update(prpLverifyLoss);
	}

	@Override
	public void update(String registNo) throws Exception {
		String statement = "UPDATE PRPLVERIFYLOSS SET UNDERWRITEFLAG='1' WHERE REGISTNO='" + registNo + "'";
		HibernateUtils.executeSql(super.getSession(), statement);
	}
	@Override
	public long getCount(String conditions) throws Exception {
		String statement = "SELECT count(*) FROM PrpLverifyLoss WHERE " + conditions;
		return HibernateUtils.getCountbyCountSql(super.getSession(), statement);
	}
	@Override
	public void saveOrUpdate(PrpLverifyLoss prpLverifyLoss) throws Exception {
		super.getSession().merge(prpLverifyLoss);
	}
}
