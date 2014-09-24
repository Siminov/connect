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

package siminov.connect.notification.design;


public interface INotification {

	public String SENDER_ID = "sender_id";
	public String APPLICATION_ID = "application_id";
	public String REGISTRATION_ID = "registration_id";
	public String MESSAGE = "message";
	
	public void doRegistration();

	public void doUnregistration();
	
	public void onRegistration(IRegistration registration);

	public void onUnregistration(IRegistration registration);
	
	public void onNotification(IMessage message);
}
