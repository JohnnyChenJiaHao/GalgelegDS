/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeleg;

/**
 *
 * @author jonathan
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class UserDTO {
    private static final long serialVersionUID = -7272979590540794430L;
	public int id;
	public String StudentId;                     
	
	int number_of_tries; 
	
	double time_used;     
	
	double score;                 
	;

	public UserDTO(){
	}
        
   
        public int getID(){return id;}
        
        public void setStudentID(String StudentId){
        this.StudentId = StudentId;};
        public String getStudentID(){return StudentId;}
        
        public void setNumber_of_tries(int tries){
        this.number_of_tries = tries;};
        public int getNumber_of_tries(){return number_of_tries;}
        
        public void setTime_used(double time){
        this.time_used = time;};
        public double getTime_used(){return time_used;}
        
        public void setScore(double score){
        this.score = score;};
        public double getScore(){return score;}
        
    
	public String toString() { return "\n" + StudentId + "\t" + number_of_tries + "\t" + time_used + "\t" + score; }
}
