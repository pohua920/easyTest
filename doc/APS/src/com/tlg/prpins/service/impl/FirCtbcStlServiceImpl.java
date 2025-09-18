package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcStlDao;
import com.tlg.prpins.entity.FirCtbcStl;
import com.tlg.prpins.service.FirCtbcStlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcStlServiceImpl implements FirCtbcStlService{

	private FirCtbcStlDao firCtbcStlDao;

	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	 *新增「轉檔異常資料修正」功能 */
	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcStlByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcStl> searchResult = firCtbcStlDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	 *新增「轉檔異常資料修正」功能 */
	@Override
	public Result updateFirCtbcStl(FirCtbcStl firCtbcStl) throws SystemException, Exception {
		if (firCtbcStl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcStlDao.update(firCtbcStl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcStl);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcStl(FirCtbcStl firCtbcStl) throws SystemException, Exception {
		if (firCtbcStl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcStl.getBatchNo());
//		params.put("batchSeq",firCtbcStl.getBatchSeq());
//		params.put("filename",firCtbcStl.getFilename());
//		params.put("fkOrderSeq",firCtbcStl.getFkOrderSeq());
//		int count = firCtbcStlDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		BigDecimal oid = firCtbcStlDao.insert(firCtbcStl);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcStl.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcStl);
		return result;
	}

	@Override
	public Result removeFirCtbcStl(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public FirCtbcStlDao getFirCtbcStlDao() {
		return firCtbcStlDao;
	}

	public void setFirCtbcStlDao(FirCtbcStlDao firCtbcStlDao) {
		this.firCtbcStlDao = firCtbcStlDao;
	}

}
