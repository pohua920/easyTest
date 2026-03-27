package com.sinosoft.claim.ExternalAgency.service.spring;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.ExternalAgency.service.facade.InsuranceSurveyorService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyor;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyorId;

public class InsuranceSurveyorServiceSpringImpl extends GenericDaoHibernate<PrpLInsuranceSurveyor, PrpLInsuranceSurveyorId> implements InsuranceSurveyorService {


	@Override
	public PrpLInsuranceSurveyor findByPrimaryKey(String comcode, String newcomcode) throws Exception {
		PrpLInsuranceSurveyorId prpLInsuranceSurveyorId=new PrpLInsuranceSurveyorId();
		prpLInsuranceSurveyorId.setComCode(comcode);
		prpLInsuranceSurveyorId.setNewcomcode(newcomcode);
		PrpLInsuranceSurveyor prpLInsuranceSurveyor=super.get(prpLInsuranceSurveyorId);
		//添加公估机构名称
		String statement="select b.COMCNAME as NEWCOMCNAME FROM PrpLInsuranceSurveyor a,Prplexternalagency b WHERE a.NEWCOMCODE=b.COMCODE AND a.Comcode='"+comcode+"' AND a.NewComcode='"+newcomcode+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement,1,10);
		for (int i = 0; i < tempList.size(); i++) {
			prpLInsuranceSurveyor.setNewComCName((String) tempList.get(i));
		}
		return prpLInsuranceSurveyor;
	}

	@Override
	public void insert(PrpLInsuranceSurveyor prpLInsuranceSurveyor) throws Exception {
		super.save(prpLInsuranceSurveyor);
	}

	@Override
	public void update(PrpLInsuranceSurveyor prpLInsuranceSurveyor) {
		super.update(prpLInsuranceSurveyor);
	}

	@Override
	public int getCount(String conditions) {
        int rowCount=0;
        if(conditions.trim().length()==0){
            conditions = "1=1";
        }
        conditions = "select * from PrpLInsuranceSurveyor where " + conditions;
        rowCount = (int) HibernateUtils.getCountbySql(this.getSession(), conditions); 
		return rowCount;
	}

	@Override
	public Page findByQueryConditions(StringBuilder conditions, int pageNo, int rowsPerPage) {
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
	        buffer.append("from PrpLInsuranceSurveyor a,Prplexternalagency b WHERE ");
	        buffer.append("a.NEWCOMCODE=b.COMCODE AND ");
	        buffer.append(conditions);
	        if(logger.isDebugEnabled()){
	            logger.debug(buffer.toString());
	        }
			List<PrpLInsuranceSurveyor> resultList = new ArrayList<PrpLInsuranceSurveyor>();
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			List<?> tempList = HibernateUtils.findbySql(session, buffer.toString(), pageNo, rowsPerPage);
			PrpLInsuranceSurveyor prpLInsuranceSurveyor=null;
			for (int i = 0; i < tempList.size(); i++) {
				Object[] object = (Object[]) tempList.get(i);
				prpLInsuranceSurveyor = new PrpLInsuranceSurveyor();
				PrpLInsuranceSurveyorId prpLInsuranceSurveyorId=new PrpLInsuranceSurveyorId();
				prpLInsuranceSurveyorId.setComCode((String) object[0]);
				prpLInsuranceSurveyorId.setNewcomcode((String) object[1]);
				prpLInsuranceSurveyor.setId(prpLInsuranceSurveyorId);
				prpLInsuranceSurveyor.setComcname((String) object[2]);
				prpLInsuranceSurveyor.setComename((String) object[3]);
				prpLInsuranceSurveyor.setTelephone((String) object[4]);
				prpLInsuranceSurveyor.setEmail((String) object[5]);
				prpLInsuranceSurveyor.setValidStatus((String) object[6]);
				prpLInsuranceSurveyor.setNewComCName((String) object[7]);
				prpLInsuranceSurveyor.setComType((String) object[8]);
				resultList.add(prpLInsuranceSurveyor);
			}
			return new Page((pageNo - 1) * rowsPerPage, HibernateUtils.getCountbySql(session, buffer.toString()), rowsPerPage, resultList);
	}

}
