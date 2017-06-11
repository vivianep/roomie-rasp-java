import rfid.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.KaaClientProperties;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.client.event.EventFamilyFactory;
import org.kaaproject.kaa.client.event.FindEventListenersCallback;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.common.endpoint.gen.UserAttachResponse;
import org.kaaproject.kaa.common.endpoint.gen.SyncResponseResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.roomie.StartMeetEvent;
import com.roomie.StartMeetECF;
import com.roomie.ConfirmationECF;
import com.roomie.ConfirmationEvent;
import com.roomie.InterruptMeetingECF;
import com.roomie.InterruptMeetingEvent;
import com.roomie.interruptReasonEnum;
import com.roomie.User;
import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import java.util.Scanner;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.wiringpi.GpioUtil;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.PinEventType;
import java.io.Console;
import java.lang.System;

public class Main {

	public static  void main(String[] args) throws ClassNotFoundException, IOException,InterruptedException {
		
		Console console = System.console();
		User u = new User();
		String password = "";
		CountDownLatch startupLatch = new CountDownLatch(1);
		//kaaClient = Kaa.newClient( desktopKaaPlatformContext, new SimpleKaaClientStateListener() {
		System.out.println("==============================================================");  //42 characters-- starting 25 til 87
		System.out.println("=============================================================="); 
		System.out.println("=                 Welcome to Roomie Raspberry                =");  
		System.out.println("=============================================================="); 
		System.out.println("Choose what do you want do next                               ");  
		System.out.println("1 - Register new user:                                        ");  
		System.out.println("2 - Start Kaa                                                 ");  
		 System.out.println("=============================================================="); 
		System.out.println("Now Type your choice:"); 
		Scanner user_input = new Scanner(System.in);
		String userOption = user_input.next();
		switch(userOption){
			case "1":
				System.out.println("Okay. Lets register a new user then!                          "); 
				System.out.println("Insert the user name:                                         ");  
				String userName = user_input.next();
				System.out.println("Got that ;) Can you please inform me the email now:           ");  
				String userEmail = user_input.next();
				//System.out.println("Now can you inform your new brand password:           ");  
				boolean pwdMatch = false;
				while(!pwdMatch){
					password = new String(console.readPassword("Now can you inform your new brand password:\n"));
					String confirmation = new String(console.readPassword("Can you type again, so I can confirm?\n"));
					if(password.equals(confirmation)){
						pwdMatch=true;
						break;
					}
					System.out.println("Both passwords don't match. Let's try again!");
				}
				u.setUserName(userName);
				u.setEmail(userEmail);
				u.setHashedPassword(Util.SHA256(password));
				u.setRfidCode(" ");
				KaaClientImplementation kaaClientImpl = new KaaClientImplementation();
				kaaClientImpl.initKaa();	
				//u.setRfidCode(tag);
			 	u.setRfidCode("");
			 	kaaClientImpl.sendCreateUserEvent(u);
				ConfirmationECF confirmationECF = kaaClientImpl.eventFamilyFactory.getConfirmationECF();	
				boolean confirmationFlag = false;
				final CountDownLatch confirmationLatch = new CountDownLatch(1);
				System.out.println("Confirmation Event Listener added");
				confirmationECF.addListener( new ConfirmationECF.Listener(){
					
					@Override
					public void onEvent(ConfirmationEvent event, String source)  {
						System.out.println("Confirmation Event Received from RoomieWeb");
						if(u.getEmail() == event.getEmail()){
							if(event.getIsRegistered())
								System.out.println("Chill out your user was registered");
							else
								System.out.println("Oh ohm I think we got a problem with you registration." + event.getStatus());
							confirmationLatch.countDown();
						}
						//confirmationFlag = true;
				}
				});
				confirmationLatch.await();
				break;
			default:
				
			 /*while(true){
						System.out.println("Reading Tag");
						System.out.println(rfidReader.readTag());
				
					}
				*/	
		}
			
		
		
       }
		       
}

		       					   
     
           
