class Company
  attr_accessor :name, :departments
  
  def initialize(name)
    @name = name
    @departments = Array.new;
  end
  
  def cut
    @departments.each { |d| d.cut }
  end
end
