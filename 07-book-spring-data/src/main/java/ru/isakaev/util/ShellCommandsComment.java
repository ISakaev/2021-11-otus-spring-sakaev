package ru.isakaev.util;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.isakaev.model.Author;
import ru.isakaev.model.Comment;
import ru.isakaev.service.CommentService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandsComment {

    private final CommentService commentService;

    @ShellMethod(value = "Get all comments", key = {"gcs", "getComments"})
    public void getComments(){
        commentService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get comment by id", key = {"gc", "getComment"})
    public String getComment(@ShellOption Integer id){
        Comment comment = commentService.getComment(id);
        if(comment != null){
            return comment.toString();
        }
        return "Comment with id " + id + " was not found";
    }

    @ShellMethod(value = "Save comment", key = {"sc", "saveComment"})
    public String saveComment(@ShellOption String text){
        Comment comment = commentService.saveComment(text);
        return "Author " + comment.toString() + " was saved";
    }

    @ShellMethod(value = "Get comment by contain text", key = {"gcc", "getCommentContainContext"})
    public void getCommentContainContext(@ShellOption String text){
        List<Comment> comments = commentService.findByTextContains(text);
        if (comments.isEmpty()){
            System.out.println("Comment with text contain - " + text + " was not found");
        }else {
            comments.forEach(System.out::println);
        }
    }

    @ShellMethod(value = "Delete comment by id", key = {"dc", "deleteComment"})
    public String deleteComment(@ShellOption Integer id){
        commentService.deleteComment(id);
        return "Comment with id - " + id + " was deleted";
    }
}
