package com.cloume.shaw.igia.management.utils;

public class Const {
	
	//货品数据状态
	public static final String STATE_DELETED = "作废";  //删除状态
	public static final String STATE_NORMAL = "正常";  //正常状态
	public static final String STATE_FINISH = "已完成";  //完成状态
	public static final String STATE_TERMINATE = "终止";  //终止状态
	
	public static final String STORAGE_INIT = "待上货";
	public static final String STORAGE_ING = "正在上货";
	public static final String STORAGE_EXCEPTION = "上货异常";
	public static final String STORAGE_FINISHED = "上货完成";
	
	public static final String CHECK_INIT = "待盘库";
	public static final String CHECK_ING = "正在盘库";
	public static final String CHECK_EXCEPTION = "盘库异常";
	public static final String CHECK_FINISHED = "盘库完成";
	
	public static final String STOCK_INIT = "待拣货";
	public static final String STOCK_ING = "正在拣货";
	public static final String STOCK_EXCEPTION = "拣货异常";
	public static final String STOCK_FINISHED = "拣货完成";
	
	public static final String STATE_INIT = "0";
	public static final String STATE_FINISHED = "1";

}
