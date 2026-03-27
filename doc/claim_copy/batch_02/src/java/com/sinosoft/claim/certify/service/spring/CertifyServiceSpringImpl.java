/*
 * @(#)CertifyServiceSpringImpl.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.certify.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.certify.service.facade.CertifyService;
import com.sinosoft.claim.certify.vo.CertifyDto;
import com.sinosoft.claim.schema.model.PrpLcertifyCollectId;
import com.sinosoft.claim.schema.model.PrpLcertifyDirect;
import com.sinosoft.claim.schema.model.PrpLcertifyPayee;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyCollectService;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyDirectService;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyImgService;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyPayeeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLqualityCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLregistExtService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.TaskParam;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class CertifyServiceSpringImpl extends GenericDaoHibernate<CertifyDto, String> implements CertifyService {
	/**单证收集服務*/
	private PrpLcertifyCollectService prpLcertifyCollectService;
	/**單證圖片服務*/
	private PrpLcertifyImgService prpLcertifyImgService;
	/**單證類型服務*/
	private PrpLcertifyDirectService prpLcertifyDirectService;
	/**质量评审内容服務*/
	private PrpLqualityCheckService prpLqualityCheckService;
	/**报案信息补充服務*/
	private PrpLregistExtService prpLregistExtService;
	/**理赔节点状态服務*/
	private PrpLclaimStatusService prpLclaimStatusService;
	/***/
	private PrpLcertifyPayeeService prpLcertifyPayeeService;
	/**立案服務*/
	private PrpLclaimService prpLclaimService;
	/**工作流服務*/
	private WorkFlowService workFlowService;

	/**
	 * 保存单证
	 * @param CertifyDto：单证对象DTO
	 * @throws Exception
	 */
	public void save(CertifyDto certifyDto) throws SQLException, Exception {
		// BLCertifyFacade bLCertifyFacade = new BLCertifyFacade();
		// bLCertifyFacade.save(certifyDto,null);
		if (certifyDto.getNodeType().equals("CertifDirect")) {
			// 处理索赔清单
			// String condition = " registNo = " + "'" +
			// certifyDto.getPrpLcertifyCollectDto().getBusinessNo().trim() +
			// "'";
			// //示例未完成
			// String statement = "";
			// statement = " DELETE FROM PrpLcertifyDirect Where " + condition;
			String registNo = certifyDto.getPrpLcertifyCollect().getId().getBusinessNo();
			prpLcertifyDirectService.deleteByRegistNo(registNo);
			// dbManager.executeUpdate(statement);
			if (certifyDto.getPrpLcertifyDirectList() != null) {
				// new
				// DBPrpLcertifyDirect(dbManager).insertAll(certifyDto.getPrpLcertifyDirectDtoList());
				prpLcertifyDirectService.saveOrUpdate(certifyDto.getPrpLcertifyDirectList());
			}
		} else {
			// String nodeType = certifyDto.getNodeType();
			// String businessNo="";
			// 首先删除原来的相关数据
			deleteSubInfo(certifyDto);
			prpLcertifyCollectService.saveOrUpdate(certifyDto.getPrpLcertifyCollect());
			// prpLcertifyCollectService.save(certifyDto.getPrpLcertifyCollect());
			// new
			// DBPrpLcertifyCollect(dbManager).insert(certifyDto.getPrpLcertifyCollectDto());
			if (certifyDto.getPrpLcertifyImgList() != null) {
				prpLcertifyImgService.saveOrUpdate(certifyDto.getPrpLcertifyImgList());
				// new
				// DBPrpLcertifyImg(dbManager).insertAll(certifyDto.getPrpLcertifyImgDtoList());
			}
			if (certifyDto.getPrpLqualityCheckList() != null) {
				prpLqualityCheckService.saveOrUpdate(certifyDto.getPrpLqualityCheckList());
				// new
				// DBPrpLqualityCheck(dbManager).insertAll(certifyDto.getPrpLqualityCheckList());
			}
			if (certifyDto.getPrpLregistExtList() != null) {
				prpLregistExtService.saveOrUpdate(certifyDto.getPrpLregistExtList());
				// new
				// DBPrpLregistExt(dbManager).insertAll(certifyDto.getPrpLregistExtDtoList());
			}
			// 进行状态的改变
			// add by wenbin start at 2007-11-3
			// 增加领款人信息录入
			List<PrpLcertifyPayee> prpLcertifyPayeeDtoList = certifyDto.getPrpLcertifyPayeeList();
			if (prpLcertifyPayeeDtoList != null) {
				// new
				// DBPrpLcertifyPayee(dbManager).insertAll(prpLcertifyPayeeDtoList);
				prpLcertifyPayeeService.saveOrUpdate(prpLcertifyPayeeDtoList);
			}
			// add by wenbin end at 2007-11-3
			// add by caozhigang at 2009-4-17 start
			// reason:更新巨灾信息
			if (certifyDto.getPrpLclaim() != null) {
				prpLclaimService.update(certifyDto.getPrpLclaim());
				// new
				// DBPrpLclaim(dbManager).update(certifyDto.getPrpLclaimDto());
			}
			// add by caozhigang at 2009-4-17 start
			updateClaimStatus(certifyDto);
			// prpLclaimStatusService.update(prpLclaimStatus)
		}
	}

	/**
	 * 变更单证的操作状态的方法
	 * @param certifyDto 立案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void updateClaimStatus(CertifyDto certifyDto) throws SQLException, Exception {
		// 示例未完成
		// String statement = "";
		String nodeType = certifyDto.getNodeType();
		if (nodeType.equals("certi")) {
			if (certifyDto.getPrpLclaimStatus() != null) {
				// String condition3 = " BusinessNo='" +
				// certifyDto.getPrpLclaimStatus().getBusinessNo().trim() + "' "
				// + " AND NodeType ='certi'";
				// statement = " DELETE FROM prpLclaimStatus Where " +
				// condition3;
				// dbManager.executeUpdate(statement);
				// new
				// DBPrpLclaimStatus(dbManager).insert(certifyDto.getPrpLclaimStatusDto());
				String registNo = certifyDto.getPrpLclaimStatus().getId().getBusinessNo();
				prpLclaimStatusService.deleteByRegistNo(registNo, nodeType);
				prpLclaimStatusService.saveOrUpdate(certifyDto.getPrpLclaimStatus());
			}
		}
	}

	/**
	 * 删除单证字表信息
	 * @param dbManager
	 * @param certifyDto
	 * @throws SQLException
	 * @throws Exception //首先删除原来的相关数据
	 */
	private void deleteSubInfo(CertifyDto certifyDto) throws SQLException, Exception {
		// 增加领款人信息录入
		String registNo = "";
		if (certifyDto.getPrpLcertifyCollect() != null) {
			registNo = certifyDto.getPrpLcertifyCollect().getId().getBusinessNo();
			prpLcertifyPayeeService.deleteByRegistNo(registNo);
		}
		if (certifyDto.getPrpLcertifyCollect() != null) {
			registNo = certifyDto.getPrpLcertifyCollect().getId().getBusinessNo();
			prpLcertifyCollectService.deleteByRegistNo(registNo);
		}
		if (certifyDto.getPrpLcertifyImgList() != null && certifyDto.getPrpLcertifyImgList().size() > 0) {
			prpLcertifyImgService.deleteByRegistNo(registNo);
		}
		if (certifyDto.getPrpLqualityCheckList() != null && certifyDto.getPrpLqualityCheckList().size() > 0) {
			prpLqualityCheckService.deleteByRegistNo(registNo);
		}
		prpLregistExtService.deleteByRegistNo(registNo);
		// condition = " registNo = '" +
		// certifyDto.getPrpLcertifyCollectDto().getBusinessNo().trim()+ "' and
		// QualityCheckType='certi'";
		// System.out.println("----存在子表删除----"+condition);
		// statement = " DELETE FROM PrpLqualityCheck Where " + condition;
		// dbManager.executeUpdate(statement);
		// System.out.println("----存在子表----");
		// condition = " businessNo = " + "'" +
		// certifyDto.getPrpLcertifyCollectDto().getBusinessNo().trim() + "'";
		// statement = " DELETE FROM prpLcertifyImg Where " + condition;
		// condition = " businessNo = " + "'" +
		// certifyDto.getPrpLcertifyCollectDto().getBusinessNo().trim() + "'";
		// System.out.println("----存在主表----"+certifyDto.getPrpLcertifyCollectDto());
		// statement = " DELETE FROM PrpLcertifyCollect Where " + condition;
		// dbManager.executeUpdate(statement);
		// prpLcertifyCollectService.delete(prpLcertifyCollectId)
		// condition = "registNo='" +
		// certifyDto.getPrpLcertifyCollectDto().getBusinessNo().trim() + "'";
		// statement = "DELETE FROM PrpLcertifyPayee Where " + condition;
		// dbManager.executeUpdate(statement);
		// add by wenbin end at 2007-11-3

		// condition = " registNo = '" +
		// StringUtils.rightTrim(certifyDto.getPrpLcertifyCollectDto().getBusinessNo().trim())
		// + "'";
		// statement = " DELETE FROM PrpLregistExt Where " + condition;
		// dbManager.executeUpdate(statement);
	}

	/**
	 * 保存单证带工作流
	 * @param CertifyDto：单证对象DTO
	 * @throws Exception
	 */
	// public void save(CertifyDto certifyDto,WorkFlowDto workFlowDto) throws
	// SQLException,Exception
	// {
	// // CertifySaveCommand certifySaveCommand = new
	// CertifySaveCommand(certifyDto,workFlowDto);
	// // certifySaveCommand.execute();
	// BLCertifyFacade bLCertifyFacade = new BLCertifyFacade();
	// bLCertifyFacade.save(certifyDto,workFlowDto);
	// }
	/**
	 * 删除单证
	 * @param certifyNo：单证号
	 * @throws Exception
	 */
	// public void delete(String certifyNo) throws SQLException,Exception
	// {
	// // CertifyDeleteCommand certifyDeleteCommand = new
	// CertifyDeleteCommand(certifyNo);
	// // certifyDeleteCommand.execute();
	// BLCertifyFacade bLCertifyFacade = new BLCertifyFacade();
	// bLCertifyFacade.delete(certifyNo);
	// }
	/**
	 * 获得单证信息
	 * @param certifyNo：单证号
	 * @return 单证对象
	 * @throws Exception
	 */
	public CertifyDto findCertifyDto(String registNo) throws SQLException, UserException, Exception {
		// BLCertifyFacade bLCertifyFacade = new BLCertifyFacade();
		// CertifyDto certifyDto = bLCertifyFacade.findByPrimaryKey(registNo);
		// String conditions = " businessNo = '"+registNo+"' order by typecode";
		// System.out.println("----conditions----------"+conditions);
		CertifyDto certifyDto = new CertifyDto();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.businessNo", registNo);
		certifyDto.setPrpLcertifyCollectList(prpLcertifyCollectService.findPrpLcertifyCollect(queryRule));

		PrpLcertifyCollectId prpLcertifyCollectId = new PrpLcertifyCollectId();
		prpLcertifyCollectId.setBusinessNo(registNo);
		prpLcertifyCollectId.setLossItemCode("1");// 查询出一条，默认为1
		certifyDto.setPrpLcertifyCollect(prpLcertifyCollectService.findByPrpLcertifyCollectId(prpLcertifyCollectId));
		// String conditionsli=" businessNo = '"+registNo+"' and ValidStatus
		// ='1' order by typecode";

		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.businessNo", registNo);
		queryRule.addEqual("validStatus", "1");
		queryRule.addAscOrder("typeCode");
		certifyDto.setPrpLcertifyImgList(prpLcertifyImgService.findPrpLcertifyImg(queryRule));

		// conditions = " registNo = '"+registNo+"'";
		queryRule = QueryRule.getInstance();
		// queryRule.addLike(propertyName, value)
		queryRule.addEqual("id.registNo", registNo);
		certifyDto.setPrpLregistExtList(prpLregistExtService.findPrpLregistExt(queryRule));
		/**
		 * 自定义的单证类型和非自定义的单证类型排序字段不同,非自定义的单证类型按typecode排序;
		 */
		queryRule = QueryRule.getInstance();
		String conditions = " registNo = '" + registNo + "' and substr(typeCode,0,2)!='99' order by typecode";
		// queryRule.addEqual("registNo", registNo);
		// queryRule.add
		// queryRule.addLike("typeCode", "99%");
		queryRule = queryRule.addSql(conditions);
		List<PrpLcertifyDirect> prpLcertifyDirectList = prpLcertifyDirectService.findPrpLcertifyDirect(queryRule);
		// List<PrpLcertifyDirect> list1 = (ArrayList)new
		// DBPrpLcertifyDirect(dbManager).findByConditions(conditions,0,0);
		/**
		 * 自定义的单证类型和非自定义的单证类型排序字段不同,自定义的单证类型按serialno排序;
		 */
		conditions = " registNo = '" + registNo + "' and substr(typeCode,0,2)='99' order by serialno";
		queryRule = QueryRule.getInstance();
		queryRule = queryRule.addSql(conditions);
		prpLcertifyDirectList.addAll(prpLcertifyDirectService.findPrpLcertifyDirect(queryRule));
		// List list2 = (ArrayList)new
		// DBPrpLcertifyDirect(dbManager).findByConditions(conditions,0,0);
		// list1.addAll(list2);
		certifyDto.setPrpLcertifyDirectList(prpLcertifyDirectList);

		// conditions = " registNo = '"+registNo+"' and
		// qualityCheckType='certi'";
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		queryRule.addEqual("id.qualityCheckType", "certi");
		certifyDto.setPrpLqualityCheckList(prpLqualityCheckService.findPrpLqualityCheck(queryRule));

		PrpLclaimStatusId prpLclaimStatusId = new PrpLclaimStatusId();
		prpLclaimStatusId.setBusinessNo(registNo);
		prpLclaimStatusId.setNodeType("certi");
		prpLclaimStatusId.setSerialNo(0);
		certifyDto.setPrpLclaimStatus(prpLclaimStatusService.findPrpLclaimStatus(prpLclaimStatusId));
		return certifyDto;
	}

	/**
	 * 判断单证号是否存在
	 * @param certifyNo:单证号
	 * @return 是/否
	 * @throws Exception
	 */
	// public boolean isExist(String certifyNo) throws SQLException,Exception
	// {
	// // CertifyIsExistCommand certifyIsExistCommand = new
	// CertifyIsExistCommand(certifyNo);
	// // return ((Boolean)certifyIsExistCommand.execute()).booleanValue();
	// BLCertifyFacade bLCertifyFacade = new BLCertifyFacade();
	// return bLCertifyFacade.isExist(certifyNo);
	// }
	/**
	 * 获得单证信息
	 * @param conditions：查询条件
	 * @return 单证对象
	 * @throws Exception
	 */

	// public Collection findByConditions(String conditions) throws
	// SQLException,Exception
	// {
	// // PrpLcertifyFindByConCommand prpLcertifyFindByConCommand = new
	// PrpLcertifyFindByConCommand(conditions);
	// // return (Collection)prpLcertifyFindByConCommand.execute();
	// BLCertifyFacade bLCertifyFacade = new BLCertifyFacade();
	// return bLCertifyFacade.findByConditions(conditions);
	// }
	/**
	 * 获得单证查询信息
	 * @param conditions：查询条件
	 * @return 报案对象
	 * @throws Exception Add By sunhao 2004-08-24 Reason:增加新的查询条件
	 */

	// public Collection findByQueryConditions(String conditions) throws
	// SQLException,Exception
	// {
	// // CertifyQueryCommand certifyQueryCommand = new
	// CertifyQueryCommand(conditions);
	// // return (Collection)certifyQueryCommand.executeCommand();
	// BLCertifyFacade bLCertifyFacade = new BLCertifyFacade();
	// return bLCertifyFacade.findByQueryConditions(conditions);
	// }
	// add by zhaolu 20060803 start
	// add by zhaolu 20060726 start
	// public PageRecord findByQueryConditions(String conditions,int pageNo,int
	// recordPerPage) throws SQLException,Exception
	// {
	// // CertifyQueryCommand certifyQueryCommand = new
	// CertifyQueryCommand(conditions,pageNo,recordPerPage);
	// // return (PageRecord)certifyQueryCommand.executeCommand();
	// return new
	// BLCertifyFacade().findByQueryConditions(conditions,pageNo,recordPerPage);
	// }
	// add by zhaolu 20060726 end
	// add by zhaolu 20060803 end
	/**
	 * 保存单证信息,带工作流的
	 * @param certifyDto
	 * @throws Exception 
	 */
	public void save(CertifyDto certifyDto, WorkFlowDto workFlowDto) throws Exception {
		// 创建数据库管理对象
		this.save(certifyDto);
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
		}
	}

	/**
	 * 保存单证信息,带工作流的
	 * @param certifyDto
	 * @throws Exception 
	 */
	@ProcessTask(userId = "certi", businessBeanOffset = 0, businessIdAttributeName = "prpLcertifyCollect.id.businessNo")
	@TaskParam(key="relatedClaim", paramValueBeanOffset=1,paramValueAttributeName="relatedClaim")
	public void saveBpm(CertifyDto certifyDto,String  relatedClaim,WorkFlowDto workFlowDto) throws Exception {
		this.save(certifyDto, workFlowDto);
	}

	public PrpLcertifyCollectService getPrpLcertifyCollectService() {
		return prpLcertifyCollectService;
	}

	public void setPrpLcertifyCollectService(PrpLcertifyCollectService prpLcertifyCollectService) {
		this.prpLcertifyCollectService = prpLcertifyCollectService;
	}

	public PrpLcertifyImgService getPrpLcertifyImgService() {
		return prpLcertifyImgService;
	}

	public void setPrpLcertifyImgService(PrpLcertifyImgService prpLcertifyImgService) {
		this.prpLcertifyImgService = prpLcertifyImgService;
	}

	public PrpLcertifyDirectService getPrpLcertifyDirectService() {
		return prpLcertifyDirectService;
	}

	public void setPrpLcertifyDirectService(PrpLcertifyDirectService prpLcertifyDirectService) {
		this.prpLcertifyDirectService = prpLcertifyDirectService;
	}

	public PrpLqualityCheckService getPrpLqualityCheckService() {
		return prpLqualityCheckService;
	}

	public void setPrpLqualityCheckService(PrpLqualityCheckService prpLqualityCheckService) {
		this.prpLqualityCheckService = prpLqualityCheckService;
	}

	public PrpLregistExtService getPrpLregistExtService() {
		return prpLregistExtService;
	}

	public void setPrpLregistExtService(PrpLregistExtService prpLregistExtService) {
		this.prpLregistExtService = prpLregistExtService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLcertifyPayeeService getPrpLcertifyPayeeService() {
		return prpLcertifyPayeeService;
	}

	public void setPrpLcertifyPayeeService(PrpLcertifyPayeeService prpLcertifyPayeeService) {
		this.prpLcertifyPayeeService = prpLcertifyPayeeService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}
