package org.softlang.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.softlang.utils.Serialization.readCompany;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;
import org.softlang.company.Company;
import org.softlang.company.Department;
import org.softlang.company.Employee;
import org.softlang.utils.CompanyDataAccess;
import org.softlang.utils.RankingConstraintException;

public class FeatureTests {

	Company sampleCompany;

	@Before
	public void loadSampleCompany() {
		sampleCompany = readCompany("sampleCompany.ser");
	}

	@Test
	public void testCut() {
		sampleCompany.cut();

		// check median of deltas
		assertEquals(-6172.5, sampleCompany.deltaMedian, 0.0);

		// check also mean of deltas
		assertEquals(-28553.4, sampleCompany.deltaMean, 0.1);
	}

	@Test
	public void testHistorization() {
		// cut company's salaries to create a brief history for each employee
		sampleCompany.cut();
		sampleCompany.cut();
		sampleCompany.cut();
		List<Double> employeeMedians = Arrays.asList(6172.5, 617.0);
		for (Department d : sampleCompany.getDepts()) {
			ListIterator<Employee> it = d.getEmployees().listIterator();
			while (it.hasNext()) {
				int index = it.nextIndex();
				Employee e = it.next();
				// every employee has 3 history entries
				assertEquals(3, e.getHistory().size());
				double median = sampleCompany.getMedianForEmployee(e);
				assertTrue(median == employeeMedians.get(index));
			}
		}
	}

	@Test(expected = RankingConstraintException.class)
	public void testRanking() {
		Company c = new Company();

		Department d1 = new Department();

		Department subD1 = new Department();
		d1.getSubdepts().add(subD1);
		subD1.setParent(d1);

		c.getDepts().add(d1);

		Employee manager = new Employee();
		manager.setSalary(34000);
		d1.setManager(manager);
		manager.setDepartment(d1);

		Employee e1 = new Employee();
		e1.setSalary(30000);
		e1.setDepartment(d1);
		d1.getEmployees().add(e1);

		Employee e2 = new Employee();

		d1.getEmployees().add(e1);
		e1.setDepartment(d1);

		subD1.setManager(e2);
		e2.setDepartment(subD1);

		e2.setSalary(20000);

		Employee e3 = new Employee();
		subD1.getEmployees().add(e3);

		// this one fails
		e3.setDepartment(subD1);
		e3.setSalary(21000);
	}

	@Test
	public void testMedian() {
		assertEquals(12345.0, sampleCompany.getMedian(), 0.1);
	}

	@Test
	public void testPersistence() throws Exception {
		CompanyDataAccess dao = new CompanyDataAccess();
		
		dao.insertCompany(sampleCompany);
		
		Company c = dao.findCompany();
		System.out.println(c.getName());
	}

}
