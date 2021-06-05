package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.Friendship;

public class RepoFriendship implements Repo<Friendship> {

  @Override
  public void addToRepo(Friendship friendship) {

  }

  @Override
  public void removeFromRepo(Friendship friendship) {

  }

  @Override
  public List<Friendship> findAll() {
    return null;
  }

  @Override
  public List<Friendship> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Friendship> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Friendship> findAllById(Iterable<Integer> iterable) {
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
  public void delete(Friendship friendship) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends Friendship> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Friendship> S save(S s) {
    return null;
  }

  @Override
  public <S extends Friendship> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Friendship> findById(Integer integer) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Integer integer) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Friendship> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends Friendship> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Friendship> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Friendship getOne(Integer integer) {
    return null;
  }

  @Override
  public Friendship getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends Friendship> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Friendship> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Friendship> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Friendship> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Friendship> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Friendship> boolean exists(Example<S> example) {
    return false;
  }
}
