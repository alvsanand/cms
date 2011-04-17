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
package es.alvsanand.webpage.web.common;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class ElParameterMap<K, V> implements Map<K, V> {
	@Override
	public Collection<V> values() {
		return null;
	}

	@Override
	public V put(K key, V value) {
		return null;
	}

	@Override
	public Set<K> keySet() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public V remove(Object key) {
		return null;
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return null;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
	}

	public abstract V get(Object key);
}
