package siminov.connect;

import java.util.Iterator;
import java.util.Map;

import siminov.connect.authentication.Credential;
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.reader.ApplicationDescriptorReader;
import siminov.connect.resource.Resources;
import siminov.orm.IInitializer;
import siminov.orm.database.DatabaseBundle;
import siminov.orm.database.design.IDatabase;
import siminov.orm.database.design.IQueryBuilder;
import siminov.orm.events.ISiminovEvents;
import siminov.orm.exception.DatabaseException;
import siminov.orm.exception.DeploymentException;
import siminov.orm.log.Log;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor.Relationship;
import siminov.orm.reader.DatabaseDescriptorReader;
import siminov.orm.reader.DatabaseMappingDescriptorReader;

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
		
		processDatabase();
		
		
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
	
	protected static void processLibraries() {
		siminov.orm.Siminov.processLibraries();
	}
	
	protected static void processDatabase() {

		ApplicationDescriptor applicationDescriptor = connectResources.getApplicationDescriptor();
		if(siminov.orm.Siminov.firstTimeProcessed) {
			
			Iterator<DatabaseDescriptor> databaseDescriptors = applicationDescriptor.getDatabaseDescriptors();
			while(databaseDescriptors.hasNext()) {
				
				DatabaseDescriptor databaseDescriptor = databaseDescriptors.next();
				Iterator<DatabaseMappingDescriptor> databaseMappingDescriptors = databaseDescriptor.getDatabaseMappingDescriptors();
				while(databaseMappingDescriptors.hasNext()) {
					
					DatabaseMappingDescriptor databaseMappingDescriptor = databaseMappingDescriptors.next();
					Iterator<Relationship> relationships = databaseMappingDescriptor.getRelationships();
					
					boolean foundCredentialRelationship = false;
					while(relationships.hasNext()) {
						
						Relationship relationship = relationships.next();
						if(relationship.getReferTo().equalsIgnoreCase(Credential.class.getName())) {

							/*
							 * Add Credential Descriptor
							 */
							databaseDescriptor.addDatabaseMappingDescriptorPath(Constants.CONNECT_CREDENTIAL_DESCRIPTOR_PATH);

							DatabaseMappingDescriptorReader databaseDescriptorReader = new DatabaseMappingDescriptorReader(Constants.CONNECT_CREDENTIAL_DESCRIPTOR_PATH);	
							DatabaseMappingDescriptor credentialDatabaseDescriptor = databaseDescriptorReader.getDatabaseMappingDescriptor();
							
							databaseDescriptor.addDatabaseMappingDescriptor(Constants.CONNECT_CREDENTIAL_DESCRIPTOR_PATH, credentialDatabaseDescriptor);

							
							/*
							 * Add Credential Resource Descriptor
							 */
							databaseDescriptor.addDatabaseMappingDescriptorPath(Constants.CONNECT_CREDENTIAL_RESOURCE_DESCRIPTOR_PATH);

							databaseDescriptorReader = new DatabaseMappingDescriptorReader(Constants.CONNECT_CREDENTIAL_RESOURCE_DESCRIPTOR_PATH);	
							DatabaseMappingDescriptor credentialResourceDatabaseDescriptor = databaseDescriptorReader.getDatabaseMappingDescriptor();
							
							databaseDescriptor.addDatabaseMappingDescriptor(Constants.CONNECT_CREDENTIAL_RESOURCE_DESCRIPTOR_PATH, credentialResourceDatabaseDescriptor);
							
							
							foundCredentialRelationship = true;
							break;
						}
					}
					
					if(foundCredentialRelationship) {
						break;
					}
				}
			}

		
			siminov.orm.Siminov.processDatabase();
		} else {
			siminov.orm.Siminov.processDatabase();
			
			Iterator<DatabaseDescriptor> databaseDescriptors = applicationDescriptor.getDatabaseDescriptors();
			while(databaseDescriptors.hasNext()) {
				
				DatabaseDescriptor databaseDescriptor = databaseDescriptors.next();
				DatabaseBundle databaseBundle = siminov.orm.resource.Resources.getInstance().getDatabaseBundle(databaseDescriptor.getDatabaseName());
			
				IDatabase database = databaseBundle.getDatabase();
				IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
				
				String fetchTableNamesQuery = queryBuilder.formTableNames(null);
				
				Iterator<Map<String, Object>> datas = null;
				try {
					datas = database.executeFetchQuery(databaseDescriptor, null, fetchTableNamesQuery);
				} catch(DatabaseException de) {
					Log.loge(Siminov.class.getName(), "processDatabaseMappingDescriptors", "DatabaseException caught while getting table names, " + de.getMessage());
					throw new DeploymentException(Siminov.class.getName(), "processDatabaseMappingDescriptors", de.getMessage());
				}
				
				
				while(datas.hasNext()) {
					Map<String, Object> data = datas.next();
					Iterator<String> keys = data.keySet().iterator();

					boolean foundCredentialTable = false;
					while(keys.hasNext()) {
						String key = keys.next();
								
						if(key.equals(siminov.orm.Constants.FORM_TABLE_NAMES_NAME)) {

							if(((String) data.get(key)).equalsIgnoreCase(Credential.TABLE_NAME)) {
								databaseDescriptor.addDatabaseMappingDescriptorPath(Constants.CONNECT_CREDENTIAL_DESCRIPTOR_PATH);
								databaseDescriptor.addDatabaseMappingDescriptorPath(Constants.CONNECT_CREDENTIAL_RESOURCE_DESCRIPTOR_PATH);
								
								foundCredentialTable = true;
								break;
							}
						}
						
						if(foundCredentialTable) {
							break;
						}
					}
				}
			}
		}
	}
}
