class Department

  attr_accessor :name, :manager, :sub_departments, :employees
  
  def initialize(name)
    @name = name
    @sub_departments = Array.new
    @employees = Array.new
  end
  
  def cut
    @manager.cut if not @manager.nil?
    @sub_departments.each { |sub_dep| sub_dep.cut }
    @employees.each { |e| e.cut}
  end

end