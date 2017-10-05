package com.trumpia.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Table(name="e_user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column( length=50, nullable=false)
	private String username;

	@Column( length=200)
	private String email;
	
	@Column(nullable=false)
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "{\n\"id\" : \"" + id + "\", \nusername\" : \"" + username + "\", \nemail\" : \"" + email
				+ "\", \ncreatedDate\" : \"" + createdDate + "\", \nupdatedDate\" : \"" + updatedDate + "\n}";
	}

	
}