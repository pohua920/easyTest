package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;

/**
 * 核保級別下拉列表類.
 */
public class NodeListVo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = NodeListVo.class.hashCode();

	/** 屬性節點號. */
	private String nodeNo = null;

	/** 屬性節點名稱. */
	private String nodeName = null;

	/**
	 * 默認構造方法.
	 */
	public NodeListVo() {

	}

	/**
	 * 獲取屬性節點號.
	 * 
	 * @return 屬性節點號的值
	 */
	public String getNodeNo() {
		return nodeNo;
	}

	/**
	 * 設置屬性節點號.
	 * 
	 * @param nodeNo
	 *            待設置的節點號的值
	 */
	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * 獲取屬性節點名稱.
	 * 
	 * @return 屬性節點名稱的值
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * 設置屬性節點名稱.
	 * 
	 * @param nodeName
	 *            待設置的節點名稱的值
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}