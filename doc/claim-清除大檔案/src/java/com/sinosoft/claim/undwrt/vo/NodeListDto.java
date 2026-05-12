package com.sinosoft.claim.undwrt.vo;

import java.io.Serializable;

/**
 * 節點基礎對象
 * @author 中科软
 *
 */
public class NodeListDto implements Serializable {
	private static final long serialVersionUID;
	/** 節點代碼*/
	private String nodeNo;
	/** 節點名稱*/
	private String nodeName;
	
	public NodeListDto() {
	}

	public NodeListDto(String nodeNo, String nodeName) {
		this.nodeNo = nodeNo;
		this.nodeName = nodeName;
	}

	public String getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	static {
		serialVersionUID = com.sinosoft.undwrt.dto.custom.NodeListDto.class.hashCode();
	}
}