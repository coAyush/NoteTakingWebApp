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
import java.util.List;
import Notes.DBConnection;
import Notes.Bean;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet(name = "ViewServlet", urlPatterns = {"/ViewServlet"})
public class ViewServlet extends HttpServlet {

    String query = "SELECT * FROM notes WHERE user_id = ?";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses = request.getSession(false);
        int id = (int) ses.getAttribute("ID");
        List<Bean> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement prep = con.prepareStatement(query);
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                Bean b = new Bean(rs.getInt("note_id"), rs.getString("title"), rs.getString("content"), rs.getTimestamp("created_at").toLocalDateTime(), rs.getTimestamp("updated_at").toLocalDateTime());
                list.add(b);
            }
            ses.setAttribute("Notes", list);
            request.setAttribute("list", list);
            String msg = request.getParameter("msg");
            if ("updated".equalsIgnoreCase(msg)) {
                request.setAttribute("msg", "updated");
            }
            request.getRequestDispatcher("View.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ViewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
