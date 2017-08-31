package com.trumpia.mapping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trumpia.model.UserEntity;

@Entity
@Table(name="e_mapping_info")
public class MappingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String description;
	@Column
	private String trumpiaFieldName;
	@Column
	private String targetFieldName;
	@Column
	private String targetCompany;
	@Column(nullable = true)
	private String customDataId;
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity userId;

	public MappingEntity() {

	}
	public Long getId() {
		return id;
	}
	public UserEntity getUserId() {
		return userId;
	}
	public String getTrumpiaFieldName() {
		return trumpiaFieldName;
	}
	public String getServiceFieldName() {
		return targetFieldName;
	}
	public String getServiceName() {
		return targetCompany;
	}
	public String getDescription() {
		return description;
	}
	public String getCustomDataId() {
		return customDataId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUserId(UserEntity userId) {
		this.userId = userId;
	}
	public void setTrumpiaFieldName(String trumpiaFieldName) {
		this.trumpiaFieldName = trumpiaFieldName;
	}
	public void setTargetFieldName(String targetFieldName) {
		this.targetFieldName = targetFieldName;
	}
	public void setTargetCompany(String targetCompany) {
		this.targetCompany = targetCompany;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCustomDataId(String customDataId) {
		this.customDataId = customDataId;
	}

	@Override
	public String toString() {
		return "MappingEntity [id=" + id + ", description=" + description + ", trumpiaFieldName=" + trumpiaFieldName
				+ ", targetFieldName=" + targetFieldName + ", targetCompany=" + targetCompany + ", customDataId="
				+ customDataId + ", userId=" + userId + "]";
	}


}
