Service Descrptor is one which define the structure of your RESTful Web Service API

```xml

                  <!-- Design Of ServiceDescriptor.si.xml -->
    <service-descriptor>
    
        <!-- General Service Properties -->
            <!-- Mandatory Field -->
        <property name="name">name_of_service</property>
    
             <!-- Optional Field -->
        <property name="description">description_of_service</property>
    
             <!-- Optional Field (DEFAULT: HTTP) -->
        <property name="protocol">HTTP|HTTPS</property>
    
             <!-- Mandatory Field -->
        <property name="instance">address_of_instance</property>
    
             <!-- Optional Field -->
        <property name="port">port_number</property>

             <!-- Optional Field -->
        <property name="context">context_of_service</property>
         
        <!-- Requests -->
            <!-- Request -->
        <request>
            
            <request>

                <!-- General Request Properties -->
                    <!-- Mandatory Field -->
                <property name="name">name_of_request</property>
         
                    <!-- Mandatory Field -->
                <property name="type">GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT|PATCH</property>

                    <!-- Mandatory Field -->
                <property name="api">full_request_path</property>

                    <!-- Mandatory Field -->
                <property name="handler">handler_of_request</property>
         	
                    <!-- Optional Field (DEFAULT: SYNC)-->
                <property name="mode">SYNC|ASYNC</property>

      					
                <!-- Query Parameters -->
                    <!-- Query Parameter -->
                <query-parameters>
          
                    <query-parameter>
      			    
                            <!-- Mandatory Field -->
                        <property name="name">name_of_query_parameter</property>
      			    
                            <!-- Mandatory Field -->
                        <property name="value">value_of_query_parameter</property>
      			    
                    </query-parameter>
      		
                </query-parameters>
      
      
                <!-- Header Parameters -->
                    <!-- Header Parameter -->
                <header-parameters>
          
                    <header-parameter>
      			    
                            <!-- Mandatory Field -->
                        <property name="name">name_of_header_parameter</property>
      			    
                            <!-- Mandatory Field -->
                        <property name="value">value_of_header_parameter</property>
      			    
                    </header-parameter>
      		
                </header-parameters>

      		
                <!-- Stream of Data Under Request Body -->
                    <!-- It is Optional Property -->
                <data-stream>stream_of_data</data-stream>	
     	
            </request>
        </requests>
     
    </service-descriptor>


```


```xml

                    <!-- Android Sample: Service Descriptor -->

    <service-descriptor>
    
        <property name="name">SIMINOV-CONNECT-LIQUORS-SERVICE</property>
        <property name="description">Siminov Connect Liquors Service</property>
        <property name="protocol">HTTP</property>
        <property name="instance">10.0.2.2</property>
        <property name="port">8080</property>
        <property name="context">CONNECT-ENTERPRISE</property>    

        <requests>
            <request>

                <property name="name">GET-LIQUORS</property>
                <property name="type">GET</property>
                <property name="api">get-liquors</property>
                <property name="handler">siminov.connect.sample.services.GetLiquors</property>
                <property name="mode">SYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                </header-parameters>
     	
            </request>
            <request>

                <property name="name">ADD-LIQUOR</property>
                <property name="type">POST</property>
                <property name="api">add-liquor</property>
                <property name="handler">siminov.connect.sample.services.AddLiquor</property>
                <property name="mode">ASYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                </header-parameters>
     	
            </request>
            <request>

                <property name="name">MODIFY-LIQUOR</property>
                <property name="type">PUT</property>
                <property name="api">modify-liquor</property>
                <property name="handler">siminov.connect.sample.services.ModifyLiquor</property>
                <property name="mode">ASYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                </header-parameters>
     	
            </request>
            <request>

                <property name="name">DELETE-LIQUOR</property>
                <property name="type">DELETE</property>
                <property name="api">delete-liquor</property>
                <property name="handler">siminov.connect.sample.services.DeleteLiquor</property>
                <property name="mode">ASYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                    <header-parameter name="name">{@resource LIQUOR_NAME}</header-parameter>
                </header-parameters>
     	
            </request>
        </requests>
    
    </service-descriptor>

```


```xml

                   <!-- iOS Sample: Service Descriptor -->
    <service-descriptor>
    
        <property name="name">SIMINOV-CONNECT-LIQUORS-SERVICE</property>
        <property name="description">Siminov Connect Liquors Service</property>
        <property name="protocol">HTTP</property>
        <property name="instance">10.0.2.2</property>
        <property name="port">8080</property>
        <property name="context">CONNECT-ENTERPRISE</property>    

        <requests>
            <request>

                <property name="name">GET-LIQUORS</property>
                <property name="type">GET</property>
                <property name="api">get-liquors</property>
                <property name="handler">GetLiquors</property>
                <property name="mode">SYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                </header-parameters>
     	
            </request>
            <request>

                <property name="name">ADD-LIQUOR</property>
                <property name="type">POST</property>
                <property name="api">add-liquor</property>
                <property name="handler">AddLiquor</property>
                <property name="mode">ASYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                </header-parameters>
     	
            </request>
            <request>

                <property name="name">MODIFY-LIQUOR</property>
                <property name="type">PUT</property>
                <property name="api">modify-liquor</property>
                <property name="handler">ModifyLiquor</property>
                <property name="mode">ASYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                </header-parameters>
     	
            </request>
            <request>

                <property name="name">DELETE-LIQUOR</property>
                <property name="type">DELETE</property>
                <property name="api">delete-liquor</property>
                <property name="handler">DeleteLiquor</property>
                <property name="mode">ASYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                    <header-parameter name="name">{@resource LIQUOR_NAME}</header-parameter>
                </header-parameters>
     	
            </request>
        </requests>
    
    </service-descriptor>

```



