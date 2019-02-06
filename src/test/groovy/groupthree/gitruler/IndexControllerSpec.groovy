package groupthree.gitruler

import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.Authentication
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import spock.lang.Specification
import groupthree.gitruler.controller.IndexController
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import org.springframework.security.core.context.SecurityContextHolder;



@ContextConfiguration
@WebMvcTest(controllers=[IndexController.class])
class IndexControllerSpec extends Specification{

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;
  private ResultActions result;
  
  
  
  @WithMockUser
  def "GET /exercises shows view exercises when authenticated" (){
    
    given: "the context of the controller is setup"
           this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build()
    and:
           Authentication a = SecurityContextHolder.getContext().getAuthentication();
    when: "I perform an HTTP GET /exercises and am authenticated"
          result = this.mockMvc.perform(get("/exercises").with(authentication(a)))
    then: "I should see the view exercises"
          result.andExpect(view().name("exercises"))
  }

  
  
  @WithMockUser
  def "GET / redirects to /exercises" (){
    
    given: "the context of the controller is setup"
           this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build()
    and:
           Authentication a = SecurityContextHolder.getContext().getAuthentication();
    when: "I perform an HTTP GET / and am authenticated"
           result = this.mockMvc.perform(get("/").with(authentication(a)))
    then: "I should be redirected to exercises page"
           result.andExpect(redirectedUrl("/exercises"))
  }
  
  
  def "GET / return status 302" (){
    
    given: "the context of the controller is setup"
           this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    when: "I perform an HTTP GET /"
          result = this.mockMvc.perform(get('/'))
    then: "the status of the HTTP response should be 302"
            result.andExpect(status().is(302))
   
  }
  
  
  def "GET / shows unauthenticated" (){
    
    given: "the context of the controller is setup"
           this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    when: "I perform an HTTP GET /"
          result = this.mockMvc.perform(get("/"))
    then: "I should be unauthenticated"
          result.andExpect(unauthenticated())
  }
  
}
