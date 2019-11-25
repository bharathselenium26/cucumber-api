package com.aman.test.core;


import org.testng.Assert;

import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomAssert extends Assert {

	public static Logger log = LogManager.getLogger(CustomAssert.class);
    public static void assertStartWith(String content, String prefix, String message) {
        if (message != null)
        	log.info(message);
        if (content.startsWith(prefix)) {
        	log.info("assertion successful");
        } else {
            log.error("assertion failed with contenct - :" + content + "\n doesn't contains :" + prefix);
            CustomAssert.fail();
        }
    }


    public static void assertStartWith(String content, String prefix) {
        CustomAssert.assertStartWith(content, prefix, null);
    }


    public static void assertEndWith(String content, String endfix, String message) {
        if (message != null)
            log.info(message);
        if (content.endsWith(endfix)) {
            log.info("assertion successful");
        } else {
            log.error("assertion failed with contenct -:" + content + "\n doesn't contains :" + endfix);
            CustomAssert.fail();
        }
    }


    public static void assertEndWith(String content, String endfix) {
        CustomAssert.assertEndWith(content, endfix, null);
    }


    public static void assertMatch(String matcher, String regex, String message) {
        if (message != null)
            log.info(message);
        if (Pattern.matches(regex, matcher)) {
            log.info("assertion successful");
        } else {
            log.error("assertion failed with contenct -:" + matcher + "\n doesn't contains :" + regex);
            CustomAssert.fail();
        }
    }

   
    public static void assertMatch(String matcher, String regex) {
        CustomAssert.assertMatch(matcher, regex, null);
    }

    
    public static void assertNoMatch(String matcher, String regex, String message) {
        if (message != null)
            log.info(message);
        if (!Pattern.matches(regex, matcher)) {
            log.info("assertion successful");
        } else {
            log.error("assertion failed with contenct -:" + matcher + "\n doesn't contains :" + regex);
            CustomAssert.fail();
        }
    }

   
    public static void assertNoMatch(String matcher, String regex) {
        CustomAssert.assertNoMatch(matcher, regex, null);
    }

    public static void assertInclude(String content, String included, String message) {
        if (message != null)
            log.info(message);
        if (content.contains(included)) {
            log.info("assertion successful");
        } else {
            log.error("assertion failed with contenct -:" + content + "\n doesn't contains :" + included);
            CustomAssert.fail(message);
        }
    }

   
    public static void assertInclude(String content, String included) {
        CustomAssert.assertInclude(content, included, null);
    }
}
