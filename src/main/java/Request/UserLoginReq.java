package Request;

public class UserLoginReq {

	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String userName;
	private String password;
	private Long storageid;
	

	public UserLoginReq() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getStorageid() {
		return storageid;
	}

	public void setStorageid(Long storageid) {
		this.storageid = storageid;
	}

	public UserLoginReq(String userName, String password, Long storageid) {
		super();
		this.userName = userName;
		this.password = password;
		this.storageid = storageid;

	}

}
