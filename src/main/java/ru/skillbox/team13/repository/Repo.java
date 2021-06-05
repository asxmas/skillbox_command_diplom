package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo<T> extends JpaRepository<T, Integer> {
  public void addToRepo(T t);
  public void removeFromRepo(T t);
}
