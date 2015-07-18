package com.qq.weixin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;




import org.junit.Test;

import com.qq.weixin.message.model.Weather;
import com.qq.weixin.message.model.Article;
import com.qq.weixin.message.model.BaiduPlace;
import com.qq.weixin.message.model.Music;
import com.qq.weixin.message.model.UserLocation;
import com.qq.weixin.message.resp.MusicMessage;
import com.qq.weixin.message.resp.NewsMessage;
import com.qq.weixin.message.resp.TextMessage;
import com.qq.weixin.utils.BaiduMapUtil;
import com.qq.weixin.utils.GameUtil;
import com.qq.weixin.utils.MessageUtil;
import com.qq.weixin.utils.WXUtil;

/**
 * 核心处理类
 * 
 * @author Administrator
 * 
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		// 返回给微信服务器的消息,默认为null
		String respMessage = null;

		try {

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 默认回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 默认回复主菜单，回复错误时回复主菜单
			// textMessage.setContent("请求处理出错，系统自动返回主菜单 \n" + mainMenu());
			// 将文本消息对象转换成xml字符串
			// respMessage = MessageUtil.messageToXml(textMessage);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content").trim();
				// 0--主菜单
				if ("0".equals(content)) {
					textMessage.setContent(mainMenu());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 1--翻译
				else if ("1".equals(content)) {
					textMessage.setContent(getTranslateUsage());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 2--歌曲点播
				else if ("2".equals(content)) {
					textMessage.setContent(getMusicUsage());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				
				// 4--号码归属地
				else if ("3".equals(content)) {
					textMessage.setContent(getMobileUsage());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				/*
				 * // 3--人脸识别
				else if ("3".equals(content)) {
					textMessage.setContent(getFaceUsage());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 5--历史上的今天
				else if ("5".equals(content)) {
					textMessage.setContent(TodayInHistoryService
							.getTodayInHistoryInfo() + "\n\n 回复'0'返回主菜单！");
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 6--小游戏
				else if ("6".equals(content)) {
					// 创建图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setFuncFlag(0);

					List<Article> articleList = new ArrayList<Article>();

					Article article1 = new Article();
					article1.setTitle("围住神经猫");
					article1.setDescription("");
					article1.setPicUrl("http://wabc.sinaapp.com/images/cat.jpg");
					article1.setUrl("http://wabc.sinaapp.com/game/surroundCat/index.html");

					Article article2 = new Article();
					article2.setTitle("小青蛙过河");
					article2.setDescription("");
					article2.setPicUrl("http://wabc.sinaapp.com/images/frog.png");
					article2.setUrl("http://wabc.sinaapp.com/game/frog/index.html");

					Article article3 = new Article();
					article3.setTitle("更多精彩游戏敬请期待");
					article3.setDescription("");
					article3.setPicUrl("");
					article3.setUrl("");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.messageToXml(newsMessage);
				}
				
				// 7--十万个冷笑话
				else if ("7".equals(content)) {
					textMessage.setContent(JokeService.sayJoke()
							+ "\n\n 继续回复'7'进入下一条冷笑话，保证不重样噢！回复'0'返回主菜单！");
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				
				// 8--图灵机器人
				else if ("8".equals(content)) {
					textMessage.setContent(getChatUsage());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				*/
				// 5--天气查询
				else if ("4".equals(content)) {
					textMessage.setContent(getWeatherUsage());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 6--热门电影查询
				else if ("5".equals(content)) {
					textMessage.setContent(getHotMovieUsage());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 7--景点查询
				else if ("6".equals(content)) {
					textMessage.setContent(getScenerySpotUsage());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 7--一伙锅邀请好友
				else if ("7".equals(content)) {
					textMessage.setContent(getHuoguoFriends());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 7--一伙锅查看我的邀请
				else if ("8".equals(content)) {
					textMessage.setContent(getMyInvite());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 7--一伙锅设置我的偏好
				else if ("9".equals(content)) {
					textMessage.setContent(getMyFavorite());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				/*
				// 12--周边搜索
				else if ("12".equals(content)) {
					textMessage.setContent(getSubscribeUsage());
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				*/
				// 以下为相关服务
				// 翻译服务
				else if (content.startsWith("翻译")) {
					String keyWord = content.replaceAll("^翻译", "").trim();
					if ("".equals(keyWord)) {
						textMessage.setContent(getTranslateUsage());
					} else {
						textMessage.setContent(BaiduTranslateService
								.translate(keyWord));
					}
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 歌曲点播服务
				else if (content.startsWith("歌曲")) {
					// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉
					String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?",
							"");
					// 如果歌曲名称为空
					if ("".equals(keyWord)) {
						textMessage.setContent(getMusicUsage());
						respMessage = MessageUtil.messageToXml(textMessage);
					} else {
						String[] kwArr = keyWord.split("@");
						// 歌曲名称
						String musicTitle = kwArr[0];
						// 演唱者默认为空
						String musicAuthor = "";
						if (2 == kwArr.length)
							musicAuthor = kwArr[1];

						// 搜索音乐
						Music music = BaiduMusicService.searchMusic(musicTitle,
								musicAuthor);
						// 未搜索到音乐
						if (null == music) {
							textMessage.setContent("对不起，没有找到您想听的歌曲<"
									+ musicTitle + ">。");
						} else {
							// 音乐消息
							MusicMessage musicMessage = new MusicMessage();
							musicMessage.setToUserName(fromUserName);
							musicMessage.setFromUserName(toUserName);
							musicMessage.setCreateTime(new Date().getTime());
							musicMessage
									.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
							musicMessage.setMusic(music);
							respMessage = MessageUtil
									.messageToXml(musicMessage);
						}
					}
				}
				// 号码归属地查询服务
				else if (content.startsWith("号码")) {
					String keyWord = content.replaceAll("^号码", "").trim();
					if ("".equals(keyWord)) {
						textMessage.setContent(getMobileUsage());
					} else {
						textMessage.setContent(MobileService
								.mobilePlace(keyWord));
					}
					respMessage = MessageUtil.messageToXml(textMessage);
				}
				// 天气查询服务
				else if (content.startsWith("天气")) {
					String keyWord = content.replaceAll("^天气", "").trim();
					if ("".equals(keyWord)) {
						textMessage.setContent(getWeatherUsage());
						respMessage = MessageUtil.messageToXml(textMessage);
					} else if (keyWord.startsWith("明后")) {
						keyWord = keyWord.replaceAll("^明后", "").trim();
						Weather weather = new Weather();
						weather = WeatherService.weatherDetect(keyWord);
						List<Map<String, String>> weather_data = new ArrayList<Map<String, String>>();
						weather_data = weather.getWeather_data();

						// 创建图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage
								.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setFuncFlag(0);

						List<Article> articleList = new ArrayList<Article>();
						// 多图文消息
						Article article1 = new Article();
						article1.setTitle(weather_data.get(1).get("date")
								+ "\n" + weather_data.get(1).get("weather")
								+ weather_data.get(1).get("wind")
								+ weather_data.get(1).get("temperature"));
						article1.setDescription("");
						article1.setPicUrl(weather_data.get(1).get(
								"dayPictureUrl"));
						article1.setUrl("");

						Article article2 = new Article();
						article2.setTitle(weather_data.get(2).get("date")
								+ "\n" + weather_data.get(2).get("weather")
								+ weather_data.get(2).get("wind")
								+ weather_data.get(2).get("temperature"));
						article2.setDescription("");
						article2.setPicUrl(weather_data.get(2).get(
								"dayPictureUrl"));
						article2.setUrl("");

						Article article3 = new Article();
						article3.setTitle("以上即为小编为您提供的" + keyWord + "未来两天的天气信息");
						article3.setDescription("");
						article3.setPicUrl("");
						article3.setUrl("");

						articleList.add(article1);
						articleList.add(article2);
						articleList.add(article3);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.messageToXml(newsMessage);
					}

					else {
						// 获取返回的weather
						Weather weather = new Weather();
						weather = WeatherService.weatherDetect(keyWord);
						List<Map<String, String>> index = new ArrayList<Map<String, String>>();
						index = weather.getIndex();
						List<Map<String, String>> weather_data = new ArrayList<Map<String, String>>();
						weather_data = weather.getWeather_data();

						String des = "";

						// 将index里面数据的拼接
						for (int i = 0; i < index.size(); i++) {
							des += index.get(i).get("title") + ":"
									+ index.get(i).get("zs") + "。"
									+ index.get(i).get("tipt") + ":"
									+ index.get(i).get("des") + "\n";
						}

						// 创建图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage
								.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setFuncFlag(0);

						List<Article> articleList = new ArrayList<Article>();
						// 多图文消息
						Article article1 = new Article();
						article1.setTitle(keyWord + "   "
								+ weather_data.get(0).get("date"));
						article1.setDescription(weather_data.get(0).get(
								"weather")
								+ "\n"
								+ weather_data.get(0).get("wind")
								+ "\n"
								+ weather_data.get(0).get("temperature")
								+ "\n\n" + des);
						article1.setPicUrl(weather_data.get(0).get(
								"dayPictureUrl"));
						article1.setUrl("");

						articleList.add(article1);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.messageToXml(newsMessage);
					}
				}
				// 热门电影查询服务
				else if (content.startsWith("电影")) {
					String keyWord = content.replaceAll("^电影", "").trim();
					if ("".equals(keyWord)) {
						textMessage.setContent(getHotMovieUsage());
						respMessage = MessageUtil.messageToXml(textMessage);
					} else {
						// 创建图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage
								.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setFuncFlag(0);

						List<Article> articleList = new ArrayList<Article>();
						articleList.add(HotMovieService
								.hotMovieMessage(keyWord));
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.messageToXml(newsMessage);
					}
				}
				// 景点查询服务
				else if (content.startsWith("景点")) {
					String keyWord = content.replaceAll("^景点", "").trim();
					if ("".equals(keyWord)) {
						textMessage.setContent(getScenerySpotUsage());
						respMessage = MessageUtil.messageToXml(textMessage);
					} else {
						// 创建图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage
								.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setFuncFlag(0);

						List<Article> articleList = new ArrayList<Article>();
						articleList.add(ScenerySpotService
								.scenerySpotMessage(keyWord));
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.messageToXml(newsMessage);
					}
				}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				textMessage.setContent("您发送的是链接消息！暂未开通该类消息相关功能，敬请期待！");
				respMessage = MessageUtil.messageToXml(textMessage);
			}
			else
			{
				textMessage.setContent("输入有误，请查看输入格式，或暂无此功能！");
				respMessage = MessageUtil.messageToXml(textMessage);
			}
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

	// 主菜单
	private static String mainMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue110 目前一伙锅开通的功能如下：").append("\n");
		buffer.append("    \ue210 1. 翻译").append("\n");
		buffer.append("    " + WXUtil.emoji(0x1F3B5) + " 2.音乐点播").append("\n");
		//buffer.append("    " + WXUtil.emoji(0x1F466) + " 3.人脸识别").append("\n");
		buffer.append("    \ue00a 3.号码归属地查询").append("\n");
		//buffer.append("    " + WXUtil.emoji(0x1F47B) + " 5.十万个冷笑话（没有幽默感）")
		//		.append("\n");
		//buffer.append("    " + WXUtil.emoji(0x1F47D) + " 6.智能陪聊（不提供）").append("\n");
		buffer.append("    \ue331 4.天气查询").append("\n");
		buffer.append("    \ue324 5.热门影片查询").append("\n");
		buffer.append("    \ue037 6.景点查询").append("\n");
		buffer.append("    "+ WXUtil.emoji(0x1F466)+" 7.一伙锅邀请好友").append("\n");
		buffer.append("    " + WXUtil.emoji(0x1F47D) + " 8.一伙锅查看邀请").append("\n");
		buffer.append("    " + WXUtil.emoji(0x1F47B) + " 9.一伙锅设置我的偏好").append("\n");
		//buffer.append("    " + WXUtil.emoji(0x26F3) + " 12.周边搜索").append("\n");
		//buffer.append("    " + WXUtil.emoji(0x2B55) + " 13.猜数字游戏").append("\n");
		//buffer.append(
		//		"    \ue009 <a href=\"http://wpa.qq.com/msgrd?v=3&uin=492203171&site=qq&menu=yes\">联系小编</a>")
		//		.append("\n");
		//buffer.append(
		//		"    \ue428 <a href=\"http://mq.wsq.qq.com/259884321?_wv=1&filterType=&source=custom\">进入微社区</a>")
		//		.append("\n\n");
		buffer.append("回复相应编号进入相应功能,例如：").append("\n");
		buffer.append("回复'1'进入翻译功能").append("\n");
		//buffer.append("如有疑问您可以点击'联系小编'直接与小编进行qq聊天").append("\n");
		//buffer.append("同样您也可以点击'进入微社区'进行发帖咨询或者分享！").append("\n")
		buffer.append("（呼朋唤友功能，开发中，敬请期待）").append("\n\n");
		return buffer.toString();
	}

	// 号码归属地查询指南
	private static String getMobileUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue13d 号码归属地查询操作指南").append("\n\n");
		buffer.append("回复：号码+手机号码").append("\n");
		buffer.append("例如：号码18692003840").append("\n\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}

	// 歌曲点播操作指南
	private static String getMusicUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue13d 歌曲点播操作指南").append("\n\n");
		buffer.append("回复：歌曲+歌名或歌曲+歌名+歌手").append("\n");
		buffer.append("例如：歌曲泡沫").append("\n");
		buffer.append("或者：歌曲泡沫@邓紫棋").append("\n\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}

	// 翻译指南
	private static String getTranslateUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue13d 翻译操作指南").append("\n\n");
		buffer.append("J译通为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
		buffer.append("    \ue513 中 -> \ue50c  英").append("\n");
		buffer.append("    \ue50c 英 -> \ue513 中").append("\n");
		buffer.append("    \ue50b 日 -> \ue513  中").append("\n\n");
		buffer.append("使用示例：").append("\n");
		buffer.append("    翻译我是中国人").append("\n");
		buffer.append("    翻译dream").append("\n");
		buffer.append("    翻译さようなら").append("\n\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}

	// 人脸识别指南
	private static String getFaceUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue13d 人脸识别操作指南").append("\n\n");
		buffer.append("发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息").append("\n");
		buffer.append("快来试试你是不是长得太着急了吧").append("\n\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}

	// 智能聊天
	private static String getChatUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue13d 智能陪聊操作指南").append("\n\n");
		buffer.append("亲，是不是无聊了呀。我是小j机器人，让我来陪您聊会天吧！").append("\n");
		buffer.append("回复任意文字即可开始聊天噢!O(∩_∩)O~~");
		return buffer.toString();
	}

	// 天气查询
	private static String getWeatherUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue13d 天气查询操作指南").append("\n\n");
		buffer.append("回复：天气+地区").append("\n");
		buffer.append("例如：天气长沙,获取长沙即时的天气信息").append("\n");
		buffer.append("回复：天气明后+地区").append("\n");
		buffer.append("例如：天气明后长沙,获取长沙未来两天的天气信息").append("\n\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}

	// 热门电影查询
	private static String getHotMovieUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue13d 热门电影查询操作指南").append("\n\n");
		buffer.append("回复：电影+城市名").append("\n");
		buffer.append("例如：电影长沙，即可获取当前长沙地区的热映电影").append("\n");
		buffer.append("回复'0'返回主菜单！").append("\n\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}

	// 热门电影查询
	private static String getScenerySpotUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue13d 景点查询操作指南").append("\n\n");
		buffer.append("回复：景点+景点的拼音").append("\n");
		buffer.append("例如：景点taishan，即可获取泰山的详细介绍").append("\n");
		buffer.append("如有景点拼音相同则显示热度较高的那个").append("\n\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}

	// 周边搜索
	private static String getSubscribeUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue13d 周边搜索操作指南").append("\n\n");
		buffer.append("1）发送地理位置").append("\n");
		buffer.append("点击窗口底部的“+”按钮，选择“位置”，点“发送”").append("\n\n");
		buffer.append("2）指定关键词搜索").append("\n");
		buffer.append("格式：周边+关键词\n例如：周边ATM、周边KTV、周边厕所").append("\n\n");
		buffer.append("注意：第一次搜索需要发送当前位置，若您的位置发生变化，请及时发送最新位置，以便获得更优质的服务！")
				.append("\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}
	
	
	private static String getHuoguoFriends() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("开发中，敬请期待！").append("\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}

	private static String getMyInvite() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("开发中，敬请期待！").append("\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}
	private static String getMyFavorite() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("开发中，敬请期待！").append("\n");
		buffer.append("回复'0'返回主菜单！").append("\n");
		return buffer.toString();
	}
	// 判断是否为数字
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	@Test
	public void test1() {
		System.out.println(ChatService.chat("衡阳天气"));
	}
}