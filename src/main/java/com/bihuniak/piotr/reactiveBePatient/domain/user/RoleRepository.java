package com.bihuniak.piotr.reactiveBePatient.domain.user;

import com.bihuniak.piotr.reactiveBePatient.domain.user.model.UserRole;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepository extends ReactiveMongoRepository<UserRole, ObjectId> {
    Mono<UserRole> findByRole(String role);
}
