package help.mygod.weixin.common.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;


/**
 * @author cc
 * @2010-11-3 @11:05:45
 */
@XmlRootElement
public class Res implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -447417517977491962L;

	private int status;

	private String msg;

	private JSONObject data;	

	
	public Res() {
		super();
	}

	public Res(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public Res(int status, String msg, JSONObject data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Res [status=" + status + ", msg=" + msg + "]";
	}

	public static void main(String[] args) {

	}
}
