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




using Siminov.Core.Exception;
using Siminov.Core.Log;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Utils
{

    /// <summary>
    /// Exposes utility methods which can be used by both SIMINOV/Application. 
    /// </summary>
    public class Utils
    {


        /// <summary>
        /// Check whether device have network coverage or not.
        /// </summary>
        /// <returns>TRUE: If network coverage if there, FALSE: If network coverage is not there.</returns>
        public static bool HasCoverage() 
        {
            return true;
	    }

        public static Stream ToStream(byte[] value)
        {
            if (value == null || value.Length <= 0)
            {
                Core.Log.Log.Error(typeof(Utils).FullName, "ToStream", "Invalid String Found.");
                throw new SiminovException(typeof(Utils).FullName, "ToStream", "Invalid String Found.");
            }

            return new MemoryStream(value);
        }
    }
}
