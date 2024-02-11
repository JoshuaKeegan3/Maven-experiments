package test.nz.ac.wgtn.swen301.assignment1;

import nz.ac.wgtn.swen301.assignment1.StudentManager;
import nz.ac.wgtn.swen301.studentdb.Degree;
import nz.ac.wgtn.swen301.studentdb.Student;
import nz.ac.wgtn.swen301.studentdb.StudentDB;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;

import org.junit.After;


public class TestStudentManager {

    // DO NOT REMOVE THE FOLLOWING -- THIS WILL ENSURE THAT THE DATABASE IS AVAILABLE
    // AND IN ITS INITIAL STATE BEFORE EACH TEST RUNS
    @Before
    public void init () {
    	StudentDB.init();
    }
    // DO NOT REMOVE BLOCK ENDS HERE

    @After
    public void reset() {
    	StudentManager.reset();
    }
    @Test
    public void test_readStudent() throws Exception{
        new StudentManager();
		Student student = StudentManager.readStudent("id0");

        // THIS WILL INITIALLY FAIL !!
        assertNotNull(student);
        assertEquals(student, new Student("id0", "James", "Smith",
        		new Degree("deg0","BSc Computer Science")));
    }
    @Test
    public void test_readDegree() throws Exception {
        new StudentManager();
		Degree degree = StudentManager.readDegree("deg0");
        // THIS WILL INITIALLY FAIL !!
        assertNotNull(degree);
        assertEquals(degree, new Degree("deg0","BSc Computer Science"));
    }
    @Test
    public void test_delete() throws Exception {
        new StudentManager();
		StudentManager.delete(new Student("id0", "James", "Smith",
        		new Degree("deg0","BSc Computer Science")));
        // THIS WILL INITIALLY FAIL !!
		try {
			Student student = StudentManager.readStudent("id0");
			assertFalse(true);
		} catch(Exception e){
			assertFalse(false);
		}
    }
    @Test
    public void test_update() throws Exception {
        new StudentManager();
        Student s = new Student("id0", "X", "X",
        		new Degree("deg0","BSc Computer Science"));
		StudentManager.update(s);
        // THIS WILL INITIALLY FAIL !!
		Student student = StudentManager.readStudent("id0");
		assertEquals("X",student.getName());
    }
    @Test
    public void test_createStudent() throws Exception {
        new StudentManager();
        int before = StudentManager.getAllStudentIds().size();
		StudentManager.createStudent("Jimmy", "Barnes", new Degree("deg0","BSc Computer Science"));
		assert before + 1 == StudentManager.getAllStudentIds().size();
    }
    @Test
    public void test_getAllStudentIds() throws Exception {
        new StudentManager();
        Iterator<String> c = StudentManager.getAllStudentIds().iterator();
        for(int i=0;i<100;i++) {
        	assert StudentManager.readStudent(c.next()) != null;
        }
		assert 15 < StudentManager.getAllStudentIds().size();
    }
    @Test
    public void test_getAllDegreeIds() throws Exception {
        new StudentManager();
        Iterator<String> iter = StudentManager.getAllDegreeIds().iterator();
        for(int i=0;i<5;i++) {
        	assert StudentManager.readDegree(iter.next()) != null;
        }
    }

    @Test
    public void test_performance() throws Exception {
    	new StudentManager();
    	Random random = new Random();
    	long startTime = System.currentTimeMillis();
    	for(int i=0; i<1000; i++) {
    	    StudentManager.readStudent("id"+random.nextInt(1000));
    	}
    	long endTime = System.currentTimeMillis();
    	long t = endTime - startTime;
    	assert t < 1000;
    }

}
