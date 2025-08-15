package Notes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "Download", urlPatterns = {"/Download"})
public class Download extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Note ID is required.");
            return;
        }

        int id = Integer.parseInt(idParam);

        String title = "";
        String content = "";
        String createdAt = "-";
        String updatedAt = "-";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        // Fetch note from DB
        try (Connection con = DBConnection.getConnection();
             PreparedStatement prep = con.prepareStatement("SELECT * FROM notes WHERE note_id=?")) {

            prep.setInt(1, id);
            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    title = rs.getString("title");
                    content = rs.getString("content");

                    Timestamp cts = rs.getTimestamp("created_at");
                    Timestamp uts = rs.getTimestamp("updated_at");
                    if (cts != null) createdAt = cts.toLocalDateTime().format(formatter);
                    if (uts != null) updatedAt = uts.toLocalDateTime().format(formatter);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Note not found.");
                    return;
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Database error while fetching note.", e);
        }

        // PDF settings
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Note_" + id + ".pdf");

        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLUE);
            Paragraph titlePara = new Paragraph(title, titleFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            titlePara.setSpacingAfter(20);
            document.add(titlePara);

            // Created/Updated dates table
            PdfPTable metaTable = new PdfPTable(2);
            metaTable.setWidthPercentage(100);
            metaTable.setSpacingAfter(20);

            Font metaFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);

            PdfPCell createdCell = new PdfPCell(new Phrase("Created: " + createdAt, metaFont));
            createdCell.setBorder(Rectangle.NO_BORDER);
            PdfPCell updatedCell = new PdfPCell(new Phrase("Updated: " + updatedAt, metaFont));
            updatedCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            updatedCell.setBorder(Rectangle.NO_BORDER);

            metaTable.addCell(createdCell);
            metaTable.addCell(updatedCell);
            document.add(metaTable);

            // Content
            Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL, BaseColor.BLACK);
            Paragraph contentPara = new Paragraph(content, contentFont);
            contentPara.setSpacingBefore(10);
            contentPara.setFirstLineIndent(20);
            document.add(contentPara);

            document.close();

        } catch (DocumentException e) {
            throw new IOException("Error generating PDF", e);
        }
    }
}
