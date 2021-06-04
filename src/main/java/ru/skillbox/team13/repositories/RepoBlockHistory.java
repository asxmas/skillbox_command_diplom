package ru.skillbox.team13.repositories;

import java.util.Optional;
import ru.skillbox.team13.entity.BlockHistory;

public class RepoBlockHistory extends Repo<BlockHistory> {

  @Override
  public <S extends BlockHistory> S save(S s) {
    return null;
  }

  @Override
  public <S extends BlockHistory> Iterable<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<BlockHistory> findById(Integer integer) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Integer integer) {
    return false;
  }

  @Override
  public Iterable<BlockHistory> findAll() {
    return null;
  }

  @Override
  public Iterable<BlockHistory> findAllById(Iterable<Integer> iterable) {
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
  public void delete(BlockHistory blockHistory) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends BlockHistory> iterable) {

  }

  @Override
  public void deleteAll() {

  }
}
