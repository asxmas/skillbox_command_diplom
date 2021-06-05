package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.NotificationType;

public class RepoNotificationType implements Repo<NotificationType> {

  @Override
  public void addToRepo(NotificationType notificationType) {

  }

  @Override
  public void removeFromRepo(NotificationType notificationType) {

  }

  @Override
  public List<NotificationType> findAll() {
    return null;
  }

  @Override
  public List<NotificationType> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<NotificationType> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<NotificationType> findAllById(Iterable<Integer> iterable) {
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
  public void delete(NotificationType notificationType) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends NotificationType> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends NotificationType> S save(S s) {
    return null;
  }

  @Override
  public <S extends NotificationType> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<NotificationType> findById(Integer integer) {
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
  public <S extends NotificationType> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends NotificationType> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<NotificationType> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public NotificationType getOne(Integer integer) {
    return null;
  }

  @Override
  public NotificationType getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends NotificationType> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends NotificationType> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends NotificationType> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends NotificationType> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends NotificationType> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends NotificationType> boolean exists(Example<S> example) {
    return false;
  }
}
