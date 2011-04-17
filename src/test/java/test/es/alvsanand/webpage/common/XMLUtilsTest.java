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

import com.google.appengine.api.datastore.Text;

import es.alvsanand.webpage.common.XMLUtils;

public class XMLUtilsTest {
	public XMLUtilsTest(){
	}
	
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRepareText() throws Exception {
    	String incorrectText1 = "&quot;aaa\n&amp;aaa\n&yuml;aaa\n&euro;aaa";
    	String incorrectText2 = "<br>\n<br sssssssdsdsdsd=\"sdsdsdsd\">\n<hr>\n<hr sssssssdsdsdsd=\"sdsdsdsd\">\n";
    	String incorrectText3 = "&quot;aaa<br>&amp;aaa<br sssssssdsdsdsd=\"sdsdsdsd\">&yuml;aaa<hr>&euro;aaa";
    	
    	String correctText1 = "&#34;aaa\n&#38;aaa\n&#255;aaa\n&#8364;aaa";
    	String correctText2 = "<br/>\n<br/>\n<hr/>\n<hr/>\n";
    	String correctText3 = "&#34;aaa<br/>&#38;aaa<br/>&#255;aaa<hr/>&#8364;aaa";
    	
    	Assert.assertEquals(null, correctText1, XMLUtils.repareText(incorrectText1));
    	Assert.assertEquals(null, correctText2, XMLUtils.repareText(incorrectText2));   
    	Assert.assertEquals(null, correctText3, XMLUtils.repareText(incorrectText3));    	
    }

    @Test
    public void testGetFullArticleData() throws Exception {
    	String articleText1 = "Lorem ipsum ad his scripta blandit partiendo, eum fastidii accumsan euripidis in, eum liber hendrerit an.<br>" +
    			"&quot;Qui ut wisi vocibus suscipiantur, quo dicit ridens inciderint id.<br>" +
    			"Quo mundi lobortis reformidans eu, legimus senserit definiebas an eos.<br>" +
    			"<hr id=\"separator\">" +
    			"Eu sit tincidunt incorrupte definitionem, vis mutat affert percipit cu, eirmod consectetuer signiferumque eu per.<br>" +
    			"In usu latine equidem dolores. Quo no falli viris intellegam, ut fugit veritus placerat per.";
    	
    	String articleFullText1 = "Lorem ipsum ad his scripta blandit partiendo, eum fastidii accumsan euripidis in, eum liber hendrerit an.<br/>" +
				"&#34;Qui ut wisi vocibus suscipiantur, quo dicit ridens inciderint id.<br/>" +
				"Quo mundi lobortis reformidans eu, legimus senserit definiebas an eos.<br/>" +
				"Eu sit tincidunt incorrupte definitionem, vis mutat affert percipit cu, eirmod consectetuer signiferumque eu per.<br/>" +
				"In usu latine equidem dolores. Quo no falli viris intellegam, ut fugit veritus placerat per.";
    	Assert.assertEquals(null, articleFullText1, XMLUtils.getFullArticleData(new Text(articleText1)));    	
    }

    @Test
    public void testGetResumeArticleData() throws Exception {
    	String articleText1 = "Lorem ipsum ad his scripta blandit partiendo, eum fastidii accumsan euripidis in, eum liber hendrerit an.<br>" +
    			"&quot;Qui ut wisi vocibus suscipiantur, quo dicit ridens inciderint id.<br>" +
    			"Quo mundi lobortis reformidans eu, legimus senserit definiebas an eos.<br>" +
    			"<hr id=\"separator\">" +
    			"Eu sit tincidunt incorrupte definitionem, vis mutat affert percipit cu, eirmod consectetuer signiferumque eu per.<br>" +
    			"In usu latine equidem dolores. Quo no falli viris intellegam, ut fugit veritus placerat per.";
    	
    	String articleFullText1 = "Lorem ipsum ad his scripta blandit partiendo, eum fastidii accumsan euripidis in, eum liber hendrerit an.<br/>" +
				"&#34;Qui ut wisi vocibus suscipiantur, quo dicit ridens inciderint id.<br/>" +
				"Quo mundi lobortis reformidans eu, legimus senserit definiebas an eos.<br/>";
    	Assert.assertEquals(null, articleFullText1, XMLUtils.getResumeArticleData(new Text(articleText1)));    	
    }
}
