package seebach.feuerwehr.maec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CoreController {

    @Autowired
    private CoreService coreService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Core core) {
        coreService.save(core);
        return ResponseEntity.ok("Saved");
    }

    @GetMapping("/all")
    public List<Core> getAll() {
        return coreService.getAll();
    }


}

