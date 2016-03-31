package com.iphonmusic.base.delegate;

import com.iphonmusic.base.network.response.BaseResponse;

public interface ModelDelegate {
	public abstract void callBack(BaseResponse coreResponse, boolean isSuccess);
}
