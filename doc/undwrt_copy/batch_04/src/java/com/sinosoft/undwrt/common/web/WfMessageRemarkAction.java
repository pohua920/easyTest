package com.sinosoft.undwrt.common.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.service.facade.WfMessageService;
import com.sinosoft.undwrt.undwrtBase.model.WfMessage;
import com.sinosoft.undwrt.undwrtBase.model.WfMessageId;
import com.sinosoft.utility.string.Str;


import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

/**
 * 備註記錄處理類.
 */
public class WfMessageRemarkAction extends Struts2Action {
	
	 /** 屬性業務號. */
 	private String businessNo="";
	 
	 /** 屬性備註訊息ID. */
 	private String messageId="";
	
	 /** 屬性操作人代碼. */
 	private String operatorCode="";
	 
	 /** 屬性操作人名稱. */
 	private String operatorName="";
	 
	 /** 屬性備註內人. */
 	private String Context="";
	
	 /** 屬性回車符號. */
 	private static String LINECR = "\r\n";
	
	/** 屬性備注訊息接口. */
	private WfMessageService wfMessageService;

    /**
	 * 備註訊息查詢儲存.
	 * 
	 * @return the string
	 * @throws Exception
	 *             異常
	 */
    public String wfMessageRemark() throws Exception
     {
         String actionType =StringUtils.trimToEmpty( this.getRequest().getParameter("actionType"));     
         if(actionType.equals("query"))
         {
             this.query(businessNo,messageId);
         }
         else if(actionType.equals("save"))
         {
             this.saveMessage();
         }
         
         return actionType;
     }
	
    
    /**
	 * 儲存備註訊息.
	 * 
	 * @throws Exception
	 *             異常
	 */
    public void saveMessage() throws Exception
    {
        Collection WfMessageDtoList = new ArrayList();
        List wfMessageDtoList = (List) this.requestToWfMessageDto();
        this.saveMessage(wfMessageDtoList);
    }
   
    
    /**
	 * 查詢備註訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param messageId
	 *            備註訊息ID
	 * @throws Exception
	 *             異常
	 */
    public void query(String businessNo,String messageId ) throws Exception
    {
        String conditions = "Select MessageID," +
        " BusinessNo," +
        " SerialNo," +
        " LineNo," +
        " Context," +
        " OperateTime," +
        " OperatorCode," +
        " OperatorName," +
        " Flag From WfMessage Where "+" messageId = '" + messageId + "' order by businessNo,serialNo";
        
        List<WfMessage> wfMessageList = (List<WfMessage>)wfMessageService.findByConditions(conditions);
        List wfMessageDispList = new ArrayList();
        int count = 0, serialNo = 0;
        String context = null, operatorName = null, operatorTime = null;
        WfMessage wfMessageDto = null, wfMessageDispDto = null;
        for(int i=0; i<wfMessageList.size(); i++)
        {
            count = count + 1;
            wfMessageDto = (WfMessage)wfMessageList.get(i);
            if(count==1)
            {
                businessNo = wfMessageDto.getBusinessNo();
                serialNo   = wfMessageDto.getWfMessageId().getSerialNo();
                context    = wfMessageDto.getContext();
                operatorName = wfMessageDto.getOperatorName();
                operatorTime = wfMessageDto.getOperateTime();
                continue;
            }
            if(wfMessageDto.getBusinessNo().equals(businessNo) && 
               wfMessageDto.getWfMessageId().getSerialNo()==serialNo)
            {
                 context = context + LINECR + wfMessageDto.getContext();
                 if(count == wfMessageList.size())
                 {
                     wfMessageDispDto = new WfMessage();
                     
                     wfMessageDispDto.setBusinessNo(businessNo);
                     WfMessageId id=new WfMessageId();
                     id.setMessageId(messageId);
                     wfMessageDispDto.setWfMessageId(id);
                     wfMessageDispDto.setOperatorName(operatorName);
                     wfMessageDispDto.setOperateTime(operatorTime);
                     wfMessageDispDto.setContext(context);
                     wfMessageDispList.add(wfMessageDispDto);
                 }
            }
            else
            {
                 //不等则将将上一条信息放入显示结果,并记录当前记录信息
                 wfMessageDispDto = new WfMessage();
                 WfMessageId id=new WfMessageId();
                 wfMessageDispDto.setBusinessNo(businessNo);
                 id.setMessageId(messageId);
                 wfMessageDispDto.setWfMessageId(id);
                 wfMessageDispDto.setOperatorName(operatorName);
                 wfMessageDispDto.setOperateTime(operatorTime);
                 wfMessageDispDto.setContext(context);
                 wfMessageDispList.add(wfMessageDispDto);
                 businessNo = wfMessageDto.getBusinessNo();
                 serialNo   = wfMessageDto.getWfMessageId().getSerialNo();
                 context    = wfMessageDto.getContext();
                 operatorName = wfMessageDto.getOperatorName();
                 operatorTime = wfMessageDto.getOperateTime();
                 if(count==wfMessageList.size())
                 {
                    wfMessageDispDto = new WfMessage();
                    id.setMessageId(messageId);
                    wfMessageDispDto.setWfMessageId(id);
                    wfMessageDispDto.setBusinessNo(businessNo);
                    wfMessageDispDto.setOperatorName(operatorName);
                    wfMessageDispDto.setOperateTime(operatorTime);
                    wfMessageDispDto.setContext(context);
                    wfMessageDispList.add(wfMessageDispDto);
                 }
             }
        }
        if(count==1)
        {
            wfMessageDispDto = new WfMessage();
            WfMessageId id=new WfMessageId();
            id.setMessageId(messageId);
            wfMessageDispDto.setWfMessageId(id);
            wfMessageDispDto.setBusinessNo(businessNo);
            wfMessageDispDto.setOperatorName(operatorName);
            wfMessageDispDto.setOperateTime(operatorTime);
            wfMessageDispDto.setContext(context);
            wfMessageDispList.add(wfMessageDispDto);
        }
        //---------------------------------------------------------------------
        HttpSession session = this.getSession();
        String userCode = (String)session.getAttribute("myUserCode");
        String userName = (String)session.getAttribute("myUserName");
        String operateTime = new DateTime().current().toString().substring(0,19);
        //生成初始数据
        wfMessageDto = new WfMessage();
        WfMessageId id=new WfMessageId();
        id.setMessageId(messageId);
        wfMessageDto.setWfMessageId(id);
        wfMessageDto.setBusinessNo(businessNo);
        wfMessageDto.setOperateTime(operateTime);
        wfMessageDto.setOperatorCode(userCode);
        wfMessageDto.setOperatorName(userName);
        //---------------------------------------------------------------------
        this.getRequest().setAttribute("WfMessageDto", wfMessageDto);
        this.getRequest().setAttribute("WfMessageList", wfMessageDispList);
    }
    
