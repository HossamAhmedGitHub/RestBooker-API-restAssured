package RestBooker.testcases;

import RestBooker.Utilities.DataUtils;
import RestBooker.Utilities.Utils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteBookingsTest {
    private String authToken;
    @Test (description = "getting accesstoken for authentication")
    public void getToken(){
        Response responseData =
                given()
                        .baseUri(DataUtils.getEnvironmentPropertyValue("baseURL"))
                        .header("Content-Type","application/json")
                        .body(DataUtils.getAuthBodyDataFile())
                        .when()
                        .post(DataUtils.getEnvironmentPropertyValue("authEndPoint"))
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .extract().response();
        authToken = responseData.path("token");
        System.out.println("authToken===="+authToken);
    }//end getToken()

    @Test( description = "verify delete without authentication, error code is returned")
    public void TCRestfulBooker_06(){
        given()
                .baseUri(DataUtils.getEnvironmentPropertyValue("baseURL"))
                .header("Content-Type", "application/json")
                .when()
                .delete(DataUtils.getEnvironmentPropertyValue("bookingEndPoint")+"/"+ String.valueOf(Utils.getRandomNumber()))
                .then()
                .log().all()
                .assertThat()
                .statusCode(403)
                .body(equalTo("Forbidden"));
    }//end TCRestfulBooker_06()

    @Test( dependsOnMethods ="getToken", description = "verify delete with authentication, 200 code is returned and success messgae is returned")
    public void TCRestfulBooker_07(){
        given()
                .baseUri(DataUtils.getEnvironmentPropertyValue("baseURL"))
                .header("Content-Type", "application/json")
                .header("Cookie","token="+authToken)
                .when()
                .delete(DataUtils.getEnvironmentPropertyValue("bookingEndPoint")+"/1")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body(equalTo("Created"));
    }//end TCRestfulBooker_07()

    @Test( dependsOnMethods ="getToken", description = "verify delete a non existed booking with authentication, error code is returned")
    public void TCRestfulBooker_08(){
        given()
                .baseUri(DataUtils.getEnvironmentPropertyValue("baseURL"))
                .header("Content-Type", "application/json")
                .header("Cookie","token="+authToken)
                .when()
                .delete(DataUtils.getEnvironmentPropertyValue("bookingEndPoint")+"/"+DataUtils.getEnvironmentPropertyValue("nonExistedBookingID"))
                .then()
                .log().all()
                .assertThat()
                .statusCode(405)
                .body(equalTo("Method Not Allowed"));
    }//end TCRestfulBooker_08()



}
