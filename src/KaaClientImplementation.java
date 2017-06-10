
import rfid.*;
import java.io.IOException;
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
import com.roomie.InterruptMeetingECF;
import com.roomie.CreateUserECF;
import com.roomie.InterruptMeetingEvent;
import com.roomie.interruptReasonEnum;
import com.roomie.User;
import com.roomie.CreateUserEvent;
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

public class KaaClientImplementation {

		private static final Logger LOG = LoggerFactory.getLogger(Main.class);
		static KaaClient kaaClient;
		public static  EventFamilyFactory eventFamilyFactory;
		private static final String KEYS_DIR = "keys_for_java_event_demo";
		private static String source;
		final GpioController gpio = GpioFactory.getInstance(); 
		//initialize PresenceSensor and all the leds 
		final GpioPinDigitalInput pirMotionsensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
		final GpioPinDigitalOutput yellowLed= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.LOW);
		final GpioPinDigitalOutput greenLed= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLED", PinState.LOW);
		//final GpioPinDigitalOutput redLed= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.LOW);
		public boolean confirmationReceived = false;  
		ReadRfid readRfid;

		public void initKaa() throws InterruptedException,IOException{
	
			KaaClientProperties endpointProperties = new KaaClientProperties();
			endpointProperties.setWorkingDirectory(KEYS_DIR);
			//Create the Kaa desktop context for the application
			DesktopKaaPlatformContext desktopKaaPlatformContext = new DesktopKaaPlatformContext(endpointProperties);
			final CountDownLatch startUpLatch = new CountDownLatch(1);  	
			readRfid = new ReadRfid();
					kaaClient = Kaa.newClient(desktopKaaPlatformContext ,new SimpleKaaClientStateListener() {
				@Override
					public void onStarted() {
					
						System.out.println("Kaa client iniciado");
						startUpLatch.countDown();
					}

					@Override
					public void onStopped() {
						System.out.println("Kaa client parado");
					}
				},true);
		
				kaaClient.start();
				startUpLatch.await();
			
				
				final CountDownLatch attachLatch = new CountDownLatch(1);
       		  	kaaClient.attachUser("UserVerifier", "67768675307474178405", new UserAttachCallback() {
                
                @Override
                public void onAttachResult(UserAttachResponse response) {
                    System.out.println("Attach to user result: {}");
                    if (response.getResult() == SyncResponseResultType.SUCCESS) {
                        LOG.info("Current endpoint have been successfully attached to user [ID={}]!");
						System.out.println("Current endpoint have been successfully attached to user [ID={}]!");
      
                    } else {
                        LOG.error("Attaching current endpoint to user [ID={}] FAILED.");
                        LOG.error("Attach response: {}", response);
                        LOG.error("Events exchange will be NOT POSSIBLE.");
                    }
                    attachLatch.countDown();
                            }
            });
            
					eventFamilyFactory = kaaClient.getEventFamilyFactory();
					StartMeetECF startMeetECF = eventFamilyFactory.getStartMeetECF();	
					startMeetECF.addListener(new StartMeetECF.Listener() {
						@Override
						public void onEvent(StartMeetEvent event, String source)  {

							System.out.println("Event Received");
							try{
								startMeeting(event);
							}catch(InterruptedException e ){
							
							}
						}
				
					});
					
					
				}
						
		
		
		
		public static void sendCreateUserEvent(User u) throws InterruptedException, IOException{
	
	
	    List<String> FQNs = new LinkedList<String>();
		FQNs.add(CreateUserEvent.class.getName());
		CreateUserECF createUserECF = eventFamilyFactory.getCreateUserECF();
			
		final CountDownLatch createUserLatch = new CountDownLatch(1);
		kaaClient.findEventListeners(FQNs, new FindEventListenersCallback(){
		
			@Override
			public void onEventListenersReceived(List<String> eventListeners) {
				System.out.println("Event listeners received "+ eventListeners.size());
				for(int i =0; i< eventListeners.size();i++){
					System.out.println(eventListeners.get(i));
				}
				//source = eventListeners.get(2);
				createUserLatch.countDown();
			}

			@Override
			public void onRequestFailed() {
				System.out.println("RequestFailed");
			}
			
			
		
			
		});
		
		createUserLatch.await();
		CreateUserEvent event = new CreateUserEvent();
		event.setUser(u);		
		createUserECF.sendEventToAll(event);
		System.out.println("Event Sent");	     			
       
        

}

		public static void sendInterruptEvent(interruptReasonEnum reason, int bookingId) throws InterruptedException{
	
	
	    List<String> FQNs = new LinkedList<String>();
		FQNs.add(InterruptMeetingEvent.class.getName());
		InterruptMeetingECF interruptMeetingECF = eventFamilyFactory.getInterruptMeetingECF();
			
		final CountDownLatch interruptMeetingLatch = new CountDownLatch(1);
		try{
		kaaClient.findEventListeners(FQNs, new FindEventListenersCallback(){
		
			@Override
			public void onEventListenersReceived(List<String> eventListeners)  {
				System.out.println("Event listeners received "+ eventListeners.size());
				for(int i =0; i< eventListeners.size();i++){
					System.out.println(eventListeners.get(i));
				}
				//source = eventListeners.get(2);
				interruptMeetingLatch.countDown();
			}

			@Override
			public void onRequestFailed() {
				System.out.println("RequestFailed");
			}
			
			
		
			
		});
		
		interruptMeetingLatch.await();
		InterruptMeetingEvent event = new InterruptMeetingEvent();
		event.setInterruptReason(reason);		
		event.setMeetingId(bookingId);
		interruptMeetingECF.sendEventToAll(event);
		System.out.println("Event Sent");	     			
        }catch(InterruptedException e ){
        
        }
        

}

