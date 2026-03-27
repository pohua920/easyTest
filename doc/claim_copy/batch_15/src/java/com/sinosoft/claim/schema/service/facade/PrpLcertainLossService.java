package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.PrpLcetainLoss;
import com.sinosoft.claim.schema.model.PrpLcetainLossId;
/**
 * 定损数据库操作接口
 * @ClassName PrpLcetainLossService
 * @Description 
 * @author 中科软
 */
public interface PrpLcertainLossService {
    /**
     * 插入一条数据
     * @param prpLcertainLossDto prpLcertainLossDto
     * @throws Exception
     */
    public void save(PrpLcetainLoss prpLcetainLoss) throws Exception;
    /**
     * 插入多条数据
     * @param collection collection
     * @throws Exception
     */
    public void save(List<PrpLcetainLoss> list) throws Exception;

    /**
     * 按主键删除一条数据
     * @param registNo 报案号
     * @param lossItemCode 标的序号
     * @throws Exception
     */
    public void delete(PrpLcetainLossId prpLcetainLossId) throws Exception;

    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param prpLcertainLossDto prpLcertainLossDto
     * @throws Exception
     */
    public void update(PrpLcetainLoss prpLcertainLoss) throws Exception;

    /**
     * 按主键查找一条数据
     * @param registNo 报案号
     * @param lossItemCode 标的序号
     * @return PrpLcertainLossDto
     * @throws Exception
     */
    public PrpLcetainLoss findPrpLcetainLoss(PrpLcetainLossId prpLcetainLossId) throws Exception;
    /**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的定损主表页面信息
     */
    public Page findPrpLcetainLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection
     * @throws Exception
     */
    public List<PrpLcetainLoss> findPrpLcetainLoss(QueryRule queryRule) throws Exception;
    
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection
     * @throws Exception
     */
    public List<PrpLverifyLoss> findPrpLcetainLoss(String conditions,int pageNo,int pageSize) throws Exception;   
    
	
}
