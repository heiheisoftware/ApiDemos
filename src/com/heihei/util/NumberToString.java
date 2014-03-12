
package com.heihei.util;

public class NumberToString {

    /**
     * example: 9 will transfer to 0000000000000009
     * @param i
     * @return
     */
    public static String longToHexString(long l) {
        int bufLen = 16; // Max number of hex digits in an int
        char[] buf = new char[bufLen];
        int cursor = bufLen;

        int p = 0;
        do {
            buf[--cursor] = DIGITS[((int) l) & 0xF];
            l >>>= 4;
        } while (++p < bufLen);

        return new String(buf);
    }

    /**
     * example: 9 will transfer to 00000009
     * @param i
     * @return
     */
    public static String intToHexString(int i) {
        int bufLen = 8; // Max number of hex digits in an int
        char[] buf = new char[bufLen];
        int cursor = bufLen;

        int p = 0;
        do {
            buf[--cursor] = DIGITS[i & 0xF];
            i >>>= 4;
        } while (++p < bufLen);

        return new String(buf);
    }

    /**
     * example: 9 will transfer to 0009
     * @param s
     * @return
     */
    public static String shortToHexString(short s) {
        int bufLen = 4; // Max number of hex digits in an int
        char[] buf = new char[bufLen];
        int cursor = bufLen;

        int p = 0;
        do {
            buf[--cursor] = DIGITS[s & 0xF];
            s >>>= 4;
        } while (++p < bufLen);

        return new String(buf);
    }

    /**
     * example: 9 will transfer to 09
     * @param b
     * @return
     */
    public static String byteToHexString(byte b) {
        int bufLen = 2; // Max number of hex digits in an int
        char[] buf = new char[bufLen];
        int cursor = bufLen;

        int p = 0;
        do {
            buf[--cursor] = DIGITS[b & 0xF];
            b >>>= 4;
        } while (++p < bufLen);

        return new String(buf);
    }

    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
    };
}
