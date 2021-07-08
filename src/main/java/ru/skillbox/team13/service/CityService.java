package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.repository.CityRepo;

@Service
@Component
@Setter
@RequiredArgsConstructor
public class CityService {
    private final CityRepo cityRepo;

    public City getById(Integer id) {
        return cityRepo.getById(id);
    }
}
