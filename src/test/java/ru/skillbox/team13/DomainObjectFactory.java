package ru.skillbox.team13;

import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.entity.enums.UserType;

import java.time.LocalDateTime;
import java.util.Random;

public class DomainObjectFactory {
    private static final char A = 'A';
    private static final char a = 'a';
    private static final int CHARACTER_COUNT = 25;
    private static final Random RANDOM = new Random();

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

    public static Like makeLike(Person liker, Notified postOrComment) {
        Like like = new Like();
        like.setTime(LocalDateTime.now());
        like.setPerson(liker);
        like.setPostOrComment(postOrComment);
        return like;
    }

    public static String genString(int length, float whiteSpaceFreq, boolean addComma) {
        if (length < 1) throw new RuntimeException("Text should be 1+ character long");
        StringBuilder sb = new StringBuilder(length);
        sb.append(getRandomChar(true));
        for (int i = 0; i < length - 1; i++) {
            if (RANDOM.nextFloat() < whiteSpaceFreq) {
                sb.append(" ");
            } else sb.append(getRandomChar(false));
        }
        if (addComma) {
            sb.deleteCharAt(length - 1);
            sb.append(".");
        }
        return sb.toString();
    }

    private static char getRandomChar(boolean isCapital) {
        return (char) (RANDOM.nextInt(CHARACTER_COUNT) + (isCapital ? A : a));
    }
}