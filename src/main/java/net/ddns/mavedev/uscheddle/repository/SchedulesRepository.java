package net.ddns.mavedev.uscheddle.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import net.ddns.mavedev.uscheddle.model.db.ScheduleModel;

public interface SchedulesRepository extends MongoRepository<ScheduleModel, String> {

    @Query(value = "{ 'ownerId': ?0 }")
    public ScheduleModel[] findByOwnerID(final String ownerId);

}
