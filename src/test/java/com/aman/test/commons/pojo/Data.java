package com.aman.test.commons.pojo;

public class Data {
	private AllLifts[] allLifts;

	public AllLifts[] getAllLifts() {
		return allLifts;
	}

	public void setAllLifts(AllLifts[] allLifts) {
		this.allLifts = allLifts;
	}

	@Override
	public String toString() {
		return "ClassPojo [allLifts = " + allLifts + "]";
	}
}
