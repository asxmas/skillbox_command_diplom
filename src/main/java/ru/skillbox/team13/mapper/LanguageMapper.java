package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.LanguageDto;
import ru.skillbox.team13.entity.Languages;

public class LanguageMapper {

    public static LanguageDto mapToLanguagesDto(Languages languages) {
        return new LanguageDto(languages.getId(), languages.getTitle());
    }
}
