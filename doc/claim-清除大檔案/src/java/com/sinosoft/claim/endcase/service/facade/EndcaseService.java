package com.sinosoft.claim.endcase.service.facade;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.schema.model.PrpLcaseNo;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 结案处理接口
 * @author 中科软
 *
 */
public interface EndcaseService {
	/**
	 * 保存结案
	 * @param EndcaseDto：结案对象DTO
	 * @throws Exception
	 */
	public void save(EndcaseDto endcaseDto) throws SQLException, Exception;

	/**
	 * 保存结案带工作流
	 * @param EndcaseDto：结案对象DTO
	 * @throws Exception
	 */
	public void save(EndcaseDto endcaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/**
	 * 重开赔案的结案
	 * @param EndcaseDto：结案对象DTO
	 * @throws Exception
	 */
	public void reCaseSave(EndcaseDto endcaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/**
	 * 删除结案
	 * @param caseNo：结案号
	 * @throws Exception
	 */
	public void delete(String caseNo) throws SQLException, Exception;

	/**
	 * 获得结案信息
	 * @param caseNo：结案号
	 * @return 结案对象
	 * @throws Exception
	 */
	public EndcaseDto findByPrimaryKey(String caseNo, String claimNo, String certiNo, String certiType) throws SQLException, UserException, Exception;

	/**
	 * 判断结案号是否存在
	 * @param caseNo:结案号
	 * @return 是/否
	 * @throws Exception
	 */
	public boolean isExist(String caseNo, String claimNo, String certiNo, String certiType) throws SQLException, Exception;

	/**
	 * 获得结案信息
	 * @param conditions：查询条件
	 * @return 结案对象
	 * @throws Exception
	 */

	public Collection<?> findByConditions(String conditions) throws SQLException, Exception;

	/**
	 * 获得结案查询信息
	 * @param conditions：查询条件
	 * @return 报案对象
	 * @throws Exception Add By sunhao 2004-08-24 Reason:增加新的查询条件
	 */

	public List<PrpLcaseNo> findByQueryConditions(String conditions) throws SQLException, Exception;

	/**
	 * 获得结案信息
	 * @param caseNo：结案号
	 * @return 结案对象
	 * @throws Exception
	 */
	public EndcaseDto findByPrimaryKey(String claimNo) throws SQLException, UserException, Exception;

	/**
	 * 按条件从prplcompensate表,prplregist表,prplclaimstatus表和表prpLclaim中查询多条数据
	 * @param conditions String
	 * @param pageNo int
	 * @param rowsPerPage int
	 * @throws Exception
	 * @return Collection Modify By sunhao 2004-08-24
	 *         Reason:增加车牌号，案件状态，操作时间查询条件，在查询结果中增加案件状态
	 */
	public List<PrpLcaseNo> findByQueryConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 从开赔案的结案，带jbpm工作流信息
	 * @param businessNo
	 * @param nodeType
	 * @param endcaseDto
	 * @param workFlowDto
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveBpmReCaseSave(String businessNo, String nodeType, EndcaseDto endcaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception;

	/**
	 * 保存结案
	 * @param endcaseDto：自定义结案对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveBpm(String businessNo, String nodeType, EndcaseDto endcaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
//	/**
//	 * 保存结案
//	 * @param endcaseDto：自定义结案对象
//	 * @throws SQLException
//	 * @throws Exception
//	 */
//	public void saveBpm_related(String businessNo, EndcaseDto endcaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception;
	/**
	 * 查询还有几条数据没有结案，如果大於一天，返回endca，如果小於一天，end结束流程
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public String findBpmNode(String claimNo)throws Exception;
}
