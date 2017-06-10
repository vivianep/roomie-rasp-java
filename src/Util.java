import rfid.*;
import java.io.IOException;
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
import java.io.Console;
import java.lang.System;


public class Util{
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
		}
