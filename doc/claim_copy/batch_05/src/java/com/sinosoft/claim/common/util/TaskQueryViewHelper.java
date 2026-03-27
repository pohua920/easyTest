package com.sinosoft.claim.common.util;

import ins.framework.common.Page;
import ins.framework.utils.DataUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.vo.ClaimTaskDto;
import com.sinosoft.claim.common.vo.UndwrtTaskInfoDto;
import com.sinosoft.claim.common.vo.UndwrtTaskPayInfoDto;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;

public class TaskQueryViewHelper {

	private CommonService commonService;
	private CodeService codeService;

	/***
	 * 立案作业查询
	 * @param request
	 * @param searchType 
	 * @param pageNo
	 * @param pageSize
	 * @throws Exception 
	 */
	public void queryClaimTask(HttpServletRequest request, String searchType, int pageNo, int pageSize) throws Exception {
		// 立案日期
		String strClaimDateStart = request.getParameter("ClaimDateStart");
		String strClaimDateEnd = request.getParameter("ClaimDateEnd");
		// 出險日期
		String strDamageDateStart = request.getParameter("DamageDateStart");
		String strDamageDateEnd = request.getParameter("DamageDateEnd");
		// 處理單位
		String strDeptCode = request.getParameter("DeptCode");
		// 險種 0全險種
		String strRiskType = request.getParameter("RiskType");
		// 狀態 0全部1未核賠2已核賠
		String strStatus = request.getParameter("Status");
		// 呈現方式 0彙整1險種分項
		String strPresentType = request.getParameter("PresentType");
		String searchFlag = request.getParameter("searchFlag");
		Object preStatements = request.getSession().getAttribute("ClaimTaskStatements");
		StringBuffer statements = new StringBuffer("");
		if (DataUtils.emptyToNull(searchFlag) == null && preStatements != null) {
			statements.append(preStatements);// 翻页
		} else {
			StringBuffer claimSql = new StringBuffer("");
			claimSql.append(" select prplclaim.*, u.risktype from prplclaim left join uticodetransfer u on prplclaim.riskcode = u.outercode where ");
			claimSql.append(" prplclaim.canceldate is null and prplclaim.dealercode is null ");
			claimSql.append(StringConvert.convertString("prplclaim.comCode", strDeptCode, "="));
			claimSql.append(StringConvert.convertDate("prplclaim.claimDate", strClaimDateStart, ">="));
			claimSql.append(StringConvert.convertDate("prplclaim.claimDate", strClaimDateEnd, "<="));
			claimSql.append(StringConvert.convertDate("prplclaim.damageStartDate", strDamageDateStart, ">="));
			claimSql.append(StringConvert.convertDate("prplclaim.damageStartDate", strDamageDateEnd, "<="));
			claimSql.append(StringConvert.convertString("u.RiskType", strRiskType, "="));
			//查询主
			statements.append(" select a.comcode,a.claimdate,a.claimno,a.policyno,a.insuredname,a.startdate,a.damagestartdate,a.damageaddress");
			if("1".equals(strPresentType)){//险种分项
				statements.append(",b.kindcode");
				statements.append(",(select sum(kindloss) from prplclaimloss where lossfeetype = 'P' and prplclaimloss.claimno = a.claimno and prplclaimloss.kindcode = b.kindcode) claimloss");
			}else{//汇整
				statements.append(",(select sum(kindloss) from prplclaimloss where lossfeetype = 'P' and prplclaimloss.claimno = a.claimno ) claimloss");
			}
			statements.append(",b.sumrealpay");//核赔金额
			statements.append(",a.handler1code,a.handlercode,a.businessnature");
			statements.append(",( case when a.riskType='D' ");
			statements.append(" then (select licenseno from prpcitemcar where prpcitemcar.policyno = a.policyno) ");
			statements.append(" else (select identifynumber from prpcinsured where prpcinsured.policyno = a.policyno and prpcinsured.insuredflag = '1' and rowNum =1) end ) remark ");
			statements.append(" from (").append(claimSql).append(") a ");
			StringBuffer payByKindSql = new StringBuffer("");
			payByKindSql.append(" select t1.claimno");
			if("1".equals(strPresentType)){//险种分项
				payByKindSql.append(",t2.kindcode");
			}
			payByKindSql.append(",sum(t2.sumrealpay) sumrealpay ");
			payByKindSql.append(" from prplcompensate t1 inner join ");
			payByKindSql.append(" ( ");
			payByKindSql.append(" select compensateno,kindcode,sumrealpay from prplloss ");
			payByKindSql.append(" union all ");
			payByKindSql.append(" select compensateno,kindcode,sumrealpay from prplpersonloss ");
			payByKindSql.append(" ) t2 on t1.compensateno = t2.compensateno ");
			payByKindSql.append(" where (t1.compensateno like 'C%' or t1.compensateno like 'Y%') ");
			if("2".equals(strStatus)){//已核赔
				payByKindSql.append(" and (t1.underwriteflag = '1' or t1.underwriteflag = '3') ");
			}else if("1".equals(strStatus)){//未核赔
				payByKindSql.append(" and (t1.underwriteflag = '0' or t1.underwriteflag = '9' or t1.underwriteflag = '2') ");
			}
			payByKindSql.append(" group by t1.claimno ");
			if("1".equals(strPresentType)){//险种分项
				payByKindSql.append(",t2.kindcode");
			}
			statements.append(" inner join (").append(payByKindSql).append(") b on a.claimno = b.claimno ");
			statements.append(" order by a.claimno ");
			if("1".equals(strPresentType)){//险种分项
				statements.append(",b.kindcode");
			}
		}
		Page page = this.commonService.findPage(statements.toString(), pageNo, pageSize);
		request.setAttribute("page", page);
		request.setAttribute("resultList", this.resultSet(page.getResult(), searchType));
		request.getSession().setAttribute("ClaimTaskStatements", statements.toString());
	}

