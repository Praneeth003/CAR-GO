package com.cargobackend.dao;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.BookingInfo.BookingStatus;
import com.cargobackend.pojo.dao.cargo.PaymentInfo;
import com.cargobackend.pojo.dao.cargo.UserProfile;
import com.cargobackend.pojo.dao.cargo.Variant;
import com.cargobackend.pojo.request.AddToCartRequest;
import com.cargobackend.pojo.request.BodyTypeRequest;
import com.cargobackend.pojo.request.ColorRequest;
import com.cargobackend.pojo.request.CreateBookingRequest;
import com.cargobackend.pojo.request.FuelTypeRequest;
import com.cargobackend.pojo.request.MakeRequest;
import com.cargobackend.pojo.request.ModelRequest;
import com.cargobackend.pojo.request.TransmissionTypeRequest;
import com.cargobackend.pojo.request.VariantRequest;
import com.cargobackend.pojo.response.AddOnResponse;
import com.cargobackend.pojo.response.AddToCartResponse;
import com.cargobackend.pojo.response.BodyTypeResponse;
import com.cargobackend.pojo.response.CarMakeResponse;
import com.cargobackend.pojo.response.ColorResponse;
import com.cargobackend.pojo.response.CreateBookingResponse;
import com.cargobackend.pojo.response.FuelTypeResponse;
import com.cargobackend.pojo.response.LocationResponse;
import com.cargobackend.pojo.response.MakeResponse;
import com.cargobackend.pojo.response.ModelResponse;
import com.cargobackend.pojo.response.PaymentInfoResponse;
import com.cargobackend.pojo.response.TransmissionTypeResponse;
import com.cargobackend.pojo.response.UserProfileResponse;
import com.cargobackend.pojo.response.VariantResponse;
import com.cargobackend.pojo.response.common.BaseResponse;

public interface ICargoDAO {

	public CarMakeResponse getCarMakes(Boolean status);
	
	public MakeResponse getMake(MakeRequest makeRequest);
	
	public ModelResponse getModel(ModelRequest modelRequest);
	
	public BodyTypeResponse getBodyType(BodyTypeRequest bodyTypeRequest);
	
	public FuelTypeResponse getFuelType(FuelTypeRequest fuelTypeRequest);
	
	public TransmissionTypeResponse getTransmissionType(TransmissionTypeRequest transmissionTypeRequest);
	
	public ColorResponse getColor(ColorRequest colorRequest);
	
	public VariantResponse getVariant(VariantRequest variantRequest);
	
	public VariantResponse getVariantById(Integer variantId);
	
	public LocationResponse getLocation();

	public AddOnResponse getAddOns();
	
	public AddOnResponse getAddOns(List<Integer> addOns);

	UserProfileResponse getUserProfiles(Integer userId);

	PaymentInfoResponse getPaymentInfo(Integer userId);

	public PaymentInfoResponse addPaymentInfo(PaymentInfo paymentInfo);

	public UserProfileResponse addUserProfile(UserProfile userProfile);

	public AddToCartResponse addToCart(AddToCartRequest addToCartRequest);

	public CreateBookingResponse createBooking(CreateBookingRequest createBookingRequest);

	public BaseResponse updateBookingStatus(Integer bookingReferenceId, String paymentReferenceId, BookingStatus status);

	public CreateBookingResponse getBooking(Integer bookingId);
}
