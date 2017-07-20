package com.trumpia.model;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.trumpia.util.StringUtils;

@Entity
@Table(name="e_user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column( length=36)
	private String uniqueId;
	
	@Column( length=50)
	private String username;
	
	@Column(nullable=false, length=32)
	private String apikey;

	@Column(columnDefinition="DATE") // @Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(columnDefinition="DATE")
	private Date updatedDate;
	
	protected UserEntity() {
		
	}
	
	public UserEntity(String username, String apikey) {
		this.username = username;
		this.apikey = apikey;
		this.uniqueId = UUID.nameUUIDFromBytes(apikey.getBytes()).toString();
		this.createdDate = new Date();
	}
	
	public String getApikey() {
		return this.apikey;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Date getCreatedDate() {
		return this.createdDate;
	}
	
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUniqueId() {
		return this.uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public String toString() {
		return "User [id=" + StringUtils.nullTextIfNull(id) + ", uniqueId=" + StringUtils.nullTextIfNull(uniqueId) + ", apikey=" + StringUtils.nullTextIfNull(apikey)
			 + ", createdDate=" + StringUtils.nullTextIfNull(createdDate)  + ", updatedDate=" + StringUtils.nullTextIfNull(updatedDate) + "]";
	}
	
}