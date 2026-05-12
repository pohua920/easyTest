package com.sinosoft.claim.schema.service.spring;
/**
 * 双核因子表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDclass;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.UtiUwFactor;
import com.sinosoft.claim.schema.model.UtiUwFactorId;
import com.sinosoft.claim.schema.service.facade.PrpDclassService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.UtiUwFactorService;

public class UtiUwFactorServiceSpringImpl extends GenericDaoHibernate<UtiUwFactor, UtiUwFactorId> implements UtiUwFactorService {

	private PrpDcodeService prpDcodeService;
	private PrpDclassService prpDclassService;
	
	@Override
	public void delete(String uwType, String classCode, String factorCode) throws Exception {
		UtiUwFactorId utiUwFactorId = new UtiUwFactorId();
		utiUwFactorId.setClassCode(classCode);
		utiUwFactorId.setFactorCode(factorCode);
		utiUwFactorId.setUwType(uwType);
		super.deleteByPK(utiUwFactorId);
	}

	@Override
	public int deleteByConditions(String conditions) throws Exception {
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("DELETE FROM UtiUwFactor WHERE ");
		buffer.append(conditions);
		Session session = super.getSession();
		Query q = session.createQuery(buffer.toString());
		int count = q.executeUpdate();
		return count;
	}

	@Override
	public List<UtiUwFactor> findByConditions(String conditions, int pageNo, int rowsPerPage) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		Page page = super.find(queryRule,pageNo,rowsPerPage);
		List<UtiUwFactor> list= new ArrayList<UtiUwFactor>();
		Iterator<?> it = page.getResult().iterator();
		while (it.hasNext()) {
			UtiUwFactor utiUwFactor = (UtiUwFactor)it.next();
			list.add(utiUwFactor);
		}
		return list;
	}

	@Override
	public List<UtiUwFactor> findByConditions(String conditions) throws Exception {
		return findByConditions(conditions, 0, 0);
	}

	@Override
	public UtiUwFactor findByPrimaryKey(String uwType, String classCode, String factorCode) {
        StringBuffer buffer = new StringBuffer(200);
        //拼SQL语句
        buffer.append("SELECT ");
        buffer.append("UwType,");
        buffer.append("RiskCategoryCode,");
        buffer.append("ClassCode,");
        buffer.append("FactorCode,");
        buffer.append("FactorName,");
        buffer.append("FactorAttr,");
        buffer.append("MultiSelectFlag,");
        buffer.append("IsCodeFlag,");
        buffer.append("ValidStatus,");
        buffer.append("ExampleValue,");
        buffer.append("ValueDesc,");
        buffer.append("Operator,");
        buffer.append("Remark ");
        buffer.append("FROM UtiUwFactor ");
        buffer.append("WHERE ");
        buffer.append("UwType = '"+uwType+"' And ");
        
        buffer.append("ClassCode = '"+classCode+"' And ");
        buffer.append("FactorCode = '"+factorCode+"'");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, buffer.toString(), 1, 10);
		UtiUwFactor utiUwFactor = new UtiUwFactor();
        PrpDcode prpDcode = null;
        PrpDclass prpDclass = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);
			utiUwFactor = new UtiUwFactor();
            utiUwFactor.getId().setUwType((String) object[0]);
            try {
				prpDcode = this.prpDcodeService.findByPrimaryKey("UwType", (String) object[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setUwTypeName(prpDcode.getCodeCName());
            utiUwFactor.setRiskCategoryCode((String) object[1]);
            try {
            	prpDcode = this.prpDcodeService.findByPrimaryKey("RiskCategory", (String) object[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setRiskCategoryName(prpDcode.getCodeCName());
            utiUwFactor.getId().setClassCode((String) object[2]);
            try {
            	prpDclass = this.prpDclassService.findPrpDclass((String) object[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setClassName(prpDclass.getClassName());
            utiUwFactor.getId().setFactorCode((String) object[3]);
            utiUwFactor.setFactorName((String) object[4]);
            utiUwFactor.setFactorAttr((String) object[5]);
            try {
            	prpDcode = this.prpDcodeService.findByPrimaryKey("UwFactorAttr", (String) object[5]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setFactorAttrName(prpDcode.getCodeCName());
            utiUwFactor.setMultiSelectFlag((String) object[6]);
            try {
            	prpDcode = this.prpDcodeService.findByPrimaryKey("UwMultiSelectFlag", (String) object[6]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setMultiSelectName(prpDcode.getCodeCName());
            utiUwFactor.setIsCodeFlag((String) object[7]);
            utiUwFactor.setValidStatus((String) object[8]);
            utiUwFactor.setValidStatusName(((String) object[8]).equals("1") ? "有效" : "註銷");
            utiUwFactor.setExampleValue((String) object[9]);
            utiUwFactor.setValueDesc((String) object[10]);
            utiUwFactor.setOperator((String) object[11]);
            utiUwFactor.setRemark((String) object[12]);
			}
		return utiUwFactor;
	}

	@Override
	public int getCount(String conditions) throws Exception {
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("SELECT count(*) FROM UtiUwFactor WHERE ");
		buffer.append(conditions);
		Session session = super.getSession();
		Number n=(Number)session.createQuery(buffer.toString()).uniqueResult();
		return n.intValue();
	}

	@Override
	public void insert(UtiUwFactor utiUwFactorDto) throws Exception {
		super.save(utiUwFactorDto);
	}

	@Override
	public void insertAll(List<?> list) throws Exception {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}

	@Override
	public void update(UtiUwFactor utiUwFactorDto) {
		super.update(utiUwFactorDto);
	}

	@Override
	public Page findPageByConditions(String conditions, int pageNo, int rowsPerPage) {
        StringBuffer buffer = new StringBuffer(200);
        //拼SQL语句
        buffer.append("SELECT ");
        buffer.append("UwType,");
        buffer.append("RiskCategoryCode,");
        buffer.append("ClassCode,");
        buffer.append("FactorCode,");
        buffer.append("FactorName,");
        buffer.append("FactorAttr,");
        buffer.append("MultiSelectFlag,");
        buffer.append("IsCodeFlag,");
        buffer.append("ValidStatus,");
        buffer.append("ExampleValue,");
        buffer.append("ValueDesc,");
        buffer.append("Operator,");
        buffer.append("Remark ");
        buffer.append("FROM UtiUwFactor WHERE ");
        buffer.append(conditions);
		Session session = super.getSession();
		List<?> tempList = HibernateUtils.findbySql(session, buffer.toString(), pageNo, rowsPerPage);
		List<UtiUwFactor> resultList = new ArrayList<UtiUwFactor>();
		UtiUwFactor utiUwFactor = null;
        PrpDcode prpDcode = null;
        PrpDclass prpDclass = null;
		 
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);
			utiUwFactor = new UtiUwFactor();
            utiUwFactor.getId().setUwType((String) object[0]);
            try {
				prpDcode = this.prpDcodeService.findByPrimaryKey("UwType", (String) object[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setUwTypeName(prpDcode.getCodeCName());
            utiUwFactor.setRiskCategoryCode((String) object[1]);
            try {
            	prpDcode = this.prpDcodeService.findByPrimaryKey("RiskCategory", (String) object[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setRiskCategoryName(prpDcode.getCodeCName());
            utiUwFactor.getId().setClassCode((String) object[2]);
            try {
            	prpDclass = this.prpDclassService.findPrpDclass((String) object[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setClassName(prpDclass.getClassName());
            utiUwFactor.getId().setFactorCode((String) object[3]);
            utiUwFactor.setFactorName((String) object[4]);
            utiUwFactor.setFactorAttr((String) object[5]);
            try {
            	prpDcode = this.prpDcodeService.findByPrimaryKey("UwFactorAttr", (String) object[5]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setFactorAttrName(prpDcode.getCodeCName());
            utiUwFactor.setMultiSelectFlag((String) object[6]);
            try {
            	prpDcode = this.prpDcodeService.findByPrimaryKey("UwMultiSelectFlag", (String) object[6]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			utiUwFactor.setMultiSelectName(prpDcode.getCodeCName());
            utiUwFactor.setIsCodeFlag((String) object[7]);
            utiUwFactor.setValidStatus((String) object[8]);
            utiUwFactor.setValidStatusName(((String) object[8]).equals("1") ? "有效" : "註銷");
            utiUwFactor.setExampleValue((String) object[9]);
            utiUwFactor.setValueDesc((String) object[10]);
            utiUwFactor.setOperator((String) object[11]);
            utiUwFactor.setRemark((String) object[12]);
            resultList.add(utiUwFactor);
			}
		return new Page((pageNo - 1) * rowsPerPage, HibernateUtils.getCountbySql(session, buffer.toString()), rowsPerPage, resultList);
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpDclassService getPrpDclassService() {
		return prpDclassService;
	}

	public void setPrpDclassService(PrpDclassService prpDclassService) {
		this.prpDclassService = prpDclassService;
	}

}
