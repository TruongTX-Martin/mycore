package com.simicart.core.checkout.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.internal.ca;
import com.simicart.core.checkout.entity.Cart;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

public class ProductOrderAdapter extends BaseAdapter {
	ArrayList<Cart> mListCart;
	View rootView;
	Context context;
	LayoutInflater inflater;
	protected String mCurrecySymbol;

	public void setCurrencySymbol(String symbol) {
		mCurrecySymbol = symbol;
	}

	private boolean flag;

	public ProductOrderAdapter(Context context, ArrayList<Cart> listProductOrder) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mListCart = listProductOrder;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public int getCount() {
		return mListCart.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (DataLocal.isLanguageRTL) {
			convertView = this.inflater.inflate(
					Rconfig.getInstance().layout(
							"rtl_listitem_product_orderhis"), null);
		} else {
			convertView = this.inflater.inflate(
					Rconfig.getInstance().layout(
							"core_listitem_product_orderhis"), null);
		}
		convertView.setBackgroundColor(Config.getInstance()
				.getApp_backrground());

		Cart cart = mListCart.get(position);

		TextView name = (TextView) convertView.findViewById(Rconfig
				.getInstance().id("name_product"));
		name.setTextColor(Config.getInstance().getContent_color());
		if (DataLocal.isLanguageRTL) {
			name.setGravity(Gravity.RIGHT);
		}
		name.setText(cart.getProduct_name());
		TextView tv_price = (TextView) convertView.findViewById(Rconfig
				.getInstance().id("price_product"));
		if (DataLocal.isLanguageRTL) {
			tv_price.setGravity(Gravity.RIGHT);
		}
		tv_price.setTextColor(Color.parseColor(Config.getInstance()
				.getPrice_color()));
		String price = Config.getInstance().getPrice(
				Float.toString(cart.getProduct_price()));
		if (null != mCurrecySymbol) {
			price = Config.getInstance().getPrice(
					Float.toString(cart.getProduct_price()), mCurrecySymbol);
		}

		tv_price.setText(price);
		TextView qty = (TextView) convertView.findViewById(Rconfig
				.getInstance().id("qty_product"));
		qty.setTextColor(Config.getInstance().getContent_color());
		qty.setText("" + cart.getQty());

		ImageView image = (ImageView) convertView.findViewById(Rconfig
				.getInstance().id("image_product"));
		String img = cart.getProduct_image();
		DrawableManager.fetchDrawableOnThread(img, image);

		TextView txt_booking_from = (TextView) convertView.findViewById(Rconfig
				.getInstance().id("txt_date_from"));
		TextView txt_booking_to = (TextView) convertView.findViewById(Rconfig
				.getInstance().id("txt_date_to"));

		if (flag == false) {
			if (Utils.validateString(cart.getBooking_time_from())) {
				txt_booking_from.setText("Booking From :"
						+ cart.getBooking_time_from());
			} else {
				txt_booking_from.setVisibility(View.GONE);
			}

			if (Utils.validateString(cart.getBooking_time_to())) {
				txt_booking_to.setText("Booking To :"
						+ cart.getBooking_time_to());
			} else {
				txt_booking_to.setVisibility(View.GONE);
			}
		}

		if (flag == true) {
			if (Utils.validateString(cart.getReservation_from())) {
				txt_booking_from.setText(cart.getReservation_from());
			} else {
				txt_booking_from.setVisibility(View.GONE);
			}
			if (Utils.validateString(cart.getReservation_to())) {
				txt_booking_to.setText(cart.getReservation_to());
			} else {
				txt_booking_to.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

}
