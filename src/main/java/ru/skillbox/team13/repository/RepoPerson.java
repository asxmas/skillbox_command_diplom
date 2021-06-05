package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.Person;

public class RepoPerson implements Repo<Person> {

  @Override
  public void addToRepo(Person person) {

  }

  @Override
  public void removeFromRepo(Person person) {

  }

  @Override
  public List<Person> findAll() {
    return null;
  }

  @Override
  public List<Person> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Person> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Person> findAllById(Iterable<Integer> iterable) {
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
  public void delete(Person person) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends Person> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Person> S save(S s) {
    return null;
  }

  @Override
  public <S extends Person> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Person> findById(Integer integer) {
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
  public <S extends Person> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends Person> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Person> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Person getOne(Integer integer) {
    return null;
  }

  @Override
  public Person getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends Person> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Person> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Person> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Person> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Person> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Person> boolean exists(Example<S> example) {
    return false;
  }
}
