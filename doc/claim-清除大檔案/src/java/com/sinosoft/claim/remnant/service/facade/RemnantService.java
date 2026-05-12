package com.sinosoft.claim.remnant.service.facade;

import ins.framework.common.Page;

import java.sql.SQLException;
import java.util.Map;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.remnant.vo.RemnantDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

public interface RemnantService {
	
	/**残余物大对象保存方法
	 * @param remnantDto 残余物大对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void save(RemnantDto remnantDto) throws SQLException, Exception;
	

	/**通过计算书号找出残余物大对象的所有子表，拼成remnantdto
	 * @param compensateNo
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public RemnantDto findByPrimaryKey(String compensateNo) throws SQLException, Exception;
	/**残余物大对象删除子表信息
	 * @param compensateNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public void deleteSubInfo(String compensateNo) throws SQLException, Exception;
	
	/**残余物的审核过程
	 * @param compensateNo
	 * @param infoMap
	 * @throws Exception
	 */
	public void undwrtPayMent(String compensateNo,Map<String, String> infoMap)throws Exception;
	
	/**
	 * 残余物审核提交收集页面信息
	 * @param httpServletRequest
	 * @param compensateNo
	 * @throws Exception
	 */
	public void undwrt(UserDto user, String compensateNo) throws Exception;
	/**
	 * 残余物任务退回修改页面数据收集
	 * @param httpServletRequest
	 * @param compensateNo
	 * @throws Exception
	 */
	public void withdrawal(UserDto user, String compensateNo) throws Exception ;
	/**
	 * 保存残余物
	 * @param compensateDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm(RemnantDto remnantDto,WorkFlowDto workFlowDto)throws Exception;
	/***
	 * 追償計算書審核通過
	 */
	public void saveUndwrtPass(String compensateNo, WorkFlowDto workFlowDto) throws Exception;
	/***
	 * 追偿审核驳回修改
	 */
	public void saveUndwrtBack(String compensateNo, WorkFlowDto workFlowDto) throws Exception;
	/***
	 * 查询待审核追偿数据
	 */
	public Page findUndwrtByConditions(String condition, int pageNo, int pageSize) throws Exception;
}
