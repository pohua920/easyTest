package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/**
 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
 */
public interface FirAgtYcb02Service {
	@SuppressWarnings("rawtypes")
	public Result selectForGenFile(Map params) throws SystemException, Exception;
}
