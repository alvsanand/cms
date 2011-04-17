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

import java.util.Comparator;


public class TagCount extends Tag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 553393591975341940L;

	public static final Comparator<TagCount> ID_ORDER = new Comparator<TagCount>() {
		public int compare(TagCount tagCount1, TagCount tagCount2) {
			if(tagCount1.getIdTag()==null){
				return -1;
			}
			if(tagCount2.getIdTag()==null){
				return 1;
			}
			
			return tagCount2.getIdTag().compareTo(tagCount2.getIdTag());
		}
	};

	public static final Comparator<TagCount> NAME_ORDER = new Comparator<TagCount>() {
		public int compare(TagCount tagCount1, TagCount tagCount2) {
			if(tagCount1.getName()==null){
				return -1;
			}
			if(tagCount2.getName()==null){
				return 1;
			}
			
			return tagCount2.getName().compareTo(tagCount2.getName());
		}
	};

	public static final Comparator<TagCount> COUNT_ORDER_ASC = new Comparator<TagCount>() {
		public int compare(TagCount tagCount1, TagCount tagCount2) {
			if(tagCount1.getCount() > tagCount2.getCount()){
				return 1;
			}
			if(tagCount1.getCount() < tagCount2.getCount()){
				return -1;
			}
			
			return 0;
		}
	};

	public static final Comparator<TagCount> COUNT_ORDER_DESC = new Comparator<TagCount>() {
		public int compare(TagCount tagCount1, TagCount tagCount2) {
			if(tagCount1.getCount() < tagCount2.getCount()){
				return 1;
			}
			if(tagCount1.getCount() > tagCount2.getCount()){
				return -1;
			}
			
			return 0;
		}
	};

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}