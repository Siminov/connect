/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package siminov.connect;

import java.util.Iterator;

import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.reader.ApplicationDescriptorReader;
import siminov.connect.reader.SyncDescriptorReader;
import siminov.connect.resource.ResourceManager;
import siminov.connect.service.AsyncServiceWorker;
import siminov.orm.IInitializer;
import siminov.orm.events.ISiminovEvents;
import siminov.orm.exception.DeploymentException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.reader.DatabaseDescriptorReader;


/**
 * Exposes methods to deal with SIMINOV FRAMEWORK.
 *	<p>
 *		Such As
 *		<p>
 *			1. Initializer: Entry point to the SIMINOV.
 *		</p>
 *	
 *		<p>
 *			2. Shutdown: Exit point from the SIMINOV.
 *		</p>
 *	</p>
 */
public class Siminov extends siminov.orm.Siminov {

	protected static ResourceManager connectResourceManager = ResourceManager.getInstance();

	protected static boolean isActive = false;

	/**
	 * It is used to check whether SIMINOV FRAMEWORK is active or not.
	 * <p>
	 * SIMINOV become active only when deployment of application is successful.
	 * 
	 * @exception If SIMINOV is not active it will throw DeploymentException which is RuntimeException.
	 * 
	 */
	public static void isActive() {
		if(!isActive && !siminov.orm.Siminov.isActive) {
			throw new DeploymentException(Siminov.class.getName(), "isActive", "Siminov Not Active.");
		}
	}
	
	
	/**
	 * Returns the IInitializer instance.
	 * @return Instance of IInitializer
	 */
	public static IInitializer initializer() {
		return new Initializer();
	}

	/**
	 * It is the entry point to the SIMINOV FRAMEWORK.
	 * <p>
	 * When application starts it should call this method to activate SIMINOV-FRAMEWORK, by providing ApplicationContext as the parameter.
	 * </p>
	 * 
	 * <p>
	 * Siminov will read all descriptor defined by application, and do necessary processing.
	 * </p>
	 * 
	 * 	EXAMPLE
	 * 		There are two ways to make a call.
	 * 
			<pre> 
	  			<ul>
	  				<li> Call it from Application class.

	public class Siminov extends Application {

		public void onCreate() { 
			super.onCreate();
	
			initializeSiminov();
		}
		
		private void initializeSiminov() {
			siminov.connect.Siminov.initialize(this);
		}

	}
					</li>
					
					<li> Call it from LAUNCHER Activity

	public class SiminovActivity extends Activity {
	
		public void onCreate(Bundle savedInstanceState) {
		
		}

		private void initializeSiminov() {
			siminov.connect.Siminov.initialize(getApplicationContext())
		}

	}
					</li>
				</ul>
			</pre>
	 * @param context Application content.
	 * @exception If any exception occur while deploying application it will through DeploymentException, which is RuntimeException.
	 */
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
		
		ISiminovEvents coreEventHandler = ormResourceManager.getSiminovEventHandler();
		if(ormResourceManager.getSiminovEventHandler() != null) {
			if(firstTimeProcessed) {
				coreEventHandler.onFirstTimeSiminovInitialized();
			} else {
				coreEventHandler.onSiminovInitialized();
			}
		} 
	}

	/**
	 * It is used to stop all service started by SIMINOV.
	 * <p>
	 * When application shutdown they should call this. It do following services: 
	 * <p>
	 * 		<pre>
	 * 			<ul>
	 * 				<li> Close all database's opened by SIMINOV.
	 * 				<li> Deallocate all resources held by SIMINOV.
	 * 			</ul>
	 *		</pre>
	 *	</p>
	 * 
	 * @throws SiminovException If any error occur while shutting down SIMINOV.
	 */
	public static void shutdown() {
		
		siminov.orm.Siminov.shutdown();
	}

	
	/**
	 * It process ApplicationDescriptor.si.xml file defined in Application, and stores in Resources.
	 */
	protected static void processApplicationDescriptor() {
		
		ApplicationDescriptorReader applicationDescriptorParser = new ApplicationDescriptorReader();
		
		ApplicationDescriptor applicationDescriptor = applicationDescriptorParser.getApplicationDescriptor();
		if(applicationDescriptor == null) {
			Log.debug(Siminov.class.getName(), "processApplicationDescriptor", "Invalid Application Descriptor Found.");
			throw new DeploymentException(Siminov.class.getName(), "processApplicationDescriptor", "Invalid Application Descriptor Found.");
		}
		
		ormResourceManager.setApplicationDescriptor(applicationDescriptor);		
		connectResourceManager.setApplicationDescriptor(applicationDescriptor);
	}
	
	/**
	 * It process all DatabaseDescriptor.si.xml files defined by Application and stores in Resources.
	 */
	protected static void processDatabaseDescriptors() {
		siminov.orm.Siminov.processDatabaseDescriptors();
		
		DatabaseDescriptorReader databaseDescriptorReader = new DatabaseDescriptorReader(Constants.DATABASE_DESSCRIPTOR_PATH);
		DatabaseDescriptor databaseDescriptor = databaseDescriptorReader.getDatabaseDescriptor();
		
		ApplicationDescriptor applicationDescriptor = connectResourceManager.getApplicationDescriptor();
		applicationDescriptor.addDatabaseDescriptor(Constants.DATABASE_DESSCRIPTOR_PATH, databaseDescriptor);
	}

	/**
	 * It process all DatabaseMappingDescriptor.si.xml file defined in Application, and stores in Resources.
	 */
	protected static void processDatabaseMappingDescriptors() {
		siminov.orm.Siminov.processDatabaseMappingDescriptors();
	}

	/**
	 * It process all SyncDescriptor.si.xml file defined in Application, and stores in Resources.
	 */
	protected static void processSyncDescriptors() {
		
		ApplicationDescriptor applicationDescriptor = connectResourceManager.getApplicationDescriptor();
		Iterator<String> syncDescriptorPaths = applicationDescriptor.getSyncDescriptorPaths();
		
		while(syncDescriptorPaths.hasNext()) {
			String syncDescriptorPath = syncDescriptorPaths.next();
			
			SyncDescriptorReader syncDescriptorReader = new SyncDescriptorReader(syncDescriptorPath);
			applicationDescriptor.addSyncDescriptor(syncDescriptorPath, syncDescriptorReader.getSyncDescriptor());
		}
	}
	
	/**
	 * It process all LibraryDescriptor.si.xml files defined by application, and stores in Resources.
	 */
	protected static void processLibraries() {
		siminov.orm.Siminov.processLibraries();
	}
	
	/**
	 * It process all DatabaseDescriptor.si.xml and initialize Database and stores in Resources.
	 */
	protected static void processDatabase() {
		siminov.orm.Siminov.processDatabase();
	}
	
	/**
	 * It starts all Service Workers
	 */
	protected static void processServices() {
		
		IWorker asyncServiceWorker = AsyncServiceWorker.getInstance();
		asyncServiceWorker.startWorker();
	}
}
