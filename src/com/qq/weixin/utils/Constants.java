package com.qq.weixin.utils;

/**
 * 一些常用的api
 * 
 * @author Administrator
 * 
 */
public class Constants {
	// 手机号码归属地查询
	public static final String SENDPATH = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo";

	// 笑话
	public static final String SENDPATH2 = "http://api100.duapp.com/joke/?appkey=trialuser";

	// 智能聊天
	public static final String SENDPATH3 = "http://www.wendacloud.com/openapi/api?key=bbab79b3f2feaf07e40553265f112aae&info=";

	// 天气查询
	public static final String SENDPATH4 = "http://api.map.baidu.com/telematics/v3/weather?location=LOCATION&output=json&ak=rhWnhhGCHdlpjVM6kdiy8isl";

	// 热门影片
	public static final String SENDPATH5 = "http://api.map.baidu.com/telematics/v3/movie?qt=hot_movie&location=LOCATION&output=json&ak=rhWnhhGCHdlpjVM6kdiy8isl";

	// 景点详情
	public static final String SENDPATH6 = "http://api.map.baidu.com/telematics/v3/travel_attractions?id=ID&output=json&ak=rhWnhhGCHdlpjVM6kdiy8isl";

	// 百度音乐
	public static final String SENDPATH7 = "http://box.zhangmen.baidu.com/x?op=12&count=1&title={TITLE}$${AUTHOR}$$$$";

	// 百度翻译
	public static final String SENDPATH8 = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=MZ0xVxAicygxHWx6YUnkcCLd&q={keyWord}&from=auto&to=auto";

	// 人脸检测
	public static final String SENDPATH9 = "http://apicn.faceplusplus.com/v2/detection/detect?url=URL&api_secret=18PekbemsZn-x954KT-bb18HKjRkSw9e&api_key=fbe9455f3e3721cd89c97f393a7fc0a7";

	// 周边检索
	public static final String SENDPATH10 = "http://api.map.baidu.com/telematics/v3/local?location=LOCATION&keyWord=KEYWORD&output=xml&ak=rhWnhhGCHdlpjVM6kdiy8isl";
}
