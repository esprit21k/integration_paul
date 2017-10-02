package com.trumpia.dynamics.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.trumpia.model.UserEntity;
import com.trumpia.util.DateUtils;
import com.trumpia.util.StringUtils;

@Entity
@Table(name="e_dynamics_account")
public class DynamicsAccountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String dynamicsId;
	
	@Column(length=500)
	private String resourceUrl;
	
	@Column(length=2000)
	private String accessToken;
	
	@Column(length=2000)
	private String refreshToken;
	
	@Column
	private String field;
	
	@Column(columnDefinition="timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireDate;
	
	@Column(columnDefinition="timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(columnDefinition="timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@OneToOne
	@JoinColumn(name = "e_user_id")
	private UserEntity userEntity;

	public DynamicsAccountEntity() {
		createdDate = new Date();
		updatedDate = new Date();
		expireDate = new Date();
		expireDate = DateUtils.addMinutes(expireDate, 60);
	}
	
	public String getDynamicsId() {
		return dynamicsId;
	}

	public void setDynamicsId(String dynamicsId) {
		this.dynamicsId = dynamicsId;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return "DynamicsAccountEntity [dynamicsId=" + dynamicsId + ", resourceUrl=" + resourceUrl + ", accessToken="
				+ accessToken + ", refreshToken=" + refreshToken + ", field=" + field + ", expireDate=" + expireDate
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", userEntity=" + userEntity + "]";
	}

}
