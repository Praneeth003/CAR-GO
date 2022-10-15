package com.cargobackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.pojo.request.BodyTypeRequest;
import com.cargobackend.pojo.request.ColorRequest;
import com.cargobackend.pojo.request.FuelTypeRequest;
import com.cargobackend.pojo.request.MakeRequest;
import com.cargobackend.pojo.request.ModelRequest;
import com.cargobackend.pojo.request.TransmissionTypeRequest;
import com.cargobackend.pojo.request.VariantRequest;
import com.cargobackend.pojo.response.AddOnResponse;
import com.cargobackend.pojo.response.BodyTypeResponse;
import com.cargobackend.pojo.response.CarMakeResponse;
import com.cargobackend.pojo.response.ColorResponse;
import com.cargobackend.pojo.response.FuelTypeResponse;
import com.cargobackend.pojo.response.LocationResponse;
import com.cargobackend.pojo.response.MakeResponse;
import com.cargobackend.pojo.response.ModelResponse;
import com.cargobackend.pojo.response.TransmissionTypeResponse;
import com.cargobackend.pojo.response.UserDetailsResponse;
import com.cargobackend.pojo.response.VariantResponse;
import com.cargobackend.service.ICargoService;
import com.cargobackend.pojo.dao.cargo.Variant;

@Controller
@CrossOrigin(origins = "*")
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
	
	@GetMapping("/location")
	@ResponseBody
	public LocationResponse getLocation() {
		System.out.println("\n CargoController getLocation ");
		return cargoService.getLocation();
	}
	
    @PostMapping(value = {"/make"})
    @ResponseBody
    public MakeResponse getMake(@RequestBody MakeRequest makeRequest) {
    	System.out.println("\n CargoController  getMake "+makeRequest);
    	return cargoService.getMake(makeRequest);
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

    @PostMapping(value = {"color"})
    @ResponseBody
    public ColorResponse getColor(@RequestBody ColorRequest colorReqeust) {
    	System.out.println("\n CargoController  getColor "+colorReqeust);
    	return cargoService.getColor(colorReqeust);
    }

    @PostMapping(value = {"variant"})
    @ResponseBody
    public VariantResponse getVariant(@RequestBody VariantRequest variantRequest) {
    	System.out.println("\n CargoController  getVariant "+variantRequest);
    	return cargoService.getVariant(variantRequest);
    }
    
    @GetMapping(value = {"/variant_by_id/{id}"})
    @ResponseBody
    public VariantResponse getVariantById(@PathVariable Integer id) {
    	System.out.println("\n CargoController  getVariantById "+id);
    	return cargoService.getVariantById(id);
    }
    
    @GetMapping(value = {"/add_on"})
    @ResponseBody
    public AddOnResponse getAddOns() {
    	System.out.println("\n CargoController  getAddOns ");
    	return cargoService.getAddOns();
    }
    

}
