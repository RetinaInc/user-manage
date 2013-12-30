package com.yigwoo.simple.util;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.lang3.Validate;


/**
 * Using SHA-1 to encrypt the plain password.
 * @author YigWoo
 *
 */
public class Digests {
	private static final String SHA1 = "SHA-1";
	private static SecureRandom random = new SecureRandom();
	
	public static byte[] sha1(byte[] input, byte[] salt, int iteration) {
		return digest(input, salt, iteration);
	}
	
	/**
	 * According to input, salt and iteration, performing sha1 hash to generate
	 * an encrypted password.
	 * @param input
	 * @param salt
	 * @param iteration
	 * @return
	 */
	private static byte[] digest(byte[] input, byte[] salt, int iteration) {
		try {
			MessageDigest digest = MessageDigest.getInstance(SHA1);
			if (salt != null) {
				digest.update(salt);
			}
			byte[] result = digest.digest(input);
			for (int i = 1; i < iteration; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException();
		}
	}
	
	
	/**
	 * Generate a random salt.
	 * @param bytesCount
	 * @return
	 */
	public static byte[] generateSalt(int bytesCount) {
		Validate.isTrue(bytesCount > 0,
				"bytesCount argument must be a positive integer",
				bytesCount);
		byte[] salt = new byte[bytesCount];
		random.nextBytes(salt);
		return salt;
	}
}
