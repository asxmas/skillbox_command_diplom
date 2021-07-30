package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PlatformValuesDto;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Country;
import ru.skillbox.team13.entity.Language;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CityRepository;
import ru.skillbox.team13.repository.CountryRepository;
import ru.skillbox.team13.repository.LanguageRepository;
import ru.skillbox.team13.service.PlatformService;
import ru.skillbox.team13.util.PageUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {

    private final CityRepository citiesRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;

    @Override
    public DTOWrapper findAllLanguages(int offset, int itemPerPage) {
        return findType(languageRepository, offset, itemPerPage);
    }

    @Override
    public DTOWrapper findAllCountries(int offset, int itemPerPage) {
        return findType(countryRepository, offset, itemPerPage);
    }

    @Override
    public DTOWrapper findAllCities(int offset, int itemPerPage) {
        return findType(citiesRepository, offset, itemPerPage);
    }

    @Override
    public DTOWrapper geAll() {
        String[] cities = findType(citiesRepository).stream().map(City::getTitle).toArray(String[]::new);
        String[] countries = findType(countryRepository).stream().map(Country::getTitle).toArray(String[]::new);
        String[] langs = findType(languageRepository).stream().map(Language::getTitle).toArray(String[]::new);

        PlatformValuesDto dto = new PlatformValuesDto(cities, countries, langs);
        return WrapperMapper.wrap(dto, false);
    }

    private <T> DTOWrapper findType(JpaRepository<T, Integer> rep, int offset, int itemPerPage) {
        Pageable p = PageUtil.getPageable(offset, itemPerPage);
        Page<T> results = rep.findAll(p);
        return WrapperMapper.wrap(results.getContent(), (int) (results.getTotalElements()), offset, itemPerPage, true);
    }

    private <T> List<T> findType(JpaRepository<T, Integer> rep) {
        return rep.findAll();
    }
}
