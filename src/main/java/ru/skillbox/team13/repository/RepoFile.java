package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.File;

public class RepoFile implements Repo<File> {

  @Override
  public void addToRepo(File file) {

  }

  @Override
  public void removeFromRepo(File file) {

  }

  @Override
  public List<File> findAll() {
    return null;
  }

  @Override
  public List<File> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<File> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<File> findAllById(Iterable<Integer> iterable) {
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
  public void delete(File file) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends File> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends File> S save(S s) {
    return null;
  }

  @Override
  public <S extends File> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<File> findById(Integer integer) {
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
  public <S extends File> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends File> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<File> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public File getOne(Integer integer) {
    return null;
  }

  @Override
  public File getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends File> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends File> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends File> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends File> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends File> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends File> boolean exists(Example<S> example) {
    return false;
  }
}
