package com.sinosoft.claim.common.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.common.service.facade.ProcessCodeInputService;
import com.sinosoft.platform.dto.domain.UtiUserGradeDto;

public class ProcessCodeInputAction extends Struts2Action {

	private static final long serialVersionUID = 1L;

	/** 数据结果集 */
	private Page page = null;
	/** 用户代码 */
	private String userCode;
	/** 基础代码处理接口 */
	private ProcessCodeInputService processCodeInputService;

	@SuppressWarnings("unchecked")
	public String getComCodeOptionsText() throws Exception {
		StringBuffer message = new StringBuffer();
		Map comCodeMap = new HashMap();
		List<UtiUserGradeDto> utiUserGradeDtoList = new ArrayList<UtiUserGradeDto>();
		try {
			Collection<UtiUserGradeDto> UtiUserGradeDtos = processCodeInputService.getComCodeOptionsText(userCode.trim());
			for (Iterator<UtiUserGradeDto> iter = UtiUserGradeDtos.iterator(); iter.hasNext();) {
				UtiUserGradeDto element = (UtiUserGradeDto) iter.next();
				if (!comCodeMap.containsKey(element.getComCode())) {
					utiUserGradeDtoList.add(element);
					comCodeMap.put(element.getComCode(), "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.append(e.getMessage());
		}

		page = new Page(0, utiUserGradeDtoList.size(), utiUserGradeDtoList.size(), utiUserGradeDtoList);
		page.setMessage("success");
		writeJSONData(page, "comCode", "comName");
		return NONE;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public ProcessCodeInputService getProcessCodeInputService() {
		return processCodeInputService;
	}

	public void setProcessCodeInputService(ProcessCodeInputService processCodeInputService) {
		this.processCodeInputService = processCodeInputService;
	}
}