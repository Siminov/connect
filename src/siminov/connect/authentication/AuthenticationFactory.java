package siminov.connect.authentication;

import siminov.connect.authentication.design.IAuthenticate;
import siminov.connect.model.AuthenticationDescriptor;
import siminov.connect.model.ConnectDescriptor;
import siminov.connect.resource.Resources;
import siminov.orm.utils.ClassUtils;

public class AuthenticationFactory {

	private static AuthenticationFactory authenticationFactory = null;
	private IAuthenticate authenticate = null;

	private String AUTHENTICATION_PACKAGE_NAME = "siminov.connect.authentication";
	private String AUTHENTICATION_CLASS_NAME = "Authentication";
	
	
	
	private AuthenticationFactory() {

	}
	
	public static AuthenticationFactory getInstance() {
		
		if(authenticationFactory == null) {
			authenticationFactory = new AuthenticationFactory();
		}
		
		return authenticationFactory;
	}
	
	public IAuthenticate getAuthenticate() {
		
		if(authenticate != null) {
			return authenticate;
		}
		
		Resources resources = Resources.getInstance();
		ConnectDescriptor connectDescriptor = resources.getConnectDescriptor();
		
		if(!connectDescriptor.containAuthenticationDescriptor()) {
			return null;
		}
		
		AuthenticationDescriptor authenticationDescriptor = connectDescriptor.getAuthenticationDescriptor();

		String type = authenticationDescriptor.getType();
		String packageName = AUTHENTICATION_PACKAGE_NAME + "." + type;
		
		return getAuthenticate(packageName);
	}

	private IAuthenticate getAuthenticate(String packageName) {
		return (IAuthenticate) ClassUtils.createClassInstance(packageName + "." + AUTHENTICATION_CLASS_NAME);
	}
}
