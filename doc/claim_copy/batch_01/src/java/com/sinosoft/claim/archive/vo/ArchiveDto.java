package com.sinosoft.claim.archive.vo;

import java.io.Serializable;

import com.sinosoft.claim.schema.model.PrpLDocArchive;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLog;

/**
 * 自定义资料归档数据传输对象
 * <p>
 * Title: 理赔资料归档DTO
 * </p>
 * <p>
 * Description: 理赔资料归档样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class ArchiveDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 资料归档调阅主表 */
	private PrpLDocArchive prpLDocArchive;
	/** 资料归档调阅日志表 */
	private PrpLDocArchiveLog prpLDocArchiveLog;

	public PrpLDocArchive getPrpLDocArchive() {
		return prpLDocArchive;
	}

	public void setPrpLDocArchive(PrpLDocArchive prpLDocArchive) {
		this.prpLDocArchive = prpLDocArchive;
	}

	public PrpLDocArchiveLog getPrpLDocArchiveLog() {
		return prpLDocArchiveLog;
	}

	public void setPrpLDocArchiveLog(PrpLDocArchiveLog prpLDocArchiveLog) {
		this.prpLDocArchiveLog = prpLDocArchiveLog;
	}
}