/* 
	Implements the behaviour of a new meeting on Raspberry 
	On the beginning Raspberry is on rfid read mode 
	after a rfid card is identified, the system goes to detect presence mode
	if the time is longer then defined on business rules of inactivity an interrupt 
	meeting event is sent

	@param e StartMeetingEvent represents the meeting to be started
*/

	public void startMeeting(StartMeetEvent event )throws InterruptedException{
	
		
		try{
				//redLed.low();
				yellowLed.toggle();
				Date initialDateConverted = Util.convertDateTimetoDate(event.getMeeting().getStartTime());
				Date endDateConverted = Util.convertDateTimetoDate(event.getMeeting().getEndTime());
				long difference = endDateConverted.getTime() - initialDateConverted.getTime();
				long readingTimeRFID = difference/5;
				long maxInactivityTime = difference/10;
				boolean rfidTagMatched = false ;
				boolean timeIsOver = false;
				String readTag;		
				long initialTimeInMs = new Date().getTime();
				
			    while(new Date().getTime()-initialTimeInMs<=readingTimeRFID){
					System.out.println("Reading Tag");
					readTag =readRfid.readTag();
					//if(SHA256(readTag).equals(event.getRfidCode())){
					if(true){
						rfidTagMatched = true;
						break;
					}
					else{
						System.out.println("Tag not recognized");
					}
				}
				if(rfidTagMatched){
					yellowLed.low();
					greenLed.toggle();
					System.out.println("Reservation found, you can come in now");
					long startTimeMotion = new Date().getTime();
					System.out.println(new Date().getTime() - startTimeMotion );
					while( new Date().getTime() - startTimeMotion <= maxInactivityTime ){
						System.out.println("Sensing");
					
						if(new Date().getTime() -initialTimeInMs >=difference){
							timeIsOver =true;
							break;
						}
						if(pirMotionsensor.getState().isHigh()){
								System.out.println("Someone was found");
								startTimeMotion =new Date().getTime();
		 				
						}
					}
					if(	!timeIsOver){
							System.out.println("Reservation canceled by Inactivity");
							sendInterruptEvent(interruptReasonEnum.Inactivity, event.getMeeting().getId());
					}else{
							System.out.println("End of Reservation");
										
					}
				}else{
					System.out.println("Reservation canceled");
					sendInterruptEvent(interruptReasonEnum.RFIDTagNotFound, event.getMeeting().getId());
				}
				
        	
			greenLed.low();
			//redLed.toggle();
					
			}catch(ParseException e){
					
			}
			}
			
	
	}


