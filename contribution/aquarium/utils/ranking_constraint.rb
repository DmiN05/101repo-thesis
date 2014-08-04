require 'ranking_error'

class RankingConstraint
  def initialize(company)
    @company = company
  end

  def align(employee)
    check_employee(employee) if self.all_employees.include? employee
  end

  def check_employee(employee)
    medium_salary = all_employees.keep_if{|emp| emp != employee}.compact.map{|emp| emp.salary}.reduce(:+) / all_employees.size

    p "medium salary #{medium_salary}"
    p "medium salary * 1.25 #{medium_salary*1.25}"

    filtered_employees = all_employees.keep_if { |emp| emp != employee and 1.25 * medium_salary >= employee.salary }.compact

    raise RankingError.new,"Employee #{employee.name} earns too much: #{employee.salary}." if filtered_employees.empty?
  end

  def all_employees
    all_depts = Array.new
    all_depts << @company.departments
    @company.departments.each do |dep|
      all_depts << collect_departments(dep)
    end
    collect_employees(all_depts.flatten)
  end

  def collect_departments(department)
    depts = Array.new
    depts << department.sub_departments
    department.sub_departments.each do |sub_dep|
      depts << collect_departments(sub_dep)
    end
    depts
  end

  def collect_employees(departments)
    emps = Array.new
    departments.each do |dep|
      emps << dep.manager
      emps << dep.employees
    end
    emps.flatten
  end

end