    /**
	 * 将录入数据传输对象转化为dto.
	 * 
	 * @return the collection
	 * @throws Exception
	 *             異常
	 */
    public Collection requestToWfMessageDto() throws Exception
    {
        Collection WfMessageDtoList = new ArrayList();
        String[] arrConText = {};
        int serialNo        = 0;
        int lineNo          = 0;
        String operateTime  = new DateTime().current().toString().substring(0,19);
        //将流言信息拆分为多行
        //对单个字符进行判断
        if(Context.length()==1){
     	   arrConText = new String[1];
     	   arrConText[0]=Context;
        }
        else{
           arrConText = Str.split(Context, LINECR);
        }
        
        if(messageId.equals("")||messageId==null)
        {
     	   messageId  =businessNo;
        }
        QueryRule queryRule=QueryRule.getInstance();
        queryRule.addEqual("wfMessageId.messageId", messageId);
        queryRule.addDescOrder("wfMessageId.serialNo");
        List list=(List) wfMessageService.getMaxSerialNo(queryRule);
        if(list.size()==0 || list==null || "".equals(list))
        {
        	serialNo=0;
        }
        else
        {
            WfMessage wfMessage=(WfMessage)list.get(0);
            serialNo=wfMessage.getWfMessageId().getSerialNo();
        }
        serialNo = serialNo+1;
        for(int i =0;i<arrConText.length;i++)
        {
             lineNo = lineNo  + 1;

             WfMessage wfMessageDto = new WfMessage();
             wfMessageDto.setBusinessNo(businessNo);
             WfMessageId id=new WfMessageId();
             id.setMessageId(messageId);
             id.setLineNo(lineNo);
             id.setSerialNo(serialNo);
             wfMessageDto.setWfMessageId(id);
             wfMessageDto.setOperateTime(operateTime);
             wfMessageDto.setOperatorCode(operatorCode);
             wfMessageDto.setOperatorName(operatorName);
             wfMessageDto.setContext(arrConText[i]);
             WfMessageDtoList.add(wfMessageDto);
        }
        return WfMessageDtoList;

    }
    
    /**
	 * 保存留言信息.
	 * 
	 * @param wfMessageDtoList
	 *            備註訊息列表
	 * @throws Exception
	 *             異常
	 */
    public void saveMessage(Collection wfMessageDtoList) throws Exception{
        DBManager dbManager = new DBManager();
        WfMessage wfMessageDto;
        try{
            dbManager.open("undwrtDataSource");
            dbManager.beginTransaction();
            //插入记录
            Iterator itwfmessage = wfMessageDtoList.iterator();
            while (itwfmessage.hasNext())
            {
                wfMessageDto = (WfMessage)itwfmessage.next();
                wfMessageService.saveMessage(wfMessageDto,dbManager);
            }
            dbManager.commitTransaction();
        }catch(Exception exception){
            dbManager.rollbackTransaction();
            throw exception;
        }finally{
            dbManager.close();
        }
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
	 * 獲取屬性備註訊息ID.
	 * 
	 * @return 屬性備註訊息ID的值
	 */
	public String getMessageId() {
		return messageId;
	}


	/**
	 * 設置屬性備註訊息ID.
	 * 
	 * @param messageId
	 *            待設置的備註訊息ID的值
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	/**
	 * 獲取屬性備注訊息接口.
	 * 
	 * @return 屬性備注訊息接口的值
	 */
	public WfMessageService getWfMessageService() {
		return wfMessageService;
	}


	/**
	 * 設置屬性備注訊息接口.
	 * 
	 * @param wfMessageService
	 *            待設置的備注訊息接口的值
	 */
	public void setWfMessageService(WfMessageService wfMessageService) {
		this.wfMessageService = wfMessageService;
	}


	/**
	 * 獲取屬性操作人代碼.
	 * 
	 * @return 屬性操作人代碼的值
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * 設置屬性操作人代碼.
	 * 
	 * @param operatorCode
	 *            待設置的操作人代碼的值
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 獲取屬性操作人名稱.
	 * 
	 * @return 屬性操作人名稱的值
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 設置屬性操作人名稱.
	 * 
	 * @param operatorName
	 *            待設置的操作人名稱的值
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 獲取屬性備註內人.
	 * 
	 * @return 屬性備註內人的值
	 */
	public String getContext() {
		return Context;
	}

	/**
	 * 設置屬性備註內人.
	 * 
	 * @param context
	 *            待設置的備註內人的值
	 */
	public void setContext(String context) {
		Context = context;
	}

}
