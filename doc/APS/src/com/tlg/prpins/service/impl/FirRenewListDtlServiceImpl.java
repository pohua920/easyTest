package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps044DetailVo;
import com.tlg.aps.vo.FirRenewListForFileVo;
import com.tlg.aps.vo.FirRenewListForPremVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirRenewListDtlDao;
import com.tlg.prpins.entity.FirRenewListDtl;
import com.tlg.prpins.service.FirRenewListDtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRenewListDtlServiceImpl implements FirRenewListDtlService{

	private FirRenewListDtlDao firRenewListDtlDao;

	@Override
	public int countFirRenewListDtl(Map params) throws SystemException, Exception {
		return firRenewListDtlDao.count(params);
	}

	@Override
	public Result findFirRenewListDtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firRenewListDtlDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirRenewListDtl> searchResult = firRenewListDtlDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirRenewListDtlByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRenewListDtl> searchResult = firRenewListDtlDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateFirRenewListDtl(FirRenewListDtl firRenewListDtl) throws SystemException, Exception {

		if (firRenewListDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firRenewListDtlDao.update(firRenewListDtl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firRenewListDtl);
		return result;
	}

	@Override
	public Result insertFirRenewListDtl(FirRenewListDtl firRenewListDtl) throws SystemException, Exception {

		if (firRenewListDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		BigDecimal oid = firRenewListDtlDao.insert(firRenewListDtl);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		firRenewListDtl.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firRenewListDtl);
		return result;
	}

	@Override
	public Result removeFirRenewListDtl(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirRenewListDtl persisted = firRenewListDtlDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firRenewListDtlDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result findRenewListForPrem(Map params) throws SystemException, Exception{
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRenewListForPremVo> searchResult = firRenewListDtlDao.selectRenewListForPrem(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 start*/
	@SuppressWarnings("unchecked")
	@Override
	public Result findForAps044DetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if ((boolean) pageInfo.getFilter().get("doPage")) {
			int rowCount = firRenewListDtlDao.countForAps044Detail(pageInfo.getFilter());
			if (0 == rowCount) {
				result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
				return result;
			}
			pageInfo.setRowCount(rowCount);
			pageInfo.doPage();
		}

		List<Aps044DetailVo> searchResult = firRenewListDtlDao.selectForAps044Detail(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int countForAps044Detail(Map params) throws SystemException, Exception {
		return firRenewListDtlDao.countForAps044Detail(params);
	}
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 end*/

	public FirRenewListDtlDao getFirRenewListDtlDao() {
		return firRenewListDtlDao;
	}

	public void setFirRenewListDtlDao(FirRenewListDtlDao firRenewListDtlDao) {
		this.firRenewListDtlDao = firRenewListDtlDao;
	}
	
	/**
	 * mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程
	 */
	@Override
	public Result findRenewListForMail(Map params) throws SystemException, Exception{
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRenewListForPremVo> searchResult = firRenewListDtlDao.selectRenewListForMail(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	@Override
	public Result findRenewListForOtherFile(Map params) throws SystemException, Exception{
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRenewListForFileVo> searchResult = firRenewListDtlDao.selectRenewListForOtherFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findRenewListForCoreFile(Map params) throws SystemException, Exception{
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRenewListForFileVo> searchResult = firRenewListDtlDao.selectRenewListForCoreFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
}
