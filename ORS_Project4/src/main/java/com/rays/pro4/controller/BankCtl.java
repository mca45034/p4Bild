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
import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.BankModel;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Model.BankModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "BankCtl", urlPatterns = { "/ctl/BankCtl" })
public class BankCtl extends BaseCtl{
	

	
	  @Override
	  protected boolean validate(HttpServletRequest request) {
	  System.out.println("uctl Validate");
	  
	  boolean pass = true;
	  
	  if (DataValidator.isNull(request.getParameter("cName"))) {
	  request.setAttribute("cName", PropertyReader.getValue("error.require","shop Name"));
	  pass = false;
	  } 
	  else if
	  (!DataValidator.isName(request.getParameter("cName"))) {
	  request.setAttribute("cName","Shop name must contains alphabet only");
	  pass = false; 
	  }
	  
	  if (DataValidator.isNull(request.getParameter("accu"))) {
	  request.setAttribute("accu", PropertyReader.getValue("error.require","order")); 
	  pass = false; 
	  }
	  else if
	  (!DataValidator.isName(request.getParameter("accu"))) {
	  request.setAttribute("accu","Order must contains alphabet only");
	  pass = false; 
	  }
	  if (DataValidator.isNull(request.getParameter("price"))) {
		  request.setAttribute("price", PropertyReader.getValue("error.require","price")); 
		  pass = false; 
		  }else if
	  (!DataValidator.isInteger(request.getParameter("price"))) {
	  request.setAttribute("price"," Price must contains number only");
	  pass = false; 
	  }
	  
	return pass; 
	  }
	

	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println(" uctl Base bean P bean");

		BankBean bean = new BankBean();

		bean.setId(DataUtility.getLong(request.getParameter("cid")));
		  bean.setC_Name(DataUtility.getString(request.getParameter("cName")));
		  bean.setAccount(DataUtility.getString(request.getParameter("accu")));
		  bean.setPrice(DataUtility.getString(request.getParameter("price")));
		  
		

		populateDTO(bean, request);


		return bean;

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		BankModel model = new BankModel();
		long id = DataUtility.getLong(request.getParameter("cid"));
		if (id > 0 || op != null) {
			BankBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		
		ServletUtility.forward(getView(), request, response);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("cid"));


		BankModel model = new BankModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			BankBean bean = (BankBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("User is successfully Updated", request);

				} else {
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("User is successfully Added", request);
					bean.setId(pk);
				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		}  else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);


	}

	
	@Override
	protected String getView() {
		return ORSView.BANK_VIEW;
	}
}
	
	