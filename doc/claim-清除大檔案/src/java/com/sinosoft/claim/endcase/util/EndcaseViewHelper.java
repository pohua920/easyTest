package com.sinosoft.claim.endcase.util;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.EndcaseService;
import com.sinosoft.claim.endcase.service.facade.RecaseService;
import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.schema.model.PrpLcaseNo;
import com.sinosoft.claim.schema.model.PrpLcaseNoId;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLltextService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;

/**
 * <p>
 * Title: EndcaseViewHelper
 * </p>
 * <p>
 * Description:结案ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */
public abstract class EndcaseViewHelper {
	/** 显示文本每行最大显示的字符长度 */
	private int RULE_LENGTH = 70; // rule字段的长度
	/** 结案服务 */
	private EndcaseService endcaseService;
	/** 重开赔案服务 */
	private RecaseService recaseService;
	/** 重开赔案信息服务 */
	private PrpLrecaseService prpLrecaseService;
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	private PrpLltextService prpLltextService;
	/**
	 * 默认构造方法
	 */
	public EndcaseViewHelper() {
	}

	/**
	 * 保存结案时整理结案页面数据. 整理采用继承的方式分层处理。
	 * @param httpServletRequest
	 * @return stepFlag 标志
	 * @throws Exception
	 */
	public EndcaseDto viewToDto(HttpServletRequest httpServletRequest, boolean stepFlag) throws Exception {
		EndcaseDto endcaseDto = new EndcaseDto();

		/*---------------------赔案号表prpLcaseno------------------------------------*/
		String policyNo = (String) httpServletRequest.getParameter("prpLclaimPolicyNo");
		String claimNo = (String) httpServletRequest.getAttribute("claimNo");
		String caseNo = (String) httpServletRequest.getAttribute("caseNo");
		if (caseNo != null) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("claimNo", claimNo.trim());
			ArrayList<PrpLcaseNo> prpLperpayList = new ArrayList<PrpLcaseNo>();
			ArrayList<PrpLcompensate> arraylist = (ArrayList<PrpLcompensate>) getPrpLcompensateService().findPrpLcompensate(queryRule);
			if (arraylist != null) {
				for (int i = 0; i < arraylist.size(); i++) {
					PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
					PrpLcaseNoId prpLcaseNoId = new PrpLcaseNoId();
					PrpLcompensate prpLcompensate = new PrpLcompensate();
					prpLcompensate = (PrpLcompensate) arraylist.get(i);
					prpLcaseNoId.setCaseNo(caseNo.trim());
					prpLcaseNoId.setCertiNo(prpLcompensate.getCompensateNo());
					prpLcaseNoId.setCertiType("C");
					prpLcaseNo.setId(prpLcaseNoId);
					prpLcaseNo.setFlag("");
					prpLcaseNo.setClaimNo(claimNo.trim());
					prpLperpayList.add(prpLcaseNo);
				}
			}
			endcaseDto.setPrpLcaseNoList(prpLperpayList);
		}

