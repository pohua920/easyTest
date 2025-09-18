package com.tlg.aps.bs.reinsInwardService.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.reinsInwardService.ReinsInwardService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardInsData;
import com.tlg.prpins.entity.ReinsInwardMainData;
import com.tlg.prpins.service.ReinsInwardInsDataService;
import com.tlg.prpins.service.ReinsInwardMainDataService;
import com.tlg.util.Constants;
import com.tlg.util.DateUtils;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;

@Transactional(value="prpinsTransactionManager", propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
public class ReinsInwardServiceImpl implements ReinsInwardService {
	
	private static final Logger logger = Logger.getLogger(ReinsInwardServiceImpl.class);
	private ReinsInwardMainDataService reinsInwardMainDataService;
	private ReinsInwardInsDataService reinsInwardInsDataService;
	private ReinsInwardNoServiceImpl reinsInwardPickNoService;
	
	@Override
	public Result createData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData,
			ArrayList<ReinsInwardInsData> reinsInwardInsDataList) throws SystemException,
			Exception {

		if(reinsInwardMainData == null ){
			throw new SystemException("無法取得分進資料主檔");
		}
		if("P".equals(reinsInwardMainData.getType()) && (reinsInwardInsDataList == null || reinsInwardInsDataList.size() == 0)){
			throw new SystemException("無法取得分進資料險種檔資料");
		}
		if(userInfo == null ){
			throw new SystemException("無法取得使用者資訊");
		}
		String proposalNo = "";
		
		//依類型取號取要保號
		if("P".equals(reinsInwardMainData.getType())){
			String poins = StringUtil.adjustNumber(reinsInwardInsDataList.get(0).getPoins(), "0", 2 , false);
			proposalNo = "P" + poins + DateUtils.getADSysDateString();
		}
		if("T".equals(reinsInwardMainData.getType())){
			//怕批單若沒有險種會出錯，因此先找保單出來，確認險種檔的出單險種是什麼
			if(reinsInwardInsDataList == null || reinsInwardInsDataList.size() == 0){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("policyNo", reinsInwardMainData.getPayNo());
				Result result = this.reinsInwardMainDataService.findReinsInwardMainDataByParams(params);
				if(result.getResObject() == null){
					throw new SystemException("無法取得保單資料主檔");
				}
				ArrayList<ReinsInwardMainData> mainList = (ArrayList<ReinsInwardMainData>)result.getResObject();
				ReinsInwardMainData tmp = mainList.get(0);
				
				params.clear();
				params.put("oidReinsInwardMainData", tmp.getOid());
				result = this.reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
				if(result.getResObject() == null){
					throw new SystemException("無法取得分進資料險種檔");
				}
				ArrayList<ReinsInwardInsData> detailList = (ArrayList<ReinsInwardInsData>)result.getResObject();
				ReinsInwardInsData tmpDetail = detailList.get(0);
				String poins = StringUtil.adjustNumber(tmpDetail.getPoins(), "0", 2 , false);
				proposalNo = "E" + poins + DateUtils.getADSysDateString();
			}else{
				String poins = StringUtil.adjustNumber(reinsInwardInsDataList.get(0).getPoins(), "0", 2 , false);
				proposalNo = "E" + poins + DateUtils.getADSysDateString();
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("proposalNoTmp", proposalNo + "%");
		int count = this.reinsInwardMainDataService.countReinsInwardMainData(params);
		proposalNo = proposalNo + StringUtil.adjustNumber(String.valueOf(++count), "0", 3, true); 
		reinsInwardMainData.setProposalNo(proposalNo);
		//暫時先不檢查
		convertCommaToSpace(reinsInwardMainData);
		reinsInwardMainData.setStatus("0");
		reinsInwardMainData.setCreater(userInfo.getUserId());
		Result result = reinsInwardMainDataService.insertReinsInwardMainData(reinsInwardMainData);
		if(result.getResObject() == null){
			logger.debug("policyNo：" + reinsInwardMainData.getPolicyNo() + "失敗");
			throw new SystemException("新增主檔失敗");
		}
		
		reinsInwardMainData = (ReinsInwardMainData) result.getResObject();
		
		if(reinsInwardInsDataList != null && reinsInwardInsDataList.size() > 0){
			for(ReinsInwardInsData reinsInwardInsData:reinsInwardInsDataList){
				if(reinsInwardInsData == null){
					continue;
				}
				convertCommaToSpace(reinsInwardInsData);
				reinsInwardInsData.setCreater(userInfo.getUserId());
				reinsInwardInsData.setOidReinsInwardMainData(reinsInwardMainData.getOid());
				result = reinsInwardInsDataService.insertReinsInwardInsData(reinsInwardInsData);
				if(result.getResObject() == null){
					logger.debug("poname：" + reinsInwardInsData.getPoname() + "失敗");
					throw new SystemException("新增明細檔失敗：" + reinsInwardInsData.getPoname());
				}
			}
		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	@Override
	public Result updateData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData,
			ArrayList<ReinsInwardInsData> reinsInwardInsDataList) throws SystemException,
			Exception {

		if(reinsInwardMainData == null ){
			throw new SystemException("無法取得險種主檔");
		}
		if(userInfo == null ){
			throw new SystemException("無法取得使用者資訊");
		}
		
		//暫時先不檢查
		convertCommaToSpace(reinsInwardMainData);
		reinsInwardMainData.setModifier(userInfo.getUserId());
		Result result = reinsInwardMainDataService.updateReinsInwardMainData(reinsInwardMainData);
		if(result.getResObject() == null){
			logger.debug("更新policyNo：" + reinsInwardMainData.getPolicyNo() + "失敗");
			throw new SystemException("update-更新主檔失敗0");
		}
		reinsInwardMainData = (ReinsInwardMainData) result.getResObject();
		
		//刪除既有的明細檔
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oidReinsInwardMainData", reinsInwardMainData.getOid());
		result = reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
		if(result.getResObject() != null){
//			logger.debug("update-刪除明細 policyNo：" + reinsInwardMainData.getPolicyNo() + "失敗");
//			throw new SystemException("update-更新主檔失敗1");
			ArrayList<ReinsInwardInsData> delList = (ArrayList<ReinsInwardInsData>)result.getResObject();
			for(ReinsInwardInsData delOldData : delList){
				reinsInwardInsDataService.removeReinsInwardInsData(delOldData.getOid());
				if(result.getResObject() == null){
					logger.debug("update-刪除明細  oid：" + delOldData.getOid() + "失敗");
					throw new SystemException("update-更新主檔失敗2");
				}
			}
		}

		
		if(reinsInwardInsDataList != null && reinsInwardInsDataList.size() > 0){
			for(ReinsInwardInsData reinsInwardInsData:reinsInwardInsDataList){
				if(reinsInwardInsData == null){
					continue;
				}
				convertCommaToSpace(reinsInwardInsData);
				reinsInwardInsData.setCreater(userInfo.getUserId());
				reinsInwardInsData.setOidReinsInwardMainData(reinsInwardMainData.getOid());
				result = reinsInwardInsDataService.insertReinsInwardInsData(reinsInwardInsData);
				if(result.getResObject() == null){
					logger.debug("poname：" + reinsInwardInsData.getPoname() + "失敗");
					throw new SystemException("update-更新明細檔失敗：" + reinsInwardInsData.getPoname());
				}
			}
		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	
	@Override
	public Result deleteData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData) throws SystemException,
			Exception {

		if(reinsInwardMainData == null ){
			throw new SystemException("無法取得險種主檔");
		}
		if(userInfo == null ){
			throw new SystemException("無法取得使用者資訊");
		}
		
		Result result = reinsInwardMainDataService.removeReinsInwardMainData(reinsInwardMainData.getOid());
		if(result.getResObject() == null){
			logger.debug("刪除policyNo：" + reinsInwardMainData.getPolicyNo() + "失敗");
			throw new SystemException("delete-刪除主檔失敗0");
		}
		
		//刪除既有的明細檔
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oidReinsInwardMainData", reinsInwardMainData.getOid());
		result = reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
		if(result.getResObject() != null){
			ArrayList<ReinsInwardInsData> delList = (ArrayList<ReinsInwardInsData>)result.getResObject();
			for(ReinsInwardInsData delOldData : delList){
				reinsInwardInsDataService.removeReinsInwardInsData(delOldData.getOid());
				if(result.getResObject() == null){
					logger.debug("update-刪除明細  oid：" + delOldData.getOid() + "失敗");
					throw new SystemException("update-更新主檔失敗2");
				}
			}

		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	@Override
	public Result submitData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData) throws SystemException,
			Exception {

		if(reinsInwardMainData == null ){
			throw new SystemException("無法取得險種主檔");
		}
		if(userInfo == null ){
			throw new SystemException("無法取得使用者資訊");
		}
		
		reinsInwardMainData.setStatus("1");
		Result result = reinsInwardMainDataService.updateReinsInwardMainData(reinsInwardMainData);
		if(result.getResObject() == null){
			logger.debug("送審policyNo：" + reinsInwardMainData.getPolicyNo() + "失敗");
			throw new SystemException("submit-送審失敗0");
		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	@Override
	public Result auditData(String auditResult, UserInfo userInfo, ReinsInwardMainData reinsInwardMainData) throws SystemException,
			Exception {

		if(reinsInwardMainData == null){
			throw new SystemException("無法取得險種主檔");
		}
		if(userInfo == null){
			throw new SystemException("無法取得使用者資訊");
		}
		if(StringUtil.isSpace(auditResult) ){
			throw new SystemException("無法取得審核結果");
		}
		
		Result result = this.reinsInwardMainDataService.findReinsInwardMainDataByOid(reinsInwardMainData.getOid());
		if (null == result.getResObject()) {
			throw new SystemException("無法取得險種主檔");
		}
		ReinsInwardMainData tmp = (ReinsInwardMainData) result.getResObject();
		if(!tmp.getStatus().equals(reinsInwardMainData.getStatus())){
			throw new SystemException("狀態已被改變，無法審核，請重新操作並確認狀態！");
		}
		
		//險部覆核
		if(userInfo.getRoleList().contains("RI002")){
			//成功，押簽單日
			if("0".equals(auditResult)){
				reinsInwardMainData.setSignDate(DateUtils.getADSysDateString());
				reinsInwardMainData.setStatus("2");
			}
			//駁回
			if("1".equals(auditResult)){
				reinsInwardMainData.setSignDate("");
				reinsInwardMainData.setStatus("0");
			}
			reinsInwardMainData.setInsReviewer(userInfo.getUserId());
			reinsInwardMainData.setInsReviewDate(DateUtils.getADSysDateTimeString());
		}
		
		//再保部覆核
		if(userInfo.getRoleList().contains("RI003")){
			//成功，押簽單日
			if("0".equals(auditResult)){
				reinsInwardMainData.setStatus("3");
			}
			//駁回
			if("1".equals(auditResult)){
				reinsInwardMainData.setSignDate("");
				reinsInwardMainData.setStatus("0");
			}
			reinsInwardMainData.setReinsReviewer(userInfo.getUserId());
			reinsInwardMainData.setReinsReviewDate(DateUtils.getADSysDateTimeString());
		}
		
		//再保部覆核通過要取號
		if(userInfo.getRoleList().contains("RI003") && "0".equals(auditResult)){
			String type = reinsInwardMainData.getType();
			//取保單或批單號
			//要取得出單險種資料，但因出單險種再明細檔，所以必須先找出主檔再到明細檔
			Map<String, Object> params = new HashMap<String, Object>();
			
			if("P".equals(type)){
				params.clear();
				params.put("oidReinsInwardMainData", tmp.getOid());
				result = this.reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
				if(result.getResObject() == null){
					throw new SystemException("無法取得分進資料險種檔");
				}
				
				ArrayList<ReinsInwardInsData> detailList = (ArrayList<ReinsInwardInsData>)result.getResObject();
				ReinsInwardInsData tmpDetail = detailList.get(0);
				//補到2碼
				String poins = StringUtil.adjustNumber(tmpDetail.getPoins(), "0", 2 , false);
				//年度
				String currentYear = new SimpleDateFormat("yy").format(new Date());
				
				/*
				 * 保單-18+單位2碼+年份2碼+I+出單險種代號2碼+序號3碼+00保單2碼
				 */
				if("P".equals(reinsInwardMainData.getType())){
					String tmpNo = reinsInwardPickNoService.getReinsInwardNo(type, poins);
					tmpNo = StringUtil.adjustNumber(tmpNo, "0", 3, true);
					String policyNo = "1800" + currentYear + "I" + poins + tmpNo + "00";
					reinsInwardMainData.setPolicyNo(policyNo);
				}

			}
			
			if("T".equals(type)){
				/*
				 * 批單-18+單位2碼+年份2碼+I+出單險種代號2碼+序號3碼(以上同保單號)+01批單2碼
				 * 180022IF000101
				 */
				//找出目前最大序號加1
				params.clear();
				params.put("policyNo", tmp.getPolicyNo());
				int currentNo = this.reinsInwardMainDataService.queryCurrentEndorseNo(params);
				String endorseNo = tmp.getPolicyNo().substring(0,12) + StringUtil.adjustNumber(String.valueOf(++currentNo), "0", 2, true);
				reinsInwardMainData.setEndorseNo(endorseNo);
			}
		}
		//更新主檔
		result = reinsInwardMainDataService.updateReinsInwardMainData(reinsInwardMainData);
		if(result.getResObject() == null){
			logger.debug("送審policyNo：" + reinsInwardMainData.getPolicyNo() + "失敗");
			throw new SystemException("audit-審核失敗0");
		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	public void convertCommaToSpace(ReinsInwardMainData reinsInwardMainData) throws Exception{
		
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrAmount())){
			reinsInwardMainData.setOriCurrAmount(reinsInwardMainData.getOriCurrAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrCommission())){
			reinsInwardMainData.setOriCurrCommission(reinsInwardMainData.getOriCurrCommission().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrInwardAmount())){
			reinsInwardMainData.setOriCurrInwardAmount(reinsInwardMainData.getOriCurrInwardAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrInwardMaxAmount())){
			reinsInwardMainData.setOriCurrInwardMaxAmount(reinsInwardMainData.getOriCurrInwardMaxAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrInwardPrem())){
			reinsInwardMainData.setOriCurrInwardPrem(reinsInwardMainData.getOriCurrInwardPrem().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrMaxAmount())){
			reinsInwardMainData.setOriCurrMaxAmount(reinsInwardMainData.getOriCurrMaxAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrPrem())){
			reinsInwardMainData.setOriCurrPrem(reinsInwardMainData.getOriCurrPrem().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtAmount())){
			reinsInwardMainData.setNtAmount(reinsInwardMainData.getNtAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtCommission())){
			reinsInwardMainData.setNtCommission(reinsInwardMainData.getNtCommission().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtInwardAmount())){
			reinsInwardMainData.setNtInwardAmount(reinsInwardMainData.getNtInwardAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtInwardMaxAmount())){
			reinsInwardMainData.setNtInwardMaxAmount(reinsInwardMainData.getNtInwardMaxAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtInwardPrem())){
			reinsInwardMainData.setNtInwardPrem(reinsInwardMainData.getNtInwardPrem().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtMaxAmount())){
			reinsInwardMainData.setNtMaxAmount(reinsInwardMainData.getNtMaxAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtPrem())){
			reinsInwardMainData.setNtPrem(reinsInwardMainData.getNtPrem().replaceAll(",", ""));
		}
	}
	
	public void convertCommaToSpace(ReinsInwardInsData reinsInwardInsData) throws Exception{
		
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrAmount())){
			reinsInwardInsData.setOriCurrAmount(reinsInwardInsData.getOriCurrAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrCommission())){
			reinsInwardInsData.setOriCurrCommission(reinsInwardInsData.getOriCurrCommission().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrInwardAmount())){
			reinsInwardInsData.setOriCurrInwardAmount(reinsInwardInsData.getOriCurrInwardAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrInwardMaxAmount())){
			reinsInwardInsData.setOriCurrInwardMaxAmount(reinsInwardInsData.getOriCurrInwardMaxAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrInwardPrem())){
			reinsInwardInsData.setOriCurrInwardPrem(reinsInwardInsData.getOriCurrInwardPrem().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrMaxAmount())){
			reinsInwardInsData.setOriCurrMaxAmount(reinsInwardInsData.getOriCurrMaxAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrPrem())){
			reinsInwardInsData.setOriCurrPrem(reinsInwardInsData.getOriCurrPrem().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtAmount())){
			reinsInwardInsData.setNtAmount(reinsInwardInsData.getNtAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtCommission())){
			reinsInwardInsData.setNtCommission(reinsInwardInsData.getNtCommission().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtInwardAmount())){
			reinsInwardInsData.setNtInwardAmount(reinsInwardInsData.getNtInwardAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtInwardMaxAmount())){
			reinsInwardInsData.setNtInwardMaxAmount(reinsInwardInsData.getNtInwardMaxAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtInwardPrem())){
			reinsInwardInsData.setNtInwardPrem(reinsInwardInsData.getNtInwardPrem().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtMaxAmount())){
			reinsInwardInsData.setNtMaxAmount(reinsInwardInsData.getNtMaxAmount().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtPrem())){
			reinsInwardInsData.setNtPrem(reinsInwardInsData.getNtPrem().replaceAll(",", ""));
		}
	}
	
	public void convertNumberComma(ReinsInwardMainData reinsInwardMainData) throws Exception{
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		DecimalFormat formatterDot = new DecimalFormat("#,###.##");
		
		
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrAmount())){
			reinsInwardMainData.setOriCurrAmount(formatterDot.format(new BigDecimal(reinsInwardMainData.getOriCurrAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrCommission())){
			reinsInwardMainData.setOriCurrCommission(formatterDot.format(new BigDecimal(reinsInwardMainData.getOriCurrCommission())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrInwardAmount())){
			reinsInwardMainData.setOriCurrInwardAmount(formatterDot.format(new BigDecimal(reinsInwardMainData.getOriCurrInwardAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrInwardMaxAmount())){
			reinsInwardMainData.setOriCurrInwardMaxAmount(formatterDot.format(new BigDecimal(reinsInwardMainData.getOriCurrInwardMaxAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrInwardPrem())){
			reinsInwardMainData.setOriCurrInwardPrem(formatterDot.format(new BigDecimal(reinsInwardMainData.getOriCurrInwardPrem())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrMaxAmount())){
			reinsInwardMainData.setOriCurrMaxAmount(formatterDot.format(new BigDecimal(reinsInwardMainData.getOriCurrMaxAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getOriCurrPrem())){
			reinsInwardMainData.setOriCurrPrem(formatterDot.format(new BigDecimal(reinsInwardMainData.getOriCurrPrem())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtAmount())){
			reinsInwardMainData.setNtAmount(formatter.format(new BigDecimal(reinsInwardMainData.getNtAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtCommission())){
			reinsInwardMainData.setNtCommission(formatter.format(new BigDecimal(reinsInwardMainData.getNtCommission())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtInwardAmount())){
			reinsInwardMainData.setNtInwardAmount(formatter.format(new BigDecimal(reinsInwardMainData.getNtInwardAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtInwardMaxAmount())){
			reinsInwardMainData.setNtInwardMaxAmount(formatter.format(new BigDecimal(reinsInwardMainData.getNtInwardMaxAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtInwardPrem())){
			reinsInwardMainData.setNtInwardPrem(formatter.format(new BigDecimal(reinsInwardMainData.getNtInwardPrem())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtMaxAmount())){
			reinsInwardMainData.setNtMaxAmount(formatter.format(new BigDecimal(reinsInwardMainData.getNtMaxAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardMainData.getNtPrem())){
			reinsInwardMainData.setNtPrem(formatter.format(new BigDecimal(reinsInwardMainData.getNtPrem())));
		}
	}
	
	public void convertNumberComma(ReinsInwardInsData reinsInwardInsData) throws Exception{
		DecimalFormat formatter = new DecimalFormat("#,###");
		DecimalFormat formatterDot = new DecimalFormat("#,###.##");
		
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrAmount())){
			reinsInwardInsData.setOriCurrAmount(formatterDot.format(new BigDecimal(reinsInwardInsData.getOriCurrAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrCommission())){
			reinsInwardInsData.setOriCurrCommission(formatterDot.format(new BigDecimal(reinsInwardInsData.getOriCurrCommission())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrInwardAmount())){
			reinsInwardInsData.setOriCurrInwardAmount(formatterDot.format(new BigDecimal(reinsInwardInsData.getOriCurrInwardAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrInwardMaxAmount())){
			reinsInwardInsData.setOriCurrInwardMaxAmount(formatterDot.format(new BigDecimal(reinsInwardInsData.getOriCurrInwardMaxAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrInwardPrem())){
			reinsInwardInsData.setOriCurrInwardPrem(formatterDot.format(new BigDecimal(reinsInwardInsData.getOriCurrInwardPrem())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrMaxAmount())){
			reinsInwardInsData.setOriCurrMaxAmount(formatterDot.format(new BigDecimal(reinsInwardInsData.getOriCurrMaxAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getOriCurrPrem())){
			reinsInwardInsData.setOriCurrPrem(formatterDot.format(new BigDecimal(reinsInwardInsData.getOriCurrPrem())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtAmount())){
			reinsInwardInsData.setNtAmount(formatter.format(new BigDecimal(reinsInwardInsData.getNtAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtCommission())){
			reinsInwardInsData.setNtCommission(formatter.format(new BigDecimal(reinsInwardInsData.getNtCommission())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtInwardAmount())){
			reinsInwardInsData.setNtInwardAmount(formatter.format(new BigDecimal(reinsInwardInsData.getNtInwardAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtInwardMaxAmount())){
			reinsInwardInsData.setNtInwardMaxAmount(formatter.format(new BigDecimal(reinsInwardInsData.getNtInwardMaxAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtInwardPrem())){
			reinsInwardInsData.setNtInwardPrem(formatter.format(new BigDecimal(reinsInwardInsData.getNtInwardPrem())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtMaxAmount())){
			reinsInwardInsData.setNtMaxAmount(formatter.format(new BigDecimal(reinsInwardInsData.getNtMaxAmount())));
		}
		if(!StringUtil.isSpace(reinsInwardInsData.getNtPrem())){
			reinsInwardInsData.setNtPrem(formatter.format(new BigDecimal(reinsInwardInsData.getNtPrem())));
		}
	}
	
	@Override
	public Result queryPolicyDataForEndorse(BigDecimal oid)
			throws SystemException, Exception {
			
		Result result = new Result();
		Map dataMap = new HashMap();
		try{
			if(oid == null || oid.longValue() == 0){
				throw new SystemException("無法取得保單資料(1)！");
			}
			
			result = this.reinsInwardMainDataService.findReinsInwardMainDataByOid(oid);
			if (null == result.getResObject()) {
				throw new SystemException("無法取得保單資料(2)！");
			}
			
			ReinsInwardMainData reinsInwardMainData = (ReinsInwardMainData) result.getResObject();
			reinsInwardMainData.setOid(null);
			reinsInwardMainData.setStatus("");
			reinsInwardMainData.setType("T");
			reinsInwardMainData.setProposalNo("");
			reinsInwardMainData.setInwardNo("");
			reinsInwardMainData.setEndorseNo("");
			reinsInwardMainData.setOriCurrAmount("");
			reinsInwardMainData.setOriCurrPrem("");
			reinsInwardMainData.setOriCurrCommission("");
			reinsInwardMainData.setOriCurrInwardAmount("");
			reinsInwardMainData.setOriCurrInwardMaxAmount("");
			reinsInwardMainData.setOriCurrInwardPrem("");
			reinsInwardMainData.setOriCurrMaxAmount("");
			
			reinsInwardMainData.setNtAmount("");
			reinsInwardMainData.setNtPrem("");
			reinsInwardMainData.setNtCommission("");
			reinsInwardMainData.setNtInwardAmount("");
			reinsInwardMainData.setNtInwardMaxAmount("");
			reinsInwardMainData.setNtInwardPrem("");
			reinsInwardMainData.setNtMaxAmount("");
			
			reinsInwardMainData.setPayNo("");
			reinsInwardMainData.setPayDate("");
			reinsInwardMainData.setCommissionDate("");
			reinsInwardMainData.setSignDate("");
			reinsInwardMainData.setFirstPremDueDate("");
			
			reinsInwardMainData.setCreater("");
			reinsInwardMainData.setCreateDate("");
			reinsInwardMainData.setModifier("");
			reinsInwardMainData.setModifyDate("");
			reinsInwardMainData.setInsReviewer("");
			reinsInwardMainData.setInsReviewDate("");
			reinsInwardMainData.setReinsReviewer("");
			reinsInwardMainData.setReinsReviewDate("");
			reinsInwardMainData.setComments("");
			
			reinsInwardMainData.setEndorseEndDate(reinsInwardMainData.getEndDate());
			
			dataMap.put("main", reinsInwardMainData);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("oidReinsInwardMainData", oid);
			result = this.reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
			if(result.getResObject() != null){
				ArrayList<ReinsInwardInsData> list = (ArrayList<ReinsInwardInsData>)result.getResObject();
				for(ReinsInwardInsData data:list){
					
					data.setOriCurrAmount("");
					data.setOriCurrCommission("");
					data.setOriCurrInwardAmount("");
					data.setOriCurrInwardMaxAmount("");
					data.setOriCurrInwardPrem("");
					data.setOriCurrMaxAmount("");
					data.setOriCurrPrem("");
					
					data.setNtAmount("");
					data.setNtCommission("");
					data.setNtInwardAmount("");
					data.setNtInwardMaxAmount("");
					data.setNtInwardPrem("");
					data.setNtMaxAmount("");
					data.setNtPrem("");
					
					data.setCreater("");
					data.setCreateDate("");
					data.setModifier("");
					data.setModifyDate("");
					
				}
				dataMap.put("ins", list);
			}
			
			result = new Result();
			result.setResObject(dataMap);
			
			
		}catch (SystemException se) {
			se.printStackTrace();
			throw se;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	


	public static void main(String args[]){
		String number = "1000500000.574";
//		double amount = Double.parseDouble(number);
		BigDecimal b = new BigDecimal(number);
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		System.out.println(formatter.format(b));
		
        String currentYear = new SimpleDateFormat("yy").format(new Date());
        System.out.println(currentYear);
        System.out.println("180022IF000101".substring(0,12));
	}
	

	public ReinsInwardInsDataService getReinsInwardInsDataService() {
		return reinsInwardInsDataService;
	}

	public void setReinsInwardInsDataService(
			ReinsInwardInsDataService reinsInwardInsDataService) {
		this.reinsInwardInsDataService = reinsInwardInsDataService;
	}

	public ReinsInwardMainDataService getReinsInwardMainDataService() {
		return reinsInwardMainDataService;
	}

	public void setReinsInwardMainDataService(
			ReinsInwardMainDataService reinsInwardMainDataService) {
		this.reinsInwardMainDataService = reinsInwardMainDataService;
	}

	public ReinsInwardNoServiceImpl getReinsInwardPickNoService() {
		return reinsInwardPickNoService;
	}

	public void setReinsInwardPickNoService(
			ReinsInwardNoServiceImpl reinsInwardPickNoService) {
		this.reinsInwardPickNoService = reinsInwardPickNoService;
	}

}
