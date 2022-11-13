package com.cargobackend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.cargobackend.pojo.request.*;
import com.cargobackend.pojo.response.*;
import com.cargobackend.pojo.response.common.BaseResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.service.ICargoService;
import com.cargobackend.pojo.dao.cargo.PaymentInfo;
import com.cargobackend.pojo.dao.cargo.UserProfile;
import com.cargobackend.pojo.dao.cargo.Variant;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping({ "/cargo" })
public class CargoController {

    private final ICargoService cargoService;

    public CargoController(ICargoService cargoService) {
        super();
        this.cargoService = cargoService;
    }

    @GetMapping("/getCarMake")
    @ResponseBody
    public CarMakeResponse getCarMake() {
        System.out.println("\n CargoController getCarMake ");
        return cargoService.getCarMakes(null);
    }

    @GetMapping("/location")
    @ResponseBody
    public LocationResponse getLocation() {
        System.out.println("\n CargoController getLocation ");
        return cargoService.getLocation();
    }

    @PostMapping(value = { "/make" })
    @ResponseBody
    public MakeResponse getMake(@RequestBody MakeRequest makeRequest) {
        System.out.println("\n CargoController  getMake " + makeRequest);
        return cargoService.getMake(makeRequest);
    }

    @PostMapping(value = { "model" })
    @ResponseBody
    public ModelResponse getModel(@RequestBody ModelRequest modelRequest) {
        System.out.println("\n CargoController  getModel " + modelRequest);
        return cargoService.getModel(modelRequest);
    }

    @PostMapping(value = { "bodyType" })
    @ResponseBody
    public BodyTypeResponse getBodyType(@RequestBody BodyTypeRequest bodyTypeReqeust) {
        System.out.println("\n CargoController  getBodyType " + bodyTypeReqeust);
        return cargoService.getBodyType(bodyTypeReqeust);
    }

    @PostMapping(value = { "fuelType" })
    @ResponseBody
    public FuelTypeResponse getFuelType(@RequestBody FuelTypeRequest fuelTypeReqeust) {
        System.out.println("\n CargoController  getFuelType " + fuelTypeReqeust);
        return cargoService.getFuelType(fuelTypeReqeust);
    }

    @PostMapping(value = { "transmissionType" })
    @ResponseBody
    public TransmissionTypeResponse getTransmissionTypeReqeust(
            @RequestBody TransmissionTypeRequest transmissionTypeReqeust) {
        System.out.println("\n CargoController  getTransmissionTypeReqeust " + transmissionTypeReqeust);
        return cargoService.getTransmissionType(transmissionTypeReqeust);
    }

    @PostMapping(value = { "color" })
    @ResponseBody
    public ColorResponse getColor(@RequestBody ColorRequest colorReqeust) {
        System.out.println("\n CargoController  getColor " + colorReqeust);
        return cargoService.getColor(colorReqeust);
    }

    @PostMapping(value = { "variant" })
    @ResponseBody
    public VariantResponse getVariant(@RequestBody VariantRequest variantRequest) {
        System.out.println("\n CargoController  getVariant " + variantRequest);
        return cargoService.getVariant(variantRequest);
    }

    @GetMapping(value = { "/variant_by_id/{id}" })
    @ResponseBody
    public VariantResponse getVariantById(@PathVariable Integer id) {
        System.out.println("\n CargoController  getVariantById " + id);
        return cargoService.getVariantById(id);
    }

    @GetMapping(value = { "/add_on" })
    @ResponseBody
    public AddOnResponse getAddOns() {
        System.out.println("\n CargoController  getAddOns ");
        return cargoService.getAddOns();
    }

    @GetMapping(value = { "/user_profile/{userId}" })
    @ResponseBody
    public UserProfileResponse getUserProfiles(@PathVariable Integer userId) {
        System.out.println("\n CargoController  getUserProfiles " + userId);
        return cargoService.getUserProfiles(userId);
    }

    @GetMapping(value = { "/payment_info/{userId}" })
    @ResponseBody
    public PaymentInfoResponse getPaymentInfo(@PathVariable Integer userId) {
        System.out.println("\n CargoController  getPaymentInfo " + userId);
        return cargoService.getPaymentInfo(userId);
    }

    @PostMapping(value = { "/payment_info" })
    @ResponseBody
    public PaymentInfoResponse addPaymentInfo(@RequestBody PaymentInfo paymentInfo) {
        System.out.println("\n CargoController  addPaymentInfo " + paymentInfo);
        return cargoService.addPaymentInfo(paymentInfo);
    }

    @PostMapping(value = { "/user_profile" })
    @ResponseBody
    public UserProfileResponse addUserProfile(@RequestBody UserProfile userProfile) {
        System.out.println("\n CargoController  addUserProfile " + userProfile);
        return cargoService.addUserProfile(userProfile);
    }