		if (this.isRecase(claimNo.trim()) == false) { // 非重开赔案回写PrpLltextDto
			/*---------------------文本表PrpLltextDto--------------------*/

			/**
			 * 获取结案报告
			 */
			ArrayList<PrpLltext> prpLltextList = new ArrayList<PrpLltext>();
			String textTemp = httpServletRequest.getParameter("prpLltextContextInnerHTML");
			if (textTemp != null) {
				String[] rules = StringUtils.split(textTemp, RULE_LENGTH);
				// 得到连接串,下面将其切分到数组
				for (int k = 0; k < rules.length; k++) {
					PrpLltext prpLltext = new PrpLltext();
					prpLltext.getId().setClaimNo((String) httpServletRequest.getAttribute("claimNo"));
					prpLltext.setContext(rules[k]);
					prpLltext.getId().setLineNo(k + 1);
					prpLltext.getId().setTextType("08");
					prpLltextList.add(prpLltext);
				}
				// prepayDto
				endcaseDto.setPrpLltextList(prpLltextList);

			} else {
				if (stepFlag == true) {
					QueryRule queryRule = QueryRule.getInstance();
					queryRule.addEqual("id.claimNo", claimNo.trim());
					queryRule.addEqual("id.textType", "08");
					endcaseDto.setPrpLltextList(getPrpLltextService().findPrpLltext(queryRule));
				}
			}

		}// end非重开赔案
		if (caseNo != null) {
			/*---------------------状态内容prpLclaimStatus------------------------------------*/
			PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
			prpLclaimStatus.setStatus(httpServletRequest.getParameter("buttonSaveType"));
			prpLclaimStatus.getId().setBusinessNo(caseNo.trim());
			prpLclaimStatus.setPolicyNo(policyNo);
			prpLclaimStatus.setRiskCode(BusinessRuleUtil.getRiskCode(policyNo, "PolicyNo"));
			prpLclaimStatus.getId().setNodeType("endca");
			prpLclaimStatus.getId().setSerialNo(0);
			// 取得当前用户信息，写操作员信息到结案中
			HttpSession session = httpServletRequest.getSession();
			UserDto user = (UserDto) session.getAttribute("user");
			prpLclaimStatus.setHandlerCode(user.getUserCode());
			prpLclaimStatus.setComCode(user.getComCode());
			prpLclaimStatus.setInputDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			endcaseDto.setPrpLclaimStatus(prpLclaimStatus);
		}

		return endcaseDto;
	}

	/**
	 * 判断是否是重开
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public boolean isRecase(String claimNo) throws Exception {
		boolean blnReturn = false; // 为false为无重开 或 重开已结案
		int maxSerialNo = 0;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.claimNo", claimNo);
		Collection<PrpLrecase> list = getPrpLrecaseService().findPrpLrecase(queryRule);
		if (list != null && list.size() > 0) {
			maxSerialNo = getRecaseService().getMaxSerialNo(claimNo);
			ReCaseDto reCaseDto = getRecaseService().findByPrimaryKey(claimNo, maxSerialNo);
			PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
			if (CommonUtils.isEmpty(DataUtils.dbNullToEmpty(prpLrecase.getCloseCaseUserCode())) && prpLrecase.getCloseCaseDate() == null) {
				blnReturn = true; // 有重开，且未结案
			}
		}
		return blnReturn;
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写结案单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public abstract EndcaseDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 填写结案页面及查询结案request的生成.
	 * 填写结案时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param endcaseDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public abstract void dtoToView(HttpServletRequest httpServletRequest, EndcaseDto endcaseDto) throws Exception;

	public EndcaseService getEndcaseService() {
		if (endcaseService == null) {
			endcaseService = (EndcaseService) ServiceFactory.getService("endcaseService");
		}
		return endcaseService;
	}

	public void setEndcaseService(EndcaseService endcaseService) {
		this.endcaseService = endcaseService;
	}

	public RecaseService getRecaseService() {
		if (recaseService == null) {
			recaseService = (RecaseService) ServiceFactory.getService("recaseService");
		}
		return recaseService;
	}

	public void setRecaseService(RecaseService recaseService) {
		this.recaseService = recaseService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		if (prpLcompensateService == null) {
			prpLcompensateService = (PrpLcompensateService) ServiceFactory.getService("prpLcompensateService");
		}
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		if (prpLrecaseService == null) {
			prpLrecaseService = (PrpLrecaseService) ServiceFactory.getService("prpLrecaseService");
		}
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public PrpLltextService getPrpLltextService() {
		if (prpLltextService == null) {
			prpLltextService = (PrpLltextService) ServiceFactory.getService("prpLltextService");
		}
		return prpLltextService;
	}

	public void setPrpLltextService(PrpLltextService prpLltextService) {
		this.prpLltextService = prpLltextService;
	}
}
