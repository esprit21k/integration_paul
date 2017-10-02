package com.trumpia.connection.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.model.UserEntity;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

@Entity
@Table(name="e_connection")
public class ConnectionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "connection_id", nullable=false)
	private String connectionName;	
	
	@ManyToOne
	@JoinColumn(name = "e_user_id", nullable=false)
	private UserEntity userEntity;
	
	@ManyToOne
	@JoinColumn(name = "e_trumpia_id", nullable=false)
	private TrumpiaAccountEntity trumpiaAccount;
	
	@ManyToOne
	@JoinColumn(name = "e_target_id", nullable=false) // third-party... Dynamics first
	private DynamicsAccountEntity dynamicsAccount;
	
	@Column
	private Boolean postOption; //true: Update, false: replace
	
	@Column
	private Boolean deleteOption; //true: delete, false: leave
	
	@Column
	private String direction; //bi, toPlatform, toThirdparty
	
	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public TrumpiaAccountEntity getTrumpiaAccount() {
		return trumpiaAccount;
	}

	public void setTrumpiaAccount(TrumpiaAccountEntity trumpiaAccount) {
		this.trumpiaAccount = trumpiaAccount;
	}

	public DynamicsAccountEntity getDynamicsAccount() {
		return dynamicsAccount;
	}

	public void setDynamicsAccount(DynamicsAccountEntity dynamicsAccount) {
		this.dynamicsAccount = dynamicsAccount;
	}

	public Boolean getPostOption() {
		return postOption;
	}

	public void setPostOption(Boolean postOption) {
		this.postOption = postOption;
	}

	public Boolean getDeleteOption() {
		return deleteOption;
	}

	public void setDeleteOption(Boolean deleteOption) {
		this.deleteOption = deleteOption;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		return "{ \"connectionName\" : \"" + connectionName + "\", \"userId\" : \"" + userEntity.getUsername()
				+ "\", \"platformId\" : \"" + trumpiaAccount.getUniqueId() + "\", \"thirdpartyId\" : \"" + dynamicsAccount.getDynamicsId() + "\", \"post\" : "
				+ postOption + ", \"delete\" : " + deleteOption + ", \"direction\" : \"" + direction + "\"}";
	}
	
}
