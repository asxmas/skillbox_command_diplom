package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.Tag2Post;

public class RepoTag2Post implements Repo<Tag2Post> {

  @Override
  public void addToRepo(Tag2Post tag2Post) {

  }

  @Override
  public void removeFromRepo(Tag2Post tag2Post) {

  }

  @Override
  public List<Tag2Post> findAll() {
    return null;
  }

  @Override
  public List<Tag2Post> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Tag2Post> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Tag2Post> findAllById(Iterable<Integer> iterable) {
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
  public void delete(Tag2Post tag2Post) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends Tag2Post> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Tag2Post> S save(S s) {
    return null;
  }

  @Override
  public <S extends Tag2Post> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Tag2Post> findById(Integer integer) {
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
  public <S extends Tag2Post> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends Tag2Post> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Tag2Post> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Tag2Post getOne(Integer integer) {
    return null;
  }

  @Override
  public Tag2Post getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends Tag2Post> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Tag2Post> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Tag2Post> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Tag2Post> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Tag2Post> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Tag2Post> boolean exists(Example<S> example) {
    return false;
  }
}
