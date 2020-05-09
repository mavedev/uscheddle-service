package net.ddns.mavedev.uscheddle.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import net.ddns.mavedev.uscheddle.model.db.ScheduleModel;

public interface SchedulesRepository extends MongoRepository<ScheduleModel, String> {

    @Query(value = "{ 'ownerId': ?0 }")
    public ScheduleModel[] findAllIdByOwnerId(final String ownerId);

    @Query(value = "{ 'ownerId': ?0 }")
    public ScheduleModel[] findAllNamesByOwnerId(final String ownerId);

}
