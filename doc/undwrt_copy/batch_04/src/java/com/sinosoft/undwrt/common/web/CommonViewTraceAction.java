package com.sinosoft.undwrt.common.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.prpall.dto.domain.PrpCmainDto;
import com.sinosoft.prpall.dto.domain.PrpTmainDto;
import com.sinosoft.prpall.pubfun.PubTools;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.common.vo.WfLogVo;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.WfLogId;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

/**
 * 主動回撤處理類.
 */
public class CommonViewTraceAction extends Struts2Action {
	
	/** 屬性業務號. */
	private String businessNo;
	
	/** 屬性業務類型. */
	private String businessType;
	
	/** 屬性核保系統查詢接口. */
	private PrpallService prpallService;
	
	/** 屬性核保處理意見接口. */
	private UwNotionService uwNotionService;
	
	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;
	

	/**
	 * 獲取主動回撤歷史訊息.
	 * 
	 * @return 調整字符
	 * @throws UserException
	 *             用戶異常
	 * @throws Exception
	 *             異常
	 */
	public String getExaminationHistory() throws UserException, Exception {
		String forward = "";
		try {
			Collection colTraceInfoList = new ArrayList();
			colTraceInfoList = this.getTraceInfoList();
			this.getRequest().setAttribute("TraceInfoList", colTraceInfoList);
			forward = "success";
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return forward;
	}
	
	/**
	 * 獲取軌跡訊息.
	 * 
	 * @return 軌跡訊息集合
	 * @throws Exception
	 *             異常
	 */
	 public Collection getTraceInfoList() throws Exception
	  {
	    Collection colTraceInfoList = new ArrayList();
	    Collection colTraceHisList = null;
	    Collection colPolicyList = null;
	    Collection colNotionList = new ArrayList();
	    WfLogVo wfLogDto = null;
	    WfLogVo wfLogQuoteDto = null;//报价单审核意见存储
	    //WfLogStoreDto wfLogStoreDto = null;//add by chenxi;20081125；转储后的核保信息根据wflogstore表里的flowid进行获取
	    PrpCmain prpCmainDto = null;
	    PrpTmain prpTmainDto = null;
	    UwNotion uwNotionDto = null;
	    String strNotionSQL = "";
	    String handleText = "";
	    String strIsILog = ""; //是否是规则引擎返回值
	    String strILogMessage = "";
	    String[] arrILog1 = null;
	    String[] arrILog2 = null;
	    String strClassCode = "";
	    String strQuoteNo =  "";

	    if(businessNo!=null && businessNo.length()>0 && businessNo.charAt(0)=='P' && businessNo.indexOf("_")<0)
	    {
	      prpCmainDto=prpallService.getPrpCmain(businessNo);
	      if(prpCmainDto!=null && prpCmainDto.getProposalNo()!=null && prpCmainDto.getProposalNo().trim().length()>0)
	      {
	      	businessNo=prpCmainDto.getProposalNo();
	      }
	    }

	    String strSQL = " BusinessNo ='" + businessNo + "'"
	        + " ORDER BY FlowId,LogNo";
	    //获取正常轨迹信息
	    colPolicyList = (ArrayList)wfLogService.findBySql(strSQL);
	    
	    if("T".equals(businessType)){
	    	List<WfLogVo> colQuoteList = new ArrayList<WfLogVo>();
	    	prpTmainDto = prpallService.getPrpTmain(businessNo,businessType);
	    	strQuoteNo = prpTmainDto.getQuoteno();
	    	
	    	String strSQLQuote = " BusinessNo ='" + strQuoteNo + "' and nodeno = '12'"
	    	        + " ORDER BY FlowId,LogNo";
	    	    //获取正常轨迹信息
	    	colQuoteList = wfLogService.findBySql(strSQLQuote);
	    	PrpQmain prpQmain=new PrpQmain();
	    	if(strQuoteNo!=null && strQuoteNo.length()>10){
	    		prpQmain = prpallService.getPrpTmainQta(strQuoteNo);//获取报价单数据
	    		wfLogQuoteDto = new WfLogVo();
	    		WfLogId id=new WfLogId();
	    		id.setFlowId(prpQmain.getProposalNo());
	    		id.setLogNo(1);
	    		wfLogQuoteDto.setId(id);
	    		wfLogQuoteDto.setNodeName(getText("undwrt.action.commonView.quotation"));
	    		wfLogQuoteDto.setOperatorName(prpQmain.getUnderWriteName());
	    		if(null!=prpQmain.getUnderWriteEndDate())
	    		{
	    			if(colQuoteList.size()>0){
	    				wfLogQuoteDto.setSubmitTime(colQuoteList.get(0).getFlowInTime());
	    	    	}else{
	    	    		wfLogQuoteDto.setSubmitTime("");
	    	    	}
	    		}
	    		wfLogQuoteDto.setHandleText(getText("undwrt.action.commonView.examineAuto"));
	    		wfLogQuoteDto.setNodeNo(-1);//报价单状态标识
	    		wfLogQuoteDto.setNodeStatusName(PubTools.convertQuoteStatus(prpQmain.getUnderWriteFlag()));
	    	}
	    }


	    if (colPolicyList.size() > 0)
	    {
	    	 if(wfLogQuoteDto!=null){
		      	  colTraceInfoList.add(wfLogQuoteDto);
		      }
	      for(Iterator iWfLog=colPolicyList.iterator();iWfLog.hasNext();)
	      {
	    	strILogMessage = "";
	        strIsILog = "";
	        wfLogDto = (WfLogVo)iWfLog.next();
	        handleText = "";
	        QueryRule queryRule=QueryRule.getInstance();
	        queryRule.addEqual("id.flowId", wfLogDto.getId().getFlowId());
	        queryRule.addEqual("id.logNo", wfLogDto.getId().getLogNo());
	        queryRule.addAscOrder("id.lineNo");
	        strClassCode = wfLogDto.getClassCode();
	        colNotionList = (ArrayList)uwNotionService.findByConditions(queryRule);
	        for(Iterator iNotion=colNotionList.iterator();iNotion.hasNext();)
	        {
	          uwNotionDto = (UwNotion)iNotion.next();
	          if ("1".equals(uwNotionDto.getFlag())) {
							strILogMessage += uwNotionDto.getHandleText();
							strIsILog = uwNotionDto.getFlag();
						} else {
							handleText += uwNotionDto.getHandleText();
							handleText += "\r\n";
							strIsILog = uwNotionDto.getFlag();
						}
	        }
	        // 拆分
	        
	        	if ("1".equals(strIsILog)) {
					arrILog1 = strILogMessage.split(";");
					if(arrILog1.length>1){
					for (int i = 0; i < arrILog1.length; i++) {
						if(strClassCode.equals("A") || strClassCode.equals("B")){
							arrILog2 = arrILog1[i].split("：");
						}else{
							arrILog2 = arrILog1[i].split(":");
						}
						handleText += arrILog2[1];
						handleText += "\r\n";
					
					}
				 }else{
					 handleText += strILogMessage;
						handleText += "\r\n";
				 }
				}
	        
					
					wfLogDto.setMessage(strILogMessage);
					wfLogDto.setHandleText(handleText);
					wfLogDto.setIsILog(strIsILog);
	      }
	     
	      colTraceInfoList.addAll(colPolicyList);
	      return colTraceInfoList;
	      
	    }
	    else
	    {
//	    	String strSQLelse = " BusinessNo ='" + businessNo + "'"
//	        + " ORDER BY FlowId,LogNo";
//	    	BLWfLogStoreActionBase blWfLogStoreActionBase = new BLWfLogStoreActionBase();
//	    	colTraceHisList = (ArrayList)blWfLogStoreActionBase.findByConditions(strSQLelse);
//	    	if(wfLogQuoteDto!=null){
//	       	    colTraceHisList.add(wfLogQuoteDto);
//	        }
//	    	for(Iterator iWfLogStoreDto=colTraceHisList.iterator();iWfLogStoreDto.hasNext();)
//	        {
//	    	   strILogMessage = "";
//	           strIsILog = "";  
//	    		wfLogStoreDto = (WfLogStoreDto)iWfLogStoreDto.next();
//	          handleText = "";
//	          strNotionSQL = " FlowId ='"+ wfLogStoreDto.getFlowID() +"'"
//	              +" AND LogNo ='"+ wfLogStoreDto.getLogNo() +"'"
//	              +" ORDER BY LineNo";
//	          strClassCode = wfLogStoreDto.getClassCode();
//	          UwNotionFindByConditionsCommand uwNotionFindByConditionsCommand = new UwNotionFindByConditionsCommand(strNotionSQL);
//	          colNotionList = (ArrayList)uwNotionFindByConditionsCommand.execute();
//	          for(Iterator iNotion=colNotionList.iterator();iNotion.hasNext();)
//	          {
//	            uwNotionDto = (UwNotionDto)iNotion.next();
//	            if("1".equals(uwNotionDto.getFlag())){
//	          	  strILogMessage += uwNotionDto.getHandleText();
//	                strIsILog = uwNotionDto.getFlag();
//	            }else{
//	            	handleText += uwNotionDto.getHandleText();
//	            	handleText+="\r\n";
//	                strIsILog = uwNotionDto.getFlag();
//	            }
//	          }
//	           //拆分
//	          
//	        if(strIsILog.equals("1")){
//		        arrILog1 = strILogMessage.split(";");
//		        if(arrILog1.length>1){
//		        	for(int i = 0; i < arrILog1.length; i++){
//		        		if(strClassCode.equals("05")){
//		        			arrILog2 = arrILog1[i].split("：");
//						}else{
//							arrILog2 = arrILog1[i].split(":");
//						}
//			        	handleText += arrILog2[1];
//			        	handleText+="\r\n";
//			        }
//		        }else{
//		        	handleText += strILogMessage;
//		        	handleText+="\r\n";
//		        }	        
//	        }
//	        wfLogStoreDto.setMessage(strILogMessage);
//	        wfLogStoreDto.setHandleText(handleText);
//	        wfLogStoreDto.setHandleText(handleText);
//	        }
	      }
	      
	     return colTraceHisList;
	  
	  }

	/**
	 * 獲取屬性業務號.
	 * 
	 * @return 屬性業務號的值
	 */
	public String getBusinessNo() {
		return businessNo;
	}

	/**
	 * 設置屬性業務號.
	 * 
	 * @param businessNo
	 *            待設置的業務號的值
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 獲取屬性業務類型.
	 * 
	 * @return 屬性業務類型的值
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 設置屬性業務類型.
	 * 
	 * @param businessType
	 *            待設置的業務類型的值
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 獲取屬性核保系統查詢接口.
	 * 
	 * @return 屬性核保系統查詢接口的值
	 */
	public PrpallService getPrpallService() {
		return prpallService;
	}

	/**
	 * 設置屬性核保系統查詢接口.
	 * 
	 * @param prpallService
	 *            待設置的核保系統查詢接口的值
	 */
	public void setPrpallService(PrpallService prpallService) {
		this.prpallService = prpallService;
	}

	/**
	 * 獲取屬性核保處理意見接口.
	 * 
	 * @return 屬性核保處理意見接口的值
	 */
	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	/**
	 * 設置屬性核保處理意見接口.
	 * 
	 * @param uwNotionService
	 *            待設置的核保處理意見接口的值
	 */
	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
	}

	/**
	 * 獲取屬性工作流日誌接口.
	 * 
	 * @return 屬性工作流日誌接口的值
	 */
	public WfLogService getWfLogService() {
		return wfLogService;
	}

	/**
	 * 設置屬性工作流日誌接口.
	 * 
	 * @param wfLogService
	 *            待設置的工作流日誌接口的值
	 */
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	} 
	
	
}
