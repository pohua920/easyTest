package com.sinosoft.undwrt.common.service.facade;

import java.util.Collection;

import com.sinosoft.platform.dto.domain.UtiUserGradeDto;

// TODO: Auto-generated Javadoc
/**
 * 輸入員工代碼自動帶出機構代碼的接口類.
 */
public interface ProcessCodeInputService {

	/**
	 * 得到可選代碼結構的集合.
	 * 
	 * @param userCode
	 *            人員工號
	 * @return 可選的機構代碼
	 * @throws Exception
	 *             異常
	 */
	public Collection<UtiUserGradeDto> getComCodeOptionsText(String userCode) throws Exception;

}
