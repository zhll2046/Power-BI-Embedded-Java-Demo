package azureAD;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.ServiceUnavailableException;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;

public class AzureADUtil {

	private final static String AUTHORITY = "https://login.microsoftonline.com/common/";
	private final static String CLIENT_ID = "2c7b189e-2cab-4039-8a6f-410eb03ebf44";

	public static AuthenticationResult getAccessTokenFromUserCredentials(String username, String password)
			throws Exception {
		AuthenticationContext context;
		AuthenticationResult result;
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(1);
			context = new AuthenticationContext(AUTHORITY, false, service);
			Future<AuthenticationResult> future = context.acquireToken("https://analysis.windows.net/powerbi/api", CLIENT_ID,
					username, password, null);
			result = future.get();
		} finally {
			service.shutdown();
		}

		if (result == null) {
			throw new ServiceUnavailableException("authentication result was null");
		}
		return result;
	}

}
