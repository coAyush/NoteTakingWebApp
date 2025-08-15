/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Notes.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger; 

/**
 *
 * @author User
 */
import javax.servlet.http.HttpSession;
@WebServlet(name = "CreateNotes", urlPatterns = {"/CreateNotes"})
public class CreateNotes extends HttpServlet {

    String query = "INSERT INTO notes(user_id, title, content) VALUES(?,?,?)";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("==== CreateNotes servlet called ====");

        HttpSession ses = request.getSession(false);
        if (ses == null) {
            System.out.println("Session is null — redirecting to login");
            response.sendRedirect("Login.jsp?error=PleaseLogin");
            return;
        }

        Object idObj = ses.getAttribute("ID");
        if (idObj == null) {
            System.out.println("Session ID is null — redirecting to login");
            response.sendRedirect("Login.jsp?error=PleaseLogin");
            return;
        }

        int id = (Integer) idObj;

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        System.out.println("User ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, id);
            prep.setString(2, title);
            prep.setString(3, content);

            int rows = prep.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("CreateNotes.jsp?status=added");
            } else {
                System.out.println("Note creation failed");
                response.sendRedirect("CreateNotes.jsp?status=failed");
            }

        } catch (SQLException ex) {
             ex.printStackTrace();
    throw new ServletException("SQL Error: " + ex.getMessage(), ex);
        }
    }
}
