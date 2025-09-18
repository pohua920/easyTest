package com.tlg.prpins.dao;

import java.util.Map;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
public interface OthPassbookSpDao {
	
	public void runSpOthPassbookMarP(Map<String,Object> params) throws Exception;

	public void runSpOthPassbookMarE(Map<String,Object> params) throws Exception;

	public void runSpOthPassbookFirP(Map<String,Object> params) throws Exception;

	public void runSpOthPassbookFirE(Map<String,Object> params) throws Exception;

	public void runSpOthPassbookCalP(Map<String,Object> params) throws Exception;

	public void runSpOthPassbookCalE(Map<String,Object> params) throws Exception;

	public void runSpOthPassbookCarP(Map<String,Object> params) throws Exception;

	public void runSpOthPassbookCarE(Map<String,Object> params) throws Exception;

	public void runSpOthPassbookLopP(Map<String,Object> params) throws Exception;

	public void runSpOthPassbookLopE(Map<String,Object> params) throws Exception;
}