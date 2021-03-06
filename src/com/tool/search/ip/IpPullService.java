package com.tool.search.ip;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/*
 * Pull解析和Sax解析很相似，都是轻量级的解析，在Android的内核中已经嵌入了Pull，所以我们不需要再添加第三方jar包来支持Pull。
 * Pull解析和Sax解析不一样的地方有
 * (1)pull读取xml文件  后触发相应的事件调用方法返回的是数字
 * (2)pull可以在程序中控制想解析到哪里就可以停止解析。
 */
public class IpPullService {
	public static List<Ip> readXml(InputStream inStream) throws Exception {
		List<Ip> ips = null;
		Ip ip = null;
		try {
			//获取XMLPull解析工厂
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			//创建XML PULL解析器
			XmlPullParser parser = factory.newPullParser();
			//设置XML Pull解析器的输入内容和编码
			parser.setInput(inStream, "GBK");
			//获取事件类型
			int eventCode = parser.getEventType();
			try {
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					switch (eventCode) {
					//判断当前事件是否是文档开始事件 
					case XmlPullParser.START_DOCUMENT: // 0 文档开始事件
						ips = new ArrayList<Ip>();
						break;
					//判断当前事件是否是标签元素开始事件  
					case XmlPullParser.START_TAG: // 2 开始元素
						if ("product".equals(parser.getName())) {
							//初始化ips集合  
							ip = new Ip();
							//person.setId(new Integer(parser.getAttributeValue(0)));
						} else if (null != ip) {
							//判断开始标签元素是否是ip
							if ("ip".equals(parser.getName())) {
								ip.setIp(parser.nextText());
							  //判断开始标签元素是否是location
							} else if ("location".equals(parser.getName())) {
								ip.setLocation(parser.nextText());
							}
						}
						break;
						//判断当前事件是否是标签元素结束事件  
					case XmlPullParser.END_TAG: // 结束元素
						if ("product".equals(parser.getName()) && ip != null) {
							//将ip添加到ips集合
							ips.add(ip);
							ip = null;
						}
						break;
					}
					//进入下一个元素并触发相应事件  
					eventCode = parser.next();
				}
			} catch (IOException e) {
				// TODO: handle exception
				System.out.println("11111111111111");
			}
		} catch (XmlPullParserException e) {
			// TODO: handle exception
			System.out.println("2222222222222");
		}		
		return ips;
	}
}
