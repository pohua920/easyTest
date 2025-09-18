package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ProposalFile;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 */
public interface ProposalFileService {

	public int countProposalFile(Map params) throws SystemException, Exception;
	
	public Result findProposalFileByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findProposalFileByParams(Map params) throws SystemException, Exception;

	public Result findProposalFileByUK(String transactionId) throws SystemException, Exception;

	public Result updateProposalFile(ProposalFile proposalFile) throws SystemException, Exception;

	public Result insertProposalFile(ProposalFile proposalFile) throws SystemException, Exception;

	public Result removeProposalFile(String transactionId) throws SystemException, Exception;
	
}
