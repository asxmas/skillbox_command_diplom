package ru.skillbox.team13.repositories;

import java.util.ArrayList;
import java.util.List;

public abstract class Repo<T> {
  List<T> repo = new ArrayList<>();

  public List<T> getRepo()  {
    return repo;
  }

  public void addToRepo(T t)  {
    repo.add(t);
  }
}
