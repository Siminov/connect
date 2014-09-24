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

package siminov.connect.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;
import siminov.orm.model.IDescriptor;

public class SyncDescriptor implements IDescriptor {

	private Map<String, String> properties = new HashMap<String, String> ();
	
	private Collection<String> services = new ArrayList<String>();
	
	public String getName() {
		return this.properties.get(Constants.SYNC_DESCRIPTOR_NAME);
	}
	
	public void setName(String name) {
		this.properties.get(name);
	}

	public long getSyncInterval() {
		String syncInterval = this.properties.get(Constants.SYNC_DESCRIPTOR_REFRESH_INTERVAL);
		if(syncInterval == null || syncInterval.length() <= 0) {
			return 0;
		}
		
		return Long.valueOf(syncInterval);
	}
	
	public void setSyncInterval(long syncInterval) {
		this.properties.put(Constants.SYNC_DESCRIPTOR_REFRESH_INTERVAL, Long.toString(syncInterval));
	}
	
	public Iterator<String> getProperties() {
		return this.properties.keySet().iterator();
	}

	public String getProperty(String name) {
		return this.properties.get(name);
	}

	public boolean containProperty(String name) {
		return this.properties.containsKey(name);
	}

	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}

	public void removeProperty(String name) {
		this.properties.remove(name);
	}
	
	public Iterator<String> getServices() {
		return this.services.iterator();
	}
	
	public void addService(String service) {
		this.services.add(service);
	}
	
	public void removeService(String service) {
		this.services.remove(service);
	}
}
