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

}
