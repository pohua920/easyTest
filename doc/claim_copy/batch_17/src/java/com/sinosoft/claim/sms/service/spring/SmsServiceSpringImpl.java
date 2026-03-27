package com.sinosoft.claim.sms.service.spring;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import com.sinosoft.claim.sms.service.facade.SmsService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLsms;
import com.sinosoft.claim.schema.model.PrpLsmsLog;
import com.sinosoft.claim.schema.model.PrpLsmsTemplate;
import com.sinosoft.claim.schema.service.facade.PrpLsmsLogService;
import com.sinosoft.claim.schema.service.facade.PrpLsmsService;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class SmsServiceSpringImpl extends GenericDaoHibernate implements SmsService {
	/**简讯长度*/
	private static final int messageLen = 70;
	/**单号生成规则*/
	private BillService billService;
	/**简讯接口*/
	private PrpLsmsService prpLsmsService;
	/**简讯日志接口*/
	private PrpLsmsLogService prpLsmsLogService;
	/**
	 * 定义线程池的大小
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(32);
	/**
	 * 发送多个模板内容
	 * @param prpLsmsTemplateList
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String sendSms(List<PrpLsmsTemplate> prpLsmsTemplateList,Map<String,Object> data)throws Exception{
		for(PrpLsmsTemplate prpLsmsTemplate : prpLsmsTemplateList){
			sendSms(prpLsmsTemplate,data);
		}
		return "1";
	}
	/**
	 * 发送短信接口
	 * @param data
	 * @param modelIds
	 * @return
	 */
	public String sendSms(final PrpLsmsTemplate prpLsmsTemplate,final Map<String, Object> data) throws Exception{
		if(prpLsmsTemplate!=null){
			String syn = this.setTemplateValue(prpLsmsTemplate.getSyn(),data);
			if("1".equals(syn)){
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						try {
							saveSms(prpLsmsTemplate,data);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}else{
				return saveSms(prpLsmsTemplate,data);
			}
		}
		return "1";
	}
	/**
	 * 保存prpLsms的值
	 * @param prpLsmsTemplate
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String saveSms(PrpLsmsTemplate prpLsmsTemplate,Map<String, Object> data) throws Exception{
		String success = "1";
		String exception = null;
		PrpLsmsLog prpLsmsLog = new PrpLsmsLog();
		PrpLsmsTemplate template = null;
		try {
			//设置prpLsmsTemplate里面的值
			template = this.setTemplateValue(prpLsmsTemplate, data);
			List<PrpLsms> prpLsmsList = this.setPrpLsms(template);
			String[] messages = prpLsmsService.saveSms(prpLsmsList);
			success = messages[0];
			exception = messages[1];
		} catch (Exception e) {
			success = "0";
			if (e instanceof com.sinosoft.sysframework.exceptionlog.UserException) {
				com.sinosoft.sysframework.exceptionlog.UserException sysUserException = (com.sinosoft.sysframework.exceptionlog.UserException) e;
				exception = sysUserException.getErrorMessage();
			} else if (e instanceof com.sinosoft.utility.error.UserException) {
				com.sinosoft.utility.error.UserException errorUserException = (com.sinosoft.utility.error.UserException) e;
				exception = errorUserException.getErrorMessage();
			} else {
				exception = e.getLocalizedMessage();
			}
			e.printStackTrace();
		}finally{
			prpLsmsLog.setSuccess(success);
			prpLsmsLog.setException(exception);
			prpLsmsLog = getPrpLsmsLos(prpLsmsLog,template,data);
			prpLsmsLogService.logForSms(prpLsmsLog);
		}
		return success;
	}
	/**
	 * 设置prpLsms的值
	 * @param prpLsmsTemplate
	 * @return
	 * @throws Exception
	 */
	public List<PrpLsms> setPrpLsms(PrpLsmsTemplate prpLsmsTemplate) throws Exception{
		List<PrpLsms> prpLsmsList = new ArrayList<PrpLsms>();
		String serial = null;
		PrpLsms prpLsms = null;
		if(prpLsmsTemplate.getTarget()!=null){
			String[] messages = StringUtils.split(prpLsmsTemplate.getMessage(),messageLen);
			String[] targets = prpLsmsTemplate.getTarget().split(",");
			String submitDate = null;
			for(String target : targets){
				if(this.isMobileNumber(target)){
					serial = billService.getNo("prplsms","","",0);
					submitDate = this.getSubmitDate(null);
					for(int i =0;i<messages.length;i++){
						prpLsms = new PrpLsms(prpLsmsTemplate);
						if(i==0){
							prpLsms.setSerial(serial);
						}else if(i<10){
							prpLsms.setSerial(serial+"0"+i);
						}else{
							prpLsms.setSerial(serial+i);
						}
						prpLsms.setTarget(target);
						prpLsms.setMessage(messages[i]);
						if(CommonUtils.isEmpty(prpLsms.getSubmit_Date())){
							prpLsms.setSubmit_Date(submitDate);
						}
						prpLsmsList.add(prpLsms);
					}
				}
//				else{
//					throw new UserException(0,0,"target","手機號碼格式不正確！");
//				}
			}
		}
		return prpLsmsList;
	}
	/**
	 * 收集日志信息
	 * @param prpLsmsLog
	 * @param prpLsmsTemplate
	 * @param data
	 * @return
	 */
	public PrpLsmsLog getPrpLsmsLos(PrpLsmsLog prpLsmsLog, PrpLsmsTemplate prpLsmsTemplate,Map<String,Object>data){
		prpLsmsLog.setModelId(prpLsmsTemplate.getModelId());
		prpLsmsLog.setBusinessNo(String.valueOf(data.get("businessNo")));
		prpLsmsLog.setNodeType(prpLsmsTemplate.getNodeType());
		prpLsmsLog.setInputDate(new Date());
		prpLsmsLog.setTarget(prpLsmsTemplate.getTarget());
		UserDto userDto = (UserDto) data.get("userDto");
		prpLsmsLog.setUserCode(userDto.getUserCode());
		prpLsmsLog.setValidstatus("1");
		return prpLsmsLog;
	}
	/**
	 * 设置${name}里面的值
	 * @param name
	 * @param data
	 * @return
	 */
	public String setTemplateValue(Object value,Map<String,Object>data){
		if(value!=null){
			String temp = String.valueOf(value);
			if(!CommonUtils.isEmpty(temp) && data != null){
				for(String key : data.keySet()){
					if(data.get(key)!=null){
						temp = temp.replaceAll("\\$\\{"+key+"\\}", String.valueOf(data.get(key)));
					}else{
						temp = temp.replaceAll("\\$\\{"+key+"\\}", " ");
					}
				}
			}
			return temp;
		}
		return "";
	}
	/**
	 * 设置${name}里面的值
	 * @param name
	 * @param data
	 * @throws Exception 
	 */
	public PrpLsmsTemplate setTemplateValue(PrpLsmsTemplate prpLsmsTemplate,Map<String,Object>data) throws Exception{
		Method [] methods = PrpLsmsTemplate.class.getDeclaredMethods();
		Object value = null;
		PrpLsmsTemplate temp = new PrpLsmsTemplate();
		String name = null;
		Method setMethod = null;
		String setName = null;
		for(Method method : methods){
			name = method.getName();
			if(name.startsWith("get")){
				value = method.invoke(prpLsmsTemplate);
				setName = "set"+name.substring(3);
				if(method.getReturnType().toString().indexOf("java.lang.String")>-1){
					value = setTemplateValue(value,data);
				}
				setMethod = PrpLsmsTemplate.class.getDeclaredMethod(setName,method.getReturnType());
				if(setMethod!=null){
					setMethod.invoke(temp, value);
				}
			}
		}
		return temp;
	}
	/**
	 * 判断是否是手机号码
	 * @param mobileNumber
	 * @return
	 */
	public boolean isMobileNumber(String mobileNumber){
		if(CommonUtils.isEmpty(mobileNumber)){
			return false;
		}
		if(!mobileNumber.matches("^[0][9][0-9]{8}$")){
			return false;
		}
		return true;
	}
	/**
	 * 获取提交日期
	 * @return
	 */
	public String getSubmitDate(Date submitDate){
		if(submitDate==null){
			submitDate = new Date();
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(submitDate);
	}
	/**
	 * 定时任务执行
	 * @throws Exception
	 */
	public void smsJobDetail()throws Exception{
		String sql = "select distinct id,serial,target,corp_id,submit_date,dr_flag,deliver_date,language,message,payrefdate,businessno,smsFlag from prpLsms,prpJpayrefrechis where smsFlag <= 0 and smsFlag >-3 and businessno=certiNo  and certitype = 'C' and payrefdate IS not null order by serial";
		List<Object[]> list = (List<Object[]>) HibernateUtils.findbySql(super.getSession(),sql);
		PrpLsms prpLsms = null;
		DateTime dateTime = null;
		DateFormat payDateFormat = new SimpleDateFormat("yyy年MM月dd日");
		String updateSql = "update prpLsms set smsFlag = ? where id=?";
		int smsFlag = 1;
		String message = null;
		for(Object[] objs : list){
			try {
				prpLsms = new PrpLsms();
				prpLsms.setId(DataUtils.getString(objs[0]));
				prpLsms.setSerial(DataUtils.getString(objs[1]));
				prpLsms.setTarget(DataUtils.getString(objs[2]));
				prpLsms.setCorp_Id(DataUtils.getString(objs[3]));
				prpLsms.setSubmit_Date(DataUtils.getString(objs[4]));
				prpLsms.setDr_Flag(DataUtils.getString(objs[5]));
				prpLsms.setDeliver_Date(DataUtils.getString(objs[6]));
				prpLsms.setLanguage(DataUtils.getString(objs[7]));
				message = objs[8]==null?"":DataUtils.getString(objs[8]);
				prpLsms.setSmsFlag("1");
				if(objs[9]!=null){
					dateTime = new DateTime((Date)objs[9]);
					message = message.replaceAll("\\$\\{payDate\\}",payDateFormat.format(dateTime.addYear(-ConstantCodes.YEAROFFSET)));
					prpLsms.setMessage(message);
					dateTime = dateTime.addDay(1);
					dateTime.setHours(8);
					dateTime.setMinutes(30);
					prpLsms.setSubmit_Date(this.getSubmitDate(dateTime));
				}
				prpLsms.setBusinessNo(DataUtils.getString(objs[10]));
				smsFlag = DataUtils.getInteger(objs[11]);
				String [] success = prpLsmsService.saveSMSRequest(prpLsms);
				if("1".equals(success[0])){
					smsFlag = 1;
				}else{
					smsFlag = smsFlag-1;
				}
				super.getSession().createSQLQuery(updateSql).setString(0, String.valueOf(smsFlag)).setString(1, prpLsms.getId()).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public BillService getBillService() {
		return billService;
	}
	public void setBillService(BillService billService) {
		this.billService = billService;
	}
	public PrpLsmsService getPrpLsmsService() {
		return prpLsmsService;
	}
	public void setPrpLsmsService(PrpLsmsService prpLsmsService) {
		this.prpLsmsService = prpLsmsService;
	}
	public PrpLsmsLogService getPrpLsmsLogService() {
		return prpLsmsLogService;
	}
	public void setPrpLsmsLogService(PrpLsmsLogService prpLsmsLogService) {
		this.prpLsmsLogService = prpLsmsLogService;
	}
	public ExecutorService getExecutorService() {
		return executorService;
	}
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
}
