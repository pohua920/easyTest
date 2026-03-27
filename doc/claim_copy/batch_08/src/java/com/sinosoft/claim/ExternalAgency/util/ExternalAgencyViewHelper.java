package com.sinosoft.claim.ExternalAgency.util;

import ins.framework.common.Page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.ExternalAgency.service.facade.ExternalagencyService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLexternalAgency;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.log.Logger;

/**
 * Description:理赔节点状态ViewHelper类，在该类中完成页面数据的整理 查询、处理公估信息
 * @author 中科软
 */
public class ExternalAgencyViewHelper {
	/** Log日志对象 */
	private static Logger log = Logger.getLogger(ExternalAgencyViewHelper.class.getName());

	/**
	 * 默认构造方法
	 */
	public ExternalAgencyViewHelper() {
	}

	/** 外部机构服务 */
	private ExternalagencyService externalagencyService;

	public ExternalagencyService getExternalagencyService() {
		return externalagencyService;
	}

	public void setExternalagencyService(ExternalagencyService externalagencyService) {
		this.externalagencyService = externalagencyService;
	}

	/**
	 * @param 公估处理搜索结果
	 * @param pageNo
	 * @param recordPerPage
	 * @throws Exception
	 */
	public Page externalAgencyDtoToView(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		// 得到页面参数
		String comcode = StringUtils.rightTrim(httpServletRequest.getParameter("ComCode")); // 公估代码
		String comtype = StringUtils.rightTrim(httpServletRequest.getParameter("ComType")); // 公估类型
		String validstatus = StringUtils.rightTrim(httpServletRequest.getParameter("Validstatus"));// 是否有效
		String createtime = StringUtils.rightTrim(httpServletRequest.getParameter("CreateTime"));// 创建时间
		String comcname = StringConvert.getParam(httpServletRequest, "ComCName", ConstantCodes.YUI_CHARSET);// 前台yui应用的是UTF-8，需要特殊处理
		// 得到页面选择查询情况 * or = 再组合SQL
		String comcodeSign = httpServletRequest.getParameter("ComCodeSign");
		String comtypeSign = httpServletRequest.getParameter("ComTypeSign");
		String createtimeSign = httpServletRequest.getParameter("CreateTimeSign");
		String comcnameSign = httpServletRequest.getParameter("ComCNameSign");
		String conditions = " 1=1 ";
		conditions += StringConvert.convertString("comcode", comcode, comcodeSign);
		conditions += StringConvert.convertString("comtype", comtype, comtypeSign);
		conditions += StringConvert.convertString("validstatus", validstatus, comtypeSign);
		conditions += StringConvert.convertString("createtime", createtime, createtimeSign);
		conditions += StringConvert.convertString("comcname", comcname, comcnameSign);

		log.info("start to search,please waiting ...");

		Page page = this.externalagencyService.findByConditions(conditions, pageNo, recordPerPage);
		return page;

	}

