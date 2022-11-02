package com.cargobackend.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.UUID;

import com.cargobackend.pojo.dao.cargo.*;
import com.cargobackend.pojo.request.*;
import com.cargobackend.pojo.response.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;


import com.cargobackend.dao.ICargoDAO;
import com.cargobackend.dao.IUserDAO;
import com.cargobackend.pojo.dao.cargo.AddOn;
import com.cargobackend.pojo.dao.cargo.AddOn.AddOnComputeStrategy;
import com.cargobackend.pojo.dao.cargo.BookingInfo;
import com.cargobackend.pojo.dao.cargo.BookingInfo.BookingStatus;
import com.cargobackend.pojo.dao.cargo.CartEntry;
import com.cargobackend.pojo.dao.cargo.PaymentInfo;
import com.cargobackend.pojo.dao.cargo.UserProfile;
import com.cargobackend.pojo.dao.cargo.Variant;
import com.cargobackend.pojo.dao.user.UserDetails;
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
import com.cargobackend.pojo.response.InvoiceRequest;
import com.cargobackend.pojo.response.InvoiceRequest.CustomField;
import com.cargobackend.pojo.response.InvoiceRequest.Item;
import com.cargobackend.pojo.response.LocationResponse;
import com.cargobackend.pojo.response.MakeResponse;
import com.cargobackend.pojo.response.ModelResponse;
import com.cargobackend.pojo.response.PaymentInfoResponse;
import com.cargobackend.pojo.response.TransmissionTypeResponse;
import com.cargobackend.pojo.response.UserDetailsResponse;
import com.cargobackend.pojo.response.UserProfileResponse;
import com.cargobackend.pojo.response.VariantResponse;
import com.cargobackend.pojo.response.common.BaseResponse;
import com.cargobackend.service.ICargoService;
import com.cargobackend.utils.CommonConstants;
import com.cargobackend.utils.CommonConstants.UserType;
import com.google.gson.Gson;

@Service
public class CargoServiceImpl implements ICargoService{
	
    private final ICargoDAO cargoDAO;
    private final IUserDAO userDAO;
    
    private class PaymentInfoResp {
    	String paymentReferenceId;
    	BookingStatus paymentStatus;
    	
    	PaymentInfoResp(String paymentReferenceId, BookingStatus paymentStatus){
    		this.paymentReferenceId = paymentReferenceId;
    		this.paymentStatus = paymentStatus;
    	}
    	
    }
    

