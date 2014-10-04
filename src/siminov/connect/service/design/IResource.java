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

package siminov.connect.service.design;

import java.util.Iterator;


/**
 * It exposes APIs to Get and Set resources
 */
public interface IResource {
	
	/**
	 * Get all resources
	 * @return Resources
	 */
	public Iterator<String> getResources();
	

	/**
	 * Get resource based on name
	 * @param name Name of resource
	 * @return Resource
	 */
	public Object getResource(final String name);

	
	/**
	 * Add resource 
	 * @param name Name of resource
	 * @param value Value of resource
	 */
	public void addResource(final String name, final Object value);
	
	
	/**
	 * Check whether it contains resource or not
	 * @param name Name of resource
	 * @return (true/false) TRUE: If resource exists | FALSE: If resource does not exists
	 */
	public boolean containResource(final String name);
}
