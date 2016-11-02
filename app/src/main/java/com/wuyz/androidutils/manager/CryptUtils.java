package com.wuyz.androidutils.manager;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wuyz on 2016/9/29.
 */

public class CryptUtils {

    private static Cipher getCipher(byte[] key, String algorithm, boolean encrypt) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            SecureRandom random = new SecureRandom();
            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);
            return cipher;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void crypt(InputStream inputStream, OutputStream outputStream, Cipher cipher) throws Exception {
        int blockSize = cipher.getBlockSize();
        int outSize = cipher.getOutputSize(blockSize);
        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outSize];
        while (true) {
            int n = inputStream.read(inBytes);
            if (n == blockSize) {
                int m = cipher.update(inBytes, 0, n, outBytes);
                outputStream.write(outBytes, 0, m);
                continue;
            }
            if (n > 0) {
                outBytes = cipher.doFinal(inBytes, 0, n);
            } else {
                outBytes = cipher.doFinal();
            }
            outputStream.write(outBytes);
            break;
        }
    }

    /**
     * @param algorithm MD5 or SHA-1
     */
    public static byte[] getMessageDigest(InputStream inputStream, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] data = new byte[1024 * 4];
            int n = 0;
            while ((n = inputStream.read(data, 0, data.length)) != -1) {
                messageDigest.update(data, 0, n);
            }
            return messageDigest.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
