package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.aps.vo.Aps034FbDetailVo;
import com.tlg.aps.vo.Aps034genFileVo;
import com.tlg.aps.vo.Aps055BotDetailVo;
import com.tlg.aps.vo.Aps055BotGenFileVo;
import com.tlg.aps.vo.Aps057ResultVo;
import com.tlg.aps.vo.Aps060YcbDetailVo;
import com.tlg.aps.vo.Aps060YcbGenFileVo;
import com.tlg.aps.vo.FirBopRenewalDataVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnBatchDtlDao;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnBatchDtlServiceImpl implements FirAgtrnBatchDtlService{
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	private FirAgtrnBatchDtlDao firAgtrnBatchDtlDao;

	@Override
	public Result findFirAgtrnBatchDtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchDtlDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnBatchDtl> searchResult = firAgtrnBatchDtlDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnBatchDtlByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnBatchDtl> searchResult = firAgtrnBatchDtlDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnBatchDtl(Map params) throws SystemException, Exception {
		return firAgtrnBatchDtlDao.count(params);
	}

	@Override
	public Result insertFirAgtrnBatchDtl(FirAgtrnBatchDtl firAgtrnBatchDtl) throws SystemException, Exception {

		if (firAgtrnBatchDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firAgtrnBatchDtlDao.insert(firAgtrnBatchDtl);

		if(firAgtrnBatchDtlDao.isUnique(firAgtrnBatchDtl)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchDtl);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnBatchDtl(FirAgtrnBatchDtl firAgtrnBatchDtl) throws SystemException, Exception {

		if (firAgtrnBatchDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnBatchDtlDao.update(firAgtrnBatchDtl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnBatchDtl);
		return result;
	}

	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  start */
	@Override
	public Result findInsuredDataJoinTocoreMain(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps016DetailVo searchResult = firAgtrnBatchDtlDao.findInsuredDataJoinTocoreMain(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirAgtrnBatchDtlForExcel(String batchNo) throws SystemException, Exception {
		if (null == batchNo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Aps016DetailVo> searchResult = firAgtrnBatchDtlDao.findFirAgtrnBatchDtlForExcel(batchNo);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findFirAgtrnBatchDtlForDetail(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchDtlDao.countFirAgtrnBatchDtlForDetail(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<Aps016DetailVo> searchResult = firAgtrnBatchDtlDao.findFirAgtrnBatchDtlForDetail(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  end */
	
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 start*/
	@Override
	public Result findRenewalDataByBatchNo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchDtlDao.countRenewalDataByBatchNo(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirBopRenewalDataVo> searchResult = firAgtrnBatchDtlDao.findRenewalDataByBatchNo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findPolicyCountByBatchNo(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirBopRenewalDataVo searchResult = firAgtrnBatchDtlDao.selectCountPolicyByBatchNo(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findBopRnDataForExcelByBatchNo(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		 List<FirBopRenewalDataVo> searchResult = firAgtrnBatchDtlDao.selectBopRnDataForExcelByBatchNo(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findRenewalDataForExcelByBatchNo(String batchNo) throws SystemException, Exception {
		if (null == batchNo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBopRenewalDataVo> searchResult = firAgtrnBatchDtlDao.findRenewalDataForExcelByBatchNo(batchNo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 end*/
	
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 start*/
	@Override
	public Result findForFbrnDetail(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchDtlDao.countForFbrnDetail(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<Aps034FbDetailVo> searchResult = firAgtrnBatchDtlDao.selectForFbrnDetail(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findFbrnInsuredData(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps034FbDetailVo searchResult = firAgtrnBatchDtlDao.selectForFbrnInsuredData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findForFbRejectFile(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Result result = new Result();
		List<Aps034genFileVo> searchResult = firAgtrnBatchDtlDao.selectForFbRejectFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findForFbRenewalData(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Aps034genFileVo> searchResult = firAgtrnBatchDtlDao.selectForFbRenewalData(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countCoreInsured(Map params) throws SystemException, Exception {
		return firAgtrnBatchDtlDao.countCoreInsured(params);
	}
	
	@Override
	public Result findCoreNotInsuredData(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps034genFileVo searchResult = firAgtrnBatchDtlDao.selectCoreNotInsuredData(params);
		if (null == searchResult.getBseq()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findForFbRenewalFkind(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Aps034genFileVo> searchResult = firAgtrnBatchDtlDao.selectForFbFkind(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/* mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 end*/
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Result findForBotrnDetail(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchDtlDao.countForBotrnDetail(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<Aps055BotDetailVo> searchResult = firAgtrnBatchDtlDao.selectForBotrnDetail(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Result findBotRnDataForXlsByBatchNo(PageInfo pageInfo) throws SystemException, Exception {
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Result result = new Result();
		List<Aps055BotGenFileVo> searchResult = firAgtrnBatchDtlDao.selectForBotrnXlsByParams(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Result findBotrnInsuredData(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps055BotDetailVo searchResult = firAgtrnBatchDtlDao.selectForBotrnInsuredData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業  */
	@SuppressWarnings("unchecked")
	@Override
	public Result findAPS057Main2ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchDtlDao.countForAps057(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps057ResultVo> searchResult = firAgtrnBatchDtlDao.selectForAps057(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業  */
	@SuppressWarnings("unchecked")
	@Override
	public int countForAps057Main2(Map params) throws SystemException, Exception {
		return firAgtrnBatchDtlDao.countForAps057(params);
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public Result findForYcbrnDetail(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnBatchDtlDao.countForYcbrnDetail(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<Aps060YcbDetailVo> searchResult = firAgtrnBatchDtlDao.selectForYcbrnDetail(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public Result findYcbRnDataForXlsByBatchNo(PageInfo pageInfo) throws SystemException, Exception {
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Result result = new Result();
		List<Aps060YcbGenFileVo> searchResult = firAgtrnBatchDtlDao.selectForYcbrnXlsByParams(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	@Override
	public Result findYcbrnInsuredData(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps060YcbDetailVo searchResult = firAgtrnBatchDtlDao.selectForYcbrnInsuredData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	public FirAgtrnBatchDtlDao getFirAgtrnBatchDtlDao() {
		return firAgtrnBatchDtlDao;
	}

	public void setFirAgtrnBatchDtlDao(FirAgtrnBatchDtlDao firAgtrnBatchDtlDao) {
		this.firAgtrnBatchDtlDao = firAgtrnBatchDtlDao;
	}
}
