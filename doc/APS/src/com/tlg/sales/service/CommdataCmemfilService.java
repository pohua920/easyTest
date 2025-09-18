package com.tlg.sales.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格*/
public interface CommdataCmemfilService {

	public Result findCommdataCmemfilByParams(Map params) throws SystemException, Exception;
	
	public Result findCommdataCmemfilByUk(Map params) throws SystemException, Exception;
	
	public Result FindForFirChangeHandler1code(Map params) throws SystemException, Exception;
}
