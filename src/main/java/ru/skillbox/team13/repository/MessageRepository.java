package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Modifying
    @Query("delete from Message m where m.dialog.id = :dialogId")
    void deleteMessageByDialogId(int dialogId);
}
