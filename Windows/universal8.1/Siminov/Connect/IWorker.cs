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




using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Threading.Tasks;

namespace Siminov.Connect
{

    /// <summary>
    /// It is a blue print for classes which want to perform background processes
    /// It exposes APIs to start, stop worker
    /// </summary>
    public interface IWorker
    {

        /// <summary>
        /// Start the worker to handle the requests
        /// </summary>
        void StartWorker();


        /// <summary>
        /// Stop the worker
        /// </summary>
        void StopWorker();


        /// <summary>
        /// Check whether worker is running or not
        /// </summary>
        /// <returns>(true/false) TRUE: If worker is running | FALSE: If worker is not running</returns>
        bool IsWorkerRunning();



        /// <summary>
        /// Add request
        /// </summary>
        /// <param name="request">IRequest</param>
        void AddRequest(IRequest request);


        /// <summary>
        /// Remove request
        /// </summary>
        /// <param name="request">IRequest</param>
        void RemoveRequest(IRequest request);


        /// <summary>
        /// Check whether it contains request or not
        /// </summary>
        /// <param name="request">(true/false) TRUE: If it contains the request | FALSE: If it does not contains the request</param>
        /// <returns></returns>
        bool ContainsRequest(IRequest request);
    }
}
