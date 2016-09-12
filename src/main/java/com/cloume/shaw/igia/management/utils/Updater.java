package com.cloume.shaw.igia.management.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Updater<T> {
	
	private T object;
	public Updater(T object){
		this.object = object;
	}
	
	public T update(Map<String, Object> from){
		return update(from, null);
	}
	
	public T update(Map<String, Object> from, IConverter converter){
		
		///记录通过field name没有匹配成功的
		Map<String, Object> missed = new HashMap<String, Object>();
		
		///尝试body中的每一个key
		for(Iterator<String> iterator = from.keySet().iterator(); iterator.hasNext(); ){
			String key = iterator.next();
			
			Field field = null;
			try{ field = object.getClass().getDeclaredField(key); }catch(Exception e){ }
			if(field == null) {
				missed.put(key, from.get(key));
				continue;
			}
			
			Object value = from.get(key);
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			try{
				if(converter != null){ value = converter.convert(key, value); }
				field.set(object, value); 
			} catch(Exception e) { 
				Logger.getLogger(getClass()).error(String.format("failed set field %s to value %s", key, value), e);
			}
			field.setAccessible(accessible);
		}
		
		///尝试被JsonProperty标注的属性
		Field[] fields = object.getClass().getDeclaredFields();
		for(int i = 0; i < fields.length && !missed.isEmpty(); i++){
			Field field = fields[i];
			JsonProperty annotation = field.getAnnotation(JsonProperty.class);
			if(annotation != null){
				String key = annotation.value();
				if(missed.containsKey(key)){
					Object value = from.get(key);
					boolean accessible = field.isAccessible();
					field.setAccessible(true);
					try{ 
						if(converter != null){ value = converter.convert(key, value); }
						field.set(object, value); 
					} catch(Exception e) {
						Logger.getLogger(getClass()).error(String.format("failed set field %s to value %s", key, value), e);
					}
					field.setAccessible(accessible);
					missed.remove(key);
				}
			}
		}
		
		return object;
	}
}
