package ru.skillbox.team13.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Dialog2Person;
import ru.skillbox.team13.entity.Message;
import ru.skillbox.team13.entity.enums.MessageReadStatus;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Modifying
    @Query("delete from Message m where m.dialog.id = :dialogId")
    void deleteMessageByDialogId(int dialogId);

    @Query("select m from Message m where m.dialog.id = :dialogId and lower(m.messageText) like %:query%")
    Page<Message> findByDialog(Pageable pageable, String query, int dialogId);

    @Modifying
    @Transactional
    @Query("update Message m set m.readStatus = :status where m.id = :msgId")
    void setStatusForId(MessageReadStatus status, int msgId);
}
