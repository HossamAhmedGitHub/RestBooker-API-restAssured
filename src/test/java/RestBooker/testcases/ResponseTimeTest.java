package RestBooker.testcases;

import RestBooker.Utilities.DataUtils;
import RestBooker.Utilities.Utils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class ResponseTimeTest {
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
    }//end getToken()

    @Test( description = "verify Response time is less than 200 ms when adding new booking")
    public void TC_RestfulBooker_09(){

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
                        .time(lessThan(200L));

    }//end TC_RestfulBooker_09()

    @Test(dependsOnMethods ="getToken", description = "verify Response time is less than 200 ms when updating a booking")
    public void TCRestfulBooker_04(){
        given()
                .baseUri(DataUtils.getEnvironmentPropertyValue("baseURL"))
                .header("Content-Type", "application/json")
                .header("Cookie","token="+authToken)
                .body(DataUtils.getUpdateBodyDataFile())
                .when()
                .patch(DataUtils.getEnvironmentPropertyValue("bookingEndPoint")+"/"+ String.valueOf(Utils.getRandomNumber()))
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .time(lessThan(200L));
    }//end TCRestfulBooker_04()


}//end class ResponseTimeTest
