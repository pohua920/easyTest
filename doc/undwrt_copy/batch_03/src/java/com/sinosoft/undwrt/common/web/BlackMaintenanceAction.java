package com.sinosoft.undwrt.common.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sinosoft.common.schema.model.PrpDBankInfo;
import com.sinosoft.platform.bl.facade.BLPrpDuserFacade;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.web.view.AbstractForm;
import com.sinosoft.undwrt.undwrtBase.model.UwBlackList;
import com.sinosoft.undwrt.undwrtBase.model.UwBlackListId;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwBlackListService;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

/**
 * 黑名單維護處理類.
 */
public class BlackMaintenanceAction extends Struts2Action {
	private UwBlackListService uwBlackListService;
    private String  insuredType;
    private String  identifyType;
    private String  identifyNumber;
    private String  blackListType;
    private String  blackListCode;
    private String  insuredName;
    private String  insuredEName;
    private String  email;
    private String  mobile;
    private String  phoneNumber;
    private String  addressEName;
    private String  linkAddress;
    private String  postCode;
    private String  addressCName;
    private String  sex;
    private String  bankCode1;
    private String  bankCode2;
    private String  bank;
    private String  account;
    private String  insuredIdvNote;
    private String  inputDate;
    private String  riskCode;
    private String  flag;
    private Date  inputDateRC;
    private String cheatMeans;

	private List blackMaintenanceList;
	/**
	 * 进入黑名單維護.
	 * 
	 * @return the string         、
	 * @throws Exception 
	 */
	public String blackMaintenance() throws Exception  {
		String message="";
		String type = this.getRequest().getParameter("type");
		PrpDuserDto prpDuserDto = (PrpDuserDto) this.getRequest().getSession().getAttribute("user");

		if("save".equals(type)){
			UwBlackList uwBlackList =new UwBlackList();
			UwBlackListId id =new UwBlackListId();
			id.setBlackListCode(blackListCode);
			id.setBlackListType(blackListType);
			uwBlackList.setId(id);
			uwBlackList.setAccount(account);
			uwBlackList.setAddressCName(addressCName);
			uwBlackList.setAddressEName(addressEName);
			uwBlackList.setBank(bank);
			uwBlackList.setBankCode1(bankCode1);
			uwBlackList.setBankCode2(bankCode2);
			uwBlackList.setEmail(email);
			uwBlackList.setOperatorCode(prpDuserDto.getUserName());
			uwBlackList.setIdentifyNumber(identifyNumber);
			uwBlackList.setIdentifyType(identifyType);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			inputDate=Integer.parseInt(inputDate.substring(0, 3))+1911+inputDate.substring(3, inputDate.length());
			Date input = sdf.parse(inputDate);
			uwBlackList.setInputDate(input);
			uwBlackList.setInsuredEName(insuredEName);
			uwBlackList.setInsuredIdvNote(insuredIdvNote);
			uwBlackList.setInsuredName(insuredName);
			uwBlackList.setInsuredType(insuredType);
			uwBlackList.setLinkAddress(linkAddress);
			uwBlackList.setMobile(mobile);
			uwBlackList.setPhoneNumber(phoneNumber);
			uwBlackList.setPostCode(postCode);
			uwBlackList.setRiskCode(riskCode);
			uwBlackList.setSex(sex);
			flag="1";
			uwBlackListService.saveBlackMaintenance(uwBlackList);
			message="save";
		}else if("query".equals(type)){
			Page page = null;
			PageRecord pageRecord = null;
			ParamUtils paramUtils = new ParamUtils(this.getRequest());
			int pageNo = paramUtils.getIntParameter("pageNo", 1);
			int rowsPerPage = paramUtils.getIntParameter("rowsPerPage", 10);
			inputDateRC=new Date();
			boolean nodeStatusView = false;
			String statement =  uwBlackListService.getStatement(identifyNumber,riskCode,nodeStatusView);
			page = uwBlackListService.findByStatement(statement, pageNo, rowsPerPage, nodeStatusView);
			pageRecord = uwBlackListService.findByStatementPageRecord(statement, pageNo, rowsPerPage, nodeStatusView);
			message="query";
			flag="1";
			blackMaintenanceList = page.getResult();
			this.getRequest().setAttribute("fm", new AbstractForm(pageRecord));
		}else if("update".equals(type)||"list".equals(type)){
			if("list".equals(type)){
				this.getRequest().setAttribute("only", "1");
			}
			String param = this.getRequest().getParameter("param");
			QueryRule queryRule  =QueryRule.getInstance();
			queryRule.addEqual("id.blackListType", "E");
			queryRule.addEqual("id.blackListCode", param);
			List<UwBlackList> list = uwBlackListService.getUwBlackList(queryRule);
			if(list!=null){
				for (int i = 0; i < list.size(); i++) {
					UwBlackList  uwBlackList=	list.get(i);
					blackListCode=uwBlackList.getId().getBlackListCode();
					blackListType=uwBlackList.getId().getBlackListType();
					account=uwBlackList.getAccount();
					addressCName=uwBlackList.getAddressCName();
					addressEName=uwBlackList.getAddressEName();
					bank=uwBlackList.getBank();
					bankCode1=uwBlackList.getBankCode1();
					bankCode2=uwBlackList.getBankCode2();
					email=uwBlackList.getEmail();
					identifyNumber=uwBlackList.getIdentifyNumber();
					identifyType=uwBlackList.getIdentifyType();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					inputDate=sdf.format(uwBlackList.getInputDate());
					inputDateRC=uwBlackList.getInputDate();
					insuredEName=uwBlackList.getInsuredEName();
					insuredIdvNote=uwBlackList.getInsuredIdvNote();
					insuredName=uwBlackList.getInsuredName();
					insuredType=uwBlackList.getInsuredType();
					linkAddress=uwBlackList.getLinkAddress();
					mobile=uwBlackList.getMobile();
					phoneNumber=uwBlackList.getPhoneNumber();
					postCode=uwBlackList.getPostCode();
					riskCode=uwBlackList.getRiskCode();
					sex=uwBlackList.getSex();
					flag="3";
					message=type;
				}
			}
		}else  if("updateList".equals(type)){
			String param = this.getRequest().getParameter("identifyNumber");
			QueryRule queryRule  =QueryRule.getInstance();
			queryRule.addEqual("id.blackListType", "E");
			queryRule.addEqual("id.blackListCode", param);
			List<UwBlackList> list = uwBlackListService.getUwBlackList(queryRule);
			if(list!=null&&list.size()>0){
				UwBlackList  uwBlackList=	list.get(0);
				uwBlackList.setAccount(account);
				uwBlackList.setAddressCName(addressCName);
				uwBlackList.setAddressEName(addressEName);
				uwBlackList.setBank(bank);
				uwBlackList.setBankCode1(bankCode1);
				uwBlackList.setBankCode2(bankCode2);
				uwBlackList.setEmail(email);
				uwBlackList.setIdentifyNumber(identifyNumber);
				uwBlackList.setIdentifyType(identifyType);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				inputDate=Integer.parseInt(inputDate.substring(0, 3))+1911+inputDate.substring(3, inputDate.length());
				Date input = sdf.parse(inputDate);
				uwBlackList.setInputDate(input);
				uwBlackList.setInsuredEName(insuredEName);
				uwBlackList.setInsuredIdvNote(insuredIdvNote);
				uwBlackList.setInsuredName(insuredName);
				uwBlackList.setInsuredType(insuredType);
				uwBlackList.setLinkAddress(linkAddress);
				uwBlackList.setMobile(mobile);
				uwBlackList.setPhoneNumber(phoneNumber);
				uwBlackList.setPostCode(postCode);
				uwBlackList.setRiskCode(riskCode);
				uwBlackList.setSex(sex);
				flag="1";
				uwBlackListService.updateBlackList(uwBlackList);
			}
			inputDateRC=new Date();
			message="success";
		}else  if("delete".equals(type)){
			String param = this.getRequest().getParameter("param");
			QueryRule queryRule  =QueryRule.getInstance();
			queryRule.addEqual("id.blackListType", "E");
			queryRule.addEqual("id.blackListCode", param);
			List<UwBlackList> list = uwBlackListService.getUwBlackList(queryRule);
			if(list!=null&&list.size()>0){
				UwBlackList  uwBlackList=	list.get(0);
				uwBlackList.setRemark("已经删除");
				uwBlackList.setCheatDate(new Date());
				uwBlackList.setCheatMeans(prpDuserDto.getUserName());
				uwBlackListService.updateBlackList(uwBlackList);
			}
			flag="1";
			inputDateRC=new Date();
			message="delete";
		}else  if("new".equals(type)){
			inputDateRC=new Date();
			message="new";
		}else  if("queryCode".equals(type)){
			String bankCode = this.getRequest().getParameter("LastBankCode");
			String bankName="";
			// 定义list对象向页面返回银行名称
			PrpDBankInfo prpDBankInfo = uwBlackListService.queryBankInfo(bankCode);
			if(prpDBankInfo!=null){
				bankName=prpDBankInfo.getBankName();
			}
			this.writeJSONMsg(bankName);
			return NONE;
		}
		return message;
		
	}

