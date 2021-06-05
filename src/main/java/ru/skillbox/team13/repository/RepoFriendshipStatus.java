package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.FriendshipStatus;

public class RepoFriendshipStatus implements Repo<FriendshipStatus> {

  @Override
  public void addToRepo(FriendshipStatus friendshipStatus) {

  }

  @Override
  public void removeFromRepo(FriendshipStatus friendshipStatus) {

  }

  @Override
  public List<FriendshipStatus> findAll() {
    return null;
  }

  @Override
  public List<FriendshipStatus> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<FriendshipStatus> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<FriendshipStatus> findAllById(Iterable<Integer> iterable) {
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
  public void delete(FriendshipStatus friendshipStatus) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends FriendshipStatus> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends FriendshipStatus> S save(S s) {
    return null;
  }

  @Override
  public <S extends FriendshipStatus> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<FriendshipStatus> findById(Integer integer) {
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
  public <S extends FriendshipStatus> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends FriendshipStatus> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<FriendshipStatus> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public FriendshipStatus getOne(Integer integer) {
    return null;
  }

  @Override
  public FriendshipStatus getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends FriendshipStatus> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends FriendshipStatus> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends FriendshipStatus> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends FriendshipStatus> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends FriendshipStatus> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends FriendshipStatus> boolean exists(Example<S> example) {
    return false;
  }
}
