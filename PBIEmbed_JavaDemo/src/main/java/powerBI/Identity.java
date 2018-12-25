package powerBI;

import flexjson.JSON;

public class Identity {
	
	
	private String username;
	private String[] roles;
	private String[] datasets;
	
	public Identity(String username, String[] roles, String[] datasets) {
		super();
		this.username = username;
		this.roles = roles;
		this.datasets = datasets;
	}
	
	@JSON
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	@JSON
	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}	

	@JSON
	public String[] getDatasets() {
		return datasets;
	}

	public void setDatasets(String[] datasets) {
		this.datasets = datasets;
	}
	
	
	 
	

}
