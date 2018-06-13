package cn.com.jxTec.schedulePro.controller;

import java.sql.SQLException;

import cn.com.jxTec.schedulePro.task.FthwCarTask;

/**
 * 所有定时任务入口
 * 方便异常处理
 * @author Administrator
 *
 */
public class FthwIntefaceClockController {

	//伏泰环卫车辆实时轨迹
	public void getWhgfCarPositionTask(){
		FthwCarTask taskOne = new FthwCarTask();
		try {
			taskOne.getFthwCarPositionTask();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

