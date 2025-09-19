package com.tlg.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import com.tlg.util.encoders.Base64Encoder;
import com.tlg.util.encoders.UrlBase64Encoder;

public class CommonFunc {

	public static final int ALIGN_LEFT = 0;

	public static final int ALIGN_RIGHT = 1;

	public static final int ALIGN_CENTER = 2;

	private static Random random = new Random(System.currentTimeMillis());



	public CommonFunc() {
	}



	/**
	 * 
	 * @param str
	 *            the string which is going to be filled zero.
	 * @param TotalLength
	 *            the total length after filled zero
	 * @return the string after filled zero
	 */
	public static String fillZero(String str, int TotalLength) {
		String result = "";
		int len = str.length();

		if (len == 0) {
			result = repeatString("0", TotalLength);
		} else if ((len >= TotalLength) || (Long.parseLong(str) < 0)) {
			result = str;
		} else {
			result = repeatString("0", TotalLength - len) + str;
		}

		return result;
	}



	/**
	 * 
	 * @param str
	 * @param TotalLength
	 * @return
	 */
	public static String fillZeroA(String str, int TotalLength) {
		String result = "";
		int len = str.getBytes().length;

		if (len == 0) {
			result = repeatString("0", TotalLength);
		} else if (len >= TotalLength) {
			result = str.substring(0, TotalLength);
		} else {
			result = repeatString("0", TotalLength - len) + str;
		}

		return result;
	}



	/**
	 * 
	 * @param str
	 * @param TotalLength
	 * @param addfront
	 * @return
	 */
	public static String fillSpace(String str, int TotalLength, boolean addfront) {
		String OutStr = "";

		if (str.length() > TotalLength) {
			OutStr = str.substring(0, TotalLength);
		} else {
			if (addfront) {
				OutStr = repeatString(" ", TotalLength - str.length()) + str;
			} else {
				OutStr = str + repeatString(" ", TotalLength - str.length());
			}
		}

		return OutStr;
	}



	public static String fillSpaceA(String str, int TotalLength, boolean addfront) {
		String OutStr = "";

		if (str.getBytes().length > TotalLength) {
			OutStr = str.substring(0, TotalLength);
		} else {
			if (addfront) {
				OutStr = repeatString(" ", TotalLength - str.getBytes().length) + str;
			} else {
				OutStr = str + repeatString(" ", TotalLength - str.getBytes().length);
			}
		}

		return OutStr;
	}



	public static String alignField(String str, int length, byte padValue, int align) {
		byte[] input = str.getBytes();

		if (input.length > length) {
			return str.substring(0, length);
		} else {
			byte[] out = new byte[length];
			Arrays.fill(out, padValue);

			switch (align) {
			case CommonFunc.ALIGN_LEFT:
				System.arraycopy(input, 0, out, 0, input.length);

				break;

			case CommonFunc.ALIGN_RIGHT:
				System.arraycopy(input, 0, out, length - input.length, input.length);

				break;

			case CommonFunc.ALIGN_CENTER:
				System.arraycopy(input, 0, out, (length - input.length) / 2, input.length);

				break;

			default:
				System.arraycopy(input, 0, out, 0, input.length);

				break;
			}

			return new String(out);
		}
	}



	public static String alignField(String str, int length, String padValue, int align) {
		byte[] input = str.getBytes();

		if (input.length > length) {
			return str.substring(0, length);
		} else {
			StringBuffer out = new StringBuffer();
			int strLen = str.getBytes().length;

			switch (align) {
			case CommonFunc.ALIGN_LEFT:
				out.append(str);

				for (int i = 0; i < (length - strLen); i++) {
					out.append(padValue);
				}

				break;

			case CommonFunc.ALIGN_RIGHT:

				for (int i = 0; i < (length - strLen); i++) {
					out.append(padValue);
				}

				out.append(str);

				break;

			case CommonFunc.ALIGN_CENTER:

				for (int i = 0; i < ((length - strLen) / 2); i++) {
					out.append(padValue);
				}

				out.append(str);

				for (int i = 0; i < (length - (((length - strLen) / 2) + strLen)); i++) {
					out.append(padValue);
				}

				break;

			default:
				out.append(str);

				for (int i = 0; i < (length - strLen); i++) {
					out.append(padValue);
				}

				break;
			}

			return out.toString();
		}
	}



