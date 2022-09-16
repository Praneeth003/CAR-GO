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

}
