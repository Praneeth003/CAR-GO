package com.cargobackend.service;

import java.io.File;

import com.cargobackend.pojo.dao.cargo.PaymentInfo;
import com.cargobackend.pojo.dao.cargo.UserProfile;
import com.cargobackend.pojo.request.*;
import com.cargobackend.pojo.response.*;
import com.cargobackend.pojo.response.common.BaseResponse;

public interface ICargoService {

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

	public UserProfileResponse getUserProfiles(Integer userId);

	public PaymentInfoResponse getPaymentInfo(Integer userId);

	public PaymentInfoResponse addPaymentInfo(PaymentInfo paymentInfo);

	public UserProfileResponse addUserProfile(UserProfile userProfile);

	public AddToCartResponse addToCart(AddToCartRequest addToCartRequest);

	public CreateBookingResponse createBooking(CreateBookingRequest createBookingRequest);

	public File getBookingInvoice(Integer bookingId);

	public PromoCodeResponse getPromoCodes();

	public CartPriceResponse getCartPrice(GetCartPriceRequest cartPriceRequest);

	public PromoPriceResponse getPromoPrice(GetPromoPriceRequest promoPriceRequest);

	public BookingHistoryResponse getBookingHistory(Integer userId);

	public CreateBookingResponse getUpdatedBookingInfo(UpdateBookingRequest bookingRequest);

	public CreateBookingResponse getUpdatedBookingInfoAndPersist(UpdateBookingRequest updateBookingRequest);

	public CreateBookingResponse cancelBooking(Integer bookingInfoId);

	public CreateBookingResponse findBooking(FindBookingRequest findBookingRequest);

	public CreateBookingResponse getBooking(Integer bookingInfoId);

	public BaseResponse finishBooking(FinishBookingRequest finishBookingRequest);

	public BaseResponse deleteVariant(Integer variantId);

	public VariantResponse getVariantS(VariantRequest variantRequest);

	public VariantResponse createVariant(CreateVariantRequest createVariantRequest);
}

