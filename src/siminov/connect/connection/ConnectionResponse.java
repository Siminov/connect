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

import java.io.InputStream;

import siminov.connect.connection.design.IConnectionResponse;

public class ConnectionResponse implements IConnectionResponse {

	private int statusCode;
	private String statusMessage;

	private InputStream response;
	
	public ConnectionResponse(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public ConnectionResponse(int statusCode, InputStream response) {
		this.statusCode = statusCode;
		this.response = response;
	}
	
	
	public int getStatusCode() {
		return this.statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatusMessage() {
		return this.statusMessage;
	}
	
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public InputStream getResponse() {
		return this.response;
	}
	
	public void setResponse(InputStream response) {
		this.response = response;
	}
}
