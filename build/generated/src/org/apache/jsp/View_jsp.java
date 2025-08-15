package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;
import Notes.Bean;

public final class View_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(2);
    _jspx_dependants.add("/Header.jsp");
    _jspx_dependants.add("/Footer.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
      out.write('\n');
      out.write("<!-- footer.jsp -->\n");
      out.write("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    <title>Your Notes</title>\n");
      out.write("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("    <style>\n");
      out.write("        body {\n");
      out.write("            background-color: #f8f9fa;\n");
      out.write("        }\n");
      out.write("        .navbar {\n");
      out.write("            margin-bottom: 20px;\n");
      out.write("        }\n");
      out.write("        .card {\n");
      out.write("            border-radius: 15px;\n");
      out.write("            transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;\n");
      out.write("        }\n");
      out.write("        .card:hover {\n");
      out.write("            transform: translateY(-5px);\n");
      out.write("            box-shadow: 0 6px 20px rgba(0,0,0,0.1);\n");
      out.write("        }\n");
      out.write("        .card-title {\n");
      out.write("            font-weight: bold;\n");
      out.write("            color: #2c3e50;\n");
      out.write("        }\n");
      out.write("        .card-text {\n");
      out.write("            color: #555;\n");
      out.write("        }\n");
      out.write("        .note-meta {\n");
      out.write("            font-size: 0.85rem;\n");
      out.write("            color: #888;\n");
      out.write("            margin-top: 8px;\n");
      out.write("        }\n");
      out.write("        .search-box {\n");
      out.write("            max-width: 400px;\n");
      out.write("            margin: 0 auto 30px auto;\n");
      out.write("        }\n");
      out.write("    </style>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("<nav class=\"navbar navbar-expand-lg navbar-light bg-white shadow-sm\">\n");
      out.write("    <div class=\"container-fluid\">\n");
      out.write("        <a class=\"navbar-brand fw-bold text-primary\" href=\"Home.jsp\">NoteApp</a>\n");
      out.write("        <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n");
      out.write("            <ul class=\"navbar-nav ms-auto\">\n");
      out.write("                <li class=\"nav-item\">\n");
      out.write("                    <a class=\"nav-link active\" href=\"Home.jsp\">Home</a>\n");
      out.write("                </li>\n");
      out.write("                <li class=\"nav-item\">\n");
      out.write("                    <a class=\"nav-link text-danger\" href=\"Logout\">Logout</a>\n");
      out.write("                </li>\n");
      out.write("            </ul>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("</nav>\n");
      out.write("\n");
      out.write("<div class=\"container py-4\">\n");
      out.write("    <h2 class=\"mb-4 text-center text-primary\">Your Notes</h2>\n");
      out.write("\n");
      out.write("    <!-- Search Bar -->\n");
      out.write("    <div class=\"search-box\">\n");
      out.write("        <input type=\"text\" id=\"searchInput\" class=\"form-control\" placeholder=\"Search notes...\">\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <div class=\"row g-4\" id=\"notesContainer\">\n");
      out.write("        ");

            List<Bean> notes = (List<Bean>) request.getAttribute("list");
            if (notes != null && !notes.isEmpty()) {
                for (Bean n : notes) {
                    String title = n.getTitle();
                    String content = n.getContent();
                    LocalDateTime c = n.getCreatedAt();
                    LocalDateTime u = n.getUpdatedAt();
        
      out.write("\n");
      out.write("        <div class=\"col-md-4 col-lg-3 note-card\">\n");
      out.write("            <div class=\"card h-100 shadow-sm\">\n");
      out.write("                <div class=\"card-body d-flex flex-column\">\n");
      out.write("                    <h5 class=\"card-title\">");
      out.print( title );
      out.write("</h5>\n");
      out.write("                    <p class=\"card-text\">");
      out.print( content );
      out.write("</p>\n");
      out.write("                    <div class=\"note-meta mt-auto\">\n");
      out.write("                        <p class=\"mb-1\">📅 Created: ");
      out.print( c.format(formatter) );
      out.write("</p>\n");
      out.write("                        <p class=\"mb-3\">✏ Updated: ");
      out.print( u.format(formatter) );
      out.write("</p>\n");
      out.write("                        <!-- Edit Button -->\n");
      out.write("                        <a href=\"EditServlet?id=");
      out.print( n.getTitle().hashCode() );
      out.write("\" class=\"btn btn-sm btn-outline-primary w-100\">Edit</a>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        ");

                }
            } else {
        
      out.write("\n");
      out.write("        <div class=\"col-12\">\n");
      out.write("            <div class=\"alert alert-warning text-center shadow-sm\">No notes available to display.</div>\n");
      out.write("        </div>\n");
      out.write("        ");

            }
        
      out.write("\n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<script>\n");
      out.write("    // Search functionality\n");
      out.write("    document.getElementById(\"searchInput\").addEventListener(\"keyup\", function() {\n");
      out.write("        let filter = this.value.toLowerCase();\n");
      out.write("        let cards = document.getElementsByClassName(\"note-card\");\n");
      out.write("        for (let i = 0; i < cards.length; i++) {\n");
      out.write("            let text = cards[i].innerText.toLowerCase();\n");
      out.write("            cards[i].style.display = text.includes(filter) ? \"\" : \"none\";\n");
      out.write("        }\n");
      out.write("    });\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
