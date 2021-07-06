package ru.skillbox.team13.service.platform;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.skillbox.team13.dto.CountryDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.entity.Country;

import ru.skillbox.team13.mapper.CountryMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CountryRepository;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository findAllCountries;

    public DTOWrapper findAllCountries(int offset, int itemPerPage) {
        List<Country> countries = (List<Country>) findAllCountries.findAll();
        int count = countries.size();
        List<CountryDto> cityDtoList = countries.stream().map(a -> CountryMapper.mapToCountryDto(a)).collect(Collectors.toList());
        return  WrapperMapper.wrap(cityDtoList, count, offset, itemPerPage, true);
    }

}
