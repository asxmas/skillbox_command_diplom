package ru.skillbox.team13.repositories;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class Repo<T> implements
    CrudRepository<T, Integer> {
  private List<T> repo = new ArrayList<>();

  public List<T> getRepo()  {
    return repo;
  }

  public void addToRepo(T t)  {
    repo.add(t);
  }

  @Override
  public <S extends T> S save(S s) {
    return null;
  }

  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<T> findById(Integer integer) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Integer integer) {
    return false;
  }

  @Override
  public Iterable<T> findAll() {
    return null;
  }

  @Override
  public Iterable<T> findAllById(Iterable<Integer> iterable) {
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
  public void delete(T t) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends T> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  public void removeFromRepo(int id)  {
    try {
      repo.remove(id);
    }
    catch (ArrayIndexOutOfBoundsException ex) {
      ex.printStackTrace();
    }
  }
}
