package com.wutka.jox.test;

import com.wutka.jox.*;
import java.io.*;

public class TestSer
{
    public static void main(String[] args)
    {
        try
        {
            TestBean b = new TestBean();
            b.setFoo(5);
            b.setBar("This is the bar value");
            b.setThingies(new String[] {
                "http://www.5a520.cn 小说520网", "http://www.bt285.cn BT下载", "http://www.bt285.cn/caj/ caj下载", "http://www.bt285.cn/baolimotuo/ 暴力摩托", "http://www.bt285.cn/btjinglin bt精灵" });
            TestSubbean sub = new TestSubbean();
            sub.setName("Mark");
            sub.setAge(35);
            b.setSub(sub);

            FileOutputStream fileOut = new FileOutputStream("bean.xml");
            JOXBeanOutputStream joxOut = new JOXBeanOutputStream(fileOut);

            joxOut.writeObject("MarkTest", b);

            joxOut.close();
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
