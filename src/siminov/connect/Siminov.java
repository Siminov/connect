package siminov.connect;

import siminov.connect.model.ConnectDescriptor;
import siminov.connect.reader.ConnectDescriptorReader;
import siminov.connect.resource.Resources;
import siminov.orm.IInitializer;
import siminov.orm.exception.DeploymentException;

public class Siminov extends siminov.orm.Siminov {

	protected static Resources konnectResources = Resources.getInstance();

	protected static boolean isActive = false;

	public static void validateSiminov() {
		if(!isActive && !siminov.orm.Siminov.isActive) {
			throw new DeploymentException(Siminov.class.getName(), "validateSiminov", "Siminov Not Active.");
		}
	}
	
	
	public static IInitializer initialize() {
		return new Initializer();
	}

	static void start() {
		
		siminov.orm.Siminov.processApplicationDescriptor();
		processEvents();
		
		
		siminov.orm.Siminov.processDatabaseDescriptors();
		siminov.orm.Siminov.processLibraries();
		siminov.orm.Siminov.processDatabaseMappingDescriptors();
		siminov.orm.Siminov.processDatabase();
		
		
		processKonnectDescriptor();
		processServices();
		processLibraries();

		
		isActive = true;
		siminov.orm.Siminov.isActive = true;
	}

	public static void shutdown() {
		
		siminov.orm.Siminov.shutdown();
	}

	protected static void processEvents() {
		
	}
	
	protected static void processKonnectDescriptor() {
		
		ConnectDescriptorReader konnectDescriptorReader = new ConnectDescriptorReader();
		ConnectDescriptor konnectDescriptor = konnectDescriptorReader.getConnectDescriptor();
		
		konnectResources.setConnectDescriptor(konnectDescriptor);
		
	}
	
	protected static void processServices() {
		
	}
	
	protected static void processLibraries() {
		
	}
	
}
