package com.example.tanmeyah.registration;

import com.example.tanmeyah.branch.domain.Branch;
import com.example.tanmeyah.branch.repository.BranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("tanmeyah/registration")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class RegistrationController {

    private final RegistrationService registrationService;
    private final BranchRepository branchRepository;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody
                                              RegistrationRequest
                                              registrationRequest) {
        return registrationService.registerEmployee(registrationRequest);
    }

    @GetMapping("{branchId}")
    public Map<String, Object> getBranch(@PathVariable("branchId") Long id) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("teller","true");
//        jsonObject.put("branch",branchRepository.findBranchById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("results", "sdsd");
        return map;

    }

}
