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
package es.alvsanand.webpage.db;

import java.util.List;

/**
 * 
 *
 * @author alvaro.santos
 * @date 23/11/2009
 *
 */
public class QueryBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5062763904089236576L;
	
	private int limit;
	private int offset;
	private List<OrderBean> orderBeans;

	/**
	 * Return the value of the field limit
	 *
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * Set the value of the field limit
	 *
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * Return the value of the field offset
	 *
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Set the value of the field offset
	 *
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Return the value of the field orderBeans
	 *
	 * @return the orderBeans
	 */
	public List<OrderBean> getOrderBeans() {
		return orderBeans;
	}

	/**
	 * Set the value of the field orderBeans
	 *
	 * @param orderBeans the orderBeans to set
	 */
	public void setOrderBeans(List<OrderBean> orderBeans) {
		this.orderBeans = orderBeans;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QueryHelper [limit=" + limit + ", offset=" + offset + ", " + (orderBeans != null ? "orderBeans=" + orderBeans : "") + "]";
	}

	/**
	 *
	 *
	 * @author alvaro.santos
	 * @date 23/11/2009
	 *
	 */
	public static class OrderBean{
		private boolean ascendingOrder;
		
		private String name;
		
		/**
		 * @param ascendingOrder
		 * @param name
		 */
		public OrderBean(boolean ascendingOrder, String name) {
			this.ascendingOrder = ascendingOrder;
			this.name = name;
		}
		/**
		 * Return the value of the field ascendingOrder
		 *
		 * @return the ascendingOrder
		 */
		public boolean isAscendingOrder() {
			return ascendingOrder;
		}
		/**
		 * Set the value of the field ascendingOrder
		 *
		 * @param ascendingOrder the ascendingOrder to set
		 */
		public void setAscendingOrder(boolean ascendingOrder) {
			this.ascendingOrder = ascendingOrder;
		}
		/**
		 * Return the value of the field name
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * Set the value of the field name
		 *
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (ascendingOrder ? 1231 : 1237);
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OrderBean other = (OrderBean) obj;
			if (ascendingOrder != other.ascendingOrder)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "OrderHelper [ascendingOrder=" + ascendingOrder + ", " + (name != null ? "name=" + name : "") + "]";
		}
	}
}
