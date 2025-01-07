
package com.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String query ="insert into BookData(BookName, BookEdition, BookPrice)values (?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//Get the book info
		String BookName = req.getParameter("bookName");
		String BookEdition = req.getParameter("bookEdition");
		float BookPrice = Float.parseFloat(req.getParameter("bookPrice"));
		//Load jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//generate the connection
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookApp","root","Oct2024@mysql");
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, BookName);
			ps.setString(2, BookEdition);
			ps.setFloat(3, BookPrice);
			
			int count = ps.executeUpdate();
			if(count == 1) {
				pw.println("<h2>Record is registered successfully.</h2>");
			}else {
				pw.println("<h2>Record is not registered .</h2>");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		}
		catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		}
pw.println("<a href='home.html'>Home</a>");
pw.println("<br>");
pw.println("<a href='BookList'>Book List</a>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}
