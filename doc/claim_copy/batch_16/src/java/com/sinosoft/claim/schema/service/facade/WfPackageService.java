package com.sinosoft.claim.schema.service.facade;
/**
 * 工作流日志业务信息表接口
 * @author 中科软
 */
import java.util.List;

import com.sinosoft.claim.schema.model.WfPackage;
import com.sinosoft.claim.schema.model.WfPackageId;
import com.sinosoft.sysframework.exceptionlog.UserException;

public interface WfPackageService {
	/**
	 * 保存工作流日志业务信息
	 * @param wfPackage ：传入的工作流日志业务
	 */
    public void save(WfPackage wfPackage);
    /**
     * 创建工作流包信息表
     *
     * @param int iModelno 模版号
     * @param String iBusinessno 业务号 throws UserException,Exception
     */
    public String create( int iModelNo, String iCertiType, String iBusinessNo, String iComCode)
            throws UserException, Exception ;

	
	/**
	 * 保存工作流日志业务信息
	 * @param list:保存工作流日志业务信息
	 */
	public void save(List<WfPackage> list) throws Exception;
	
	/**
	 * 删除工作流日志业务信息
	 * @param wfPackageId ：传入的工作流日志业务编号
	 */
	public void delete(WfPackageId wfPackageId) throws Exception;

	/**
	 * 更新工作流日志业务信息
	 * @param wfPackage :传入需要更新的工作流日志业务
	 */
	public void update(WfPackage wfPackage) throws Exception;

	/**
	 * 根据工作流日志业务编号查询出工作流日志业务信息
	 * @param wfPackageId ：传入的工作流日志业务编号
	 * @return 返回工作流日志业务
	 */
	public WfPackage findWfPackage(WfPackageId wfPackageId) throws Exception;
}
