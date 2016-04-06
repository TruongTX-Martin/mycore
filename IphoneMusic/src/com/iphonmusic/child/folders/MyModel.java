package com.iphonmusic.child.folders;

import com.iphonmusic.base.model.BaseModel;
import com.iphonmusic.config.Config;

public class MyModel extends BaseModel {

	@Override
	public void setUrl_request() {
		url_request = Config.getInstance().getUrl_mp3zing();
	}
}
