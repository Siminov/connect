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



using Siminov.Connect.Model;
using Siminov.Connect.Resource;
using Siminov.Core.Exception;
using Siminov.Core.Log;
using Siminov.Core.Reader;
using Siminov.Core.Utils;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace Siminov.Connect.Reader
{
    public class QuickServiceDescriptorReader : SiminovSAXDefaultHandler
    {

	    private StringBuilder tempValue = new StringBuilder();
	    private String finalServiceDescriptorName = null;
		
	    private ServiceDescriptor serviceDescriptor = null;
	
	    private bool doesMatch = false;
	    private bool isNameProperty = false;
	
	    private Core.Resource.ResourceManager coreResourceManager = Core.Resource.ResourceManager.GetInstance();
	    private ResourceManager connectResourceManager = ResourceManager.GetInstance();
	
	    public QuickServiceDescriptorReader(String findServiceDescriptorName) 
        {
		
		    if(findServiceDescriptorName == null || findServiceDescriptorName.Length <= 0) 
            {
			    Log.Error(this.GetType().Name, "Constructor", "Invalid Service Descriptor Name Which Needs To Be Searched.");
			    throw new SiminovException(this.GetType().Name, "Constructor", "Invalid Service Descriptor Name Which Needs To Be Searched.");
		    }
		
		    this.finalServiceDescriptorName = findServiceDescriptorName;
	    }
	
	    public void Process() 
        {
		
		    ApplicationDescriptor applicationDescriptor = connectResourceManager.GetApplicationDescriptor();
		    IEnumerator<String> serviceDescriptorPaths = applicationDescriptor.GetServiceDescriptorPaths();
		
		    while(serviceDescriptorPaths.MoveNext()) 
            {
			    String serviceDescriptorPath = serviceDescriptorPaths.Current;

                String serviceDescriptorFileName = null;
                String serviceDescriptorFilePath = null;

                serviceDescriptorFilePath = serviceDescriptorPath.Substring(0, serviceDescriptorPath.LastIndexOf("/"));
                serviceDescriptorFileName = serviceDescriptorPath.Substring(serviceDescriptorPath.LastIndexOf("/") + 1, (serviceDescriptorPath.Length - serviceDescriptorPath.LastIndexOf("/")) - 1);

                Stream serviceDescriptorStream = null;

                try 
                {
                    serviceDescriptorStream = FileUtils.SearchFile(serviceDescriptorFilePath, serviceDescriptorFileName, FileUtils.INSTALLED_FOLDER);
                } 
                catch(System.Exception ioException) 
                {
                    Log.Error(this.GetType().Name, "Process", "IOException caught while getting input stream of Service Descriptor: " + serviceDescriptorPath + ", " + ioException.Message);
                    throw new SiminovException(this.GetType().Name, "process", "IOException caught while getting input stream of Service Descriptor: " + serviceDescriptorPath + ", " + ioException.Message);
                }
			
			    try 
                {
                    ParseMessage(serviceDescriptorStream);
			    }
                catch (PrematureEndOfParseException exception) 
                {
                    Log.Error(this.GetType().Name, "Process", "PrematureEndOfParseException caught while parsing Service Descriptor: " + serviceDescriptorPath + ", " + exception.Message);
			    }
			
			    if(doesMatch) 
                {

				    ServiceDescriptorReader serviceDescriptor = new ServiceDescriptorReader(serviceDescriptorPath);
				    this.serviceDescriptor = serviceDescriptor.GetServiceDescriptor();
				
				    return;
			    }
		    }
	    }
	
        public override void StartElement(XmlReader reader, IDictionary<String, String> attributes)
        {		
		
            String localName = reader.Name;
		    tempValue = new StringBuilder();

		    if(localName.Equals(Constants.SERVICE_DESCRIPTOR_PROPERTY)) 
            {
			    String propertyName = attributes[Constants.SERVICE_DESCRIPTOR_PROPERTY_NAME];

			    if(propertyName.Equals(Constants.SERVICE_DESCRIPTOR_NAME, StringComparison.OrdinalIgnoreCase)) 
                {
				    isNameProperty = true;
			    }
		    } 
	    }
	
        public override void Characters(String value)
        {
            if (value == null || value.Length <= 0 || value.Equals(Core.Constants.NEW_LINE, StringComparison.OrdinalIgnoreCase))
            {
                return;
            }

            value = value.Trim();
            tempValue.Append(value);
	    }

        public override void EndElement(String localName)
        {
		
		    if(localName.Equals(Constants.SERVICE_DESCRIPTOR_PROPERTY, StringComparison.OrdinalIgnoreCase)) 
            {
			
			    if(isNameProperty) 
                {
				    if(tempValue.ToString().Equals(finalServiceDescriptorName, StringComparison.OrdinalIgnoreCase)) 
                    {
					    doesMatch = true;
				    }
				
				    throw new PrematureEndOfParseException(this.GetType().Name, "EndElement", "Service Descriptor Name: " + tempValue);
			    }
		    }
	    }



        /// <summary>
        /// Get service descriptor
        /// </summary>
        /// <returns>Service Descriptor</returns>
	    public ServiceDescriptor GetServiceDescriptor() 
        {
		    return this.serviceDescriptor;
	    }
	

        /// <summary>
        /// Set service descriptor
        /// </summary>
        /// <returns>Service Descriptor</returns>
	    public bool ContainServiceDescriptor() 
        {
		    return this.doesMatch;
	    }

    }
}
