package com.sinosoft.claim.common.service.facade;

import java.util.Collection;

import com.sinosoft.platform.dto.domain.UtiUserGradeDto;

public interface ProcessCodeInputService {
	/***
	 * 获取岗位机构信息
	 * @param userCode：用户代码
	 * @throws Exception
	 */
	public Collection<UtiUserGradeDto> getComCodeOptionsText(String userCode)throws Exception;

}