	/**
	 * 
	 * @param str
	 *            the string to be separated
	 * @param BrkStr
	 *            the string will be separated according to this string
	 * @param outstr
	 *            an array of string contains the result of separating
	 */
	public static void splitString(String str, String BrkStr, String[] outstr) {
		int i = 0;
		int p = 0;

		do {
			p = str.indexOf(BrkStr);

			if (p > 0) {
				outstr[i] = str.substring(0, p);
				str = str.substring(p, str.length());
				i++;
			}

			if (p == 0) {
				str = str.substring(1, str.length());
			}

			if (p == -1) {
				outstr[i] = str;
			}
		} while (p > -1);
	}



	/**
	 * 
	 * @param str
	 *            the string to be repeated
	 * @param count
	 *            the times to be repeated
	 * @return the string after repeated
	 */
	public static String repeatString(String str, int count) {
		StringBuffer result = new StringBuffer();

		for (int i = 1; i <= count; i++) {
			result.append(str);
		}

		return result.toString();
	}



	public static String replaceString(String str, String oldstr, String newstr) {
		int p = -1;

		while ((p = str.indexOf(oldstr, p + 1)) >= 0) {
			str = str.substring(0, p) + newstr + str.substring(p + oldstr.length(), str.length());
		}

		return str;
	}



	public static String InttoHex(int dec) {
		String hex = "";

		if (dec < 0) {
			dec = dec + 256;
		}

		hex = Integer.toHexString(dec).toUpperCase();

		return hex;
	}



	public static byte[] InttoByte(int num, int byteCount) {
		byte[] rtn = new byte[byteCount];

		for (int i = byteCount - 1; i >= 0; i--) {
			rtn[i] = (byte) (num % 256);
			num = num / 256;
		}

		return rtn;
	}



	public static byte[] InttoByte_LoByteFirst(int num, int byteCount) {
		byte[] rtn = new byte[byteCount];

		for (int i = 0; i < byteCount; i++) {
			rtn[i] = (byte) (num % 256);
			num = num / 256;
		}

		return rtn;
	}



	public static byte[] LongtoByte_LoByteFirst(long num, int byteCount) {
		byte[] rtn = new byte[byteCount];

		for (int i = 0; i < byteCount; i++) {
			rtn[i] = (byte) (num % 256);
			num = num / 256;
		}

		return rtn;
	}



	public static int BytetoInt(byte[] InData) {
		int rtn = 0;
		int num = 0;
		int n = 0;

		for (int i = InData.length - 1; i >= 0; i--) {
			if (InData[i] < 0) {
				num = InData[i] + 256;
			} else {
				num = InData[i];
			}

			rtn = rtn + (int) (Math.pow((double) 256, (double) n) * (double) num);
			n++;
		}

		return rtn;
	}



	public static int BytetoInt_LoByteFirst(byte[] InData) {
		int rtn = 0;
		int num = 0;

		for (int i = 0; i < InData.length; i++) {
			if (InData[i] < 0) {
				num = InData[i] + 256;
			} else {
				num = InData[i];
			}

			rtn = rtn + (int) (Math.pow((double) 256, (double) i) * (double) num);
		}

		return rtn;
	}



	public static long BytetoLong_LoByteFirst(byte[] InData) {
		long rtn = 0;
		long num = 0;

		for (int i = 0; i < InData.length; i++) {
			if (InData[i] < 0) {
				num = InData[i] + 256;
			} else {
				num = InData[i];
			}

			rtn = rtn + (long) (Math.pow((double) 256, (double) i) * (double) num);
		}

		return rtn;
	}



	public static String getHexTable(byte[] Data, String Encoding) throws Exception {
		String Seperator = "<---> ";
		StringBuffer OutStr = new StringBuffer();
		StringBuffer HexStr = new StringBuffer();
		StringBuffer AscStr = new StringBuffer();
		byte[] bAscStr = new byte[16];
		byte space = " ".getBytes(Encoding)[0];
		byte non = ".".getBytes(Encoding)[0];

		Arrays.fill(bAscStr, space);

		int i = 0;
		int Line = 0;

		for (i = 0; i < Data.length; i++) {
			String s = InttoHex(Data[i]);
			HexStr.append((s.length() > 1) ? s : ("0" + s));
			HexStr.append(' ');

			bAscStr[i % 16] = (((Data[i] >= 0) && (Data[i] < 32)) || (Data[i] == 37)) ? non : Data[i];

			if (((i + 1) % 16) == 0) {
				String sLine = Integer.toHexString(Line++ * 16);
				AscStr = new StringBuffer();
				AscStr.append(new String(bAscStr, Encoding));
				OutStr.append(repeatString("0", 6 - sLine.length()));
				OutStr.append(sLine);
				OutStr.append("h: ");
				OutStr.append(HexStr);
				OutStr.append(Seperator);
				OutStr.append(AscStr);
				OutStr.append("\r\n");
				HexStr = new StringBuffer();
				Arrays.fill(bAscStr, space);
			}
		}

		if (HexStr.length() > 0) {
			int count = 16 - (i % 16);
			String sLine = Integer.toHexString(Line++ * 16);
			AscStr = new StringBuffer();
			AscStr.append(new String(bAscStr, Encoding));
			OutStr.append(repeatString("0", 6 - sLine.length()));
			OutStr.append(sLine);
			OutStr.append("h: ");
			OutStr.append(HexStr);
			OutStr.append(repeatString("   ", count));
			OutStr.append(Seperator);
			OutStr.append(AscStr);
			OutStr.append("\r\n");
		}

		return OutStr.toString();
	}



