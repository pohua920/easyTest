package com.sinosoft.undwrt.undwrtBase.service.facade;

import java.util.Collection;

import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.undwrt.undwrtBase.model.WfOtherFees;

/**
 * The Interface WfOtherFeesService.
 */
public interface WfOtherFeesService {

    /**
	 * 插入一条数据.
	 * 
	 * @param wfotherfees
	 *            the wfotherfees
	 * @throws Exception
	 *             異常
	 */
    public void insert(WfOtherFees wfotherfees) throws Exception;

    /**
	 * 按主键删除一条数据.
	 * 
	 * @param businessno
	 *            BUSINESSNO
	 * @param serialno
	 *            SERIALNO
	 * @param lineno
	 *            LINENO
	 * @throws Exception
	 *             異常
	 */
    public void delete(String businessno,int serialno,int lineno) throws Exception;

    /**
	 * 按条件删除数据.
	 * 
	 * @param conditions
	 *            删除条件
	 * @throws Exception
	 *             異常
	 */
    public void deleteByConditions(String conditions) throws Exception;

    /**
	 * 按主键更新一条数据(主键本身无法变更).
	 * 
	 * @param wfotherfees
	 *            the wfotherfees
	 */
    public void update(WfOtherFees wfotherfees);

    /**
	 * 按主键查找一条数据.
	 * 
	 * @param businessno
	 *            業務號
	 * @param serialno
	 *            序列號
	 * @param lineno
	 *            行號
	 * @return wfotherfeesDto wfotherfeesDto
	 * @throws Exception
	 *             異常
	 */
    public WfOtherFees findByPrimaryKey(String businessno,int serialno,int lineno) throws Exception;

    /**
	 * 按条件查询多条数据.
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页号
	 * @param rowsPerPage
	 *            每页的行数
	 * @return PageRecord 查询的一页的结果
	 * @throws Exception
	 *             異常
	 */
    public PageRecord findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;

    /**
	 * 按条件查询多条数据.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return Collection 包含wfotherfeesDto的集合
	 * @throws Exception
	 *             異常
	 */
    public Collection findByConditions(String conditions) throws Exception;

    /**
	 * 查询满足模糊查询条件的记录数.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 *             異常
	 */
    public int getCount(String conditions) throws Exception;
}
