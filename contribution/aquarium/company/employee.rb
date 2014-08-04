class Employee
  attr_accessor :name, :address, :salary
  
  def initialize(name, address, salary)
    @name = name
    @address = address
    @salary = salary
  end
  
  def cut
    @salary = @salary.fdiv(2)
  end
end