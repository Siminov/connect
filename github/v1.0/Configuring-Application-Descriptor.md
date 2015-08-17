Application Descriptor is the one which connects application to Siminov framework. It provide basic information about application, which defines the behaviour of application.

```xml

                    <!-- Design Of ApplicationDescriptor.si.xml -->
    <siminov>
    
        <!-- General Application Description Properties -->
            <!-- Mandatory Field -->
        <property name="name">application_name</property>	
	
            <!-- Optional Field -->
        <property name="description">application_description</property>
	
            <!-- Mandatory Field (Default is 0.0) -->
        <property name="version">application_version</property>


        <!-- Database Descriptors Used By Application (zero-to-many) -->	
            <!-- Optional Fields -->
        <database-descriptors>
            <database-descriptor>full_path_of_database_descriptor_file</database-descriptor>
        </database-descriptors>
	
	
        <!-- Service Descriptors -->
        <service-descriptors>
  		
            <!-- Service Descriptor -->
            <service-descriptor>full_path_of_service_descriptor</service-descriptor>
    
        </service-descriptors>

	
        <!-- Sync Descriptors -->
            <!-- Sync Descriptor -->
        <sync-descriptors>
        
            <sync-descriptor>full_path_of_sync_descriptor</sync-descriptor>
        
        </sync-descriptors>
    

        <!-- Notification Descriptor -->
        <notification-descriptor>
        
                <!-- Optional Field -->
            <property name="name_of_property">value_of_property</property>

        </notification-descriptor>
		
	
        <!-- Library Descriptors Used By Application (zero-to-many) -->
            <!-- Optional Fields -->
        <library-descriptors>
            <library-descriptor>full_path_of_library_descriptor_file</library-descriptor>   
        </librar-descriptors>
	
		
        <!-- Event Handlers Implemented By Application (zero-to-many) -->
            <!-- Optional Fields -->
        <event-handlers>
            <event-handler>full_class_path_of_event_handler_(ISiminovHandler/IDatabaseHandler)</event-handler>
        </event-handlers>

    </siminov>

```

```xml

                  <!-- Android Sample: ApplicationDescriptor.si.xml -->

    <siminov>

        <property name="name">SIMINOV CONNECT SAMPLE</property>	
        <property name="description">Siminov Connect Sample Application</property>
        <property name="version">0.9</property>

        <!-- Database Descriptors -->
        <database-descriptors>
            <database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
        </database-descriptors>

	
        <!-- Service Descriptors -->
        <service-descriptors>
            <service-descriptor>Liquor-Services/Liquors.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/LiquorBrands.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/NotificationService.si.xml</service-descriptor>
        </service-descriptors>
	
	
        <!-- Sync Descriptors -->
        <syn-descriptors>
            <sync-descriptor>Liquor-Sync/Liquors.si.xml</sync-descriptor>
            <sync-descriptor>Liquor-Sync/LiquorBrands.si.xml</sync-descriptor>
        </syn-descriptors>
	

        <!-- Notification Descriptor -->
        <notification-descriptor>
            <property name="sender_id">251333150904</property>
        </notification-descriptor>
    
		
        <!-- Event Handlers -->
        <event-handlers>
            <event-handler>siminov.connect.sample.events.SiminovEventHandler</event-handler>
            <event-handler>siminov.connect.sample.events.DatabaseEventHandler</event-handler>
            <event-handler>siminov.connect.sample.events.NotificationEventHandler</event-handler>
        </event-handlers>

    </siminov>


```


```xml
								
               <!-- iOS Sample: ApplicationDescriptor.si.xml -->

    <siminov>

        <property name="name">SIMINOV CONNECT SAMPLE</property>	
        <property name="description">Siminov Connect Sample Application</property>
        <property name="version">0.9</property>

        <!-- Database Descriptors -->
        <database-descriptors>
            <database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
        </database-descriptors>

	
        <!-- Service Descriptors -->
        <service-descriptors>
            <service-descriptor>Liquor-Services/Liquors.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/LiquorBrands.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/NotificationService.si.xml</service-descriptor>
        </service-descriptors>
	
	
        <!-- Sync Descriptors -->
        <syn-descriptors>
            <sync-descriptor>Liquor-Sync/Liquors.si.xml</sync-descriptor>
            <sync-descriptor>Liquor-Sync/LiquorBrands.si.xml</sync-descriptor>
        </syn-descriptors>
	

        <!-- Notification Descriptor -->
        <notification-descriptor>
            <property name="sender_id">251333150904</property>
        </notification-descriptor>

    	
        <!-- Event Handlers -->
        <event-handlers>
            <event-handler>SiminovEventHandler</event-handler>
            <event-handler>DatabaseEventHandler</event-handler>
            <event-handler>NotificationEventHandler</event-handler>
        </event-handlers>

    </siminov>


```


