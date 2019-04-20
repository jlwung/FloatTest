
public class BinaryFloat {
	public static void main(String[] args) {
		BinaryFloat bf = new BinaryFloat();

		bf.prtFloat(0.1f);
		bf.prtFloat(-0.1f);
		bf.prtFloat(0.5f);
		bf.prtFloat(-0.5f);

		System.out.println("----------------------------");
		bf.prtBinary("0-01111111-00000000000000000000000");
		bf.prtBinary("0-01111111-00000000000000000000001");
		bf.prtBinary("0-01111111-00000000000000000000010");
		bf.prtBinary("0-01111111-00000000000000000000011");
		bf.prtBinary("0-01111111-00000000000000000000100");
		bf.prtBinary("0-01111111-00000000000000000000101");

		bf.prtFloat(1.0f);
		bf.prtFloat(1.0000001f);
		bf.prtFloat(1.0000002f);
		bf.prtFloat(1.0000003f);
		bf.prtFloat(1.0000004f);
		bf.prtFloat(Float.parseFloat("1.0000003"));

		System.out.println("----------------------------");
		bf.prtFloat(bf.binaryToFloat("0-00000000-00000000000000000000001"));
		bf.prtFloat(bf.binaryToFloat("0-00000000-00000000000000000000010"));
		bf.prtFloat(bf.binaryToFloat("0-00000000-00000000000000000000011"));
		
		System.out.println("----------------------------");
		
		bf.check("", 4, 7);
		
//		for(int i=5; i<10; i++) {
//			if (bf.check("", i, 2) > 0)
//				break;
//		}
		
	
		System.out.println("over!");
	}

	int check(String prefix, int lenAppend, int lenDecimal) {
		int cntMismatch=0;
		
		if (! prefix.chars().allMatch( Character::isDigit )) {
			System.out.println("Invalid agument: prefix");
			return 0;
		}
		
		String strPrefix = prefix;
		for(int i=prefix.length()+lenAppend; i<=lenDecimal; i++){
			strPrefix = "0" + strPrefix;
		}
		
		// used when converting float to string
		String formatterFloat = "%.0" + lenDecimal + "f";
		
		//used when generating decimal suffix
		String formatterDecimal = "%0" + lenAppend + "d";

		// get value of 1000
		int maxInt = (int)Math.pow(10, lenAppend);
		
		for (int i = 0; i < maxInt; i++) {
			// get string 000, 001, 002, ... 010, ... 012, ..., 999
			String suffix = String.format(formatterDecimal, i);
			
			String originalString = strPrefix + suffix;
			
			StringBuilder sb = new StringBuilder(originalString).insert(originalString.length()-lenDecimal, ".");
			
			while (sb.charAt(0)== '0' && sb.charAt(1) != '.') {
				sb.deleteCharAt(0);
			}
			
			String paddedString = sb.toString();
			
			System.out.println("paddedString: " + paddedString);
			
			// convert string to float
			float f = Float.parseFloat(paddedString);
			
			// convert float to string using the formatter
			String floatString = String.format(formatterFloat, f);
			
			// compare 2 string: the original padded string and the converted string from float
			if (!paddedString.equals(floatString)) {
				System.out.println(paddedString + " : " + floatString + " : " + floatToBinary(f));
				cntMismatch ++;
				//if(cntMismatch > 100) break;
			}
		}
		System.out.println("missed count " + cntMismatch + " from " + maxInt + " at " + ((float)cntMismatch/(float)maxInt*100) + "%");
		return cntMismatch;
	}

	void prtFloat(float f) {
		System.out.println(f + " : " + floatToBinary(f));
	}

	void prtBinary(String str) {
		float f = binaryToFloat(str);
		System.out.println(str + " : " + f);
	}

	float binaryToFloat(String str) {
		String s = str.replaceAll("-", "");
		int intBits = Integer.parseInt(s, 2);
		float myFloat = Float.intBitsToFloat(intBits);
		return myFloat;
	}

	String floatToBinary(float f) {
		int intBits = Float.floatToIntBits(f);
		String str = Integer.toBinaryString(intBits);
		for (int i = str.length(); i < 32; i++)
			str = "0" + str;
		String s = str.substring(0, 1) + "-" + str.substring(1, 9) + "-" + str.substring(9);
		return s;
	}
}
