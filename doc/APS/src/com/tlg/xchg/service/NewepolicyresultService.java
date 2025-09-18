package com.tlg.xchg.service;

import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.NewepolicyVo;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
/** mantis：OTH0159，處理人員：CD094，需求單編號：OTH0159 電子保單系統條款檢核不通過資料通知(APS)  **/
public interface NewepolicyresultService {
	
	public Result findNewepolicyresultByParams(Map params) throws SystemException, Exception;
	
	public Result findNewepolicyresultByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findNewepolicyVo(PageInfo pageInfo)throws SystemException, Exception;

	public Result findYdayNewepolicyVo()throws SystemException, Exception;

	public String sendEmail(List<NewepolicyVo> newepolicyresultErrList)throws SystemException, Exception;
	
	public Result findPdfUrl(String policyno)throws SystemException, Exception;
}
