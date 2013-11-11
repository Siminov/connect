package siminov.connect;

import java.util.Iterator;

import siminov.connect.model.ConnectDescriptor;
import siminov.connect.reader.ConnectDescriptorReader;
import siminov.connect.reader.ConnectLibraryDescriptorReader;
import siminov.connect.resource.Resources;
import siminov.orm.IInitializer;
import siminov.orm.exception.DeploymentException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

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
		
		connectResources.setConnectDescriptor(konnectDescriptor);
		
	}
	
	protected static void processServices() {
		
	}
	
	protected static void processLibraries() {
		
		ConnectDescriptor connectDescriptor = connectResources.getConnectDescriptor();
		Iterator<String> libraryPaths = connectDescriptor.getLibraryPaths();

		while(libraryPaths.hasNext()) {
			String libraryPath = libraryPaths.next();
			
			/*
			 * Parse LibraryDescriptor.
			 */
			ConnectLibraryDescriptorReader libraryDescriptorParser = null;
			
			try {
				libraryDescriptorParser = new ConnectLibraryDescriptorReader(libraryPath);
			} catch(SiminovException se) {
				Log.loge(Siminov.class.getName(), "processLibraries", "SiminovException caught while parsing library descriptor, LIBRARY-NAME: " + libraryPath + ", " + se.getMessage());
				throw new DeploymentException(Siminov.class.getName(), "processLibraries", se.getMessage());
			}
			
			connectDescriptor.addLibrary(libraryPath, libraryDescriptorParser.getLibraryDescriptor());
		}
	}
	
}
