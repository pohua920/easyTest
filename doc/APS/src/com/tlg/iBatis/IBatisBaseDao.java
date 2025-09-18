package com.tlg.iBatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tlg.util.PageInfo;

public interface IBatisBaseDao<T extends IBatisBaseEntity<OID> ,OID extends Serializable> {
	/**
	 * 計算資料筆數
	 * @param params
	 * @return
	 */
	public int count(Map params);
	/**
	 * 依照PageInfo條件搜尋
	 * pageInfo.filter 為搜尋條件
	 * pageInfo.StartRow -1 起始頁
	 * pageInfo.PageSize 第幾頁
	 * 注意，搜尋條件需配合SqlMap的select條件
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public List<T> findByPageInfo(PageInfo pageInfo)throws Exception;
	/**
	 * 依照params條件搜尋
	 * 注意，需配合SqlMap的selectByParam條件
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<T> findByParams(Map params)throws Exception;
	/**
	 * 依照oid搜尋
	 * 注意，需配合SqlMap的select條件
	 * @param oid
	 * @return
	 */
	public T findByOid(OID oid);
	/**
	 * 依照UK搜尋
	 * 注意，需配合SqlMap的selectByUK條件
	 * @param oid
	 * @return
	 */
	public T findByUK(Map params);
	/**
	 * 新增entity
	 * 注意，需配合SqlMap的insert條件
	 * @param entity
	 * @return
	 */
	public OID insert(T entity);
	/**
	 * 依照oid刪除
	 * 注意，需配合SqlMap的delete條件
	 * @param oid
	 * @return
	 */
	public boolean remove(OID oid);
	
	/**
	 * 依照entity刪除
	 * 注意，需配合SqlMap的delete條件
	 * @param oid
	 * @return
	 */
	public boolean remove(T entity);	
	/**
	 * 更新oid
	 * 注意，需配合SqlMap的update條件
	 * @param entity
	 * @return
	 */
	public boolean update(T entity);
	/**
	 * 依照entity內uk搜尋，若搜尋筆數為>1則表示不是唯一
	 * 注意，需配合SqlMap的countUnit條件
	 * @param entity
	 * @return true(唯一)
	 */
	public boolean isUnique(T entity);
}