```xml
	
                  <!-- Windows Sample: ApplicationDescriptor.si.xml -->

    <siminov>

        <property name="name">SIMINOV CONNECT SAMPLE</property>	
        <property name="description">Siminov Connect Sample Application</property>
        <property name="version">0.9</property>

        <!-- Database Descriptors -->
        <database-descriptors>
            <database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
        </database-descriptors>

	
        <!-- Service Descriptors -->
        <service-descriptors>
            <service-descriptor>Liquor-Services/Liquors.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/LiquorBrands.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/NotificationService.si.xml</service-descriptor>
        </service-descriptors>
	
	
        <!-- Sync Descriptors -->
        <syn-descriptors>
            <sync-descriptor>Liquor-Sync/Liquors.si.xml</sync-descriptor>
            <sync-descriptor>Liquor-Sync/LiquorBrands.si.xml</sync-descriptor>
        </syn-descriptors>
	

        <!-- Notification Descriptor -->
        <notification-descriptor>
            <property name="sender_id">251333150904</property>
        </notification-descriptor>

		
        <!-- Event Handlers -->
        <event-handlers>
            <event-handler>Siminov.Connect.Sample.Events.SiminovEventHandler</event-handler>
            <event-handler>Siminov.Connect.Sample.Events.DatabaseEventHandler</event-handler>
            <event-handler>Siminov.Connect.Sample.Events.NotificationEventHandler</event-handler>
        </event-handlers>

    </siminov>


```



> **Note**: Application Developer can provide their own properties also, and by using following API's they can use properties.
>
> - **Get Properties - [Android:getProperties | iOS:getProperties | Windows:getProperties]**: It will return all properties associated with Application Descriptor.
>
> - **Get Property - [Android:getProperty(Name-of-Property) | iOS:getProperty:Name-of-Property | Windows:GetProperty(Name-of-Property)]**: It will return property value associated with property name provided.
>
> - **Contains Property - [Android:containsProperty(Name-of-Property) | iOS:containsProperty:Name-of-Property) | Windows:ContainsProperty(Name-of-Property)]**: It will return TRUE/FALSE whether property exists or not.
>
> - **Add Property - [Android:addProperty(Name-of-Property, Value-of-Property) | iOS:addProperty:Name-of-Property value:Value-of-Property | Windows:AddProperty(Name-of-Property, Value-of-Property)]**: It will add new property to the  collection of Application Descriptor properties.
>
> - **Remove Property - [Android:removeProperty(Name-of-Property) | iOS:removeProperty:Name-of-Property | Windows:RemoveProperty(Name-of-Property)]**: It will remove property from Application Descriptor properties based on name provided.



## Application Descriptor Elements

###### 1. General properties about application

- _**name**_*: Name of application. It is mandatory field. If any resources is created by Siminov Framework then it will be under this folder name.


**Android Sample - Application data folder based on name defined**

***

![Application Data Folder Based On Name Defined] (https://raw.githubusercontent.com/Siminov/android-connect/docs/github/v1.0/siminov_connect_sample_application_data_folder_structure_for_application_name.png "Application Data Folder Based On Name Defined")

***

**iOS Sample - Application data folder based on name defined**

***

![Application Data Folder Based On Name Defined] (https://raw.githubusercontent.com/Siminov/docs/github/v1.0/siminov_connect_sample_application_data_folder_structure_for_application_name.png "Application Data Folder Based On Name Defined")

***

**Windows Sample - Application data folder based on name defined**

***

![Application Data Folder Based On Name Defined] (https://raw.githubusercontent.com/Siminov/windows-connect/docs/github/v1.0/siminov_connect_sample_application_data_folder_structure_for_application_name.png "Application Data Folder Based On Name Defined")

***

- _**descriptor**_: Description of application. It is optional field. 

- _**version**_: Version of application. It is mandatory field. Default is 0.0.

###### 3. Paths of database descriptor's used in application
- Path of all database descriptor's used in application.
- Every database descriptor will have its own database object.


###### 4. Path of service descriptor's used in application
- Path of all service descriptor's used in application.


###### 5. Path of sync descriptor's used in application
- Path of all sync descriptor's used in application.


###### 6. Properties of notification
Push notifications let your application notify a user of new messages or events even when the user is not actively using your application. When the user taps the notification, they are sent to your application.		


###### 4. Paths of all library descriptors needed by the application.

> **Note**: 
> - Provide full package name under which LibraryDescriptor.si.xml file is placed.
>
> - Connect will automatically read LibraryDescriptor.si.xml file defined under package name provided.

###### 5. Event handlers implemented by application

- Siminov Framework provides two type of event handlers

- **Android:ISyncEvents | iOS:SICISyncEvents | Windows:ISyncEvents**:
It contain events associated with sync operations. such as **On Sync Start**, **On Sync Queued**, **On Sync Finished**, **On Sync Terminated**

- **Android:INotificationEvents | iOS:SICINotificationEvents | Windows:INotificationEvents**:
It contain events associated with notification. such as **On Notification Registered**, **On Notification Unregistered**, **On Notification Message**, **On Notification Error**

- Application can implement these event handlers based on their requirement.

> **Note**
>
> - Application descriptor file name should always be same as ApplicationDescriptor.si.xml only.
>
> - It should always be in root folder of application assets.


## Android Sample: Application Descriptor

***
![Siminov Connect Sample Application] (https://raw.githubusercontent.com/Siminov/android-connect/docs/github/v1.0/siminov_connect_sample_service_descriptor_path_example.png "Siminov Connect Sample Application")
***

## iOS Sample: Application Descriptor

***
![Siminov Connect Sample Application] (https://raw.githubusercontent.com/Siminov/ios-connect/docs/github/v1.0/siminov_connect_sample_service_descriptor_path_example.png "Siminov Connect Sample Application")
***


## Windows Sample: Application Descriptor

***
![Siminov Connect Sample Application] (https://raw.githubusercontent.com/Siminov/windows-connect/docs/github/v1.0/siminov_connect_sample_service_descriptor_path_example.png "Siminov Connect Sample Application")
***

