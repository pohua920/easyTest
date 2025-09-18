package com.tlg.prpins.service;

import java.util.Map;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
public interface OthPassbookSpService {
	
	public int runSpOthPassbookMarP(Map<String, Object> params) throws Exception;

	public int runSpOthPassbookMarE(Map<String, Object> params) throws Exception;

	public int runSpOthPassbookFirP(Map<String, Object> params) throws Exception;

	public int runSpOthPassbookFirE(Map<String, Object> params) throws Exception;

	public int runSpOthPassbookCalP(Map<String, Object> params) throws Exception;

	public int runSpOthPassbookCalE(Map<String, Object> params) throws Exception;

	public int runSpOthPassbookCarP(Map<String, Object> params) throws Exception;

	public int runSpOthPassbookCarE(Map<String, Object> params) throws Exception;

	public int runSpOthPassbookLopP(Map<String, Object> params) throws Exception;

	public int runSpOthPassbookLopE(Map<String, Object> params) throws Exception;
	
}
