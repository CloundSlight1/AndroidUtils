package com.wuyz.androidutils;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wuyz on 2016/9/29.
 *
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
//        System.out.println("blockSize " + blockSize + ", outSize " + outSize);
        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outSize];
        while (true) {
            int n = inputStream.read(inBytes);
            if (n == blockSize) {
                int m = cipher.update(inBytes, 0, n, outBytes);
//                System.out.println("update " + m);
                outputStream.write(outBytes, 0, m);
                continue;
            }
            if (n > 0) {
                outBytes = cipher.doFinal(inBytes, 0, n);
            } else {
                outBytes = cipher.doFinal();
            }
//            System.out.println("doFinal " + outBytes.length);
            outputStream.write(outBytes);
            break;
        }
    }
}
