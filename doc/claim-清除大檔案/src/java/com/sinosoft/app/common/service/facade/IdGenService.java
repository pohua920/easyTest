/**
 * 序列生成器接口
 *
 */

package com.sinosoft.app.common.service.facade;

public interface IdGenService {

    //功能Task编号
    public static final String SAA_TASKNO = "SaaTaskNoAdd";
	//部门计划编号
	public static final String WPS_PDEPART = "WpsPDepart";
	//部门计划明细编号
	public static final String WPS_PDEPARTDETAIL = "WpsPDepartDetail";
	//部门副职计划编号
	public static final String WPS_PVDEPART = "WpsPVDepart";
	//部门副职计划明细编号
	public static final String WPS_PVDEPARTDETAIL = "WpsPVDepartDetail";
	
    //个人计划编号
	public static final String WPS_PPERSONAL = "WpsPPersonal";
    //个人计划明细编号
	public static final String WPS_PPERSONALDETAIL = "WpsPPersonalDetail";
	//处室计划编号
	public static final String WPS_POFFICE="WpsPOffice";
	//处室计划明细编号
	public static final String WPS_POFFICEDETAIL="WpsPOfficeDetail";
	//部门总结编号
	public static final String WPS_CDEPART = "WpsCDepart";
	//部门总结明细编号
	public static final String WPS_CDEPARTDETAIL = "WpsCDepartDetail";
	//部门副职总结编号
	public static final String WPS_CVDEPART = "WpsCVDepart";
	//部门副职总结明细编号
	public static final String WPS_CVDEPARTDETAIL = "WpsCVDepartDetail";
	//部门总结附录编号
	public static final String WPS_CDEPARTAPPEND = "WpsCDepartAppend";
	//处室总结编号
	public static final String WPS_COFFICE="WpsCOffice";
	//处室总结明细编号
	public static final String WPS_COFFICEDETAIL="WpsCOfficeDetail";
	//处室总结附录编号
	public static final String WPS_COFFICEAPPEND = "WpsCOfficeAppend";
	//个人总结编号
	public static final String WPS_CPERSONAL = "WpsCPersonal";
    //个人总结明细编号
	public static final String WPS_CPERSONALDETAIL = "WpsCPersonalDetail";
	//活动量填写--机构拜访编号
	public static final String WPS_COMPANYVISITINFO="WpsCompanyVisitInfo";
	//活动量填写--车商(经代)编号
	public static final String WPS_CARDEALERVISITINFO="WpsCarDealerVisitInfo";
	//活动量填写--银保拜访编号
	public static final String WPS_BANKVISITINFO="WpsBankVisitInfo";
	//活动量填写--重客拜访编号
	public static final String WPS_CUSTOMERVISITINFO="WpsCustomerVisitInfo";
	//周工作填写 --计划编号
	public static final String WPS_PWEEKWORK="WpsPWeekWork";
	//周工作填写 --总结编号
	public static final String WPS_CWEEKWORK="WpsCWeekWork";
	//周工作填写 --工作编号
	public static final String WPS_WEEKWORK="WpsWeekWork";
	//事项管理 -个人事项管理个人轨迹编号
	public static final String WPS_PERSONALDETAILHIS="WpsPersonalDetailHis";
	//事项管理-处室事项管理处室轨迹编号 
	public static final String WPS_OFFICEDETAILHIS="WpsOfficeDetailHis";
	//事项管理-部门级事项管理部门级轨迹编号 
	public static final String WPS_VICEDEPDETAILHIS="WpsViceDepDetailHis";
	//事项管理-部门事项管理部门轨迹编号 
	public static final String WPS_DEPARTDETAILHIS="WpsDepartDetailHis";
	
	//邮件发送提醒
	public static final String SENDMESREMARK="SendMesRemark";
	//过程管理主表
	public static final String WPS_DEPARTHIS="WpsDepartHis";
	
	/**
	 * 根据类型获取主键
	 * @param idType
	 * @return String
	 */
	public String getId(final String idType) throws Exception;
     
    
}
