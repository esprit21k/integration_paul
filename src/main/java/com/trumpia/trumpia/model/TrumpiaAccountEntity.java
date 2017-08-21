package com.trumpia.trumpia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.trumpia.model.UserEntity;

@Entity
@Table(name="e_trumpia_account")
public class TrumpiaAccountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column( length=36)
	private String uniqueId;

	@Column( length=36)
	private String APIkey;

	@OneToOne
	@JoinColumn(name = "e_user_id")
	private UserEntity userEntity;

	public UserEntity getUserEntity() {
		return userEntity;
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
		return APIkey;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void setAPIkey(String aPIkey) {
		APIkey = aPIkey;
	}

	@Override
	public String toString() {
		return "TrumpiaAccountRepository [id=" + id + ", uniqueId=" + uniqueId + ", APIkey=" + APIkey + "]";
	}
}
