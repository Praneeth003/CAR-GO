package com.cargobackend.service;

import com.cargobackend.pojo.response.CarMakeResponse;

public interface ICargoService {

	public CarMakeResponse getCarMakes(Boolean status);


















	public ModelResponse getModel(ModelRequest modelRequest);
	
	public BodyTypeResponse getBodyType(BodyTypeRequest bodyTypeRequest);
	
	public FuelTypeResponse getFuelType(FuelTypeRequest fuelTypeRequest);
	
	public TransmissionTypeResponse getTransmissionType(TransmissionTypeRequest transmissionTypeRequest);
		
}
