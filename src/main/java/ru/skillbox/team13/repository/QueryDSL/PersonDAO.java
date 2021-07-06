package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@PersistenceContext
@RequiredArgsConstructor
public class PersonDAO {

    private final EntityManager em;

    public List<PersonDTO> fetchFriendDtos(int srcPersonID, FriendshipStatusCode fsc) {
        QFriendship friendship = QFriendship.friendship;
        QPerson person = QPerson.person;
        QCountry country = QCountry.country;
        QCity city = QCity.city;

        JPAQuery<Person> query = new JPAQuery<>(em);

        return query
                .select(Projections.constructor(PersonDTO.class,
                        person.id, person.firstName, person.lastName, person.regDate, person.birthDate, person.email,
                        person.phone, person.photo, person.about, city.id, city.title, country.id, country.title))
                .from(friendship)
                .innerJoin(friendship.destinationPerson, person)
                .leftJoin(person.country, country)
                .leftJoin(person.city, city)
                .where(friendship.sourcePerson.id.eq(srcPersonID)
                        .and(friendship.status.code.eq(fsc))).fetch();
    }

    public List<PersonDTO> getById(List<Integer> ids) {
        Predicate where = QPerson.person.id.in(ids);
        return find(where);
    }

    public PersonDTO getById(int authorId) {
        Predicate where = QPerson.person.id.eq(authorId);
        return find(where).get(0);
    }

    private List<PersonDTO> find(Predicate predicate) {
        QPerson person = QPerson.person;
        QCity city = QCity.city;
        QCountry country = QCountry.country;

        JPAQuery<Person> query = new JPAQuery<>(em);

        return query
                .select(Projections.constructor(PersonDTO.class,
                        person.id, person.firstName, person.lastName, person.regDate, person.birthDate, person.email,
                        person.phone, person.photo, person.about, city.id, city.title, country.id, country.title))
                .from(person)
                .leftJoin(person.country, country)
                .leftJoin(person.city, city)
                .where(predicate)
                .fetch();
    }
}