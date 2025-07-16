package seebach.feuerwehr.maec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seebach.feuerwehr.maec.obj.Alarm;
import seebach.feuerwehr.maec.service.Core;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CoreRepository extends JpaRepository<Core, Long> {
    List<Core> findByTimestampBeforeAndActiveFalse(LocalDateTime time);

}

