package entity;

import java.io.Serializable;

/**
 * 返回结果类
 * @author 86189
 *
 */
public class Result implements Serializable {

	private Boolean success; //是否成功
	private String message; // 返回信息
	
	public Result(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
