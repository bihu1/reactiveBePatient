package com.bihuniak.piotr.reactiveBePatient;

import com.bihuniak.piotr.reactiveBePatient.TestEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends ReactiveMongoRepository<TestEntity, ObjectId> {}
