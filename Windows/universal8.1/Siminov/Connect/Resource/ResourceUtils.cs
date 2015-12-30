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




using Siminov.Connect.Exception;
using Siminov.Connect.Model;
using Siminov.Connect.Service.Design;
using Siminov.Core.Exception;
using Siminov.Core.Log;
using Siminov.Core.Model;
using Siminov.Core.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Siminov.Connect.Resource
{


    /// <summary>
    /// It is a resource utility class, it exposes APIs to get service dynamic resources
    /// </summary>
    public class ResourceUtils
    {


        /// <summary>
        /// It resolve dynamic resources
        /// </summary>
        /// <param name="resourceValue">Value of resource</param>
        /// <param name="descriptors">Array of descriptors</param>
        /// <returns>ServiceException</returns>
	    public static String Resolve(String resourceValue, IDescriptor[] descriptors) 
        {
		
		    if(resourceValue == null) 
            {
			    return resourceValue;
		    }
		
		    if(resourceValue.Contains(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFER_REFERENCE)) 
            {

			    //Find {}
			    int openingCurlyBracketIndex = resourceValue.IndexOf(Constants.RESOURCE_SPACE) + 1;
			
			    int singleClosingCurlyBracketIndex = resourceValue.IndexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET);
			    int doubleClosingCurlyBracketIndex = resourceValue.IndexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET + Constants.RESOURCE_CLOSE_CURLY_BRACKET);

			    String resourceKey;
			
			    if(doubleClosingCurlyBracketIndex != -1) 
                {

				    resourceKey = resourceValue.Substring(openingCurlyBracketIndex, doubleClosingCurlyBracketIndex + 1);
				    int slashIndex = resourceKey.LastIndexOf(Constants.RESOURCE_SLASH);

				    //Find {-
				    String resourceClass = resourceKey.Substring(0, resourceKey.Substring(0, slashIndex).LastIndexOf(Constants.RESOURCE_DOT));
				    String resourceAPI = resourceKey.Substring(resourceKey.Substring(0, slashIndex).LastIndexOf(Constants.RESOURCE_DOT) + 1, resourceKey.Substring(0, slashIndex).Length);

				    ICollection<Type> resourceAPIParameterTypes = new LinkedList<Type>();
				    ICollection<String> resourceAPIParameters = new LinkedList<String>();
				
				
				    //Find -}}
				    String apiParameters = resourceKey.Substring(slashIndex + 1, resourceKey.LastIndexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET) + 1);
				
				    //Resolve all API parameters
                    String[] apiParameterTokenizer = Regex.Split(apiParameters, Constants.RESOURCE_COMMA);

                    for (int i = 0; i < apiParameterTokenizer.Length;i++)
                    {
                        String apiParameter = apiParameterTokenizer[i];

                        resourceAPIParameterTypes.Add(typeof(String));
                        resourceAPIParameters.Add(Resolve(apiParameter, descriptors));
                    }
				
			
				    int count = 0;
				    Type[] apiParameterTypes = new Type[resourceAPIParameters.Count];
				    foreach(Type resourceAPIParameterType in resourceAPIParameterTypes) 
                    {
					    apiParameterTypes[count++] = resourceAPIParameterType;
				    }
				

				    Object classObject = ClassUtils.CreateClassInstance(resourceClass);
				    String resolvedValue = null;
				    try 
                    {
					    resolvedValue = (String) ClassUtils.InvokeMethod(classObject, resourceAPI, apiParameterTypes, resourceAPIParameters.ToArray());
				    } 
                    catch(SiminovException se) 
                    {
					    Log.Error(typeof(ResourceUtils).Name, "Resolve", "SiminovException caught while invoking method, RESOURCE-API: " + resourceAPI + ", " + se.Message);
					    throw new ServiceException(typeof(ResourceUtils).Name, "Resolve", se.GetMessage());
				    }

				
				    return Resolve(resolvedValue, descriptors);
			    } else {

				    resourceKey = resourceValue.Substring(openingCurlyBracketIndex, singleClosingCurlyBracketIndex);
				    int dotIndex = resourceKey.LastIndexOf(Constants.RESOURCE_DOT);

				    String resourceClass = resourceKey.Substring(0, dotIndex);

				    String resourceAPI = resourceKey.Substring(resourceKey.LastIndexOf(Constants.RESOURCE_DOT) + 1, resourceKey.Length);
				
				    Object classObject = ClassUtils.CreateClassInstance(resourceClass);
				    String value = null;
				    try 
                    {
					    value = (String) ClassUtils.GetValue(classObject, resourceAPI);
				    } 
                    catch(SiminovException se) 
                    {
					    Log.Error(typeof(ResourceUtils).Name, "resolve", "SiminovException caught while getting values, RESOURCE-API: " + resourceAPI + ", " + se.GetMessage());
					    throw new ServiceException(typeof(ResourceUtils).Name, "resolve", se.GetMessage());
				    }

				
				    String resolvedValue = resourceValue.Replace(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFER_REFERENCE + Constants.RESOURCE_SPACE + resourceKey + Constants.RESOURCE_CLOSE_CURLY_BRACKET, value);
				    return Resolve(resolvedValue, descriptors);
			    }
		    } 
            else if(resourceValue.Contains(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_SELF_REFERENCE)) 
            {
			
			    String key = resourceValue.Substring(resourceValue.IndexOf(Constants.RESOURCE_SPACE) + 1, resourceValue.IndexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET));
			    String value = null;
			
			
			    foreach(IDescriptor descriptor in descriptors) {
				
				    if(descriptor.ContainProperty(key)) {
					    value = descriptor.GetProperty(key);
					    break;
				    }
			    }
			
			    return Resolve(value, descriptors);
		    } 
            else if(resourceValue.Contains(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE)) 
            {

                String key = resourceValue.Substring(resourceValue.IndexOf(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE) + (Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE).Length + 1, resourceValue.IndexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET) - (resourceValue.IndexOf(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE) + (Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE).Length + 1));
			    String value = null;
			
			    foreach(IDescriptor descriptor in descriptors) 
                {
				
				    if(descriptor.ContainProperty(key)) 
                    {
					    value = descriptor.GetProperty(key);
					    break;
				    }
			    }

			
			    String resolvedValue = resourceValue.Replace(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE + " " + key + Constants.RESOURCE_CLOSE_CURLY_BRACKET, value);
			    return Resolve(resolvedValue, descriptors);
		    } 
		
		    return resourceValue;
	    }
	
	

        /// <summary>
        /// It resolve service descriptor instance 
        /// </summary>
        /// <param name="service">Service instance</param>
        /// <exception cref="Siminov.Connect.Exception.ServiceException">If any exception occur while resolving dynamic resources</exception>
	    public static void Resolve(IService service) 
        {

		    /*
		     * Resolve Service Descriptor Properties
		     */
		    ServiceDescriptor serviceDescriptor = service.GetServiceDescriptor();
		
		    IEnumerator<String> resources = service.GetResources();
		    while(resources.MoveNext()) 
            {
			    String resourceName = resources.Current;
			    Object resourceValue = service.GetResource(resourceName);
			
			    if(resourceValue is String) 
                {
				    serviceDescriptor.AddProperty(resourceName, (String) resourceValue);
			    }
		    }
		
		
		    IEnumerator<String> serviceDescriptorProperties = serviceDescriptor.GetProperties();
            IDictionary<String, String> changedServiceDescriptorProperties = new Dictionary<String, String>();

		    while(serviceDescriptorProperties.MoveNext()) 
            {
			
			    String serviceDescriptorProperty = serviceDescriptorProperties.Current;
			    String serviceDescriptorValue = serviceDescriptor.GetProperty(serviceDescriptorProperty);

                var serviceDescriptors = new List<IDescriptor>();
                serviceDescriptors.Add(serviceDescriptor);
                
                serviceDescriptorValue = ResourceUtils.Resolve(serviceDescriptorValue, serviceDescriptors.ToArray());

                changedServiceDescriptorProperties.Add(serviceDescriptorProperty, serviceDescriptorValue);
		    }

            foreach (String changedServiceDescriptorProperty in changedServiceDescriptorProperties.Keys) 
            {
                serviceDescriptor.AddProperty(changedServiceDescriptorProperty, changedServiceDescriptorProperties[changedServiceDescriptorProperty]);
            }
		
		
		    /*
		     * Resolve API Properties
		     */
		    Connect.Model.ServiceDescriptor.Request request = serviceDescriptor.GetRequest(service.GetRequest());
		    IEnumerator<String> apiProperties = request.GetProperties();
            IDictionary<String, String> changedApiProperties = new Dictionary<String, String>();

            while(apiProperties.MoveNext()) 
            {
			
			    String apiProperty = apiProperties.Current;
			    String apiValue = request.GetProperty(apiProperty);

                var serviceDescriptors = new List<IDescriptor>();
                serviceDescriptors.Add(serviceDescriptor);

			    apiValue = ResourceUtils.Resolve(apiValue, serviceDescriptors.ToArray());

                changedApiProperties.Add(apiProperty, apiValue);
		    }

            foreach (String changedApiProperty in changedApiProperties.Keys)
            {
                request.AddProperty(changedApiProperty, changedApiProperties[changedApiProperty]);
            }
		
		    /*
		     * Resolve API Query Parameters
		     */
		    IEnumerator<Connect.Model.ServiceDescriptor.Request.QueryParameter> queryParameters = request.GetQueryParameters();
            while(queryParameters.MoveNext()) 
            {
			
			    Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter = queryParameters.Current;
			
			    String queryValue = queryParameter.GetValue();

                var serviceDescriptors = new List<IDescriptor>();
                serviceDescriptors.Add(serviceDescriptor);

			    queryValue = ResourceUtils.Resolve(queryValue, serviceDescriptors.ToArray());
			
			    queryParameter.SetValue(queryValue);
		    }

		
		    /*
		     * Resolve API Query Parameters
		     */
		    IEnumerator<Connect.Model.ServiceDescriptor.Request.HeaderParameter> headerParameters = request.GetHeaderParameters();
            while(headerParameters.MoveNext()) 
            {
			
			    Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter = headerParameters.Current;
			
			    String headerValue = headerParameter.GetValue();

                var serviceDescriptors = new List<IDescriptor>();
                serviceDescriptors.Add(serviceDescriptor);

			    headerValue = ResourceUtils.Resolve(headerValue, serviceDescriptors.ToArray());
			
			    headerParameter.SetValue(headerValue);
		    }
	    }
    }
}
