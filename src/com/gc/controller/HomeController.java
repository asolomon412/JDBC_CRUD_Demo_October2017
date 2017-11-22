package com.gc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// Step 1 import sql
import java.sql.*;
import java.util.ArrayList;

/*
 * author: Antonella Solomon
 *
 */

@Controller
public class HomeController {
	
	
	// this is an example showing how to take data from a form and just add it to a page
	@RequestMapping(value = "/submitform", method = RequestMethod.POST)
	public String submitForm(Model model, @RequestParam("customerID") String custID,
			@RequestParam("companyName") String comp, @RequestParam("contactName") String contact) {

		model.addAttribute("addDataToPage", custID + " " + comp + " " + contact);
		return "newPage";
	}

	@RequestMapping("/welcome")
	public ModelAndView helloWorld(Model model) throws ClassNotFoundException, SQLException {

		ArrayList<String> list = getCustomers();
		return new ModelAndView("welcome", "message", list);
	}

	/**
	 * This is the method that is pulling back the list every time. It was
	 * originally created in the welcome method
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private ArrayList<String> getCustomers() throws ClassNotFoundException, SQLException {
		// prep for step # 3
		String url = "jdbc:mysql://localhost:3306/northwind";
		// put your own username and password for mysql here
		String userName = "root";
		String password = "admin";
		String query = "select * from customers";

		// Step # 2: Load and register the driver
		//Class.forName("com.mysql.jdbc.Driver"); -- we don't need this unless we are using an older version

		// Step # 3 : Create Connections
		Connection con = DriverManager.getConnection(url, userName, password);

		// Step 4 : Create Statment
		Statement st = con.createStatement();

		// Step 5 : Retrieve results
		ResultSet rs = st.executeQuery(query);

		ArrayList<String> list = new ArrayList<String>();
		// Step 6: Process Results (optional)
		while (rs.next()) {

			String productID = rs.getString(1);
			String prodName = rs.getString(2);
			String supplierID = rs.getString(3);

			list.add(productID + " " + prodName + " " + supplierID);

		}
		
		rs.close();
		con.close();
		return list;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView test(@RequestParam("customerID") String custID, @RequestParam("companyName") String comp,
			@RequestParam("contactName") String contact) throws ClassNotFoundException, SQLException {
		// prep for step # 3
		String url = "jdbc:mysql://localhost:3306/northwind";
		String userName = "root";
		String password = "admin";
		// String query = "select * from customers";

		// Step # 2: Load and register the driver
		//Class.forName("com.mysql.jdbc.Driver");

		// Step # 3 : Create Connection
		Connection con = DriverManager.getConnection(url, userName, password);

		
		// String sql = "insert into customers(CustomerID, CompanyName, ContactName)"
		// + "values(12345, 'Testing','ASolomon')";
		//
		// Statement st = con.createStatement();
		// int rowCount = st.executeUpdate(sql);
		
		// instead of hardcoding these values we can use a prepared statement
		String preparedSql = "insert into customers(CustomerID, CompanyName, ContactName)" + "values(?,?,?)";
		PreparedStatement ps = con.prepareStatement(preparedSql);
		ps.setString(1, custID);
		ps.setString(2, comp);
		ps.setString(3, contact);
		ps.execute();
		con.close();
		ArrayList<String> list = getCustomers();
		return new ModelAndView("welcome", "message", list);
	}

	@RequestMapping("/update")
	public ModelAndView dbUpdate() throws ClassNotFoundException, SQLException {
		// prep for step # 3
		String url = "jdbc:mysql://localhost:3306/northwind";
		String userName = "root";
		String password = "admin";
		// String query = "select * from products";

		// Step # 2: Load and register the driver
		//Class.forName("com.mysql.jdbc.Driver");

		// Step # 3 : Create Connection
		Connection con = DriverManager.getConnection(url, userName, password);

		String sql = "update customers set CustomerID= 3456, CompanyName='Grand Circus', ContactName='Merc' where ContactName ='Help'";
		Statement st = con.createStatement();
		st.executeUpdate(sql);
		con.close();
		
		ArrayList<String> list = getCustomers();
		return new ModelAndView("welcome", "message", list);
	}

	@RequestMapping("/delete")
	public ModelAndView dbDelete() throws ClassNotFoundException, SQLException {
		// prep for step # 3
		String url = "jdbc:mysql://localhost:3306/northwind";
		String userName = "root";
		String password = "admin";
		// String query = "select * from products";

		// Step # 2: Load and register the driver
		//Class.forName("com.mysql.jdbc.Driver");

		// Step # 3 : Create Connection
		Connection con = DriverManager.getConnection(url, userName, password);

		String sql = "delete from customers where ContactName='Merc'";
		Statement st = con.createStatement();
		st.executeUpdate(sql);
		con.close();
		ArrayList<String> list = getCustomers();
		return new ModelAndView("welcome", "message", list);
	}

}
