package ru.skillbox.team13.service.platform;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.skillbox.team13.dto.CityDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.mapping.CityMapper;
import ru.skillbox.team13.repository.CitiesRepository;
import ru.skillbox.team13.mapper.WrapperMapper;



import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CitiesRepository findAllCities;

    public DTOWrapper findAllCities( String name, int offset, int itemPerPage) {
        List<City> cities = (List<City>) findAllCities.findAll();
        int count = cities.size();
        List<CityDto> cityDtoList = cities.stream().map(a -> CityMapper.mapToCityDto(a)).collect(Collectors.toList());
        return  WrapperMapper.wrap(cityDtoList, count, offset, itemPerPage, true);
    }

}
