package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.BankModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;
/**
 * The Class BankCtl.
 * 
 * @author Rahul Kirar
 * 
 */
@WebServlet(name = "OrderListCtl", urlPatterns = { "/ctl/OrderListCtl" })
public class OrderListCtl extends BaseCtl{
	

	@Override
	protected void preload(HttpServletRequest request) {

		
		BankModel umodel = new BankModel();

		try {
			
		List ulist = umodel.list(0,0);

		
		request.setAttribute("accu", ulist);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		BankBean bean = new BankBean();

		bean.setId(DataUtility.getLong(request.getParameter("cid")));
		bean.setC_Name(DataUtility.getString(request.getParameter("cName")));
		bean.setAccount(DataUtility.getString(request.getParameter("accu")));
		 bean.setPrice(DataUtility.getString(request.getParameter("price")));

		return bean;
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		List nextList = null;

		int pageNo = 1;

		int pageSize =10 ;

		BankBean bean = (BankBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));


		BankModel model = new BankModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("list" + list);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			//ServletUtility.setBean(bean, request);
			
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		}
		
		ServletUtility.forward(getView(), request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("OrderListCtl doPost Start");

		List list;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? 10 : pageSize;
		String op = DataUtility.getString(request.getParameter("operation"));
		BankBean bean = (BankBean) populateBean(request);
		
		String[] ids = request.getParameterValues("ids");
		BankModel model = new BankModel();
		
		
		if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
			return;
		} else if (OP_SEARCH.equalsIgnoreCase(op)) {
			System.out.println("search chali");
			pageNo = 1;
		}
		 
		else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
			
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} 
		
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				BankBean deletebean = new BankBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("User is Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {

			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		
		ServletUtility.forward(getView(), request, response);

	}

	
	@Override
	protected String getView() {
		return ORSView.ORDER_LIST_VIEW;
	}


}
