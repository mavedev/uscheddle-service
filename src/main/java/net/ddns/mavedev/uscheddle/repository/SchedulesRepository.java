package net.ddns.mavedev.uscheddle.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import net.ddns.mavedev.uscheddle.model.db.ScheduleModel;

public interface SchedulesRepository extends MongoRepository<ScheduleModel, Integer> {
}
