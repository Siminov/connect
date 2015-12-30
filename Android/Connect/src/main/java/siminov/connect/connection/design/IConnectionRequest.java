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

package siminov.connect.connection.design;

import java.util.Iterator;

import siminov.connect.model.ServiceDescriptor.Request.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.Request.QueryParameter;


/**
 * It contain the blue print for the connection request object
 */
public interface IConnectionRequest {

	/**
	 * Get request URL
	 * @return URL
	 */
	public String getUrl();
	
	/**
	 * Set request URL
	 * @param url URL
	 */
	public void setUrl(String url);

	/**
	 * Get request protocol
	 * @return Protocol (HTTP/HTTPS)
	 */
	public String getProtocol();
	
	/**
	 * Set request protocol
	 * @param protocol Protocol
	 */
	public void setProtocol(final String protocol);
	
	
	/**
	 * Get request type 
	 * @return Type
	 */
	public String getType();
	
	/**
	 * Set request type
	 * @param type Request Type
	 */
	public void setType(final String type);
	
	/**
	 * Get all query parameters
	 * @return Query Parameters
	 */
	public Iterator<String> getQueryParameters();
	
	/**
	 * Get query parameter
	 * @param key Name of query parameter
	 * @return Query Parameter
	 */
	public QueryParameter getQueryParameter(String key);
	
	/**
	 * Add query parameter
	 * @param queryParameter Query Parameter
	 */
	public void addQueryParameter(QueryParameter queryParameter);
	
	/**
	 * Get all header parameters
	 * @return Header Parameter
	 */
	public Iterator<String> getHeaderParameters();
	
	/**
	 * Get header parameter
	 * @param key Name of header parameter
	 * @return Header Parameter
	 */
	public HeaderParameter getHeaderParameter(String key);

	/**
	 * Add header parameter
	 * @param headerParameter Header Parameter
	 */
	public void addHeaderParameter(HeaderParameter headerParameter);

	/**
	 * Get data stream
 	 * @return Data Stream
	 */
	public byte[] getDataStream();
	
	/**
	 * Set data stream
	 * @param dataStream Data Stream
	 */
	public void setDataStream(byte[] dataStream);
}
