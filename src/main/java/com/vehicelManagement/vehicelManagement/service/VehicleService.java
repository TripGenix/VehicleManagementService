package com.vehicelManagement.vehicelManagement.service;

import com.vehicelManagement.vehicelManagement.dto.VehicleDto;
import com.vehicelManagement.vehicelManagement.model.Vehicle;
import com.vehicelManagement.vehicelManagement.repo.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VehicleRepository vehicleRepository;

    //get all
    public List<VehicleDto> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.getAllVehicles();
        return modelMapper.map(vehicles, new TypeToken<List<VehicleDto>>() {}.getType());
    }

    //save vehicle
    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        Optional<Vehicle> existingVehicle =
                vehicleRepository.getVehicleByNumberPlate(vehicleDto.getNumberPlate());
        if (existingVehicle.isPresent()) {
            throw new RuntimeException("Vehicle already exists!");
        }
        vehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(vehicle, VehicleDto.class);
    }

    //get by number
    public VehicleDto getVehicleByNumber(String numberPlate) {
        Vehicle vehicle = vehicleRepository.getVehicleByNumberPlate(numberPlate)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return modelMapper.map(vehicle, VehicleDto.class);
    }

    //update vehicle
    public VehicleDto updateVehicle(int id, VehicleDto dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setNumberPlate(dto.getNumberPlate());
        vehicle.setType(dto.getType());
        vehicle.setDescription(dto.getDescription());
        vehicle.setPassengerCount(dto.getPassengerCount());
        vehicle.setCostPerKm(dto.getCostPerKm());
        vehicle.setBookingPrice(dto.getBookingPrice());
        vehicle.setStatus(dto.getStatus());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(updatedVehicle, VehicleDto.class);
    }

    public int deleteVehicle(int id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setIsDelete(true);
        vehicleRepository.save(vehicle);
        return id;
    }

}
