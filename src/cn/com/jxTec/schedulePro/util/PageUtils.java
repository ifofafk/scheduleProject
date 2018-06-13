package cn.com.jxTec.schedulePro.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenWang:
 * @version 创建时间：2018年5月4日 下午3:48:43 类说明
 * 基于请求参数过多，导致http多长，将1次http请求拆分为多次的需求，而做的分页
 */
public class PageUtils<T> {

	public List<List<T>> paging(List<T> list, int pageSize) {
		int pageCount = 0;//总页数
		int totalCount = 0;//总数量
		int m = 0;//判断使用
		
		totalCount = list.size();
		m = totalCount % pageSize;
		
		List<List<T>> totalList = new ArrayList<List<T>>();//存放每一页的list

		if (m > 0) {
			pageCount = totalCount / pageSize + 1;
		} else {
			pageCount = totalCount / pageSize;
		}

		for (int i = 1; i <= pageCount; i++) {
			if (m == 0) {
				List<T> subList = list.subList((i - 1) * pageSize, pageSize * (i));
				totalList.add(subList);
			} else {
				if (i == pageCount) {
					List<T> subList = list.subList((i - 1) * pageSize, totalCount);
					totalList.add(subList);
				} else {
					List<T> subList = list.subList((i - 1) * pageSize, pageSize * i);
					totalList.add(subList);
				}
			}
		}

		return totalList;
	}

	//返回每一个分页
	public List<T> page(List<T> test,Integer startNum){
		//方法2
		List<T> obj = null;
		//数据总数
		int whgfCarsSize = test!=null?test.size() : 155;
		//总的页数
		int pageCount = 0;
		//每页显示的总数
		int pageSize = 500;
		//当前页码
		startNum = startNum != null? startNum : 1;
		/*计算出总共能分成多少页*/
		if (whgfCarsSize % pageSize > 0) {     //数据总数和每页显示的总数不能整除的情况
			pageCount = whgfCarsSize / pageSize + 1;
		}else{   //数据总数和每页显示的总数能整除的情况
			pageCount = whgfCarsSize / pageSize;
		}
		
		if(whgfCarsSize > 0){
			if(startNum <= pageCount){
				if(startNum == 1) {    //当前页数为第一页
					if(whgfCarsSize <= pageSize) { //数据总数小于每页显示的数据条数
						//截止到总的数据条数(当前数据不足一页，按一页显示)，这样才不会出现数组越界异常
						obj = test.subList(0, whgfCarsSize);
					}else{
						obj = test.subList(0, pageSize);
					}
				}else{
					//截取起始下标
					int fromIndex = (startNum - 1) * pageSize;
					//截取截止下标
					int toIndex = startNum * pageSize;
					/*计算截取截止下标*/
					if ((whgfCarsSize - toIndex) % pageSize >= 0){
						toIndex = startNum * pageSize;
					}else{
						toIndex = (startNum - 1) * pageSize + (whgfCarsSize % pageSize);
					}
					if (whgfCarsSize >= toIndex){
						obj = test.subList(fromIndex, toIndex);
					}
				} 
			}else{
				obj = null;
			}
		}
		return obj;
	}
	
	//测试方法1.
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i < 52; i++) {
			list.add(i+"");
		}
		PageUtils<String> pageutils = new PageUtils<>();
		List<List<String>> totalList = pageutils.paging(list, 10);
		System.out.println(totalList);
	}
}
