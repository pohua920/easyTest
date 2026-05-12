/*
 * @(#)PrpLcertifyImgService.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.facade;
/**
 * 单证上传信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcertifyImg;
import com.sinosoft.claim.schema.model.PrpLcertifyImgId;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @Date    <Jan 23, 2013>
 * @description 
 */
public interface PrpLcertifyImgService {
	
	/**
	 * 保存单证上传信息
	 * @param prpLcaseNo ：传入的单证上传信息
	 */
	public void save(PrpLcertifyImg prpLcertifyImg) throws Exception;

	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLcertifyImg> list)throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLcertifyImg prpLcertifyImg)throws Exception;
	/**
	 * 单证上传信息信息
	 * @param list  :传入的单证上传信息信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLcertifyImg> list) throws Exception;
	/**
	 * 删除单证上传信息信息
	 * @param prpLcaseNoId ：传入的单证上传信息编号
	 */
	public void delete(PrpLcertifyImgId prpLcertifyImgId) throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 根据单证上传信息编号查询出单证上传信息信息
	 * @param prpLcaseNoId ：传入的单证上传信息编号
	 * @return 返回单证上传信息
	 */
	public PrpLcertifyImg findByPrpLcertifyImgId(PrpLcertifyImgId prpLcertifyImgId)throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的单证上传信息页面信息
	 */
	public Page findPrpLcertifyImg(QueryRule queryRule, int pageNo, int pageSize)throws Exception;
	/**
	 * 根据查询对象获取 单证上传信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  单证上传信息的集合
	 */
	public List<PrpLcertifyImg> findPrpLcertifyImg(QueryRule queryRule) throws Exception;

}
