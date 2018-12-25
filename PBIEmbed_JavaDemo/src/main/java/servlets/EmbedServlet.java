package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.aad.adal4j.AuthenticationResult;

import azureAD.AzureADUtil;
import flexjson.JSONSerializer;
import powerBI.Identity;
import powerBI.PBIUtil;
import powerBI.RLS;

/**
 * Servlet implementation class EmbedServlet
 */
public class EmbedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmbedServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 

		// Azure AD authentication parameters
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		
		//Embed Type
		String embedType = request.getParameter("embedOption"); 
		
		// embedded report/dashboard/tile parameters
		String groupId = request.getParameter("groupId");
		String reportId = request.getParameter("reportId");
		String dashboardId = request.getParameter("dashboardId");
		String tileId = request.getParameter("tileId");

		// row level security parameters in embedded report
		String user = request.getParameter("user");
		String[] roles = request.getParameter("roles").equals("") ? null : request.getParameter("roles").split(",");
		String[] datasetId = request.getParameter("datasetId").equals("") ? null
				: request.getParameter("datasetId").split(",");
		RLS rlsSetting = null;

		try {

			// get accesstoken with Azure AD authentication parameters
			AuthenticationResult result = AzureADUtil.getAccessTokenFromUserCredentials(account, password);

			JSONSerializer serializer = new JSONSerializer();
			String rlsSettingStr = "";
			// generate RLS parameter identities
			// https://docs.microsoft.com/en-us/rest/api/power-bi/embedtoken/reports_generatetoken
			Identity identity = new Identity(user, roles, datasetId);
			rlsSetting = new RLS();
			rlsSetting.putIdentities(identity);

			// if RLS setting is specified, generate a json including identities
			if ((!user.isEmpty()) && (roles != null) && (datasetId != null)) {
				rlsSettingStr = serializer.exclude("*.class").serialize(rlsSetting);
			}
			// if RLS setting is not specified, generate a json excluding identities
			else {
				rlsSettingStr = serializer.exclude("*.class").exclude("identities").serialize(rlsSetting);
			}

			System.out.println(rlsSettingStr);
			// get embedded token with report and RLS parameters and accesstoken
 
			System.out.println(result.getAccessToken());
			
			String embedToke="";

			System.out.println(embedType);
			if(embedType.equals("report")) {
				
				embedToke=PBIUtil.generateReportEmbedToken(groupId, reportId, result.getAccessToken(), rlsSettingStr);
				
			}
			else if (embedType.equals("dashboard")) {
				
				embedToke=PBIUtil.generateDashboardEmbedToken(groupId, dashboardId, result.getAccessToken(), rlsSettingStr);
			}
			else
			{
				embedToke=PBIUtil.generateTileEmbedToken(groupId, dashboardId, tileId, result.getAccessToken(), rlsSettingStr);
			} 
			
			PrintWriter pw=response.getWriter();
			response.setContentType("text/html");
			pw.println(embedToke);  
			
			System.out.println(result.getAccessToken()); 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
