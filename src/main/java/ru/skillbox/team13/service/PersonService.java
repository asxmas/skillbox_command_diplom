package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.EditPersonDto;

public interface PersonService {

    DTOWrapper getMyProfile();

    DTOWrapper updateMyProfile(EditPersonDto personDTO);

    DTOWrapper deleteMyProfile();

    DTOWrapper getProfile(int id);

    DTOWrapper find(String firstName, String lastName, Integer ageFrom, Integer ageTo, String country, String city,
                    int offset, int itemPerPage);
}
