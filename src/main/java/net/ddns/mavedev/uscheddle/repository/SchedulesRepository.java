package net.ddns.mavedev.uscheddle.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import net.ddns.mavedev.uscheddle.model.db.ScheduleModel;

public interface SchedulesRepository extends MongoRepository<ScheduleModel, String> {

    @Query(value = "{ 'ownerID': ?0 }")
    public List<ScheduleModel> findByOwnerID(final String ownerId);

}
