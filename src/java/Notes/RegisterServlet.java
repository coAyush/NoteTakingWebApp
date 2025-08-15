package Notes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.http.HttpSession;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    final String url = "jdbc:mysql://localhost:3306/Notes?autoReconnect=true&useSSL=false";
    final String username = "root";
    final String password = "ayush52141";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");
        String p = request.getParameter("newPass");
        String action = request.getParameter("pas");

        HttpSession session = request.getSession();
        int id = (session != null && session.getAttribute("ID") != null) ? (Integer) session.getAttribute("ID") : 0;
        session.setMaxInactiveInterval(3600);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully.");
            try (Connection con = DriverManager.getConnection(url, username, password)) {

                // Check if user already exists by name only (optional)
                if ("changed".equalsIgnoreCase(action)) {
                    String UpdateQuery = "UPDATE  Customers SET password=? WHERE Id=?";
                    PreparedStatement change = con.prepareStatement(UpdateQuery);
                    change.setString(1, p);
                    change.setInt(2, id);
                    int up = change.executeUpdate();
                    if (up > 0) {
                        response.sendRedirect("forgotPassword.jsp?status=passwordchanged");
                    } else {
                        response.sendRedirect("forgotPassword.jsp?status=updatefailed");
                    }

                } else {
                    String checkQuery = "SELECT * FROM Customers WHERE Name = ? AND email =?";
                    PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                    checkStmt.setString(1, name);
                    checkStmt.setString(2, email);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        System.out.println("User already exists.");
                        response.setContentType("text/html");
                        PrintWriter out = response.getWriter();
                        session.setAttribute("name", name);
                        session.setAttribute("email", email);

                        out.println("<script type='text/javascript'>");
                        out.println("alert('User already registered. Please login.');");
                        out.println("location='Login.jsp';");
                        out.println("</script>");
                        return;
                    }

                    PrintWriter out = response.getWriter();
                    String insertQuery = "INSERT INTO Customers(Name, password,email) VALUES (?, ?,?)";
                    PreparedStatement prep = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    prep.setString(1, name);
                    prep.setString(2, pass);
                    prep.setString(3, email);
                    int res = prep.executeUpdate();

                    if (res > 0) {
                        ResultSet keys = prep.getGeneratedKeys();
                        if (keys.next()) {
                            id = keys.getInt(1); // get auto-incremented ID
                        }
                        session.setAttribute("name", name);
                        session.setAttribute("pass", pass);
                        session.setAttribute("email", email);
                        session.setAttribute("ID", id);
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Registration successful. Redirecting to login page...');");
                        out.println("window.location.href = 'Login.jsp';");
                        out.println("</script>");
                    } else {
                        System.out.println("User registration failed.");
                        response.setContentType("text/html");
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Registration failed.');");
                        out.println("location='Register.html';");
                        out.println("</script>");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // prints full stack trace in server logs (NetBeans Output tab)
            response.setContentType("text/plain");
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
