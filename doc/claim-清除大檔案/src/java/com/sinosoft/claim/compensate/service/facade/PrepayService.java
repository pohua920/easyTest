package com.sinosoft.claim.compensate.service.facade;

import java.util.List;

import com.sinosoft.claim.compensate.vo.PrepayDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

/**
 * <p>Title: 车险理赔 预赔</p>
 * @Description 
 * @author 中科软
 * @date Feb 28, 2013 3:27:06 PM
 */
public interface PrepayService {
	/**
	 * 保存预赔带工作流
	 * @param PrepayDto：预赔对象DTO
	 * @throws Exception
	 */
	public void save(PrepayDto prepayDto,WorkFlowDto workFlowDto,UserDto user,String preCompensateNo,boolean isSubmitUndwrt) throws Exception;
	
	/**
	 * 保存预赔
	 * @param PrepayDto：预赔对象DTO
	 * @throws Exception
	 */
	public void save(PrepayDto prepayDto) throws Exception;
	
	/**
	 * 删除预赔
	 * @param prepayNo：预赔号
	 * @throws Exception
	 */
	public void delete(String PrepayNo) throws Exception;
	
	/**
	 * 获得预赔信息
	 * @param  prepayNo：预赔号
	 * @return 预赔对象
	 * @throws Exception
	 */
	public PrepayDto findByPrimaryKey(String prepayNo) throws Exception;
	
	/**
	 * 判断预赔号是否存在
	 * @param prepayNo:预赔号
	 * @return 是/否
	 * @throws Exception
	 */
	public boolean isExist(String prepayNo) throws Exception;
	
	/**
	 * 获得预赔信息
	 * @param  conditions：查询条件
	 * @return 预赔对象
	 * @throws Exception
	 */
	
	public List<PrpLprepay> findByConditions(String conditions) throws Exception;
	
	/**
	 * 获得预赔查询信息 按条件从prplprepay,prplclaimstatus表中查询多条数据
	 * @param  conditions：查询条件
	 * @return 报案对象
	 * @throws Exception
	 */
	
	public List<PrpLprepay> findByQueryConditions(String conditions) throws Exception;
	
	/**
	 * 获得预赔信息
	 * @param  conditions：查询条件
	 * @return 预赔对象
	 * @throws Exception
	 */
	public List<PrpLprepay> findByApproveConditions(String conditions) throws Exception;
	
	/**
	 * 保存预赔
	 * @param PrepayDto：预赔对象DTO
	 * @throws Exception
	 * 预赔复核update
	 */
	public void approve(String prepayNo,String userCode,String underWriteFlag) throws Exception;
	/**
	 * 保存带工作流的信息,特殊赔案的处理
	 * @param prepayDto
	 * @param workFlowDto
	 * @param userCode
	 * @param preCompensateNo
	 * @param isSubmitUndwrt
	 * @throws Exception
	 */
	public void saveBpm(String businessNo,PrepayDto prepayDto, WorkFlowDto workFlowDto, UserDto user, String preCompensateNo, boolean isSubmitUndwrt) throws Exception;
}
