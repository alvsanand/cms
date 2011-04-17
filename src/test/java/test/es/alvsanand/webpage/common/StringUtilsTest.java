/*
 * Copyright 2008-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.es.alvsanand.webpage.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.alvsanand.webpage.common.StringUtils;

public class StringUtilsTest {
	public StringUtilsTest(){
	}
	
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testValidateRegExp() throws Exception {
    	String emailRegularExpression = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
    	String validEmail1 = "aaaa@aaa.com";
    	String validEmail2 = "aaaa@aaa.co";
    	String invalidEmail1 = "aaaa";
    	String invalidEmail2 = "aaaa@ddd";
    	String invalidEmail3 = "@dddd.com";    	
    	
    	Assert.assertTrue(StringUtils.validateRegExp(validEmail1, emailRegularExpression));
    	Assert.assertTrue(StringUtils.validateRegExp(validEmail2, emailRegularExpression));
    	Assert.assertFalse(StringUtils.validateRegExp(invalidEmail1, emailRegularExpression));
    	Assert.assertFalse(StringUtils.validateRegExp(invalidEmail2, emailRegularExpression));
    	Assert.assertFalse(StringUtils.validateRegExp(invalidEmail3, emailRegularExpression));    	
    }

    @Test
    public void testGetValidName() throws Exception {
    	String invalidName1 = "aaaa aaaa";
    	String invalidName2 = "aaaa@ddd";
    	String invalidName3 = "$&/(";
    	String correctName1 = "aaaa_aaaa";
    	String correctName2 = "aaaa_ddd";
    	String correctName3 = "____";
    	
    	Assert.assertEquals(correctName1, StringUtils.getValidName(invalidName1));
    	Assert.assertEquals(correctName2, StringUtils.getValidName(invalidName2));
    	Assert.assertEquals(correctName3, StringUtils.getValidName(invalidName3));  	
    }
}
