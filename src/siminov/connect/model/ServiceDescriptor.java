package siminov.connect.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;

public class ServiceDescriptor implements Constants {
		
	private Map<String, String> properties = new HashMap<String, String> ();
	private Map<String, API> apis = new HashMap<String, API> ();
	
	public String getName() {
		return this.properties.get(SERVICE_DESCRIPTOR_NAME);
	}
	
	public void setName(final String name) {
		this.properties.put(SERVICE_DESCRIPTOR_NAME, name);
	}
	
	public String getDescription() {
		return this.properties.get(SERVICE_DESCRIPTOR_DESCRIPTION);
	}
	
	public void setDescription(final String description) {
		this.properties.put(SERVICE_DESCRIPTOR_DESCRIPTION, description);
	}
	
	public String getProtocol() {
		return this.properties.get(SERVICE_DESCRIPTOR_PROTOCOL);
	}
	
	public void setProtocol(final String protocol) {
		this.properties.put(SERVICE_DESCRIPTOR_PROTOCOL, protocol);
	}
	
	public String getInstance() {
		return this.properties.get(SERVICE_DESCRIPTOR_INSTANCE);
	}
	
	public void setInstance(final String instance) {
		this.properties.put(SERVICE_DESCRIPTOR_INSTANCE, instance);
	}
	
	public String getPort() {
		return this.properties.get(SERVICE_DESCRIPTOR_PORT);
	}
	
	public void setPort(final String port) {
		this.properties.put(SERVICE_DESCRIPTOR_PORT, port);
	}

	public String getContext() {
		return this.properties.get(SERVICE_DESCRIPTOR_CONTEXT);
	}
	
	public void setContext(String context) {
		this.properties.put(SERVICE_DESCRIPTOR_CONTEXT, context);
	}
	
	public Iterator<String> getProperties() {
		return this.properties.keySet().iterator();
	}
	
	public String getProperty(String name) {
		return this.properties.get(name);
	}

	public boolean containProperty(String name) {
		return this.properties.containsKey(name);
	}
	
	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
	public void removeProperty(String name) {
		this.properties.remove(name);
	}

	public Iterator<API> getApis() {
		return this.apis.values().iterator();
	}
	
	public API getApi(final String name) {
		return this.apis.get(name);
	}
	
	public void addApi(final API api) {
		this.apis.put(api.getName(), api);
	}
	
	public boolean containApi(final String name) {
		return this.containApi(name);
	}
	
	public void removeApi(final API api) {
		this.apis.remove(api);
	}
	
	public static class API {
	
		private Map<String, String> properties = new HashMap<String, String>();

		private Map<String, QueryParameter> queryParameters = new HashMap<String, QueryParameter> ();
		private Map<String, HeaderParameter> headerParameters = new HashMap<String, HeaderParameter> ();
		
		private String dataStream = null;
		
		public String getName() {
			return this.properties.get(SERVICE_DESCRIPTOR_API_NAME);
		}
		
		public void setName(final String name) {
			this.properties.put(SERVICE_DESCRIPTOR_API_NAME, name);
		}
		
		public String getType() {
			return this.properties.get(SERVICE_DESCRIPTOR_API_TYPE);
		}
		
		public void setType(final String type) {
			this.properties.put(SERVICE_DESCRIPTOR_API_TYPE, type);
		}
		
		public String getApi() {
			return this.properties.get(SERVICE_DESCRIPTOR_API_API);
		}
		
		public void setApi(final String api) {
			this.properties.put(SERVICE_DESCRIPTOR_API_API, api);
		}
		
		public String getMode() {
			return this.properties.get(SERVICE_DESCRIPTOR_API_MODE);
		}
		
		public void setMode(final String mode) {
			this.properties.put(SERVICE_DESCRIPTOR_API_MODE, mode);
		}
		
		public String getDataStream() {
			return this.dataStream;
		}
		
		public void setDataStream(final String dataStream) {
			this.dataStream = dataStream;
		}
		
		
		public Iterator<String> getProperties() {
			return this.properties.keySet().iterator();
		}
		
		public String getProperty(String name) {
			return this.properties.get(name);
		}

		public boolean containProperty(String name) {
			return this.properties.containsKey(name);
		}
		
		public void addProperty(String name, String value) {
			this.properties.put(name, value);
		}
		
		public void removeProperty(String name) {
			this.properties.remove(name);
		}

		public Iterator<QueryParameter> getQueryParameters() {
			return this.queryParameters.values().iterator();
		}
		
		public QueryParameter getQueryParameter(final String name) {
			return this.queryParameters.get(name);
		}
		
		public boolean containQueryParameter(final String name) {
			return this.queryParameters.containsKey(name);
		}
		
		public void addQueryParameter(final QueryParameter queryParameter) {
			this.queryParameters.put(queryParameter.getName(), queryParameter);
		}
		
		public void removeQueryParameter(final QueryParameter queryParameter) {
			this.queryParameters.remove(queryParameter);
		}
		
		public Iterator<HeaderParameter> getHeaderParameters() {
			return this.headerParameters.values().iterator();
		}
		
		public HeaderParameter getHeaderParameter(final String name) {
			return this.headerParameters.get(name);
		}
		
		public void addHeaderParameter(final HeaderParameter headerParameter) {
			this.headerParameters.put(headerParameter.getName(), headerParameter);
		}
		
		public boolean containHeaderParameter(final String name) {
			return this.headerParameters.containsKey(name);
		}
		
		public void removeHeaderParameter(final HeaderParameter headerParameter) {
			this.queryParameters.remove(headerParameter);
		}

		public static class QueryParameter {
			
			private String name;
			private String value;
			
			public String getName() {
				return this.name;
			}
			
			public void setName(final String name) {
				this.name = name;
			}
			
			public String getValue() {
				return this.value;
			}
			
			public void setValue(final String value) {
				this.value = value;
			}
		}
		
		public static class HeaderParameter {
			
			private String name;
			private String value;
			
			public String getName() {
				return this.name;
			}
			
			public void setName(final String name) {
				this.name = name;
			}
			
			public String getValue() {
				return this.value;
			}
			
			public void setValue(final String value) {
				this.value = value;
			}
		}
	}
}
