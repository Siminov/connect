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

package siminov.connect.connection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.connection.design.IConnectionRequest;
import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;

public class ConnectionRequest implements IConnectionRequest {

	private String url;

	private String protocol;
	private String type;
	
	private Map<String, QueryParameter> queryParameters = new HashMap<String, QueryParameter>();
	private Map<String, HeaderParameter> headerParameters = new HashMap<String, HeaderParameter>();
	
	private byte[] dataStream = null;
	
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getProtocol() {
		return this.protocol;
	}
	
	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(final String type) {
		this.type = type;
	}
	
	public Iterator<String> getQueryParameters() {
		return this.queryParameters.keySet().iterator();
	}
	
	public QueryParameter getQueryParameter(String key) {
		return this.queryParameters.get(key);
	}
	
	public void addQueryParameter(QueryParameter queryParameter) {
		this.queryParameters.put(queryParameter.getName(), queryParameter);
	}
	
	public Iterator<String> getHeaderParameters() {
		return this.headerParameters.keySet().iterator();
	}
	
	public HeaderParameter getHeaderParameter(String key) {
		return this.headerParameters.get(key);
	}

	public void addHeaderParameter(HeaderParameter headerParameter) {
		this.headerParameters.put(headerParameter.getName(), headerParameter);
	}

	public byte[] getDataStream() {
		return this.dataStream;
	}
	
	public void setDataStream(byte[] dataStream) {
		this.dataStream = dataStream;
	}
}
