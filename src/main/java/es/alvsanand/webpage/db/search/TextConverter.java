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
package es.alvsanand.webpage.db.search;

import org.compass.core.converter.ConversionException;
import org.compass.core.converter.basic.AbstractBasicConverter;
import org.compass.core.mapping.ResourcePropertyMapping;
import org.compass.core.marshall.MarshallingContext;

import com.google.appengine.api.datastore.Text;

public class TextConverter extends AbstractBasicConverter<Text> {
	@Override
	protected String doToString(Text text, ResourcePropertyMapping resourcePropertyMapping, MarshallingContext context) {
		return text.getValue();
	}

	@Override
	protected Text doFromString(String str, ResourcePropertyMapping resourcePropertyMapping, MarshallingContext marshallingContext) throws ConversionException {
		return new Text(str);
	}
}
