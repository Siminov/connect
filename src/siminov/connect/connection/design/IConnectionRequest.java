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

package siminov.connect.connection.design;

import java.util.Iterator;

import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;

public interface IConnectionRequest {

	public String getUrl();
	
	public void setUrl(String url);

	public String getProtocol();
	
	public void setProtocol(final String protocol);
	
	public String getType();
	
	public void setType(final String type);
	
	public Iterator<String> getQueryParameters();
	
	public QueryParameter getQueryParameter(String key);
	
	public void addQueryParameter(QueryParameter queryParameter);
	
	public Iterator<String> getHeaderParameters();
	
	public HeaderParameter getHeaderParameter(String key);

	public void addHeaderParameter(HeaderParameter headerParameter);

	public byte[] getDataStream();
	
	public void setDataStream(byte[] dataStream);
}
