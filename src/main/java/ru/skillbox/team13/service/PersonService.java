package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

public interface PersonService {

    DTOWrapper getMyProfile();

    DTOWrapper updateMyProfile(String fName, String lName, String about, String city, String country, String photo,
                               String phone, Long bdate);

    DTOWrapper deleteMyProfile();

    DTOWrapper getProfile(int id);

    DTOWrapper find(String firstName, String lastName, Integer ageFrom, Integer ageTo, String country, String city,
                    int offset, int itemPerPage);
}
