package com.rays.pro4.Bean;

public class OrderBean extends BaseBean{
	
	private String c_Name;
	private String account;
	private String price;
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getC_Name() {
		return c_Name;
	}
	public void setC_Name(String c_Name) {
		this.c_Name = c_Name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	@Override
	public String getValue() {
		
		return c_Name+"";
	}
	
	

}
