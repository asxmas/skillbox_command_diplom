package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.Message;

public class RepoMessage implements Repo<Message> {

  @Override
  public void addToRepo(Message message) {

  }

  @Override
  public void removeFromRepo(Message message) {

  }

  @Override
  public List<Message> findAll() {
    return null;
  }

  @Override
  public List<Message> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Message> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Message> findAllById(Iterable<Integer> iterable) {
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

  @Override
  public <S extends Message> S save(S s) {
    return null;
  }

  @Override
  public <S extends Message> List<S> saveAll(Iterable<S> iterable) {
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
  public void flush() {

  }

  @Override
  public <S extends Message> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends Message> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Message> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Message getOne(Integer integer) {
    return null;
  }

  @Override
  public Message getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends Message> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Message> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Message> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Message> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Message> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Message> boolean exists(Example<S> example) {
    return false;
  }
}
