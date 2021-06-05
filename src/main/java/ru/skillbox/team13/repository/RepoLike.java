package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.Like;

public class RepoLike implements Repo<Like> {

  @Override
  public void addToRepo(Like like) {

  }

  @Override
  public void removeFromRepo(Like like) {

  }

  @Override
  public List<Like> findAll() {
    return null;
  }

  @Override
  public List<Like> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Like> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Like> findAllById(Iterable<Integer> iterable) {
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

  @Override
  public <S extends Like> S save(S s) {
    return null;
  }

  @Override
  public <S extends Like> List<S> saveAll(Iterable<S> iterable) {
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
  public void flush() {

  }

  @Override
  public <S extends Like> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends Like> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Like> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Like getOne(Integer integer) {
    return null;
  }

  @Override
  public Like getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends Like> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Like> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Like> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Like> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Like> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Like> boolean exists(Example<S> example) {
    return false;
  }
}