	/***
	 * 立案作业查询结果导出
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void queryClaimTaskExport(HttpServletRequest request, HttpServletResponse response, String searchType) throws Exception {
		Object statements = request.getSession().getAttribute("ClaimTaskStatements");
		if (statements != null) {
			if (statements != null) {
				List<?> list = this.commonService.find(statements.toString(), 0, 0);
				CommonUtils.exportExcel(request,response, ClaimTaskDto.getDisPlayTitile(), ClaimTaskDto.getDisPlayClumName(), ClaimTaskDto.getDisPlayField(), resultSet(list, searchType));
			}
		}
	}

	/***
	 * 已核賠資料查詢
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @throws Exception
	 */
	public void queryUndwrtTaskInfo(HttpServletRequest request, String searchType, int pageNo, int pageSize) throws Exception {
		String strUnderWriteDeptCode = request.getParameter("UnderWriteDeptCode");
		String strUnderWriteDateStart = request.getParameter("UnderWriteDateStart");//核赔时间起
		String strUnderWriteDateEnd = request.getParameter("UnderWriteDateEnd");//核赔时间止
		String strRiskType = request.getParameter("RiskType");
		String searchFlag = request.getParameter("searchFlag");
		Object preStatements = request.getSession().getAttribute("UndwrtTaskInfoStatements");
		StringBuffer statements = new StringBuffer("");
		if (DataUtils.emptyToNull(searchFlag) == null && preStatements != null) {
			statements.append(preStatements);// 翻页
		} else {
			StringBuffer compensateSql = new StringBuffer("");
			compensateSql.append("select t.*,u.riskType from PrpLcompensate t left join uticodetransfer u on t.riskcode=u.outercode where ");
			compensateSql.append(" (t.underwriteflag = '1' or t.underwriteflag = '3') and (t.compensateno like 'C%' or t.compensateno like 'Y%' ) ");
			compensateSql.append(StringConvert.convertString("t.UnderWriteDeptCode", strUnderWriteDeptCode, "="));
			compensateSql.append(StringConvert.convertDate("t.UnderWriteEndDate", strUnderWriteDateStart, ">="));
			compensateSql.append(StringConvert.convertDate("t.UnderWriteEndDate", strUnderWriteDateEnd, "<="));
			compensateSql.append(StringConvert.convertString("u.RiskType", strRiskType, "="));
			statements.append("select c.underwritedeptcode,b.claimdate,c.underwriteenddate,c.compensateno,");
			statements.append("c.policyno,b.insuredname,b.damagestartdate,c.riskcode,c.sumthispaid,c.sumnodutyfee,");
			statements.append("c.handler1code,c.handlercode,b.businessnature,c.underwriteenddate paydate,");
			statements.append("( case when c.riskType='D' ");
			statements.append(" then (select licenseno from prpcitemcar where prpcitemcar.policyno = c.policyno) ");
			statements.append(" else (select identifynumber from prpcinsured where prpcinsured.policyno = c.policyno and prpcinsured.insuredflag = '1' and rowNum=1) end ) remark ");
			statements.append("from (").append(compensateSql).append(") c left join prplclaim b on c.claimno = b.claimno ");
			statements.append("order by c.claimno, c.compensateno");
		}
		Page page = this.commonService.findPage(statements.toString(), pageNo, pageSize);
		request.setAttribute("page", page);
		request.setAttribute("resultList", this.resultSet(page.getResult(), searchType));
		request.getSession().setAttribute("UndwrtTaskInfoStatements", statements.toString());
	}

