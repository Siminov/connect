package siminov.connect.connection;

import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;

public class ConnectionHelper {

	
	public String formUrl(final ServiceDescriptor serviceDescriptor, final API api) {
		
		String protocol = serviceDescriptor.getProtocol();
		
		String instance = serviceDescriptor.getInstance();
		long port = serviceDescriptor.getPort();
		
		String context = serviceDescriptor.getContext();
		
		String apiName = api.getName();
		
		
		return protocol + "://" + instance + ":" + port + "/" + context + "/" + apiName;
	}
	
}
