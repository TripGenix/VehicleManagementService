package com.vehicelManagement.vehicelManagement.controller;

import com.vehicelManagement.vehicelManagement.dto.VehicleDto;
import com.vehicelManagement.vehicelManagement.model.Vehicle;
import com.vehicelManagement.vehicelManagement.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vehicleController/api/v1")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/getallvehicles")
    public List<VehicleDto> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/getvehiclebynumber")
    public ResponseEntity<?> getVehicleByNumber(@RequestParam String number) {
        try {
            return ResponseEntity.ok(vehicleService.getVehicleByNumber(number));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveVehicle(@RequestBody VehicleDto vehicleDto) {

        try {
            VehicleDto savedVehicle = vehicleService.createVehicle(vehicleDto);
            return ResponseEntity.ok(savedVehicle);

        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(ex.getMessage());
        }
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<?> updateVehicle(
            @PathVariable int id,
            @RequestBody VehicleDto dto) {

        try {
            return ResponseEntity.ok(vehicleService.updateVehicle(id, dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable int id) {
        try {
            int deletedId = vehicleService.deleteVehicle(id);
            return ResponseEntity.ok("Vehicle deleted successfully. ID: " + deletedId);

        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


}
