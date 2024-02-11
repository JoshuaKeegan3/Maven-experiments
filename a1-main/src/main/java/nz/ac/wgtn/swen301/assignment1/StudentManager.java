package nz.ac.wgtn.swen301.assignment1;

import nz.ac.wgtn.swen301.studentdb.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A student managers providing basic CRUD operations for instances of Student, and a read operation for instances of Degree.
 * @author jens dietrich
 */
public class StudentManager {

    // DO NOT REMOVE THE FOLLOWING -- THIS WILL ENSURE THAT THE DATABASE IS AVAILABLE
    // AND THE APPLICATION CAN CONNECT TO IT WITH JDBC
    static {
        StudentDB.init();
    	init();
    }
    // DO NOT REMOVE BLOCK ENDS HERE

    // THE FOLLOWING METHODS MUST IMPLEMENTED :

    private static int size;
    private static PreparedStatement readStudentStatment ;
    private static PreparedStatement readDegreeStatment ;
    private static PreparedStatement deleteStudentStatment ;
    private static PreparedStatement updateStudentStatment ;
    private static PreparedStatement createStudentStatment ;
    private static PreparedStatement getAllStudentIdsStatment ;
    private static PreparedStatement getAllDegreeIdsStatment ;
    private static Connection con;

    private static Map<String, Student> studentMap = new HashMap<>();
    private static Map<String, Degree> degreeMap = new HashMap<>();

    private static void init(){
    	try {
	    	if (con ==null) {
		    	String url = "jdbc:derby:memory:studentdb";
		    	con = DriverManager.getConnection(url);
		    	readStudentStatment 	 = con.prepareStatement("SELECT * FROM STUDENTS WHERE id = ?");
		    	readDegreeStatment 		 = con.prepareStatement("SELECT * FROM DEGREES WHERE id = ?");
		    	deleteStudentStatment  	 = con.prepareStatement("DELETE FROM STUDENTS WHERE id = ?");

		    	updateStudentStatment 	 = con.prepareStatement("UPDATE STUDENTS SET "
		    			+ "first_name = ?, name = ?, degree = ? WHERE id = ?");

		    	createStudentStatment  	 = con.prepareStatement("INSERT INTO STUDENTS "
		    			+ "VALUES (?, ?, ?, ?)");
		    	getAllStudentIdsStatment  = con.prepareStatement("SELECT id FROM STUDENTS");
		    	getAllDegreeIdsStatment   = con.prepareStatement("SELECT id FROM DEGREES");
		        studentMap = new HashMap<>();
		        degreeMap = new HashMap<>();
	    	}
    	} catch(Exception e){
    		System.exit(0);
    	}


    }

    /**
     * Return a student instance with values from the row with the respective id in the database.
     * If an instance with this id already exists, return the existing instance and do not create a second one.
     * @param id
     * @return
     * @throws NoSuchRecordException if no record with such an id exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_readStudent (followed by optional numbers if multiple tests are used)
     */
    public static Student readStudent(String id) throws NoSuchRecordException {
    	if(studentMap.containsKey(id)) {
    		return studentMap.get(id);
    	}
    	init();
    	try {
	    	readStudentStatment.setString(1, id);
	    	readStudentStatment.execute();
	    	ResultSet results = readStudentStatment.getResultSet();
	    	while (results.next()) {
	    		if (results.getString("id").equals(id)) {
		    		String first_name = results.getString("first_name");
		    		String name = results.getString("name");
		    		String degree_id = results.getString("degree");
					Degree degree = readDegree(degree_id);
					Student s = new Student(id, first_name, name, degree);
					studentMap.put(id, s);

		    		return s;
	    		}
	    	}
    	}
	    catch(Exception e){
	    	throw new NoSuchRecordException();
	    }

    	throw new NoSuchRecordException();
    }

    /**
     * Return a degree instance with values from the row with the respective id in the database.
     * If an instance with this id already exists, return the existing instance and do not create a second one.
     * @param id
     * @return
     * @throws NoSuchRecordException if no record with such an id exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_readDegree (followed by optional numbers if multiple tests are used)
     */
    public static Degree readDegree(String id) throws NoSuchRecordException {
    	if(degreeMap.containsKey(id)) {
    		return degreeMap.get(id);
    	}
    	init();
    	try {
	    	readDegreeStatment.setString(1, id);
	    	readDegreeStatment.execute();
	    	ResultSet results = readDegreeStatment.getResultSet();
	    	while (results.next()) {
	    		if (results.getString("id").equals(id)) {
		    		String name = results.getString("name");
					Degree s = new Degree(id, name);
					degreeMap.put(id, s);
		    		return new Degree(id, name);
	    		}
	    	}
    	}
	    catch(Exception e){
	    	throw new NoSuchRecordException();
	    }
    	throw new NoSuchRecordException();
    }