```xml

                 <!-- Windows Sample: Service Descriptor -->
    <service-descriptor>
    
        <property name="name">SIMINOV-CONNECT-LIQUORS-SERVICE</property>
        <property name="description">Siminov Connect Liquors Service</property>
        <property name="protocol">HTTP</property>
        <property name="instance">10.0.2.2</property>
        <property name="port">8080</property>
        <property name="context">CONNECT-ENTERPRISE</property>    

        <requests>
            <request>

                <property name="name">GET-LIQUORS</property>
                <property name="type">GET</property>
                <property name="api">get-liquors</property>
                <property name="handler">Siminov.Connect.Sample.Services.GetLiquors</property>
                <property name="mode">SYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                </header-parameters>
     	
            </request>
            <request>

                <property name="name">ADD-LIQUOR</property>
                <property name="type">POST</property>
                <property name="api">add-liquor</property>
         	<property name="handler">Siminov.Connect.Sample.Services.AddLiquor</property>
	     	<property name="mode">ASYNC</property>

	      	<header-parameters>
	        	<header-parameter name="Accept">application/xml</header-parameter>
	          	<header-parameter name="Content-Type">application/xml</header-parameter>
	      	</header-parameters>
     	
            </request>
            <request>

                <property name="name">MODIFY-LIQUOR</property>
                <property name="type">PUT</property>
                <property name="api">modify-liquor</property>
                <property name="handler">Siminov.Connect.Sample.Services.ModifyLiquor</property>
                <property name="mode">ASYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                </header-parameters>
     	
            </request>
            <request>

                <property name="name">DELETE-LIQUOR</property>
                <property name="type">DELETE</property>
                <property name="api">delete-liquor</property>
                <property name="handler">Siminov.Connect.Sample.Services.DeleteLiquor</property>
                <property name="mode">ASYNC</property>

                <header-parameters>
                    <header-parameter name="Accept">application/xml</header-parameter>
                    <header-parameter name="Content-Type">application/xml</header-parameter>
                    <header-parameter name="name">{@resource LIQUOR_NAME}</header-parameter>
                </header-parameters>
     	
            </request>
        </requests>
    
    </service-descriptor>

```


> **Note**: Application Developer can provide their own properties also, and by using following API's they can use properties.
>
> - **Get Properties - [Android:getProperties | iOS:getProperties | Windows:getProperties]**: It will return all properties associated with Application Descriptor.
>
> - **Get Property - [Android:getProperty(Name-of-Property) | iOS:getProperty:Name-of-Property | Windows:GetProperty(Name-of-Property)]**: It will return property value associated with property name provided.
>
> - **Contains Property - [Android:containsProperty(Name-of-Property) | iOS:containsProperty:Name-of-Property | Windows:containsProperty(Name-of-Property)]**: It will return TRUE/FALSE whether property exists or not.
>
> - **Add Property - [Android:addProperty(Name-of-Property, Value-of-Property) | iOS:addProperty:Name-of-Property  value:Value-of-Property) | Windows:AddProperty(Name-of-Property, Value-of-Property)]**: It will add new property to the collection of Application Descriptor properties.
>
> - **Remove Property - [Android:removeProperty(Name-of-Property) | iOS:removeProperty(Name-of-Property) | Windows:RemoveProperty(Name-of-Property)]**: It will remove property from Application Descriptor properties based on name provided.


## Service Descriptor Elements

###### 1. General properties about service: It contain common properties to a service.

- _**name**_*: Name of the service. It is Mandatory property.

- _**descriptor**_: Description of the service. It is optional property.

- _**protocol**_: HTTP/HTTPS. Protocol used by the service. It is optional property. Default value is HTTP 

- _**instance**_*: Instance (IP Address) of the service. It is mandatory property.

- _**name**_*: Name of the service. It is Mandatory property.

- _**port**_: Port number used by the service. It is optional property.

- _**context**_: Context used by the service. It is optional property.	


###### 2. Request: It defines the structure Web Service API

**Properties**: Properties about the Request

- _**name**_*: Name of the Request. It is mandatory property.

- _**type**_*: GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT|PATCH. Type of API. It is mandatory property.

- _**api**_*: Path of API. It is mandatory field.

- _**handler**_: Handler of the API. It is mandatory property.

- _**mode**_: SYNC|ASYNC. Mode of API. Is is optional property. Default is SYNC.


**Query Parameters Properties**: It is key|value pair property which define the query parameter, if any associated to the API.	

- _**name**_*: Name of Query Parameter. It is mandatory property.

- _**value**_*: Value of Query Parameter. It is mandatory property.


**Header Parameters Properties**: It is key|value pair property which define the header parameter, if any associated to the API.

- _**name**_*: Name of Header Parameter. It is mandatory property.

- _**value**_*: Value of Header Parameter. It is mandatory property.


**Data Stream**: Data part under request. Is is optional property.


> **Note**
>
> You can specify any name for ServiceDescriptor.si.xml file.


###### Android Sample: Service Descriptor

***

![Application Data Folder Based On Name Defined] (https://raw.githubusercontent.com/Siminov/android-connect/docs/github/v1.0/siminov_connect_sample_service_descriptor_path_example.png "ApplicationDescriptor.si.xml file path")

***


###### iOS Sample: Service Descriptor

***

![Application Data Folder Based On Name Defined] (https://raw.githubusercontent.com/Siminov/ios-connect/docs/github/v1.0/siminov_connect_sample_service_descriptor_path_example.png "ApplicationDescriptor.si.xml file path")

***


###### Windows Sample: Service Descriptor

***

![Application Data Folder Based On Name Defined] (https://raw.githubusercontent.com/Siminov/windows-connect/docs/github/v1.0/siminov_connect_sample_service_descriptor_path_example.png "ApplicationDescriptor.si.xml file path")

***



