package seebach.feuerwehr.maec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seebach.feuerwehr.maec.obj.Setting;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findBySetting(String setting);
}
