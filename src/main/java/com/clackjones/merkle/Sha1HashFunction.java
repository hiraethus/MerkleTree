package com.clackjones.merkle;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1HashFunction implements HashFunction {

    /**
     * copied from http://www.sha1-online.com/sha1-java/
     * (accessed: 2017-12-13)
     */
    public String hashOf(String value) {
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] result = mDigest.digest(value.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
