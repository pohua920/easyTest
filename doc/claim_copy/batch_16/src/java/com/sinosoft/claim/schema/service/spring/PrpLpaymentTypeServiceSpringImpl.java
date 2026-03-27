package com.sinosoft.claim.schema.service.spring;

/**
 * 给付类别service
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.app.common.util.StringUtil;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLpaymentType;
import com.sinosoft.claim.schema.model.PrpLpaymentTypeId;
import com.sinosoft.claim.schema.service.facade.PrpLpaymentTypeService;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class PrpLpaymentTypeServiceSpringImpl extends GenericDaoHibernate<PrpLpaymentType, PrpLpaymentTypeId> implements PrpLpaymentTypeService {

	/**
	 * 保存给付类别信息
	 * @param prpLpaymentType ：给付类别信息
	 */
	@Override
	public void save(PrpLpaymentType prpLpaymentType) throws Exception {
		super.save(prpLpaymentType);

	}
	/**
	 * 保存给付类别信息
	 * @param list  :给付类别信息集合
	 * @throws Exception
	 */
	@Override
	public void save(List<PrpLpaymentType> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 删除给付类别
	 * @param prpLpaymentTypeId ：给付类别信息主键
	 */
	@Override
	public void delete(PrpLpaymentTypeId prpLpaymentTypeId) throws Exception {
		super.deleteByPK(PrpLpaymentType.class, prpLpaymentTypeId);
	}
	/**
	 * 根据主键查询给付类别信息
	 * @param prpLpaymentTypeId ：给付类别信息ID
	 * @return 给付类别信息
	 */
	@Override
	public PrpLpaymentType findPrpLpaymentType(PrpLpaymentTypeId prpLpaymentTypeId) throws Exception {
		return super.get(PrpLpaymentType.class, prpLpaymentTypeId);
	}
	/**
	 * 根据查询对象获取 给付类别信息
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的给付类别信息集合
	 */
	@Override
	public Page findPrpLpaymentType(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}
	/**
	 * 根据查询对象获取 给付类别信息
	 * @param queryRule 查询对象
	 * @return 包含的 给付类别信息 的集合
	 */
	@Override
	public List<PrpLpaymentType> findPrpLpaymentType(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 查询给付类别
	 * @param prpLpaymentType 查询条件
	 * @param codeType 查询那个给付类别
	 * @param pageNo 当前页数
	 * @param pageSize 总页数
	 * @return
	 * @throws Exception
	 */
	public List<PrpLpaymentType> findPrpLpaymentType(PrpLpaymentType prpLpaymentType,String codeType,int pageNo,int pageSize)throws Exception{
		StringBuffer sql = new StringBuffer("");
		List<PrpLpaymentType> paymentTypeList = new ArrayList<PrpLpaymentType>();
		PrpLpaymentType paymentTypeTemp = null;
		if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())&&prpLpaymentType.getContractingScope().length()>2){
			prpLpaymentType.setContractingScope(prpLpaymentType.getContractingScope().substring(0,2));
		}
		if("paymentType".equals(codeType)){
			sql.append("select distinct type from  PrpLpaymentType where validStatus='1' ");
			if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())){
				sql.append(" and contractingScope ='"+ prpLpaymentType.getContractingScope()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getId().getType())){
				sql.append(" and type like '"+prpLpaymentType.getId().getType()+"%'");
			}
			sql.append(" order by type");
		}else if("paymentType1".equals(codeType)){
			sql.append("select distinct type,type1 from  PrpLpaymentType where validStatus='1' ");
			if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())){
				sql.append(" and contractingScope ='"+ prpLpaymentType.getContractingScope()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getId().getType())){
				sql.append(" and type ='"+prpLpaymentType.getId().getType()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getType1())){
				sql.append(" and type1 like '"+prpLpaymentType.getType1()+"%'");
			}
			sql.append(" order by type1");
		}else if("paymentType2".equals(codeType)){
			sql.append("select distinct type,type1,type2,content,injuryGrade,paymentRate from  PrpLpaymentType where validStatus='1' ");
			if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())){
				sql.append(" and contractingScope ='"+ prpLpaymentType.getContractingScope()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getId().getType())){
				sql.append(" and type ='"+prpLpaymentType.getId().getType()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getType1())){
				sql.append(" and type1 ='"+prpLpaymentType.getType1()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getType2())){
				sql.append(" and type2 like '"+prpLpaymentType.getType2()+"%'");
			}
			sql.append(" order by type2");
		}else{
			throw new UserException(-98,-1003,"給付類別錯誤","給付類別錯誤");
		}
		List<?> temp = null;
		if(pageSize>0){
			temp = HibernateUtils.findbySql(super.getSession(), sql.toString(), pageNo, pageSize);
		}else{
			temp = HibernateUtils.findbySql(super.getSession(), sql.toString());
		}
		int length = -1;
		Object[] strTemp = null;
		for(int i=0;i<temp.size();i++){
			paymentTypeTemp = new PrpLpaymentType();
			paymentTypeTemp.setContractingScope(prpLpaymentType.getContractingScope());
			length = -1;
			if(temp.get(i) instanceof Object[]){
				strTemp = (Object[]) temp.get(i);
				length = strTemp.length;
			}
			paymentTypeTemp.getId().setType(length>0? (String)strTemp[0] : (String)temp.get(i));
			paymentTypeTemp.setType1(length>1? (String)strTemp[1] : "");
			paymentTypeTemp.setType2(length>2? (String)strTemp[2] : "");
			paymentTypeTemp.setContent(length>3? (String)strTemp[3] : "");
			paymentTypeTemp.setInjuryGrade(length>4? (String)strTemp[4] : "");
			paymentTypeTemp.setPaymentRate(length>5? ((Number)strTemp[5]).doubleValue() : 1D);
			paymentTypeList.add(paymentTypeTemp);
		}
		return paymentTypeList;
	}
	/**
	 * 查询给付类别
	 * @param prpLpaymentType 查询条件
	 * @param codeType 查询那个给付类别
	 * @param pageNo 当前页数
	 * @param pageSize 总页数
	 * @return
	 * @throws Exception
	 */
	public List<PrpLpaymentType> getPrpLpaymentType(PrpLpaymentType prpLpaymentType,String codeType)throws Exception{
		StringBuffer sql = new StringBuffer("");
		List<PrpLpaymentType> paymentTypeList = new ArrayList<PrpLpaymentType>();
		List<?> temp = null;
		PrpLpaymentType paymentTypeTemp = null;
		Object[] strTemp = null;
		if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())&&prpLpaymentType.getContractingScope().length()>2){
			prpLpaymentType.setContractingScope(prpLpaymentType.getContractingScope().substring(0,2));
		}
		if("paymentType".equals(codeType)){
			sql.append("select distinct type from  PrpLpaymentType where validStatus='1' ");
			if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())){
				sql.append(" and contractingScope ='"+ prpLpaymentType.getContractingScope()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getId().getType())){
				sql.append(" and type ='"+prpLpaymentType.getId().getType()+"'");
			}
			temp = HibernateUtils.findbySql(super.getSession(), sql.toString());
			for(int i=0;i<temp.size();i++){
				paymentTypeTemp = new PrpLpaymentType();
				paymentTypeTemp.setContractingScope(prpLpaymentType.getContractingScope());
				paymentTypeTemp.getId().setType((String)temp.get(i));
				paymentTypeTemp.setType1("");
				paymentTypeTemp.setType2("");
				paymentTypeTemp.setContent("");
				paymentTypeTemp.setInjuryGrade("");
				paymentTypeList.add(paymentTypeTemp);
			}
		}else if("paymentType1".equals(codeType)){
			sql.append("select distinct type,type1 from  PrpLpaymentType where validStatus='1' ");
			if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())){
				sql.append(" and contractingScope ='"+ prpLpaymentType.getContractingScope()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getId().getType())){
				sql.append(" and type ='"+prpLpaymentType.getId().getType()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getType1())){
				sql.append(" and type1 = '"+prpLpaymentType.getType1()+"'");
			}
			temp = HibernateUtils.findbySql(super.getSession(), sql.toString());
			for(int i=0;i<temp.size();i++){
				paymentTypeTemp = new PrpLpaymentType();
				paymentTypeTemp.setContractingScope(prpLpaymentType.getContractingScope());
				strTemp = (Object[]) temp.get(i);
				paymentTypeTemp.getId().setType((String)strTemp[0]);
				paymentTypeTemp.setType1((String)strTemp[1]);
				paymentTypeTemp.setType2("");
				paymentTypeTemp.setContent("");
				paymentTypeTemp.setInjuryGrade("");
				paymentTypeList.add(paymentTypeTemp);
			}
		}else if("paymentType2".equals(codeType)){
			sql.append("select distinct type,type1,type2,content,injuryGrade,paymentRate from  PrpLpaymentType where validStatus='1' ");
			if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())){
				sql.append(" and contractingScope ='"+ prpLpaymentType.getContractingScope()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getId().getType())){
				sql.append(" and type ='"+prpLpaymentType.getId().getType()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getType1())){
				sql.append(" and type1 ='"+prpLpaymentType.getType1()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getType2())){
				sql.append(" and type2 = '"+prpLpaymentType.getType2()+"'");
			}
			temp = HibernateUtils.findbySql(super.getSession(), sql.toString());
			for(int i=0;i<temp.size();i++){
				paymentTypeTemp = new PrpLpaymentType();
				paymentTypeTemp.setContractingScope(prpLpaymentType.getContractingScope());
				strTemp = (Object[]) temp.get(i);
				paymentTypeTemp.getId().setType((String)strTemp[0]);
				paymentTypeTemp.setType1((String)strTemp[1]);
				paymentTypeTemp.setType2((String)strTemp[2]);
				paymentTypeTemp.setContent((String)strTemp[3]);
				paymentTypeTemp.setInjuryGrade((String)strTemp[4]);
				paymentTypeTemp.setPaymentRate(((Number)strTemp[5]).doubleValue());
				paymentTypeList.add(paymentTypeTemp);
			}
		}else{
			throw new UserException(-98,-1003,"給付類別錯誤","給付類別錯誤");
		}
		return paymentTypeList;
	}
	/**
	 * 验证输入的给付类别是否存在
	 * @param prpLpaymentType 给付类别
	 * @param codeType 给付类型
	 * @return
	 * @throws Exception
	 */
	public Long countPrpLpaymentType(PrpLpaymentType prpLpaymentType,String codeType)throws Exception{
		StringBuffer sql = new StringBuffer("");
		if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())&&prpLpaymentType.getContractingScope().length()>2){
			prpLpaymentType.setContractingScope(prpLpaymentType.getContractingScope().substring(0,2));
		}
		if("paymentType".equals(codeType)){
			sql.append("select distinct type from  PrpLpaymentType where 1=1 ");
			if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())){
				sql.append(" and contractingScope ='"+ prpLpaymentType.getContractingScope()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getId().getType())){
				sql.append(" and type = '"+prpLpaymentType.getId().getType()+"'");
			}
		}else if("paymentType1".equals(codeType)){
			sql.append("select distinct type,type1 from  PrpLpaymentType where 1=1 ");
			if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())){
				sql.append(" and contractingScope ='"+ prpLpaymentType.getContractingScope()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getId().getType())){
				sql.append(" and type ='"+prpLpaymentType.getId().getType()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getType1())){
				sql.append(" and type1 = '"+prpLpaymentType.getType1()+"'");
			}
		}else if("paymentType2".equals(codeType)){
			sql.append("select distinct type,type1,type2,content,injuryGrade from  PrpLpaymentType where 1=1 ");
			if(StringUtil.isNotBlank(prpLpaymentType.getContractingScope())){
				sql.append(" and contractingScope ='"+ prpLpaymentType.getContractingScope()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getId().getType())){
				sql.append(" and type ='"+prpLpaymentType.getId().getType()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getType1())){
				sql.append(" and type1 ='"+prpLpaymentType.getType1()+"'");
			}
			if(StringUtil.isNotBlank(prpLpaymentType.getType2())){
				sql.append(" and type2 = '"+prpLpaymentType.getType2()+"'");
			}
		}else{
			throw new UserException(-98,-1003,"給付類別錯誤","給付類別錯誤");
		}
		return HibernateUtils.getCountbySql(super.getSession(), sql.toString());
	}

}
