package RestBooker.testcases;

import RestBooker.Utilities.DataUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RetrieveBookingsTest {
    private int bookingID;
    @Test(description = "verify creating a new booking, status coede should be 200")
    public void TC_RestfulBooker_01(){
        Response res =
        given()
                .baseUri(DataUtils.getEnvironmentPropertyValue("baseURL"))
                .header("Content-Type","application/json")
                .body(DataUtils.getNewBookingDataFile())
        .when()
                .post(DataUtils.getEnvironmentPropertyValue("bookingEndPoint"))
        .then()
                .log().body()
                .assertThat()
                .statusCode(200)
                .body("booking.firstname",equalTo("Hossam"))
                .extract().response();
        bookingID = res.path("bookingid");
        System.out.println("bookingid extracted is "+bookingID);
    }
    @Test(description = "verify retrieving a specific booking, the reponse data is correct ")
    public void TC_RestfulBooker_02(){
        given()
                .baseUri(DataUtils.getEnvironmentPropertyValue("baseURL"))
                .header("Content-Type","application/json")
                .body(DataUtils.getNewBookingDataFile())
                .when()
                .get("/booking/"+String.valueOf(bookingID))
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("firstname",equalTo("Hossam"));
    }
}