    @PostMapping(value = { "/add_to_cart" })
    @ResponseBody
    public AddToCartResponse addToCart(@RequestBody AddToCartRequest addToCartRequest) {
        System.out.println("\n CargoController  addToCart " + addToCartRequest);
        return cargoService.addToCart(addToCartRequest);
    }

    @PostMapping(value = { "/create_booking" })
    @ResponseBody
    public CreateBookingResponse createBooking(@RequestBody CreateBookingRequest createBookingRequest) {
        System.out.println("\n CargoController  addToCart " + createBookingRequest);
        return cargoService.createBooking(createBookingRequest);
    }

    @GetMapping(value = { "/booking_invoice/{bookingId}" })
    @ResponseBody
    public ResponseEntity<Resource> getBookingInvoice(@PathVariable Integer bookingId) throws IOException {
        System.out.println("\n CargoController  addToCart " + bookingId);
        File file = cargoService.getBookingInvoice(bookingId);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                // .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping(value = { "/promo_code" })
    @ResponseBody
    public PromoCodeResponse getPromoCodes() {
        System.out.println("\n CargoController  getPromoCodes ");
        return cargoService.getPromoCodes();
    }

    @PostMapping(value = { "/cart_price" })
    @ResponseBody
    public CartPriceResponse getCartPrice(@RequestBody GetCartPriceRequest cartPriceRequest) {
        System.out.println("\n CargoController  cartPriceRequest ");
        return cargoService.getCartPrice(cartPriceRequest);
    }

    @PostMapping(value = { "/promo_price" })
    @ResponseBody
    public PromoPriceResponse getPromoCodes(@RequestBody GetPromoPriceRequest promoPriceRequest) {
        System.out.println("\n CargoController  promoPriceRequest ");
        return cargoService.getPromoPrice(promoPriceRequest);
    }

    @GetMapping(value = { "/booking_history/{userId}" })
    @ResponseBody
    public BookingHistoryResponse getPromoCodes(@PathVariable Integer userId) {
        System.out.println("\n CargoController  getPromoCodes ");
        return cargoService.getBookingHistory(userId);
    }

    @PostMapping(value = { "/update_booking" })
    @ResponseBody
    public CreateBookingResponse updateBooking(@RequestBody UpdateBookingRequest updateBookingRequest) {
        System.out.println("\n CargoController  updateBookingRequest ");
        return cargoService.getUpdatedBookingInfo(updateBookingRequest);
    }

    @PostMapping(value = { "/update_booking_persist" })
    @ResponseBody
    public CreateBookingResponse updateBookingPersist(@RequestBody UpdateBookingRequest updateBookingRequest) {
        System.out.println("\n CargoController  updateBookingRequest ");
        return cargoService.getUpdatedBookingInfoAndPersist(updateBookingRequest);
    }

    @GetMapping(value = { "/cancel_booking/{bookingInfoId}" })
    @ResponseBody
    public CreateBookingResponse cancelBooking(@PathVariable Integer bookingInfoId) {
        System.out.println("\n CargoController  cancelBooking ");
        return cargoService.cancelBooking(bookingInfoId);
    }

    @PostMapping(value = { "/find_booking" })
    @ResponseBody
    public CreateBookingResponse findBooking(@RequestBody FindBookingRequest findBookingRequest) {
        System.out.println("\n CargoController  findBooking ");
        return cargoService.findBooking(findBookingRequest);
    }

    @GetMapping(value = { "/booking/{bookingInfoId}" })
    @ResponseBody
    public CreateBookingResponse getBooking(@PathVariable Integer bookingInfoId) {
        System.out.println("\n CargoController  getBooking ");
        return cargoService.getBooking(bookingInfoId);
    }

    @PostMapping(value = { "/finish_booking" })
    @ResponseBody
    public BaseResponse getBooking(@RequestBody FinishBookingRequest finishBookingRequest) {
        System.out.println("\n CargoController  finishBookingRequest " + finishBookingRequest);
        return cargoService.finishBooking(finishBookingRequest);
    }

    @DeleteMapping(value = { "/delete_variant/{variantId}" })
    @ResponseBody
    public BaseResponse deleteVaraint(@PathVariable Integer variantId) {
        System.out.println("\n CargoController  deleteVaraint ");
        return cargoService.deleteVariant(variantId);
    }

    @PostMapping(value = { "variant_s" })
    @ResponseBody
    public VariantResponse getVariantS(@RequestBody VariantRequest variantRequest) {
        System.out.println("\n CargoController  getVariantS " + variantRequest);
        return cargoService.getVariantS(variantRequest);
    }

    @PostMapping(value = { "create_variant" })
    @ResponseBody
    public VariantResponse createVariant(@RequestBody CreateVariantRequest createVariantRequest) {
        System.out.println("\n CargoController  CreateVariantRequest " + createVariantRequest);
        return cargoService.createVariant(createVariantRequest);
    }

}