	/***
	 * 已核賠資料查詢结果导出
	 * @param request
	 * @throws Exception
	 */
	public void queryUndwrtTaskInfoExport(HttpServletRequest request, HttpServletResponse response, String searchType) throws Exception {
		Object statements = request.getSession().getAttribute("UndwrtTaskInfoStatements");
		if (statements != null) {
			List<?> list = this.commonService.find(statements.toString(), 0, 0);
			CommonUtils.exportExcel(request,response, UndwrtTaskInfoDto.getDisPlayTitile(), UndwrtTaskInfoDto.getDisPlayClumName(), UndwrtTaskInfoDto.getDisPlayField(), resultSet(list, searchType));
		}
	}

	/***
	 * 已核赔赔付查询
	 * @param request
	 * @param parseInt
	 * @param pageSize
	 * @throws Exception 
	 */
	public void queryUndwrtTaskPayInfo(HttpServletRequest request, String searchType, int pageNo, int pageSize) throws Exception {
		String strUnderWriteDeptCode = request.getParameter("UnderWriteDeptCode");
		String strUnderWriteDateStart = request.getParameter("UnderWriteDateStart");//核赔时间起
		String strUnderWriteDateEnd = request.getParameter("UnderWriteDateEnd");//核赔时间止
		String strRiskType = request.getParameter("RiskType");
		String searchFlag = request.getParameter("searchFlag");
		Object preStatements = request.getSession().getAttribute("UndwrtTaskPayInfoStatements");
		StringBuffer statements = new StringBuffer("");
		if (DataUtils.emptyToNull(searchFlag) == null && preStatements != null) {
			statements.append(preStatements);// 翻页
		} else {
			StringBuffer compensateSql = new StringBuffer("");
			compensateSql.append("select t.*,u.riskType from PrpLcompensate t left join uticodetransfer u on t.riskcode=u.outercode where ");
			compensateSql.append(" (t.underwriteflag = '1' or t.underwriteflag = '3') and (t.compensateno like 'C%' or t.compensateno like 'Y%' ) ");
			compensateSql.append(StringConvert.convertString("t.UnderWriteDeptCode", strUnderWriteDeptCode, "="));
			compensateSql.append(StringConvert.convertDate("t.UnderWriteEndDate", strUnderWriteDateStart, ">="));
			compensateSql.append(StringConvert.convertDate("t.UnderWriteEndDate", strUnderWriteDateEnd, "<="));
			compensateSql.append(StringConvert.convertString("u.RiskType", strRiskType, "="));
			statements.append("select c.underwritedeptcode,b.claimdate,c.underwriteenddate,c.compensateno,");
			statements.append("c.policyno,b.insuredname,b.damagestartdate,a.serialno payObjectSerialNo,a.ownerName,a.payAmount,");
			statements.append("c.handler1code,c.handlercode,b.businessnature,c.underwriteenddate paydate,");
			statements.append("( case when c.riskType='D' ");
			statements.append(" then (select licenseno from prpcitemcar where prpcitemcar.policyno = c.policyno) ");
			statements.append(" else (select identifynumber from prpcinsured where prpcinsured.policyno = c.policyno and prpcinsured.insuredflag = '1'  and rownum=1) end ) remark ");
			statements.append("from (").append(compensateSql).append(") c left join prplclaim b on c.claimno = b.claimno left join prplpayobjectinfo a on a.compensateno = c.compensateno ");
			statements.append(" where a.certitype = '"+PrpLpayObjectInfo.CERTITYPE_PAYOBJECT+"' order by c.claimno, c.compensateno , payObjectSerialNo ");
		}
		Page page = this.commonService.findPage(statements.toString(), pageNo, pageSize);
		request.setAttribute("page", page);
		request.setAttribute("resultList", this.resultSet(page.getResult(), searchType));
		request.getSession().setAttribute("UndwrtTaskPayInfoStatements", statements.toString());

	}

