package main;

import brugerautorisation.data.Diverse;
import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import galgeleg.GalgeI;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import database.UserDAOSOAPI;
import galgeleg.UserDTO;

/**
 *
 * @author j
 */

public class main {
    static Brugeradmin ba;
    static String guess;
    static long tStart, tEnd, tDelta;
    static double elapsedSeconds, temp1, temp2, temp3;
    static GalgeI game;
    static UserDAOSOAPI game2;
    static Thread sc;
    static String username;
	public static void main(String[] args) throws MalformedURLException {
//		URL url = new URL("http://localhost:9901/brugeradmin?wsdl");
		URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
		QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
		Service service = Service.create(url, qname);
                ba = service.getPort(Brugeradmin.class);
                
                //URL url2 = new URL("http://localhost:9943/galgeleg?wsdl");
                URL url2 = new URL("http://ubuntu4.saluton.dk:9913/galgeleg?wsdl");
                QName qname2 = new QName("http://galgeleg/", "GalgelogikService");
                Service service2 = Service.create(url2, qname2);
                game = service2.getPort(GalgeI.class);
                
                //URL url3 = new URL("http://localhost:9943/galgeleg?wsdl");
                URL url3 = new URL("http://ubuntu4.saluton.dk:9911/SQL_Soap?wsdl");
                QName qname3 = new QName("http://database/", "SOAPImplService");
                Service service3 = Service.create(url3, qname3);
                game2 = service3.getPort(UserDAOSOAPI.class);
                
                
                
        game.nulstil();
        System.out.println("Welcome to Hangman - Please login!");
        
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter username: ");
            username = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            
            if(Login(username, password)){break;}
            else{System.out.println("Wrong credentials. Please try again..");}
            
        }
        System.out.println("You are now logged in, please enjoy the game :)" );
        System.out.println(game.logStatus());
        
        //starting timer & score announcer
        tStart = System.currentTimeMillis();
        
        sc = new Thread(new score());
        sc.start();
        
        while (true) {      
            guess = scanner.nextLine();
            game.gætBogstav(guess);
            System.out.println(game.logStatus());
            
            if (game.erSpilletVundet()) {
                UserDTO user = new UserDTO();
                
                user.setStudentID(username);
                //sc.interrupt();
                getScore();
                user.setScore(temp3);
                user.setTime_used(elapsedSeconds);
                user.setNumber_of_tries(game.getBrugteBogstaver().size());
                
                System.out.println("Score: "+String.format("%.3f", temp3));
                
                game2.createScore(user);
                System.out.println("Congratulations you won! You guessed the word: " + game.getOrdet());
                System.out.println("Type Y to play again");
                
                if (guess.equalsIgnoreCase("Y")){
                    game.nulstil();
                    
                    //starting timer & score announcer
                    tStart = System.currentTimeMillis();
                    sc = new Thread(new score());
                    sc.start();
                    
                    System.out.println(game.logStatus());
                    System.out.println("Guess a word!");
                }
            }
            else if (game.erSpilletTabt()) {
                //sc.interrupt();
                
                //TEMP REMOVE//
                getScore();
                UserDTO user = new UserDTO();
                
                user.setStudentID(username);
                user.setScore(temp3);
                user.setTime_used(elapsedSeconds);
                user.setNumber_of_tries(game.getBrugteBogstaver().size());
                
                System.out.println("Score: "+String.format("%.3f", temp3));
                
                game2.createScore(user);
                
                System.out.println(user.getStudentID());
                System.out.println(user.getNumber_of_tries());
                System.out.println(user.getScore());
                System.out.println(user.getTime_used());
                System.out.println("You lost! The word was: " + game.getOrdet());
                System.out.println("Type Y to play again");
                
                if (guess.equalsIgnoreCase("Y")){
                    game.nulstil();
                    
                    //starting timer & score announcer
                    tStart = System.currentTimeMillis();
                    sc = new Thread(new score());
                    sc.start();
        
                    System.out.println(game.logStatus());
                    System.out.println("Guess a word!");
                }
            }
        }

	}
        public static Boolean Login(String usrname, String password){
               
                try{
                 
                    ba.hentBruger(usrname, password);
                    return true;
                }catch(Exception e){
                    
                    System.out.println("Wrong credentials");
                    return false;
                } 
        }     
        
        public static void getScore(){
                //Grabbing time and converting to seconds.
                tEnd = System.currentTimeMillis();
                tDelta = tEnd- tStart;
                elapsedSeconds = tDelta / 1000.0;
                
                //Random "algorithm" i came up with
                //to calculate score relative to word length,
                //time spend and amount of misses.
                temp1 = elapsedSeconds*(game.getBrugteBogstaver().size()+1);
                temp2 = temp1/(game.getSynligtOrd().length()+1);
                temp3 = (100/temp2)*100;
    }
        
    //Score Thread for calculating and updating score
    public static class score implements Runnable {
        public void run() {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
            getScore();
            System.out.println("Score: "+String.format("%.3f", temp3));

        }
    }
    
    
    
}
