package com.sinosoft.claim.schedule.service.spring;

/**
 * 新增定损方案接口实现类
 * @author 中科软
 */
import java.sql.SQLException;

import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.schedule.service.facade.ScheduleCertainLossService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckLossService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonTraceService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleItemService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdCarLossService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPropService;

public class ScheduleCertainLossServiceSpringImpl implements ScheduleCertainLossService {
	private PrpLthirdPartyService prpLthirdPartyService;
	private PrpLscheduleItemService prpLscheduleItemService;
	private PrpLthirdCarLossService prpLthirdCarLossService;
	private PrpLpersonTraceService prpLpersonTraceService;
	private PrpLthirdPropService prpLthirdPropService;
	private PrpLcheckLossService prpLcheckLossService;

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}

	public PrpLscheduleItemService getPrpLscheduleItemService() {
		return prpLscheduleItemService;
	}

	public void setPrpLscheduleItemService(PrpLscheduleItemService prpLscheduleItemService) {
		this.prpLscheduleItemService = prpLscheduleItemService;
	}

	public PrpLthirdCarLossService getPrpLthirdCarLossService() {
		return prpLthirdCarLossService;
	}

	public void setPrpLthirdCarLossService(PrpLthirdCarLossService prpLthirdCarLossService) {
		this.prpLthirdCarLossService = prpLthirdCarLossService;
	}

	public PrpLpersonTraceService getPrpLpersonTraceService() {
		return prpLpersonTraceService;
	}

	public void setPrpLpersonTraceService(PrpLpersonTraceService prpLpersonTraceService) {
		this.prpLpersonTraceService = prpLpersonTraceService;
	}

	public PrpLthirdPropService getPrpLthirdPropService() {
		return prpLthirdPropService;
	}

	public void setPrpLthirdPropService(PrpLthirdPropService prpLthirdPropService) {
		this.prpLthirdPropService = prpLthirdPropService;
	}

	public PrpLcheckLossService getPrpLcheckLossService() {
		return prpLcheckLossService;
	}

	public void setPrpLcheckLossService(PrpLcheckLossService prpLcheckLossService) {
		this.prpLcheckLossService = prpLcheckLossService;
	}

	@Override
	public void save(CheckDto checkDto) throws SQLException, Exception {
		String registNo = "";
		registNo = checkDto.getPrpLcheck().getId().getRegistNo();
		// 首先删除原来的相关数据
		deleteSubInfo(registNo, checkDto);
		// 理赔车辆信息表
		if (checkDto.getPrpLthirdPartyList() != null) {
			this.prpLthirdPartyService.insertAll(checkDto.getPrpLthirdPartyList());
		}
		// 调度任务标的表
		if (checkDto.getPrpLscheduleItemList() != null) {

			this.prpLscheduleItemService.saveAndDelete(checkDto.getPrpLscheduleItemList());
		}
		// 损失部位表
		if (checkDto.getPrpLthirdCarLossList() != null) {
			this.prpLthirdCarLossService.insertAll(checkDto.getPrpLthirdCarLossList());
		}

		if (checkDto.getPrpLpersonTraceList() != null) {
			this.prpLpersonTraceService.insertAll(checkDto.getPrpLpersonTraceList());
		}

		if (checkDto.getPrpLthirdPropList() != null) {
			this.prpLthirdPropService.insertAll(checkDto.getPrpLthirdPropList());
		}

		if (checkDto.getPrpLcheckLossList() != null) {
			this.prpLcheckLossService.insertAll(checkDto.getPrpLcheckLossList());
		}
	}

	@Override
	public void deleteSubInfo(String businessNo, CheckDto checkDto) throws SQLException, Exception {
		if (checkDto.getPrpLthirdPartyList() != null) {
			this.prpLthirdPartyService.deleteByRegistNo(businessNo);
		}

		if (checkDto.getPrpLthirdCarLossList() != null) {
			this.prpLthirdCarLossService.deleteByRegistNo(businessNo);
		}

		if (checkDto.getPrpLcheckLossList() != null) {

			this.prpLcheckLossService.deleteByRegistNo(businessNo);
		}

		this.prpLthirdPropService.deleteByRegistNo(businessNo);

		this.prpLpersonTraceService.deleteByRegistNo(businessNo);
	}

}