	/***
	 * 已核赔赔付查询结果导出
	 * @param request
	 * @param response
	 * @param searchType
	 * @throws Exception 
	 */
	public void queryUndwrtTaskPayInfoExport(HttpServletRequest request, HttpServletResponse response, String searchType) throws Exception {
		Object statements = request.getSession().getAttribute("UndwrtTaskPayInfoStatements");
		if (statements != null) {
			List<?> list = this.commonService.find(statements.toString(), 0, 0);
			CommonUtils.exportExcel(request,response, UndwrtTaskPayInfoDto.getDisPlayTitile(), UndwrtTaskPayInfoDto.getDisPlayClumName(), UndwrtTaskPayInfoDto.getDisPlayField(), resultSet(list, searchType));
		}
	}

	/***
	 * 查询结果处理(将SQL查询的结果集处理成对应的对象集合，并对相关的内容进行中文转换)
	 * @param result SQL查询的结果
	 * @param searchType 查询类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Object> resultSet(List<?> result, String searchType) throws Exception {
		List<Object> list = new ArrayList<Object>();
		if (result != null && !result.isEmpty()) {
			Iterator<?> it = result.iterator();
			int i = 0;// 序号
			while (it.hasNext()) {
				i++;
				if("ClaimTask".equals(searchType)){
					ClaimTaskDto ct = new ClaimTaskDto((Map<String, Object>) it.next());
					ct.setSerialNo(i);
					ct.setHandler1Name(this.codeService.translateUserCode(ct.getHandler1Code().toString(), true));
					ct.setHandlerName(this.codeService.translateUserCode(ct.getHandlerCode().toString(), true));
					ct.setComCName(this.codeService.translateComCode(ct.getComCode().toString(), true));
					list.add(ct);
				}else if ("UndwrtTaskInfo".equals(searchType)) {
					UndwrtTaskInfoDto uti = new UndwrtTaskInfoDto((Map<String, Object>) it.next());
					uti.setSerialNo(i);
					uti.setHandler1Name(this.codeService.translateUserCode(uti.getHandler1Code().toString(), true));
					uti.setHandlerName(this.codeService.translateUserCode(uti.getHandlerCode().toString(), true));
					uti.setUnderWriteDeptName(this.codeService.translateComCode(uti.getUnderWriteDeptCode().toString(), true));
					list.add(uti);
				} else if ("UndwrtTaskPayInfo".equals(searchType)) {
					UndwrtTaskPayInfoDto utpi = new UndwrtTaskPayInfoDto((Map<String, Object>) it.next());
					utpi.setSerialNo(i);
					utpi.setHandler1Name(this.codeService.translateUserCode(utpi.getHandler1Code().toString(), true));
					utpi.setHandlerName(this.codeService.translateUserCode(utpi.getHandlerCode().toString(), true));
					utpi.setUnderWriteDeptName(this.codeService.translateComCode(utpi.getUnderWriteDeptCode().toString(), true));
					list.add(utpi);
				}
			}
		}
		return list;
	}
	
	/**
	 * 導出受害人醫療收據明細資料
	 * @author songxin
	 * @date 2017-05-10
	 * @param request
	 * @param response
	 * @param searchType
	 */
	public void queryExportTiiDetail(HttpServletRequest request, HttpServletResponse response, String searchType){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//获取前一个月第一天
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        String firstDay = sdf.format(calendar1.getTime());
        //获取前一个月最后一天
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = sdf.format(calendar2.getTime());

        String sql = "select distinct p2.compensateno,p4.damagestartdate,p4.claimdate,p2.times,p2.paycode,p4.endcasedate,"
				+"p2.underwriteenddate,p2.subrogation,p2.indemnitydutyrate,p2.sumthispaid,p1.identityofinjuredperson,"
				+"p1.identifynumber,p1.medicalcode,p1.endCaseAndRecoverFlag,p3.serialno,p3.startdate"
				+" from prplpersonloss p1,prplcompensate p2,prplcompelmedical p3,prplclaim p4 where "
				+"p1.compensateno = p2.compensateno and p2.compensateno = p3.compensateno and p2.claimno = p4.claimno "
				+" and p4.endcasedate >= to_date('"+firstDay+"','yyyymmdd') "
				+" and p4.endcasedate <= to_date('"+lastDay+"','yyyymmdd') and p1.riskcode = 'B01'";
		String sql1 = "SELECT p1.compensateNo,sum(p1.sumthispaid) FROM  prplcompensate p1 "
		        +"where (p1.underwriteflag ='1' or p1.underwriteflag ='3') "
		        +"and p1.riskcode = 'B01' and p1.compensateno like 'R%' group by p1.compensateNo";
		
		
		List<Object[]> list = (List<Object[]>) this.commonService.findByStatements(sql);
		List<Object[]> list1 = (List<Object[]>) this.commonService.findByStatements(sql1);
		CommonUtils.exporTxtFile(response,list,list1);
	}
	
