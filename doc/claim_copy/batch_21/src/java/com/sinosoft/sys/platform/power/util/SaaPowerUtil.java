package com.sinosoft.sys.platform.power.util;

public class SaaPowerUtil
{
  private static String systemIdentify;

  public static String getSystemIdentify()
  {
    return systemIdentify;
  }

  public static void setSystemIdentify(String systemId ) {
    systemIdentify = systemId;
  }
}
