package cn.com.sinosoft.ims.util;

public class PasswordUtil {
	
	 public String fencode(String s) {
			char ac[] = new char[8];
			String s1 = " ";
			String s2 = "54176382";
			String s3 = "1e4s2dj6l38 5097vw.";
			String s4 = "ab cdefghijklmn102p";
			int i = 0;
			boolean flag = false;
			boolean flag1 = false;
			i = s.length();
			if (s.length() < 8) {
				for (int j = 0; j < 8 - i; j++)
					s = s + s1;

			}
			for (int k = 0; k < 8; k++)
				ac[k] = s.charAt(Integer.parseInt((new Character(s2.charAt(k)))
						.toString()) - 1);

			for (int l = 0; l < 8; l++) {
				for (int i1 = 0; i1 < 19; i1++)
					if (ac[l] == s3.charAt(i1))
						ac[l] = s4.charAt(i1);

			}

			String s5 = new String(ac);
			return s5;
		}

		public String fdecode(String s) {
			char ac[] = new char[8];
			char ac1[] = new char[8];
			String s1 = " ";
			String s2 = "54176382";
			String s3 = "1e4s2dj6l38 5097vw.";
			String s4 = "ab cdefghijklmn102p";
			boolean flag = false;
			boolean flag1 = false;
			int j1 = s.length();
			if (j1 < 8) {
				for (int i = 0; i < 8 - j1; i++)
					s = s + s1;

			}
			ac = s.toCharArray();
			for (int j = 0; j < 8; j++) {
				for (int l = 0; l < 19; l++)
					if (s.charAt(j) == s4.charAt(l))
						ac[j] = s3.charAt(l);

			}

			for (int k = 0; k < 8; k++)
				if (ac[k] != ' ') {
					int i1 = Integer.parseInt((new Character(s2.charAt(k)))
							.toString()) - 1;
					ac1[i1] = ac[k];
				}

			String s5 = new String(ac1);
			return s5;
		}
}
