package com.sinosoft.claim.schema.service.spring;

/**
 * 赔案保单关联接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.PrplregistrpolicyId;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.util.StringConvert;

public class PrplregistrpolicyServiceSpringImpl extends GenericDaoHibernate<Prplregistrpolicy, PrplregistrpolicyId> implements PrplregistrpolicyService {

	/**
	 * 保存赔案保单关联信息
	 * @param prplregistrpolicy ：传入的赔案保单关联
	 */
	@Override
	public void save(Prplregistrpolicy prplregistrpolicy) throws Exception {
		logger.info("保存赔案保单关联信息");
		super.save(prplregistrpolicy);
	}

	/**
	 * 保存赔案保单关联信息
	 * @param list:保存赔案保单关联信息
	 */
	@Override
	public void save(List<Prplregistrpolicy> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @param prpLregist
	 * @throws Exception 保存或修改，
	 */
	public void saveOrUpdate(List<Prplregistrpolicy> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	public void saveOrUpdate(Prplregistrpolicy prplregistrpolicy) throws Exception {
		super.getSession().merge(prplregistrpolicy);
	}

	/**
	 * 删除赔案保单关联信息
	 * @param prplregistrpolicyId ：传入的赔案保单关联编号
	 */
	@Override
	public void delete(PrplregistrpolicyId prplregistrpolicyId) throws Exception {
		super.deleteByPK(prplregistrpolicyId);
		logger.info("删除赔案保单关联编号为" + prplregistrpolicyId + "的赔案保单关联信息");
	}

	/**
	 * @description: 赔案保单关联修改
	 * @param Prplregistrpolicy prplregistrpolicy
	 * @throws Exception
	 */
	@Override
	public void update(Prplregistrpolicy prplregistrpolicy) {
		logger.info("修改赔案保单关联信息开始");
		super.update(prplregistrpolicy);
		logger.info("修改赔案保单关联信息结束");
	}

	/**
	 * 根据赔案保单关联编号查询出赔案保单关联信息
	 * @param prplregistrpolicyId ：传入的赔案保单关联编号
	 * @return 返回赔案保单关联
	 */
	@Override
	public Prplregistrpolicy findPrplregistrpolicy(PrplregistrpolicyId prplregistrpolicyId) throws Exception {
		logger.info("查询赔案保单关联编号为" + prplregistrpolicyId + "的赔案保单关联信息");
		return super.get(Prplregistrpolicy.class, prplregistrpolicyId);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔案保单关联页面信息
	 */
	@Override
	public Page findPrplregistrpolicy(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取赔案保单关联列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<Prplregistrpolicy> findPrplregistrpolicy(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * @param registNo
	 * @return
	 * @throws Exception 更具报案号查询关联信息
	 */
	public List<Prplregistrpolicy> findByRegistNo(String registNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		return super.find(queryRule);
	}

	/**
	 * @param registNo
	 * @throws Exception 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception {
		String sql = "delete from Prplregistrpolicy where registNo='" + registNo + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}

	/**
	 * @param registNo
	 * @return
	 * @throws Exception 根据报案号判断是否关联报案
	 */
	public boolean isCompelFlag(String registNo) throws Exception {
		// String hql = "from Prplregistrpolicy where registNo=?";
		String sql = "select count(1) from Prplregistrpolicy where registNo='" + registNo + "'";
		long count = HibernateUtils.getCountbyCountSql(super.getSession(), sql);
		if (count > 1) {
			return true;
		}
		return false;
	}

	/**
	 * 以保单号组织查询到的报案号信息
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public List<String> getRegistNoByPolicyNo(String policyNo, String policyNoSign) throws Exception {
		String statement = "select distinct(registno) from " + " prplregistrpolicy where 1=1 " + StringConvert.convertString(" policyNo", policyNo, policyNoSign);
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		List<String> registNoList = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			Iterator<?> it = list.iterator();
			while (it.hasNext()) {
				registNoList.add(String.valueOf(it.next()));
			}
		}
		return registNoList;
	}

	/**
	 * 以赔案（立案）号组织查询到的报案号信息
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public List<String> getRegistNoByClaimNo(String claimNo, String claimNoSign) throws Exception {
		String statement = "select distinct(registno) from " + "prplregistrpolicy where 1=1 " + StringConvert.convertString(" claimNo", claimNo, claimNoSign);
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		List<String> registNoList = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			Iterator<?> it = list.iterator();
			while (it.hasNext()) {
				registNoList.add(String.valueOf(it.next()));
			}
		}
		return registNoList;
	}
	/**
	 * 以强制保险证号 组织查询到的报案号信息
	 */
	@Override
	public List<String> getRegistNoByPrintNo(String printNo, String printNoSign) throws Exception {
		String statement = "select distinct(prplregistrpolicy.registno) from prplregistrpolicy,(select * from prpcmain where prpcmain.printno =?) c  where prplregistrpolicy.policyno = c.policyno ";
		List<?> list = super.getSession().createSQLQuery(statement).setString(0, printNo).list();
		List<String> registNoList = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			Iterator<?> it = list.iterator();
			while (it.hasNext()) {
				registNoList.add(String.valueOf(it.next()));
			}
		}
		return registNoList;
	}
	/**
	 * 根据被保险人ID查询保单
	 */
	@Override
	public String getPolicyNoByInsuredIdentifyNumber(String identifyNumber) throws Exception {
		String statement = "select distinct(policyno) from prpcinsured where insuredFlag='1' and identifynumber = ?";
		List<?> list = super.getSession().createSQLQuery(statement).setString(0, identifyNumber).list();
		if (list != null && !list.isEmpty()) {
			Iterator<?> it = list.iterator();
			String tempStr = "";
			while (it.hasNext()) {
				tempStr +=" policyno = '" + String.valueOf(it.next())+"' or";
			}
			return tempStr.substring(0, tempStr.lastIndexOf("or"));
		}
		return null;
	}
	/**
	 * 根据任意保險卡號查询保单
	 */
	public List<String> getPolicyNoByVisaCodeBI(String visaCodeBI,String visaCodeBISign) throws Exception {
		String statement = "select distinct(prplregistrpolicy.registno) from prplregistrpolicy,(select * from prpcmain where 1=1 "
			+StringConvert.convertString(" visaCodeBI", visaCodeBI, visaCodeBISign)+") c  where prplregistrpolicy.policyno = c.policyno ";
		List<?> list = super.getSession().createSQLQuery(statement).list();
		List<String> registNoList = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			Iterator<?> it = list.iterator();
			while (it.hasNext()) {
				registNoList.add(String.valueOf(it.next()));
			}
		}
		return registNoList;
	}

	/**
	 * 根据三者车车牌号查询报案号信息
	 */
	@Override
	public List<String> getRegistNoByThirdLicenseNo(String thirdLicenseNo, String thirdLicenseNoSign) {
		String statement = "select distinct(registno) from prpLthirdParty where Licenseno = ?";
		List<?> list = super.getSession().createSQLQuery(statement).setString(0, thirdLicenseNo).list();
		List<String> registNoList = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			Iterator<?> it = list.iterator();
			while (it.hasNext()) {
				registNoList.add(String.valueOf(it.next()));
			}
		}
		return registNoList;
	}

	@Override
	public List<String> getRegistNoByPersonIdentifyNumber(String identifyNumber, String identifyNumberSign) {
		String statement = "select distinct(claim.registno) from prplclaim claim,prplcompensate comp,prplpersonloss loss where claim.claimno=comp.claimno and comp.compensateno = loss.compensateno and loss.identifynumber =?";
		List<?> list = super.getSession().createSQLQuery(statement).setString(0, identifyNumber).list();
		List<String> registNoList = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			Iterator<?> it = list.iterator();
			while (it.hasNext()) {
				registNoList.add(String.valueOf(it.next()));
			}
		}
		return registNoList;
	}

	@Override
	public String getSharingRegistNo(String policyNo,PrpLregist tempPrpLregist) {
		String statement = "select distinct(r.registno) from Prplregistrpolicy rp,prplregist r where rp.registno =r.registno and rp.validstatus='1' and r.sharingflag = '1' and rp.policyno = ? and r.registno!=? order by r.registno asc";
		Query query = super.getSession().createSQLQuery(statement);
		query.setString(0, policyNo);
		query.setString(1, tempPrpLregist.getRegistNo());
		List<?> list = query.list();
		if (list != null && !list.isEmpty()) {//存在同业共摊、再判断此保单在数据库中是否存在与当前备案相同出险时间的备案(忽略已注销的备案)
			statement = "select distinct(r.registno) from Prplregistrpolicy rp,prplregist r where rp.registno =r.registno and rp.validstatus='1' and rp.policyno = ?  and r.damagestartdate = ? and r.damagestarthour=? and r.registno!=? order by r.registno asc";
			query = super.getSession().createSQLQuery(statement);
			query.setString(0, policyNo);
			query.setDate(1, tempPrpLregist.getDamageStartDate());
			query.setString(2, tempPrpLregist.getDamageStartHour());
			query.setString(3, tempPrpLregist.getRegistNo());
			List<?> tempList = query.list();
			if(tempList != null && !tempList.isEmpty()){
				return String.valueOf(list.get(0));
			}
		}
		return "";
	}
	/**
	 * 根据报案号查询流程id
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public String findSwfLogId (String registNo) throws Exception{
		String swfLogId = "";
		String statement = "select distinct(flowId) from swfFlowMain where flowName = '"+registNo+"'";
		List<?> list = super.getSession().createSQLQuery(statement).list();
		for(Object obj : list){
			if(obj!=null){
				swfLogId = String.valueOf(obj);
			}
		}
		return swfLogId;
	}
	/**
     * webservice 根據身份證號碼，險种查詢保單號
     */
    @Override
    public String findPolicyNoByIdentifyNumberCode(String identifyNumber,String code) throws Exception {
        String statement = "";
        if(code == "" || "".equals(code) || code == null){
            statement = "select distinct(policyno) from prpcinsured where insuredFlag='1' and identifynumber = '"+identifyNumber+"' "; 
        }else if("D".equals(code)){
            statement = "select distinct(p.policyno) from prpcinsured p,prpcopymain m where p.policyno = m.policyno and m.classCode in ('A','B') and p.insuredFlag='1' and p.identifynumber = '"+identifyNumber+"' ";
        }else if("C1".equals(code)){
            statement = "select distinct(p.policyno) from prpcinsured p,prpcopymain m where p.policyno = m.policyno and m.classCode='"+code+"' and p.insuredFlag='1' and p.identifynumber = '"+identifyNumber+"' ";
        }else{
            statement = "select distinct(p.policyno) from prpcinsured p,prpcopymain m where p.policyno = m.policyno and m.riskCode='"+code+"' and p.insuredFlag='1' and p.identifynumber = '"+identifyNumber+"' ";
        }
        List<?> list = super.getSession().createSQLQuery(statement).list();
       // List<?> list = super.getSession().createSQLQuery(statement).setString(0, identifyNumber).list();
        if (list != null && !list.isEmpty()) {
            Iterator<?> it = list.iterator();
            String tempStr = "";
            while (it.hasNext()) {
                tempStr +=" policyno = '" + String.valueOf(it.next())+"' or";
            }
            return tempStr.substring(0, tempStr.lastIndexOf("or"));
        }
        return null;
    }
}