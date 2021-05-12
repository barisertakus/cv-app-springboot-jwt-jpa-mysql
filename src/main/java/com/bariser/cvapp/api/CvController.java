package com.bariser.cvapp.api;

import com.bariser.cvapp.dto.CvDTO;
import com.bariser.cvapp.service.CvService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cv")
@CrossOrigin("*")
public class CvController {

    private final CvService cvService;

    public CvController(CvService cvService) {
        this.cvService = cvService;
    }

    @GetMapping("/{userId}")
    ResponseEntity<CvDTO> getCvByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(cvService.findByUserId(userId));
    }

    @PostMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<CvDTO> createCv(@RequestBody CvDTO cv, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(cvService.createCv(cv, userId));
    }

    @PutMapping("/{cvId}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<CvDTO> editCv(@RequestBody CvDTO cv, @PathVariable("cvId") Long cvId){
        return ResponseEntity.ok(cvService.editCv(cv,cvId));
    }

    @DeleteMapping("/{cvId}")
    ResponseEntity<Boolean> deleteCv(@PathVariable Long cvId){
        Boolean delete = cvService.deleteCv(cvId);
        return ResponseEntity.ok(delete);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<CvDTO>> getAll(){
        return ResponseEntity.ok(cvService.getAll());
    }


}
