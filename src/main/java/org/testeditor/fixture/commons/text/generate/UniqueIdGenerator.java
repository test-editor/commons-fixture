/*******************************************************************************
 * Copyright (c) 2012 - 2018 Signal Iduna Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
 *
 * Contributors:
 * Signal Iduna Corporation - initial API and implementation
 * akquinet AG
 * itemis AG
 *******************************************************************************/

package org.testeditor.fixture.commons.text.generate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.testeditor.fixture.core.FixtureException;

public class UniqueIdGenerator {
    
    private final int characterLength = 64;
    
    
    /**
     * Generates a unique identifier as a String with 64 hexadecimal characters.
     * @return A unique identifier as a String in the form uid = 74cb6f38a27603017ed01b91433429f443b690b006c868c4288802d6241cd92d.
     * @throws FixtureException 
     */
    public String generateUniqueId() throws FixtureException  {
        String encoding = "UTF-8";
        String algorythm = "SHA-256";
        MessageDigest salt = null;
        try {
            salt = MessageDigest.getInstance(algorythm);
            salt.update(UUID.randomUUID().toString().getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
           throw new FixtureException("Unknown encoding.", FixtureException.keyValues("encoding", encoding));
        }catch (NoSuchAlgorithmException e1) {
           throw new FixtureException("Unknown algorythm.", FixtureException.keyValues("algorythmn", algorythm));

        }
        return bytesToHex(salt.digest());
    }
    
    /**
     * Generates a unique identifier as a String with the given number of hexadecimal characters.
     * 
     * @param amountOfCharacters The number of hexadecimal characters that should be returned.
     * @return A unique identifier as a String in the form 74cb6f38a2760 when the amounOfCharacters = 13.
     * @throws FixtureException When something got wrong during ID generation. 
     * @throws IllegalArgumentException when given parameter amountOfCharacters is less then 0 and greater than 64
     */
    public String generateUniquId(int amountOfCharacters) throws FixtureException {
        if (amountOfCharacters > 0 && amountOfCharacters <= characterLength) {
            return generateUniqueId().substring(0, amountOfCharacters);
        }else {
            throw new IllegalArgumentException("The number of characters should be between 1 and " + characterLength + " but was " + amountOfCharacters + ".");
        }
    }
    
    private static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (final byte b: in)
            builder.append(String.format("%02x", b));
        return builder.toString();
    }

}
