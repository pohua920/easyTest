package com.sinosoft.claim.schedule.service.facade;
/**
 * 新增定损方案接口
 * @author 中科软
 */
import java.sql.SQLException;

import com.sinosoft.claim.check.vo.CheckDto;

public interface ScheduleCertainLossService {
	  /**
	   * 保存新增定损调度
	   * @param checkDto：自定义新增定损调度对象
	   * @param dbManager  数据连接
	   * @throws SQLException
	   * @throws Exception
	   */
	  public void save(CheckDto checkDto)
	    throws SQLException,Exception;
	  
	  /**
	   * 删除定损字表信息
	   * @param businessNo
	   * @param checkDto
	   * @throws SQLException
	   * @throws Exception
	   */
	  public void deleteSubInfo(String businessNo,CheckDto checkDto)
	    throws SQLException,Exception;
}
