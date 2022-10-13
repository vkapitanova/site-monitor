package io.aiven.monitor.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"consumer", "web"})
public interface MetricsRepository extends CrudRepository<MetricsEntity, String> {
    List<MetricsEntity> findByIdLessThanOrderByIdDesc(String id, Pageable pageable);
}