    /**
     * Delete a student instance from the database.
     * I.e., after this, trying to read a student with this id will result in a NoSuchRecordException.
     * @param student
     * @throws NoSuchRecordException if no record corresponding to this student instance exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_delete
     */
    public static void delete(Student student) throws NoSuchRecordException {

    	init();
    	try {
	    	deleteStudentStatment.setString(1, student.getId());
	    	deleteStudentStatment.execute();
	    	studentMap.remove(student.getId());
    	}
	    catch(Exception e){
	    	throw new NoSuchRecordException();
	    }
    }

    /**
     * Update (synchronize) a student instance with the database.
     * The id will not be changed, but the values for first names or degree in the database might be changed by this operation.
     * After executing this command, the attribute values of the object and the respective database value are consistent.
     * Note that names and first names can only be max 1o characters long.
     * There is no special handling required to enforce this, just ensure that tests only use values with < 10 characters.
     * @param student
     * @throws NoSuchRecordException if no record corresponding to this student instance exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_update (followed by optional numbers if multiple tests are used)
     */
    public static void update(Student student) throws NoSuchRecordException {
    	init();
    	try {
	    	updateStudentStatment.setString(1, student.getFirstName());
	    	updateStudentStatment.setString(2, student.getName());
	    	updateStudentStatment.setString(3, student.getDegree().getId());
	    	updateStudentStatment.setString(4, student.getId());
	    	updateStudentStatment.execute();
	    	studentMap.remove(student.getId());
	    	studentMap.put(student.getId(), student);
		}
	    catch(Exception e){
	    	throw new NoSuchRecordException();
	    }

    }

    /**
     * Create a new student with the values provided, and save it to the database.
     * The student must have a new id that is not been used by any other Student instance or STUDENTS record (row).
     * Note that names and first names can only be max 1o characters long.
     * There is no special handling required to enforce this, just ensure that tests only use values with < 10 characters.
     * @param name
     * @param firstName
     * @param degree
     * @return a freshly created student instance
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_createStudent (followed by optional numbers if multiple tests are used)
     */
    public static Student createStudent(String name,String firstName,Degree degree) {
    	init();
    	try {
	    	Statement s = con.createStatement();

	    	ResultSet r = s.executeQuery("SELECT max(id) FROM STUDENTS");
	    	r.next();
		    String v = r.getString(1);

	    	size = Integer.parseInt(v.substring(2));
	    	String id = "id"+(++size);
	    	createStudentStatment.setString(1, id);
	    	createStudentStatment.setString(2, name);
	    	createStudentStatment.setString(3, firstName);
	    	createStudentStatment.setString(4, degree.getId());

	    	createStudentStatment.execute();

	        return new Student(id,name,firstName, degree);
    	}
        catch(Exception e){
        	return null;
        }
    }

    /**
     * Get all student ids currently being used in the database.
     * @return
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_getAllStudentIds (followed by optional numbers if multiple tests are used)
     */
    public static Collection<String> getAllStudentIds() throws SQLException {
    	ResultSet rs = getAllStudentIdsStatment.executeQuery();

    	List<String> l = new ArrayList<String>();
    	while (rs.next()) {
    		l.add(rs.getString("id"));
    	}
        return l;
    }

    /**
     * Get all degree ids currently being used in the database.
     * @return
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_getAllDegreeIds (followed by optional numbers if multiple tests are used)
     */
    public static Iterable<String> getAllDegreeIds() throws SQLException{
    	ResultSet rs = getAllDegreeIdsStatment.executeQuery();

    	List<String> l = new ArrayList<String>();
    	while (rs.next()) {
    		l.add(rs.getString("id"));
    	}
        return l;
    }

	public static void reset() {
    	try {
    	    readStudentStatment.close();
    	    readDegreeStatment.close() ;
    	    deleteStudentStatment.close() ;
    	    updateStudentStatment.close();
    	    createStudentStatment.close();
    	    getAllStudentIdsStatment.close();
    	    getAllDegreeIdsStatment.close();
			con.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		StudentDB.init();

    	con = null;

		init();
	}
}
