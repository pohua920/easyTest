package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.prpins.entity.UtiRecorder;
import com.tlg.util.PageInfo;

public interface UtiRecorderDao {
	
	public String getNameSpace();
	/**
	 * 計算資料筆數
	 * @param params
	 * @return
	 */
	public int count(Map params);
	
	public List<UtiRecorder> findByPageInfo(PageInfo pageInfo)throws Exception;
	/**
	 * 依照params條件搜尋
	 * 注意，需配合SqlMap的selectByParam條件
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<UtiRecorder> findByParams(Map params)throws Exception;
	/**
	 * 新增entity
	 * 注意，需配合SqlMap的insert條件
	 * @param entity
	 * @return
	 */
	public BigDecimal insert(UtiRecorder entity);
	/**
	 * 更新oid
	 * 注意，需配合SqlMap的update條件
	 * @param entity
	 * @return
	 */
	public boolean update(UtiRecorder UtiRecorder);
}