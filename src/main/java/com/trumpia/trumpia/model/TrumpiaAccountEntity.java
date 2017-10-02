package com.trumpia.trumpia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

import com.trumpia.model.UserEntity;

@Entity
@Table(name="e_trumpia_account")
public class TrumpiaAccountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column( length=36)
	private String username;

	@Column( length=36)
	private String apikey;

	@Column
	private String description;
	
	@Column(nullable=false)
	private String baseURL = "https://api.trumpia.com";

	@ManyToOne
	@JoinColumn(name = "e_user_id", nullable=false)
	private UserEntity userEntity;
	
	public TrumpiaAccountEntity() {
		
	}
	
	public TrumpiaAccountEntity(JSONObject parsed) {
		if(parsed.has("APIKey"))
			this.apikey = parsed.getString("APIKey");
		if(parsed.has("description"))
			this.description = parsed.getString("description");
		if(parsed.has("username"))
			this.username = parsed.getString("username");
		if(parsed.has("baseURL"))
			this.baseURL = parsed.getString("baseURL");
	}
	
	public JSONObject IdAndDescriptionJSON() {
		JSONObject json = new JSONObject();
		json.put("description", this.description);
		json.put("id", this.username);
		return json;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public Long getId() {
		return id;
	}
	
	public String getBaseURL() {
		return baseURL;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "TrumpiaAccountEntity [id=" + id + ", username=" + username + ", APIkey=" + apikey + ", baseURL="
				+ baseURL + ", userEntity=" + userEntity + "]";
	}


}
