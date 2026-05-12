package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLpersonId;
/**
 * 人员伤亡明细信息接口
 * @ClassName PrpLpersonService
 * @Description 
 * @author 中科软
 */
public interface PrpLpersonService {
    /**
     * 插入一条数据
     * @param prpLpersonDto 人员伤亡明细信息
     * @throws Exception
     */
    public void save(PrpLperson prpLperson) throws Exception;
    /**
     * 插入多条数据
     * @param collection collection
     * @throws Exception
     */
    public void save(List<PrpLperson> list) throws Exception;

    /**
     * 按主键删除一条数据
     * @param serialNo 序号
     * @param registNo 报案号
     * @throws Exception
     */
    public void delete(PrpLpersonId prpLpersonId) throws Exception;
    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param prpLperson 人员伤亡明细信息
     * @throws Exception
     */
    public void update(PrpLperson prpLperson) throws Exception;
    /**
     * 按主键查找一条数据
     * @param serialNo 序号
     * @param registNo 报案号
     * @return PrpLpersonDto
     * @throws Exception
     */
    public PrpLperson findPrpLperson(PrpLpersonId prpLpersonId) throws Exception;
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public Page findPrpLperson(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection
     * @throws Exception
     */
    public List<PrpLperson> findPrpLperson(QueryRule queryRule) throws Exception;

}
