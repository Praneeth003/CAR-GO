package com.cargobackend.dao;

import java.sql.Timestamp;
import java.util.List;

import com.cargobackend.pojo.dao.cargo.BookingInfo.BookingStatus;
import com.cargobackend.pojo.dao.cargo.PaymentInfo;
import com.cargobackend.pojo.dao.cargo.UserProfile;
import com.cargobackend.pojo.dao.cargo.Variant;
import com.cargobackend.pojo.request.*;
import com.cargobackend.pojo.response.*;
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

	public PromoCodeResponse getPromoCodes();

	public BookingHistoryResponse getBookingHistory(Integer userId);

	public BaseResponse updateCartDateInfo(List<Integer> cartIdList, List<Double> priceList,
									Timestamp fromDate, Timestamp toDate);

	public BaseResponse finishBooking(FinishBookingRequest finishBookingRequest);

	public BaseResponse deleteVariant(Integer variantId);

    public VariantResponse getVariantS(VariantRequest variantRequest);
    
	public VariantResponse createVariant(CreateVariantRequest createVariantRequest) ;
}


