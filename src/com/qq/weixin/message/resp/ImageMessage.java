package com.qq.weixin.message.resp;

import com.qq.weixin.message.model.Image;

/**
 * 图片消息
 * 
 * @author Administrator
 * 
 */
public class ImageMessage extends BaseMessage {
	// 图片
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}

}
