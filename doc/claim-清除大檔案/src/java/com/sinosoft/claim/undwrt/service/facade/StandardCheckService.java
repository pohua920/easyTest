/*
 * @(#)BLStandardCheck.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.facade;

import java.util.Collection;
import java.util.Map;


import com.sinosoft.platform.dto.domain.UtiUwConditionDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  <中科软>
 * @Date    <Feb 21, 2013>
 * @description 
 */
public interface StandardCheckService {
	
	/**
	 * 通过判处异常来将结果展示给用户 add 2006-12-4 by xukefeng
	 * 
	 * @param title
	 * @param standardData
	 * @param businessData
	 * @throws UserException
	 * @throws Exception 
	 */
	
	public void throwException(UtiUwConditionDto utiUwConditionDto, String businessData) throws UserException, Exception;

	public boolean checkHepei(Collection<?> standardList,Map<?,?> businessDataMap) throws Exception;
	
}
