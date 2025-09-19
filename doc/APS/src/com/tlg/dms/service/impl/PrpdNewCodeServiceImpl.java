package com.tlg.dms.service.impl;

// mantis：FIR0651，處理人員：DP0714，商住火65歲業務員高齡教育訓練檢核及APS例外設定
//mantis： HAS0254，處理人員：DP0706，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.dms.dao.PrpdNewCodeDao;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdNewCodeServiceImpl implements PrpdNewCodeService{

	private PrpdNewCodeDao prpdNewCodeDao;

	@Override
	public int countPrpdNewCode(Map params) throws SystemException, Exception {
		return prpdNewCodeDao.count(params);
	}

	@Override
	public Result findPrpdNewCodeByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdNewCodeDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		//mantis： HAS0254，處理人員：DP0706，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳
        // mantis：FIR0651，處理人員：DP0714，商住火65歲業務員高齡教育訓練檢核及APS例外設定
		List<PrpdNewCode> searchResult = prpdNewCodeDao.findByParams(pageInfo.getFilter());
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdNewCodeByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PrpdNewCode> searchResult = prpdNewCodeDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 start */
	@Override
	public Result updatePrpdNewCode(PrpdNewCode prpdNewCode) throws SystemException, Exception {
		if (prpdNewCode == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpdNewCodeDao.update(prpdNewCode);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(prpdNewCode);
		return result;
	}
	/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 end */

	/**
	 * mantis： HAS0254，處理人員：DP0706，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳
	 * mantis：FIR0651，處理人員：DP0714，商住火65歲業務員高齡教育訓練檢核及APS例外設定
	 */
	@Override
	public Result insertPrpdnewcode(PrpdNewCode prpdNewCode) throws SystemException, Exception {
		if (prpdNewCode == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("codetype", prpdNewCode.getCodetype());
		params.put("codecode", prpdNewCode.getCodecode());
		int count = prpdNewCodeDao.count(params);
		if(count > 0) {
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}

		prpdNewCodeDao.insert(prpdNewCode);

		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(prpdNewCode);
		return result;
	}

	public PrpdNewCodeDao getPrpdNewCodeDao() {
		return prpdNewCodeDao;
	}

	public void setPrpdNewCodeDao(PrpdNewCodeDao prpdNewCodeDao) {
		this.prpdNewCodeDao = prpdNewCodeDao;
	}
}
