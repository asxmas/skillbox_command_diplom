package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.PersonCompactDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.QFriendship;
import ru.skillbox.team13.entity.QPerson;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@PersistenceContext
@RequiredArgsConstructor
public class PersonDAO {

    private final EntityManager em;

    public List<Integer> getFriendsIds(int srcPersonID, FriendshipStatusCode fsc) {
        QFriendship friendship = QFriendship.friendship;
        QPerson person = QPerson.person;

        JPAQuery<Person> query = new JPAQuery<>(em);

        List<PersonCompactDto> dtos =  query.select(Projections.constructor(PersonCompactDto.class, person.id))
                .from(friendship)
                .innerJoin(friendship.destinationPerson, person)
                .where(friendship.sourcePerson.id.eq(srcPersonID).and(friendship.code.eq(fsc))).fetch();
        return dtos.stream().map(PersonCompactDto::getId).collect(Collectors.toList());
    }
}