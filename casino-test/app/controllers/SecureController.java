package controllers;

import play.mvc.Controller;
import play.mvc.With;
import controllers.casino.CasinoCheck;
import controllers.casino.SecureCasino;

//make sure controller only works when authentificated

@With(SecureCasino.class)
public class SecureController extends Controller {

    @CasinoCheck("isConnected")
    public static void index() {
        render();
    }

    @CasinoCheck("role:admin")
    public static void adminOnly() {
        render();
    }
    
    @CasinoCheck("role:superadmin")
    public static void superadminOnly() {
        render();
    }
    
}