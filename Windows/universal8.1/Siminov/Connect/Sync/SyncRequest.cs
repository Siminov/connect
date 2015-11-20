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



using Siminov.Connect.Sync.Design;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Sync
{
    public class SyncRequest : ISyncRequest
    {

	    private String name;
	
	    private IDictionary<String, Object> resources = new Dictionary<String, Object>();
	
	    public String GetName() 
        {
		    return this.name;
	    }
	
	    public void SetName(String name) 
        {
		    this.name = name;
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
    }
}
