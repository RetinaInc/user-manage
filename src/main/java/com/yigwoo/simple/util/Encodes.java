package com.yigwoo.simple.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * A simple class which contains bytes from/to string methods
 * @author YigWoo
 *
 */

public class Encodes {

	/**
	 * Using Commons Codec to encode bytes into string
	 * @param input
	 * @return
	 */
	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}
	
	/**
	 * Using Commons Codec to encode string into bytes
	 * @param input
	 * @return
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException();
		}
	}
	
}
