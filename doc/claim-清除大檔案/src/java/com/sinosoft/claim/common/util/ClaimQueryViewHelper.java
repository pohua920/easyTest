package com.sinosoft.claim.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.service.facade.PowerService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpCopyInsured;
import com.sinosoft.claim.schema.service.facade.PrpCopyInsuredService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

public class ClaimQueryViewHelper {

	private PrpLcompensateService prpLcompensateService;
	private PrpLclaimService prpLclaimService;
	private CommonService commonService;
	private CodeService codeService;
	private PowerService powerService;
	private PrpCopyInsuredService prpCopyInsuredService;

	/***
	 * 分頁查應追償未追償
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Page claimReplevyQuery(HttpServletRequest request, int pageNo, int pageSize) throws Exception {
		String makeCom = request.getParameter("MakeCom");
		String comCode = request.getParameter("ComCode");
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		StringBuilder statements = new StringBuilder();
		statements.append("SELECT a.makecom,");
		statements.append("a.comcode,");
		statements.append("a.claimno,");
		statements.append("a.policyno,");
		statements.append("a.insuredname,");
		statements.append("c.licenseno,");
		statements.append("a.handlercode claimhandlername,");
		statements.append("a.damagestartdate,");
		statements.append("a.claimdate,");
		statements.append("a.endcasedate,");
		statements.append("b.preservedate replevydate,");
		statements.append("b.handlercode replevyhandlername,");
		statements.append("b.replevyamount,");
		statements.append("b.inputdate ");
		statements.append("FROM prplclaim a,");
		statements.append("view_claimreplevy b,");
		statements.append("prplthirdparty c ");
		statements.append("WHERE a.claimno = b.claimno AND a.registno = c.registno(+) AND a.registno = c.registno(+) ");
		statements.append(" AND (c.insurecarflag = '1' OR c.insurecarflag IS NULL) AND b.replevyamount > 0 ");
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		statements.append(powerService.addRiskPower(userDto, "a","claim"));
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		if(!CommonUtils.isEmpty(comCode)){
			statements.append(" AND b.comcode = '"+ comCode +"' ");
		} else {
			if(!CommonUtils.isEmpty(makeCom)){
				statements.append(" AND a.makecom = '"+ makeCom +"' ");
			}
			statements.append(uiPowerInterface.addCustomerPower(userDto, "b", "", "ComCode"));
		}
		if (!CommonUtils.isEmpty(dateStart) && !CommonUtils.isEmpty(dateEnd)) {
			statements.append(" AND b.inputdate between to_date('" + dateStart + "','yyyy-mm-dd') and to_date('" + dateEnd + "','yyyy-mm-dd') ");
		} else if (!CommonUtils.isEmpty(dateStart)) {
			statements.append(" AND b.inputdate >= to_date('" + dateStart + "','yyyy-mm-dd') ");
		} else if (!CommonUtils.isEmpty(dateEnd)) {
			statements.append(" AND b.inputdate <= to_date('" + dateEnd + "','yyyy-mm-dd') ");
		}
		statements.append(" order by b.inputdate , a.claimdate ");
		System.out.println(statements);
		Page page = this.commonService.findPage(statements.toString(), pageNo, pageSize);
		List<?> list = page.getResult();
		if(!CommonUtils.isEmpty(list)){
			Map<String , Object> rowObject = null;
			Object temp = null;
			for (int index = 0, size = list.size(); index < size; index++) {
				rowObject = (Map<String , Object>)list.get(index);
				temp = rowObject.get("claimhandlername");
				if(temp != null){
					rowObject.put("CLAIMHANDLERNAME", this.codeService.translateUserCode(String.valueOf(temp), true));
				}
				temp = rowObject.get("replevyhandlername");
				if(temp != null){
					rowObject.put("REPLEVYHANDLERNAME", this.codeService.translateUserCode(String.valueOf(temp), true));
				}
			}
		}
		return page;
	}

	/***
	 * 分頁查已追償明細
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Page claimRepleviedQuery(HttpServletRequest request, int pageNo, int pageSize) throws Exception {
		String makeCom = request.getParameter("MakeCom");
		String comCode = request.getParameter("ComCode");
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		String[] replevyStatus = request.getParameterValues("replevyStatus");
		StringBuilder statements = new StringBuilder();
		statements.append("SELECT a.makecom,");
		statements.append("a.comcode,");
		statements.append("a.claimno,");
		statements.append("a.policyno,");
		statements.append("a.insuredname,");
		statements.append("c.licenseno,");
		statements.append("a.handlercode,");
		statements.append("a.damagestartdate,");
		statements.append("a.claimdate,");
		statements.append("b.sumloss,");
		statements.append("b.sumthispaid*(-1) sumthispaid,");
		statements.append("b.sumnodutyfee,");
		statements.append("b.inputdate, ");
		statements.append("decode(b.paysituation,'3','Y','N') endflag ");
		statements.append("FROM prplclaim a,");
		statements.append("prplcompensate b,");
		statements.append("prplthirdparty c ");
		statements.append("WHERE a.claimno = b.claimno AND b.casetype = 'R' AND b.compensateno > 'R' || b.claimno || '00' ");
		statements.append("AND b.underwriteflag = '1' AND a.registno = c.registno(+) ");
		statements.append("AND (c.insurecarflag = '1' OR c.insurecarflag IS NULL) ");
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		statements.append(powerService.addRiskPower(userDto, "a","claim"));
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		if(!CommonUtils.isEmpty(replevyStatus)){
			statements.append("AND b.paysituation in ( '").append(CommonUtils.join(replevyStatus, "','")).append("' ) ");
		}
		if(!CommonUtils.isEmpty(comCode)){
			statements.append(" AND b.comcode = '"+ comCode +"' ");
		} else {
			if(!CommonUtils.isEmpty(makeCom)){
				statements.append(" AND a.makecom = '"+ makeCom +"' ");
			}
			statements.append(uiPowerInterface.addCustomerPower(userDto, "b", "", "ComCode"));
		}
		if (!CommonUtils.isEmpty(dateStart) && !CommonUtils.isEmpty(dateEnd)) {
			statements.append(" AND b.inputdate between to_date('" + dateStart + "','yyyy-mm-dd') and to_date('" + dateEnd + "','yyyy-mm-dd') ");
		} else if (!CommonUtils.isEmpty(dateStart)) {
			statements.append(" AND b.inputdate >= to_date('" + dateStart + "','yyyy-mm-dd') ");
		} else if (!CommonUtils.isEmpty(dateEnd)) {
			statements.append(" AND b.inputdate <= to_date('" + dateEnd + "','yyyy-mm-dd') ");
		}
		statements.append(" order by b.inputdate , a.claimdate ");
		System.out.println(statements);
		Page page = this.commonService.findPage(statements.toString(), pageNo, pageSize);
		List<?> list = page.getResult();
		if(!CommonUtils.isEmpty(list)){
			Map<String , Object> rowObject = null;
			Object temp = null;
			for (int index = 0, size = list.size(); index < size; index++) {
				rowObject = (Map<String , Object>)list.get(index);
				temp = rowObject.get("HANDLERCODE");
				if(temp != null){
					rowObject.put("CLAIMHANDLERNAME", this.codeService.translateUserCode(String.valueOf(temp), true));
				}
			}
		}
		return page;
	}

	/***
	 * 分頁查未決賠案明細
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Page claimOutstandingQuery(HttpServletRequest request, int pageNo, int pageSize) throws Exception {
		String makeCom = request.getParameter("MakeCom");
		String comCode = request.getParameter("ComCode");
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		StringBuilder statements = new StringBuilder();
		statements.append("SELECT c.makecom,");
		statements.append("c.comcode,");
		statements.append("d.channeltype,");
		statements.append("d.businessnature,");
		statements.append("c.registno,");
		statements.append("a.claimno,");
		statements.append("c.policyno,");
		statements.append("c.insuredname,");
		statements.append("c.insuredcode,");
		statements.append("e.licenseno,");
		statements.append("c.damagestartdate,");
		statements.append("c.claimdate,");
		statements.append("b.claimlossdate,");
		statements.append("a.kindcode,");
//		statements.append("nvl(b.sumclaim,0) sumclaim,");
		statements.append("nvl(a.sumkindpay,0) sumkindpay,");
		statements.append("( case when c.endcasedate is not null then 'Y' else 'N' end ) endflag,");
		statements.append("c.endcasedate,");
		statements.append("c.handlercode,");
		statements.append("c.handler1code ");
		statements.append("FROM view_claimpay a, prplclaim c, prpcmain d, prplthirdparty e , view_claimloss b "); 
		statements.append("WHERE a.underwriteflag = '9' AND a.claimno = c.claimno AND c.policyno = d.policyno "); 
		statements.append("AND (e.insurecarflag = '1' OR e.insurecarflag IS NULL) AND c.registno = e.registno(+) ");
		statements.append("AND a.claimno_kindcode = b.claimno_kindcode(+) ");
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		statements.append(powerService.addRiskPower(userDto, "c","claim"));
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		if(!CommonUtils.isEmpty(comCode)){
			statements.append(" AND c.comcode = '"+ comCode +"' ");
		} else {
			if(!CommonUtils.isEmpty(makeCom)){
				statements.append(" AND c.makecom = '"+ makeCom +"' ");
			}
			statements.append(uiPowerInterface.addCustomerPower(userDto, "c", "", "ComCode"));
		}
		if (!CommonUtils.isEmpty(dateStart) && !CommonUtils.isEmpty(dateEnd)) {
			statements.append(" AND a.inputdate between to_date('" + dateStart + "','yyyy-mm-dd') and to_date('" + dateEnd + "','yyyy-mm-dd') ");
		} else if (!CommonUtils.isEmpty(dateStart)) {
			statements.append(" AND a.inputdate >= to_date('" + dateStart + "','yyyy-mm-dd') ");
		} else if (!CommonUtils.isEmpty(dateEnd)) {
			statements.append(" AND a.inputdate <= to_date('" + dateEnd + "','yyyy-mm-dd') ");
		}
		statements.append(" order by a.inputdate , a.underwriteenddate , c.claimdate , c.claimno , a.kindcode ");
		Page page = this.commonService.findPage(statements.toString(), pageNo, pageSize);
		List<?> list = page.getResult();
		if(!CommonUtils.isEmpty(list)){
			Map<String , String > trans = new HashMap<String, String>();
			Map<String , Object> rowObject = null;
			Object temp = null;
			QueryRule queryRule = null;
			List<PrpCopyInsured> insuredlist = null;
			String registno , policyno , insuredcode , insuredname , tempKey , querysql = null;
			for (int index = 0, size = list.size(); index < size; index++) {
				rowObject = (Map<String , Object>)list.get(index);
				temp = rowObject.get("BUSINESSNATURE");
				if(temp != null ){
					String businessnaturename = this.codeService.translateCode("BusinessNature", String.valueOf(temp), ConstantCodes.Language.CHINESE);
					rowObject.put("BUSINESSNATURENAME", businessnaturename);
				}
				temp = rowObject.get("HANDLERCODE");
				if(temp != null){
					rowObject.put("HANDLERNAME", this.codeService.translateUserCode(String.valueOf(temp), true));
				}
				temp = rowObject.get("HANDLER1CODE");
				if(temp != null){
					rowObject.put("HANDLER1NAME", this.codeService.translateUserCode(String.valueOf(temp), true));
				}
				policyno = String.valueOf(rowObject.get("POLICYNO"));
				registno = String.valueOf(rowObject.get("REGISTNO"));
				insuredcode = String.valueOf(rowObject.get("INSUREDCODE"));
				insuredname = String.valueOf(rowObject.get("INSUREDNAME"));
				tempKey = registno+"_"+insuredcode+"_"+insuredname;
				if(trans.containsKey(registno)){
					rowObject.put("INSUREDCODE", trans.get(tempKey));
				} else {
					queryRule = QueryRule.getInstance();
					querysql = " policyno ='" + policyno + "' and insuredname = '" + insuredname + "' and ( insuredcode='" + insuredcode + "' or identifynumber = '" + insuredcode + "' )";
					queryRule.addSql(querysql);
					insuredlist = this.prpCopyInsuredService.findPrpCopyInsured(queryRule);
					if(!CommonUtils.isEmpty(insuredlist)){
						insuredcode = insuredlist.get(0).getIdentifyNumber();
						rowObject.put("INSUREDCODE", insuredcode);
						trans.put(tempKey, insuredcode);
					}
				}
			}
		}
		return page;
	}

	/***
	 * 分頁查已決賠案明細
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Page claimSettledQuery(HttpServletRequest request , int pageNo, int pageSize) throws Exception{
		String makeCom = request.getParameter("MakeCom");
		String comCode = request.getParameter("ComCode");
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		StringBuilder statements = new StringBuilder();
		statements.append("SELECT c.makecom,");
		statements.append("c.comcode,");
		statements.append("d.channeltype,");
		statements.append("d.businessnature,");
		statements.append("a.claimno,");
		statements.append("c.policyno,");
		statements.append("c.insuredname,");
		statements.append("e.licenseno,");
		statements.append("c.damagestartdate,");
		statements.append("c.claimdate,");
		statements.append("a.kindcode,");
		statements.append("nvl(b.sumclaim,0) sumclaim,");
		statements.append("nvl(a.sumkindpay,0) sumkindpay,");
		statements.append("nvl(a.sumkindfee,0) sumkindfee,");
		statements.append("a.underwriteenddate,");
		statements.append("c.handlercode,");
		statements.append("c.handler1code ");
		statements.append("FROM view_claimpay a, prplclaim c, prpcmain d, prplthirdparty e , view_claimloss b "); 
		statements.append("WHERE a.underwriteflag IN ('1', '3') AND a.claimno = c.claimno AND c.policyno = d.policyno "); 
		statements.append("AND (e.insurecarflag = '1' OR e.insurecarflag IS NULL) AND c.registno = e.registno(+) ");
		statements.append("AND a.claimno_kindcode = b.claimno_kindcode(+) ");
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		statements.append(powerService.addRiskPower(userDto, "c","claim"));
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		if(!CommonUtils.isEmpty(comCode)){
			statements.append(" AND c.comcode = '"+ comCode +"' ");
		} else {
			if(!CommonUtils.isEmpty(makeCom)){
				statements.append(" AND c.makecom = '"+ makeCom +"' ");
			}
			statements.append(uiPowerInterface.addCustomerPower(userDto, "c", "", "ComCode"));
		}
		if (!CommonUtils.isEmpty(dateStart) && !CommonUtils.isEmpty(dateEnd)) {
			statements.append(" AND a.inputdate between to_date('" + dateStart + "','yyyy-mm-dd') and to_date('" + dateEnd + "','yyyy-mm-dd') ");
		} else if (!CommonUtils.isEmpty(dateStart)) {
			statements.append(" AND a.inputdate >= to_date('" + dateStart + "','yyyy-mm-dd') ");
		} else if (!CommonUtils.isEmpty(dateEnd)) {
			statements.append(" AND a.inputdate <= to_date('" + dateEnd + "','yyyy-mm-dd') ");
		}
		statements.append(" order by a.inputdate , a.underwriteenddate , c.claimdate , c.claimno , a.kindcode ");
		Page page = this.commonService.findPage(statements.toString(), pageNo, pageSize);
		List<?> list = page.getResult();
		if(!CommonUtils.isEmpty(list)){
			Map<String , Object> rowObject = null;
			Object temp = null;
			for (int index = 0, size = list.size(); index < size; index++) {
				rowObject = (Map<String , Object>)list.get(index);
				temp = rowObject.get("BUSINESSNATURE");
				if(temp != null ){
					String businessnaturename = this.codeService.translateCode("BusinessNature", String.valueOf(temp), ConstantCodes.Language.CHINESE);
					rowObject.put("BUSINESSNATURENAME", businessnaturename);
				}
				temp = rowObject.get("HANDLERCODE");
				if(temp != null){
					rowObject.put("HANDLERNAME", this.codeService.translateUserCode(String.valueOf(temp), true));
				}
				temp = rowObject.get("HANDLER1CODE");
				if(temp != null){
					rowObject.put("HANDLER1NAME", this.codeService.translateUserCode(String.valueOf(temp), true));
				}
			}
		}
		return page;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
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

	public PowerService getPowerService() {
		return powerService;
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

	public PrpCopyInsuredService getPrpCopyInsuredService() {
		return prpCopyInsuredService;
	}

	public void setPrpCopyInsuredService(PrpCopyInsuredService prpCopyInsuredService) {
		this.prpCopyInsuredService = prpCopyInsuredService;
	}

}