	public CargoServiceImpl(ICargoDAO cargoDAO, IUserDAO userDAO) {
		super();
		this.cargoDAO = cargoDAO;
		this.userDAO = userDAO;
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
	
	@Override
	public UserProfileResponse getUserProfiles(Integer userId) {
		// TODO Auto-generated method stub
		return cargoDAO.getUserProfiles(userId);
	}
	
	@Override
	public PaymentInfoResponse getPaymentInfo(Integer userId) {
		// TODO Auto-generated method stub
		return cargoDAO.getPaymentInfo(userId);
	}
	
	@Override
	public PaymentInfoResponse addPaymentInfo(PaymentInfo paymentInfo ) {
		// TODO Auto-generated method stub
		return cargoDAO.addPaymentInfo(paymentInfo);
	}
	
	@Override
	public UserProfileResponse addUserProfile(UserProfile userProfile ) {
		// TODO Auto-generated method stub
		return cargoDAO.addUserProfile(userProfile);
	}
	
	public CartPrice calculateTripCost(Variant variant, List<AddOn> addOns, Timestamp fromTimeStamp, Timestamp toTimestamp) {
		Double  variantPrice = Double.valueOf(variant.getVariantPricePerKm());
		int nDays = tripDurationInDays(fromTimeStamp,toTimestamp);
		System.out.println("\n tripDurationInDays ........nDays "+nDays);
		if(nDays <=0) {
			nDays = 1;
		}
		CartPrice cartPrice = new CartPrice();
		variantPrice = nDays*variantPrice;
		Double tripPrice = variantPrice;
		System.out.println("\n AddOnList ........nDays "+ addOns);
		List<AddOnPrice> addOnPriceList = new ArrayList<>();
		if(addOns != null) {
			for(AddOn addOn : addOns) {
				Double addOnPrice = 0.0;
				if(AddOnComputeStrategy.FIXED.toString().equalsIgnoreCase(addOn.getAddOnComputeStrategy().toString())) {
					addOnPrice = addOn.getAddOnValue();
				}
				else if(AddOnComputeStrategy.PER_DAY.toString().equalsIgnoreCase(addOn.getAddOnComputeStrategy().toString())) {
					addOnPrice = (nDays * addOn.getAddOnValue());
				}
				AddOnPrice addOnPriceInfo = new AddOnPrice();
				addOnPriceInfo.setAddOn(addOn);
				addOnPriceInfo.setPrice(addOnPrice);
				addOnPriceList.add(addOnPriceInfo);
				tripPrice = tripPrice + addOnPrice;
			}
		}
		cartPrice.setAddOnPrices(addOnPriceList);
		System.out.println("\n tripPrice ........tripPrice "+tripPrice);
		System.out.println("\n tripPrice ........final tripPrice "+tripPrice);
		cartPrice.setPrice(tripPrice);
		return cartPrice;
	}
	
	public Integer tripDurationInDays(Timestamp fromTimeStamp, Timestamp toTimestamp) {
		long diff = toTimestamp.getTime()-fromTimeStamp.getTime();
		long nDays =  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return (int) nDays;
	}
	
	@Override
	public AddToCartResponse addToCart(AddToCartRequest addToCartRequest) {
		AddToCartResponse addToCartErrorResponse = new AddToCartResponse();
		addToCartErrorResponse.setFailedResponse();
		List<Integer> addOnIds = addToCartRequest.getAddOnIds();
		List<AddOn> addOns = (addOnIds == null || addOnIds.isEmpty()) ?  new ArrayList<>(): cargoDAO.getAddOns(addOnIds).getAddOnList();
		List<Variant> variantList = cargoDAO.getVariantById(addToCartRequest.getVariantId()).getVariantList();
		Variant variant = null;
		if(variantList != null && variantList.size() > 0) {
			variant = variantList.get(0);
		}
		if(variant == null) {
			addToCartErrorResponse.setErrorCode("404");
			addToCartErrorResponse.setErrorDescription("Variant Not Found");
			return addToCartErrorResponse;
		}
		CartPrice cartPrice = calculateTripCost(variant, addOns,addToCartRequest.getFromDate(),addToCartRequest.getToDate());
		Double tripCost = cartPrice.getPrice();
		addToCartRequest.setPrice(tripCost);
		if(addToCartRequest.getUserId() == null) {
			UserDetails userDetails = new UserDetails();
			UUID uuid = UUID.randomUUID();
			userDetails.setUserName("Guest" + uuid.toString().substring(0,13).replace("-",""));
			userDetails.setUserPassword("Pass1234");
			userDetails.setUserType("GUEST");
			UserDetailsResponse usd = userDAO.registerUser(userDetails); 
			addToCartRequest.setUserId(usd.getUserDetails().getUserId());
		}
		AddToCartResponse addToCartResponse = cargoDAO.addToCart(addToCartRequest);
		if(addToCartResponse.isSuccess()){
			addToCartResponse.getCartEntry().setCartPrice(cartPrice);
		}
		return addToCartResponse;
		
	}
	
	public CreateBookingResponse validateCreateBookingRequest(CreateBookingRequest createBookingRequest) {
		CreateBookingResponse initialValidationsResponse = new CreateBookingResponse();
		if(createBookingRequest.getUserId() == null) {
			initialValidationsResponse.setErrorCode("404");
			initialValidationsResponse.setErrorDescription("UserId Not Found");
			initialValidationsResponse.setFailedResponse();
			return initialValidationsResponse;
		}
		if(createBookingRequest.getCartIds() == null || createBookingRequest.getCartIds().isEmpty()) {
			initialValidationsResponse.setErrorCode("404");
			initialValidationsResponse.setErrorDescription("No Cart Details For Booking");
			initialValidationsResponse.setFailedResponse();
			return initialValidationsResponse;
		}
		if(createBookingRequest.getUserProfileIds() == null || createBookingRequest.getCartIds().size() != createBookingRequest.getUserProfileIds().size()) {
			initialValidationsResponse.setErrorCode("404");
			initialValidationsResponse.setErrorDescription("Not Enough User Profiles for Booking");
			initialValidationsResponse.setFailedResponse();
			return initialValidationsResponse;
		}
		initialValidationsResponse.setSuccessResponse();
		return initialValidationsResponse;
	}
	
	public CreateBookingResponse validateCreateBookingResponse(CreateBookingResponse postValidationsResponse) {
		BookingInfo bookingInfo = postValidationsResponse.getBookingInfo();
		if(bookingInfo.getUser() == null) {
			postValidationsResponse.setErrorCode("404");
			postValidationsResponse.setErrorDescription("UserId Not Found");
			postValidationsResponse.setFailedResponse();
			return postValidationsResponse;
		}
		if(bookingInfo.getCartList() == null || bookingInfo.getCartList().isEmpty()) {
			postValidationsResponse.setErrorCode("404");
			postValidationsResponse.setErrorDescription("No Cart Details For Booking");
			postValidationsResponse.setFailedResponse();
			return postValidationsResponse;
		}
		postValidationsResponse.setSuccessResponse();
		return postValidationsResponse;
	}
	
	public PaymentInfoResp completeBookingAndGetStatus(PaymentInfo paymentInfo, Double price ) {
		UUID uuid = UUID.randomUUID();
        String uuidAsString = "pr" + uuid.toString().substring(0,18);
		return new PaymentInfoResp(uuidAsString, BookingStatus.SUCCESS);
	}

	public Double getPromoPrice(PromoCode promoCode, Double baseAmount){
    	if(promoCode.getPromoCodeType() == PromoCode.PromoCodeType.FLAT){
    		return promoCode.getPromoValue();
		}
		else if(promoCode.getPromoCodeType() == PromoCode.PromoCodeType.PERCENTAGE){
    		return (promoCode.getPromoValue() * baseAmount) /100;
		}
    	return 0.0;
	}
	
	public Double getTotalCost(BookingInfo bookingInfo) {
		Double allCarsPrice =  bookingInfo.getCartList().stream().map(cart -> (cart.getPrice() != null ? cart.getPrice() : 0.0)).mapToDouble(Double::doubleValue).sum();
		Double promoCodePrice = bookingInfo.getPromoCodeList().stream().map(promoCode -> (getPromoPrice(promoCode,allCarsPrice))).mapToDouble(Double::doubleValue).sum();
		return (allCarsPrice - promoCodePrice);
	}
	
	public String nullToString(String a) {
		return a == null ? "" :a;
	}
	
	public File generateBookingInvoice(BookingInfo bookingInfo) {
		
		Gson gson = new Gson();
		UserDetails user = bookingInfo.getUser();
		String userInfo = user.getUserName() +"\n" + nullToString(user.getUserMobileNumber()) + "\n" + nullToString(user.getUserEmail());
		
		InvoiceRequest invoice = new InvoiceRequest();
		invoice.setFrom("Cargo, Inc");
		invoice.setTo(userInfo);
		invoice.setDate(new Date().toGMTString());
		List<CustomField> customFields = new ArrayList<>();
		
		CustomField bookingReferenceId = new CustomField();
		bookingReferenceId.setName("Booking Reference Id");
		bookingReferenceId.setValue("cargo" + bookingInfo.getBookingInfoId().toString());
		customFields.add(bookingReferenceId);
		
		CustomField paymentReferenceId = new CustomField();
		paymentReferenceId.setName("Payment Reference Id");
		paymentReferenceId.setValue(bookingInfo.getPaymentInfoReferenceId() != null ? bookingInfo.getPaymentInfoReferenceId() : "");
		customFields.add(paymentReferenceId);
		
		invoice.setCustom_fields(customFields);
		
		invoice.setAmount_paid(getTotalCost(bookingInfo).toString());
		
		List<Item> itemList = new ArrayList<Item>();
		
		for(CartEntry cartEntry : bookingInfo.getCartList()) {
			Item carInfo = new Item();
			Variant variant = cartEntry.getVariant();
			carInfo.setName(variant.getModel().getMake().getMakeName() + "   "  + variant.getModel().getModelName() + "   " + variant.getVariantName());
			carInfo.setQuantity("1");
			carInfo.setUnit_cost(cartEntry.getPrice().toString());
			itemList.add(carInfo);
		}
		invoice.setItems(itemList);
        
        HttpURLConnection con = null;
        
        String url = "https://invoice-generator.com";
        
        System.out.println(gson.toJson(invoice).toString());
        
        byte[] postData = gson.toJson(invoice).getBytes(StandardCharsets.UTF_8);
        
//        FileOutputStream outS = new FileOutputStream("Invoice.pdf");
        File file = new File("cargo" + bookingInfo.getBookingInfoId().toString() +"_invoice.pdf");
        
        try {

            var myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/json");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {

                wr.write(postData);
            }
            

            IOUtils.copy(con.getInputStream(), new FileOutputStream(file));

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            con.disconnect();
     
        }
        return file;
		
	}
	
