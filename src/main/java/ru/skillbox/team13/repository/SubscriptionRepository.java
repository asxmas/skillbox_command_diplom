package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
