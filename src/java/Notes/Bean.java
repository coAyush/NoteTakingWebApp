/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;
import java.time.LocalDateTime;
/**
 *
 * @author User
 */
public class Bean {
    int noteId;
    String title,content;
    LocalDateTime createdAt,updatedAt;

    public Bean(int noteId,String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.noteId=noteId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
}
