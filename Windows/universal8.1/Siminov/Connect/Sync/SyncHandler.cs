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



using connect.Siminov.Connect.Sync;
using Siminov.Connect.Model;
using Siminov.Connect.Resource;
using Siminov.Connect.Sync.Design;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Sync
{

    /// <summary>
    /// It is a singleton class which handle any sync request
    /// It exposes API to Handle, Remove sync request
    /// </summary>
    public class SyncHandler
    {

        private ResourceManager resourceManager = ResourceManager.GetInstance();

        private SyncWorker syncWorker = SyncWorker.GetInstance();
        private IDictionary<ISyncRequest, long> requestTimestamps = new Dictionary<ISyncRequest, long>();

        private static SyncHandler syncHandler = null;

        /// <summary>
        /// SyncHandler Constructor
        /// </summary>
        private SyncHandler()
        {

        }


        /// <summary>
        /// It provides singleton instance of SyncHandler
        /// </summary>
        /// <returns>Singleton instance of SyncHandler</returns>
        public static SyncHandler GetInstance()
        {

            if (syncHandler == null)
            {
                syncHandler = new SyncHandler();
            }

            return syncHandler;
        }


        /// <summary>
        /// Handles sync request
        /// </summary>
        /// <param name="syncRequest">Sync Request</param>
        public void Handle(ISyncRequest syncRequest)
        {

            SyncDescriptor syncDescriptor = resourceManager.GetSyncDescriptor(syncRequest.GetName());

            long requestTimestamp = 0;
            if (requestTimestamps.ContainsKey(syncRequest))
            {
                requestTimestamp = requestTimestamps[syncRequest];
            }

            if (requestTimestamp <= 0)
            {
                syncWorker.AddRequest(syncRequest);
                requestTimestamps.Add(syncRequest, DateTime.Now.Ticks);

                return;
            }


            long syncInterval = syncDescriptor.GetSyncInterval();
            long lastRefreshTimestamp = requestTimestamps[syncRequest];
            long currentTimestamp = DateTime.Now.Ticks;

            long timeDifference = lastRefreshTimestamp + syncInterval;

            if (timeDifference < currentTimestamp)
            {
                syncWorker.AddRequest(syncRequest);
                requestTimestamps.Add(syncRequest, DateTime.Now.Ticks);
            }
        }


        /// <summary>
        /// Removes sync request
        /// </summary>
        /// <param name="syncRequest">Sync Request</param>
        public void Remove(ISyncRequest syncRequest)
        {

        }


        /// <summary>
        /// Check whether it contains sync request or not
        /// </summary>
        /// <param name="syncRequest">Sync Request</param>
        /// <returns>(true/false) TRUE: If it contains sync request | FALSE: If it does not contains request</returns>
        public bool Contain(ISyncRequest syncRequest)
        {
            return false;
        }
    }
}
