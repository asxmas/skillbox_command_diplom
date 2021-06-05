package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.Comment;

public class RepoComment implements Repo<Comment> {

  @Override
  public void addToRepo(Comment comment) {

  }

  @Override
  public void removeFromRepo(Comment comment) {

  }

  @Override
  public List<Comment> findAll() {
    return null;
  }

  @Override
  public List<Comment> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Comment> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Comment> findAllById(Iterable<Integer> iterable) {
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
  public void delete(Comment comment) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends Comment> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Comment> S save(S s) {
    return null;
  }

  @Override
  public <S extends Comment> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Comment> findById(Integer integer) {
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
  public <S extends Comment> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends Comment> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Comment> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Comment getOne(Integer integer) {
    return null;
  }

  @Override
  public Comment getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends Comment> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Comment> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Comment> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Comment> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Comment> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Comment> boolean exists(Example<S> example) {
    return false;
  }
}
