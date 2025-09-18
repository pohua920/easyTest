package com.tlg.xchg.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.xchg.entity.UndwrtIpb902i;

public interface UndwrtIpb902iDao extends IBatisBaseDao<UndwrtIpb902i, BigDecimal> {
	

	
	public List<UndwrtIpb902i> selectByDistinctIdno() throws Exception;
	
	/**
	 * 更新上傳時間
	 * 注意，需配合SqlMap的update條件
	 * @param entity
	 * @return
	 */
	public boolean updateSendtime(UndwrtIpb902i entity);
}