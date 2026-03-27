package cn.com.sinosoft.intf.image.web;

import ins.framework.web.Struts2Action;

import java.io.IOException;

import cn.com.sinosoft.intf.image.common.ImageServiceUtil;
import cn.com.sinosoft.intf.image.model.vo.BASE_DATA;
import cn.com.sinosoft.intf.image.model.vo.META_DATA;

public class ImageMangeAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String reqXml;
	private BASE_DATA base_data;
	private META_DATA meta_data;
	//业务编号
	private String busi_num;
	
	public void getRequestXml(){
		base_data = new BASE_DATA();
		meta_data = new META_DATA();
		base_data.setAPP_TYPE("dms");
		base_data.setOP_ID((String) getSession().getAttribute("UserCode"));
		base_data.setOP_RIGHT("C001");
		//业务代码，这里是文件号
		
		meta_data.setBUSI_NUM(busi_num);
		//业务类型
		meta_data.setAPP_CODE("UW");
		ImageServiceUtil imageServiceUtil = new ImageServiceUtil();
		reqXml = imageServiceUtil.dtoToXML(base_data, meta_data);
		try {
			this.getResponse().getWriter().print(reqXml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getReqXml() {
		return reqXml;
	}

	public void setMeta_data(META_DATA meta_data) {
		this.meta_data = meta_data;
	}


	public BASE_DATA getBase_data() {
		return base_data;
	}

	public void setBase_data(BASE_DATA base_data) {
		this.base_data = base_data;
	}

	public String getBusi_num() {
		return busi_num;
	}

	public void setBusi_num(String busi_num) {
		this.busi_num = busi_num;
	}


	public META_DATA getMeta_data() {
		return meta_data;
	}


	public void setReqXml(String reqXml) {
		this.reqXml = reqXml;
	}
	
}
