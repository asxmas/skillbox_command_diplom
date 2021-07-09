package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.entity.Country;
import ru.skillbox.team13.repository.RepoCountry;

@Service
@RequiredArgsConstructor
public class CountryServiceRepo {
    private final RepoCountry repoCountry;

    public Country getCountryById (Integer id)  {
        return repoCountry.getById(id);
    }
}
