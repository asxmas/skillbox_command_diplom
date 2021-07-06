package ru.skillbox.team13.service.platform;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LanguageDto;
import ru.skillbox.team13.entity.Languages;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.LanguageRepository;

import java.util.List;
import java.util.stream.Collectors;

import ru.skillbox.team13.mapper.LanguageMapper;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository findAllLanguages;

    public DTOWrapper findAllLanguages(int offset, int itemPerPage) {
        List<Languages> languages = (List<Languages>) findAllLanguages.findAll();
        int count = languages.size();
        List<LanguageDto> languageDtoList = languages.stream().map(a -> LanguageMapper.mapToLanguagesDto(a)).collect(Collectors.toList());
        return WrapperMapper.wrap(languageDtoList, count, offset, itemPerPage, true);
    }


}
