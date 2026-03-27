package com.sinosoft.claim.common.service.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.UtiCodeTransfer;
/**
 * 险种险类代码对照表接口
 */
public interface UtiCodeTransferService {
    /**
     * 插入数据
     * @param utiCodeTransfer  险种险类代码对照表
     * @throws Exception
     */
    public void insert(UtiCodeTransfer utiCodeTransfer)throws Exception;
    /**
     * 批量插入数据
     * @param collection 险种险类代码对照表集合
     * @throws Exception
     */
    public void insertAll(Collection<?> collection)throws Exception;
    /**
     * 更具险类代码删除
     * @param configCode 险类代码
     * @throws Exception
     */
    public void delete(String configCode)throws Exception;
    /**
     * 更新代码
     * @param utiCodeTransfer 险种险类代码
     * @throws Exception
     */
    public void update(UtiCodeTransfer utiCodeTransfer)throws Exception;
    /**
     * 更具险类代码查询
     * @param configCode 险类代码
     * @return
     * @throws Exception
     */
    public UtiCodeTransfer findByPrimaryKey(String configCode)throws Exception;
    /**
     * 查询险类代码表
     * @param conditions 查询条件
     * @param pageNo 开始页数
     * @param rowsPerPage 没有显示条数
     * @return
     * @throws Exception
     */
    public Collection<?> findByConditions(String conditions,int pageNo,int rowsPerPage)throws Exception;
    /**
     * 查询险类代码表
     * @param conditions 查询条件
     * @return
     * @throws Exception
     */
    public List<UtiCodeTransfer> findByConditions(String conditions)throws Exception;
    /**
     * 根据条件删除
     * @param conditions 查询条件
     * @return
     * @throws Exception
     */
    public int deleteByConditions(String conditions)throws Exception;
    /**
     * 根据条件查询条数
     * @param conditions 查询条件
     * @return
     * @throws Exception
     */
    public int getCount(String conditions) throws Exception;
    /**更具险别，查询险种险类代码对照表
	 * @param riskCode 险别
	 * @return 返回险别对象
	 * @throws Exception
	 */
	public UtiCodeTransfer findUtiCodeTransfer(String riskCode)throws Exception;
	/**
	 * 查询险类类表
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> findRiskType() throws Exception;
}
