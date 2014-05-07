package siminov.connect.authorization;

import siminov.connect.design.authorization.IAuthorization;
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.model.AuthorizationDescriptor;
import siminov.connect.resource.Resources;
import siminov.orm.utils.ClassUtils;

public class AuthorizationFactory {

	private static AuthorizationFactory authorizationFactory = null;
	private IAuthorization authenticate = null;

	private String AUTHORIZATION_PACKAGE_NAME = "siminov.connect.authorization";
	private String AUTHORIZATION_CLASS_NAME = "Authorization";
	
	
	private AuthorizationFactory() {

	}
	
	public static AuthorizationFactory getInstance() {
		
		if(authorizationFactory == null) {
			authorizationFactory = new AuthorizationFactory();
		}
		
		return authorizationFactory;
	}
	
	public IAuthorization getAuthorization() {
		
		if(authenticate != null) {
			return authenticate;
		}
		
		Resources resources = Resources.getInstance();
		ApplicationDescriptor applicationDescriptor = resources.getApplicationDescriptor();
		
		if(!applicationDescriptor.containAuthenticationDescriptor()) {
			return null;
		}
		
		AuthorizationDescriptor authorizationDescriptor = applicationDescriptor.getAuthorizationDescriptor();

		String type = authorizationDescriptor.getType();
		String packageName = AUTHORIZATION_PACKAGE_NAME + "." + type;
		
		return getAuthenticate(packageName);
	}

	private IAuthorization getAuthenticate(final String packageName) {
		return (IAuthorization) ClassUtils.createClassInstance(packageName + "." + AUTHORIZATION_CLASS_NAME);
	}
}
