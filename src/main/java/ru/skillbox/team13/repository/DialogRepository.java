package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Dialog;

public interface DialogRepository extends JpaRepository<Dialog, Integer> {

   // void deleteDialogById(int dialogId);
}
