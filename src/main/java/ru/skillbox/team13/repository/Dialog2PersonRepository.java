package ru.skillbox.team13.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Dialog2Person;
import ru.skillbox.team13.entity.Person;

import java.util.ArrayList;
import java.util.List;

public interface Dialog2PersonRepository extends JpaRepository<Dialog2Person, Integer> {

    @Query("select d2p from Dialog2Person d2p where d2p.person.id = :currentPersonId")
    Page<Dialog2Person> findPersonDialogs(Pageable pageable, int currentPersonId);

    List<Dialog2Person> findDialog2PersonByDialog(Dialog dialog);

    @Modifying
    @Query("delete from Dialog2Person d2p where d2p.dialog.id = :dialogId")
    void deleteAllByDialog(int dialogId);

    @Query("select sum(d2p.unreadCount) from Dialog2Person d2p where d2p.person.id = :personId")
    Integer countAllUnread(int personId);

    List<Dialog2Person> findAllByDialogIdAndPersonIdIsIn(int dialogId, ArrayList<Integer> personIds);

    Dialog2Person findByDialogAndPerson(Dialog dialog, Person person);

    void deleteAllByDialogIdAndPersonIdIsIn(int dialogId, ArrayList<Integer> personIds);
 }
