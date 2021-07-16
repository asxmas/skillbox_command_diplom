package ru.skillbox.team13.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Dialog2Person;
import ru.skillbox.team13.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Modifying
    @Query("delete from Message m where m.dialog.id = :dialogId")
    void deleteMessageByDialogId(int dialogId);

    @Query("select m from Message m where m.dialog.id = :dialogId and m.id <= :fromMessageId")
    Page<Message> findByDialog(Pageable pageable, int fromMessageId, int dialogId);

    Message findFirstByOrderByIdDesc();
}