	@Override
	public CreateBookingResponse createBooking(CreateBookingRequest createBookingRequest) {
		CreateBookingResponse initialValidationsResponse = validateCreateBookingRequest(createBookingRequest);
		if(initialValidationsResponse.getStatus().equals(CommonConstants.Status.FAILURE.name().toString())) {
			return initialValidationsResponse;
		}
		createBookingRequest.setBookingStatus(BookingStatus.INITIATED);
		CreateBookingResponse initiateResponse = cargoDAO.createBooking(createBookingRequest);
		if(initiateResponse.getStatus().equals(CommonConstants.Status.FAILURE.name().toString())) {
			return initiateResponse;
		}
		validateCreateBookingResponse(initiateResponse);
		if(initiateResponse.getStatus().equals(CommonConstants.Status.FAILURE.name().toString())) {
			return initiateResponse;
		}
		Double price = getTotalCost(initiateResponse.getBookingInfo());
		PaymentInfoResp paymentInfo = completeBookingAndGetStatus(initiateResponse.getBookingInfo().getPaymentInfo(), price);
		initiateResponse.getBookingInfo().setStatus(paymentInfo.paymentStatus);
		BaseResponse bookingStatusUpdateResponse = cargoDAO.updateBookingStatus(initiateResponse.getBookingInfo().getBookingInfoId(),paymentInfo.paymentReferenceId ,paymentInfo.paymentStatus);
		initiateResponse.setStatus(bookingStatusUpdateResponse.getStatus());
		initiateResponse.getBookingInfo().setStatus(paymentInfo.paymentStatus);
		initiateResponse.getBookingInfo().setPaymentInfoReferenceId(paymentInfo.paymentReferenceId);
		initiateResponse.setErrorCode(bookingStatusUpdateResponse.getErrorCode());
		initiateResponse.setErrorDescription(bookingStatusUpdateResponse.getErrorDescription());
		return initiateResponse;
	}
	
