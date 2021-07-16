package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.EditPersonDto;
import ru.skillbox.team13.dto.PersonDTO;

public interface PersonService {

    DTOWrapper getMyProfile();

    DTOWrapper updateMyProfile(EditPersonDto personDTO);

    DTOWrapper deleteMyProfile();

    DTOWrapper getProfile(int id);
}
