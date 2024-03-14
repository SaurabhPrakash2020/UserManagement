package com.xadmin.usermanagement.web;
import com.xadmin.usermanagement.bean.*;
import com.xadmin.usermanagement.dao.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init() throws ServletException {
		// TODO Auto-generated method stub
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action=request.getServletPath();
		switch(action)
		{
		case "/new":
			showNewForm(request,response);
			break;
		case "/insert":
			insertUser(request,response);
			break;
		case "/delete":
			deleteUser(request,response);
			break;
		case "/edit":
			showEditForm(request,response);
			break;
		case "/update":
			try {
				updateUsers(request,response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			try {
				listUser(request,response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   RequestDispatcher rd=request.getRequestDispatcher("user-form.jsp");
		   rd.forward(request, response);
		}	
	
	
	private void insertUser(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String country=request.getParameter("country");
		User newUser=new User(name,email,country);
		UserDao.insertUser(newUser);
		response.sendRedirect("list");
	}
    private void deleteUser(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
    	int id=Integer.parseInt(request.getParameter("id"));
    	try {
    		UserDao.deleteUser(id);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	response.sendRedirect("list");
    }
    //EDIT Form
    private void showEditForm(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
    	int id=Integer.parseInt(request.getParameter("id"));
    	User existUser;
    	try {
    		existUser=UserDao.selectUser(id);
    		RequestDispatcher rd=request.getRequestDispatcher("user-form.jsp");
    		request.setAttribute("user", existUser);
    		rd.forward(request, response);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
   private void updateUsers(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException
   {
	   int id=Integer.parseInt(request.getParameter("id"));
	   String name=request.getParameter("name");
	   String email=request.getParameter("email");
	   String country=request.getParameter("country");
	   User user =new User(id,name,email,country);
	   UserDao.updateUser(user);
	   response.sendRedirect("list");
   }
   //Default 
   
   private void listUser(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException ,SQLException
   {
	   try {
		   List<User>list=UserDao.selectAllUsers();
		   request.setAttribute("listUser", list);
		   RequestDispatcher rd=request.getRequestDispatcher("user-list.jsp");
		   rd.forward(request, response);
	   }
	   catch(Exception e)
	   {
		    e.printStackTrace();
	   }
   }
    
}
