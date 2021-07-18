package ru.skillbox.team13.test_util;

import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.entity.enums.UserType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DomainObjectFactory {
    private static final char A = 'A';
    private static final char a = 'a';
    private static final int CHARACTER_COUNT = 26;
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

    public static User makeUser(String email, Person person) {
        User user = makeUser(email);
        user.setPerson(person);
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

    public static Person makePerson(String email) {
        return makePerson(genString(5), genString(10), email);
    }

    public static Person makePerson() {
        return makePerson(genString(5), genString(10), genString(20));
    }

    public static Post makePost(String title, String text) {
        Post post = new Post();
        post.setTime(LocalDateTime.now());
        post.setTitle(title);
        post.setPostText(text);
        post.setBlocked(false);
        return post;
    }

    public static Post makePost(String title, String text, Person author) {
        Post p = makePost(title, text);
        p.setAuthor(author);
        return p;
    }

    public static Post makePost() {
        return makePost(genString(50, 0.1f, false), genString(200, 0.15f, true));
    }

    public static Post makePost(Person author) {
        Post p = makePost();
        p.setAuthor(author);
        return p;
    }


    public static Comment makeComment(String text) {
        Comment comment = new Comment();
        comment.setTime(LocalDateTime.now());
        comment.setCommentText(text);
        comment.setBlocked(false);
        return comment;
    }

    public static Comment makeComment(String text, Person author, Post post) {
        Comment comment = makeComment(text);
        comment.setAuthor(author);
        comment.setPost(post);
        return comment;
    }

    public static Comment makeComment(Person author, Post post) {
        Comment comment = makeComment(genString(50, 0.2f, true));
        comment.setAuthor(author);
        comment.setPost(post);
        return comment;
    }


    public static Like makeLike(Person liker, Notified notified) {
        if (notified instanceof Comment) {
            return likeComment(liker, (Comment) notified);
        } else if (notified instanceof Post) {
            return likePost(liker, (Post) notified);
        } else throw new RuntimeException();
    }

    public static Like likePost(Person liker, Post post) {
        Like like = new Like();
        like.setTime(LocalDateTime.now());
        like.setPerson(liker);
        like.setPost(post);
        return like;
    }

    public static Like likeComment(Person liker, Comment comment) {
        Like like = new Like();
        like.setTime(LocalDateTime.now());
        like.setPerson(liker);
        like.setComment(comment);
        return like;
    }

    public static List<Like> makeLike(Person liker, Notified postOrComment, int howMany) {
        return IntStream.range(0, howMany)
                .mapToObj(i -> makeLike(liker, postOrComment)).collect(Collectors.toList());
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


    public static String genString(int length) {
        return genString(length, 0f, false);
    }

    private static char getRandomChar(boolean isCapital) {
        return (char) (RANDOM.nextInt(CHARACTER_COUNT) + (isCapital ? A : a));
    }
}