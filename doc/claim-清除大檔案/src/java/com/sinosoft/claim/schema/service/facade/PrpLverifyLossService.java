package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLverifyLoss;
/**
 * 定核损信息接口
 * @ClassName PrpLverifyLossService
 * @Description 
 * @author 中科软
 * @date Jan 25, 2013 5:05:25 PM
 */
public interface PrpLverifyLossService {

    /**
     * 插入一条数据
     * @param prpLverifyLossDto prpLverifyLossDto
     * @throws Exception
     */
    public void save(PrpLverifyLoss prpLverifyLoss)throws Exception;
    /**
     * 采用批方式插入多条数据
     * @param collection collection
     * @throws Exception
     */
    public void save(List<PrpLverifyLoss> list) throws Exception;

    /**
     * 按主键删除一条数据
     * @param registNo 报案号码
     * @param lossItemCode 标的序号
     * @throws Exception
     */
    public void delete(String registNo,String lossItemCode,String nodeType) throws Exception;

    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param prpLverifyLossDto prpLverifyLossDto
     * @throws Exception
     */
    public void update(PrpLverifyLoss prpLverifyLoss) throws Exception;

    /**
     * 按主键查找一条数据
     * @param registNo 报案号码
     * @param lossItemCode 标的序号
     * @return PrpLverifyLossDto
     * @throws Exception
     */
    public PrpLverifyLoss findPrpLverifyLoss(String registNo,String lossItemCode,String nodeType)  throws Exception;

    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public Page findPrpLverifyLoss(QueryRule queryRule,int pageNo,int pageSize) throws Exception;

    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection
     * @throws Exception
     */
    public List<PrpLverifyLoss> findPrpLverifyLoss(QueryRule queryRule) throws Exception ;
    /**
     * 按条件从prplverifyLoss表,prplregist表和prplclaimstatus表中查询多条数据
     * @param conditions String
     * @param nodeType String
     * @param pageNo int
     * @param rowsPerPage int
     * @throws Exception
     * @return Collection
     * Modify By sunhao 2004-08-24 Reason:增加车牌号，案件状态，操作时间查询条件，在查询结果中增加案件状态
     */
    public List<PrpLverifyLoss> findByQueryConditions(String conditions,int pageNo,int pageSize) throws Exception;

    /**
     * 按报案号更新数据
     * @param String registNo
     * @throws Exception
     */
    public void update(String registNo) throws Exception;
    /**
     * 查询满足条件的数据条数
     * @param String conditions
     * @throws Exception
     */
    public long getCount(String conditions) throws Exception;
 	/**
	 * @param prpLverifyLoss
	 * @throws Exception
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 */
    public void saveOrUpdate(PrpLverifyLoss prpLverifyLoss) throws Exception;
}
