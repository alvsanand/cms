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
package es.alvsanand.webpage.model;


public enum ArticleState {
	DISABLED(0), ENABLED(1);
	
	private int value;
	
	ArticleState(int value){
		this.value = value;
	}
	
	public ArticleState[] getPossibleArticleState(){
		switch(value){
		case 0:
			return new ArticleState[]{ENABLED};
		case 1:
			return new ArticleState[]{DISABLED};
		default:
				return new ArticleState[0];
		}
	}
	
	public static ArticleState getArticleState(int state){
		switch(state){
		case 0:
			return DISABLED;
		case 1:
			return ENABLED;
		default:
				return DISABLED;
		}
	}

	public int getValue() {
		return value;
	}
}
