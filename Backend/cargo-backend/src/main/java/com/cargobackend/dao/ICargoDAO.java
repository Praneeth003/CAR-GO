package com.cargobackend.dao;

import com.cargobackend.pojo.response.CarMakeResponse;

public interface ICargoDAO {

	public CarMakeResponse getCarMakes(Boolean status);
}
