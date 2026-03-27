package com.sinosoft.claim.undwrt.service.facade;

import com.sinosoft.undwrt.dto.custom.SubmitTaskDto;

public interface UndwrtSendClaimService {
	/**
	 * 发送立案数据
	 * @param submitTaskDto
	 * @return
	 * @throws Exception
	 */
	 public int sendClaimData(SubmitTaskDto submitTaskDto) throws Exception;

}
