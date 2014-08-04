class LogEntry
  attr_accessor :name,:old_salary,:new_salary
  
  def initialize(name,old_salary,new_salary)
    @name = name
    @old_salary = old_salary
    @new_salary = new_salary
  end
  
  def to_s
    "LogEntry {name="+@name+", oldSalary="+@old_salary+", newSalary="+@new_salary+"}"
  end
end