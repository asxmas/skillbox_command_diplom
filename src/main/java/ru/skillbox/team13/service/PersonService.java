package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.repository.PersonRepo;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo personRepo;

    public boolean updatePerson (Person updatePerson)   {
        try {
            Person person = personRepo.getById(updatePerson.getId());
            person.setFirstName(updatePerson.getFirstName());
            person.setLastName(updatePerson.getLastName());
            person.setBirthDate(updatePerson.getBirthDate());
            person.setRegDate(updatePerson.getRegDate());
            person.setEmail(updatePerson.getEmail());
            person.setPhone(updatePerson.getPhone());
            person.setPhoto(updatePerson.getPhoto());
            person.setAbout(updatePerson.getAbout());
            person.setCity(updatePerson.getCity());
            person.setMessagesPermission(updatePerson.getMessagesPermission());
            person.setLastOnlineTime(updatePerson.getLastOnlineTime());
            person.setIsBlocked(updatePerson.getIsBlocked());
            person.setFriendshipsReceived(updatePerson.getFriendshipsReceived());
            person.setFriendshipsRequested(updatePerson.getFriendshipsRequested());
            person.setMessagesReceived(updatePerson.getMessagesReceived());
            person.setMessagesSent(updatePerson.getMessagesSent());
            person.setPosts(updatePerson.getPosts());
            person.setComments(updatePerson.getComments());
            person.setLikes(updatePerson.getLikes());
            person.setBlocks(updatePerson.getBlocks());
            return true;
        } catch (Exception ex)  {
            ex.printStackTrace();
            return false;
        }
    }
}
