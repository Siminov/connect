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




using Siminov.Connect.Service.Design;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Sync.Design
{

    /// <summary>
    /// It is a blue print for classes which wants to contain sync request information.
    /// It exposes APIs to Get and Set sync request details
    /// </summary>
    public interface ISyncRequest : IRequest, IResource
    {


        /// <summary>
        /// Get sync request name
        /// </summary>
        /// <returns>Name of sync request</returns>
        String GetName();


        /// <summary>
        /// Set sync request name
        /// </summary>
        /// <param name="name">Name of sync request</param>
        void SetName(String name);
    }
}
