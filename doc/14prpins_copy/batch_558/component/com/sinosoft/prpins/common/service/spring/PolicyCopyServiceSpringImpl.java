/**
 * 
 */
package com.sinosoft.prpins.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.prpins.common.service.facade.PolicyCopyService;

/**
 * @功能：保单复制Service服务接口实现
 * 		<p>主要包括：保单复制Service服务接口实现</p>
 * @作者：陶宇杰
 * @日期：2010-01-20
 *
 */
public class PolicyCopyServiceSpringImpl extends GenericDaoHibernate<Serializable, Serializable> implements
		PolicyCopyService {

	/** 日志 */
	private static Log log = LogFactory.getLog(PolicyCopyServiceSpringImpl.class);
	
	/**
	 * 通过险种代码获取险种名称
	 * 
	 * @param riskCode 险种代码
	 * @return riskCname 险种名称
	 * 
	 * @作者：
	 * @日期：2010-01-20
	 * @修改记录：
	 */
	public static String getRisk(String riskCode){
		
		//初始化险种名称
		String riskCname = "";
		//设置险种名称
		riskCname = riskCode;

		return riskCname;
	}

}