	@Override 
	public File getBookingInvoice(Integer bookingId){
		CreateBookingResponse bookingResponse = cargoDAO.getBooking(bookingId);
		return generateBookingInvoice(bookingResponse.getBookingInfo());
	}

	@Override
	public PromoCodeResponse getPromoCodes(){
    	return cargoDAO.getPromoCodes();
	}

    @Override
    public CartPriceResponse getCartPrice(GetCartPriceRequest cartPriceRequest) {
		CartPriceResponse cartPriceResponse = new CartPriceResponse();
        Variant variant = cargoDAO.getVariantById(cartPriceRequest.getVariantId()).getVariantList().get(0);
        List<AddOn> addOnList = cargoDAO.getAddOns(cartPriceRequest.getAddOnIds()).getAddOnList();
        CartPrice cartPrice = calculateTripCost(variant, addOnList,cartPriceRequest.getFromDate(),cartPriceRequest.getToDate());
		cartPriceResponse.setCartPrice(cartPrice);
		cartPriceResponse.setSuccessResponse();
        return cartPriceResponse;
    }

	@Override
	public PromoPriceResponse getPromoPrice(GetPromoPriceRequest promoPriceRequest) {
		Double promoPrice = getPromoPrice(promoPriceRequest.getPromo(), promoPriceRequest.getTotalAmount());
		PromoPriceResponse promoPriceResponse = new PromoPriceResponse();
		promoPriceResponse.setSuccessResponse();
		promoPriceResponse.setPromoPrice(promoPrice);
		return promoPriceResponse;
	}


}
