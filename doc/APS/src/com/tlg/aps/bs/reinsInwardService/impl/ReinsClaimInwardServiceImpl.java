package com.tlg.aps.bs.reinsInwardService.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.analysis.SynonymFilter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.reinsInwardService.ReinsClaimInwardService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardClaimData;
import com.tlg.prpins.entity.ReinsInwardClaimInsData;
import com.tlg.prpins.entity.ReinsInwardMainData;
import com.tlg.prpins.service.ReinsInwardClaimDataService;
import com.tlg.prpins.service.ReinsInwardClaimInsDataService;
import com.tlg.prpins.service.ReinsInwardMainDataService;
import com.tlg.util.Constants;
import com.tlg.util.DateUtils;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;

@Transactional(value="prpinsTransactionManager", propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
public class ReinsClaimInwardServiceImpl implements ReinsClaimInwardService {
	
	private static final Logger logger = Logger.getLogger(ReinsClaimInwardServiceImpl.class);
	private ReinsInwardClaimDataService reinsInwardClaimDataService;
	private ReinsInwardClaimInsDataService reinsInwardClaimInsDataService;
	private ReinsInwardMainDataService reinsInwardMainDataService;
	private ReinsInwardNoServiceImpl reinsInwardPickNoService;
	
	@Override
	public synchronized Result createData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData, ReinsInwardClaimData reinsInwardClaimData, ArrayList<ReinsInwardClaimInsData> reinsInwardClaimInsDataList) throws SystemException,
			Exception {

		if(reinsInwardMainData == null ){
			throw new SystemException("無法取得分進保單資料主檔(1)");
		}
		if(reinsInwardClaimData == null ){
			throw new SystemException("無法取得分進賠款主檔資料");
		}
		if(reinsInwardClaimInsDataList == null || reinsInwardClaimInsDataList.size() == 0){
			throw new SystemException("無法取得分進賠款險種檔資料");
		}
		if(userInfo == null ){
			throw new SystemException("無法取得使用者資訊");
		}
		
		if(reinsInwardMainData.getOid() == null){
			throw new SystemException("無法取得分進保單資料主檔(2)");
		}
		//重新取得保單資料
		Result result = reinsInwardMainDataService.findReinsInwardMainDataByOid(reinsInwardMainData.getOid());
		reinsInwardMainData = (ReinsInwardMainData)result.getResObject();
		convertCommaToSpace(reinsInwardClaimData);
		reinsInwardClaimData.setApplicantName(reinsInwardMainData.getApplicantName());
		reinsInwardClaimData.setApplicantId(reinsInwardMainData.getApplicantId());
		reinsInwardClaimData.setInsuredName(reinsInwardMainData.getInsuredName());
		reinsInwardClaimData.setInsuredId(reinsInwardMainData.getInsuredId());
		reinsInwardClaimData.setCmnpNo(reinsInwardMainData.getCmnpNo());
		reinsInwardClaimData.setStartDate(converDate(reinsInwardMainData.getStartDate()));
		reinsInwardClaimData.setEndDate(converDate(reinsInwardMainData.getEndDate()));
		reinsInwardClaimData.setPolicyNo(reinsInwardMainData.getPolicyNo());
		reinsInwardClaimData.setEndorseNo(reinsInwardMainData.getEndorseNo());
		reinsInwardClaimData.setInwardNo(reinsInwardMainData.getInwardNo());
		reinsInwardClaimData.setType("C");
		reinsInwardClaimData.setStatus("0");
		reinsInwardClaimData.setCreater(userInfo.getUserId());
		String proposalNo = "";
		//依類型取號取要保號 start
		String poins = StringUtil.adjustNumber(reinsInwardClaimInsDataList.get(0).getPoins(), "0", 2 , false);
		proposalNo = "I" + poins + DateUtils.getADSysDateString();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("proposalNoTmp", proposalNo + "%");
		int count = this.reinsInwardClaimDataService.countReinsInwardClaimData(params);
		proposalNo = proposalNo + StringUtil.adjustNumber(String.valueOf(++count), "0", 3, true);
		reinsInwardClaimData.setTmpclNo(proposalNo);
		//依類型取號取要保號 end
		result = reinsInwardClaimDataService.insertReinsInwardClaimData(reinsInwardClaimData);
		if(result.getResObject() == null){
			logger.debug("理賠分進 policyNo：" + reinsInwardMainData.getPolicyNo() + "失敗");
			throw new SystemException("新增主檔失敗");
		}
		reinsInwardClaimData = (ReinsInwardClaimData) result.getResObject();
		if(reinsInwardClaimInsDataList != null && reinsInwardClaimInsDataList.size() > 0){
			for(ReinsInwardClaimInsData reinsInwardClaimInsData:reinsInwardClaimInsDataList){
				if(reinsInwardClaimInsData == null){
					continue;
				}
				convertCommaToSpace(reinsInwardClaimInsData);
				reinsInwardClaimInsData.setCreater(userInfo.getUserId());
				reinsInwardClaimInsData.setOidReinsInwardClaimData(reinsInwardClaimData.getOid());
				result = reinsInwardClaimInsDataService.insertReinsInwardClaimInsData(reinsInwardClaimInsData);
				if(result.getResObject() == null){
					logger.debug("poname：" + reinsInwardClaimInsData.getPoname() + "失敗");
					throw new SystemException("理賠分進新增明細檔失敗：" + reinsInwardClaimInsData.getPoname());
				}
			}
		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	@Override
	public Result updateData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData, ReinsInwardClaimData reinsInwardClaimData, ArrayList<ReinsInwardClaimInsData> reinsInwardClaimInsDataList) throws SystemException,
			Exception {

		if(reinsInwardMainData == null ){
			throw new SystemException("無法取得分進保單資料主檔(1)");
		}
		if(reinsInwardClaimData == null ){
			throw new SystemException("無法取得分進賠款主檔資料");
		}
		if(reinsInwardClaimInsDataList == null || reinsInwardClaimInsDataList.size() == 0){
			throw new SystemException("無法取得分進賠款險種檔資料");
		}
		if(userInfo == null ){
			throw new SystemException("無法取得使用者資訊");
		}
		
		if(reinsInwardMainData.getOid() == null){
			throw new SystemException("無法取得分進保單資料主檔(2)");
		}
		
		//重新取得保單資料
		Result result = reinsInwardMainDataService.findReinsInwardMainDataByOid(reinsInwardMainData.getOid());
		reinsInwardMainData = (ReinsInwardMainData)result.getResObject();
		convertCommaToSpace(reinsInwardClaimData);
		reinsInwardClaimData.setApplicantName(reinsInwardMainData.getApplicantName());
		reinsInwardClaimData.setApplicantId(reinsInwardMainData.getApplicantId());
		reinsInwardClaimData.setInsuredName(reinsInwardMainData.getInsuredName());
		reinsInwardClaimData.setInsuredId(reinsInwardMainData.getInsuredId());
		reinsInwardClaimData.setCmnpNo(reinsInwardMainData.getCmnpNo());
		reinsInwardClaimData.setStartDate(converDate(reinsInwardMainData.getStartDate()));
		reinsInwardClaimData.setEndDate(converDate(reinsInwardMainData.getEndDate()));
		reinsInwardClaimData.setPolicyNo(reinsInwardMainData.getPolicyNo());
		reinsInwardClaimData.setEndorseNo(reinsInwardMainData.getEndorseNo());
		reinsInwardClaimData.setInwardNo(reinsInwardMainData.getInwardNo());
		reinsInwardClaimData.setModifier(userInfo.getUserId());

		result = reinsInwardClaimDataService.updateReinsInwardClaimData(reinsInwardClaimData);
		if(result.getResObject() == null){
			logger.debug("更新policyNo：" + reinsInwardMainData.getPolicyNo() + "失敗");
			throw new SystemException("update-更新主檔失敗0");
		}
		reinsInwardClaimData = (ReinsInwardClaimData) result.getResObject();

		//刪除既有的明細檔
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oidReinsInwardClaimData", reinsInwardClaimData.getOid());
		result = reinsInwardClaimInsDataService.findReinsInwardClaimInsDataByParams(params);
		if(result.getResObject() != null){
//			logger.debug("update-刪除明細 policyNo：" + reinsInwardMainData.getPolicyNo() + "失敗");
//			throw new SystemException("update-更新主檔失敗1");
			ArrayList<ReinsInwardClaimInsData> delList = (ArrayList<ReinsInwardClaimInsData>)result.getResObject();
			for(ReinsInwardClaimInsData delOldData : delList){
				reinsInwardClaimInsDataService.removeReinsInwardClaimInsData(delOldData.getOid());
				if(result.getResObject() == null){
					logger.debug("update-刪除明細  oid：" + delOldData.getOid() + "失敗");
					throw new SystemException("update-更新主檔失敗2");
				}
			}
		}

		
		if(reinsInwardClaimInsDataList != null && reinsInwardClaimInsDataList.size() > 0){
			for(ReinsInwardClaimInsData reinsInwardClaimInsData:reinsInwardClaimInsDataList){
				if(reinsInwardClaimInsData == null){
					continue;
				}
				convertCommaToSpace(reinsInwardClaimInsData);
				reinsInwardClaimInsData.setCreater(userInfo.getUserId());
				reinsInwardClaimInsData.setOidReinsInwardClaimData(reinsInwardClaimData.getOid());
				result = reinsInwardClaimInsDataService.insertReinsInwardClaimInsData(reinsInwardClaimInsData);
				if(result.getResObject() == null){
					logger.debug("poname：" + reinsInwardClaimInsData.getPoname() + "失敗");
					throw new SystemException("update-更新明細檔失敗：" + reinsInwardClaimInsData.getPoname());
				}
			}
		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	
	@Override
	public Result deleteData(UserInfo userInfo, ReinsInwardClaimData reinsInwardClaimData) throws SystemException,
			Exception {

		if(reinsInwardClaimData == null ){
			throw new SystemException("無法取得理賠分進主檔");
		}
		if(userInfo == null ){
			throw new SystemException("無法取得使用者資訊");
		}
		
		Result result = reinsInwardClaimDataService.removeReinsInwardClaimData(reinsInwardClaimData.getOid());
		if(result.getResObject() == null){
			logger.debug("刪除理賠分進policyNo：" + reinsInwardClaimData.getPolicyNo() + "失敗");
			throw new SystemException("delete-刪除主檔失敗0");
		}
		
		//刪除既有的明細檔
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oidReinsInwardClaimData", reinsInwardClaimData.getOid());
		result = reinsInwardClaimInsDataService.findReinsInwardClaimInsDataByParams(params);
		if(result.getResObject() != null){
			ArrayList<ReinsInwardClaimInsData> delList = (ArrayList<ReinsInwardClaimInsData>)result.getResObject();
			for(ReinsInwardClaimInsData delOldData : delList){
				reinsInwardClaimInsDataService.removeReinsInwardClaimInsData(delOldData.getOid());
				if(result.getResObject() == null){
					logger.debug("update-刪除理賠分進明細  oid：" + delOldData.getOid() + "失敗");
					throw new SystemException("update-刪除理賠分進明細檔失敗2");
				}
			}
		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	@Override
	public Result submitData(UserInfo userInfo, ReinsInwardClaimData reinsInwardClaimData) throws SystemException,
			Exception {

		if(reinsInwardClaimData == null ){
			throw new SystemException("無法取得理賠分進主檔");
		}
		if(userInfo == null ){
			throw new SystemException("無法取得使用者資訊");
		}
		
		reinsInwardClaimData.setStatus("1");
		Result result = reinsInwardClaimDataService.updateReinsInwardClaimData(reinsInwardClaimData);
		if(result.getResObject() == null){
			logger.debug("送審policyNo：" + reinsInwardClaimData.getPolicyNo() + "失敗");
			throw new SystemException("submit-送審失敗0");
		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	@Override
	public synchronized Result auditData(String auditResult, UserInfo userInfo, ReinsInwardClaimData reinsInwardClaimData) throws SystemException,
			Exception {

		if(reinsInwardClaimData == null){
			throw new SystemException("無法取得理賠分進主檔");
		}
		if(userInfo == null){
			throw new SystemException("無法取得使用者資訊");
		}
		if(StringUtil.isSpace(auditResult) ){
			throw new SystemException("無法取得審核結果");
		}
		
		Result result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByOid(reinsInwardClaimData.getOid());
		if (null == result.getResObject()) {
			throw new SystemException("無法取得理賠分進主檔");
		}
		ReinsInwardClaimData tmp = (ReinsInwardClaimData) result.getResObject();
		if(!tmp.getStatus().equals(reinsInwardClaimData.getStatus())){
			throw new SystemException("狀態已被改變，無法審核，請重新操作並確認狀態！");
		}
		
		//險部覆核
		if(userInfo.getRoleList().contains("CR002")){
			//成功，押簽單日
			if("0".equals(auditResult)){
				reinsInwardClaimData.setStatus("2");
			}
			//駁回
			if("1".equals(auditResult)){
				reinsInwardClaimData.setStatus("0");
			}
			reinsInwardClaimData.setReviewer(userInfo.getUserId());
			reinsInwardClaimData.setReviewDate(DateUtils.getADSysDateTimeString().substring(0, 8));
		}
		
		//再保部覆核
		if(userInfo.getRoleList().contains("CR003")){
			//成功，押簽單日
			if("0".equals(auditResult)){
				reinsInwardClaimData.setStatus("3");
			}
			//駁回
			if("1".equals(auditResult)){
				reinsInwardClaimData.setStatus("0");
			}
			reinsInwardClaimData.setReinsReviewer(userInfo.getUserId());
			reinsInwardClaimData.setReinsReviewDate(DateUtils.getADSysDateTimeString().substring(0, 8));
		}
		
		//再保部覆核通過要取號
		if(userInfo.getRoleList().contains("CR003") && "0".equals(auditResult)){
			String type = reinsInwardClaimData.getType();
			//取保單或批單號
			//要取得出單險種資料，但因出單險種再明細檔，所以必須先找出主檔再到明細檔
			Map<String, Object> params = new HashMap<String, Object>();
			
			/*
			 * 批單-18+單位2碼+年份2碼+I+出單險種代號2碼+序號3碼(以上同保單號)+01批單2碼
			 *    18+單位2碼+年份2碼+I+出單險種代號2碼+序號5碼
			 * 180022IF000101
			 */
			String claimNo = "";
			int count = 1;
			params.put("policyNo", reinsInwardClaimData.getPolicyNo());
			params.put("status", "3");
			result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByParams(params);
			if(result.getResObject() != null){
				ArrayList<ReinsInwardClaimData> claimDataList = (ArrayList<ReinsInwardClaimData>)result.getResObject();
				count = claimDataList.size() + 1;
				//取得賠案號
				ReinsInwardClaimData data = claimDataList.get(0);
				claimNo = data.getClaimNo();
			}
			
			//因為預設是1，表示同一個保單號沒半筆賠案，因此要取號
			if(count == 1){
				params.clear();
				params.put("oidReinsInwardClaimData", tmp.getOid());
				result = this.reinsInwardClaimInsDataService.findReinsInwardClaimInsDataByParams(params);
				if(result.getResObject() == null){
					throw new SystemException("無法取得分進資料險種檔");
				}
				
				ArrayList<ReinsInwardClaimInsData> detailList = (ArrayList<ReinsInwardClaimInsData>)result.getResObject();
				ReinsInwardClaimInsData tmpDetail = detailList.get(0);
				//補到2碼
				String poins = StringUtil.adjustNumber(tmpDetail.getPoins(), "0", 2 , false);
				//年度
				String currentYear = new SimpleDateFormat("yy").format(new Date());
				
				/*
				 * 保單-18+單位2碼+年份2碼+I+出單險種代號2碼+序號3碼+00保單2碼
				 */
				String tmpNo = reinsInwardPickNoService.getReinsInwardNo(type, poins);
				tmpNo = StringUtil.adjustNumber(tmpNo, "0", 5, true);
				claimNo = "1800" + currentYear + "I" + poins + tmpNo;
			}
			reinsInwardClaimData.setClaimNo(claimNo);
			reinsInwardClaimData.setCtime(String.valueOf(count));
		}
		//更新主檔
		result = reinsInwardClaimDataService.updateReinsInwardClaimData(reinsInwardClaimData);
		if(result.getResObject() == null){
			logger.debug("理賠分進送審policyNo：" + reinsInwardClaimData.getPolicyNo() + "失敗");
			throw new SystemException("audit-審核失敗0");
		}
		
		result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		
		return result;
	}
	
	public void convertCommaToSpace(ReinsInwardClaimData reinsInwardClaimData) throws Exception{
		

		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareFeeNtd())){
			reinsInwardClaimData.setPrepareFeeNtd(reinsInwardClaimData.getPrepareFeeNtd().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareFeeO())){
			reinsInwardClaimData.setPrepareFeeO(reinsInwardClaimData.getPrepareFeeO().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareLossNtd())){
			reinsInwardClaimData.setPrepareLossNtd(reinsInwardClaimData.getPrepareLossNtd().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareLossO())){
			reinsInwardClaimData.setPrepareLossO(reinsInwardClaimData.getPrepareLossO().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareSumNtd())){
			reinsInwardClaimData.setPrepareSumNtd(reinsInwardClaimData.getPrepareSumNtd().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareSumO())){
			reinsInwardClaimData.setPrepareSumO(reinsInwardClaimData.getPrepareSumO().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmFeeNtd())){
			reinsInwardClaimData.setConfirmFeeNtd(reinsInwardClaimData.getConfirmFeeNtd().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmFeeO())){
			reinsInwardClaimData.setConfirmFeeO(reinsInwardClaimData.getConfirmFeeO().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmLossNtd())){
			reinsInwardClaimData.setConfirmLossNtd(reinsInwardClaimData.getConfirmLossNtd().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmLossO())){
			reinsInwardClaimData.setConfirmLossO(reinsInwardClaimData.getConfirmLossO().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmSumNtd())){
			reinsInwardClaimData.setConfirmSumNtd(reinsInwardClaimData.getConfirmSumNtd().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmSumO())){
			reinsInwardClaimData.setConfirmSumO(reinsInwardClaimData.getConfirmSumO().replaceAll(",", ""));
		}
	}
	
	public void convertCommaToSpace(ReinsInwardClaimInsData reinsInwardClaimInsData) throws Exception{
		
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getConfirmKindfeeN())){
			reinsInwardClaimInsData.setConfirmKindfeeN(reinsInwardClaimInsData.getConfirmKindfeeN().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getConfirmKindfeeO())){
			reinsInwardClaimInsData.setConfirmKindfeeO(reinsInwardClaimInsData.getConfirmKindfeeO().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getConfirmKindlossN())){
			reinsInwardClaimInsData.setConfirmKindlossN(reinsInwardClaimInsData.getConfirmKindlossN().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getConfirmKindlossO())){
			reinsInwardClaimInsData.setConfirmKindlossO(reinsInwardClaimInsData.getConfirmKindlossO().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getPrepareKindfeeN())){
			reinsInwardClaimInsData.setPrepareKindfeeN(reinsInwardClaimInsData.getPrepareKindfeeN().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getPrepareKindfeeO())){
			reinsInwardClaimInsData.setPrepareKindfeeO(reinsInwardClaimInsData.getPrepareKindfeeO().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getPrepareKindlossN())){
			reinsInwardClaimInsData.setPrepareKindlossN(reinsInwardClaimInsData.getPrepareKindlossN().replaceAll(",", ""));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getPrepareKindlossO())){
			reinsInwardClaimInsData.setPrepareKindlossO(reinsInwardClaimInsData.getPrepareKindlossO().replaceAll(",", ""));
		}
	}
	
	private String converDate(String yyyMMdd) throws Exception{
		if(StringUtil.isSpace(yyyMMdd)){
			return yyyMMdd;
		}
		if(yyyMMdd.length() != 7){
			return yyyMMdd;
		}
		return (Integer.parseInt(yyyMMdd.substring(0, 3)) + 1911 ) + yyyMMdd.substring(3);
	}
	
	public void convertNumberComma(ReinsInwardClaimData reinsInwardClaimData) throws Exception{
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		DecimalFormat formatterDot = new DecimalFormat("#,###.##");
		
		
		
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareFeeNtd())){
			reinsInwardClaimData.setPrepareFeeNtd(formatterDot.format(new BigDecimal(reinsInwardClaimData.getPrepareFeeNtd())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareFeeO())){
			reinsInwardClaimData.setPrepareFeeO(formatterDot.format(new BigDecimal(reinsInwardClaimData.getPrepareFeeO())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareLossNtd())){
			reinsInwardClaimData.setPrepareLossNtd(formatterDot.format(new BigDecimal(reinsInwardClaimData.getPrepareLossNtd())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareLossO())){
			reinsInwardClaimData.setPrepareLossO(formatterDot.format(new BigDecimal(reinsInwardClaimData.getPrepareLossO())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareSumNtd())){
			reinsInwardClaimData.setPrepareSumNtd(formatterDot.format(new BigDecimal(reinsInwardClaimData.getPrepareSumNtd())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getPrepareSumO())){
			reinsInwardClaimData.setPrepareSumO(formatterDot.format(new BigDecimal(reinsInwardClaimData.getPrepareSumO())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmFeeNtd())){
			reinsInwardClaimData.setConfirmFeeNtd(formatterDot.format(new BigDecimal(reinsInwardClaimData.getConfirmFeeNtd())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmFeeO())){
			reinsInwardClaimData.setConfirmFeeO(formatterDot.format(new BigDecimal(reinsInwardClaimData.getConfirmFeeO())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmLossNtd())){
			reinsInwardClaimData.setConfirmLossNtd(formatterDot.format(new BigDecimal(reinsInwardClaimData.getConfirmLossNtd())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmLossO())){
			reinsInwardClaimData.setConfirmLossO(formatterDot.format(new BigDecimal(reinsInwardClaimData.getConfirmLossO())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmSumNtd())){
			reinsInwardClaimData.setConfirmSumNtd(formatterDot.format(new BigDecimal(reinsInwardClaimData.getConfirmSumNtd())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimData.getConfirmSumO())){
			reinsInwardClaimData.setConfirmSumO(formatterDot.format(new BigDecimal(reinsInwardClaimData.getConfirmSumO())));
		}
		
	}
	
	public void convertNumberComma(ReinsInwardClaimInsData reinsInwardClaimInsData) throws Exception{
		DecimalFormat formatter = new DecimalFormat("#,###");
		DecimalFormat formatterDot = new DecimalFormat("#,###.##");
		
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getConfirmKindfeeN())){
			reinsInwardClaimInsData.setConfirmKindfeeN(formatterDot.format(new BigDecimal(reinsInwardClaimInsData.getConfirmKindfeeN())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getConfirmKindfeeO())){
			reinsInwardClaimInsData.setConfirmKindfeeO(formatterDot.format(new BigDecimal(reinsInwardClaimInsData.getConfirmKindfeeO())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getConfirmKindlossN())){
			reinsInwardClaimInsData.setConfirmKindlossN(formatterDot.format(new BigDecimal(reinsInwardClaimInsData.getConfirmKindlossN())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getConfirmKindlossO())){
			reinsInwardClaimInsData.setConfirmKindlossO(formatterDot.format(new BigDecimal(reinsInwardClaimInsData.getConfirmKindlossO())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getPrepareKindfeeN())){
			reinsInwardClaimInsData.setPrepareKindfeeN(formatterDot.format(new BigDecimal(reinsInwardClaimInsData.getPrepareKindfeeN())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getPrepareKindfeeO())){
			reinsInwardClaimInsData.setPrepareKindfeeO(formatterDot.format(new BigDecimal(reinsInwardClaimInsData.getPrepareKindfeeO())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getPrepareKindlossN())){
			reinsInwardClaimInsData.setPrepareKindlossN(formatterDot.format(new BigDecimal(reinsInwardClaimInsData.getPrepareKindlossN())));
		}
		if(!StringUtil.isSpace(reinsInwardClaimInsData.getPrepareKindlossO())){
			reinsInwardClaimInsData.setPrepareKindlossO(formatterDot.format(new BigDecimal(reinsInwardClaimInsData.getPrepareKindlossO())));
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
			
			result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByOid(oid);
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
//			result = this.reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
//			if(result.getResObject() != null){
//				ArrayList<ReinsInwardInsData> list = (ArrayList<ReinsInwardInsData>)result.getResObject();
//				for(ReinsInwardInsData data:list){
//					
//					data.setOriCurrAmount("");
//					data.setOriCurrCommission("");
//					data.setOriCurrInwardAmount("");
//					data.setOriCurrInwardMaxAmount("");
//					data.setOriCurrInwardPrem("");
//					data.setOriCurrMaxAmount("");
//					data.setOriCurrPrem("");
//					
//					data.setNtAmount("");
//					data.setNtCommission("");
//					data.setNtInwardAmount("");
//					data.setNtInwardMaxAmount("");
//					data.setNtInwardPrem("");
//					data.setNtMaxAmount("");
//					data.setNtPrem("");
//					
//					data.setCreater("");
//					data.setCreateDate("");
//					data.setModifier("");
//					data.setModifyDate("");
//					
//				}
//				dataMap.put("ins", list);
//			}
			
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
        
        
        String yyyMMdd = "1120123";
        
        System.out.println(yyyMMdd.substring(0, 3));
        System.out.println(yyyMMdd.substring(3));
	}

	public ReinsInwardClaimDataService getReinsInwardClaimDataService() {
		return reinsInwardClaimDataService;
	}

	public void setReinsInwardClaimDataService(
			ReinsInwardClaimDataService reinsInwardClaimDataService) {
		this.reinsInwardClaimDataService = reinsInwardClaimDataService;
	}

	public ReinsInwardNoServiceImpl getReinsInwardPickNoService() {
		return reinsInwardPickNoService;
	}

	public void setReinsInwardPickNoService(
			ReinsInwardNoServiceImpl reinsInwardPickNoService) {
		this.reinsInwardPickNoService = reinsInwardPickNoService;
	}

	public ReinsInwardClaimInsDataService getReinsInwardClaimInsDataService() {
		return reinsInwardClaimInsDataService;
	}

	public void setReinsInwardClaimInsDataService(
			ReinsInwardClaimInsDataService reinsInwardClaimInsDataService) {
		this.reinsInwardClaimInsDataService = reinsInwardClaimInsDataService;
	}

	public ReinsInwardMainDataService getReinsInwardMainDataService() {
		return reinsInwardMainDataService;
	}

	public void setReinsInwardMainDataService(
			ReinsInwardMainDataService reinsInwardMainDataService) {
		this.reinsInwardMainDataService = reinsInwardMainDataService;
	}

}
