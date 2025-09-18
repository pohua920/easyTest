package com.tlg.aps.bs.agentBranchBatchUploadService;

import java.io.File;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface AgentBranchBatchUploadService {

	public Result uploadBatchData(String userId, File uploadFile) throws SystemException, Exception;
	
}
