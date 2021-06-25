package ru.skillbox.team13.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Dialog2person;
import ru.skillbox.team13.entity.Person;

public interface Dialog2PersonRepository extends JpaRepository<Dialog2person, Integer> {

    @Query("select d2p.dialog from Dialog2person d2p left join d2p.dialog.messages m " +
           "where lower(m.messageText) like %:query% and d2p.person = :currentUser")
    Page<Dialog> findUserDialogs(Pageable pageable, String query, Person currentUser);
}
