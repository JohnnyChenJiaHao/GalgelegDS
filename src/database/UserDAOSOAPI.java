/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import galgeleg.DALException;
import galgeleg.UserDTO;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author jespe
 */

@WebService
public interface UserDAOSOAPI {
    @WebMethod UserDTO getStudent(String student_id) throws DALException;
    @WebMethod List<UserDTO> getStudentList() throws DALException;
    @WebMethod void createScore(UserDTO user);
}
