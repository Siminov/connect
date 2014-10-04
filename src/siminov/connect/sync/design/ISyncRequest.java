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

package siminov.connect.sync.design;

import siminov.connect.IRequest;
import siminov.connect.service.design.IResource;


/**
 * It is a blue print for classes which wants to contain sync request information.
 * It exposes APIs to Get and Set sync request details
 */
public interface ISyncRequest extends IRequest, IResource {

	/**
	 * Get sync request name
	 * @return Name of sync request
	 */
	public String getName();
	
	
	/**
	 * Set sync request name
	 * @param name Name of sync request
	 */
	public void setName(String name);
}
