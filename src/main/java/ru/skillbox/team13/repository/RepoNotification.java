package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.Notification;

public class RepoNotification implements Repo<Notification> {

  @Override
  public void addToRepo(Notification notification) {

  }

  @Override
  public void removeFromRepo(Notification notification) {

  }

  @Override
  public List<Notification> findAll() {
    return null;
  }

  @Override
  public List<Notification> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Notification> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Notification> findAllById(Iterable<Integer> iterable) {
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
  public void delete(Notification notification) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends Notification> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Notification> S save(S s) {
    return null;
  }

  @Override
  public <S extends Notification> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Notification> findById(Integer integer) {
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
  public <S extends Notification> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends Notification> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Notification> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Notification getOne(Integer integer) {
    return null;
  }

  @Override
  public Notification getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends Notification> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Notification> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Notification> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Notification> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Notification> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Notification> boolean exists(Example<S> example) {
    return false;
  }
}
