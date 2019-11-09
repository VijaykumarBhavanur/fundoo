package com.bridgelabz.fundoo.test;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bridgelabz.fundoo.controller.UserController;
import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.service.IUserService;
import static org.hamcrest.Matchers.*;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class FundooAppTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IUserService userServiceImpl;
	
	
	@Test
	public void registerTest()throws Exception
	{
		
	}
	/*
	 * @InjectMocks private UserController usercontroller;
	 * 
	 * @Mock private IUserService userServiceImpl;
	 * 
	 * private MockMvc mockMvc;
	 * 
	 * 
	 * 
	 * @Before public void setup() throws Exception {
	 * MockitoAnnotations.initMocks(this);
	 * mockMvc=MockMvcBuilders.standaloneSetup(usercontroller).build(); }
	 * 
	 * @Test public void getAllUsersTest() throws Exception { List<RegisterUser>
	 * users=new ArrayList<RegisterUser>(); users.add(new RegisterUser("1",
	 * "test1@gmail.com", "user1", "9854577879", "user@12", null, null, null));
	 * users.add(new RegisterUser("2", "test2@gmail.com", "user2", "9457877894",
	 * "user@21", null, null, null));
	 * 
	 * when(userServiceImpl.getUsers()).thenReturn((List)users);
	 * mockMvc.perform(get("/user/users")) .andExpect(status().isOk())
	 * .andExpect(view().name("users"))
	 * .andExpect(model().attribute("users",hasSize(2)));
	 * 
	 * }
	 */
}



