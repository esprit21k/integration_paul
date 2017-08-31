package com.trumpia.trumpia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="e_subscriptionId_mapping")
public class SubscriptionIdMappingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String trumpiaId;
	@Column
	private String targetSubscriptionId;
	
	public SubscriptionIdMappingEntity(String trumpiaId, String targetSubscriptionId) {
		this.trumpiaId = trumpiaId;
		this.targetSubscriptionId = targetSubscriptionId;
	}
	public String getTrumpiaId() {
		return trumpiaId;
	}

	public void setTrumpiaId(String trumpiaId) {
		this.trumpiaId = trumpiaId;
	}	
	
	public Long getId() {
		return id;
	}
	
	public String getTargetSubscriptionId() {
		return targetSubscriptionId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setTargetSubscriptionId(String targetSubscriptionId) {
		this.targetSubscriptionId = targetSubscriptionId;
	}

}
