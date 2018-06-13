package cn.com.jxTec.schedulePro.entity;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */
public class UserCenterPage {

    //当前页号
    private Integer pageNow;

    //每页显示记录数，默认为8条
    private Integer pageSize;

    //总记录数
    private Integer allRecordNO;

    //总页数
    private Integer allPageNO;

    public Integer getPageNow() {
		return pageNow;
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getAllRecordNO() {
		return allRecordNO;
	}

	public void setAllRecordNO(Integer allRecordNO) {
		this.allRecordNO = allRecordNO;
	}

	public Integer getAllPageNO() {
		return allPageNO;
	}

	public void setAllPageNO(Integer allPageNO) {
		this.allPageNO = allPageNO;
	}

	public List<Map<String, Object>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}

	//内容
    private List<Map<String, Object>> mapList;


    public UserCenterPage(Integer pageNow, Integer pageSize){
        this.pageNow = pageNow;
        this.pageSize = pageSize;
    }

    public static UserCenterPage getInstance(Integer pageNow, Integer pageSize){
        return new UserCenterPage(pageNow, pageSize);
    }


}
