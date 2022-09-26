package com.cargobackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cargobackend.pojo.response.CarMakeResponse;
import com.cargobackend.service.ICargoService;

@Controller
@RequestMapping({"/cargo"})
public class CargoController {
	
    private final ICargoService cargoService;
    
	
	public CargoController(ICargoService cargoService) {
		super();
		this.cargoService = cargoService;
	}

	@GetMapping("/getCarMake")
	@ResponseBody
	public CarMakeResponse getCarMake() {
		System.out.println("\n CargoController getCarMake ");
		return cargoService.getCarMakes(null);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping(value = {"model"})
    @ResponseBody
    public ModelResponse getModel(@RequestBody ModelRequest modelRequest) {
    	System.out.println("\n CargoController  getModel "+modelRequest);
    	return cargoService.getModel(modelRequest);
    }
    
    @PostMapping(value = {"bodyType"})
    @ResponseBody
    public BodyTypeResponse getBodyType(@RequestBody BodyTypeRequest bodyTypeReqeust) {
    	System.out.println("\n CargoController  getBodyType "+bodyTypeReqeust);
    	return cargoService.getBodyType(bodyTypeReqeust);
    }
    
    
    @PostMapping(value = {"fuelType"})
    @ResponseBody
    public FuelTypeResponse getFuelType(@RequestBody FuelTypeRequest fuelTypeReqeust) {
    	System.out.println("\n CargoController  getFuelType "+fuelTypeReqeust);
    	return cargoService.getFuelType(fuelTypeReqeust);
    }

    @PostMapping(value = {"transmissionType"})
    @ResponseBody
    public TransmissionTypeResponse getTransmissionTypeReqeust(@RequestBody TransmissionTypeRequest transmissionTypeReqeust) {
    	System.out.println("\n CargoController  getTransmissionTypeReqeust "+transmissionTypeReqeust);
    	return cargoService.getTransmissionType(transmissionTypeReqeust);
    }


}
