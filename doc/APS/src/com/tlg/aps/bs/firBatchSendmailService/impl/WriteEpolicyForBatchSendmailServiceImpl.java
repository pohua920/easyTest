package com.tlg.aps.bs.firBatchSendmailService.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firBatchSendmailService.WriteEpolicyForBatchSendmailService;
import com.tlg.aps.bs.firBatchSendmailService.WriteForBatchSendmailService;
import com.tlg.aps.vo.WriteForBatchSendmailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchSendmail;
import com.tlg.prpins.entity.FirBatchSendmailDetail;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.util.Message;
import com.tlg.util.Result;

/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險)*/
@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class WriteEpolicyForBatchSendmailServiceImpl implements WriteEpolicyForBatchSendmailService {
	
	private static final Logger logger = Logger.getLogger(WriteEpolicyForBatchSendmailServiceImpl.class);
	private PrpcmainService prpcmainService;
	private WriteForBatchSendmailService writeForBatchSendmailService;

	@SuppressWarnings("unchecked")
	public Result writeEpolicyForBatchSendmail() throws SystemException, Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String,String> bnoMap = new HashMap<>();
		String bno = "";
		Result result = null;
		Calendar sysdate = Calendar.getInstance();
		sysdate.setTime(new Date());
		
		try {
			Map<String, Object> params = new HashMap<>();
			String[] riskcoedList = {"A01","B01","TA"};
			params.put("riskcoedList", riskcoedList);
			result = prpcmainService.findForBatchSendmail(params);
			
			if(result.getResObject()!=null) {
				List<WriteForBatchSendmailVo> batchSendmailList =  (List<WriteForBatchSendmailVo>) result.getResObject();
				//依查出險種資料分別將筆數、簽單日期寫進主檔中
				for(WriteForBatchSendmailVo sendmailVo :batchSendmailList ) {
					//因時間只給到秒，多個險種迴圈時會取到相同的bno，改為每次取bno都多加一秒
					sysdate.add(Calendar.SECOND, 1);
					bno = sdf.format(sysdate.getTime());
					bnoMap.put(sendmailVo.getRiskcode(), bno);
					FirBatchSendmail firBatchSendmail = new FirBatchSendmail();
					firBatchSendmail.setBno(bno);
					firBatchSendmail.setDatacount(sendmailVo.getCount());
					firBatchSendmail.setUnderwriteenddate(sendmailVo.getUnderwriteenddate());
					firBatchSendmail.setRiskcode(sendmailVo.getRiskcode());
					firBatchSendmail.setStatus("4");
					writeForBatchSendmailService.insertFirBatchSendmail(firBatchSendmail); 
				}
				
				//主檔資料新增完後查詢明細資料新增明細檔
				result = prpcmainService.findForBatchSendmailDtl(params);
				if(result.getResObject()!=null) {
					List<WriteForBatchSendmailVo> batchSendmailDtlList =  (List<WriteForBatchSendmailVo>) result.getResObject();
					for(WriteForBatchSendmailVo sendmailDtlVo :batchSendmailDtlList ) {
						FirBatchSendmailDetail firBatchSendmailDetail = new FirBatchSendmailDetail();
						firBatchSendmailDetail.setPolicyno(sendmailDtlVo.getPolicyno());
						firBatchSendmailDetail.setRiskcode(sendmailDtlVo.getRiskcode());
						firBatchSendmailDetail.setUnderwriteenddate(sendmailDtlVo.getUnderwriteenddate());
						if(bnoMap.containsKey(sendmailDtlVo.getRiskcode())) {
							firBatchSendmailDetail.setBno(bnoMap.get(sendmailDtlVo.getRiskcode()));
						}
						writeForBatchSendmailService.insertFirBatchSendmailDetail(firBatchSendmailDetail); 
					}
				}
			}
		}catch(Exception e) {
			logger.error("寫入比對電子保單發送結果資料排程作業失敗",e);
			return this.getReturnResult("寫入比對電子保單發送結果資料排程作業失敗");
		}
		
		return this.getReturnResult("執行完成");
	}
	
	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}

	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
	}

	public WriteForBatchSendmailService getWriteForBatchSendmailService() {
		return writeForBatchSendmailService;
	}

	public void setWriteForBatchSendmailService(WriteForBatchSendmailService writeForBatchSendmailService) {
		this.writeForBatchSendmailService = writeForBatchSendmailService;
	}
}
