package com.sinosoft.claim.undwrt.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.platform.bl.facade.BLPrpDcodeFacade;
import com.sinosoft.platform.bl.facade.BLSwfModelMainFacade;
import com.sinosoft.platform.bl.facade.BLSwfNodeFacade;
import com.sinosoft.platform.bl.facade.BLUtiUwLevelFacade;
import com.sinosoft.platform.dto.domain.SwfModelMainDto;
import com.sinosoft.platform.dto.domain.SwfNodeDto;
import com.sinosoft.platform.dto.domain.UtiUwLevelDto;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.common.util.SqlUtils;
import com.sinosoft.undwrt.bl.facade.BLSWfNodeFacade;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author <中科软>
 * @Date <Feb 19, 2013>
 * @description
 */
public class UtiUwLevelAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private UtiUwLevelService utiUwLevelService;
	private PrpDuserService prpDuserService;
	private PrpDcompanyService prpDcompanyService;

	public String execute() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String actionType = request.getParameter("actionType");
		String forward = actionType;
		try {
			if (actionType == null || actionType.trim().equals("")) {
				forward = "invalid";
			} else if (actionType.equals("prepareQuery")) {
				prepareQuery(request, response);
			} else if (actionType.equals("query")) {
				query(request, response);
			} else if (actionType.equals("queryContinue")) {
				queryContinue(request, response);
			} else if (actionType.equals("prepareUpdate")) {
				prepareUpdate(request, response);
			} else if (actionType.equals("update")) {
				update(request, response);
			} else {
				Object parameters[] = { request, response };
				Method method = getClass().getMethod(actionType, HttpServletRequest.class, HttpServletResponse.class);
				method.invoke(this, parameters);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return forward;
	}

	public UtiUwLevelDto generateDto(HttpServletRequest request) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		UtiUwLevelDto utiUwLevelDto = new UtiUwLevelDto();
		utiUwLevelDto.setUserCode(paramUtils.getParameter("userCode"));
		utiUwLevelDto.setComCode(paramUtils.getParameter("comCode"));
		utiUwLevelDto.setRiskCode(paramUtils.getParameter("riskCode"));
		utiUwLevelDto.setModelNo(paramUtils.getLongParameter("modelNo", 0L));
		utiUwLevelDto.setNodeNo(paramUtils.getIntParameter("nodeNo", 0));
		utiUwLevelDto.setStartDate(paramUtils.getParameter("startDate"));
		utiUwLevelDto.setEndDate(paramUtils.getParameter("endDate"));
		utiUwLevelDto.setValidStatus(paramUtils.getParameter("validStatus"));
		utiUwLevelDto.setFlag(paramUtils.getParameter("flag"));
		utiUwLevelDto.setUwType(paramUtils.getParameter("uwType"));
		utiUwLevelDto.setClassCode(paramUtils.getParameter("classCode"));
		return utiUwLevelDto;
	}

	public void uwLevelmain(HttpServletRequest request, HttpServletResponse response) {

	}

	public void prepareQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("utiUwLevelDto", new UtiUwLevelDto());
	}

	public String generateConditions(HttpServletRequest request) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String userCode = paramUtils.getParameter("userCodeQuery");
		String comCode = paramUtils.getParameter("comCodeQuery");
		String riskCode = paramUtils.getParameter("riskCodeQuery");
		String modelNo = paramUtils.getParameter("modelNoQuery");
		String nodeNo = paramUtils.getParameter("nodeNoQuery");
		String flag = paramUtils.getParameter("utiUwLevelFlag");
		String uwType = paramUtils.getParameter("uwTypeQuery");
		String conditions = "1=1";
		conditions = conditions + SqlUtils.convertString("utiUwLevel.UserCode", userCode);
		conditions = conditions + SqlUtils.convertString("utiUwLevel.ComCode", comCode);
		conditions = conditions + SqlUtils.convertString("utiUwLevel.RiskCode", riskCode);
		conditions = conditions + SqlUtils.convertNumber("utiUwLevel.ModelNo", modelNo);
		conditions = conditions + SqlUtils.convertNumber("utiUwLevel.NodeNo", nodeNo);
		conditions = conditions + SqlUtils.convertString("utiUwLevel.Flag", flag);
		conditions = conditions + SqlUtils.convertString("utiUwLevel.UwType", uwType);
		return conditions;
	}

	public void query(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		int pageNo = 1;
		int rowsPerPage = 10;
		String conditions = generateConditions(request);
		user.setQueryCondition("utiUwLevel", conditions, pageNo, rowsPerPage);
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		PageRecord pageRecord = blUtiUwLevelFacade.findByConditions(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", new Page((pageNo - 1) * rowsPerPage, pageRecord.getCount(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult()));
		request.setAttribute("utiUwLevelOverview", this.transUtiUwLevelDto(pageRecord.getResult()));
	}

	public void prepareInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UtiUwLevelDto utiUwLevelDto = new UtiUwLevelDto();
		request.setAttribute("utiUwLevelDto", utiUwLevelDto);
	}

	public void insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UtiUwLevelDto utiUwLevelDto = generateDto(request);
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		blUtiUwLevelFacade.insert(utiUwLevelDto);
	}

	public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UtiUwLevelDto utiUwLevelDto = generateDto(request);
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		blUtiUwLevelFacade.update(utiUwLevelDto);
	}

	public void queryContinue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String conditions = "1=1";
		if (user.getQueryCondition().getQueryKey().equals("utiUwLevel"))
			conditions = user.getQueryCondition().getConditions();
		int pageNo = paramUtils.getIntParameter("pageNo", user.getQueryCondition().getPageNo());
		int rowsPerPage = paramUtils.getIntParameter("rowsPerPage", user.getQueryCondition().getRowsPerPage());
		user.setQueryCondition("utiUwLevel", conditions, pageNo, rowsPerPage);
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		PageRecord pageRecord = blUtiUwLevelFacade.findByConditions(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", new Page((pageNo - 1) * rowsPerPage, pageRecord.getCount(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult()));
		request.setAttribute("utiUwLevelOverview", this.transUtiUwLevelDto(pageRecord.getResult()));
	}

	public void prepareUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String myselect[] = paramUtils.getParameterValues("checkboxSelect");
		String utiUwLevelUserCodes[] = paramUtils.getParameterValues("utiUwLevelUserCode");
		String utiUwLevelComCodes[] = paramUtils.getParameterValues("utiUwLevelComCode");
		long utiUwLevelModelNos[] = paramUtils.getLongParameterValues("utiUwLevelModelNo", 0L);
		int utiUwLevelNodeNos[] = paramUtils.getIntParameterValues("utiUwLevelNodeNo", 0);
		String utiUwLevelUwTypes[] = paramUtils.getParameterValues("utiUwLevelUwType");
		String utiUwLevelUserCode = utiUwLevelUserCodes[Integer.parseInt(myselect[0])];
		String utiUwLevelComCode = utiUwLevelComCodes[Integer.parseInt(myselect[0])];
		long utiUwLevelModelNo = utiUwLevelModelNos[Integer.parseInt(myselect[0])];
		int utiUwLevelNodeNo = utiUwLevelNodeNos[Integer.parseInt(myselect[0])];
		String utiUwLevelUwType = utiUwLevelUwTypes[Integer.parseInt(myselect[0])];
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		UtiUwLevelDto utiUwLevelDto = blUtiUwLevelFacade.findByPrimaryKey(utiUwLevelUserCode, utiUwLevelComCode, utiUwLevelModelNo, utiUwLevelNodeNo, utiUwLevelUwType);
		request.setAttribute("utiUwLevelDto", this.setValue(utiUwLevelDto));
		request.getSession().setAttribute("UtiUwLevelDto", utiUwLevelDto);
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String myselect[] = paramUtils.getParameterValues("checkboxSelect");
		String utiUwLevelUserCodes[] = paramUtils.getParameterValues("utiUwLevelUserCode");
		String utiUwLevelComCodes[] = paramUtils.getParameterValues("utiUwLevelComCode");
		long utiUwLevelModelNos[] = paramUtils.getLongParameterValues("utiUwLevelModelNo", 0L);
		int utiUwLevelNodeNos[] = paramUtils.getIntParameterValues("utiUwLevelNodeNo", 0);
		String utiUwLevelUwTypes[] = paramUtils.getParameterValues("utiUwLevelUwType");
		for (int i = 0; i < myselect.length; i++) {
			String utiUwLevelUserCode = utiUwLevelUserCodes[Integer.parseInt(myselect[0])];
			String utiUwLevelComCode = utiUwLevelComCodes[Integer.parseInt(myselect[0])];
			long utiUwLevelModelNo = utiUwLevelModelNos[Integer.parseInt(myselect[0])];
			int utiUwLevelNodeNo = utiUwLevelNodeNos[Integer.parseInt(myselect[0])];
			String utiUwLevelUwType = utiUwLevelUwTypes[Integer.parseInt(myselect[0])];
			BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
			blUtiUwLevelFacade.delete(utiUwLevelUserCode, utiUwLevelComCode, utiUwLevelModelNo, utiUwLevelNodeNo, utiUwLevelUwType);
		}
	}

	public void view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String utiUwLevelUserCodes = paramUtils.getParameter("utiUwLevelUserCode");
		String utiUwLevelComCodes = paramUtils.getParameter("utiUwLevelComCode");
		long utiUwLevelModelNos = paramUtils.getLongParameter("utiUwLevelModelNo", 0L);
		int utiUwLevelNodeNos = paramUtils.getIntParameter("utiUwLevelNodeNo", 0);
		String utiUwLevelUwTypes = paramUtils.getParameter("utiUwLevelUwType");
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		UtiUwLevelDto utiUwLevelDto = blUtiUwLevelFacade.findByPrimaryKey(utiUwLevelUserCodes, utiUwLevelComCodes, utiUwLevelModelNos, utiUwLevelNodeNos, utiUwLevelUwTypes);
		request.setAttribute("utiUwLevelDto", this.setValue(utiUwLevelDto));
	}

	public List<UtiUwLevelDto> transUtiUwLevelDto(Collection<?> iUtiUwLevelDtoList) throws Exception {
		List<UtiUwLevelDto> list = new ArrayList<UtiUwLevelDto>();
		Iterator<?> it = iUtiUwLevelDtoList.iterator();
		while (it.hasNext()) {
			UtiUwLevelDto utiUwLevelDto = (UtiUwLevelDto)it.next();
			list.add(utiUwLevelDto);
		}
		return list;
	}

	private UtiUwLevelDto setValue(UtiUwLevelDto utiUwLevelDto) throws Exception {
		BLSwfNodeFacade blSwfNodeFacade = new BLSwfNodeFacade();
		BLPrpDcodeFacade blPrpDcodeFacade = new BLPrpDcodeFacade();
		BLSwfModelMainFacade blSwfModelMainFacade = new BLSwfModelMainFacade();
		PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(utiUwLevelDto.getComCode());//
		if (prpDcompany != null) {
			utiUwLevelDto.setComName(prpDcompany.getComCName());
		}
		PrpDuser prpDuser = prpDuserService.findPrpDuser(utiUwLevelDto.getUserCode());
		if (prpDuser != null) {
			utiUwLevelDto.setUserComCode(prpDuser.getComCode());
			prpDcompany = prpDcompanyService.findPrpDcompany(utiUwLevelDto.getUserComCode());
			if (prpDcompany != null) {
				utiUwLevelDto.setUserComName(prpDcompany.getComCName());
			}
		}
		SwfModelMainDto swfModelMainDto = blSwfModelMainFacade.findByPrimaryKey(new Long(utiUwLevelDto.getModelNo()).intValue());
		if (swfModelMainDto != null) {
			utiUwLevelDto.setModelName(swfModelMainDto.getModelName());
		}
		// 模板号使用固定的23号
		SwfNodeDto swfNodeDto = blSwfNodeFacade.findByPrimaryKey((int) utiUwLevelDto.getModelNo(), utiUwLevelDto.getNodeNo());
		if (swfNodeDto != null) {
			utiUwLevelDto.setNodeName(swfNodeDto.getNodeName().substring(2, swfNodeDto.getNodeName().length()));
		} else {
			utiUwLevelDto.setNodeName(utiUwLevelDto.getNodeNo() + "");
		}
		String uwTypeName = blPrpDcodeFacade.translateCodeCode("UwType", utiUwLevelDto.getUwType(), true);
		if (DataUtils.emptyToNull(uwTypeName) != null) {
			utiUwLevelDto.setUwTypeName(uwTypeName);
		} else {
			utiUwLevelDto.setUwTypeName(utiUwLevelDto.getUwType());
		}
		prpDuser = prpDuserService.findPrpDuser(utiUwLevelDto.getUserCode());
		if (prpDuser != null) {
			utiUwLevelDto.setUserName(prpDuser.getUserName());
		}
		return utiUwLevelDto;
	}

	/**
	 * @Description: 新增人员权限 - 下一步
	 * @author 中科软
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void prepareInsertUwLevel2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		UtiUwLevelDto utiUwLevelDto = new UtiUwLevelDto();
		utiUwLevelDto.setUwType(paramUtils.getParameter("uwType"));
		utiUwLevelDto.setUwTypeName(paramUtils.getParameter("uwTypeName"));
		utiUwLevelDto.setComCode(paramUtils.getParameter("comCode"));
		utiUwLevelDto.setComName(paramUtils.getParameter("comName"));
		utiUwLevelDto.setModelNo(paramUtils.getIntParameter("modelNo", 1));
		utiUwLevelDto.setModelName(paramUtils.getParameter("modelName"));
		BLSWfNodeFacade blSWfNodeFacade = new BLSWfNodeFacade();
		List<?> swfNodeList = (List<?>) blSWfNodeFacade.findByConditions(" modelNo= " + utiUwLevelDto.getModelNo());
		request.setAttribute("swfNodeList", swfNodeList);
		request.setAttribute("utiUwLevelDto", utiUwLevelDto);
	}

	public void insertUwLevel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		int nodeNo = paramUtils.getIntParameter("nodeNo", 0);
		List<?> utiUwLevelUserList = (List<?>) utiUwLevelService.findByConditions(" nodeNo = " + nodeNo + "");
		UtiUwLevelDto utiUwLevelDto = new UtiUwLevelDto();
		utiUwLevelDto.setNodeNo(nodeNo);
		request.setAttribute("utiUwLevelUserList", utiUwLevelUserList);
		request.setAttribute("uwLevelDto", utiUwLevelDto);
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

}
