<%-- 
    Document   : View
    Author     : User
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page session="true"%>
<%@include file="Header.jsp" %>
<%@include file="Footer.jsp" %>
<%@ page import="Notes.Bean"%>

<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    Object msgAttr = request.getAttribute("msg"); // expected: "updated" | "deleted" | "downloaded"
    String flashMsg = msgAttr == null ? "" : msgAttr.toString();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Notes</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons (for crisp icons instead of emojis) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body { background-color: #f6f7fb; }
        .page-title { color: #2563eb; letter-spacing: .3px; }

        /* Card styling */
        .note-card .card {
            border: 0;
            border-radius: 16px;
            box-shadow: 0 8px 24px rgba(0,0,0,.06);
            transition: transform .18s ease, box-shadow .18s ease;
        }
        .note-card .card:hover {
            transform: translateY(-3px);
            box-shadow: 0 14px 28px rgba(0,0,0,.08);
        }
        .note-title {
            font-weight: 700;
            color: #1f2937;
            font-size: 1.1rem;
            margin-bottom: .35rem;
        }
        .note-content {
            color: #4b5563;
            white-space: pre-line;              /* keep user line breaks */
            display: -webkit-box;               /* clamp to 5 lines */
            -webkit-line-clamp: 5;
            -webkit-box-orient: vertical;
            overflow: hidden;
            min-height: 5.5em;
        }
        .note-meta {
            font-size: .86rem;
            color: #6b7280;
        }
        .action-stack .btn {
            border-radius: .7rem;
        }
        .search-box {
            max-width: 520px;
            margin: 0 auto 1.25rem auto;
        }
        #noResults {
            display: none;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold text-primary" href="Home.jsp">NoteApp</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#nav"
                aria-controls="nav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div id="nav" class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link active" href="Home.jsp">Home</a></li>
                <li class="nav-item"><a class="nav-link text-danger" href="Logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container py-4">

    <h2 class="page-title text-center mb-3">Your Notes</h2>

    <!-- Flash message (updated/deleted/downloaded) -->
    <div id="flash" class="alert alert-success text-center shadow-sm d-none" role="alert"></div>

    <!-- Search -->
    <div class="search-box">
        <div class="input-group">
            <span class="input-group-text bg-white"><i class="bi bi-search"></i></span>
            <input type="text" id="searchInput" class="form-control" placeholder="Search notes by title or content...">
            <button id="clearSearch" class="btn btn-outline-secondary" type="button"><i class="bi bi-x-circle"></i></button>
        </div>
        <div class="form-text text-center mt-1" id="resultCount"></div>
    </div>

    <!-- Cards -->
    <div class="row g-4" id="notesContainer">
        <%
            List<Bean> notes = (List<Bean>) request.getAttribute("list");
            if (notes != null && !notes.isEmpty()) {
                for (Bean n : notes) {
                    String title = n.getTitle();
                    String content = n.getContent();
                    LocalDateTime c = n.getCreatedAt();
                    LocalDateTime u = n.getUpdatedAt();
        %>
        <div class="col-12 col-sm-6 col-lg-4 col-xl-3 note-card">
            <div class="card h-100">
                <div class="card-body d-flex flex-column">
                    <div class="note-title"><%= title %></div>
                    <div class="note-content mb-3"><%= content %></div>

                    <div class="note-meta mt-auto">
                        <div class="mb-1"><i class="bi bi-calendar-event me-1"></i>Created:
                            <%= (c != null) ? c.format(formatter) : "-" %></div>
                        <div class="mb-2"><i class="bi bi-pencil-square me-1"></i>Updated:
                            <%= (u != null) ? u.format(formatter) : "-" %></div>
                    </div>

                    <div class="d-grid gap-2 action-stack mt-2">
                        <a href="Edit?id=<%= n.getNoteId() %>"
                           class="btn btn-outline-primary"
                           onclick="return confirmEdit();">
                            <i class="bi bi-pencil-square me-1"></i>Edit
                        </a>

                        <a href="Edit?id=<%= n.getNoteId() %>&action=delete"
                           class="btn btn-outline-danger"
                           onclick="return confirmDelete();">
                            <i class="bi bi-trash me-1"></i>Delete
                        </a>

                        <a href="Download?id=<%= n.getNoteId() %>"
                           class="btn btn-outline-warning"
                           onclick="return confirmDownload();">
                            <i class="bi bi-download me-1"></i>Download
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <%
                }
            } else {
        %>
        <div class="col-12">
            <div class="alert alert-warning text-center shadow-sm">No notes available to display.</div>
        </div>
        <%
            }
        %>
    </div>

    <!-- No results banner for search -->
    <div id="noResults" class="alert alert-info text-center mt-3">
        No notes match your search.
    </div>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // --- Flash message handling ---
    (function showFlash(){
        const msg = "<%= flashMsg %>";
        if(!msg) return;
        const flash = document.getElementById('flash');
        let text = '';
        if (msg === 'updated')   text = '✅ Note updated successfully.';
        if (msg === 'deleted')   text = '🗑️ Note deleted successfully.';
        if (msg === 'downloaded')text = '📥 Download started.';
        if (text) {
            flash.textContent = text;
            flash.classList.remove('d-none');
            setTimeout(() => flash.classList.add('d-none'), 3500);
        }
    })();

    // --- Search functionality with count + no-results ---
    const searchInput = document.getElementById('searchInput');
    const clearSearch = document.getElementById('clearSearch');
    const cards = document.getElementsByClassName('note-card');
    const noResults = document.getElementById('noResults');
    const resultCount = document.getElementById('resultCount');

    function applyFilter() {
        const q = searchInput.value.trim().toLowerCase();
        let visible = 0;
        for (let i = 0; i < cards.length; i++) {
            const text = cards[i].innerText.toLowerCase();
            const show = text.includes(q);
            cards[i].style.display = show ? '' : 'none';
            if (show) visible++;
        }
        noResults.style.display = (visible === 0 && q.length > 0) ? 'block' : 'none';
        if (q.length === 0) { resultCount.textContent = ''; }
        else { resultCount.textContent = visible + ' result' + (visible === 1 ? '' : 's'); }
    }

    searchInput.addEventListener('keyup', applyFilter);
    clearSearch.addEventListener('click', function(){
        searchInput.value = '';
        applyFilter();
        searchInput.focus();
    });

    // --- Action confirmations ---
    function confirmEdit(){
        return confirm('Open this note in edit mode?');
    }
    function confirmDelete(){
        return confirm('This will permanently delete the note. Continue?');
    }
    function confirmDownload(){
        return confirm('Download this note as a file?');
    }
</script>

</body>
</html>
