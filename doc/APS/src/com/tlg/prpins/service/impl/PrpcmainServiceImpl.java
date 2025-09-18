package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps032CompareVo;
import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.aps.vo.WriteForBatchSendmailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpcmainDao;
import com.tlg.prpins.entity.Prpcmain;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpcmainServiceImpl implements PrpcmainService{

	private PrpcmainDao prpcmainDao;

	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpcmainByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpcmain> searchResult = prpcmainDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  START**/
	@Override
	public Result findPAAs400PrefilEpolicy() throws SystemException, Exception {
		Result result = new Result();
		List<Prpcmain> searchResult = prpcmainDao.findAS400PrefilEpolicy();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  END**/

	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  START**/
	@Override
	public Result findPAAs400HeccfilEpolicy() throws SystemException, Exception {
		Result result = new Result();
		List<Prpcmain> searchResult = prpcmainDao.findAS400HeccfilEpolicy();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  END**/
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  START**/
	@Override
	public Result findPAAs400TolfilEpolicy() throws SystemException, Exception {
		Result result = new Result();
		List<Prpcmain> searchResult = prpcmainDao.findAS400TolfilEpolicy();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  END**/

	/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 start*/
	@Override
	public Result findToCompareTiiTvbcm(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Aps032CompareVo> searchResult = prpcmainDao.findToCompareTiiTvbcm(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findPolicynoToTvbcm(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Prpcmain> searchResult = prpcmainDao.findPolicynoToTvbcm(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 end*/

	/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險) start*/
	@Override
	public Result findForBatchSendmail(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<WriteForBatchSendmailVo> searchResult = prpcmainDao.findForBatchSendmail(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findForBatchSendmailDtl(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<WriteForBatchSendmailVo> searchResult = prpcmainDao.findForBatchSendmailDtl(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險) end*/
	
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start*/
	@Override
	public Result findForPanhsinRenewalCoreData(Map params) throws SystemException, Exception {
		Result result = new Result();
		FirPahsinRenewalVo searchResult = prpcmainDao.findForPanhsinRenewalCoreData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end*/
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  START**/
	@Override
	public Result findAs400TAPrefilEpolicy() throws SystemException, Exception {
		Result result = new Result();
		List<Prpcmain> searchResult = prpcmainDao.findAS400TAPrefilEpolicy();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  END**/

	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  START**/
	@Override
	public Result findAs400TAHeccfilEpolicy() throws SystemException, Exception {
		Result result = new Result();
		List<Prpcmain> searchResult = prpcmainDao.findAS400TAHeccfilEpolicy();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  END**/
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  START**/
	@Override
	public Result findAs400TATolfilEpolicy() throws SystemException, Exception {
		Result result = new Result();
		List<Prpcmain> searchResult = prpcmainDao.findAS400TATolfilEpolicy();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  END**/
	
	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  start**/
	@Override
	public Result findForRerunFpolicy(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Prpcmain> searchResult =prpcmainDao.findForRerunFpolicy(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  end**/
	
	public PrpcmainDao getPrpcmainDao() {
		return prpcmainDao;
	}

	public void setPrpcmainDao(PrpcmainDao prpcmainDao) {
		this.prpcmainDao = prpcmainDao;
	}



}
