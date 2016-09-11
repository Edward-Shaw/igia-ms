package com.cloume.shaw.igia.management.rest;

import java.util.Collection;

public class PagingRestResponse<LT> extends RestResponse<Collection<LT>> {

	/**
	 * 总记录条数
	 */
	private long count;
	
	/**
	 * 有些情况下面获取下一个不是通过页索引号(0,1,2...)，而是通过第一条记录的某个属性，比如创建时间(排序情况下)
	 */
	private Object next;
	
	public PagingRestResponse(int code, String message, Collection<LT> result) {
		super(code, message, result);
	}
	
	public static <T> PagingRestResponse<T> result(int code, String message, Collection<T> result, long count){
		PagingRestResponse<T> instance = new PagingRestResponse<T>(code, message, result);
		instance.setCount(count);
		return instance;
	}
	
	/**
	 * 默认count属性为result.size()
	 * @param code
	 * @param message
	 * @param result
	 * @return
	 */
	public static <T> PagingRestResponse<T> result(int code, String message, Collection<T> result){
		return result(code, message, result, result.size());
	}
	
	public static <T> PagingRestResponse<T> good(Collection<T> result, long count){
		return result(0, "OK", result, count);
	}
	
	/**
	 * 默认count属性为result.size()
	 * @param result
	 * @return
	 */
	public static <T> PagingRestResponse<T> good(Collection<T> result){
		return good(result, result == null ? 0 : result.size());
	}
	
	public PagingRestResponse<LT> setPaging(Object next, int count){
		return setNext(next).setCount(count);
	}

	public long getCount() {
		return count;
	}

	public PagingRestResponse<LT> setCount(long count) {
		this.count = count;
		return this;
	}

	public Object getNext() {
		return next;
	}

	public PagingRestResponse<LT> setNext(Object next) {
		this.next = next;
		return this;
	}

}