	public PrpLexternalAgency viewToDto(HttpServletRequest httpServletRequest) throws Exception {

		PrpLexternalAgency prplexternalagency = new PrpLexternalAgency();
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String creatorcode = user.getUserCode();

		String comcode = StringUtils.rightTrim(httpServletRequest.getParameter("ComCode")); // 公估代码
		String comtype = StringUtils.rightTrim(httpServletRequest.getParameter("ComType")); // 公估类型
		String comcname = StringUtils.rightTrim(httpServletRequest.getParameter("ComCName")); // 中文名称
		String comename = StringUtils.rightTrim(httpServletRequest.getParameter("ComEName")); // 英文名称
		String juridicalperson = StringUtils.rightTrim(httpServletRequest.getParameter("JuridicalPerson"));// 法人
		String address = StringUtils.rightTrim(httpServletRequest.getParameter("Address"));// 地址
		String telephone = StringUtils.rightTrim(httpServletRequest.getParameter("Telephone"));// 联系电话
		String faxno = StringUtils.rightTrim(httpServletRequest.getParameter("FaxNo"));// 传真
		String webaddress = StringUtils.rightTrim(httpServletRequest.getParameter("WebAddress"));// 公司地址
		String linkername = StringUtils.rightTrim(httpServletRequest.getParameter("LinkerName"));// 联系人
		String linkernametel = StringUtils.rightTrim(httpServletRequest.getParameter("LinkerNameTel"));// 联系人电话
		String linkeremail = StringUtils.rightTrim(httpServletRequest.getParameter("LinkerEMail")); // 联系人E-Mail
		String specialty = StringUtils.rightTrim(httpServletRequest.getParameter("Specialty")); // 专长
		String createtime = StringUtils.rightTrim(httpServletRequest.getParameter("CreateTime"));// 创建时间
		String validstatus = StringUtils.rightTrim(httpServletRequest.getParameter("Validstatus"));// 是否有效
		String remark = StringUtils.rightTrim(httpServletRequest.getParameter("Remark"));// 备注
		String flag = StringUtils.rightTrim(httpServletRequest.getParameter("Flag"));// 标志位
		String accountcode = StringUtils.rightTrim(httpServletRequest.getParameter("AccountCode"));// 银行帳号
		String bankcode = StringUtils.rightTrim(httpServletRequest.getParameter("prpdpaymentaccountBankCode"));// 总行代码
		String bankname = StringUtils.rightTrim(httpServletRequest.getParameter("prpdpaymentaccountBankName"));// 总行名称
		String custombankcode = StringUtils.rightTrim(httpServletRequest.getParameter("prpdpaymentaccountCustomBankCode"));// 分行代码
		String custombankname = StringUtils.rightTrim(httpServletRequest.getParameter("prpdpaymentaccountCustomBankName"));// 分行名称
		String certificatecode = StringUtils.rightTrim(httpServletRequest.getParameter("CertifiCateCode"));// 帳号归属人证件号码
		String ownername = StringUtils.rightTrim(httpServletRequest.getParameter("OwnerName"));// 帳号归属人名称
		String ownerphoneno = StringUtils.rightTrim(httpServletRequest.getParameter("PhoneNo"));// 帳号归属人电话
		String accounttype = StringUtils.rightTrim(httpServletRequest.getParameter("AccountType"));// 帳户类型
		String accountcurrency = StringUtils.rightTrim(httpServletRequest.getParameter("AccountCurrency"));// 帳户币别
		String agentNo = StringUtils.rightTrim(httpServletRequest.getParameter("agentNo"));// 代理商NO
		String postCode = StringUtils.rightTrim(httpServletRequest.getParameter("postCode"));// POBOX
		String vestingCom = StringUtils.rightTrim(httpServletRequest.getParameter("vestingCom"));// 歸屬公司
		String countryType = StringUtils.rightTrim(httpServletRequest.getParameter("countryType"));// 國別
		String areaCode = StringUtils.rightTrim(httpServletRequest.getParameter("areaCode"));// 地區別
		String address2 = StringUtils.rightTrim(httpServletRequest.getParameter("address2"));// 地址2
		String editType = httpServletRequest.getParameter("editType");
		if (editType != null && editType.equals("insertSave")) {// 如果是新增
			int comcode1 = 0;
			String[] inputTime = createtime.split("-");
			String validDate = inputTime[0] + inputTime[1];
			String conditions = "comcode like '" + comtype + validDate + "%" + "'" + " order by comcode Desc";
			List<?> prplexternalagencyList = new ArrayList<PrpLexternalAgency>();
			prplexternalagencyList = externalagencyService.findByConditions(conditions, 1, 10).getResult();

			if (prplexternalagencyList != null && prplexternalagencyList.size() > 0) {
				PrpLexternalAgency tempObject = (PrpLexternalAgency) prplexternalagencyList.get(0);
				comcode1 = Integer.parseInt(tempObject.getId().getComCode().substring(7, 11));
				comcode1 = comcode1 + 1;

				if (comcode1 < 9) {
					comcode = comtype + validDate + "000" + comcode1;
				} else if (comcode1 >= 9 && comcode1 < 99) {
					comcode = comtype + validDate + "00" + comcode1;
				} else if (comcode1 >= 99 && comcode1 < 999) {
					comcode = comtype + validDate + "0" + comcode1;
				} else if (comcode1 >= 999){
					comcode = comtype + validDate + comcode1;
				}
			} else {
				comcode = comtype + validDate + "0001";
			}
		}

		prplexternalagency.getId().setComCode(comcode);
		prplexternalagency.getId().setComtype(comtype);
		prplexternalagency.setComcname(comcname);
		prplexternalagency.setComename(comename);
		prplexternalagency.setJuridicalperson(juridicalperson);
		prplexternalagency.setAddress(address);
		prplexternalagency.setTelephone(telephone);
		prplexternalagency.setFaxno(faxno);
		prplexternalagency.setWebaddress(webaddress);
		prplexternalagency.setLinkerName(linkername);
		prplexternalagency.setLinkernametel(linkernametel);
		prplexternalagency.setLinkeremail(linkeremail);
		prplexternalagency.setSpecialty(specialty);
		prplexternalagency.setCreatorcode(creatorcode);
		prplexternalagency.setCreatetime(new DateTime(createtime));
		prplexternalagency.setValidDate(new DateTime("2000-1-1"));
		prplexternalagency.setInvaliddate(new DateTime("2050-1-1"));
		prplexternalagency.setValidStatus(validstatus);
		prplexternalagency.setRemark(remark);
		prplexternalagency.setFlag(flag);
		prplexternalagency.setAccountCode(accountcode);
		prplexternalagency.setBankCode(bankcode);
		prplexternalagency.setBankName(bankname);
		prplexternalagency.setCustomBankCode(custombankcode);
		prplexternalagency.setCustomBankName(custombankname);
		prplexternalagency.setCertifiCateCode(certificatecode);
		prplexternalagency.setOwnerName(ownername);
		prplexternalagency.setOwnerPhoneNo(ownerphoneno);
		prplexternalagency.setAccountType(accounttype);
		prplexternalagency.setAccountCurrency(accountcurrency);
		prplexternalagency.setAgentNo(agentNo);
		prplexternalagency.setPostCode(postCode);
		prplexternalagency.setVestingCom(vestingCom);
		prplexternalagency.setCountryType(countryType);
		prplexternalagency.setAreaCode(areaCode);
		prplexternalagency.setAddress2(address2);
		httpServletRequest.setAttribute("prplexternalagency", prplexternalagency);
		return prplexternalagency;
	}

