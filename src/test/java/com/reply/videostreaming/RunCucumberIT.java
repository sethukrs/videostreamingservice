package com.reply.videostreaming;

import org.junit.runner.RunWith;

import com.reply.videostreaming.cucumber.stepdefs.CucumberStepDefinitions;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Cucumber Runner
 * @author sraamasubbu
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    strict = true,
    dryRun = false,
    glue = {"com.reply.videostreaming.cucumber.stepdefs"},
    features = "src/test/resources/features",
    plugin = {"pretty"})
public class RunCucumberIT extends CucumberStepDefinitions {

}
