package com.cargobackend.dao;

import com.cargobackend.pojo.response.CarMakeResponse;

public interface ICargoDAO {

	public CarMakeResponse getCarMakes(Boolean status);


















	public ModelResponse getModel(ModelRequest modelRequest);
	
	public BodyTypeResponse getBodyType(BodyTypeRequest bodyTypeRequest);
	
	public FuelTypeResponse getFuelType(FuelTypeRequest fuelTypeRequest);
	
	public TransmissionTypeResponse getTransmissionType(TransmissionTypeRequest transmissionTypeRequest);
	
}
