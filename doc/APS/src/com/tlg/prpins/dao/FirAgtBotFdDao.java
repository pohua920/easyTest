package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps057ResultVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.util.PageInfo;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
public interface FirAgtBotFdDao extends IBatisBaseDao<FirAgtBotFd, BigDecimal> {
	
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 start */
	public List<Aps057ResultVo> selectForAps057(PageInfo pageInfo) throws Exception;
	
	public int countForAps057(Map<String, String> params) throws Exception;
	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 end */
}