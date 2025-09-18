package com.tlg.util;

import java.net.*;
import java.security.*;
import java.util.*;

public class GUID {
  private static Random       myRand;

  private static SecureRandom mySecureRand;

  private static String       s_id;
  /*
   * * Static block to take care of one time secureRandom seed. It takes a few
   * seconds to initialize SecureRandom. You might want to consider removing
   * this static block or replacing it with a "time since first loaded" seed to
   * reduce this time. This block will run only once per JVM instance.
   */
  static{
    mySecureRand = new SecureRandom();
    long secureInitializer = mySecureRand.nextLong();
    myRand = new Random(secureInitializer);
    try{
      s_id = InetAddress.getLocalHost().toString();
    }catch (UnknownHostException e){
      e.printStackTrace();
    }
  }

  public String               valueBeforeMD5 = "";

  public String               valueAfterMD5  = "";

  /*
   * * Default constructor. With no specification of security option, * this
   * constructor defaults to lower security, high performance.
   */
  public GUID() throws Exception {
    getRandomGUID(false);
  }

  /*
   * * Constructor with security option. Setting secure true enables each random
   * number generated to be cryptographically strong. Secure false defaults to
   * the standard Random function seeded with a single cryptographically strong
   * random number.
   */
  public GUID(boolean secure) throws Exception {
    getRandomGUID(secure);
  }

  /* * Method to generate the random GUID */
  private void getRandomGUID(boolean secure) throws Exception {
    MessageDigest md5 = null;
    StringBuffer sbValueBeforeMD5 = new StringBuffer();
    try{
      md5 = MessageDigest.getInstance("MD5");
    }catch (NoSuchAlgorithmException e){
      System.out.println("GUID:getRandomGUID:error");
      throw e;
    }

    try{
      long time = System.currentTimeMillis();
      long rand = 0;
      if(secure){
        rand = mySecureRand.nextLong();
      }else{
        rand = myRand.nextLong();
      }
      // This StringBuffer can be a long as you need; the MD5
      // hash will always return 128 bits. You can change
      // the seed to include anything you want here.
      // You could even stream a file through the MD5 making
      // the odds of guessing it at least as great as that
      // of guessing the contents of the file!
      sbValueBeforeMD5.append(s_id);
      sbValueBeforeMD5.append(":");
      sbValueBeforeMD5.append(Long.toString(time));
      sbValueBeforeMD5.append(":");
      sbValueBeforeMD5.append(Long.toString(rand));
      valueBeforeMD5 = sbValueBeforeMD5.toString();
      md5.update(valueBeforeMD5.getBytes());
      byte[] array = md5.digest();
      StringBuffer sb = new StringBuffer();
      for(int j = 0; j < array.length; ++j){
        int b = array[j] & 0xFF;
        if(b < 0x10){
          sb.append('0');
        }
        sb.append(Integer.toHexString(b));
      }
      valueAfterMD5 = sb.toString();
    }catch (Exception e){
      System.out.println("GUID:getRandomGUID:error");
      throw e;
    }
  }

  /*
   * * Convert to the standard format for GUID (Useful for SQL Server
   * UniqueIdentifiers, etc.) Example: C2FEEEAC-CFCD-11D1-8B05-00600806D9B6
   */
  public String toString(int length) throws Exception {
    String sbString = null;
    try{
      StringBuffer sb = new StringBuffer();
      String raw = valueAfterMD5.toUpperCase();
      sb.append(raw.substring(0, length));
      sbString = sb.toString();
    }catch (Exception e){
      // TODO Auto-generated catch block
      System.out.println("GUID:toString:error");
      throw e;
    }
    return sbString;

  } /* * Demonstraton and self test of class */

  /*
   * * Convert to the standard format for GUID (Useful for SQL Server
   * UniqueIdentifiers, etc.) Example: C2FEEEAC-CFCD-11D1-8B05-00600806D9B6
   */
  public String toString() {
    String raw = valueAfterMD5.toUpperCase();
    StringBuffer sb = new StringBuffer();
    sb.append(raw.substring(0, 8));
    sb.append("-");
    sb.append(raw.substring(8, 12));
    sb.append("-");
    sb.append(raw.substring(12, 16));
    sb.append("-");
    sb.append(raw.substring(16, 20));
    sb.append("-");
    sb.append(raw.substring(20));
    return sb.toString();
  } /* * Demonstraton and self test of class */

  public static void main(String[] args) {
    try{
      System.out.println("GUID=" + new GUID().toString());
    }catch (Exception e){
      System.out.println("GUID:main:error");
    }
  }
}