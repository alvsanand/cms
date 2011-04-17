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


public enum UserState {
	REGISTERED(0), ACCEPTED(1), REJECTED(2), DISABLED(3), ENABLED(4);
	
	private int value;
	
	UserState(int value){
		this.value = value;
	}
	
	public UserState[] getPossibleUserState(){
		switch(value){
		case 0:
			return new UserState[]{ACCEPTED, REJECTED};
		case 1:
			return new UserState[]{DISABLED};
		case 2:
			return new UserState[0];
		case 3:
			return new UserState[]{ENABLED};
		case 4:
			return new UserState[]{DISABLED};
		default:
				return new UserState[0];
		}
	}
	
	public static UserState getUserState(int state){
		switch(state){
		case 0:
			return REGISTERED;
		case 1:
			return ACCEPTED;
		case 2:
			return REJECTED;
		case 3:
			return DISABLED;
		case 4:
			return ENABLED;
		default:
				return REGISTERED;
		}
	}

	public int getValue() {
		return value;
	}
}
