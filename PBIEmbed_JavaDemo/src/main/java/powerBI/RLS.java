package powerBI;

import java.util.ArrayList;
import java.util.List;

import flexjson.JSON;

public class RLS {
	 
	private String accessLevel;
	private List<Identity> identities;	
	
	public RLS() {
		super();
		this.identities = new ArrayList<Identity>();
		this.accessLevel = "View";
	} 
	
	@JSON
	public List<Identity> getIdentities() {
		return identities;
	}


	public void setIdentities(List<Identity> identities) {
		this.identities = identities;
	}
	
	@JSON
	public String getAccesslevel() {
		return accessLevel;
	}

	public void putIdentities(Identity identity) {
		this.identities.add(identity);
	}	
}
