package seebach.feuerwehr.maec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seebach.feuerwehr.maec.repository.CoreRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CoreService {

    @Autowired
    private CoreRepository coreRepository;

    public void save(Core core) {
        coreRepository.save(core);
    }

    public List<Core> getTime(LocalDateTime time) {
        return coreRepository.findByTimestamp(time);
    }

    public List<Core> getAll() {
        return coreRepository.findAll();
    }
}
