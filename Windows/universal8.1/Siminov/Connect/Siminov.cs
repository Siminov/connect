///
/// [SIMINOV FRAMEWORK - CONNECT]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.




using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Siminov.Core;
using Siminov.Connect.Resource;
using Siminov.Core.Exception;
using Siminov.Core.Events;
using Siminov.Connect.Reader;
using Siminov.Connect.Model;
using Siminov.Core.Log;
using Siminov.Core.Reader;
using Siminov.Core.Model;
using Siminov.Connect.Service;

namespace Siminov.Connect
{

    /// <summary>
    /// Exposes methods to deal with SIMINOV FRAMEWORK.
    ///	<p>
    ///		Such As
    ///		<p>
    ///			1. Initializer: Entry point to the SIMINOV.
    ///		</p>
    ///	
    ///		<p>
    ///			2. Shutdown: Exit point from the SIMINOV.
    ///		</p>
    ///	</p> 
    /// </summary>
    public class Siminov : Core.Siminov
    {
	    protected static ResourceManager connectResourceManager = ResourceManager.GetInstance();

	    protected static bool isActive = false;

        /// <summary>
	    /// It is used to check whether SIMINOV FRAMEWORK is active or not.
	    /// <p>
	    /// SIMINOV become active only when deployment of application is successful.
        /// </summary>
        /// <exception cref="Siminov.Connect.Exception.DeploymentException">If SIMINOV is not active it will throw DeploymentException which is RuntimeException.</exception>
	    public static void IsActive() 
        {
		    if(!isActive && !Core.Siminov.isActive) 
            {
			    throw new DeploymentException(typeof(Siminov).Name, "IsActive", "Siminov Not Active.");
		    }
	    }
	
        /// <summary>
	    /// Returns the IInitializer instance.
        /// </summary>
        /// <returns>Instance of IInitializer</returns>
	    public static IInitializer Initializer() 
        {
		    return new Initializer();
	    }

        /// <summary>
	    /// It is the entry point to the SIMINOV FRAMEWORK.
	    /// <p>
	    /// When application starts it should call this method to activate SIMINOV-FRAMEWORK, by providing ApplicationContext as the parameter.
	    /// </p>
	    /// 
	    /// <p>
	    /// Siminov will read all descriptor defined by application, and do necessary processing.
	    /// </p>
	    /// 
	    /// 	EXAMPLE
	    /// 		
	    ///public class Siminov extends Application {
        ///
		///    public void onCreate() { 
		///	    super.onCreate();
        ///	
		///	    initializeSiminov();
		///    }
		///
		///    private void initializeSiminov() {
		///	    siminov.connect.Siminov.initialize(this);
		///    }
        ///}
        /// 
        /// </summary>
        /// <exception cref="Siminov.Core.Exception.DeploymentException">If any exception occur while deploying application it will through DeploymentException, which is RuntimeException.</exception>
	    public static void Start() 
        {
		
		    ProcessApplicationDescriptor();
		
		    ProcessDatabaseDescriptors();
		    ProcessLibraries();
		    ProcessEntityDescriptors();
		    ProcessSyncDescriptors();
		
		    ProcessDatabase();
		
		    ProcessServices();

		
		    isActive = true;
		    Core.Siminov.isActive = true;
		
		    ISiminovEvents coreEventHandler = coreResourceManager.GetSiminovEventHandler();
		    if(coreResourceManager.GetSiminovEventHandler() != null) 
            {
			    if(firstTimeProcessed) 
                {
				    coreEventHandler.OnFirstTimeSiminovInitialized();
			    } 
                else 
                {
				    coreEventHandler.OnSiminovInitialized();
			    }
		    } 
	    }

        /// <summary>
	    /// It is used to stop all service started by SIMINOV.
	    /// <p>
	    /// When application shutdown they should call this. It do following services: 
	    /// <p>
	    /// 		<pre>
	    /// 			<ul>
	    /// 				<li> Close all database's opened by SIMINOV.
	    /// 				<li> Deallocate all resources held by SIMINOV.
	    /// 			</ul>
	    ///		</pre>
	    ///	</p>
	    /// 
        /// </summary>
        /// <exception cref="Siminov.Core.Exception.SiminovException">If any error occur while shutting down SIMINOV.</exception>
	    public static void Shutdown() 
        {
		
		    Core.Siminov.Shutdown();
	    }

