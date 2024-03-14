<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Management Application</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
     <header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: blue;">
        <div>
            <a href="http://www.xadmin.net" class="navbar-brand">User Management Application</a>
        </div>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath() %>/list" class="nav-link">User List</a></li>
        </ul>
    </nav>
</header>
<br>

     <div class="row">
        <div class="container">
           <h3 class="text-center">List Of Users</h3>
           <hr>
           <div class="container text-left">
              <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add New User</a>
           </div>
           <br>
           <table class="table table-bordered">
             <thead>
               <tr>
                 <th>ID</th>
                 <th>Name</th>
                 <th>Email</th>
                 <th>Country</th>
                 <th>Actions</th>
               </tr>
             </thead>
             <tbody>
                 <c:forEach var="user" items="${listUser}">
                    <tr>
                       <td><c:out value="${user.id}"/></td>
                       <td><c:out value="${user.name}"/></td>
                       <td><c:out value="${user.email}"/></td>
                       <td><c:out value="${user.country}"/></td>
                       <td><a href="edit?id=<c:out value="${user.id}"/>">Edit</a>
                       &nbsp;&nbsp;&nbsp;&nbsp;<a href="delete?id=<c:out value='${user.id}'/>">Delete</a></td>   
                    </tr>
                 </c:forEach>
             </tbody>
           </table>
        </div>
      </div>
</body>
</html>