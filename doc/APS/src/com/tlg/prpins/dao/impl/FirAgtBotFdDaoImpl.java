package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps057ResultVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtBotFdDao;
import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.util.PageInfo;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
public class FirAgtBotFdDaoImpl extends IBatisBaseDaoImpl<FirAgtBotFd, BigDecimal> implements FirAgtBotFdDao {
	@Override
	public String getNameSpace() {
		return "FirAgtBotFd";
	}
	
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 start */
	@Override
	public List<Aps057ResultVo> selectForAps057(PageInfo pageInfo) throws Exception {
		List<Aps057ResultVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps057",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps057(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps057", params);
		return count;
	}
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 end */
}