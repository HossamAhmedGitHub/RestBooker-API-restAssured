package RestBooker.testcases;

import RestBooker.Utilities.DataUtils;
import RestBooker.Utilities.Utils;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class UpdateBookingTest {
    private String authToken;
    @Test
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
    }
    @Test(description = "verify Update without authentication, error message 'Forbidden' is returned")
    public void TCRestfulBooker_03(){
        given()
                .baseUri(DataUtils.getEnvironmentPropertyValue("baseURL"))
                .header("Content-Type", "application/json")
                .body(DataUtils.getUpdateBodyDataFile())
        .when()
                .patch(DataUtils.getEnvironmentPropertyValue("bookingEndPoint")+"/3501")
        .then()
                .log().all()
                .assertThat()
                .statusCode(403)
                .body(equalTo("Forbidden"));
    }//end TCRestfulBooker_03()

    @Test(dependsOnMethods ="getToken", description = "verify authorized authentication update , status code is 200 and booking is updated correctly")
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
                .body("firstname",equalTo("Martin"))
                .body("lastname",equalTo("Mohsen"));
    }//end TCRestfulBooker_04()

    @Test(dependsOnMethods ="getToken", description = "verify update non existed bookingid, status code is 405 and message 'Method Not Allowed' is returned")
    public void TCRestfulBooker_05(){
        given()
                .baseUri(DataUtils.getEnvironmentPropertyValue("baseURL"))
                .header("Content-Type", "application/json")
                .header("Cookie","token="+authToken)
                .body(DataUtils.getUpdateBodyDataFile())
                .when()
                .patch(DataUtils.getEnvironmentPropertyValue("bookingEndPoint")+"/"+ String.valueOf(DataUtils.getEnvironmentPropertyValue("nonExistedBookingID")))
                .then()
                .log().all()
                .assertThat()
                .statusCode(405)
                .body(equalTo("Method Not Allowed"));
    }//end TCRestfulBooker_05()


}//end class UpdateBookingTest
