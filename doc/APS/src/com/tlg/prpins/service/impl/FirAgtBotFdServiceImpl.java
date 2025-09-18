package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps057ResultVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtBotFdDao;
import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.prpins.service.FirAgtBotFdService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtBotFdServiceImpl implements FirAgtBotFdService{
	private FirAgtBotFdDao firAgtBotFdDao;



	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtBotFdByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtBotFd> searchResult = firAgtBotFdDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtBotFd(Map params) throws SystemException, Exception {
		return firAgtBotFdDao.count(params);
	}

	@Override
	public Result insertFirAgtBotFd(FirAgtBotFd firAgtBotFd) throws SystemException, Exception {

		if (firAgtBotFd == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		firAgtBotFdDao.insert(firAgtBotFd);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtBotFd);
		return result;
	}
	
	@Override
	public Result updateFirAgtBotFd(FirAgtBotFd firAgtBotFd) throws SystemException, Exception {

		if (firAgtBotFd == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtBotFdDao.update(firAgtBotFd);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtBotFd);
		return result;
	}
	
	/** mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業  */
	@SuppressWarnings("unchecked")
	@Override
	public Result findAPS057Main1ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBotFdDao.countForAps057(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps057ResultVo> searchResult = firAgtBotFdDao.selectForAps057(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirAgtBotFdDao getFirAgtBotFdDao() {
		return firAgtBotFdDao;
	}

	public void setFirAgtBotFdDao(FirAgtBotFdDao firAgtBotFdDao) {
		this.firAgtBotFdDao = firAgtBotFdDao;
	}
	
}
