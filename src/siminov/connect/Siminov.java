package siminov.connect;

import siminov.connect.model.ConnectDescriptor;
import siminov.connect.reader.ConnectDescriptorReader;
import siminov.connect.resource.Resources;
import siminov.orm.IInitializer;
import siminov.orm.exception.DeploymentException;
import siminov.orm.model.ApplicationDescriptor;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.reader.DatabaseDescriptorReader;

public class Siminov extends siminov.orm.Siminov {

	protected static Resources connectResources = Resources.getInstance();

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
		
		
		
		processDatabaseDescriptor();
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

	protected static void processDatabaseDescriptor() {
		siminov.orm.Siminov.processDatabaseDescriptors();
		
		DatabaseDescriptorReader databaseDescriptorReader = new DatabaseDescriptorReader(Constants.CONNECT_DATABASE_DESSCRIPTOR_PATH);
		DatabaseDescriptor databaseDescriptor = databaseDescriptorReader.getDatabaseDescriptor();
		
		ApplicationDescriptor applicationDescriptor = siminov.orm.resource.Resources.getInstance().getApplicationDescriptor();
		applicationDescriptor.addDatabaseDescriptor(Constants.CONNECT_DATABASE_DESSCRIPTOR_PATH, databaseDescriptor);
	}
	
	protected static void processEvents() {
		
	}
	
	protected static void processKonnectDescriptor() {
		
		ConnectDescriptorReader konnectDescriptorReader = new ConnectDescriptorReader();
		ConnectDescriptor konnectDescriptor = konnectDescriptorReader.getConnectDescriptor();
		
		connectResources.setConnectDescriptor(konnectDescriptor);
		
	}
	
	protected static void processServices() {
		
	}
	
	protected static void processLibraries() {
		
	}
	
}
