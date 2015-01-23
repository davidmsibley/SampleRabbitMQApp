/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.usgs.cida.samplerabbitmqapp.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dmsibley
 */
public class Common {
	private static final Logger log = LoggerFactory.getLogger(Common.class);
	
	public static final String HOST_NAME = "localhost";
	public static final String QUEUE_NAME = "hello";
	public static final String MESSAGE_CONTENTS = "Hello World!";
	
	public static final String USERNAME = "dmsibley";
	public static final String PASSWORD = "dmsibley";

}
