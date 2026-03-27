package com.sinosoft.claim.schema.service.facade;
/**
 * 伤情信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;
import com.sinosoft.claim.schema.model.PrpLpersonWound;
import com.sinosoft.claim.schema.model.PrpLpersonWoundId;

public interface PrpLpersonWoundService {
    /**
     * 插入一条数据
     * @param prpLpersonWoundDto 伤情信息
     * @throws Exception
     */
    public void save(PrpLpersonWound prpLpersonWound) throws Exception;
    /**
     * 采用批方式插入多条数据
     * @param collection collection
     * @throws Exception
     */
    public void save(List<PrpLpersonWound> list) throws Exception;
    /**
     * 按主键删除一条数据
     * @param registNo 报案号
     * @param serialNo 序号
     * @param personNo 人员序号
     * @throws Exception
     */
    public void delete(PrpLpersonWoundId prpLpersonWoundId) throws Exception;
    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param 伤情信息 
     * @throws Exception
     */
    public void update(PrpLpersonWound prpLpersonWound) throws Exception;
    /**
     * 按主键查找一条数据
     * @param registNo 报案号
     * @param serialNo 序号
     * @param personNo 人员序号
     * @return PrpLpersonWoundDto
     * @throws Exception
     */
    public PrpLpersonWound findPrpLpersonWound(PrpLpersonWoundId prpLpersonWoundId) throws Exception;
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public Page findPrpLpersonWound(QueryRule queryRule,int pageNo,int rowsPerPage) throws Exception;
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection
     * @throws Exception
     */
    public List<PrpLpersonWound> findPrpLpersonWound(QueryRule queryRule) throws Exception;

}
