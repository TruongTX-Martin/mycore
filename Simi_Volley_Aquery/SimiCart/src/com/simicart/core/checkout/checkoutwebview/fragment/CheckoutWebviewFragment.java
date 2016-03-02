package com.simicart.core.checkout.checkoutwebview.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.controller.ConfigCheckout;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

public class CheckoutWebviewFragment extends SimiFragment {
	protected String Url = "";

	public static CheckoutWebviewFragment newInstanse() {
		CheckoutWebviewFragment fragment = new CheckoutWebviewFragment();
		return fragment;
	}

	public void setUrl(String url) {
		this.Url = url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("core_webview_layout"), container,
				false);

		final SimiBlock simiBlock = new SimiBlock(rootView, getActivity());
		simiBlock.showLoading();
		ConfigCheckout.getInstance().setCheckStatusCart(true);
		SimiManager.getIntance().onUpdateCartQty("");
		SimiManager.getIntance().showCartLayout(false);

		final WebView webview = (WebView) rootView.findViewById(Rconfig
				.getInstance().id("webview_Ad"));
		final View mImageView = inflater.inflate(
				Rconfig.getInstance().layout("core_base_loading"), null, false);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);

		mImageView.setLayoutParams(lp);
		// add loading View
		webview.addView(mImageView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		String url_site = "";
		String url_cp = "";

		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.e("CheckoutWebviewFragment", "onPageStarted url: " + url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				webview.removeView(mImageView);
				simiBlock.dismissLoading();
				Log.e("CheckoutWebviewFragment", "onPageFinished url: " + url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.e("CheckoutWebviewFragment",
						"shouldOverrideUrlLoading url: " + url);
				view.loadUrl(url);
				return false;
			}

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed(); // Ignore SSL certificate errors
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(getActivity(), "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
			}

		});

		if (DataLocal.isSignInComplete()) {
			if (Url.contains("email_value")) {

				url_site = Url.replace("email_value", DataLocal.getEmail());
				url_cp = url_site.replace("password_value",
						DataLocal.getPassword());

				// webview.loadUrl(url_cp);
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(url_cp));
				startActivity(browserIntent);
			}
		} else {
			// webview.loadUrl(Url);
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(Url));
			startActivity(browserIntent);
		}

		return rootView;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		ConfigCheckout.getInstance().setCheckStatusCart(true);
		SimiManager.getIntance().onUpdateCartQty("");
		SimiManager.getIntance().showCartLayout(true);
		SimiManager.getIntance().backToHomeFragment();
	}
}
