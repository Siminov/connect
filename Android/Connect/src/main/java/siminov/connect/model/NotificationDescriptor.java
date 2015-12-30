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

package siminov.connect.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.core.model.IDescriptor;


/**
 * Exposes methods to GET and SET Notification Descriptor information as per define in ApplicationDescriptor.xml file by application.
 *
 *	<p>
		<pre>
		
Example:
	{@code

	<siminov>
	    
	    <!-- Notification Descriptor -->
	    <notification-descriptor>
	        
	        	<!-- Optional Field -->
	        <property name="name_of_property">value_of_property</property>
	
	    </notification-descriptor>
	    
	</siminov>
	
	}
	
		</pre>
	</p>
 */
public class NotificationDescriptor implements IDescriptor {

	private Map<String, String> properties = new HashMap<String, String> ();

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
}
