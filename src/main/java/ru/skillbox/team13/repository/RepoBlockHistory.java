package ru.skillbox.team13.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.skillbox.team13.entity.BlockHistory;

public class RepoBlockHistory implements Repo<BlockHistory> {

  @Override
  public void addToRepo(BlockHistory blockHistory) {

  }

  @Override
  public void removeFromRepo(BlockHistory blockHistory) {

  }

  @Override
  public List<BlockHistory> findAll() {
    return null;
  }

  @Override
  public List<BlockHistory> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<BlockHistory> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<BlockHistory> findAllById(Iterable<Integer> iterable) {
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
  public void delete(BlockHistory blockHistory) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> iterable) {

  }

  @Override
  public void deleteAll(Iterable<? extends BlockHistory> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends BlockHistory> S save(S s) {
    return null;
  }

  @Override
  public <S extends BlockHistory> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<BlockHistory> findById(Integer integer) {
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
  public <S extends BlockHistory> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public <S extends BlockHistory> List<S> saveAllAndFlush(Iterable<S> iterable) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<BlockHistory> iterable) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public BlockHistory getOne(Integer integer) {
    return null;
  }

  @Override
  public BlockHistory getById(Integer integer) {
    return null;
  }

  @Override
  public <S extends BlockHistory> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends BlockHistory> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends BlockHistory> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends BlockHistory> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends BlockHistory> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends BlockHistory> boolean exists(Example<S> example) {
    return false;
  }
}
