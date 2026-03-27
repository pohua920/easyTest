package com.sinosoft.claim.common.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.schema.model.PrpLhospital;
import com.sinosoft.claim.schema.service.facade.PrpLhospitalService;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

/**
 * @author 中科软 医院自动填充功能
 */
@SuppressWarnings("serial")
public class AutoHospitalAction extends Struts2Action {

	/** 医院表接口 */
	private PrpLhospitalService prpLhospitalService;
	/** 医院代码 */
	private String hospitalCode;
	/** 医院名称 */
	private String hospitalName;

	/**
	 * 医院信息自动带出处理
	 * @return
	 * @throws Exception
	 */
	public String autoHospital() throws Exception {
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/HTML");
		String result = "";
		try {
			long count = prpLhospitalService.findCount(hospitalCode, hospitalName);
			int pageNo = 1;
			int pageSize = 20;
			if (count > 10) {
				if (hospitalCode != null && !"".equals(hospitalCode)) {
					for (int i = hospitalCode.length(); i > 0; i--) {
						if (i * pageSize <= count) {
							pageNo = i;
							break;
						}
					}
				} else if (hospitalName != null && !"".equals(hospitalName)) {
					for (int i = hospitalName.length(); i > 0; i--) {
						if (i * pageSize <= count) {
							pageNo = i;
							break;
						}
					}
				} else if (hospitalCode != null && !"".equals(hospitalCode) && hospitalName != null && !"".equals(hospitalName)) {
					for (int i = hospitalCode.length() + hospitalName.length(); i > 0; i--) {
						if (i * pageSize <= count) {
							pageNo = i;
							break;
						}
					}
				}
			}
			if (pageNo < 1) {
				pageNo = 1;
			}
			Page page = prpLhospitalService.findPrpLhospital(hospitalCode, hospitalName, pageNo, pageSize);
			List<PrpLhospital> list = page.getResult();
			StringBuffer sb = new StringBuffer("[");
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					PrpLhospital prpLhospital = list.get(i);
					sb.append("{'hospitalCode':'" + prpLhospital.getHospitalCode() + "','hospitalName':'" + prpLhospital.getHospitalName() + "'},");
				}
				result = sb.substring(0, sb.length() - 1) + "]";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(result);
		return NONE;
	}

	/**
	 * 医院信息校验
	 * @return
	 * @throws Exception
	 */
	public String verificationHospital() throws Exception {
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/HTML");
		long count = prpLhospitalService.getCount(hospitalCode, hospitalName);
		if (count > 0) {
			response.getWriter().print("true");
		} else {
			response.getWriter().print("false");
		}
		return NONE;
	}

	public PrpLhospitalService getPrpLhospitalService() {
		return prpLhospitalService;
	}

	public void setPrpLhospitalService(PrpLhospitalService prpLhospitalService) {
		this.prpLhospitalService = prpLhospitalService;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
}
