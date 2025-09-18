package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 */
import com.tlg.aps.vo.Aps038DetailVo;
import com.tlg.aps.vo.Aps041DetailVo1;
import com.tlg.aps.vo.Aps041DetailVo2;
import com.tlg.aps.vo.FirUbProcessVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtBatchDtlDao;
import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.prpins.entity.FirAgtUb02;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtBatchDtlServiceImpl implements FirAgtBatchDtlService{
	/*mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
	private FirAgtBatchDtlDao firAgtBatchDtlDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtBatchDtlByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtBatchDtl> searchResult = firAgtBatchDtlDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result findAPS009Detail01ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBatchDtlDao.countForAps009Detail01(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirAgtBatchDtl> searchResult = firAgtBatchDtlDao.selectForAps009Detail01(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result findAPS009Detail03ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBatchDtlDao.countForAps009Detail03(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirAgtBatchDtl> searchResult = firAgtBatchDtlDao.selectForAps009Detail03(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
	@Override
	public Result updateFirAgtBatchDtlByParams(Map<String,Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Integer count = firAgtBatchDtlDao.updateByParams(params);
		Result result = new Result();
		if (null == count || 0 == count) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		}else {
			result.setResObject(count);
		}
		return result;
	}
	
	/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 start */
	@SuppressWarnings("unchecked")
	@Override
	public Result findAPS038Detail01ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBatchDtlDao.countForAps038Detail01(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps038DetailVo> searchResult = firAgtBatchDtlDao.selectForAps038Detail01(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result findAPS038Detail03ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBatchDtlDao.countForAps038Detail03(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps038DetailVo> searchResult = firAgtBatchDtlDao.selectForAps038Detail03(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/* mantis：FIR0495，處理人員：CC009，需求單編號：FIR0495_住火-APS板信回饋檔-排程查詢作業 end */
	
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 start */
	@Override
	public Result findAPS041Dtl1ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBatchDtlDao.countForAps041Dtl1(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps041DetailVo1> searchResult = firAgtBatchDtlDao.selectForAps041Dtl1(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findAPS041Dtl2ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBatchDtlDao.countForAps041Dtl2(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps041DetailVo2> searchResult = firAgtBatchDtlDao.selectForAps041Dtl2(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 end */

	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 start*/
	@Override
	public Result insertFirAgtBatchDtl(FirAgtBatchDtl firAgtBatchDtl) throws SystemException, Exception {
		if (firAgtBatchDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
	
		firAgtBatchDtlDao.insert(firAgtBatchDtl);
	
		if(firAgtBatchDtlDao.isUnique(firAgtBatchDtl)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtBatchDtl);
		return result;
	}
	
	@Override
	public Result updateFirAgtBatchDtl(FirAgtBatchDtl firAgtBatchDtl) throws SystemException, Exception {
		if (firAgtBatchDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtBatchDtlDao.update(firAgtBatchDtl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtBatchDtl);
		return result;
	}
	
	@Override
	public Result findFirAgtBatchDtlByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirAgtBatchDtl searchResult = firAgtBatchDtlDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findForUbProposalEmail(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirUbProcessVo> searchResult = firAgtBatchDtlDao.selectForUbProposalEmail(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 end*/
	
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 start*/
	@Override
	public Result findForUbBackFile(Map params) throws SystemException, Exception{
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtUb02> searchResult = firAgtBatchDtlDao.selectForUbBackFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findForUbBackFileEmail(Map params) throws SystemException, Exception{
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtBatchDtl> searchResult = firAgtBatchDtlDao.selectForUbBackFileEmail(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 end*/
	
	public FirAgtBatchDtlDao getFirAgtBatchDtlDao() {
		return firAgtBatchDtlDao;
	}


	public void setFirAgtBatchDtlDao(FirAgtBatchDtlDao firAgtBatchDtlDao) {
		this.firAgtBatchDtlDao = firAgtBatchDtlDao;
	}
}
