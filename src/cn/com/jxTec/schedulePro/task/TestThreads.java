package cn.com.jxTec.schedulePro.task;

import java.sql.SQLException;
import java.time.LocalTime;

/**
 * 测试线程池， schedule 
 * 
 * @author ChenWang:
 * @version 20180509
 */
public class TestThreads {

	public void test1() throws SQLException {
		 System.out.println(Thread.currentThread() + ", test1@"  + LocalTime.now());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
		
	}
	 
	public void test2() throws SQLException {
		System.out.println(Thread.currentThread() + ", test2@"  + LocalTime.now());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
	}


}
