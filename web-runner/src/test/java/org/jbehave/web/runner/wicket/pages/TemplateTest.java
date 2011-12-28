package org.jbehave.web.runner.wicket.pages;

import org.apache.wicket.util.tester.WicketTester;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.Steps;
import org.jbehave.web.runner.wicket.WebRunnerApplication;
import org.junit.Before;

public abstract class TemplateTest {

    protected WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WebRunnerApplication(){

            @Override
            protected InjectableStepsFactory stepsFactory(){
                return new InstanceStepsFactory(configuration(), new TestSteps(), new OtherTestSteps());
            }            
        });
    }

    public static class TestSteps extends Steps {
        
        @Given("a step")
        public void givenAStep(){
            
        }

        @When("step is executed")
        public void whenStepIsExecuted(){
            
        }

        @Then("step is successful")
        public void thenStepIsSuccessful(){
            
        }
    }

    public static class OtherTestSteps extends Steps {
        
        @Given("another step")
        public void givenAnotherStep(){
            
        }

        @When("the other step is executed")
        public void whenOtherStepIsExecuted(){
            
        }

        @Then("other step is successful")
        public void thenOtherStepIsSuccessful(){
            
        }
    }

}
