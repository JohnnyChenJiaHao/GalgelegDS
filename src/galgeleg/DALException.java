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
public class DALException extends Exception{
	private static final long serialVersionUID = -5490114628089339322L;
	public DALException(String message) { super(message); }    
	public DALException(Exception e) { super(e); }
}
