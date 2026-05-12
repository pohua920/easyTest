/**
 * 序列生成器
 *
 */
package com.sinosoft.app.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.springframework.orm.hibernate3.HibernateCallback;

import com.sinosoft.app.common.service.facade.IdGenService;
import com.sinosoft.app.common.vo.IdSeq;

@SuppressWarnings("unchecked")
public class IdGenServiceSpringImpl extends GenericDaoHibernate implements IdGenService {
	private static Map<String, IdSeq> IDSeqMap;
	static {
		// 主键类型对应的序列名称
		IDSeqMap = new HashMap<String, IdSeq>();
		//功能Task编号
		IDSeqMap.put(SAA_TASKNO, new IdSeq("SEQ_SAA_TASK","",18));
		//部门计划编号
		IDSeqMap.put(WPS_PDEPART, new IdSeq("SEQ_WPS_P_DEPART","PDE",17));
		//部门计划明细编号
		IDSeqMap.put(WPS_PDEPARTDETAIL, new IdSeq("SEQ_WPS_P_DEPARTDETAIL","PDD",17));
		//部门副职计划编号
		IDSeqMap.put(WPS_PVDEPART, new IdSeq("SEQ_WPS_P_VDEPART","PVE",17));
		//部门副职计划明细编号
		IDSeqMap.put(WPS_PVDEPARTDETAIL, new IdSeq("SEQ_WPS_P_VDEPARTDETAIL","PVD",17));
		//个人计划编号
		IDSeqMap.put(WPS_PPERSONAL, new IdSeq("SEQ_WPS_P_PERSONAL","PPE",17));
		//个人计划明细编号
		IDSeqMap.put(WPS_PPERSONALDETAIL, new IdSeq("SEQ_WPS_P_PERSONALDETAIL","PPD",17));
		//处室计划编号
		IDSeqMap.put(WPS_POFFICE, new IdSeq("SEQ_WPS_P_OFFICE","POF",17));
		//处室计划明细编号
		IDSeqMap.put(WPS_POFFICEDETAIL, new IdSeq("SEQ_WPS_P_OFFICEDETAIL","POD",17));
		//部门总结编号
		IDSeqMap.put(WPS_CDEPART, new IdSeq("SEQ_WPS_C_DEPART","CDE",17));
		//部门总结明细编号
		IDSeqMap.put(WPS_CDEPARTDETAIL, new IdSeq("SEQ_WPS_C_DEPARTDETAIL","CDD",17));
		//部门总结编号
		IDSeqMap.put(WPS_CVDEPART, new IdSeq("SEQ_WPS_C_VDEPART","CVE",17));
		//部门总结明细编号
		IDSeqMap.put(WPS_CVDEPARTDETAIL, new IdSeq("SEQ_WPS_C_VDEPARTDETAIL","CVD",17));
		//部门总结附录编号
		IDSeqMap.put(WPS_CDEPARTAPPEND, new IdSeq("SEQ_WPS_C_DEPARTAPPEND", "CDA", 17));
		//个人总结编号
		IDSeqMap.put(WPS_CPERSONAL, new IdSeq("SEQ_WPS_C_PERSONAL","CPE",17));
		//个人总结明细编号
		IDSeqMap.put(WPS_CPERSONALDETAIL, new IdSeq("SEQ_WPS_C_PERSONALDETAIL","CPD",17));
		//处室总结编号
		IDSeqMap.put(WPS_COFFICE,  new IdSeq("SEQ_WPS_C_OFFICE", "COF", 17));
		//处室总结明细编号
		IDSeqMap.put(WPS_COFFICEDETAIL,  new IdSeq("SEQ_WPS_C_OFFICEDETAIL", "COD", 17));
		//处室总结附录编号
		IDSeqMap.put(WPS_COFFICEAPPEND,  new IdSeq("SEQ_WPS_C_OFFICE", "COA", 17));
	
		
		//活动量填写--机构拜访编号
		IDSeqMap.put(WPS_COMPANYVISITINFO,  new IdSeq("SEQ_WPS_COMPANY_VISITINFO", "COV", 17));
		//活动量填写--车商(经代)编号
		IDSeqMap.put(WPS_CARDEALERVISITINFO,  new IdSeq("SEQ_WPS_CARDEALER_VISITINFO", "CDV", 17));
		//活动量填写--银保拜访编号
		IDSeqMap.put(WPS_BANKVISITINFO,  new IdSeq("SEQ_WPS_BANK_VISITINFO", "BKV", 17));
		//活动量填写--重客拜访编号
		IDSeqMap.put(WPS_CUSTOMERVISITINFO,  new IdSeq("SEQ_WPS_CUSTOMER_VISITINFO", "CMV", 17));
		//周工作填写 --计划编号
		IDSeqMap.put(WPS_PWEEKWORK,  new IdSeq("SEQ_WPS_P_WEEKWORK", "PWW", 17));
		//周工作填写 --总结编号
		IDSeqMap.put(WPS_CWEEKWORK,  new IdSeq("SEQ_WPS_C_WEEKWORK", "CWW", 17));
		//周工作填写 --工作编号
		IDSeqMap.put(WPS_WEEKWORK,  new IdSeq("SEQ_WPS_WEEKWORK", "WWK", 17));
		
		//事项管理 个人事项轨迹编号
		IDSeqMap.put(WPS_PERSONALDETAILHIS,  new IdSeq("SEQ_WPS_PERSONALDETAILHIS", "PDH", 17));
		//事项管理处室事项轨迹编号 
		IDSeqMap.put(WPS_OFFICEDETAILHIS,  new IdSeq("SEQ_WPS_OFFICEDETAILHIS", "ODH", 17));
		//事项管理部门级事项轨迹编号 
		IDSeqMap.put(WPS_VICEDEPDETAILHIS,  new IdSeq("SEQ_WPS_VICEDEPDETAILHIS", "VDH", 17));
		//事项管理部门事项轨迹编号 
		IDSeqMap.put(WPS_DEPARTDETAILHIS,  new IdSeq("SEQ_WPS_DEPARTDETAILHIS", "DDH", 17));
		
		//邮件发送提醒
		IDSeqMap.put(SENDMESREMARK,  new IdSeq("SEQ_SendMesRemark", "", 20));
		//过程管理主表
		IDSeqMap.put(WPS_DEPARTHIS,  new IdSeq("SEQ_WpsDepartHis", "WDH", 17));
	}

	/**
	 * 
	 * 根据id类型查询主键
	 * 
	 * @param idType
	 */
	public String getId(final String idType) throws Exception{
        String seqNo = (String) this.getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws SQLException, HibernateException {
                        SQLQuery query = session.createSQLQuery("select lpad ("
                                + IDSeqMap.get(idType).getIdSeqName()
                                + ".nextval,"+IDSeqMap.get(idType).getIdLength()+",'0') SEQID from dual");
                        query.addScalar("SEQID",
                                new org.hibernate.type.StringType());
                        List children = query.list();
                        return (String) children.iterator().next();
                    }
                });
        return IDSeqMap.get(idType).getIdHead()+seqNo;
    }
}
