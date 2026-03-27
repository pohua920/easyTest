package cn.com.sinosoft.dms.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcompanyGrade;
import cn.com.sinosoft.dms.model.PrpDcompanyGradeId;
import cn.com.sinosoft.dms.service.facade.PrpDcompanyGradeService;
import cn.com.sinosoft.dms.service.facade.PrpDcompanyService;
import cn.com.sinosoft.saa.util.IConstants;
public class PrpDcompanyGradeServiceSpringImpl extends
GenericDaoHibernate<PrpDcompanyGrade, PrpDcompanyGradeId> implements PrpDcompanyGradeService {
	/**
	 * 通过prpDcompany表生成Grade表，BelongType设置为common
	 * */
	public void insertGrade(PrpDcompany prpDcompany, PrpDcompany newCompany) {
		PrpDcompanyService prpDcompanyService=(PrpDcompanyService) applicationContext.getBean("prpDcompanyService");
		PrpDcompanyGrade newprpDcompanyGrade = new PrpDcompanyGrade();
		PrpDcompanyGradeId prpDcompanyGradeId = new PrpDcompanyGradeId();
		prpDcompanyGradeId.setComCode(prpDcompany.getComCode());
		prpDcompanyGradeId.setSubComCode(newCompany.getComCode());
		newprpDcompanyGrade.setId(prpDcompanyGradeId);
		newprpDcompanyGrade.setValidStatus(prpDcompany.getValidStatus());
		newprpDcompanyGrade.setBelongType("0");
		int comGrade = prpDcompanyService.getLv(prpDcompany);
		int subComGrade = prpDcompanyService.getLv(newCompany);
		newprpDcompanyGrade.setComGrade(new BigDecimal(comGrade));
		newprpDcompanyGrade.setSubComGrade(new BigDecimal(subComGrade));
		String isDirectSubCom;
		if(newCompany.getUpperComCode()!=null&&newCompany.getUpperComCode().equals(prpDcompany.getComCode())){
			 isDirectSubCom=IConstants.ISDIRECTSUBCOM_YES;
		}else{
			 isDirectSubCom=IConstants.ISDIRECTSUBCOM_NO;
		}
		newprpDcompanyGrade.setIsDirectSubCom(isDirectSubCom);
		super.save(newprpDcompanyGrade);
	}

	public void clearPrpDcompanyGrade() {
		super.clear();
	}
	
	/**
	 * 删除一条机构时同步删除机构级别表
	 * 步骤：
	 * 1.删除当前机构的上级机构与之的关联
	 * 2.删除当前机构的下级机构与之的关联
	 * 3.如果下级机构不为空，则修改当前机构的直接上级机构和当前机构的直接下级机构设置是否为直接下级机构为 '1'
	 * */
	@SuppressWarnings("unchecked")
	public void deletePrpDcompanyGrade(PrpDcompany prpDcompany) {
		if(prpDcompany==null){
			return;
		}
		PrpDcompanyService prpDcompanyService=(PrpDcompanyService) applicationContext.getBean("prpDcompanyService");
		String currComCode = prpDcompany.getComCode();//currComCode当前操作机构代码
		String upperComCode = prpDcompany.getUpperComCode();//upperComCode当前机构的上级机构
		List<PrpDcompany> subCompanys = prpDcompanyService.getSubCode(currComCode);
		removeUpRelation(currComCode);
		removeSubRelation(currComCode);
		changedeleteRelation(upperComCode,subCompanys);
	}

	/**删除当前机构的上级机构与之的关联*/
	private void removeSubRelation(String currComCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("from prpDcompanyGrade where subComCode = '");
		sql.append(currComCode);
		sql.append("'");
		List<PrpDcompanyGrade> grade =  super.findByHql(sql.toString());
		super.deleteAll(grade);
	}
	/**删除当前机构的下级机构与之的关联*/
	private void removeUpRelation(String currComCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("from prpDcompanyGrade where comcode = '");
		sql.append(currComCode);
		sql.append("'");
		List<PrpDcompanyGrade> grade = super.findByHql(sql.toString());
		super.deleteAll(grade);
	}
	
	/**修改当前机构的直接上级机构和当前机构的直接下级机构设置是否为直接下级机构为 '1' <strong>会修改多条记录</strong>*/
	private void changedeleteRelation(String upperComCode, List<PrpDcompany> subCompanys) {
		StringBuffer sql = new StringBuffer();
		sql.append("from prpDcompanyGrade where comcode = '");
		sql.append(upperComCode);
		sql.append("' and subcomcode in (");
		for (int i = 0;i<subCompanys.size();i++){
			String temp = subCompanys.get(i).getComCode();
			sql.append("'");
			sql.append(temp);
			sql.append("'");
			sql.append(",");
		}
		if(subCompanys.size()>0){
			sql.deleteCharAt(sql.length()-1);//将sql中最后一个符号 ',' 号去掉
		}
		sql.append(")");
		List<PrpDcompanyGrade> prpDcompanyGradeList = super.findByHql(sql.toString());
		//TODO UPDATE
	}

	/**
	 * 增加机构
	 *注意：prpDcompany 不能为空，如果为空则直接返回。并且上级代码要与实际对应。
	 *步骤
	 *  1.增加当前机构和上级机构的对应关系
	 *  2.增加当前机构和下级机构的对应关系（如果为空则为叶子节点）
	 *  3.如果下级不为空，则修改当前机构的直接上级机构和当前机构的直接下级机构设置是否为直接下级机构为 '0'，只需修改一条记录。
	 * */
	public void insertPrpDcompanyGrade(PrpDcompany prpDcompany,String subComCode) {
		if(prpDcompany==null){
			return ;
		}
		PrpDcompanyService prpDcompanyService=(PrpDcompanyService) applicationContext.getBean("prpDcompanyService");
		String upperComCode = prpDcompany.getUpperComCode();
		PrpDcompany subcompany= prpDcompanyService.getPrpDcompany(subComCode);//subCompany 当前机构的下级机构
		List<PrpDcompany> subCompanys = new ArrayList<PrpDcompany>();
		subCompanys.add(subcompany);
		insertUpRelation();
		insertSubRelation();
		changeinsertRelation(upperComCode,subCompanys);
	}
	
	/**增加当前机构和上级机构的对应关系*/
	private void insertSubRelation() {
		// TODO Auto-generated method stub
	}
	/**增加当前机构和下级机构的对应关系（如果为空则为叶子节点）*/
	private void insertUpRelation() {
		// TODO Auto-generated method stub
	}
	/**修改当前机构的直接上级机构和当前机构的直接下级机构设置是否为直接下级机构为 '0'*/
	private void changeinsertRelation(String upperComCode,
			List<PrpDcompany> subCompanys) {
		// TODO Auto-generated method stub
	}
}