	public static byte[] getBinaryDataFromHexString(String hex) {
		int p1 = hex.indexOf("{");
		int p2 = hex.indexOf("}");
		byte[] output = null;

		if ((p1 == -1) || (p2 == -1)) {
			return output;
		}

		hex = hex.substring(p1 + 1, p2 - p1).trim().toUpperCase();
		hex = replaceString(hex, ",", " ");

		StringTokenizer st = new StringTokenizer(hex);
		output = new byte[st.countTokens()];

		int index = 0;

		while (st.hasMoreTokens()) {
			String hexStr = st.nextToken();

			if (hexStr.length() == 2) {
				int hi = getBinaryValue(hexStr.charAt(0)) << 4;
				int lo = getBinaryValue(hexStr.charAt(1));
				output[index++] = (byte) (hi + lo);
			}
		}

		if (index != output.length) {
			byte[] newOutput = new byte[index];
			System.arraycopy(output, 0, newOutput, 0, index);
			output = newOutput;
		}

		return output;
	}



	private static byte getBinaryValue(char c) {
		byte out = (byte) Character.getNumericValue(c);

		if (((out < 0) || (out > 15))) {
			out = 0;
		}

		return out;
	}



	public static int getTimeinSeconds(Calendar c) {
		int hour = c.get(c.HOUR_OF_DAY);
		int min = c.get(c.MINUTE);
		int sec = c.get(c.SECOND);

		return (hour * 60 * 60) + (min * 60) + sec;
	}



	public static byte[] copyBytes(byte[] source, int beginIndex, int endIndex) {
		if ((endIndex > beginIndex) && (source.length >= (endIndex - beginIndex))) {
			byte[] out = new byte[endIndex - beginIndex];
			System.arraycopy(source, beginIndex, out, 0, out.length);

			return out;
		} else {
			return new byte[0];
		}
	}



	public static byte[] pack(String str) {
		int len = str.length() / 2;

		if ((str.length() % 2) == 1) {
			len++;
		}

		byte[] rtn = new byte[len];
		int index = 0;

		for (int i = 0; i < str.length(); i++) {
			byte b = 0;

			if ((str.charAt(i) >= 48) && (str.charAt(i) <= 57)) {
				b = (byte) (str.charAt(i) - 48);
			} else {
				b = (byte) (str.charAt(i) - 55);
			}

			if ((i % 2) == 0) {
				rtn[index] = (byte) (b << 4);
			} else {
				rtn[index] = (byte) (rtn[index] + b);
				index++;
			}
		}

		return rtn;
	}



