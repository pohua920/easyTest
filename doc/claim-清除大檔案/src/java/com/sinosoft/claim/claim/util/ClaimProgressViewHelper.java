package com.sinosoft.claim.claim.util;

import ins.framework.common.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpLclaimProgress;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;

public class ClaimProgressViewHelper {

	private CodeService codeService;
	private CommonService commonService;
	private PrpLregistService prpLregistService;

	/***
	 * 獲取
	 * @param request
	 * @param registNo
	 * @param businessNo
	 * @param policyNo
	 * @param taskCode
	 * @param taskObject
	 * @param lossItemCode
	 * @return
	 */
	public List<PrpLclaimProgress> getClaimProgressData(HttpServletRequest request , String registNo , String businessNo , String policyNo , String taskCode , String taskObject , int lossItemCode) {
		List<PrpLclaimProgress> prpClaimProgressList = new ArrayList<PrpLclaimProgress>();
		String[] cbxProgresses = request.getParameterValues("cbxProgress");
		if (!CommonUtils.isEmpty(cbxProgresses)) {
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			PrpLclaimProgress p = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String cbx = null;
			for(int i = 0 , l = cbxProgresses.length ; i < l ; i++){
				cbx = cbxProgresses[i];
				p = new PrpLclaimProgress();
				p.setId(CommonUtils.createKey("CP"));
				p.setRegistNo(registNo);
				p.setBusinessNo(businessNo);
				p.setPolicyNo(policyNo);
				p.setTaskCode(taskCode);
				p.setTaskObject(taskObject);
				p.setLossItemCode(lossItemCode);
				p.setInputDate(new Date());
				p.setProgressSerialNo(Integer.parseInt(request.getParameter("prpLclaimProgressProgressSerialNo" + cbx)));
				p.setProgressType(request.getParameter("prpLclaimProgressProgressType" + cbx));
				p.setProgressDesc(request.getParameter("prpLclaimProgressProgressDesc" + cbx));
				try {
					p.setProcessDate(sdf.parse(request.getParameter("prpLclaimProgressProcessDate" + cbx)));
				} catch (ParseException e) {
					p.setProcessDate(new Date());
				}
				p.setHandlerCode(user.getUserCode());
				p.setHandlerName(user.getUserName());
				p.setNodeStatus(request.getParameter("buttonSaveType"));
				prpClaimProgressList.add(p);
			}
		}
		return prpClaimProgressList;
	}

	/***
	 * 設置理賠進度類別、進度描述以及該賠案理賠進度訊息
	 * @param request
	 * @param registNo 備案號碼
	 * @param businessNo 業務號碼 立案為立案號，理算為計算書號
	 * @param taskCode 任務名稱
	 * @param nodeType 節點代碼
	 * @param lossItemCode 節點代碼 區分定損、核損 損失項 
	 * @param isSimpleCase 是否簡易賠案
	 * @throws Exception 
	 */
	public void setClaimProgressData(HttpServletRequest request, String registNo, String businessNo, String taskCode , String nodeType ,int lossItemCode , boolean isSimpleCase) throws Exception {
		List<PrpDcode> mainProgressList = this.codeService.findPrpDcodeByConditions(" codeType='ClaimProgress' and validStatus = '1' and codeLevel = '1' order by codeCode ");
		request.setAttribute("mainProgressTypeList", mainProgressList);
		List<PrpDcode> subProgressList = this.codeService.findPrpDcodeByConditions(" codeType='ClaimProgress' and validStatus = '1' and codeLevel = '2' order by upperCode , codeCode ");
		request.setAttribute("subProgressTypeList", subProgressList);
		String statements = " select * from PrpLclaimProgress where registNo = '" + registNo + "' order by processDate , inputDate , progressType , progressSerialNo ";
		List<PrpLclaimProgress> list = commonService.findByStatements(statements, PrpLclaimProgress.class);
		request.setAttribute("claimProgressList", list);
		request.setAttribute("claimProgressTaskCode", taskCode);
		request.setAttribute("claimProgressNodeType", nodeType);
		request.setAttribute("claimProgressRegistNo", registNo);
		request.setAttribute("claimProgressBusinessNo", businessNo);
		request.setAttribute("claimProgressLossItemCode", lossItemCode);
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(registNo);
		if(prpLregist != null){
			request.setAttribute("claimProgressDamageTime", new DateTime(prpLregist.getDamageStartDate()).toString());
		}
		if("claim".equals(nodeType)){//設置當前任務可選的理賠進度訊息
			request.setAttribute("claimProgressType", "A");
		} else if("check".equals(nodeType) || "certa".equals(nodeType) || "verif".equals(nodeType)
				|| "wound".equals(nodeType) || "veriw".equals(nodeType) 
				|| "propc".equals(nodeType) || "propv".equals(nodeType) ){
			request.setAttribute("claimProgressType", "B,C,D");
		} else if("compp".equals(nodeType) || "compe".equals(nodeType)){
			if(isSimpleCase){//簡易賠案
				request.setAttribute("claimProgressType", "B,C,D,E");
			} else {
				request.setAttribute("claimProgressType", "E");
			}
		}
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

}
