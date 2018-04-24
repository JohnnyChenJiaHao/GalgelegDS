/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeleg;

import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Johnny
 */

@WebService
public interface GalgeI {
    @WebMethod public ArrayList<String> getBrugteBogstaver();
    @WebMethod public String getSynligtOrd();
    @WebMethod public void gætBogstav(String bogstav);
    @WebMethod public String logStatus();
    @WebMethod public boolean erSpilletVundet();
    @WebMethod public boolean erSpilletTabt();         
    @WebMethod public void nulstil();
    @WebMethod public String getOrdet();
}
