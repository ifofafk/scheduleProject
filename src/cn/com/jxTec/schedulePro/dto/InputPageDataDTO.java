package cn.com.jxTec.schedulePro.dto;

import java.io.Serializable;

/**
 * Created by Bing.Wu on 2017-05-25.
 */
public class InputPageDataDTO<T> implements Serializable {
	
	private static final long serialVersionUID = -6388803180593751670L;
	
	private Integer pageNum;
    private Integer pageSize;
    private T data;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

	@Override
	public String toString() {
		return "InputPageDataDTO [pageNum=" + pageNum + ", pageSize=" + pageSize + ", data=" + data + "]";
	}
    
    
    
}


