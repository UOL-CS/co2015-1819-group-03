package groupthree.gitruler

import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
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
import groupthree.gitruler.domain.Exercise
import groupthree.gitruler.repository.ExerciseRepository

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.dao.DataIntegrityViolationException


@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes=[GitrulerApplication.class])
class IndexControllerSpec extends Specification{

  @Autowired
  private WebApplicationContext wac;

  @Autowired
  private ExerciseRepository exRepo;
  
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
  
  def "GET / shows exercises page with no exercises" () {
    
     given: "the context of the controller is setup"
             this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
       and: "the exercises repository has no exercises"
             exRepo.deleteAll()
     when:  "I perform an HTTP GET /exercises"
             result = this.mockMvc.perform(get("/exercises"))
     then:  "the model should contain attribute isEmpty which is true"
             result.andExpect(model().attribute("isEmpty", is(true)))
  }
  
  
  def "GET / shows exercises page with 1 exercise" () {
    
    given: "the context of the controller is setup"
           this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
      and: "the exercises repository has 1 exercise"
           exRepo.deleteAll()
           
           Exercise ex = new Exercise()
           ex.setName("Exercise 1")
           ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
               + "Aenean mattis vel purus et sagittis nullam.")
           ex.setPoint(250)
           
           exRepo.save(ex)
           
    when:  "I perform an HTTP GET /exercises"
           result = this.mockMvc.perform(get("/exercises"))
    then:  "the model should contain attribute isEmpty which is null"
           result.andExpect(model().attribute("isEmpty", is(null)))
    and:   "the model should contain attribute exercises containing 1 exercise"
           result.andExpect(model().attribute("exercises", hasSize(1)))
  }
    
  def "find all exercises in the repository" () {
    
    given: "two different exercises"
           exRepo.deleteAll()
           
           Exercise ex = new Exercise()
           ex.setName("Exercise 1")
           
           Exercise ex2 = new Exercise()
           ex2.setName("Exercise 2")

    when: "saving the exercises into the repository"
           exRepo.save(ex)
           exRepo.save(ex2)
    then:
           assertThat(exRepo.findAll(), hasSize(2))
  }
  
  def "exercise with null attributes" () {

    given: "the exercise has attributes set to null"
           exRepo.deleteAll()
           
           Exercise ex = new Exercise()
           ex.setName(null)
           ex.setDescription(null)
    when:  "saving the exercise into the repository"
           exRepo.save(ex)
    then:  "throw an exception"
           thrown(DataIntegrityViolationException)
    
  }
  
  def "two exercises with the same name" () {
    
    given: "two exercises with the name attribute identical"
           exRepo.deleteAll();

           Exercise ex = new Exercise()
           ex.setName("Exercise 1")
           
           Exercise ex2 = new Exercise()
           ex2.setName("Exercise 1")
           
    when:  "saving the exercises into the repository"
           exRepo.save(ex)
           exRepo.save(ex2)
    then:  "throw an exception"
           thrown(DataIntegrityViolationException)
  }
  
  def "exercise with no set attributes" () {
    
    given: "an empty exercise object"
           exRepo.deleteAll()
           Exercise ex = new Exercise()
           
    when:  "saving the exercise into the repository"
           exRepo.save(ex)
    then:  "throw an exception"
           thrown(DataIntegrityViolationException)
  }
  
  def "repository is empty when last one is deleted" () {
    
    given: "a single exercise in the repository"
           exRepo.deleteAll()
           
           Exercise ex = new Exercise()
           ex.setName("Exercise 1")
           exRepo.save(ex)
            
    when: "deleting the exercise from the repository"
           exRepo.delete(ex)
    then: "repository should be empty"
           assertThat(exRepo.findAll(), empty());
  }
  
  def "GET /exercise/1 returns exercise view" (){
    
     given: "the context of the controller is setup"
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
     when: "I perform an HTTP GET /exercise"
            result = this.mockMvc.perform(get("/exercise/1"))
     then: "I should see the view exercise"
            result.andExpect(view().name("exercise"))
  }
    
  def "Check model to verify exercise has property id" (){
        
     given: "the context of the controller is setup"
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
     when:  "I perform an HTTP GET /exercise/1"
            result = this.mockMvc.perform(get("/exercise/1"))
     then:  "the model should contain an exercise with id 1"
            result.andExpect(model().attribute("exercise", hasProperty("id", is(1))))
  }
    
  def "Check model to verify exercise has property name" (){
    
     given: "the context of the controller is setup"
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
     when:  "I perform an HTTP GET /exercise/1"
            result = this.mockMvc.perform(get("/exercise/1"))
     then:  "the model should contain an exercise with name Exercise 1"
            result.andExpect(model().attribute("exercise", hasProperty("name", is("Exercise 1"))))
  }
      
      
  def "Check model to verify exercise has property theme" (){
    
     given: "the context of the controller is setup"
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
     when:  "I perform an HTTP GET /exercise/1"
            result = this.mockMvc.perform(get("/exercise/1"))
     then:  "the model should contain an exercise with a theme"
            result.andExpect(model().attribute("exercise", hasProperty("theme")))
  }
      
  def "GET /exercises returns exercises view" (){
        
     given: "the context of the controller is setup"
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
     when:  "I perform an HTTP GET /exercises"
            result = this.mockMvc.perform(get("/exercises"))
     then:  "I should see the view exercises"
            result.andExpect(view().name("exercises"))
  }
 }
