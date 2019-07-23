package com.bihuniak.piotr.reactiveBePatient.domain.user;

import com.bihuniak.piotr.reactiveBePatient.domain.user.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, ObjectId> {
    Mono<User> findByUsername(String username);
    Mono<Boolean> existsByUsername(String username);
}
