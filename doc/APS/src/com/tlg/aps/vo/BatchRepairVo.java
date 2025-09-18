/**
 * 
 */
package com.tlg.aps.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author bj016
 * @param <BatchRepairItemVo>
 *
 */
@XmlRootElement(name="ROOT")
public class BatchRepairVo {

	private BatchRepairHeaderVo header;
	
	private List<BatchRepairItemVo> items;

	@XmlElement(name = "Header")
	public BatchRepairHeaderVo getHeader() {
		return header;
	}

	public void setHeader(BatchRepairHeaderVo header) {
		this.header = header;
	}

	@XmlElement(name = "Item")
	public List<BatchRepairItemVo> getItems() {
		return items;
	}

	public void setItems(List<BatchRepairItemVo> items) {
		this.items = items;
	}
	
}
