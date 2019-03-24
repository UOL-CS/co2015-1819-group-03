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
import groupthree.gitruler.domain.JobQueue
import groupthree.gitruler.domain.User
import groupthree.gitruler.repository.ExerciseRepository
import groupthree.gitruler.repository.JobQueueRepository
import groupthree.gitruler.repository.UserRepository

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
  
  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private JobQueueRepository jqRepo;
  
  
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
           ex.setRepository("https://gitlab.com/gitlab-org/gitlab-ce")
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
           ex.setRepository("https://gitlab.com/gitlab-org/gitlab-ce")
           
           Exercise ex2 = new Exercise()
           ex2.setName("Exercise 2")
           ex2.setRepository("https://gitlab.com/gitlab-org/gitlab-runner")

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
           ex.setRepository(null)
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
           ex.setRepository("https://gitlab.com/gitlab-org/gitlab-ce")
           
           Exercise ex2 = new Exercise()
           ex2.setName("Exercise 1")
           ex2.setRepository("https://gitlab.com/gitlab-org/gitlab-ce")
           
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
           ex.setRepository("https://gitlab.com/gitlab-org/gitlab-ce")
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
  
  def "Check model to verify exercise has property description" (){
    
     given: "the context of the controller is setup"
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
     when:  "I perform an HTTP GET /exercise/1"
            result = this.mockMvc.perform(get("/exercise/1"))
     then:  "the model should contain an exercise with a description"
            result.andExpect(model().attribute("exercise", hasProperty("description")))
  }
  
    
      
  def "find the exercise by its description in the repository" () {
        
     given: "a single exercise in the repository"
            exRepo.deleteAll()
        
            Exercise ex = new Exercise()
            ex.setName("Exercise 1")
            ex.setRepository("https://gitlab.com/gitlab-org/gitlab-ce")
            ex.setDescription("Description")
                   
      when: "saving the exercise into the repository"
            exRepo.save(ex)
      then: "repository should have one exercise with a Description"
            assertThat(exRepo.findByDescription("Description"), hasSize(1));
  }
          
  def "find the exercise by its name in the repository" () {
            
     given: "a single exercise in the repository"
            exRepo.deleteAll()
            
            Exercise ex = new Exercise()
            ex.setName("Exercise 1")
            ex.setRepository("https://gitlab.com/example/exercise1")
                       
                 
      when: "saving the exercise into the repository"
            exRepo.save(ex)
      then: "repository should have one exercise with a name"
            assertThat(exRepo.findByName("Exercise 1"), hasSize(1));
  }
              
              
  def "Check the exercise repository url" (){
         
     given: "a single exercise in the repository"
            exRepo.deleteAll()

            Exercise ex = new Exercise()
            ex.setName("Exercise 1")
            ex.setRepository("https://gitlab.com/example/exercise1")
                
      when: "saving the exercise into the repository"
            exRepo.save(ex)
                
      then: "repository should have one exercise with a repository url"
            assertThat(exRepo.findByName("Exercise 1").get(0).getRepository(),is("https://gitlab.com/example/exercise1"));
                         
  }
       
       
  def "Check the exercise point" (){
         
     given: "a single exercise in the repository"
            exRepo.deleteAll()

            Exercise ex = new Exercise()
            ex.setName("Exercise 1")
            ex.setRepository("https://gitlab.com/example/exercise1")
            ex.setPoint(10)
                
      when: "saving the exercise into the repository"
            exRepo.save(ex)
                
      then: "repository should have one exercise with a repository url"
            assertThat(exRepo.findByName("Exercise 1").get(0).getPoint(),is(10));
                         
  }
       
  def "exercise with no set repository" () {
         
     given: "a single exercise in the repository"
            exRepo.deleteAll()
            Exercise ex = new Exercise()
            ex.setName("Exercise 1")
            ex.setRepository(null)
               
      when: "saving the exercise into the repository"
            exRepo.save(ex)
                
      then: "throw an exception"
            thrown(DataIntegrityViolationException)
         
  }
  def "user is saved to user repository"() {
    
    given: "a single user in the repository"
           userRepo.deleteAll()
           String userToken = "de6780bc506a0446309bd9362820ba8aed28aa506c71eedbe1c5c4f9dd350e54"
           String encryptionPass = "password";
           User user = new User()
           user.setId(1)
           String encryptedToken = user.encryptToken(userToken, encryptionPass)
           user.setToken(encryptedToken)
    when: "saving the user into the repository"
          userRepo.save(user)
    then: "repository should have one user"
          assertThat(userRepo.findAll(), hasSize(1))
  }
    
  def "user id is empty"() {
    
    given: "a single user in the repository"
           userRepo.deleteAll()
           String userToken = "de6780bc506a0446309bd9362820ba8aed28aa506c71eedbe1c5c4f9dd350e54"
           String encryptionPass = "password"
           User user = new User()
           String encryptedToken = user.encryptToken(userToken, encryptionPass)
           user.setToken(encryptedToken)
    when: "saving the user into the repository"
           userRepo.save(user)
    then: "repository should have one user"
           assertThat(userRepo.findAll(), hasSize(1))
  }
    
  def "user token is null"() {
    
    given: "a single user in the repository"
           userRepo.deleteAll()
           User user = new User()
           user.setId(1)
           user.setToken(null)
    when: "saving the user into the repository"
          userRepo.save(user)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
  }
    
  def "user token is empty"() {
    
    given: "a single user in the repository"
           userRepo.deleteAll()
           User user = new User()
           user.setId(1)
           user.setToken()
    when: "saving the user into the repository"
          userRepo.save(user)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
  }
    
  def "user id and token is null"() {
    
    given: "a single user in the repository"
           userRepo.deleteAll()
           User user = new User()
           user.setToken(null)
    when: "saving the user into the repository"
          userRepo.save(user)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
    
  }
    
  def "user id and token is empty"() {
    given: "a single user in the repository"
           userRepo.deleteAll()
           User user = new User()
           user.setToken()
    when: "saving the user into the repository"
          userRepo.save(user)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
  }
    
  def "save two users with same id"() {
    
    given: "two users with same id in the repository"
           userRepo.deleteAll()
           String userToken = "de6780bc506a0446309bd9362820ba8aed28aa506c71eedbe1c5c4f9dd350e54"
           String user2Token = "de6780bc506a0446309bd9362820ba8aed28aa506c71eedbe1c5c4f9dd350e56"
           String encryptionPass = "password"
           
           User user = new User()
           user.setId(1)
           String encryptedToken = user.encryptToken(userToken, encryptionPass)
           user.setToken(encryptedToken)
           
           User user2 = new User()
           user2.setId(1)
           String encryptedToken2 = user.encryptToken(user2Token, encryptionPass)
           user.setToken(encryptedToken2)
    when: "saving the user into the repository"
          userRepo.save(user)
          userRepo.save(user2)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
  }
    
  def "save two users with the same token"() {
    
    given: "two users with same token in the repository"
           userRepo.deleteAll()
           String userToken = "de6780bc506a0446309bd9362820ba8aed28aa506c71eedbe1c5c4f9dd350e54"
           String encryptionPass = "password"
           
           User user = new User()
           String encryptedToken = user.encryptToken(userToken, encryptionPass)
           user.setToken(encryptedToken)
           
           User user2 = new User()
           String encryptedToken2 = user.encryptToken(userToken, encryptionPass)
           user.setToken(encryptedToken2)
    when: "saving the user into the repository"
          userRepo.save(user)
          userRepo.save(user2)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
  }
  
      
  def "save two duplicate users"() {
    
    given: "two identical users in the repository"
           userRepo.deleteAll()
           String userToken = "de6780bc506a0446309bd9362820ba8aed28aa506c71eedbe1c5c4f9dd350e54"
           String encryptionPass = "password"
           
           User user = new User()
           String encryptedToken = user.encryptToken(userToken, encryptionPass);
           user.setToken(encryptedToken)
           
           User user2 = user
    when: "saving the user into the repository"
          userRepo.save(user)
          userRepo.save(user2)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
  }
    
  def "save user with no set id or token"() {
    
    given: "a single user in the repository"
           userRepo.deleteAll()
           User user = new User()
    when: "saving the user into the repository"
          userRepo.save(user)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
  }
  
  def "add job to job queue"() {
    
    given: "a single job in the repository"
           jqRepo.deleteAll()
           JobQueue job = new JobQueue()
           job.setLink("https://gitlab.com/gitlab-org/gitlab-ce")
           job.setUserId(72792)
           job.setExerciseId(2)
    when: "saving the job to the repository"
          jqRepo.save(job)
    then: "repository should contain one job"
          assertThat(jqRepo.findAll(), hasSize(1))
  }
  
  def "add job with no attributes to queue"() {
    
    given: "a single job in the repository"
           jqRepo.deleteAll()
           JobQueue job = new JobQueue()
    when: "saving the job to the repository"
          jqRepo.save(job)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
  }
  
  def "add job with empty string as link to queue"() {
    
    given: "a single job in the repository"
           jqRepo.deleteAll()
           JobQueue job = new JobQueue()
           job.setLink("")
           job.setUserId(72792)
           job.setExerciseId(2)
    when: "saving the job to the repository"
          jqRepo.save(job)
    then: "repository should contain one job"
          assertThat(jqRepo.findAll(), hasSize(1))
  }
  
  def "add job with null link to queue"() {
    
    given: "a single job in the repository"
           jqRepo.deleteAll()
           JobQueue job = new JobQueue()
           job.setLink(null)
           job.setUserId(72792)
           job.setExerciseId(2)
    when: "saving the job to the repository"
          jqRepo.save(job)
    then: "throw an exception"
          thrown(DataIntegrityViolationException)
  }
  
  def "add multiple non-unique jobs to queue"() {
    
    given: "two jobs in the repository"
           jqRepo.deleteAll()
           JobQueue job1 = new JobQueue()
           job1.setLink("https://gitlab.com/gitlab-org/gitlab-ce")
           job1.setUserId(72792)
           job1.setExerciseId(2)
           JobQueue job2 = new JobQueue()
           job2.setLink("https://gitlab.com/gitlab-org/gitlab-ce")
           job2.setUserId(72792)
           job2.setExerciseId(2)
    when: "saving the jobs to the repository"
          jqRepo.save(job1)
          jqRepo.save(job2)
    then: "repository should contain one job"
          assertThat(jqRepo.findAll(), hasSize(2))
  }
  
  def "add multiple unique jobs to queue"() {
    
    given: "two jobs in the repository"
           jqRepo.deleteAll()
           JobQueue job1 = new JobQueue()
           job1.setLink("https://gitlab.com/gitlab-org/gitlab-ce")
           job1.setUserId(72792)
           job1.setExerciseId(2)
           JobQueue job2 = new JobQueue()
           job2.setLink("https://gitlab.com/gitlab-org/gitlab-runner")
           job2.setUserId(56456)
           job2.setExerciseId(1)
    when: "saving the jobs to the repository"
          jqRepo.save(job1)
          jqRepo.save(job2)
    then: "repository should contain one job"
          assertThat(jqRepo.findAll(), hasSize(2))
  }
  
  def "POST /sumbmit/1 redirects to exercise page"() {
    
    given: "the context of the controller is setup"
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    when:  "I perform an HTTP POST /submit/{id}"
            result = this.mockMvc.perform(post('/exercise/submit/1')
                  .param("id", "2")
                  .param("link", "testLink")
                  .with(csrf()))
    then:  "the status of the HTTP response should be 302"
            result.andExpect(redirectedUrl("/exercise/1"))
  }
  
  def "POST /sumbmit/1 without csrf token"() {
    
    given: "the context of the controller is setup"
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    when:  "I perform an HTTP POST /submit/{id}"
            result = this.mockMvc.perform(post('/exercise/submit/100')
                  .param("id", "2")
                  .param("link", "testLink"))
    then:  "the status of the HTTP response should be 302"
            result.andExpect(status().is(302))
  }

 }
