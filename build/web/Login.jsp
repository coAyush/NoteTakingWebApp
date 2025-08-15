<!DOCTYPE html>
<%@page session="true"%>
<%@page import="javax.servlet.http.*,javax.servlet.*" %>
<%@include file="Header.jsp" %>
<%@include file="Footer.jsp" %>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <style>
            .hi {
                display: grid;
                grid-template-columns: 1fr;
                column-gap: 10px;
            } 
        </style>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>
    <body
        <%String user = (String) session.getAttribute("name");
        String email = (String) session.getAttribute("email");%>
        <div class="container mt-5" style="max-width: 400px;">
            <h2 class="text-center mb-4">Login</h2>
            <form method="post" action="LoginServlet">
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" name="username" class="form-control" required placeholder="Enter your username" 
                           value="<%=(user != null) ? user : ""%>">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required placeholder="Enter your email" 
                       value="<%=(email != null) ? email : ""%>">
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" required placeholder="Enter your password">
                </div>

                <!-- Login buttons for Student and Admin -->
                <div class="hi">
                    <button type="submit"  class="btn btn-success w-100" style="border-radius: 12px;">Login</button>
                </div>
            </form>
                <a href="forgotPassword.jsp">Reset</a>
        </div>
    </body>
</html>
