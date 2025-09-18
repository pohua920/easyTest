package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps034DownloadlVo;
import com.tlg.aps.vo.Aps055BotDetailVo;
import com.tlg.aps.vo.Aps055DownloadlVo;
import com.tlg.aps.vo.Aps060DownloadlVo;
import com.tlg.aps.vo.Aps060YcbDetailVo;
import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnBatchMainDao;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnBatchMainServiceImpl implements FirAgtrnBatchMainService{
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	private FirAgtrnBatchMainDao firAgtrnBatchMainDao;

	@Override
	public Result findFirAgtrnBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchMainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnBatchMain> searchResult = firAgtrnBatchMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnBatchMainByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnBatchMain> searchResult = firAgtrnBatchMainDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnBatchMain(Map params) throws SystemException, Exception {
		return firAgtrnBatchMainDao.count(params);
	}

	@Override
	public Result insertFirAgtrnBatchMain(FirAgtrnBatchMain firAgtrnBatchMain) throws SystemException, Exception {

		if (firAgtrnBatchMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firAgtrnBatchMainDao.insert(firAgtrnBatchMain);

		if(firAgtrnBatchMainDao.isUnique(firAgtrnBatchMain)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchMain);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnBatchMain(FirAgtrnBatchMain firAgtrnBatchMain) throws SystemException, Exception {

		if (firAgtrnBatchMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnBatchMainDao.update(firAgtrnBatchMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchMain);
		return result;
	}
	
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 */
	@Override
	public Result findFbrnDownloadData(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps034DownloadlVo searchResult = firAgtrnBatchMainDao.findForFbrnDownloadData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findFirAgtrnBatchMainByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirAgtrnBatchMain firAgtrnBatchMain = firAgtrnBatchMainDao.findByUK(params);
		if (null == firAgtrnBatchMain) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firAgtrnBatchMain);
		}
		return result;
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	@Override
	public Result findBatchMainForBoprnByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchMainDao.countBatchMainForBoprn(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirPahsinRenewalVo> searchResult = firAgtrnBatchMainDao.selectBatchMainForBoprn(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Result findBotrnDownloadData(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps055DownloadlVo searchResult = firAgtrnBatchMainDao.findForBotrnDownloadData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Result findBatchMainForBotrnByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchMainDao.countBatchMainForYcbrn(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<Aps060YcbDetailVo> searchResult = firAgtrnBatchMainDao.selectBatchMainForYcbrn(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
	@Override
	public Result findBatchMainForBotrnIntoCore() throws SystemException, Exception {
		Result result = new Result();
		
		List<Aps055BotDetailVo> searchResult = firAgtrnBatchMainDao.selectBatchMainForBotrnIntoCore();
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public Result findBatchMainForYcbrnByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchMainDao.countBatchMainForYcbrn(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<Aps060YcbDetailVo> searchResult = firAgtrnBatchMainDao.selectBatchMainForYcbrn(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public Result findYcbrnDownloadData(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps060DownloadlVo searchResult = firAgtrnBatchMainDao.findForYcbrnDownloadData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	public FirAgtrnBatchMainDao getFirAgtrnBatchMainDao() {
		return firAgtrnBatchMainDao;
	}

	public void setFirAgtrnBatchMainDao(FirAgtrnBatchMainDao firAgtrnBatchMainDao) {
		this.firAgtrnBatchMainDao = firAgtrnBatchMainDao;
	}
}
