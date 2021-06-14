package ru.skillbox.team13.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.repository.PersonRepo;

import java.util.Set;

@Service
@Setter
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo personRepo;
    private Person person;

    public boolean updatePerson ()   {
        try {
            Person updatePerson = personRepo.getById(person.getId());
            updatePerson.setFirstName(person.getFirstName());
            updatePerson.setLastName(person.getLastName());
            updatePerson.setBirthDate(person.getBirthDate());
            updatePerson.setRegDate(person.getRegDate());
            updatePerson.setEmail(person.getEmail());
            updatePerson.setPhone(person.getPhone());
            updatePerson.setPhoto(person.getPhoto());
            updatePerson.setAbout(person.getAbout());
            updatePerson.setCity(person.getCity());
            updatePerson.setMessagesPermission(person.getMessagesPermission());
            updatePerson.setLastOnlineTime(person.getLastOnlineTime());
            updatePerson.setBlocked(person.isBlocked());
            updatePerson.setFriendshipsReceived(person.getFriendshipsReceived());
            updatePerson.setFriendshipsRequested(person.getFriendshipsRequested());
            updatePerson.setMessagesReceived(person.getMessagesReceived());
            updatePerson.setMessagesSent(person.getMessagesSent());
            updatePerson.setPosts(person.getPosts());
            updatePerson.setComments(person.getComments());
            updatePerson.setLikes(person.getLikes());
            updatePerson.setBlocks(person.getBlocks());
            return true;
        } catch (Exception ex)  {
            ex.printStackTrace();
            return false;
        }
    }

    public void addPostToWall (Post post)   {
        post.setAuthor(person);
        Set<Post> personPosts = person.getPosts();
        personPosts.add(post);
        person.setPosts(personPosts);
    }
}
