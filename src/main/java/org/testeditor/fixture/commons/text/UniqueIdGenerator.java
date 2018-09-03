/*******************************************************************************
 * Copyright (c) 2012 - 2018 Signal Iduna Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Signal Iduna Corporation - initial API and implementation
 * akquinet AG
 * itemis AG
 *******************************************************************************/

package org.testeditor.fixture.commons.text;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.testeditor.fixture.core.FixtureException;
import org.testeditor.fixture.core.interaction.FixtureMethod;

/**
 * This class offers methods to generate a unique ID up to 64 hexadecimal characters for testing purposes.
 * These unique IDs are just for your convenience to fill input fields properly, to search for this unique 
 * ID afterwards and just get a single result.    
 *
 */
public class UniqueIdGenerator {
    
    private final int characterLength = 64;
    
    
    /**
     * Generates a unique identifier as a String with 64 hexadecimal characters as 
     * the output of a digest or cryptographic hash function, as a one-way fixed-sized-output.
     * @return A unique identifier as a String in fixed size of 64 characters in the form 
     * uid = 74cb6f38a27603017ed01b91433429f443b690b006c868c4288802d6241cd92d. 
     * Using Message Digest with salt please refer to https://en.wikipedia.org/wiki/Salt_(cryptography) 
     * @throws FixtureException When encoding or algorithm is unknown.
     */
    @FixtureMethod
    public String generateUniqueId() throws FixtureException  {
        String encoding = "UTF-8";
        String algorithm = "SHA-256";
        MessageDigest salt = null;
        try {
            salt = MessageDigest.getInstance(algorithm);
            salt.update(UUID.randomUUID().toString().getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            throw new FixtureException("Unknown encoding.", FixtureException.keyValues("encoding", encoding));
        } catch (NoSuchAlgorithmException e1) {
            throw new FixtureException("Unknown algorythm.", FixtureException.keyValues("algorythmn", algorithm));

        }
        return bytesToHex(salt.digest());
    }
    
    /**
     * Generates a unique identifier as a String with the given number of hexadecimal characters.
     * 
     * @param amountOfCharacters The number of hexadecimal characters that should be returned.
     * @return A unique identifier as a String in the form 74cb6f38a2760 when the amounOfCharacters = 13.
     * @throws FixtureException When something went wrong during ID generation, or given parameter 
     * amountOfCharacters is less then 1 and greater than 64
     */
    @FixtureMethod
    public String generateUniqueId(int amountOfCharacters) throws FixtureException {
        if (amountOfCharacters > 0 && amountOfCharacters <= characterLength) {
            return generateUniqueId().substring(0, amountOfCharacters);
        } else {
            throw new FixtureException("The number of characters should be between 1 and " + characterLength 
                    + " but was " + amountOfCharacters + ".", 
                    FixtureException.keyValues("amountOfCharacters", amountOfCharacters, 
                    "characterLength" , characterLength));
        }
    }
    
    private static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (final byte b: in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}
