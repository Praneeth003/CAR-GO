package com.cargobackend.service.impl;

import org.springframework.stereotype.Service;

import com.cargobackend.dao.ICargoDAO;
import com.cargobackend.pojo.response.CarMakeResponse;
import com.cargobackend.service.ICargoService;

@Service
public class CargoServiceImpl implements ICargoService{
	
    private final ICargoDAO cargoDAO;
    

	public CargoServiceImpl(ICargoDAO cargoDAO) {
		super();
		this.cargoDAO = cargoDAO;
	}


	@Override
	public CarMakeResponse getCarMakes(Boolean status) {
		System.out.println("In getCarMakes status......"+status);
		return cargoDAO.getCarMakes(status);
	}
























	@Override
	public ModelResponse getModel(ModelRequest modelRequest) {
		// TODO Auto-generated method stub
		return cargoDAO.getModel(modelRequest);
	}


	@Override
	public BodyTypeResponse getBodyType(BodyTypeRequest bodyTypeRequest) {
		// TODO Auto-generated method stub
		return cargoDAO.getBodyType(bodyTypeRequest);
	}


	@Override
	public FuelTypeResponse getFuelType(FuelTypeRequest fuelTypeRequest) {
		// TODO Auto-generated method stub
		return cargoDAO.getFuelType(fuelTypeRequest);
	}


	@Override
	public TransmissionTypeResponse getTransmissionType(TransmissionTypeRequest transmissionTypeRequest) {
		// TODO Auto-generated method stub
		return cargoDAO.getTransmissionType(transmissionTypeRequest);
	}

}
