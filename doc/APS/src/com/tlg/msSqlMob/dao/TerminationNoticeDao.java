package com.tlg.msSqlMob.dao;

import com.tlg.aps.vo.mob.fetPolicy.TerminationNoticeVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.TerminationNotice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 */
public interface TerminationNoticeDao extends IBatisBaseDao<TerminationNotice, BigDecimal>{
	public List<TerminationNoticeVo> selectForCancel(Map params)throws Exception;
	public List<TerminationNoticeVo> selectForUnpaid1(Map params)throws Exception;
	public List<TerminationNoticeVo> selectForUnpaid2(Map params)throws Exception;
}