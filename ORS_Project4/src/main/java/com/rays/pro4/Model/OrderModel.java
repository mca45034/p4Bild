package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.OrderBean;

import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

	public class OrderModel {
		private static Logger log = Logger.getLogger(OrderModel.class);

		public int nextPK() throws DatabaseException {

			log.debug("Model nextPK Started");

			String sql = "SELECT MAX(ID) FROM ST_BANK ";
			Connection conn = null;
			int pk = 0;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					pk = rs.getInt(1);
				}
				rs.close();
			} catch (Exception e) {

				throw new DatabaseException("Exception : Exception in getting PK");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model nextPK Started");
			return pk + 1;

		}

		public long add(OrderBean bean) throws ApplicationException, DuplicateRecordException {
			log.debug("Model add Started");

			String sql = "INSERT INTO ST_BANK VALUES(?,?,?,?)";

			Connection conn = null;
			int pk = 0;

//			OrderBean existbean = findByLogin(bean.getLogin());                               
//			if (existbean != null) {
//				throw new DuplicateRecordException("login Id already exists");
//
//			}

			try {
				conn = JDBCDataSource.getConnection();
				pk = nextPK();

				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, pk);
				pstmt.setString(2, bean.getC_Name());
				pstmt.setString(3, bean.getAccount());
				pstmt.setString(4, bean.getPrice());
				

				int a = pstmt.executeUpdate();
				System.out.println(a);
				conn.commit();
				pstmt.close();

			} catch (Exception e) {
				log.error("Database Exception ...", e);
				try {
					e.printStackTrace();
					conn.rollback();

				} catch (Exception e2) {
					e2.printStackTrace();
					// application exception
					throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
				}
			}

			finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model Add End");
			return pk;

		}

		public void delete(OrderBean bean) throws ApplicationException {
			log.debug("Model delete start");
			String sql = "DELETE FROM ST_BANK WHERE ID=?";
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, bean.getId());
				int i=pstmt.executeUpdate();
				System.out.println(i+"data deleted");
				conn.commit();
				pstmt.close();
				
			} catch (Exception e) {
				log.error("DataBase Exception", e);
				try {
					conn.rollback();
				} catch (Exception e2) {
					throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
				}
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model Delete End");
		}

		
		

		public void update(OrderBean bean) throws ApplicationException, DuplicateRecordException {
			log.debug("Model Update Start");
			String sql = "UPDATE ST_BANK SET c_name=?,Accou=?,price=? WHERE ID=?";
			Connection conn = null;
//			OrderBean existBean = findByLogin(bean.getLogin());
//			if (existBean != null && !(existBean.getId() == bean.getId())) {
//				throw new DuplicateRecordException("LoginId is Already Exist");
//			}
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bean.getC_Name());
				pstmt.setString(2, bean.getAccount());
				pstmt.setString(3, bean.getPrice());
				pstmt.setLong(4, bean.getId());
				pstmt.executeUpdate();
				int i=pstmt.executeUpdate();
				conn.commit();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("DataBase Exception", e);
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
					throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
				}
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model Update End ");
		}

		public List search(OrderBean bean) throws ApplicationException {
			return search(bean, 0, 0);
		}

		public List search(OrderBean bean, int pageNo, int pageSize) throws ApplicationException {
			log.debug("Model Search Start");
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_BANK WHERE 1=1");
			if (bean != null) {
				if (bean.getC_Name() != null && bean.getC_Name().length() > 0) {
					sql.append(" AND c_name like '" + bean.getC_Name() + "%'");
				}
			}
				
			if (pageSize > 0) {
				// Calculate start record index
				pageNo = (pageNo - 1) * pageSize;

				sql.append(" Limit " + pageNo + ", " + pageSize);
				// sql.append(" limit " + pageNo + "," + pageSize);
			}

			System.out.println(sql);
			List list = new ArrayList();
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new OrderBean();
					bean.setId(rs.getLong(1));
					bean.setC_Name(rs.getString(2));
					bean.setAccount(rs.getString(3));
					bean.setPrice(rs.getString(4));

					
				
					list.add(bean);

				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception", e);
				throw new ApplicationException("Exception: Exception in Search User");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model Search end");
			return list;

		}
		public OrderBean findByPK(long pk) throws ApplicationException {
			log.debug("Model findBy PK start");
			String sql = "SELECT * FROM ST_BANK WHERE ID=?";
			OrderBean bean = null;
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, pk);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new OrderBean();
					bean.setId(rs.getLong(1));
					bean.setC_Name(rs.getString(2));
					bean.setAccount(rs.getString(3));
					bean.setPrice(rs.getString(4));
					
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("DataBase Exception ", e);
				throw new ApplicationException("Exception : Exception in getting bank by pk");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Method Find By PK end");
			return bean;
		}
		
		public List list() throws ApplicationException {
			return list(0, 0);
		}

		public List list(int pageNo, int pageSize) throws ApplicationException {
			log.debug("Model list Started");
			ArrayList list = new ArrayList();
			StringBuffer sql = new StringBuffer("select * from ST_BANK");

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" limit " + pageNo + "," + pageSize);
			}

			Connection conn = null;

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					OrderBean bean=new OrderBean();
					bean.setId(rs.getLong(1));
					bean.setC_Name(rs.getString(2));
					bean.setAccount(rs.getString(3));
					bean.setPrice(rs.getString(4));
					

					list.add(bean);

				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception...", e);
				throw new ApplicationException("Exception : Exception in getting list of bankk");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model list End");
			return list;
		}
	

}
