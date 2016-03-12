package test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * http://stackoverflow.com/questions/15948662/decrypting-in-java-with-blowfish
 * */
public class EncryptionPOC {
	public static void main(String[] args) throws Exception {
		// Hex-encoded, encrypted server time. Decrypt with password from Partner passwords and skip first four bytes of garbage.
	    String syncTime = "d0ce85362801eb4a574e6d787c6b9050";
	    
	    // https://6xq.net/pandora-apidoc/json/partners/ -- android decrypted password
	    String partnerPassword = "R=U!LH$O2B#";
	    
	    // create key with password
	    SecretKeySpec key = new SecretKeySpec(partnerPassword.getBytes(), "Blowfish");
	    
	    Cipher cipher = Cipher.getInstance("Blowfish");
	    
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] decrypted = cipher.doFinal(hexToBytes(syncTime));
	    byte[] decryptedMinusGarbage = new byte[decrypted.length - 4];
	    // remove first 4 bytes of garbage
	    for(int i=0;i< decrypted.length; i++){
	    	if (i>3) {
	    		decryptedMinusGarbage[i-4] = decrypted[i];
			}
	    }
	    System.out.println("Decrypted sync time: "+ new String(decryptedMinusGarbage));
	    // calc time
	    // Synchonized time. Calculation: current time + (time of Partner login request – syncTime from Partner login response).
	  }
	public static byte[] hexToBytes(String str) {
	    if (str == null) {
	      return null;
	    } else if (str.length() < 2) {
	      return null;
	    } else {
	      int len = str.length() / 2;
	      byte[] buffer = new byte[len];
	      for (int i = 0; i < len; i++) {
	        buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
	      }
	      return buffer;
	    }

	  }

	  public static String bytesToHex(byte[] data) {
	    if (data == null) {
	      return null;
	    } else {
	      int len = data.length;
	      String str = "";
	      for (int i = 0; i < len; i++) {
	        if ((data[i] & 0xFF) < 16)
	          str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
	        else
	          str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
	      }
	      return str.toUpperCase();
	    }
	  }
}
