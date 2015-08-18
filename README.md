Siminov Connect (RESTful) - Android
===================================================

Siminov Framework (Connect) is a open source framework used to consume RESTful Web Services for all different environments - (Android - Java, iOS - Objective-C|Swift, Windows - C#, OS X - Objective-C|Swift). Using this app developer can easily consume data from any of web services without worrying about underlying architecture of communication.

Get Started
-----------
Get the source

  git clone git://github.com/siminov/android-connect.git
  
	
Features
--------

###### 1. Simple
Working on communication layer on any of the platform (Android, iOS, Windows Phone) is combersome. It provides an easy set of defined descriptors, using these you can easy describe your RESTful Web Services.

	|- ApplicationDescriptor.si.xml 
	|- ServiceDescriptor.si.xml
	|- SyncDescriptor.si.xml
	|- NotificationDescriptor.si.xml
	|- LibraryDescriptor.si.xml

###### 2. Push Notification
Push notification is provide by all the platforms but in different forms and implementation. It provides a unique and generic implementation on all platforms with same APIs bundle and architecture.

###### 3. Handle Multiple Schema's
It also supports multiple schema's if required by application.

###### 4. Synchronization
Synchronizing your app data with service data is a tough job.It allows app to automatically checks for updates in the background. There are different ways by which you can synchronize your app data

  |- Time Interval
  |- Screen Click
  |- Time Interval + Screen Click

###### 5. Database Seeding
Since it is build top on Siminov Core, it provide all features related to database. You can easly map your Core Data Object with database.

	|- Database Create and Drop
	|- Table Create and Drop
	|- Index Create and Drop
	|- Fetch
	|- Save
	|- Update
	|- Save Or Update
	|- Delete
	
	|- Count
	|- Average
	|- Sum
	|- Total
	|- Minimum
	|- Maximum
	|- Group Concat
	
	|- Begin Transaction
	|- Commit Transaction
	|- End Transaction
	
	

###### 8. Event Notifier
It provides event notifier which gets triggered based on particular action. Using these you can easly take action based on event triggered.


LICENSE
-------

 
<b> SIMINOV FRAMEWORK </b>
 <p>
 Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
    http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

