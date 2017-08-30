package com.trumpia.trumpia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.trumpia.model.UserEntity;

@Entity
@Table(name="e_trumpia_account")
public class TrumpiaAccountEntity implements Comparable<TrumpiaAccountEntity>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column( length=36)
	private String uniqueId;

	@Column( length=36)
	private String apikey;
	
	@Column(nullable=false)
	private String baseURL = "http://api.trumpia.com";
	
	@Column
	private String description;

	@CreationTimestamp
	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@UpdateTimestamp
	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@ManyToOne
	@JoinColumn(name = "e_user_id")
	private UserEntity userEntity;

	public String getDescription() {
		return description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public UserEntity getUserEntity() {
		return userEntity;
	}
	
	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}


	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Long getId() {
		return id;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public String getAPIkey() {
		return apikey;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void setAPIkey(String aPIkey) {
		apikey = aPIkey;
	}

	@Override
	public String toString() {
		return "TrumpiaAccountRepository [id=" + id + ", uniqueId=" + uniqueId + ", APIkey=" + apikey + "]";
	}

	@Override
	public int compareTo(TrumpiaAccountEntity that) {
		if(this.getUpdatedDate().getTime() > that.getUpdatedDate().getTime()) return 1;
		if(this.getUpdatedDate().getTime() < that.getUpdatedDate().getTime()) return -1;
		else return 0;
	}
}
