package hotel;


import hotel.checkout.CheckoutCTL;
import hotel.credit.CreditCardType;
import hotel.entities.*;
import hotel.service.RecordServiceCTL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;



public class Bug1Replication {

    RecordServiceCTL TestRecordServiceCTL;
    CheckoutCTL testCheckoutCTL;
    Hotel testHotel;
    CreditCardType testCreditCardType;

    int testRoomId;
    int testCardNumber;
    int TestCcv;



    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testRoomId = 301;
        testCreditCardType = CreditCardType.VISA;
        testCardNumber = 2;
        TestCcv = 2;
        try {
            testHotel = HotelHelper.loadHotel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        testCheckoutCTL = new CheckoutCTL(testHotel);
    }


    @AfterEach
    void tearDown() {
    }


    @Test
    void replicateBug1() {
        //arrange
        TestRecordServiceCTL = new RecordServiceCTL(testHotel);
        TestRecordServiceCTL.roomNumberEntered(testRoomId);
        TestRecordServiceCTL.serviceDetailsEntered(ServiceType.BAR_FRIDGE, 10.00);
        TestRecordServiceCTL = new RecordServiceCTL(testHotel);
        TestRecordServiceCTL.roomNumberEntered(testRoomId);
        TestRecordServiceCTL.serviceDetailsEntered(ServiceType.RESTAURANT, 20.00);
        TestRecordServiceCTL = new RecordServiceCTL(testHotel);
        TestRecordServiceCTL.roomNumberEntered(testRoomId);
        TestRecordServiceCTL.serviceDetailsEntered(ServiceType.ROOM_SERVICE, 30.00);
        testCheckoutCTL.setStateToRoom();

        //act
        testCheckoutCTL.roomIdEntered(testRoomId);
        testCheckoutCTL.chargesAccepted(true);
        testCheckoutCTL.creditDetailsEntered(testCreditCardType,testCardNumber,TestCcv);
    }

}