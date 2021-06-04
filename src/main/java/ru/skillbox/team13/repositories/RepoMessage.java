package ru.skillbox.team13.repositories;

import java.util.Optional;
import ru.skillbox.team13.entity.Message;

public class RepoMessage extends Repo<Message> {

  @Override
  public <S extends Message> S save(S s) {
    return null;
  }

  @Override
  public <S extends Message> Iterable<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Message> findById(Integer integer) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Integer integer) {
    return false;
  }

  @Override
  public Iterable<Message> findAll() {
    return null;
  }

  @Override
  public Iterable<Message> findAllById(Iterable<Integer> iterable) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Integer integer) {

  }

  @Override
  public void delete(Message message) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends Message> iterable) {

  }

  @Override
  public void deleteAll() {

  }
}
