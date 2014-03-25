package siminov.connect.exception;

public class AuthorizationException extends Exception {

	private String className = null;
	private String methodName = null;
	private String message = null;
	
	public AuthorizationException(final String className, final String methodName, final String message) {
		this.className = className;
		this.methodName = methodName;
		this.message = message;
	}
	
	/**
	 * Get POJO class name.
	 * @return POJO Class Name.
	 */
	public String getClassName() {
		return this.className;
	}
	
	/**
	 * Set POJO class name.
	 * @param className POJO Class Name.
	 */
	public void setClassName(final String className) {
		this.className = className;
	}
	
	/**
	 * Get method Name.
	 * @return Name Of Method.
	 */
	public String getMethodName() {
		return this.methodName;
	}
	
	/**
	 * Set method Name.
	 * @param methodName Name Of Method.
	 */
	public void setMethodName(final String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Get message.
	 * @return Message.
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Set message.
	 * @param message Message.
	 */
	public void setMessage(final String message) {
		this.message = message;
	}
}
