package cn.edu.scnu.common;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    /** * 使用md5的算法进行加密*/
    public static String md5(String plainText) {
        if(plainText!=null){
            byte[] secretBytes = null;
            try {
                secretBytes = MessageDigest.getInstance("md5").digest(
                        plainText.getBytes());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("没有md5这个算法！");
            }
            String md5code = new BigInteger(1, secretBytes).toString(16);
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code = "0" + md5code;
            }
            return md5code;
        }else{
            return null;
        }
    }
    public static void main(String[] args) {
        System.out.println(md5("123"));
    }
}
