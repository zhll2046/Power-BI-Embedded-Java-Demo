package powerBI;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
 
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class PBIUtil {
	
	private static final String PBI_URL = "https://api.powerbi.com/v1.0/myorg"; 
	
	
	public static String generateReportEmbedToken(String groupId, String reportId, String accessToken, String rlsSetting) {
		 
		String targetURL= PBI_URL;
		if(!groupId.isEmpty()) {
			
			 targetURL = targetURL +"/groups/"+ groupId;
		} 
		targetURL = targetURL + "/reports/" + reportId +"/GenerateToken";
		

		System.out.println("Report  "+targetURL);		
		
		//generate POST body
		return sendPostRequest(targetURL, accessToken, rlsSetting); 
		 
	}
	
	public static String generateDashboardEmbedToken(String groupId, String dashboardId, String accessToken, String rlsSetting) {

		String targetURL= PBI_URL;
		
		if(!groupId.isEmpty()) {
			
			targetURL = targetURL +"/groups/"+ groupId;
		} 
		
		targetURL = targetURL + "/dashboards/" + dashboardId +"/GenerateToken";
		
		System.out.println("Dashboard  "+targetURL);		
		//generate POST body
		//String data = new createObjectBuilder().add("accessLevel","View").put("identities","ddd").toString();;  
		return  sendPostRequest(targetURL, accessToken, rlsSetting); 
		 
	}
	
	
	public static String generateTileEmbedToken(String groupId, String dashboardId, String tileId, String accessToken, String rlsSetting) {
		 
		String targetURL= PBI_URL;
		
		if(!groupId.isEmpty()) {
			
			targetURL = targetURL +"/groups/"+ groupId;
		} 
		targetURL = targetURL + "/dashboards/" + dashboardId+"/tiles/"+ tileId +"/GenerateToken";
		

		System.out.println("Tile  "+targetURL);		
		
		//generate POST body
		//String data = new createObjectBuilder().add("accessLevel","View").put("identities","ddd").toString();;  
		return  sendPostRequest(targetURL, accessToken, rlsSetting); 
		 
	}
	
	private static String sendPostRequest(String TargetURL, String accessToken, String rlsSetting) {
		
		
		String urlParameters  = rlsSetting;
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length; 
		HttpURLConnection conn=	null;
		try {

			URL    url            = new URL( TargetURL );		
			conn= (HttpURLConnection) url.openConnection();           
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/json"); 
			conn.setRequestProperty( "charset", "utf-8");
			conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
			conn.setRequestProperty( "Authorization", "Bearer "+ accessToken);
			conn.setUseCaches( false );
			conn.getOutputStream().write(postData); 
			
			int responseCode = conn.getResponseCode();
			System.out.println(responseCode);
			
		    if (responseCode == 200) {
		    	//read response lines
		         BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		         StringBuilder response = new StringBuilder();
		         String line;
		         while ((line = reader.readLine()) != null) {
		          response.append(line); 
		         }
		           
		         JSONObject responseJSON = (JSONObject) JSONValue.parse(response.toString());
		         //get embed token from the repsonse
		         return responseJSON.get("token").toString() ;   
		    }
			
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null; 
		
	}
	
	
	
	

}
