package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sa_user", schema = "LoginTask")
public class User {
	public Long getRecid() {
		return recid;
	}

	public void setRecid(Long recid) {
		this.recid = recid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long recid;

	public int getStorageid() {
		return storageid;
	}

	public void setStorageid(int storageid) {
		this.storageid = storageid;
	}

	@Column(name = "name")
	private String name;

	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	@Column(name = "storageid")
	private int storageid;
	@Column(name = "type")
	private boolean type;

	public boolean getType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
