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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
@WebServlet("/Edit")
public class Edit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Step 1: Load note details
        int id = Integer.parseInt(request.getParameter("id"));
        String act=request.getParameter("action");
        if("delete".equalsIgnoreCase(act)){
            try{
               Connection con=DBConnection.getConnection();
               PreparedStatement ps=con.prepareStatement("DELETE FROM notes WHERE note_id=?");
               ps.setInt(1,id);
               int c=ps.executeUpdate();
               if(c>0){
                    response.sendRedirect("ViewServlet?msg=deleted");
               }
            }catch(SQLException e){
            e.printStackTrace();
            }
        }else{
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT title, content FROM notes WHERE note_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                request.setAttribute("noteId", id);
                request.setAttribute("title", rs.getString("title"));
                request.setAttribute("content", rs.getString("content"));
                request.setAttribute("status", "edit"); // mode flag
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("CreateNotes.jsp").forward(request, response);
    }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Step 2: Update note details
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE notes SET title=?, content=? WHERE note_id=?");
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        response.sendRedirect("ViewServlet?msg=updated");
    }
}

