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

import com.roomie.motion.*;
import com.roomie.motion.motionFields;
import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	static KaaClient kaaClient;
	private static final String KEYS_DIR = "keys_for_java_event_demo";
	public static <MotionEvent> void main(String[] args) throws ClassNotFoundException, IOException {
		
	KaaClientProperties endpointProperties = new KaaClientProperties();
	endpointProperties.setWorkingDirectory(KEYS_DIR);
    //Create the Kaa desktop context for the application
	DesktopKaaPlatformContext desktopKaaPlatformContext = new DesktopKaaPlatformContext(endpointProperties);
	try{	

	CountDownLatch startupLatch = new CountDownLatch(1);
	kaaClient = Kaa.newClient(desktopKaaPlatformContext, new SimpleKaaClientStateListener() {
      
	
		//kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {
		@Override
			public void onStarted() {
				System.out.println("Kaa client iniciado");
				startupLatch.countDown();
			}

			@Override
			public void onStopped() {
				System.out.println("Kaa client parado");
			}
		}, true);
		
		kaaClient.start();
		startupLatch.await();
		System.out.println("Before attach");
		
		
		
		EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
		MotionECF motionCf = eventFamilyFactory.getMotionECF();
	
		motionCf.addListener(new MotionECF.Listener() {
		    

			@Override
			public void onEvent(motionFields event, String source) {
				System.out.println("Event Received "+ event.getIsPresent());
				
			}
		    
		});
		
	
		try {
            // Attach the endpoint to the user
            // This demo application uses a trustful verifier, therefore
            // any user credentials sent by the endpoint are accepted as valid.
            final CountDownLatch attachLatch = new CountDownLatch(1);
            kaaClient.attachUser("userverifier", "53485055969391987719", new UserAttachCallback() {
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
            // EventUtil.sleepForSeconds(3);
        } catch (InterruptedException e) {
            LOG.warn("Thread interrupted when wait for attach current endpoint to user", e);
        }
    

		System.out.println("After attach");
		List<String> FQNs =  new LinkedList<String>();
		FQNs.add(motionFields.class.getName());
		final CountDownLatch listenersLatch = new CountDownLatch(1);
          
		kaaClient.findEventListeners(FQNs, new FindEventListenersCallback() {
	    @Override
	    public void onEventListenersReceived(List<String> eventListeners) {
	        System.out.println("Event listeners received with size " + eventListeners.size() );
	    	listenersLatch.countDown();
  
	    }   
	    @Override
	    public void onRequestFailed() {
	        // Some code
	    }
	});	
		listenersLatch.await();
			motionFields motion = new motionFields();
			motion.setIsPresent(true);
			motion.setTimeTag("ok");
			motionCf.sendEventToAll(motion);	
			System.out.println("Message sent" );
			
		// TODO Auto-generated method stub
		/*ConnectionFactory cf = new ConnectionFactory();
		try {
			Connection connection = ConnectionFactory.createConnection();
		
		 System.out.println("Creating statement...");
	     ResultSet rs = cf.retrieveData(connection, "SELECT * FROM roomie.user");

	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	         int id  = rs.getInt("user_id");
	         String name = rs.getString("name");
	         String email = rs.getString("email");

	         //Display values
	         System.out.print("ID: " + id);
	         System.out.print(", Name: " + name);
	         System.out.print(", Email: " + email);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();
	      connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		} catch (InterruptedException e) {
            LOG.warn("Thread interrupted when wait for attach current endpoint to user", e);
        }		
	}

}
