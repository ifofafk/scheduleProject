package cn.com.jxTec.schedulePro.dto;
/**
 * 
 * @author wangchen
 *
 * @param <T> 所有的T的toString方法都被重写过，为了拼成json字符串
 */
public class ParametersDTO<T> {
	
	private T Parameters;

	public T getParameters() {
		return Parameters;
	}

	public void setParameters(T parameters) {
		Parameters = parameters;
	}

	@Override
	public String toString() {
		return "parameters=" + Parameters + "";
	}
	
	
}
