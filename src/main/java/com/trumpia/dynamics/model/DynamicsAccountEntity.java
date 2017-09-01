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
	
	@Column(length=500)
	private String resourceUrl;
	
	@Column(length=2000)
	private String accessToken;
	
	@Column(length=2000)
	private String refreshToken;
	
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

	@Override
	public String toString() {
		return "User [id=" + StringUtils.nullTextIfNull(id)
			 + ", createdDate=" + StringUtils.nullTextIfNull(createdDate)  + ", updatedDate=" + StringUtils.nullTextIfNull(updatedDate) + "]";
	}

}
