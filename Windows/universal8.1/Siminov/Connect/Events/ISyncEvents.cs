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



using Siminov.Connect.Sync.Design;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Events
{


    /// <summary>
    /// It is a blue print for class which handles sync events
    /// </summary>
    public interface ISyncEvents
    {

        /// <summary>
        /// This method is called then a Sync is started.
        /// In this you can initialize resources related to Sync.
        /// Once OnStart has finished, Connect will call OnQueue.
        /// </summary>
        /// <param name="syncRequest">ISyncRequest instance</param>
        void OnStart(ISyncRequest syncRequest);



        /// <summary>
        /// This method is called then the Sync request is added to the Queue.
        /// </summary>
        /// <param name="syncRequest">ISyncRequest instance</param>
        void OnQueue(ISyncRequest syncRequest);



        /// <summary>
        /// This method is called then Sync request completes its all synchronization data with web service.
        /// </summary>
        /// <param name="syncRequest">ISyncRequest instance</param>
        void OnFinish(ISyncRequest syncRequest);



        /// <summary>
        /// This method is called if there is any error/exception while synchronizing data with web service
        /// </summary>
        /// <param name="syncRequest">ISyncRequest instance</param>
        void OnTerminate(ISyncRequest syncRequest);

    }
}
