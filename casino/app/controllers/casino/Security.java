package controllers.casino;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import play.Play;
import play.utils.Java;
import casino.Casino;
import casino.CasinoApplicationConfConstants;

/**
 * A more or less exact copy of the secure implementation of play.
 * - enhanced with a transport guarantee. therefore logins are fixed to a uri eg https://example.com.
 * - enhanced with putting a session on both original and fixed domain 
 * - Secure.Security authentifies against casino.User model
 * 
 * @author ra
 *
 */
public class Security extends Secure.Security {

        /**
         * @Deprecated
         * 
         * @param username
         * @param password
         * @return
         */
        static boolean authentify(String username, String password) {
            throw new UnsupportedOperationException();
        }

        /**
    	 * Extend Play!s security mechanism to authenticate against
    	 * the User object.
    	 */
    	public static boolean authenticate(String username, String password) {

    		if (!Casino.getCasinoUser().isUserActivated(username)) {
    			
    			return false;
    			
    		}
    		
    		String passwordHash = Casino.getCasinoUser().getUserPasswordHash(username);
    		
    		
    		return Casino.doPasswordAndHashMatch(password, passwordHash);

    	}

    	/**
    	 * Annotate your methods using 
    	 * @Check("isConnected")
    	 * 
    	 * check roles with:
    	 * @Check("role:admin")
    	 * or
    	 * @Check("role:admin")
    	 * or more general:
    	 * @Check("role:ROLE") => depending on the roles you have in your app...
    	 * 
    	 * 
    	 * @param check
    	 * @return
    	 */
        public static boolean check(String check) {
        	
        	// Possibility 1: Make sure is connected
        	if ("isConnected".equals(check)) {
        		return Security.isConnected();   		
        	}
        	
        	
        	// Possibility 2: Check for a certain role
        	if (check.startsWith("role:")) {
        		
        		
        		String email = Security.connected();
        		//if user is not logged in role checking does not make sense...
        		if (email == null) {
        			return false;
        		}
        		
        		String [] splittedStuff = check.split(":");
        		
        		if (splittedStuff.length > 1) {
        			
        			String role = splittedStuff[1];
        			
        			//now check if user is in that role and return result...  
        			
        			
        			boolean boole = Casino.getCasinoUser().hasRole(email, role);
        			System.out.println("checking role: " + role + " - " + boole);
         			return boole;
        			
        		}
        		
        	}
        	    	
        	return false;
        }     

        /**
         * This method returns the current connected username
         * @return
         */
        public static String connected() {
            return session.get("username");
        }

        /**
         * Indicate if a user is currently connected
         * @return  true if the user is connected
         */
        public static boolean isConnected() {
            return session.contains("username");
        }

        /**
         * This method is called after a successful authentication.
         * You need to override this method if you with to perform specific actions (eg. Record the time the user signed in)
         */
        static void onAuthenticated() {
        	
        	
        	String secureUrl = Play.configuration.getProperty(CasinoApplicationConfConstants.SECURE_URL, "");
        	
        	//only do stuff if we have two domains:
        	if (!secureUrl.equals("")) {
        		
        		String regularUrl = Play.configuration.getProperty(CasinoApplicationConfConstants.REGULAR_URL, "");
        		
        		if (regularUrl.equals("")) {
        			throw new RuntimeException("Error. Please set " + CasinoApplicationConfConstants.REGULAR_URL + " AND " + CasinoApplicationConfConstants.SECURE_URL + " in application.conf.");
        		}
        		
        		String url = flash.get("url");
        		if (url == null) {
        			url = "/"; 
        		}
            	String username = session.get("username");
            		
            	String token = SessionTransfer.doSetMemcacheToken(username);

                //System.out.println("redirecting and authentificating cookie on other server......");                

                redirect(regularUrl + "/login/auth_via_token?token="+token+"&url="+url);          		
        	}
   	
        }

         /**
         * This method is called before a user tries to sign off.
         * You need to override this method if you wish to perform specific actions (eg. Record the name of the user who signed off)
         */
        static void onDisconnect() {
            
        	String secureUrl = Play.configuration.getProperty(CasinoApplicationConfConstants.SECURE_URL, "");
        	
        	//save username so we can inform the other server (if there is one)
        	String username = session.get("username");
        	
        	//clear stuff on this server => because we redirect the stuff in original logout won't be called...
        	session.clear();
            response.removeCookie("rememberme"); 
              
        	//only do stuff if we have two domains:
        	if (!secureUrl.equals("")) {
        		
        		String regularUrl = Play.configuration.getProperty(CasinoApplicationConfConstants.REGULAR_URL, "");
        		
        		if (regularUrl.equals("")) {
        			throw new RuntimeException("Error. Please set " + CasinoApplicationConfConstants.REGULAR_URL + " AND " + CasinoApplicationConfConstants.SECURE_URL + " in application.conf.");
        		}
        		
        		String url = flash.get("url");
        		if (url == null) {
        			url = "/"; 
        		}


            	String token = SessionTransfer.doSetMemcacheToken(username);
            	
                //System.out.println("redirecting and nulling cookie on other server......");                

                //I assume we are on the secureUrl. Therefore we also sign the other url...
            	redirect(regularUrl + "/logout/auth_via_token?token="+token+"&url="+url);        		
        	}
        	
        	
        }

         /**
         * This method is called after a successful sign off.
         * You need to override this method if you wish to perform specific actions (eg. Record the time the user signed off)
         */
        static void onDisconnected() {
        	

        }

        /**
         * This method is called if a check does not succeed. By default it shows the not allowed page (the controller forbidden method).
         * @param profile
         */
        static void onCheckFailed(String profile) {
            forbidden();
        }

        private static Object invoke(String m, Object... args) throws Throwable {
            Class security = null;
            List<Class> classes = Play.classloader.getAssignableClasses(Security.class);
            if(classes.size() == 0) {
                security = Security.class;
            } else {
                security = classes.get(0);
            }
            try {
                return Java.invokeStaticOrParent(security, m, args);
            } catch(InvocationTargetException e) {
                throw e.getTargetException();
            }
        }

    
}
