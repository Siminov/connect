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



using Siminov.Connect.Connection.Design;
using Siminov.Connect.Exception;
using Siminov.Connect.Model;
using Siminov.Connect.Service.Design;
using Siminov.Core.Log;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Service
{
    public abstract class Service : IService
    {

	    private long requestId;
	
	    private String service = null;
	    private String request = null;

	    private IDictionary<String, Object> resources = new Dictionary<String, Object>();
	
	    private ServiceDescriptor serviceDescriptor = null;

        /// <summary>
        /// Service Constructor
        /// </summary>
	    public Service() 
        {
		    requestId = new Random().Next();
	    }

	    public long GetRequestId() 
        {
		    return this.requestId;
	    }
	
	    public void SetRequestId(long request) 
        {
		    this.requestId = request;
	    }
	
	    public String GetService() 
        {
		    return this.service;
	    }
	
	    public void SetService(String service) 
        {
		    this.service = service;
	    }
	
	    public String GetRequest() 
        {
		    return this.request;
	    }
	
	    public void SetRequest(String request) 
        {
		    this.request = request;
	    }
	
	    public IEnumerator<String> GetResources() 
        {
		    return this.resources.Keys.GetEnumerator();
	    }
	
	    public Object GetResource(String name) 
        {
		    return this.resources[name];
	    }

	    public void AddResource(String name, Object value) 
        {
		    this.resources.Add(name, value);
	    }
	
	    public bool ContainResource(String name) 
        {
		    return this.resources.ContainsKey(name);
	    }

	    public ServiceDescriptor GetServiceDescriptor() 
        {
		    return this.serviceDescriptor;
	    }
	
	    public void SetServiceDescriptor(ServiceDescriptor serviceDescriptor) 
        {
		    this.serviceDescriptor = serviceDescriptor;
	    }
	
	    public void Invoke() 
        {

            ((IServiceEvents) this).OnStart();
		
		    ServiceHandler serviceHandler = ServiceHandler.GetInstance();
		    try 
            {
			    serviceHandler.Handle(this);
		    } 
            catch(ServiceException se) 
            {
			    Log.Error(typeof(Service).Name, "Invoke", "ServiceException caught while invoking service, " + se.GetMessage());

                ((IServiceEvents)this).OnTerminate(se);
		    }
	    }
	
	    public void Terminate() 
        {
		
	    }


        public virtual void OnStart() { }


        public virtual void OnQueue() { }


        public virtual void OnPause() { }


        public virtual void OnResume() { }


        public virtual void OnFinish() { }


        public virtual void OnRequestInvoke(IConnectionRequest connectionRequest) { }


        public virtual void OnRequestFinish(IConnectionResponse connectionResponse) { }


        public virtual void OnTerminate(ServiceException serviceException) { }

    }
}
