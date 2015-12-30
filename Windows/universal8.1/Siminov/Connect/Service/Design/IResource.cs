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



using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Service.Design
{

    /// <summary>
    /// It exposes APIs to Get and Set resources
    /// </summary>
    public interface IResource
    {
        	
        /// <summary>
        /// Get all resources
        /// </summary>
        /// <returns>Resources</returns>
	    IEnumerator<String> GetResources();
	

        /// <summary>
        /// Get resource based on name
        /// </summary>
        /// <param name="name">Name of resource</param>
        /// <returns>Resource</returns>
	    Object GetResource(String name);

	
        /// <summary>
        /// Add resource 
        /// </summary>
        /// <param name="name">Name of resource</param>
        /// <param name="value">Value of resource</param>
	    void AddResource(String name, Object value);
	
	
        /// <summary>
        /// Check whether it contains resource or not
        /// </summary>
        /// <param name="name">Name of resource</param>
        /// <returns>(true/false) TRUE: If resource exists | FALSE: If resource does not exists</returns>
	    bool ContainResource(String name);
    }
}
