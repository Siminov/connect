package siminov.connect;

import java.util.Iterator;

import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.reader.ApplicationDescriptorReader;
import siminov.connect.reader.SyncDescriptorReader;
import siminov.connect.resource.Resources;
import siminov.connect.worker.IWorker;
import siminov.connect.worker.service.AsyncServiceWorker;
import siminov.orm.IInitializer;
import siminov.orm.events.ISiminovEvents;
import siminov.orm.exception.DeploymentException;
import siminov.orm.log.Log;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.reader.DatabaseDescriptorReader;

public class Siminov extends siminov.orm.Siminov {

	protected static Resources connectResources = Resources.getInstance();

	protected static boolean isActive = false;

	public static void isActive() {
		if(!isActive && !siminov.orm.Siminov.isActive) {
			throw new DeploymentException(Siminov.class.getName(), "isActive", "Siminov Not Active.");
		}
	}
	
	
	public static IInitializer initializer() {
		return new Initializer();
	}

	static void start() {
		
		processApplicationDescriptor();
		
		processDatabaseDescriptors();
		processLibraries();
		processDatabaseMappingDescriptors();
		processSyncDescriptors();
		
		processDatabase();
		
		processServices();

		
		isActive = true;
		siminov.orm.Siminov.isActive = true;
		
		ISiminovEvents coreEventHandler = ormResources.getSiminovEventHandler();
		if(ormResources.getSiminovEventHandler() != null) {
			if(firstTimeProcessed) {
				coreEventHandler.onFirstTimeSiminovInitialized();
			} else {
				coreEventHandler.onSiminovInitialized();
			}
		} 
	}

	public static void shutdown() {
		
		siminov.orm.Siminov.shutdown();
	}

	protected static void processApplicationDescriptor() {
		
		ApplicationDescriptorReader applicationDescriptorParser = new ApplicationDescriptorReader();
		
		ApplicationDescriptor applicationDescriptor = applicationDescriptorParser.getApplicationDescriptor();
		if(applicationDescriptor == null) {
			Log.logd(Siminov.class.getName(), "processApplicationDescriptor", "Invalid Application Descriptor Found.");
			throw new DeploymentException(Siminov.class.getName(), "processApplicationDescriptor", "Invalid Application Descriptor Found.");
		}
		
		ormResources.setApplicationDescriptor(applicationDescriptor);		
		connectResources.setApplicationDescriptor(applicationDescriptor);
	}
	
	protected static void processDatabaseDescriptors() {
		siminov.orm.Siminov.processDatabaseDescriptors();
		
		DatabaseDescriptorReader databaseDescriptorReader = new DatabaseDescriptorReader(Constants.CONNECT_DATABASE_DESSCRIPTOR_PATH);
		DatabaseDescriptor databaseDescriptor = databaseDescriptorReader.getDatabaseDescriptor();
		
		ApplicationDescriptor applicationDescriptor = connectResources.getApplicationDescriptor();
		applicationDescriptor.addDatabaseDescriptor(Constants.CONNECT_DATABASE_DESSCRIPTOR_PATH, databaseDescriptor);
	}

	protected static void processDatabaseMappingDescriptors() {
		siminov.orm.Siminov.processDatabaseMappingDescriptors();
	}
	
	protected static void processSyncDescriptors() {
		
		ApplicationDescriptor applicationDescriptor = connectResources.getApplicationDescriptor();
		Iterator<String> syncDescriptorPaths = applicationDescriptor.getSyncDescriptorPaths();
		
		while(syncDescriptorPaths.hasNext()) {
			String syncDescriptorPath = syncDescriptorPaths.next();
			
			SyncDescriptorReader syncDescriptorReader = new SyncDescriptorReader(syncDescriptorPath);
			applicationDescriptor.addSyncDescriptor(syncDescriptorPath, syncDescriptorReader.getSyncDescriptor());
		}
	}
	
	protected static void processLibraries() {
		siminov.orm.Siminov.processLibraries();
	}
	
	protected static void processDatabase() {
		siminov.orm.Siminov.processDatabase();
	}
	
	protected static void processServices() {
		
		IWorker asyncServiceWorker = AsyncServiceWorker.getInstance();
		asyncServiceWorker.startWorker();
	}
}
