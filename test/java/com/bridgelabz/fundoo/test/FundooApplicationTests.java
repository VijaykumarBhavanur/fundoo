
package com.bridgelabz.fundoo.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.repository.IRegisterRepository;
import com.bridgelabz.fundoo.service.IUserService;
import com.bridgelabz.fundoo.service.UserServiceImpl;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)

@SpringBootTest
public class FundooApplicationTests {

	@Autowired
	private IUserService service;

	@MockBean
	private IRegisterRepository repository;

	/*
	 * @Test public void getUsersTest() {
	 * when(repository.findAll()).thenReturn(Stream .of(new RegisterUser("1",
	 * "user1@gmail.com", "user1", "9866423126", "user@123", null, null, null), new
	 * RegisterUser("2", "user2@gmail.com", "user2", "7766423126", "user@321", null,
	 * null, null)) .collect(Collectors.toList())); assertEquals(2,
	 * service.getUsers().size()); }
	 */
	 
	@Test
	public void sortNoteByNameTest() {
		RestAssured.baseURI = "http://localhost:8080/notes";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoibGV0dGVyMnZpamF5a3VtYXJiaGF2YW51ckBnbWFpbC5jb20ifQ.-YtRykmrLIgqNJ7UzCjOhsvqcGbRMq-NLSrUodfmS6E");
		Response response = httpRequest.request(Method.GET, "/note/sorted/by/name");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

	}

	@Test
	public void createNoteTest() throws Exception {

		RestAssured.baseURI = "http://localhost:8080/notes";

		JSONObject requestParams = new JSONObject();
		requestParams.put("title", "TajMahal");
		requestParams.put("description", "One of Seven Wonders of World");

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoibGV0dGVyMnZpamF5a3VtYXJiaGF2YW51ckBnbWFpbC5jb20ifQ.-YtRykmrLIgqNJ7UzCjOhsvqcGbRMq-NLSrUodfmS6E")
				.body(requestParams.toString()).contentType("application/json");
		Response response = httpRequest.request(Method.POST, "/note");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

	}

	@Test
	public void updateNoteTest1_With_Proper_Input() throws Exception {

		RestAssured.baseURI = "http://localhost:8080/notes";
		JSONObject requestParams = new JSONObject();
		requestParams.put("title", "MysorePalace");
		requestParams.put("description", "One of royal house!!!");

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoibGV0dGVyMnZpamF5a3VtYXJiaGF2YW51ckBnbWFpbC5jb20ifQ.-YtRykmrLIgqNJ7UzCjOhsvqcGbRMq-NLSrUodfmS6E")
				.body(requestParams.toString()).contentType("application/json")
				.param("noteId", "5db9740ebe9b8757a1ed17e5");
		Response response = httpRequest.request(Method.PUT, "/note");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);

	}

	@Test
	public void updateNoteTest2_With_Invalid_Note_Id() throws Exception {

		RestAssured.baseURI = "http://localhost:8080/notes";

		JSONObject requestParams = new JSONObject();
		requestParams.put("title", "MysorePalace");
		requestParams.put("description", "One of royal house!!!");

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoibGV0dGVyMnZpamF5a3VtYXJiaGF2YW51ckBnbWFpbC5jb20ifQ.-YtRykmrLIgqNJ7UzCjOhsvqcGbRMq-NLSrUodfmS6E")
				.body(requestParams.toString()).contentType("application/json").param("noteId", "fdhfdhfdhff");
		Response response = httpRequest.request(Method.PUT, "/note");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

		int statusCode = response.getStatusCode();
		System.out.println("StatusCode: " + statusCode);
		Assert.assertEquals(200, statusCode);

	}

	@Test
	public void deleteNoteTest() throws Exception {

		RestAssured.baseURI = "http://localhost:8080/notes";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoibGV0dGVyMnZpamF5a3VtYXJiaGF2YW51ckBnbWFpbC5jb20ifQ.-YtRykmrLIgqNJ7UzCjOhsvqcGbRMq-NLSrUodfmS6E")
				.param("id", "fdghdgh54t45");

		Response response = httpRequest.request(Method.DELETE, "/note");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

	}

	@Test
	public void loginTest1_With_Valid_Credentials() throws Exception {
		RestAssured.baseURI = "http://localhost:8080/user";
		RequestSpecification httpRequest = RestAssured.given().header("emailId", "vijaykumarbhavanur@gmail.com")
				.header("password", "Vijay@123");
		Response response = httpRequest.request(Method.GET, "/login");
		System.out.println("Response Body:::::::::>" + response.getBody().asString());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);

		String message = response.path("message").toString();
		Assert.assertEquals("Login sucess", message);

	}

	@Test
	public void loginTest2_With_Invalid_EmailID() throws Exception {
		RestAssured.baseURI = "http://localhost:8080/user";
		RequestSpecification httpRequest = RestAssured.given().header("emailId", "bhavanur@gmail.com")
				.header("password", "Vijay@123");
		Response response = httpRequest.request(Method.GET, "/login");
		System.out.println("Response Body:::::::::>" + response.getBody().asString());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
		String message = response.path("message").toString();
		Assert.assertEquals("Login sucess", message);

	}

	@Test
	public void loginTest3_With_Invalid_Password() throws Exception {
		RestAssured.baseURI = "http://localhost:8080/user";
		RequestSpecification httpRequest = RestAssured.given().header("emailId", "vijaykumarbhavanur@gmail.com")
				.header("password", "Vijay");
		Response response = httpRequest.request(Method.GET, "/login");
		System.out.println("Response Body:::::::::>" + response.getBody().asString());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
		String message = response.path("message").toString();
		Assert.assertEquals("Login sucess", message);

	}

	@Test
	public void loginTest4_With_Empty_Fields() throws Exception {
		RestAssured.baseURI = "http://localhost:8080/user";
		RequestSpecification httpRequest = RestAssured.given().header("emailId", "").header("password", "");
		Response response = httpRequest.request(Method.GET, "/login");
		System.out.println("Response Body:::::::::>" + response.getBody().asString());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
		String message = response.path("message").toString();
		Assert.assertEquals("Login sucess", message);

	}

	@Test
	public void getAllNotesTest1_With_Valid_Token() throws Exception {
		RestAssured.baseURI = "http://localhost:8080/notes";
		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ"
						+ "lbWFpbElkIjoibGV0dGVyMnZpamF5a3VtYXJiaGF2YW51ckBnbWFpbC5jb20ifQ.-YtRykmrLIgqNJ7UzCjOhsvqcGbRMq-NLSrUodfmS6E");
		Response response = httpRequest.request(Method.GET, "/notes");
		System.out.println("Response Body:::::::::>" + response.getBody().asString());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void getAllNotesTest2_With_Invalid_Token() throws Exception {
		RestAssured.baseURI = "http://localhost:8080/notes";
		RequestSpecification httpRequest = RestAssured.given().header("token", "dxggfggff.fgfdggf.fdggfg");
		Response response = httpRequest.request(Method.GET, "/notes");
		System.out.println("Response Body:::::::::>" + response.getBody().asString());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void registerTest() throws Exception {

		RestAssured.baseURI = "http://localhost:8080/user";

		JSONObject requestParams = new JSONObject();
		requestParams.put("emailId", "vijaykumarbhavanur@gmail.com");
		requestParams.put("name", "Raj");
		requestParams.put("mobile", "9964605832");
		requestParams.put("password", "Vijay@123");

		RequestSpecification httpRequest = RestAssured.given().body(requestParams.toString())
				.contentType("application/json");
		Response response = httpRequest.request(Method.POST, "/register");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);

	}

}
