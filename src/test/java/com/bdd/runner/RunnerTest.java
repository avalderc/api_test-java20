package com.bdd.runner;

import com.bdd.environmet.ManageEnvironment;
import freemarker.core.Environment;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {
                "html:target/build/cucumber-html-report",
                "pretty:target/build/cucumber-pretty.txt",
                "json:target/build/cucumber.json"
        },
        features = {"src/test/resources/features"},
        stepNotifications = true,
        glue = {"com.bdd"},
        tags = "@Sample"
)
public class RunnerTest {
        @BeforeClass
        public static void beforeExecution() {
        }

        @AfterClass
        public static void afterExecution() {
        }
}
