package com.simicart.core.base.network.volley.customrequest;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.util.Log;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.network.volley.AppController;
import com.simicart.core.base.network.volley.AuthFailureError;
import com.simicart.core.base.network.volley.NetworkResponse;
import com.simicart.core.base.network.volley.Response;
import com.simicart.core.base.network.volley.Response.ErrorListener;
import com.simicart.core.base.network.volley.Response.Listener;
import com.simicart.core.base.network.volley.toolbox.StringRequest;
import com.simicart.core.config.Config;

public class CustomRequest extends StringRequest {

	private Map<String, String> mParam = new HashMap<String, String>();
	private Map<String, String> mHeaders = new HashMap<>();
	private String url = "";

	public CustomRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		this.url = url;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		mHeaders.put("Token", Config.getInstance().getSecretKey());
		mHeaders.put("Cookie", AppController.getCookie(SimiManager.getIntance()
				.getCurrentContext()));
		mHeaders.put("Cookie", VolleyConstant.COOKIE);
		if (VolleyConstant.HASHMAP_RESPONSE.size() > 0) {
			// if (VolleyConstant.HASHMAP_RESPONSE.containsKey("CF-RAY")) {
			// mHeaders.put("CF-RAY",
			// VolleyConstant.HASHMAP_RESPONSE.get("CF-RAY"));
			// }
//			if (VolleyConstant.HASHMAP_RESPONSE.containsKey("Host-Header")) {
//				mHeaders.put("Authorization",
//						VolleyConstant.HASHMAP_RESPONSE.get("Host-Header"));
//			}
		}
		return mHeaders;
	}

	public void setParams(JSONObject object) {
		mParam.put("data", object.toString());
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mParam;
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		Map header = response.headers;
		Header[] arrayHeader = response.apacheHeaders;
		for (Header header2 : arrayHeader) {
			Log.e("HeaderArray:", header2.toString());
			String header_string = header2.toString();
			if (header_string.contains("CF-RAY")) {
				VolleyConstant.HASHMAP_RESPONSE.put("CF-RAY", header_string
						.substring(header_string.indexOf(":") + 1,
								header_string.length()));
			}
			if (header_string.contains("Host-Header")) {
				VolleyConstant.HASHMAP_RESPONSE.put("Host-Header",
						header_string.substring(header_string.indexOf(":") + 1,
								header_string.length()));
			}
			if (header_string.contains("Set-Cookie")) {
				String cookie = header_string.substring(
						header_string.indexOf(":") + 1, header_string.length())
						.trim();
				if (!VolleyConstant.COOKIE.contains(cookie)) {
					VolleyConstant.COOKIE += cookie + ";";
				}
			}
		}
		Log.e("ArrayHeader:", arrayHeader + "");
		String cookie = (String) header.get("Set-Cookie");
		AppController.saveCookie(SimiManager.getIntance().getCurrentContext(),
				cookie, url);
		return super.parseNetworkResponse(response);
	}
}
