package com.cargobackend.service;

import com.cargobackend.pojo.response.CarMakeResponse;

public interface ICargoService {

	public CarMakeResponse getCarMakes(Boolean status);
}
