package com.qq.weixin.message.resp;

import com.qq.weixin.message.model.Video;

/**
 * 视频消息
 * 
 * @author Administrator
 * 
 */
public class VideoMessage extends BaseMessage {
	private Video video;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
