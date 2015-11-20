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



using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Model
{

    /// <summary>
    /// Exposes methods to GET and SET Notification Descriptor information as per define in ApplicationDescriptor.xml file by application.
    /// Example:
    ///
    ///    <siminov>
	///    
    ///        <!-- Notification Descriptor -->
    ///        <notification-descriptor>
	///        
    ///                <!-- Optional Field -->
    ///            <property name="name_of_property">value_of_property</property>
	///
    ///        </notification-descriptor>
	///    
    ///    </siminov>
    /// 
    /// </summary>
    public class NotificationDescriptor : Core.Model.IDescriptor
    {
        private IDictionary<String, String> properties = new Dictionary<String, String>();

        public IEnumerator<String> GetProperties()
        {
            return this.properties.Keys.GetEnumerator();
        }

        public String GetProperty(String name)
        {
            return this.properties[name];
        }

        public bool ContainProperty(String name)
        {
            return this.properties.ContainsKey(name);
        }

        public void AddProperty(String name, String value)
        {
            this.properties.Add(name, value);
        }

        public void RemoveProperty(String name)
        {
            this.properties.Remove(name);
        }
    }
}