	public UwBlackListService getUwBlackListService() {
		return uwBlackListService;
	}

	public void setUwBlackListService(UwBlackListService uwBlackListService) {
		this.uwBlackListService = uwBlackListService;
	}


	public String getInsuredType() {
		return insuredType;
	}

	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getBlackListType() {
		return blackListType;
	}

	public void setBlackListType(String blackListType) {
		this.blackListType = blackListType;
	}

	public String getBlackListCode() {
		return blackListCode;
	}

	public void setBlackListCode(String blackListCode) {
		this.blackListCode = blackListCode;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getInsuredEName() {
		return insuredEName;
	}

	public void setInsuredEName(String insuredEName) {
		this.insuredEName = insuredEName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressEName() {
		return addressEName;
	}

	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddressCName() {
		return addressCName;
	}

	public void setAddressCName(String addressCName) {
		this.addressCName = addressCName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBankCode1() {
		return bankCode1;
	}

	public void setBankCode1(String bankCode1) {
		this.bankCode1 = bankCode1;
	}

	public String getBankCode2() {
		return bankCode2;
	}

	public void setBankCode2(String bankCode2) {
		this.bankCode2 = bankCode2;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getInsuredIdvNote() {
		return insuredIdvNote;
	}

	public void setInsuredIdvNote(String insuredIdvNote) {
		this.insuredIdvNote = insuredIdvNote;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public List getBlackMaintenanceList() {
		return blackMaintenanceList;
	}

	public void setBlackMaintenanceList(List blackMaintenanceList) {
		this.blackMaintenanceList = blackMaintenanceList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getInputDateRC() {
		return inputDateRC;
	}

	public void setInputDateRC(Date inputDateRC) {
		this.inputDateRC = inputDateRC;
	}

	public String getCheatMeans() {
		return cheatMeans;
	}

	public void setCheatMeans(String cheatMeans) {
		this.cheatMeans = cheatMeans;
	}

}
