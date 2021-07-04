package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonDAO {

    private final EntityManagerFactory emf;

    public List<PersonDTO> fetchFriendDtos(int srcPersonID, FriendshipStatusCode fsc) {
        QFriendship friendship = QFriendship.friendship;
        QPerson person = QPerson.person;
        QCountry country = QCountry.country;
        QCity city = QCity.city;

        EntityManager em = emf.createEntityManager();
        JPAQuery<Person> query = new JPAQuery<>(em);

        List<PersonDTO> dtos = query
                .select(Projections.constructor(PersonDTO.class,
                        person.id, person.firstName, person.lastName, person.regDate, person.birthDate, person.email,
                        person.phone, person.photo, person.about, city.id, city.title, country.id, country.title))
                .from(friendship)
                .innerJoin(friendship.destinationPerson, person)
                .leftJoin(person.country, country)
                .leftJoin(person.city, city)
                .where(friendship.sourcePerson.id.eq(srcPersonID)
                        .and(friendship.status.code.eq(fsc))).fetch();
        em.close();
        return dtos;
    }
}