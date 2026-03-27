package com.sinosoft.claim.archive.service.facade;

import ins.framework.common.Page;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLDocArchive;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLog;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLogId;
import com.sinosoft.claim.schema.model.UtiUserGrade;

/**
 * 资料归档调阅接口
 * @author 中科软
 */
public interface ArchiveService {
	/**
	 * 插入一条数据
	 * @param PrpLDocArchive prpLDocArchive
	 * @throws Exception
	 */
	public void insert(PrpLDocArchive prpLDocArchive) throws Exception;

	/**
	 * 按主键更新一条数据(主键本身无法变更)
	 * @param prpLDocArchive prpLDocArchive
	 * @throws Exception
	 */
	public void update(PrpLDocArchive prpLDocArchive,PrpLDocArchiveLog prpLDocArchiveLog) throws Exception;
	/**
	 * 根據條件查詢
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public List<PrpLDocArchive> findByConditions(String conditions) throws Exception;

	/**
	 * 按条件从PrpLDocArchive表中查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 页码
	 * @param rowsPerPage 每页显示的行数
	 * @throws Exception
	 * @return Collection 实体资料对象
	 */
	public Collection<PrpLDocArchive> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;
	/**
	 * 根據條件查詢page
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	public Page findPageByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;
	/**
	 * 按主键查找一条数据
	 * @param claimNo 赔案号
	 * @throws Exception
	 * @return PrpLDocArchiveDto 实体资料对象
	 */
	public PrpLDocArchive findByPrimaryKey(String claimNo) throws Exception;

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions 查询条件
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	public int getCount(String conditions) throws Exception;
	/**
	 * 资料归档操作
	 * @param prpLDocArchiveDto 资料归档调阅主表
	 * @param prpLDocArchiveLogDto 资料归档调阅日志表
	 * @throws Exception
	 */
	public void toArchive(PrpLDocArchive prpLDocArchive, PrpLDocArchiveLog prpLDocArchiveLog) throws Exception ;
	
	/**
	 * 获得资料归档调阅日志表中数据
	 * @param claimNo 赔案号
	 * @param serialNo 序号
	 * @return 资料归档调阅日志对象
	 * @throws Exception
	 */
	public PrpLDocArchiveLog findByPrimaryKey(PrpLDocArchiveLogId prpLDocArchiveLogId) throws Exception;

	/**
	 * 获取审核人员信息
	 * @param request
	 * @param userDto
	 * @throws Exception
	 */
	public String getPower(HttpServletRequest request, UserDto userDto) throws Exception ;
	/**
	 * 根据条件获取用户权限信息
	 * @param conditions 查询条件
	 * @return Collection 用户权限对象
	 * @throws SQLException Exception 
	 */
	public List<UtiUserGrade> findUtiUserGradeByConditions(String conditions) throws Exception ;

	/**
	 * 提交請求
	 * @param request
	 */
	public void submit(HttpServletRequest request);
	/**
	 * 根据条件获得资料归档调阅日志表中数据
	 * @param conditions 查询条件
	 * @return 资料归档调阅日志对象
	 * @throws Exception
	 */
	public PrpLDocArchiveLog findPrpLDocArchiveLogByConditions(String conditions) throws Exception ;
}
