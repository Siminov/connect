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

package siminov.connect.sync;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.service.NameValuePair;
import siminov.connect.sync.design.ISyncRequest;

public class SyncRequest implements ISyncRequest {

	private String name;
	
	private Map<String, NameValuePair> resources = new HashMap<String, NameValuePair>();
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Iterator<NameValuePair> getResources() {
		return this.resources.values().iterator();
	}

	public Object getResource(String name) {
		return this.resources.get(name).getValue();
	}

	public void addResource(final NameValuePair nameValuePair) {
		this.resources.put(nameValuePair.getName(), nameValuePair);
	}

	public boolean containResource(final NameValuePair nameValuePair) {
		return this.resources.containsKey(nameValuePair.getName());
	}
}
