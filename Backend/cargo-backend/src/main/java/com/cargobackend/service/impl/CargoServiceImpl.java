package com.cargobackend.service.impl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.cargobackend.pojo.dao.cargo.*;
import com.cargobackend.pojo.request.*;
import com.cargobackend.pojo.response.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import com.cargobackend.dao.ICargoDAO;
import com.cargobackend.dao.IUserDAO;
import com.cargobackend.pojo.dao.cargo.AddOn.AddOnComputeStrategy;
import com.cargobackend.pojo.dao.cargo.BookingInfo.BookingStatus;
import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.pojo.response.InvoiceRequest.CustomField;
import com.cargobackend.pojo.response.InvoiceRequest.Item;
import com.cargobackend.pojo.response.common.BaseResponse;
import com.cargobackend.service.ICargoService;
import com.cargobackend.utils.CommonConstants;
import com.google.gson.Gson;

@Service
public class CargoServiceImpl implements ICargoService {

	private final ICargoDAO cargoDAO;
	private final IUserDAO userDAO;

	private class PaymentInfoResp {
		String paymentReferenceId;
		BookingStatus paymentStatus;

		PaymentInfoResp(String paymentReferenceId, BookingStatus paymentStatus) {
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
		System.out.println("In getCarMakes status......" + status);
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
	public PaymentInfoResponse addPaymentInfo(PaymentInfo paymentInfo) {
		// TODO Auto-generated method stub
		return cargoDAO.addPaymentInfo(paymentInfo);
	}

	@Override
	public UserProfileResponse addUserProfile(UserProfile userProfile) {
		// TODO Auto-generated method stub
		return cargoDAO.addUserProfile(userProfile);
	}

	public CartPrice calculateTripCost(Variant variant, List<AddOn> addOns, Timestamp fromTimeStamp,
			Timestamp toTimestamp) {
		Double variantPrice = Double.valueOf(variant.getVariantPricePerKm());
		int nDays = tripDurationInDays(fromTimeStamp, toTimestamp);
		System.out.println("\n tripDurationInDays ........nDays " + nDays);
		if (nDays <= 0) {
			nDays = 1;
		}
		CartPrice cartPrice = new CartPrice();
		variantPrice = nDays * variantPrice;
		Double tripPrice = variantPrice;
		System.out.println("\n AddOnList ........nDays " + addOns);
		List<AddOnPrice> addOnPriceList = new ArrayList<>();
		if (addOns != null) {
			for (AddOn addOn : addOns) {
				Double addOnPrice = 0.0;
				if (AddOnComputeStrategy.FIXED.toString()
						.equalsIgnoreCase(addOn.getAddOnComputeStrategy().toString())) {
					addOnPrice = addOn.getAddOnValue();
				} else if (AddOnComputeStrategy.PER_DAY.toString()
						.equalsIgnoreCase(addOn.getAddOnComputeStrategy().toString())) {
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
		System.out.println("\n tripPrice ........tripPrice " + tripPrice);
		System.out.println("\n tripPrice ........final tripPrice " + tripPrice);
		cartPrice.setPrice(tripPrice);
		return cartPrice;
	}

	public Integer tripDurationInDays(Timestamp fromTimeStamp, Timestamp toTimestamp) {
		long diff = toTimestamp.getTime() - fromTimeStamp.getTime();
		long nDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return (int) nDays;
	}

	@Override
	public AddToCartResponse addToCart(AddToCartRequest addToCartRequest) {
		AddToCartResponse addToCartErrorResponse = new AddToCartResponse();
		addToCartErrorResponse.setFailedResponse();
		List<Integer> addOnIds = addToCartRequest.getAddOnIds();
		List<AddOn> addOns = (addOnIds == null || addOnIds.isEmpty()) ? new ArrayList<>()
				: cargoDAO.getAddOns(addOnIds).getAddOnList();
		List<Variant> variantList = cargoDAO.getVariantById(addToCartRequest.getVariantId()).getVariantList();
		Variant variant = null;
		if (variantList != null && variantList.size() > 0) {
			variant = variantList.get(0);
		}
		if (variant == null) {
			addToCartErrorResponse.setErrorCode("404");
			addToCartErrorResponse.setErrorDescription("Variant Not Found");
			return addToCartErrorResponse;
		}
		CartPrice cartPrice = calculateTripCost(variant, addOns, addToCartRequest.getFromDate(),
				addToCartRequest.getToDate());
		Double tripCost = cartPrice.getPrice();
		addToCartRequest.setPrice(tripCost);
		if (addToCartRequest.getUserId() == null) {
			UserDetails userDetails = new UserDetails();
			UUID uuid = UUID.randomUUID();
			userDetails.setUserName("Guest" + uuid.toString().substring(0, 13).replace("-", ""));
			userDetails.setUserPassword("Pass1234");
			userDetails.setUserType("GUEST");
			UserDetailsResponse usd = userDAO.registerUser(userDetails);
			addToCartRequest.setUserId(usd.getUserDetails().getUserId());
		}
		AddToCartResponse addToCartResponse = cargoDAO.addToCart(addToCartRequest);
		if (addToCartResponse.isSuccess()) {
			addToCartResponse.getCartEntry().setCartPrice(cartPrice);
		}
		return addToCartResponse;

	}

	public CreateBookingResponse validateCreateBookingRequest(CreateBookingRequest createBookingRequest) {
		CreateBookingResponse initialValidationsResponse = new CreateBookingResponse();
		if (createBookingRequest.getUserId() == null) {
			initialValidationsResponse.setErrorCode("404");
			initialValidationsResponse.setErrorDescription("UserId Not Found");
			initialValidationsResponse.setFailedResponse();
			return initialValidationsResponse;
		}
		if (createBookingRequest.getCartIds() == null || createBookingRequest.getCartIds().isEmpty()) {
			initialValidationsResponse.setErrorCode("404");
			initialValidationsResponse.setErrorDescription("No Cart Details For Booking");
			initialValidationsResponse.setFailedResponse();
			return initialValidationsResponse;
		}
		if (createBookingRequest.getUserProfileIds() == null
				|| createBookingRequest.getCartIds().size() != createBookingRequest.getUserProfileIds().size()) {
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
		if (bookingInfo.getUser() == null) {
			postValidationsResponse.setErrorCode("404");
			postValidationsResponse.setErrorDescription("UserId Not Found");
			postValidationsResponse.setFailedResponse();
			return postValidationsResponse;
		}
		if (bookingInfo.getCartList() == null || bookingInfo.getCartList().isEmpty()) {
			postValidationsResponse.setErrorCode("404");
			postValidationsResponse.setErrorDescription("No Cart Details For Booking");
			postValidationsResponse.setFailedResponse();
			return postValidationsResponse;
		}
		postValidationsResponse.setSuccessResponse();
		return postValidationsResponse;
	}

	public PaymentInfoResp completeBookingAndGetStatus(PaymentInfo paymentInfo, Double price) {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = "pr" + uuid.toString().substring(0, 18);
		return new PaymentInfoResp(uuidAsString, BookingStatus.SUCCESS);
	}

	public Double getPromoPrice(PromoCode promoCode, Double baseAmount) {
		if (promoCode.getPromoCodeType() == PromoCode.PromoCodeType.FLAT) {
			return promoCode.getPromoValue();
		} else if (promoCode.getPromoCodeType() == PromoCode.PromoCodeType.PERCENTAGE) {
			return (promoCode.getPromoValue() * baseAmount) / 100;
		}
		return 0.0;
	}

	public Double getTotalCost(BookingInfo bookingInfo) {
		Double allCarsPrice = bookingInfo.getCartList().stream()
				.map(cart -> (cart.getPrice() != null ? cart.getPrice() : 0.0)).mapToDouble(Double::doubleValue).sum();
		Double promoCodePrice = bookingInfo.getPromoCodeList().stream()
				.map(promoCode -> (getPromoPrice(promoCode, allCarsPrice))).mapToDouble(Double::doubleValue).sum();
		return (allCarsPrice - promoCodePrice);
	}

	public String nullToString(String a) {
		return a == null ? "" : a;
	}

	public static String formatDateToString(Date date, String format,
											String timeZone) {
		// null check
		if (date == null) return null;
		// create SimpleDateFormat object with input format
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// default system timezone if passed null or empty
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}
		// set timezone to SimpleDateFormat
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		// return Date in required format with timezone as String
		return sdf.format(date);
	}

	public File generateBookingInvoice(BookingInfo bookingInfo) {

		System.out.print("\n generateBookingInvoice \n"+bookingInfo);
		Gson gson = new Gson();
		UserDetails user = bookingInfo.getUser();
		String userInfo = user.getUserName() + "\n" + nullToString(user.getUserMobileNumber()) + "\n"
				+ nullToString(user.getUserEmail());

		InvoiceRequest invoice = new InvoiceRequest();
		invoice.setFrom("Cargo, Inc");
		invoice.setTo(userInfo);
		invoice.setDate(formatDateToString(new Date(), "dd MMM yyyy hh:mm:ss a", "EST") + " EST");
		List<CustomField> customFields = new ArrayList<>();

		CustomField bookingReferenceId = new CustomField();
		bookingReferenceId.setName("Booking Reference Id");
		bookingReferenceId.setValue("cargo" + bookingInfo.getBookingInfoId().toString());
		customFields.add(bookingReferenceId);

		CustomField paymentReferenceId = new CustomField();
		paymentReferenceId.setName("Payment Reference Id");
		paymentReferenceId.setValue(
				bookingInfo.getPaymentInfoReferenceId() != null ? bookingInfo.getPaymentInfoReferenceId() : "");
		customFields.add(paymentReferenceId);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");

		if (bookingInfo.getCartList().size() > 0) {
			CustomField bookingDateInfo = new CustomField();
			bookingDateInfo.setName("Booking Dates");
			Timestamp fromDate = bookingInfo.getCartList().get(0).getFromDate();
			Timestamp toDate = bookingInfo.getCartList().get(0).getToDate();
			bookingDateInfo.setValue(formatter.format(fromDate) + " to " + formatter.format(toDate));
			customFields.add(bookingDateInfo);
		}

		CustomField bookingStatus = new CustomField();
		bookingStatus.setName("Booking Status");
		bookingStatus.setValue(bookingInfo.getStatus() != null ? bookingInfo.getStatus().name() : "");
		customFields.add(bookingStatus);

		Double allCarsPrice = bookingInfo.getCartList().stream()
				.map(cart -> (cart.getPrice() != null ? cart.getPrice() : 0.0)).mapToDouble(Double::doubleValue).sum();
		Double promoCodePrice = bookingInfo.getPromoCodeList().stream()
				.map(promoCode -> (getPromoPrice(promoCode, allCarsPrice))).mapToDouble(Double::doubleValue).sum();
		System.out.print(promoCodePrice);
		invoice.setCustom_fields(customFields);
		Double total = allCarsPrice - promoCodePrice;
		Double tax = (0.1 * total);
		Double totalAmountPaid = total + tax;
		invoice.setAmount_paid(totalAmountPaid.toString());
		invoice.setTax(String.valueOf(10));
		invoice.setDiscounts(promoCodePrice);

		System.out.println("total :: " + total);
		System.out.println("totalAmountPaid :: " + totalAmountPaid);
		System.out.println("allCarsPrice :: " + allCarsPrice);
		System.out.println("promoCodePrice :: " + promoCodePrice);
		System.out.println("tax :: " + tax);

		InvoiceRequest.Field f = new InvoiceRequest.Field("%", true, false);

		invoice.setField(f);

		List<Item> itemList = new ArrayList<Item>();

		for (CartEntry cartEntry : bookingInfo.getCartList()) {
			Item carInfo = new Item();
			Variant variant = cartEntry.getVariant();
			carInfo.setName(variant.getModel().getMake().getMakeName() + "   " + variant.getModel().getModelName()
					+ "   " + variant.getVariantName());
			carInfo.setQuantity("1");
			carInfo.setUnit_cost(cartEntry.getPrice().toString());
			List<AddOnPrice> addOnPrices = cartEntry.getCartPrice().getAddOnPrices();
			List<AddOn> addOnList = cartEntry.getAddOnList();
			String description = "";
			for (int i = 1; i <= addOnPrices.size(); i++) {
				AddOnPrice addOnPrice = addOnPrices.get(i - 1);
				description = description + String.valueOf(i) + " " + addOnPrice.getAddOn().getAddOnName() + "  "
						+ addOnPrice.getPrice() + "\n";
			}
			carInfo.setDescription(description);
			itemList.add(carInfo);
		}
		invoice.setItems(itemList);

		HttpURLConnection con = null;

		String url = "https://invoice-generator.com";

		System.out.println(gson.toJson(invoice).toString());

		byte[] postData = gson.toJson(invoice).getBytes(StandardCharsets.UTF_8);

		// FileOutputStream outS = new FileOutputStream("Invoice.pdf");
		File file = new File("cargo" + bookingInfo.getBookingInfoId().toString() + "_invoice.pdf");

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
		if (initialValidationsResponse.getStatus().equals(CommonConstants.Status.FAILURE.name().toString())) {
			return initialValidationsResponse;
		}
		createBookingRequest.setBookingStatus(BookingStatus.INITIATED);
		CreateBookingResponse initiateResponse = cargoDAO.createBooking(createBookingRequest);
		if (initiateResponse.getStatus().equals(CommonConstants.Status.FAILURE.name().toString())) {
			return initiateResponse;
		}
		validateCreateBookingResponse(initiateResponse);
		if (initiateResponse.getStatus().equals(CommonConstants.Status.FAILURE.name().toString())) {
			return initiateResponse;
		}
		Double price = getTotalCost(initiateResponse.getBookingInfo());
		PaymentInfoResp paymentInfo = completeBookingAndGetStatus(initiateResponse.getBookingInfo().getPaymentInfo(),
				price);
		initiateResponse.getBookingInfo().setStatus(paymentInfo.paymentStatus);
		BaseResponse bookingStatusUpdateResponse = cargoDAO.updateBookingStatus(
				initiateResponse.getBookingInfo().getBookingInfoId(), paymentInfo.paymentReferenceId,
				paymentInfo.paymentStatus);
		initiateResponse.setStatus(bookingStatusUpdateResponse.getStatus());
		initiateResponse.getBookingInfo().setStatus(paymentInfo.paymentStatus);
		initiateResponse.getBookingInfo().setPaymentInfoReferenceId(paymentInfo.paymentReferenceId);
		initiateResponse.setErrorCode(bookingStatusUpdateResponse.getErrorCode());
		initiateResponse.setErrorDescription(bookingStatusUpdateResponse.getErrorDescription());
		return initiateResponse;
	}

	@Override
	public File getBookingInvoice(Integer bookingId) {
		CreateBookingResponse bookingResponse = cargoDAO.getBooking(bookingId);
		BookingInfo bookingInfo = bookingResponse.getBookingInfo();
		Double totalPrice = 0.0;
		for (CartEntry cartEntry : bookingInfo.getCartList()) {
			CartPrice cartPrice = calculateTripCost(cartEntry.getVariant(), cartEntry.getAddOnList(),
					cartEntry.getFromDate(), cartEntry.getToDate());
			cartEntry.setCartPrice(cartPrice);
			totalPrice += cartPrice.getPrice();
		}
		Double totalPromoPrice = 0.0;
		for (PromoCode promoCode : bookingInfo.getPromoCodeList()) {
			Double promoPrice = getPromoPrice(promoCode, totalPrice);
			promoCode.setPromoPrice(promoPrice);
			totalPromoPrice += promoPrice;
		}
		bookingInfo.setTotalPrice(totalPromoPrice > totalPrice ? 0.0 : totalPrice - totalPromoPrice);
		bookingInfo.setPromoPrice(totalPromoPrice);
		return generateBookingInvoice(bookingInfo);
	}

	@Override
	public PromoCodeResponse getPromoCodes() {
		return cargoDAO.getPromoCodes();
	}

	@Override
	public CartPriceResponse getCartPrice(GetCartPriceRequest cartPriceRequest) {
		CartPriceResponse cartPriceResponse = new CartPriceResponse();
		Variant variant = cargoDAO.getVariantById(cartPriceRequest.getVariantId()).getVariantList().get(0);
		List<AddOn> addOnList = cargoDAO.getAddOns(cartPriceRequest.getAddOnIds()).getAddOnList();
		CartPrice cartPrice = calculateTripCost(variant, addOnList, cartPriceRequest.getFromDate(),
				cartPriceRequest.getToDate());
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

	@Override
	public BookingHistoryResponse getBookingHistory(Integer userId) {
		BookingHistoryResponse bookingHistoryResponse = cargoDAO.getBookingHistory(userId);
		if (bookingHistoryResponse.getBookingInfoList() != null) {
			for (BookingInfo bookingInfo : bookingHistoryResponse.getBookingInfoList()) {
				Double totalPrice = 0.0;
				for (CartEntry cartEntry : bookingInfo.getCartList()) {
					CartPrice cartPrice = calculateTripCost(cartEntry.getVariant(), cartEntry.getAddOnList(),
							cartEntry.getFromDate(), cartEntry.getToDate());
					cartEntry.setCartPrice(cartPrice);
					totalPrice += cartPrice.getPrice();
				}
				Double totalPromoPrice = 0.0;
				for (PromoCode promoCode : bookingInfo.getPromoCodeList()) {
					Double promoPrice = getPromoPrice(promoCode, totalPrice);
					promoCode.setPromoPrice(promoPrice);
					totalPromoPrice += promoPrice;
				}
				bookingInfo.setTotalPrice(totalPromoPrice > totalPrice ? 0.0 : totalPrice - totalPromoPrice);
				bookingInfo.setPromoPrice(totalPromoPrice);
			}
		}
		return bookingHistoryResponse;
	}

	@Override
	public CreateBookingResponse getUpdatedBookingInfo(UpdateBookingRequest bookingRequest) {
		CreateBookingResponse bookingResponse = cargoDAO.getBooking(bookingRequest.getBookingInfoId());
		if (bookingResponse.getBookingInfo() != null) {
			BookingInfo bookingInfo = bookingResponse.getBookingInfo();
			Double totalPrice = 0.0;
			for (CartEntry cartEntry : bookingInfo.getCartList()) {
				cartEntry.setFromDate(bookingRequest.getFromDate());
				cartEntry.setToDate(bookingRequest.getToDate());
				CartPrice cartPrice = calculateTripCost(cartEntry.getVariant(), cartEntry.getAddOnList(),
						cartEntry.getFromDate(), cartEntry.getToDate());
				cartEntry.setCartPrice(cartPrice);
				cartEntry.setPrice(cartPrice.getPrice());
				totalPrice += cartPrice.getPrice();
			}
			Double totalPromoPrice = 0.0;
			for (PromoCode promoCode : bookingInfo.getPromoCodeList()) {
				Double promoPrice = getPromoPrice(promoCode, totalPrice);
				promoCode.setPromoPrice(promoPrice);
				totalPromoPrice += promoPrice;
			}
			bookingInfo.setTotalPrice(totalPromoPrice > totalPrice ? 0.0 : totalPrice - totalPromoPrice);
			bookingInfo.setPromoPrice(totalPromoPrice);
		}
		return bookingResponse;
	}

	@Override
	public CreateBookingResponse getUpdatedBookingInfoAndPersist(UpdateBookingRequest updateBookingRequest) {
		CreateBookingResponse bookingResponse = getUpdatedBookingInfo(updateBookingRequest);
		List<Integer> cartList = new ArrayList<>();
		List<Double> priceList = new ArrayList<>();
		for (CartEntry cartEntry : bookingResponse.getBookingInfo().getCartList()) {
			cartList.add(cartEntry.getCartId());
			priceList.add(cartEntry.getPrice());
		}
		BaseResponse updateResponse = cargoDAO.updateCartDateInfo(cartList, priceList,
				updateBookingRequest.getFromDate(), updateBookingRequest.getToDate());
		if (!updateResponse.isSuccess()) {
			bookingResponse.setFailedResponse();
			bookingResponse.setErrorCode(updateResponse.getErrorCode());
			bookingResponse.setErrorDescription(updateResponse.getErrorDescription());
			bookingResponse.setStatus(updateResponse.getStatus());
		}
		return bookingResponse;
	}

	@Override
	public CreateBookingResponse cancelBooking(Integer bookingInfoId) {
		CreateBookingResponse bookingResponse = cargoDAO.getBooking(bookingInfoId);
		if (bookingResponse.getBookingInfo() != null) {
			BaseResponse updateResponse = cargoDAO.updateBookingStatus(
					bookingResponse.getBookingInfo().getBookingInfoId(),
					bookingResponse.getBookingInfo().getPaymentInfoReferenceId(), BookingStatus.CANCELLED);
			if (!updateResponse.isSuccess()) {
				bookingResponse.setFailedResponse();
				bookingResponse.setErrorCode(updateResponse.getErrorCode());
				bookingResponse.setErrorDescription(updateResponse.getErrorDescription());
				bookingResponse.setStatus(updateResponse.getStatus());
			} else {
				bookingResponse.getBookingInfo().setStatus(BookingStatus.CANCELLED);
			}
		}
		return bookingResponse;
	}

	public boolean verifyLicenceNumber(List<CartEntry> cartList, String licenseLast4Digits) {
		for (CartEntry cartEntry : cartList) {
			UserProfile userProfile = cartEntry.getUserProfile();
			if (userProfile != null) {
				if (userProfile.getLicenceNumber().endsWith(licenseLast4Digits)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public CreateBookingResponse findBooking(FindBookingRequest findBookingRequest) {
		CreateBookingResponse bookingResponse = cargoDAO.getBooking(findBookingRequest.getBookingInfoId());
		if (bookingResponse.getBookingInfo() != null) {
			BookingInfo bookingInfo = bookingResponse.getBookingInfo();
			if (bookingInfo.getUser().getUserType() == null || !bookingInfo.getUser().getUserType().equals("GUEST")) {
				bookingResponse.setBookingInfo(null);
				bookingResponse.setFailedResponse();
				bookingResponse.setErrorCode("400");
				bookingResponse.setErrorDescription("Please login to cancel this booking");
				bookingResponse.setStatus("Bad Request");
				return bookingResponse;
			}

			if (findBookingRequest.getLicenseLast4Digits() == null
					|| findBookingRequest.getLicenseLast4Digits().length() < 4) {
				bookingResponse.setBookingInfo(null);
				bookingResponse.setFailedResponse();
				bookingResponse.setErrorCode("400");
				bookingResponse.setErrorDescription("Please add atleast 4 digits for license number verification");
				bookingResponse.setStatus("Bad Request");
				return bookingResponse;
			}

			if (!verifyLicenceNumber(bookingInfo.getCartList(), findBookingRequest.getLicenseLast4Digits())) {
				bookingResponse.setBookingInfo(null);
				bookingResponse.setFailedResponse();
				bookingResponse.setErrorCode("400");
				bookingResponse.setErrorDescription("Invalid Licence Number");
				bookingResponse.setStatus("Bad Request");
				return bookingResponse;
			}
		}
		return bookingResponse;
	}

	@Override
	public CreateBookingResponse getBooking(Integer bookingInfoId) {
		CreateBookingResponse bookingResponse = cargoDAO.getBooking(bookingInfoId);
		return bookingResponse;
	}

	@Override
	public BaseResponse finishBooking(FinishBookingRequest finishBookingRequest) {
		BookingInfo bookingInfo = cargoDAO.getBooking(finishBookingRequest.getBookingId()).getBookingInfo();
		UpdateBookingRequest updateBookingRequest = new UpdateBookingRequest();
		updateBookingRequest.setBookingInfoId(bookingInfo.getBookingInfoId());
		updateBookingRequest.setFromDate(bookingInfo.getCartList().get(0).getFromDate());
		updateBookingRequest.setToDate(finishBookingRequest.getClosingDate());
		CreateBookingResponse bookingResponse = getUpdatedBookingInfo(updateBookingRequest);
		List<Integer> cartList = new ArrayList<>();
		List<Double> priceList = new ArrayList<>();
		for (CartEntry cartEntry : bookingResponse.getBookingInfo().getCartList()) {
			cartList.add(cartEntry.getCartId());
			priceList.add(cartEntry.getPrice());
		}
		System.out.println("updateBookingRequest :: " + updateBookingRequest);
		BaseResponse updateResponse = cargoDAO.updateCartDateInfo(cartList, priceList,
				updateBookingRequest.getFromDate(), updateBookingRequest.getToDate());
		return cargoDAO.finishBooking(finishBookingRequest);
	}

	@Override
	public BaseResponse deleteVariant(Integer variantId) {
		return cargoDAO.deleteVariant(variantId);
	}

	@Override
	public VariantResponse getVariantS(VariantRequest variantRequest) {
		return cargoDAO.getVariantS(variantRequest);
	}

	@Override
	public VariantResponse createVariant(CreateVariantRequest createVariantRequest) {
		int index = 0;
		List<VariantImageInfo> imagePathList = new ArrayList<>();
		for (VariantImageInfo base64IamgeInfo : createVariantRequest.getImageList()) {
			// byte[] data = Base64.decodeBase64(base64Iamge);
			// try (OutputStream stream = new FileOutputStream("../" + String.valueOf(index)
			// + ".bmp")) {
			// stream.write(data);
			// }
			VariantImageInfo newOne = new VariantImageInfo();
			String base64Iamge = base64IamgeInfo.getImageUri();
			newOne.setImageType(base64IamgeInfo.getImageType());
			try {
				String imageFileDir = "assets/" + createVariantRequest.getVariantName() + "/"
						+ createVariantRequest.getNumberPlate();
				String imageFileDirForDB = ".//assets//" + createVariantRequest.getVariantName() + "//"
						+ createVariantRequest.getNumberPlate()+"//"+String.valueOf(index)+".jpg";
				String frontEndPrefix = "../../cargo-frontend/src/";
				new File(frontEndPrefix + imageFileDir).mkdirs();
				String base64Image = base64Iamge.split(",")[1];
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
				Path destinationFile = Paths.get(frontEndPrefix + imageFileDir, String.valueOf(index) + ".jpg");
				Files.write(destinationFile, imageBytes);
				newOne.setImageUri(imageFileDirForDB);
				imagePathList.add(newOne);
			} catch (Exception e) {
				e.printStackTrace();
			}
			index++;
		}
		createVariantRequest.setImageList(imagePathList);
		System.out.println("\n createVariantRequest *******************************************"+createVariantRequest);
		return cargoDAO.createVariant(createVariantRequest);
	}

}
