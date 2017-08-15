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

	@Column( length=200)
	private String email;
	
	@Column( length=200)
	private String password;

	@Column(columnDefinition="DATE") // @Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(columnDefinition="DATE")
	private Date updatedDate;
	
	
	public UserEntity() {
		this.createdDate = new Date();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + StringUtils.nullTextIfNull(id) + ", uniqueId=" + StringUtils.nullTextIfNull(uniqueId) + ", password=" + StringUtils.nullTextIfNull(password)
		+ ", email=" + StringUtils.nullTextIfNull(email) + ", createdDate=" + StringUtils.nullTextIfNull(createdDate)  + ", updatedDate=" + StringUtils.nullTextIfNull(updatedDate) + "]";
	}
	
}