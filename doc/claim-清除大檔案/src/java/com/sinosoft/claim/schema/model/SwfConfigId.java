package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfConfigId
 */
@Embeddable
public class SwfConfigId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** 属性流程实例 */
	private String processId;
	/** 属性流程节点 */
	private String actorId;

	public SwfConfigId() {
	}

	public SwfConfigId(String processId, String actorId) {
		this.processId = processId;
		this.actorId = actorId;
	}

	@Column(name = "PROCESSID")
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@Column(name = "ACTORID")
	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof SwfConfigId)) {
			return false;
		}
		SwfConfigId castOther = (SwfConfigId) other;
		return ((this.getActorId() == castOther.getActorId()) || (this.getActorId() != null && castOther.getActorId() != null && this.getActorId().equals(castOther.getActorId())))
				&& ((this.getProcessId() == castOther.getProcessId()) || (this.getProcessId() != null && castOther.getProcessId() != null && this.getProcessId().equals(castOther.getProcessId())));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (getProcessId() == null ? 0 : this.getProcessId().hashCode());
		result = 37 * result + (getActorId() == null ? 0 : this.getActorId().hashCode());
		return result;
	}

}
