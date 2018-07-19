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

package org.testeditor.fixture.commons.text.generate;

import java.util.Random;

public class RandomNameGenerator {
    
    private static final Random randomNumber = new Random(System.currentTimeMillis());
    static final String[] CONSONANTS = new String[] {"B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q",
        "R", "S", "T", "V", "W", "X", "Y", "Z", "ß" };
    // have a slightly less likelihood on umlauts
    static final String[] VOWELS = new String[] {"A", "E", "I", "O", "U", "Ä", "Ü", "Ö", "A", "E", "I", "O" };
    static final String[] SPECIALS = new String[] {" ", "-" };
    private static final int LEN_MIN_NAME = 3;
    /** probability for special char in name = 1/(1 + PROB_SPECIALS) */
    private static final int PROB_SPECIALS = 30;
    /** max. no of specials in name */
    private static final int COUNT_MAX_SPECIALS = 1;
    
    /**
     * Generates a near realistic Name with german umlauts (ä, ö, ü) and special characters like ß.
     * @param length The maximal length of generated name.
     * @return Generated near realistic name
     */
    public static String getRandomName(int length) {
        // randomize length
        if (length < LEN_MIN_NAME) {
            length = LEN_MIN_NAME;
        }
        int len = LEN_MIN_NAME + randomNumber.nextInt(length - LEN_MIN_NAME);
        Letter lastLetter = getRandomLetter();
        String result = lastLetter.getCharacter();
        int countSpecials = 0;

        while (result.length() < len) {
            boolean isSpecial = false;
            if (countSpecials < COUNT_MAX_SPECIALS && result.length() < len - 2) {
                String special = getRandomSpecial(PROB_SPECIALS);
                if (special.length() > 0) {
                    result += special;
                    countSpecials++;
                    isSpecial = true;
                }
            }
            if (lastLetter.isVowel()) {
                lastLetter = getRandomConsonant();
            } else {
                lastLetter = getRandomVowel();
            }
            if (!isSpecial) {
                result += lastLetter.getCharacter().toLowerCase();
            } else {
                result += lastLetter.getCharacter();
            }
        }
        return result;
    }
    
    private static Letter getRandomLetter() {
        if (randomNumber.nextBoolean()) {
            return getRandomVowel();
        } else {
            return getRandomConsonant();
        }
    }

    private static Letter getRandomConsonant() {
        return new Letter(CONSONANTS[randomNumber.nextInt(CONSONANTS.length)], false);
    }
    
    private static Letter getRandomVowel() {
        return new Letter(VOWELS[randomNumber.nextInt(VOWELS.length)], true);
    }
    
    private static String getRandomSpecial(int probability) {
        int hurdle = randomNumber.nextInt(probability);
        if (hurdle == 0) {
            return SPECIALS[randomNumber.nextInt(SPECIALS.length)];
        } else {
            return "";
        }
    }
    
    private static class Letter {
        private String character;
        private boolean isVowel;

        public Letter(String character, boolean isVowel) {
            this.character = character;
            this.isVowel = isVowel;
        }

        public boolean isVowel() {
            return isVowel;
        }

        public String getCharacter() {
            return character;
        }
    }

}


