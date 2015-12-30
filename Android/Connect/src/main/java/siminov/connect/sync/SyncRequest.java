/**
 * [SIMINOV FRAMEWORK - CONNECT]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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

import siminov.connect.sync.design.ISyncRequest;


public class SyncRequest implements ISyncRequest {

	private long requestId;
	private String name;
	
	private Map<String, Object> resources = new HashMap<String, Object>();
	
	public long getRequestId() {
		return this.requestId;
	}
	
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Iterator<String> getResources() {
		return this.resources.keySet().iterator();
	}

	public Object getResource(String name) {
		return this.resources.get(name);
	}

	public void addResource(final String name, final Object value) {
		this.resources.put(name, value);
	}

	public boolean containResource(final String name) {
		return this.resources.containsKey(name);
	}
}
