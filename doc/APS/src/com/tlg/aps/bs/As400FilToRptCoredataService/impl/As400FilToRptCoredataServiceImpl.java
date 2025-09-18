package com.tlg.aps.bs.As400FilToRptCoredataService.impl;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.As400FilToRptCoredataService.As400FilToRptCoredataService;
import com.tlg.db2.entity.As400FilToRptCoredata;
import com.tlg.db2.service.As400FilCustService;
import com.tlg.msSqlRdcB2b.entity.RptCoredata;
import com.tlg.msSqlRdcB2b.service.RptCoredataService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B*/
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class As400FilToRptCoredataServiceImpl implements As400FilToRptCoredataService {

	private static final Logger logger = Logger.getLogger(As400FilToRptCoredataServiceImpl.class);
	
	private As400FilCustService as400FilCustService;
	private RptCoredataService rptCoredataService;

	@Override
	public Result excute() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		//取得查詢語法
		FileReader fr =null;
		try {
			
			List<String> lines = Files.readAllLines(Paths.get(Constants.aps061QueryPath));
			String queryStr = String.join("\n", lines);//保留換行
			
			//查詢DB2 AS400
            Map<String, Object> qryMap = new HashMap<String, Object>();
            qryMap.put("queryStr", queryStr);
            Result result =  as400FilCustService.findAs400FilByCustQueryStr(qryMap);
            List<As400FilToRptCoredata> datas = (List<As400FilToRptCoredata>) result.getResObject();
            
            if(datas != null && datas.size() > 0) {
            	 //轉入sql server RptCoredata table
                List<RptCoredata> rptCoreDataList = new ArrayList<RptCoredata>();
                for(As400FilToRptCoredata data :datas) {
                	RptCoredata rptCoredata = new RptCoredata();
                	//資料內容都須trim過
                	
                	//出單日
                	if(StringUtils.isNotBlank(data.getPolDate())) {
                		rptCoredata.setPoldate(sdf.parse(StringUtils.trim(data.getPolDate())));
                	}
                	//保單號碼
                	if(StringUtils.isNotBlank(data.getPolicyNo())) {
                		rptCoredata.setPolicyno(StringUtils.trim(data.getPolicyNo()));
                	}
                	//批單號碼
                	if(StringUtils.isNotBlank(data.getEndorseNo())) {
                		rptCoredata.setEndorseno(StringUtils.trim(data.getEndorseNo()));
                	} else {//mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
                		rptCoredata.setEndorseno("");
                	}
                	//險種
                	if(StringUtils.isNotBlank(data.getInType())) {
                		rptCoredata.setInstype(StringUtils.trim(data.getInType()));
                	}
                	//被保險人ID
                	if(StringUtils.isNotBlank(data.getInsUid())) {
                		rptCoredata.setInsuid(StringUtils.trim(data.getInsUid()));
                	} else {//mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
                		rptCoredata.setInsuid("");
                	}
                	//被保險人姓名
                	if(StringUtils.isNotBlank(data.getInsuName())) {
                		rptCoredata.setInsuname(StringUtils.trim(data.getInsuName()));
                	}            	
                	//保單生效日
                	if(StringUtils.isNotBlank(data.getEffDate())) {
                		rptCoredata.setEffdate(sdf.parse(StringUtils.trim(data.getEffDate())));
                	}
                	//保險期間起日
                	if(StringUtils.isNotBlank(data.getStartPeriodDate())) {
                		rptCoredata.setStartperioddate(sdf.parse(StringUtils.trim(data.getStartPeriodDate())));
                	}
                	//保險期間迄日
                	if(StringUtils.isNotBlank(data.getEndPeriodDate())) {
                		rptCoredata.setEndperioddate(sdf.parse(StringUtils.trim(data.getEndPeriodDate())));
                	}
                	//保費
                	if(data.getTotalPrem() != null) {
                		rptCoredata.setTotalprem(data.getTotalPrem());
                	}
                	//通路代號
                	if(data.getChannelId() != null) {
                		rptCoredata.setChannelid(StringUtils.trim(data.getChannelId()));
                	}
                	//通路名稱
                	if(data.getChannelName() != null) {
                		rptCoredata.setChannelname(StringUtils.trim(data.getChannelName()));
                	}
                	//業務員證號
                	if(data.getChnlAgentBussNo() != null) {
                		rptCoredata.setChnlagentbussno(StringUtils.trim(data.getChnlAgentBussNo()));
                	}
                	//業務員姓名
                	if(data.getChnlAgentName() != null) {
                		rptCoredata.setChnlagentname(StringUtils.trim(data.getChnlAgentName()));
                	}
                	//服務人員代號
                	if(data.getPolAgentId() != null) {
                		rptCoredata.setPolagentid(StringUtils.trim(data.getPolAgentId()));
                	}
                	//服務人員姓名
                	if(data.getPolAgentName() != null) {
                		rptCoredata.setPolagentname(StringUtils.trim(data.getPolAgentName()));
                	}
                	//建檔日期
                	rptCoredata.setCreatedate(today);
                	
                	
                	//放入List
                	rptCoreDataList.add(rptCoredata);
                }
                
                //存入RptCoredata
                rptCoredataService.insertRptCoredata(rptCoreDataList);
            }
        } catch (Exception e) {
        	logger.error("As400FilToRptCoredataServiceImpl.excute:error"+ e);
			e.printStackTrace();
			return this.getReturnResult("收件報備系統已出單資料回拋B2B排程作業執行異常:"+e.getMessage());
		} finally {
			try {
				if (fr != null) {
		            fr.close();
				}
				
			} catch (Exception e) {
				logger.error("As400FilToRptCoredataServiceImpl.excute:error"	+ e);
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		return this.getReturnResult("收件報備系統已出單資料回拋B2B排程作業執行成功");
	}
	
	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public As400FilCustService getAs400FilCustService() {
		return as400FilCustService;
	}

	public void setAs400FilCustService(As400FilCustService as400FilCustService) {
		this.as400FilCustService = as400FilCustService;
	}

	public RptCoredataService getRptCoredataService() {
		return rptCoredataService;
	}

	public void setRptCoredataService(RptCoredataService rptCoredataService) {
		this.rptCoredataService = rptCoredataService;
	}
	
	
}
