package io.testproject.myaddon;

import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

import java.text.NumberFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.bogdanovmn.humanreadablevalues.BytesValue;

@Action(name = "Convert Number To Human Readble Format")
public class ConvertNumberToHumanReadbleNumber implements GenericAction {

    @Parameter(description = "Actual Number", direction = ParameterDirection.INPUT)
    private String aValue;

    @Parameter(description = "Human Readble value", direction = ParameterDirection.OUTPUT)
    private String hrValue;

   
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
    	
    	try {
    	//hrValue = new BytesValue(Long.parseLong(aValue)).shortString();
    		hrValue = showVulnerabilityAbbr(aValue);
        return ExecutionResult.PASSED;
    	}catch(Exception e) {
        	hrValue = aValue;
           	return ExecutionResult.FAILED;
        }
    
    }
    
    private String showVulnerabilityAbbr(String input) {
        String output = input;
        double temp = 0;
        NumberFormat nf= NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        if (Long.parseLong(input) >= 1.0e+6) {
          temp = (Long.parseLong(input) / 1.0e+6);
          output = nf.format(temp);
          output = this.checkIfLastDigitZero(output);
          output += 'M';
        } else if (Long.parseLong(input) >= 1.0e+3) {
          temp = (Long.parseLong(input) / 1.0e+3);
          output = nf.format(temp);
          output = this.checkIfLastDigitZero(output);
          output += 'K';
        } else {
          output = input;
        }

        return output;
      }

      private String checkIfLastDigitZero(String input) {
        if (input.isEmpty()) {
          return input;
        }

        final String isLastDigitZero = input.substring(input.length() - 1, input.length());
        if (isLastDigitZero.equals("0")) {
          input = input.split(".")[0];
        }

        return input;
      }
    

}
