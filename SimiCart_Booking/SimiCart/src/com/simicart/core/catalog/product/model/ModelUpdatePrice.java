package com.simicart.core.catalog.product.model;

import com.simicart.core.base.model.SimiModel;

public class ModelUpdatePrice extends SimiModel {

	@Override
	protected void paserData() {
		// TODO Auto-generated method stub
		super.paserData();
	}

	@Override
	protected void setUrlAction() {
		url_action = "simibooking/api/get_price";
	}

}
