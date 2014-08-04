package springAOP;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softlang.aspects.History;
import org.softlang.aspects.Logging;
import org.softlang.company.Company;
import org.softlang.company.Department;
import org.softlang.company.Employee;
import org.softlang.services.EmployeeService;
import org.softlang.utils.CompanyUtils;
import org.softlang.utils.RankingConstraintException;
import org.softlang.utils.Serialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring.xml" })
public class EmployeeTest {

	static void getEmployees(Set<Employee> employees, Company c) {
		for (Department d : c.getDepts()) {
			getEmployees(employees, d);
		}
	}

	static void getEmployees(Set<Employee> employees, Department d) {
		employees.add(d.getManager());
		for (Department subDep : d.getSubdepts()) {
			getEmployees(employees, subDep);
		}
		employees.addAll(d.getEmployees());
	}

	@Autowired
	private EmployeeService service;

	@Autowired
	private Logging logging;

	@Autowired
	private History history;

	private Company sampleCompany;
	
	private Set<Employee> allEmployees = new HashSet<Employee>();

	@Before
	public void setUp() {
		sampleCompany = Serialization.readCompany("sampleCompany.ser");
		 getEmployees(allEmployees, sampleCompany);
	}

	@Test
	public void testLogging() {
		for (Employee e : allEmployees) {
			service.cut(e);
		}
		// check median of deltas
		Assert.assertEquals(-6172.5,
				CompanyUtils.calculateMedian(logging.getLogger().getDeltas()),
				0.1);

		// check also mean of deltas
		assertEquals(-28553.4,
				CompanyUtils.calculateMean(logging.getLogger().getDeltas()),
				0.1);
	}

	@Test
	public void testHistorization() {
		for(Employee e : allEmployees) {
			service.cut(e);
			service.cut(e);
			service.setEmployeeAddress(e, "another new address");
			service.setEmployeeName(e, "new name");
			service.setEmployeeSalary(e, 1234);
			
			assertEquals(5, history.getHistory(e).size());
		}
	}
	
	
	@Test(expected=RankingConstraintException.class)
	@SuppressWarnings("unused")
	public void testRanking() {
		Company c = new Company();

		Department d1 = new Department();

		Department subD1 = new Department();
		d1.getSubdepts().add(subD1);
		subD1.setParent(d1);

		c.getDepts().add(d1);
		
		Employee e1 = service.createEmployee("E1", "E1addr", 30000.0, d1);

		// this should fail, because sub dept employees aren't allowed to earn more then their higher colleagues
		Employee e2 = service.createEmployee("E2", "E2addr", 31000.0, subD1);
	}

}
