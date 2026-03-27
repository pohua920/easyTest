package com.sinosoft.undwrt.common.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sinosoft.platform.dto.domain.UtiUserGradeDto;
import com.sinosoft.undwrt.common.service.facade.ProcessCodeInputService;

/**
 * 登入機構處理類.
 */
public class ProcessCodeInputAction extends Struts2Action {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 屬性Page對象. */
	private Page page = null;
	
	/** 屬性用戶代碼. */
	private String userCode;
	
	/** 屬性輸入員工代碼自動帶出機構代碼的接口. */
	private ProcessCodeInputService processCodeInputService;
	
	/**
	 * 獲取登入機構訊息.
	 * 
	 * @return 登入機構訊息
	 * @throws Exception
	 *             異常
	 */
	public String getComCodeOptionsText() throws Exception {
        StringBuffer message = new StringBuffer();
        Map<String,String> comCodeMap = new HashMap<String,String>();
        List<UtiUserGradeDto> utiUserGradeDtoList = new ArrayList<UtiUserGradeDto>();
        try {
        	
        	Collection<UtiUserGradeDto> UtiUserGradeDtos = processCodeInputService.getComCodeOptionsText(userCode);
            for (Iterator<UtiUserGradeDto> iter = UtiUserGradeDtos.iterator(); iter.hasNext();) {
                UtiUserGradeDto element =	iter.next();
                if (!comCodeMap.containsKey(element.getComCode())) {
                	utiUserGradeDtoList.add(element);
                    comCodeMap.put(element.getComCode(), "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.append(e.getMessage());
        }
        
        page = new Page(0,utiUserGradeDtoList.size(),10,utiUserGradeDtoList);
        page.setMessage("success");                       
		writeJSONData(page,"comCode","comName");
        return NONE;
    }
	
	/**
	 * 獲取屬性用戶代碼.
	 * 
	 * @return 屬性用戶代碼的值
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * 設置屬性用戶代碼.
	 * 
	 * @param userCode
	 *            待設置的用戶代碼的值
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 獲取屬性輸入員工代碼自動帶出機構代碼的接口.
	 * 
	 * @return 屬性輸入員工代碼自動帶出機構代碼的接口的值
	 */
	public ProcessCodeInputService getProcessCodeInputService() {
		return processCodeInputService;
	}

	/**
	 * 設置屬性輸入員工代碼自動帶出機構代碼的接口.
	 * 
	 * @param processCodeInputService
	 *            待設置的輸入員工代碼自動帶出機構代碼的接口的值
	 */
	public void setProcessCodeInputService(
			ProcessCodeInputService processCodeInputService) {
		this.processCodeInputService = processCodeInputService;
	}
}