package controllers;

import play.mvc.Controller;
import play.mvc.With;
import controllers.casino.Check;
import controllers.casino.Secure;

//make sure controller only works when authenticated

@With(Secure.class)
public class SecureController extends Controller {

	@Check("isConnected")
    public static void index() {
        render();
    }

	@Check("role:admin")
    public static void adminOnly() {
        render();
    }
    
	@Check("role:superadmin")
    public static void superadminOnly() {
        render();
    }
    
}