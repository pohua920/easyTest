package com.sinosoft.claim.check.util;

import ins.framework.common.Page;

import javax.servlet.http.*;

import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
/**
 * 说明：原来的文件丢失，用于显示历次调查信息
 * @author 中科软 
 *  created 2005-9-1 
 */
public class PrpLacciCheckViewHelper{
	/**
	 * 调查信息
	 */
	private PrpLacciCheckService prpLacciCheckService;
	/**
	 * 默认构造函数
	 */
	public PrpLacciCheckViewHelper(){}
	/**
	 * 返回历次调查信息列表
	 * @param request
	 * @param pageNo
	 * @param recordPerPage
	 * @throws Exception
	 */
	public Page policyListToView(HttpServletRequest request,String registNo,String pageNo,String recordPerPage) throws Exception{
		String conditions = " registNo = '"+registNo+"' ";
		int intPageNo = ConstantCodes.DEFAULT_PAGENO;
		if(!CommonUtils.isEmpty(pageNo)){
			intPageNo = Integer.parseInt(pageNo);
		}
		int pageSize =  ConstantCodes.DEFAULT_ROWSPERPAGE;
		if(!CommonUtils.isEmpty(recordPerPage)){
			pageSize = Integer.parseInt(recordPerPage);
		}
		return this.query(request,intPageNo,pageSize,conditions);
	}	 
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public Page query(HttpServletRequest request,int pageNo,int pageSize,String conditions) throws Exception {
		HttpSession session = request.getSession();
		UserDto user = (UserDto) (session.getAttribute("user"));
		user.setQueryCondition("prpLacciCheck", conditions, pageNo,pageSize);
		Page page = prpLacciCheckService.findByConditions(conditions, pageNo, pageSize);
		PrpLregist prpLregist = new PrpLregist();
		prpLregist.setRegistList(page.getResult());
		request.setAttribute("page", page);
		request.setAttribute("prpLregist",prpLregist);
		return page;
	}
	public PrpLacciCheckService getPrpLacciCheckService() {
		return prpLacciCheckService;
	}
	public void setPrpLacciCheckService(PrpLacciCheckService prpLacciCheckService) {
		this.prpLacciCheckService = prpLacciCheckService;
	}
	
}