	/**
	 * 導出受害人醫療收據匯總資料
	 * @author songxin
	 * @date  2017-05-10
	 * @param request
	 * @param response
	 * @param searchType
	 */
	public void queryExportTiiCollect(HttpServletRequest request, HttpServletResponse response, String searchType){
		
		String basePath1 = this.getClass().getClassLoader().getResource("/").getPath();
        if(basePath1.indexOf("WEB-INF/classes") > -1) {
	         basePath1 = basePath1.substring(0, basePath1.indexOf("WEB-INF/classes"));
         }
        String basePath = basePath1 + "behaviors";
        
        Map<String,Object> dataMap=new HashMap<String,Object>();
				
		//格式化時間數據并查詢相應的數據
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//获取前一个月第一天
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        String firstDay = sdf.format(calendar1.getTime());
        //获取前一个月最后一天
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = sdf.format(calendar2.getTime());

        //受害人醫療數據
        String sql = "SELECT sum(p3.sumrealpay),sum(p2.healthamount),sum(p3.healthamount),count(distinct p3.compensateNo) "
        		+" FROM prplcompensate p1,prplcompelmedical p2,prplpersonloss p3,swflog s"
        		+"  where p1.compensateno = p2.compensateno and p2.compensateno = p3.compensateno and p1.compensateno = s.businessno"
        		+" and s.nodetype = 'veric' and (s.nodestatus = '0' or s.nodestatus = '2') and s.riskcode = 'B01' and s.flowintime >= '"
        		+firstDay+"' and s.flowintime <= '"+lastDay+"' and p3.liabdetailcode like 'A%'";
        //賠案未付款
        String sql1 = "SELECT sum(sumpaid),count(compensateNo) FROM prplcompensate where compensateNo in "
        		+"(SELECT businessNo FROM swflog s where s.nodetype = 'veric' and (s.nodestatus = '0' or s.nodestatus = '2')"
        		+" and s.riskcode = 'B01' and s.flowintime >= '"+firstDay+"' and s.flowintime <= '"+lastDay+"')";
        
        List<Object[]> list = (List<Object[]>) this.commonService.findByStatements(sql);
		List<Object[]> list1 = (List<Object[]>) this.commonService.findByStatements(sql1);
		
		Object[] obj = null;
		Object[] obj1 = null;
		if(null != list && list.size()>0){
			obj = list.get(0);
		}
		if(null != list1 && list1.size()>0){
			obj1 = list1.get(0);
		}
		
		//給模板中對應的欄位賦值
		if(null != obj && null != obj1){
			dataMap.put("year", firstDay.substring(0, 4));//年
			dataMap.put("month", firstDay.substring(6, 7));//月
			dataMap.put("sumrealpay", obj[0]==null?"0":obj[0]);//受害人醫療收據
			dataMap.put("healthamount1", obj[1]==null?"0":obj[1]);//給付健保金額
			dataMap.put("healthamount2", obj[2]==null?"0":obj[2]);	//健保點數/金額
			dataMap.put("sum1", obj[3]);//受害人醫療收據筆數
			dataMap.put("sumpaid", obj1[0]==null?"0":obj1[0]);//赔案未付款
			dataMap.put("sum2", obj1[1]);//赔案未付款筆數
		}
			
        try {
			CommonUtils.exportWord(response,dataMap,basePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
