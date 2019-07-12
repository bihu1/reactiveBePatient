package com.bihuniak.piotr.reactiveBePatient.common;

import org.bson.types.ObjectId;

public class ObjectIdUtils {
    public static ObjectId initializeObjectId(String id){
        try {
            return new ObjectId(id);
        }
        catch (IllegalStateException | IllegalArgumentException e){
            throw new RuntimeException();
        }
    }
}
