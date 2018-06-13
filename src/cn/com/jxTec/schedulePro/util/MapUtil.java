package cn.com.jxTec.schedulePro.util;

import java.util.ArrayList;
import java.util.List;

public class MapUtil {
	/** 
     * 给定点和多边形，判断给定的点是否在多边形内 
     * @param point 
     * @param points 
     * @return 
     */  
    public static boolean isPointInPolygon(Point point, List<Point> points) {  


        boolean result = false;  
        int intersectCount = 0;  
        // 判断依据：求解从该点向右发出的水平线射线与多边形各边的交点，当交点数为奇数，则在内部  
        //不过要注意几种特殊情况：1、点在边或者顶点上;2、点在边的延长线上;3、点出发的水平射线与多边形相交在顶点上  
        /** 
         * 具体步骤如下： 
         * 循环遍历各个线段： 
         *  1、判断点是否在当前边上(斜率相同,且该点的x值在两个端口的x值之间),若是则返回true 
         *  2、否则判断由该点发出的水平射线是否与当前边相交,若不相交则continue 
         *  3、若相交,则判断是否相交在顶点上(边的端点是否在给定点的水平右侧).若不在,则认为此次相交为穿越,交点数+1 并continue 
         *  4、若交在顶点上,则判断上一条边的另外一个端点与当前边的另外一个端点是否分布在水平射线的两侧.若是则认为此次相交为穿越,交点数+1. 
         */  
        for (int i = 0; i < points.size(); i++) {  
            Point pointA = points.get(i);  
            Point pointB = null;  
            Point pointPre = null;  
            //若当前是第一个点,则上一点则是list里面的最后一个点  
            if (i == 0) {  
                pointPre = points.get(points.size() - 1);  
            } else {  
                pointPre = points.get(i - 1);  
            }  
            //若已经循环到最后一个点,则与之连接的是第一个点  
            if (i == (points.size() - 1)) {  
                pointB = points.get(0);  
            } else {  
                pointB = points.get(i + 1);  
            }  
            Line line = new Line(pointA, pointB);  
            //1、判断点是否在当前边上(斜率相同,且该点的x值在两个端口的x值之间),若是则返回true  
            boolean isAtLine = line.isContainsPoint(point);  
            if (isAtLine) {  
                return true;  
            } else {  
                //2、若不在边上,判断由该点发出的水平射线是否与当前边相交,若不相交则continue  
                //设置该射线的另外一个端点的x值=999,保证边的x永远不超过  
                Point  radialPoint = new Point(999d, point.Y);  
                Line radial = new Line(point, radialPoint);  
                boolean isIntersect = radial.isIntersect(line);  
                if (!isIntersect) {  
                    continue;  
                } else {  
                    //3、若相交,则判断是否相交在顶点上(边的端点是否在给定点的水平右侧).若不在,则认为此次相交为穿越,交点数+1 并continue  
                    if (!( (pointA.X > point.X) && (pointA.Y.equals(point.Y))  
                            || (pointB.X > point.X) && (pointB.Y.equals(point.Y)) )) {  
                        intersectCount++;  
                        continue;  
                    } else {  
                        //4、若交在顶点上,则判断上一条边的另外一个端点与当前边的另外一个端点是否分布在水平射线的两侧.若是则认为此次相交为穿越,交点数+1  
                        if ((pointPre.Y - point.Y) * (pointB.Y - point.Y) < 0) {  
                            intersectCount++;  
                        }  
                    }  
                }  
            }  
        }  
        result = intersectCount % 2 == 1;  
        return result;  
    }  


    public static void main(String[] args) {  
    	//武汉市硚口区边缘经纬度，可先用百度API获取经纬度
        Point point1 = new Point(114.285808, 30.578716);  
        Point point2 = new Point(114.291401, 30.569866);  
        Point point3 = new Point(114.282133, 30.56697);  
        Point point4 = new Point(114.261547, 30.570716);  
        Point point5 = new Point(114.244733, 30.57787);  
        Point point6 = new Point(114.233662, 30.577933);  
        Point point7 = new Point(114.21512, 30.589768);  
        Point point8 = new Point(114.195418, 30.587075);  
        Point point9 = new Point(114.18315, 30.595076); 
        Point point10 = new Point(114.184033, 30.608107);  
        Point point11 = new Point(114.177132, 30.609285);  
        Point point12 = new Point(114.167893, 30.623536);  
        Point point13 = new Point(114.163361, 30.624345); 
        Point point14 = new Point(114.163435, 30.634917);  
        Point point15 = new Point(114.167403, 30.636771);  
        Point point16 = new Point(114.233662, 30.577933);  
        Point point17 = new Point(114.196288, 30.629728);  
        Point point18 = new Point(114.234788, 30.631724);  
        Point point19 = new Point(114.231964, 30.627504);  
        Point point20 = new Point(114.235719, 30.616227);  
        Point point21 = new Point(114.2442, 30.618627);  
        Point point22 = new Point(114.247407, 30.611119);  
        Point point23 = new Point(114.237908, 30.610015);  
        Point point24 = new Point(114.232848, 30.60456);  
        Point point25 = new Point(114.240464, 30.599007);  
        Point point26 = new Point(114.240431, 30.593253);  
        Point point27 = new Point(114.245731, 30.589321);
        Point point28 = new Point(114.253697, 30.592824);
        Point point29 = new Point(114.258012, 30.590322);
        Point point30 = new Point(114.262259, 30.594424);
        Point point31 = new Point(114.272301, 30.588116);
        Point point32 = new Point(114.240431, 30.593253);
        Point point33 = new Point(114.274946, 30.582811);
        Point point34 = new Point(114.285808, 30.578716);
        List<Point> points = new ArrayList<>();  
        points.add(point1);  
        points.add(point2);  
        points.add(point3);  
        points.add(point4);  
        points.add(point5);  
        points.add(point6);  
        points.add(point7);
        points.add(point8);
        points.add(point9);
        points.add(point10);
        points.add(point11);
        points.add(point12);
        points.add(point13);
        points.add(point14);
        points.add(point15);
        points.add(point16);
        points.add(point17);
        points.add(point18);
        points.add(point19);
        points.add(point20);
        points.add(point21);
        points.add(point22);
        points.add(point23);
        points.add(point24);
        points.add(point25);
        points.add(point26);
        points.add(point27);
        points.add(point28);
        points.add(point29);
        points.add(point30);
        points.add(point31);
        points.add(point32);
        points.add(point33);
        points.add(point34);
        Point test = new Point(114.157904, 30.632099); //定位到铜川市
        Point test1 = new Point(114.2735098451, 30.5748163352); //定位到武汉市硚口区
        System.out.println(isPointInPolygon(test, points));  
        System.out.println(isPointInPolygon(test1, points)); 
    }  
}
