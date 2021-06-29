package ru.skillbox.team13.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Dialog2Person;

import java.util.List;

public interface Dialog2PersonRepository extends JpaRepository<Dialog2Person, Integer> {

    @Query("select d2p from Dialog2Person d2p left join d2p.dialog.messages m " +
           "where lower(m.messageText) like %:query% and d2p.person.id = :currentPersonId")
    Page<Dialog2Person> findPersonDialogs(Pageable pageable, String query, int currentPersonId);

    List<Dialog2Person> findDialog2PersonByDialog(Dialog dialog);

    @Query("select sum(d2p.unreadCount) from Dialog2Person d2p where d2p.person = :personId")
    int countAllUnread(int personId);
}
