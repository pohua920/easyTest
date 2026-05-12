package com.sinosoft.app.common.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.app.common.CodeConstants;

import ins.framework.exception.BusinessException;
import ins.framework.web.Struts2Action;

public class PubToolsAction extends Struts2Action {
	private static final long serialVersionUID = 1L;

	private String fileName;

	public String downloadFileTemplate() throws Exception {
		String path = "";
		ActionContext ac = ActionContext.getContext();
		ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);

		if ("PlanTemplate".equals(fileName)) {
			path = sc.getRealPath(CodeConstants.Template.PLANDATA_TEPLATE);
			getResponse().setHeader("content-disposition", "attachment;filename=" + CodeConstants.Template.PLANTEMP_NAME);
		} else if ("KPIDataTemplate".equals(fileName)) {
			path = sc.getRealPath(CodeConstants.Template.KPIDATA_TEPLATE);
			getResponse().setHeader("content-disposition", "attachment;filename=" + CodeConstants.Template.KPIDATATEMP_NAME);
		} else if ("KPITemplate".equals(fileName)) {
			path = sc.getRealPath(CodeConstants.Template.KPI_TEPLATE);
			getResponse().setHeader("content-disposition", "attachment;filename=" + CodeConstants.Template.KPITEMP_NAME);
		} else {
			throw new BusinessException("²»´æÔÚÔ“Ä£°å", false);
		}

		OutputStream outp = null;
		FileInputStream in = null;
		try {
			outp = super.getResponse().getOutputStream();
			in = new FileInputStream(new File(path));

			byte[] b = new byte[1024];
			int i = 0;

			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
			if (outp != null) {
				outp.close();
				outp = null;
			}
		}
		return NONE;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
