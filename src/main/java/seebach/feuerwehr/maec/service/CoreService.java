package seebach.feuerwehr.maec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seebach.feuerwehr.maec.repository.CoreRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CoreService {

    @Autowired
    private CoreRepository coreRepository;

    public void save(Core core) {
        coreRepository.save(core);
    }

    public List<Core> getTime() {
        return coreRepository.findByTimestampBeforeAndActiveFalse(LocalDateTime.now());
    }

    public Optional<Core> getId(long id){
        return coreRepository.findById(id);
    }

    public boolean delete(long id){
        if (coreRepository.existsById(id)){
            coreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean write(long id, Core updatedCoreData) {
        Optional<Core> optionalCore = coreRepository.findById(id);
        if (optionalCore.isPresent()) {
            Core existingCore = optionalCore.get();

            // Update der Felder
            existingCore.setFwName(updatedCoreData.getFwName());
            existingCore.setFachBereich(updatedCoreData.getFachBereich());
            existingCore.setMeldung(updatedCoreData.getMeldung());
            existingCore.setOrt(updatedCoreData.getOrt());
            existingCore.setForces(updatedCoreData.getForces());
            existingCore.setTimestamp(updatedCoreData.getTimestamp());

            // Speichern (überschreibt bestehenden Datensatz)
            coreRepository.save(existingCore);
            return true;
        }
        return false;
    }

    public List<Core> getAll() {
        return coreRepository.findAll();
    }


}