	public static String encodeBased16(byte[] ba) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < ba.length; i++) {
			String value = Integer.toHexString((ba[i] < 0) ? (ba[i] + 256) : ba[i]).toUpperCase();

			if (value.length() < 2) {
				sb.append('0');
			}

			sb.append(value);
		}

		return sb.toString();
	}



	public static byte[] decodeBased16(String hex) {
		byte[] output = null;

		hex = replaceString(hex, " ", "");

		if ((hex.length() % 2) == 1) {
			hex = hex.substring(0, hex.length() - 1);
		}

		output = new byte[hex.length() / 2];

		int index = 0;

		for (int i = 0; i < hex.length(); i += 2) {
			int hi = getBinaryValue(hex.charAt(i)) << 4;
			int lo = getBinaryValue(hex.charAt(i + 1));
			output[index++] = (byte) (hi + lo);
		}

		if (index != output.length) {
			byte[] newOutput = new byte[index];
			System.arraycopy(output, 0, newOutput, 0, index);
			output = newOutput;
		}

		return output;
	}



	public static byte[] decodeBase64(String base64Str) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Base64Encoder coder = new Base64Encoder();
		coder.decode(base64Str, baos);

		return baos.toByteArray();
	}



	public static String encodeBase64(byte[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Base64Encoder coder = new Base64Encoder();
		coder.encode(data, 0, data.length, baos);

		return baos.toString();
	}



	public static byte[] decodeBase64URL(String base64Str) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		UrlBase64Encoder coder = new UrlBase64Encoder();
		coder.decode(base64Str, baos);

		return baos.toByteArray();
	}



	public static String encodeBase64URL(byte[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		UrlBase64Encoder coder = new UrlBase64Encoder();
		coder.encode(data, 0, data.length, baos);

		return baos.toString();
	}



	public static String getMemoryUsageInfo() {
		NumberFormat nf = NumberFormat.getInstance();
		StringBuffer info = new StringBuffer();
		info.append("Total Memory : " + nf.format(Runtime.getRuntime().totalMemory()) + " bytes");
		info.append(System.getProperty("line.separator"));
		info.append("\tMemory Used  : "
				+ nf.format(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " bytes");
		info.append(System.getProperty("line.separator"));
		info.append("\tFree Memory  : " + nf.format(Runtime.getRuntime().freeMemory()) + " bytes");
		info.append(System.getProperty("line.separator"));
		info.append("\tMax Memory  : " + nf.format(Runtime.getRuntime().maxMemory()) + " bytes");

		return info.toString();
	}



	public static byte[] getFileData(String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		byte[] data = new byte[fis.available()];
		fis.read(data);
		fis.close();

		return data;
	}



	public static String genRandomKey(int length) {
		byte[] key = new byte[length];
		random.nextBytes(key);

		return CommonFunc.encodeBased16(key);
	}



	public static String[] splitString(String str, String separator) {
		if (str == null) {
			return new String[0];
		}

		String[] values = str.split(separator);
		String[] temp = new String[values.length];

		int index = 0;

		for (int i = 0; i < values.length; i++) {
			if ((values != null) && (values[i].trim().length() > 0)) {
				temp[index++] = values[i].trim();
			}
		}

		String[] output = new String[index];
		System.arraycopy(temp, 0, output, 0, index);

		return output;
	}



	public static String regroupString(String[] strs, String separator) {
		if (strs == null) {
			return "";
		}

		StringBuffer out = new StringBuffer();

		for (int i = 0; i < strs.length; i++) {
			if ((strs[i] != null) && (strs[i].trim().length() > 0)) {
				if (out.length() > 0) {
					out.append(separator);
				}

				out.append(strs[i]);
			}
		}

		return out.toString();
	}



	public static byte[] toIPBytes(String ip) {
		byte[] data = new byte[4];
		StringTokenizer st = new StringTokenizer(ip, ".");

		for (int i = 0; i < data.length; i++) {
			try {
				data[i] = (byte) Integer.parseInt(st.nextToken().trim());
			} catch (NumberFormatException ex) {
			}
		}

		return data;
	}



	public static String toIPString(byte[] ipData) {
		if (ipData.length != 4) {
			return "";
		}

		StringBuffer out = new StringBuffer();

		for (int i = 0; i < ipData.length; i++) {
			out.append(ipData[i]);

			if (i < (ipData.length - 1)) {
				out.append(".");
			}
		}

		return out.toString();
	}
	
	public static Properties loadProperties(String path) throws Exception {
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(path);
		properties.load(fis);
		fis.close();
		fis = null;
		return properties;
	}
	
	public static Properties loadLocalProperty(String propertyName) throws IOException {
    	Properties properties = new Properties();
        String realPath = CommonFunc.class.getClassLoader().getResource(propertyName).getPath();
		FileInputStream fis = new FileInputStream(realPath);
		properties.load(fis);
		fis.close();
		fis = null;
		return properties;
    }
	
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
		
		if(source == null){
			return null;
		}
		
		if(source.size() <= n){
			n = source.size(); 
		}
		
		List<List<T>> result = new ArrayList<List<T>>();
		int remainder = source.size() % n; // (先計算出餘數)
		int number = source.size() / n; // 然後是商
		int offset = 0;// 偏移量
		for (int i = 0; i < n; i++) {
			List<T> value = null;
			if (remainder > 0) {
				value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
				remainder--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			result.add(value);
		}
		return result;
	}
}
