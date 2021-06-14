package ru.skillbox.team13;

import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.entity.enums.UserType;

import java.time.LocalDateTime;

public class DomainObjectFactory {

    public static User makeUser(String email) {
        User user = new User();
        user.setName("names are unsupported now");
        user.setPassword("password");
        user.setType(UserType.USER);
        user.setEmail(email);
        user.setApproved(true);
        return user;
    }

    public static Person makePerson(String fName, String lName, String email) {
        Person person = new Person();
        person.setFirstName(fName);
        person.setLastName(lName);
        person.setRegDate(LocalDateTime.now());
        person.setEmail(email);
        person.setMessagesPermission(PersonMessagePermission.ALL);
        person.setBlocked(false);
        return person;
    }

    public static Post makePost(String title, String text) {
        Post post = new Post();
        post.setTime(LocalDateTime.now());
        post.setTitle(title);
        post.setPostText(text);
        post.setBlocked(false);
        return post;
    }

    public static Comment makeComment(String text) {
        Comment comment = new Comment();
        comment.setTime(LocalDateTime.now());
        comment.setCommentText(text);
        comment.setBlocked(false);
        return comment;
    }
}