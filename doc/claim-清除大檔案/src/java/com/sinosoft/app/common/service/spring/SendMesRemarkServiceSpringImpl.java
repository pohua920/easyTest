package com.sinosoft.app.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.app.common.model.SendMesRemark;
import com.sinosoft.app.common.service.facade.SendMesRemarkService;

public class SendMesRemarkServiceSpringImpl extends GenericDaoHibernate<SendMesRemark, String> implements SendMesRemarkService{

	public void save(SendMesRemark sendMesRemark) throws Exception {
		//保存部门总
		super.save(sendMesRemark);
	}
	//删除数据
	public void delete(){
		String hql = "delete from sendmesremark";
		super.getSession().createSQLQuery(hql).executeUpdate();
	}
}
