package com.rays.proj4.Test;

import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.OrderModel;

public class BankTest {

	public static void main(String[] args) throws Exception {
		//testAdd();
		//testUpdate();
		testDelete();
		

	}

	private static void testDelete() throws Exception {
		OrderBean bean=new OrderBean();
		bean.setId(16);
		OrderModel model=new OrderModel();
		//model.delete(bean);
		
		
	}

	private static void testUpdate() throws Exception, Exception {
		OrderBean bean=new OrderBean();
		bean.setId(4);
		bean.setC_Name("ppppp");
		bean.setAccount("7859674567");
		OrderModel model=new OrderModel();
		model.update(bean);
		
	}

	private static void testAdd() throws ApplicationException, DuplicateRecordException {
		OrderBean bean=new OrderBean();
		bean.setId(4);
		bean.setC_Name("rahul");
		bean.setAccount("7859674567");
		OrderModel model=new OrderModel();
		model.add(bean);
		
	}
	

}
