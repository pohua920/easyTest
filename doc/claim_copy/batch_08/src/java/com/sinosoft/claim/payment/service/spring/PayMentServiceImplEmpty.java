package com.sinosoft.claim.payment.service.spring;

import java.util.Map;

import com.sinosoft.claim.payment.service.facade.PayMentService;
 /**
  * 收付接口实现类
  * @author 中科软
  *
  */
 public class PayMentServiceImplEmpty implements PayMentService {
   public void send( String businessType, String businessNo)throws Exception{
     System.out.println("该收付的实现是空的，请重载实现方法，并在配置文件中重指向");
   }
 
   public void transData(String businessType, String businessNo, Map<?, ?> infoMap) throws Exception{
     System.out.println("该收付的实现是空的，请重载实现方法，并在配置文件中重指向");
   }
 }

