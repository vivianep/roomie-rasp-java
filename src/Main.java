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
import com.roomie.StartMeetingECF.StartMeetingEvent.StartMeetingEvent;
import com.roomie.StartMeetingECF.*;
import com.roomie.InterruptMeetingECF.*;
import com.roomie.InterruptMeetingECF.InterruptMeetingEvent.InterruptMeetingEvent;
import com.roomie.InterruptMeetingECF.InterruptMeetingEvent.interruptReasonEnum;
import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;

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


public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	static KaaClient kaaClient;
	private static  EventFamilyFactory eventFamilyFactory;
	private static final String KEYS_DIR = "keys_for_java_event_demo";
	private static String source;
	
	public static Date convertDateTimetoDate(String time) throws ParseException{
		
		return (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		
		
	}
	
	public static String SHA256(String base) {
		        try{
		        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(base.getBytes("UTF-8"));
                StringBuffer hexString = new StringBuffer();
 
                for (int i = 0; i < hash.length; i++) {
                    String hex = Integer.toHexString(0xff & hash[i]);
                    if(hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
 
              return   hexString.toString();
            } catch(Exception ex){
               throw new RuntimeException(ex);
            }
        }
	
	
	
	public static void sendInterruptEvent(interruptReasonEnum reason, int bookingId) throws InterruptedException{
	
	
	    List<String> FQNs = new LinkedList<String>();
		FQNs.add(InterruptMeetingEvent.class.getName());
		InterruptMeetingECF interruptMeetingECF = eventFamilyFactory.getInterruptMeetingECF();
			
		final CountDownLatch interruptMeetingLatch = new CountDownLatch(1);
		try{
		kaaClient.findEventListeners(FQNs, new FindEventListenersCallback(){
		
			@Override
			public void onEventListenersReceived(List<String> eventListeners) {
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
		event.setBookingId(bookingId);
		interruptMeetingECF.sendEventToAll(event);
		System.out.println("Event Sent");	     			
        }catch(InterruptedException e ){
        
        }
        

}


	


	public static  void main(String[] args) throws ClassNotFoundException, IOException,InterruptedException {
		
	//KaaClientProperties endpointProperties = new KaaClientProperties();
	//endpointProperties.setWorkingDirectory(KEYS_DIR);
    //Create the Kaa desktop context for the application
//	DesktopKaaPlatformContext desktopKaaPlatformContext = new DesktopKaaPlatformContext(endpointProperties);
	final GpioController gpio = GpioFactory.getInstance(); 
	final GpioPinDigitalInput pirMotionsensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
	final GpioPinDigitalOutput yellowLed= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.LOW);
    final GpioPinDigitalOutput greenLed= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLED", PinState.LOW);
    final GpioPinDigitalOutput redLed= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.LOW);
                
	ReadRfid rfidReader = new ReadRfid();
					
	//try{	

	CountDownLatch startupLatch = new CountDownLatch(1);
	//kaaClient = Kaa.newClient( desktopKaaPlatformContext, new SimpleKaaClientStateListener() {
      
	
		kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {
		@Override
			public void onStarted() {
				System.out.println("Kaa client iniciado");
				startupLatch.countDown();
			}

			@Override
			public void onStopped() {
				System.out.println("Kaa client parado");
			}
		},false);
		
		kaaClient.start();
		startupLatch.await();
		System.out.println("Before attach");
		
		
		
		eventFamilyFactory = kaaClient.getEventFamilyFactory();
		StartMeetingECF startMeetingECF = eventFamilyFactory.getStartMeetingECF();
	
		
		startMeetingECF.addListener(new StartMeetingECF.Listener() {
		
			@Override
			public void onEvent(StartMeetingEvent event, String source)  {
			
			try{ 
				redLed.low();
				yellowLed.toggle();
				Date initialDateConverted = convertDateTimetoDate(event.getStartTime());
				Date endDateConverted = convertDateTimetoDate(event.getEndTime());
				long difference = endDateConverted.getTime() - initialDateConverted.getTime();
				long readingTimeRFID = difference/5;
				long maxInactivityTime = difference/10;
				boolean rfidTagMatched = false ;
				boolean timeIsOver = false;
				String readTag;		
				long initialTimeInMs = new Date().getTime();
			    while(new Date().getTime()-initialTimeInMs<=readingTimeRFID){
					System.out.println("Reading Tag");
					readTag =rfidReader.readTag();
					if(SHA256(readTag).equals(event.getRfidCode())){
						rfidTagMatched = true;
						break;
					}else{
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
							sendInterruptEvent(interruptReasonEnum.Inactivity, event.getBookingId());
					}else{
							System.out.println("End of Reservation");
										
					}
				}else{
					System.out.println("Reservation canceled");
					sendInterruptEvent(interruptReasonEnum.RfidTagNotFound, event.getBookingId());
				}
				
        	
				
			greenLed.low();
			redLed.toggle();
			}catch(InterruptedException e){
					
			}catch(ParseException e){
					
			}					   
            }
		   
		});
		   
        
	
		
            // Attach the endpoint to the user
            // This demo application uses a trustful verifier, therefore
            // any user credentials sent by the endpoint are accepted as valid.
            final CountDownLatch attachLatch = new CountDownLatch(1);
            kaaClient.attachUser("userVerifier", "37412542592082044091", new UserAttachCallback() {
                
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

				
            attachLatch.await();
            redLed.toggle();
            //sendInterruptEvent("RFIDTagNotFound", 1);
            System.out.println("After await");
		/*}catch(InterruptedException e){
					
					
			}
			*/	
           }
           
           }
           					   
     
           
