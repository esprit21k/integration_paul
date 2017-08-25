package com.trumpia.dynamics.schema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trumpia.dynamics.model.DynamicsAccountEntity;

@Entity
@Table(name="e_dynamics_schema")
public class DynamicsSchemaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;
	
	@Column
	private String type;
	
	@ManyToOne
	@JoinColumn(name="dynamics_id")
	private DynamicsAccountEntity dynamicsAccountEntity;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public DynamicsAccountEntity getDynamicsAccountEntity() {
		return dynamicsAccountEntity;
	}

	public void setDynamicsAccountEntity(DynamicsAccountEntity dynamicsAccountEntity) {
		this.dynamicsAccountEntity = dynamicsAccountEntity;
	}

	@Override
	public String toString() {
		return "DynamicsSchemaEntity [name=" + name + ", type=" + type + ", dynamicsAccountEntity="
				+ dynamicsAccountEntity + "]";
	}
}
