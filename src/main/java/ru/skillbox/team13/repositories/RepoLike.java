package ru.skillbox.team13.repositories;

import java.util.Optional;
import ru.skillbox.team13.entity.Like;

public class RepoLike extends Repo<Like> {

  @Override
  public <S extends Like> S save(S s) {
    return null;
  }

  @Override
  public <S extends Like> Iterable<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Like> findById(Integer integer) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Integer integer) {
    return false;
  }

  @Override
  public Iterable<Like> findAll() {
    return null;
  }

  @Override
  public Iterable<Like> findAllById(Iterable<Integer> iterable) {
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
  public void delete(Like like) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends Like> iterable) {

  }

  @Override
  public void deleteAll() {

  }
}
