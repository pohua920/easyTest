package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.enums.EnumYCBFile;
import com.tlg.aps.vo.Aps055BotGenFileVo;
import com.tlg.aps.vo.Aps060YcbGenFileVo;
import com.tlg.aps.vo.Aps060YcbGenPolicyFileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtTocoreMainDao;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.service.FirAgtTocoreMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtTocoreMainServiceImpl implements FirAgtTocoreMainService{
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	private FirAgtTocoreMainDao firAgtTocoreMainDao;

	@Override
	public Result findFirAgtTocoreMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtTocoreMainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtTocoreMain> searchResult = firAgtTocoreMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtTocoreMainByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtTocoreMain> searchResult = firAgtTocoreMainDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtTocoreMain(Map params) throws SystemException, Exception {
		return firAgtTocoreMainDao.count(params);
	}

	@Override
	public Result insertFirAgtTocoreMain(FirAgtTocoreMain firAgtTocoreMain) throws SystemException, Exception {

		if (firAgtTocoreMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAgtTocoreMainDao.insert(firAgtTocoreMain);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firAgtTocoreMain.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtTocoreMain);
		return result;
	}
	
	@Override
	public Result updateFirAgtTocoreMain(FirAgtTocoreMain firAgtTocoreMain) throws SystemException, Exception {

		if (firAgtTocoreMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtTocoreMainDao.update(firAgtTocoreMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtTocoreMain);
		return result;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Result findBotReFileDataByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		 List<Aps055BotGenFileVo> searchResult = firAgtTocoreMainDao.selectBotReFileData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Result findBotEnFileDataByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		 List<Aps055BotGenFileVo> searchResult = firAgtTocoreMainDao.selectBotEnFileData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Result findDistinctExtracomcodeByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		 List<FirAgtTocoreMain> searchResult = firAgtTocoreMainDao.selectDistinctExtracomcodeByParams(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public Result findYcbRnFileDataByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		 List<Aps060YcbGenFileVo> searchResult = firAgtTocoreMainDao.selectYcbRnFileData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public Result findYcbEnFileDataByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		 List<Aps060YcbGenFileVo> searchResult = firAgtTocoreMainDao.selectYcbEnFileData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/**
	 * FIR0690、FIR0693，處理人員：CF048，FIR0690、FIR0693 元大續保作業PhaseII_匯出出單明細檔、保單副本檔
	 * EnumFileType fileType	檔案類型
	 * Map params				篩選條件
	 */
	public Result findYcbFileDataByParams(EnumYCBFile ycbFile, Map params) throws SystemException, Exception{
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		 List<Aps060YcbGenPolicyFileVo> searchResult = firAgtTocoreMainDao.selectYcbFileData(ycbFile, params);
		if (null == searchResult || searchResult.size()==0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	
	/**
	 * mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
	 */
	@Override
	public Result updateFirAgtTocoreMainByOid(FirAgtTocoreMain firAgtTocoreMain) throws SystemException, Exception {

		if (firAgtTocoreMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtTocoreMainDao.updateByOid(firAgtTocoreMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtTocoreMain);
		return result;
	}

	public FirAgtTocoreMainDao getFirAgtTocoreMainDao() {
		return firAgtTocoreMainDao;
	}

	public void setFirAgtTocoreMainDao(FirAgtTocoreMainDao firAgtTocoreMainDao) {
		this.firAgtTocoreMainDao = firAgtTocoreMainDao;
	}

}
