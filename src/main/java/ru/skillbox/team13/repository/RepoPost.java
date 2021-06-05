package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.Post;

public class RepoPost implements Repo<Post> {

  @Override
  public void addToRepo(Post post) {

  }

  @Override
  public void removeFromRepo(Post post) {

  }

  @Override
  public List<Post> findAll() {
    return null;
  }

  @Override
  public List<Post> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Post> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Post> findAllById(Iterable<Integer> iterable) {
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
  public void delete(Post post) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends Post> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Post> S save(S s) {
    return null;
  }

  @Override
  public <S extends Post> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Post> findById(Integer integer) {
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
  public <S extends Post> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends Post> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Post> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Post getOne(Integer integer) {
    return null;
  }

  @Override
  public Post getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends Post> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Post> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Post> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Post> boolean exists(Example<S> example) {
    return false;
  }
}
