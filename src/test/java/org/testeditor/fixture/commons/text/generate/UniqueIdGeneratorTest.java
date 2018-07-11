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

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testeditor.fixture.core.FixtureException;

import static org.hamcrest.CoreMatchers.is;

public class UniqueIdGeneratorTest {
    
    private UniqueIdGenerator uidGenerator;
    
    
    @BeforeEach
    public void init() {
      uidGenerator  = new UniqueIdGenerator(); 
    }

    @Test
    public void generateUniqueIdTest() throws FixtureException {
        // given
        
        // when
        String uid = uidGenerator.generateUniqueId();
        
        // then
        assertThat(uid.length(), is(64));
    }
    

    
    @Test
    public void generateUniqueIdTestWithCorrectNumber() throws FixtureException {
        // given
        int amountofCharacters = 9;
        
        // when
        String uid = uidGenerator.generateUniquId(amountofCharacters);
        
        // then
        assertThat(uid.length(), is(9));
    }
    
    @Test
    public void generateUniqueIdTestWithWrongPositiveNumber() {
        
        // given
        int wrongAmountofCharacters = 65;
        
        //when 
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> 
        {
            uidGenerator.generateUniquId(wrongAmountofCharacters);
        });
        
        // then
        assertEquals("The number of characters should be between 1 and 64 but was 65.", exception.getMessage());
    } 

    @Test
    public void generateUniqueIdTestWithWrongNegativeNumber() {
        
        // given
        int wrongAmountofCharacters = -1;
        
        //when 
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> 
        {
            uidGenerator.generateUniquId(wrongAmountofCharacters);
        });
        
        // then
        assertEquals("The number of characters should be between 1 and 64 but was -1.", exception.getMessage());
    } 

    @Test
    public void generateUniqueIdTestWithZeroNumber() {
        
        // given
        int wrongAmountofCharacters = 0;
        
        //when 
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> 
        {
            uidGenerator.generateUniquId(wrongAmountofCharacters);
        });
        
        // then
        assertEquals("The number of characters should be between 1 and 64 but was 0.", exception.getMessage());
    } 


}
