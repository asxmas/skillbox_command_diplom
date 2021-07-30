package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

public interface PlatformService {

    DTOWrapper findAllLanguages(int offset, int itemPerPage);

    DTOWrapper findAllCountries(int offset, int itemPerPage);

    DTOWrapper findAllCities(int offset, int itemPerPage);

    DTOWrapper geAll();
}