	public PrpLexternalAgency externalagencyShow(HttpServletRequest httpServletRequest, int pageNo, int pageSize) throws Exception {
		PrpLexternalAgency prplexternalagency = new PrpLexternalAgency();
		String comcode = StringUtils.rightTrim(httpServletRequest.getParameter("comCode")); // 公估代码
		String comtype = StringUtils.rightTrim(httpServletRequest.getParameter("comtype")); // 公估类型
		prplexternalagency = externalagencyService.findByPrimaryKey(comcode, comtype, pageNo, pageSize);
		httpServletRequest.setAttribute("prplexternalagency", prplexternalagency);
		return prplexternalagency;
	}

	public PrpLexternalAgency externalagencyUpdate(HttpServletRequest httpServletRequest) throws Exception {
		PrpLexternalAgency prplexternalagency = new PrpLexternalAgency();
		String strComCode = httpServletRequest.getParameter("comCode");
		String conditions = "comcode = '" + strComCode + "'";
		List<?> prplexternalagencyList = new ArrayList<PrpLexternalAgency>();
		prplexternalagencyList = externalagencyService.findByConditions(conditions, 1, 10).getResult();
		if (prplexternalagencyList != null && prplexternalagencyList.size() > 0) {
			prplexternalagency = (PrpLexternalAgency) prplexternalagencyList.get(0);
			httpServletRequest.setAttribute("prplexternalagency", prplexternalagency);
		}
		return prplexternalagency;
	}
}
