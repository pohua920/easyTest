package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps050DetailResultVo;
import com.tlg.aps.vo.Aps050ResultVo;
import com.tlg.aps.vo.ReturnPolicyNoToG10Vo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.CustomerDao;
import com.tlg.msSqlMob.entity.Customer;

public class CustomerDaoImpl extends IBatisBaseDaoImpl<Customer, BigDecimal> implements CustomerDao {
	
	@Override
	public String getNameSpace() {
		return "Customer";
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	@Override
	public List<Aps050ResultVo> selectForAps050(Map params) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps050",params);
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public int countForAps050(Map params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps050", params);
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Override
	public List<Aps050DetailResultVo> selectForAps050Detail(Map params) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps050Detail",params);
	}
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 START*/
	@Override
	public List<ReturnPolicyNoToG10Vo> returnPolicyNoToG10(Map<String, String> params) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".returnPolicyNoToG10",params);
	}
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 END*/

}