<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@include file="Header.jsp" %>
<%@include file="Footer.jsp" %>
<%
    String status = request.getParameter("status");
    if (status == null) {
    status = (String) request.getAttribute("status");
}// from EditServlet forward
    String title = (String) request.getAttribute("title");
    String content = (String) request.getAttribute("content");
    Integer noteId = (Integer) request.getAttribute("noteId");

    boolean isEdit = (status != null && status.equalsIgnoreCase("edit"));
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title><%= isEdit ? "Edit Note" : "Create Note"%></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <div class="container mt-4" style="max-width: 700px;">
            <h2 class="mb-4 text-primary fw-bold text-center">
                <%= isEdit ? "✏ Edit Note" : "📝 Create a New Note"%>
            </h2>

            <!-- Bootstrap Alerts -->
            <% if ("added".equalsIgnoreCase(status)) { %>
            <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
                ✅ <strong>Note saved successfully!</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% } else if ("failed".equalsIgnoreCase(status)) { %>
            <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
                ❌ <strong>Failed to save the note.</strong> Please try again.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% }%>

            <!-- Note Form -->
            <form action="<%= isEdit
            ? request.getContextPath() + "/Edit"
            : request.getContextPath() + "/CreateNotes"%>" method="post" class="card shadow-sm p-4 border-0">

                <% if (isEdit) {%>
                <input type="hidden" name="id" value="<%= noteId%>">
                <% }%>

                <div class="mb-3">
                    <label class="form-label fw-bold">Note Title</label>
                    <input type="text" name="title" class="form-control" 
                           value="<%= (title != null) ? title : ""%>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold">Content</label>
                    <textarea name="content" class="form-control" rows="6" required><%= (content != null) ? content : ""%></textarea>
                </div>

                <div class="d-flex justify-content-between">
                    <a href="Home.jsp" class="btn btn-warning rounded-pill px-4">⬅ Back</a>
                    <button type="submit" class="btn btn-success rounded-pill px-4">
                        <%= isEdit ? "Update Note" : "Save Note"%>
                    </button>
                </div>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
