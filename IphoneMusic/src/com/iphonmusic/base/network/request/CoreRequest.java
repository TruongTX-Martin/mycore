package com.iphonmusic.base.network.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.os.AsyncTask;
import android.util.Log;

import com.iphonmusic.base.delegate.NetworkDelegate;
import com.iphonmusic.base.network.response.CoreResponse;
import com.iphonmusic.base.network.volley.AppController;
import com.iphonmusic.base.network.volley.Request.Method;
import com.iphonmusic.base.network.volley.Response;
import com.iphonmusic.base.network.volley.VolleyError;
import com.iphonmusic.base.network.volley.customrequest.CustomRequest;

public class CoreRequest {

	private NetworkDelegate mDelegate;

	public CoreRequest(NetworkDelegate delegate) {
		this.mDelegate = delegate;

	}

	public void request(String url) {
		CustomRequest request = new CustomRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						excuteResult(response);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("error");
					}
				});
		request.setShouldCache(false);
		AppController.getInstance().addToRequestQueue(request, "tagjson");
		// try {
		// Document document = Jsoup.connect(url).get();
		// excuteResult(document);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// new getDataFromServer().execute(url);

	}

	private void excuteResult(String result) {
		CoreResponse response = new CoreResponse();
		// response.setDocument(document);
		response.setData(result);
		mDelegate.callBack(response, true);
	}

	private class getDataFromServer extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				String resString = "";
				String url = params[0];
				System.out.println(url);
				HttpClient httpclient = new DefaultHttpClient(); // Create HTTP
																	// Client
				HttpGet httpget = new HttpGet(url); // Set the action you want
													// to do
				HttpResponse response = httpclient.execute(httpget); // Executeit
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent(); // Create an InputStream
														// with the response
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) { // Read line by
																// line
					sb.append(line + "\n");
				}

				resString = sb.toString(); // Result is here

				is.close();
				return resString;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println(result);
		}

	}

}
