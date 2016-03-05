package com.simicart.core.catalog.product.fragment;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.catalog.product.entity.Attributes;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

public class TechSpecsFragment extends SimiFragment {

	protected ArrayList<Attributes> mAttributes;

	public static TechSpecsFragment newInstance() {
		TechSpecsFragment fragment = new TechSpecsFragment();
		return fragment;
	}

	public void setAttributes(ArrayList<Attributes> attributes) {
		mAttributes = attributes;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout(
						"core_information_description_layout"), container,
				false);
		LinearLayout ll_view = (LinearLayout) rootView.findViewById(Rconfig
				.getInstance().id("l_scrollView"));

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		ScrollView sc_techSpecs = new ScrollView(getActivity());
		sc_techSpecs.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		LinearLayout ll_techSpecs = new LinearLayout(getActivity());
		ll_techSpecs.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		ll_techSpecs.setOrientation(1);
		sc_techSpecs.addView(ll_techSpecs);

		for (Attributes attributeProduct : mAttributes) {
			String title = attributeProduct.getTitle();
			String value = attributeProduct.getValue();
			if (Utils.validateString(title)) {
				TextView tv_title = new TextView(inflater.getContext());
				tv_title.setLayoutParams(lp);
				tv_title.setText(title);
				tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				tv_title.setTypeface(Typeface.DEFAULT_BOLD);
				tv_title.setTextColor(Config.getInstance().getContent_color());
				ll_techSpecs.addView(tv_title);
			}

			if (Utils.validateString(value)) {
				TextView tv_value = new TextView(inflater.getContext());
				tv_value.setLayoutParams(lp);
				tv_value.setText(Html.fromHtml(("<font color='"
						+ Config.getInstance().getContent_color_string() + "'>"
						+ value + "</font><br>")));
				ll_techSpecs.addView(tv_value);
			}
		}

		ll_view.addView(sc_techSpecs);
		rootView.setBackgroundColor(Config.getInstance().getApp_backrground());

		return rootView;
	}
}
