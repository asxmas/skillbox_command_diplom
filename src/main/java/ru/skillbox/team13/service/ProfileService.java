package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PersonDTO;

public interface ProfileService {

    DTOWrapper getMyProfile();

    DTOWrapper updateMyProfile(PersonDTO personDTO);

    DTOWrapper deleteMyProfile();

    DTOWrapper getProfile(int id);
}
