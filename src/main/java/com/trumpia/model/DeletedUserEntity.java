package com.trumpia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="e_deleted_user")
public class DeletedUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column( length=50, nullable=false)
	private String username;

	@Column( length=200)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(columnDefinition="DATE") 
	private Date createdDate;

	@Column(columnDefinition="DATE") // @Temporal(TemporalType.TIMESTAMP)
	private Date deletedDate;
	
	
	public DeletedUserEntity(UserEntity user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.createdDate = user.getCreatedDate();
		this.deletedDate = new Date();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public Date getCreatedDate() {
		return this.createdDate;
	}
	
	public Date getDeletedDate() {
		return this.deletedDate;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "{\n\"id\" : \"" + id + "\", \n\"username\" : \"" + username + "\", \n\"email\" : \"" + email
				+ "\", \n\"createdDate\" : \"" + createdDate + "\", \n\"deletedDate\" : \"" + deletedDate + "\"\n}";
	}

	

}
