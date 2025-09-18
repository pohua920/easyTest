package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps046ResultVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.TmpfetclaimmainDao;
import com.tlg.prpins.entity.Tmpfetclaimmain;
import com.tlg.prpins.service.TmpfetclaimmainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class TmpfetclaimmainServiceImpl implements TmpfetclaimmainService{

	private TmpfetclaimmainDao tmpfetclaimmainDao;

	@Override
	public int countTmpfetclaimmain(Map params) throws SystemException, Exception {
		return tmpfetclaimmainDao.count(params);
	}
	
	@Override
	public int countMultiClaim(String policyNo) throws SystemException,
			Exception {
		return tmpfetclaimmainDao.countMultiClaim(policyNo);
	}

	/**
	 * mantis：CLM0200，處理人員：DP0714，APS-行動裝置險資料轉入出險日期重複檢核確認
	 */
	@Override
	public int countMultiClaim2(Map params) throws SystemException, Exception {
		return tmpfetclaimmainDao.countMultiClaim2(params);
	}

	@Override
	public Result findTmpfetclaimmainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = tmpfetclaimmainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Tmpfetclaimmain> searchResult = tmpfetclaimmainDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTmpfetclaimmainByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Tmpfetclaimmain> searchResult = tmpfetclaimmainDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateTmpfetclaimmain(Tmpfetclaimmain tmpfetclaimmain) throws SystemException, Exception {
		
		if (tmpfetclaimmain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = tmpfetclaimmainDao.update(tmpfetclaimmain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(tmpfetclaimmain);
		return result;
	}

	@Override
	public Result insertTmpfetclaimmain(Tmpfetclaimmain tmpfetclaimmain) throws SystemException, Exception {

		if (tmpfetclaimmain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = tmpfetclaimmainDao.insert(tmpfetclaimmain);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(tmpfetclaimmain);
		return result;
	}

	@Override
	public Result removeTmpfetclaimmain(BigDecimal oid) throws SystemException, Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Tmpfetclaimmain persisted = tmpfetclaimmainDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = tmpfetclaimmainDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 start */
	@Override
	public Result removeTmpfetclaimmainAll() throws SystemException, Exception {
		boolean status = tmpfetclaimmainDao.removeAll();
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	@Override
	public void batchInsertTmpfetclaimmain(List<Tmpfetclaimmain> listTmpfetclaimmain) throws SystemException,
			Exception {
		this.tmpfetclaimmainDao.processInBatch(listTmpfetclaimmain);
	}
	
	@Override
	public Result findPayoutDataByParams(String policyNo) throws SystemException, Exception {
		
		if (StringUtil.isSpace(policyNo)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Map<String, String>> searchResult = tmpfetclaimmainDao.getPayoutData(policyNo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 end */
	
	/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業  start*/
	@Override
	public Result sumWda35() throws SystemException, Exception {
		Result result = new Result();
		Aps046ResultVo aps046ResultVo = tmpfetclaimmainDao.selectSumWda35();
		if (null == aps046ResultVo) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(aps046ResultVo);
		}
		return result;
	}

	@Override
	public Result findForMainDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = tmpfetclaimmainDao.countForMainData(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps046ResultVo> searchResult = tmpfetclaimmainDao.selectForMainData(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業  end*/
	
	public TmpfetclaimmainDao getTmpfetclaimmainDao() {
		return tmpfetclaimmainDao;
	}

	public void setTmpfetclaimmainDao(TmpfetclaimmainDao tmpfetclaimmainDao) {
		this.tmpfetclaimmainDao = tmpfetclaimmainDao;
	}

}