        /// <summary>
        /// It process ApplicationDescriptor.xml file defined in Application, and stores in Resource Manager.
        /// </summary>
	    protected static void ProcessApplicationDescriptor() 
        {

            Connect.Reader.ApplicationDescriptorReader applicationDescriptorParser = new Connect.Reader.ApplicationDescriptorReader();

            Connect.Model.ApplicationDescriptor applicationDescriptor = applicationDescriptorParser.GetApplicationDescriptor();
		    if(applicationDescriptor == null) 
            {
			    Log.Debug(typeof(Siminov).Name, "ProcessApplicationDescriptor", "Invalid Application Descriptor Found.");
			    throw new DeploymentException(typeof(Siminov).Name, "ProcessApplicationDescriptor", "Invalid Application Descriptor Found.");
		    }
		
		    coreResourceManager.SetApplicationDescriptor(applicationDescriptor);		
		    connectResourceManager.SetApplicationDescriptor(applicationDescriptor);
	    }
	
        /// <summary>
        /// It process all DatabaseDescriptor.xml files defined by Application and stores in Resource Manager.   
        /// </summary>
	    protected static void ProcessDatabaseDescriptors() 
        {
		    Core.Siminov.ProcessDatabaseDescriptors();
		
		    DatabaseDescriptorReader databaseDescriptorReader = new DatabaseDescriptorReader(Constants.DATABASE_DESSCRIPTOR_PATH);
		    DatabaseDescriptor databaseDescriptor = databaseDescriptorReader.GetDatabaseDescriptor();
		
		    Connect.Model.ApplicationDescriptor applicationDescriptor = connectResourceManager.GetApplicationDescriptor();
		    applicationDescriptor.AddDatabaseDescriptor(Constants.DATABASE_DESSCRIPTOR_PATH, databaseDescriptor);
	    }
        
        /// <summary>
        /// It process all EntityDescriptor.xml file defined in Application, and stores in Resource Manager.
        /// </summary>
        protected static void ProcessEntityDescriptors() 
        {
            Core.Siminov.ProcessEntityDescriptors();
	    }

        /// <summary>
        /// It process all SyncDescriptor.xml file defined in Application, and stores in Resource Manager.
        /// </summary>
	    protected static void ProcessSyncDescriptors() 
        {
		
		    Connect.Model.ApplicationDescriptor applicationDescriptor = connectResourceManager.GetApplicationDescriptor();
		    IEnumerator<String> syncDescriptorPaths = applicationDescriptor.GetSyncDescriptorPaths();
		
		    while(syncDescriptorPaths.MoveNext()) 
            {
			    String syncDescriptorPath = syncDescriptorPaths.Current;
			
			    SyncDescriptorReader syncDescriptorReader = new SyncDescriptorReader(syncDescriptorPath);
			    applicationDescriptor.AddSyncDescriptor(syncDescriptorPath, syncDescriptorReader.GetSyncDescriptor());
		    }
	    }
	
        /// <summary>
        /// It process all LibraryDescriptor.xml files defined by application, and stores in Resource Manager.
        /// </summary>
	    protected static void ProcessLibraries() 
        {
		    Core.Siminov.ProcessLibraries();
	    }
	
        /// <summary>
        /// It process all DatabaseDescriptor.xml and initialize Database and stores in Resource Manager.
        /// </summary>
	    protected static void ProcessDatabase() 
        {
		    Core.Siminov.ProcessDatabase();
	    }
	
        /// <summary>
        /// It starts all Service Workers
        /// </summary>
	    protected static void ProcessServices() 
        {
		
		    IWorker asyncServiceWorker = AsyncServiceWorker.GetInstance();
		    asyncServiceWorker.StartWorker();
	    }
    }
}
