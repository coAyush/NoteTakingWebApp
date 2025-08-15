<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%
    String user = (String) session.getAttribute("USER");
    if (user == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Note Taking App - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #f8f9fa, #eef2f7);
        }
        .card {
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.15);
        }
        .navbar-brand img {
            filter: drop-shadow(1px 1px 2px rgba(0,0,0,0.2));
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="#">
            <img src="<%= request.getContextPath() %>/Images/download.png" alt="Logo" height="30" class="me-2">
            Note Taking App
        </a>
        <div class="d-flex">
            <form action="Logout" method="post">
                <button type="submit" class="btn btn-outline-light rounded-pill px-4">Logout</button>
            </form>
        </div>
    </div>
</nav>

<!-- Alerts -->
<div class="container mt-3">
    <% if (session.getAttribute("loginDone") != null) { %>
        <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
            <strong>✅ Logged in successfully!</strong>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% session.removeAttribute("loginDone"); %>
    <% } %>
</div>

<!-- Welcome Section -->
<div class="container mt-4">
    <div class="text-center">
        <h1 class="fw-bold text-primary">Welcome, <%= user %>!</h1>
        <p class="lead text-muted">Your personal note-taking space</p>
    </div>

    <!-- Main Actions -->
    <div class="row mt-5 justify-content-center g-4">
        <div class="col-md-4 col-sm-6">
            <div class="card shadow-sm border-0 text-center p-4">
                <h5 class="fw-bold">📝 Create a Note</h5>
                <p class="text-muted">Add a new note to your collection</p>
                <a href="CreateNotes.jsp" class="btn btn-primary px-4 rounded-pill">Create Note</a>
            </div>
        </div>

        <div class="col-md-4 col-sm-6">
            <div class="card shadow-sm border-0 text-center p-4">
                <h5 class="fw-bold">📂 View Notes</h5>
                <p class="text-muted">Browse through your saved notes</p>
                <a href="ViewServlet" class="btn btn-success px-4 rounded-pill">View Notes</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
