package cn.com.jxTec.schedulePro.util;

public class Point {
	/** 
     * 水平方向值/经度 
     */  
    public Double X;  
    /** 
     * 垂直方向值/纬度 
     */  
    public Double Y;  


    Point(Double x, Double y) {  


        x = x == null ? 0:x;  
        y = y == null ? 0:y;  
        this.X = x;  
        this.Y = y;  
    }  


    public boolean equals(Object obj) {  


        // 如果为同一对象的不同引用,则相同  
        if (this == obj) {  
            return true;  
        }  
        // 如果传入的对象为空,则返回false  
        if (obj == null) {  
            return false;  
        }  
        if (obj instanceof Point) {  
            Point point = (Point) obj;  
            if (point.X.equals(this.X) && point.Y.equals(this.Y)) {  
                return true;  
            } else {  
                return false;  
            }  
        } else {  
            return false;  
        }  
    }  


    public static void main(String[] args) {  


        Point A = new Point(114.163361, 30.56697);  
        Point B = new Point(114.291401, 30.636771);  
        System.out.println(A.equals(B));  
    }  
}
