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
	private String dynamicFieldName;
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
	public String getTrumpiaFieldName() {
		return trumpiaFieldName;
	}
	public String getDynamicFieldName() {
		return dynamicFieldName;
	}
	public String getDescription() {
		return description;
	}
	public String getCustomDataId() {
		return customDataId;
	}
	public void setCustomDataId(String customDataId) {
		this.customDataId = customDataId;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setTrumpiaFieldName(String trumpiaFieldName) {
		this.trumpiaFieldName = trumpiaFieldName;
	}
	public void setDynamicFieldName(String dynamicFieldName) {
		this.dynamicFieldName = dynamicFieldName;
	}

	@Override
	public String toString() {
		return "MappingEntity [id=" + id +  ", trumpiaFieldName=" + trumpiaFieldName
				+ ", dynamicFieldName=" + dynamicFieldName + "]";
	}


}
