package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.PersonCompactDto;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Repository
@PersistenceContext
@RequiredArgsConstructor
public class PersonDAO {

    private final EntityManager em;

    public List<Integer> getFriendsIds(int srcPersonID, FriendshipStatusCode fsc) {
        QFriendship friendship = QFriendship.friendship;
        QPerson person = QPerson.person;

        JPAQuery<Person> query = new JPAQuery<>(em);

        List<PersonCompactDto> dtos = query.select(Projections.constructor(PersonCompactDto.class, person.id))
                .from(friendship)
                .innerJoin(friendship.destinationPerson, person)
                .where(friendship.sourcePerson.id.eq(srcPersonID).and(friendship.code.eq(fsc))).fetch();
        return dtos.stream().map(PersonCompactDto::getId).collect(Collectors.toList());
    }

    public Page<PersonDTO> getPersonsMinusIds(List<Integer> minusIds, Pageable pageable) {
        JPAQuery<Person> query = new JPAQuery<>(em);
        QPerson person = QPerson.person;
        QCity city = QCity.city;
        QCountry country = QCountry.country;

        Predicate where = person.id.notIn(minusIds);

        QueryResults<PersonDTO> qr = query.select(Projections.constructor(PersonDTO.class,
                person.id, person.firstName, person.lastName, person.regDate, person.birthDate,
                person.email, person.phone, person.photo, person.about, city.id, city.title,
                country.id, country.title))
                .from(person)
                .leftJoin(person.city, city)
                .leftJoin(person.country, country)
                .where(where)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(qr.getResults(), pageable, (int) (qr.getTotal()));
    }

    public Page<PersonDTO> find(int currentPersonId, String firstName, String lastName, LocalDateTime earliest,
                                LocalDateTime latest, String countryTitle, String cityTitle, Pageable pageable) {
        QPerson person = QPerson.person;
        QCity city = QCity.city;
        QCountry country = QCountry.country;

        Predicate predicate = person.id.ne(currentPersonId);

        BooleanBuilder where = new BooleanBuilder(predicate);
        if (nonNull(firstName)) {
            where.and(person.firstName.contains(firstName).or(person.firstName.containsIgnoreCase(firstName)));
        }

        if (nonNull(lastName)) {
            where.and(person.lastName.contains(lastName).or(person.lastName.containsIgnoreCase(lastName)));
        }

        if (nonNull(earliest)) where.and(person.birthDate.after(earliest));
        if (nonNull(latest)) where.and(person.birthDate.before(latest));
        if (nonNull(cityTitle)) where.and(city.title.eq(cityTitle));
        if (nonNull(countryTitle)) where.and(country.title.eq(countryTitle));

        JPAQuery<Person> query = new JPAQuery<>(em);

        QueryResults<PersonDTO> qr = query.select(Projections.constructor(PersonDTO.class,
                person.id, person.firstName, person.lastName, person.regDate, person.birthDate,
                person.email, person.phone, person.photo, person.about, city.id, city.title,
                country.id, country.title))
                .from(person)
                .leftJoin(person.city, city)
                .leftJoin(person.country, country)
                .where(where)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(qr.getResults(), pageable, (int) (qr.getTotal()));
    }
}