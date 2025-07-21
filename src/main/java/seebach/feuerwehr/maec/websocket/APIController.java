package seebach.feuerwehr.maec.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import seebach.feuerwehr.maec.service.Core;
import seebach.feuerwehr.maec.service.CoreService;

import java.util.Map;

@RestController
@RequestMapping("/alarm/api")
public class APIController {

    @Autowired
    private CoreService coreService;

    @GetMapping("/{id}")
    public CoreDTO getAlarm(@PathVariable Long id) {
        Core core = coreService.getId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new CoreDTO(core);
    }

}

