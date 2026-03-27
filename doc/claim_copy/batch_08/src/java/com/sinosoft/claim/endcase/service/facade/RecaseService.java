package com.sinosoft.claim.endcase.service.facade;

import java.sql.SQLException;
import java.util.Collection;

import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 重开赔案处理接口
 * @author 中科软
 *
 */
public interface RecaseService {
	/**
	 * 重开赔案保存带工作流的
	 * @param recaseDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void save(ReCaseDto recaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/**
	 * 重开赔案保存
	 * @param recaseDto
	 * @throws Exception
	 */
	public void save(ReCaseDto recaseDto) throws SQLException, Exception;

	/**
	 * 根据主键查询
	 * @param claimNo
	 * @param serialNo
	 * @return
	 * @throws Exception
	 */
	public ReCaseDto findByPrimaryKey(String claimNo, int serialNo) throws SQLException, UserException, Exception;

	/**
	 * 根据条件查询重开赔案信息集合
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public Collection<?> findByConditions(String conditions) throws SQLException, Exception;

	public int getMaxSerialNo(String claimNo) throws SQLException, Exception;

	/**
	 * 重开赔案保存,保存jbpm工作流信息
	 * @param business
	 * @param recaseDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm(String business, ReCaseDto recaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
	/**
	 * 判断是否重开赔案
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public boolean isRecase(String claimNo) throws Exception;
}
