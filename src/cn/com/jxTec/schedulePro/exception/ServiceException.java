package cn.com.jxTec.schedulePro.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1909073935621883896L;
	
	private String code;
	
	public String getCode() {
		return code;
	}
	
	public ServiceException () {}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Throwable thrawable) {
		super(message, thrawable);
	}
	
	public ServiceException(Throwable cause) {
        super(cause);
    }
	
	public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
	
	public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }
	
	public ServiceException(int code, String message) {
        super(message);
        this.code = String.valueOf(code);
    }
}

