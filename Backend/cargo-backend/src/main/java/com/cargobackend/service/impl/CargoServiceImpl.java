package com.cargobackend.service.impl;

import org.springframework.stereotype.Service;


import com.cargobackend.dao.ICargoDAO;
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
import com.cargobackend.pojo.response.VariantResponse;
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
	public MakeResponse getMake(MakeRequest makeRequest) {
		// TODO Auto-generated method stub
		return cargoDAO.getMake(makeRequest);
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


	@Override
	public ColorResponse getColor(ColorRequest colorRequest) {
		// TODO Auto-generated method stub
		return cargoDAO.getColor(colorRequest);
	}


	@Override
	public VariantResponse getVariant(VariantRequest variantRequest) {
		// TODO Auto-generated method stub
		return cargoDAO.getVariant(variantRequest);
	}
	
	@Override
	public VariantResponse getVariantById(Integer variantId) {
		// TODO Auto-generated method stub
		return cargoDAO.getVariantById(variantId);
	}


	@Override
	public LocationResponse getLocation() {
		// TODO Auto-generated method stub
		return cargoDAO.getLocation();
	}
	
	@Override
	public AddOnResponse getAddOns() {
		// TODO Auto-generated method stub
		return cargoDAO.getAddOns();
	}

}
