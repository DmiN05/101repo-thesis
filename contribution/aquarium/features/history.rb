require 'aquarium'
require 'employee'
require 'history_factory'

module History

  include Aquarium::Aspects
  
  @history = HistoryFactory.new
  
  def self.history
    @history.history
  end
  
  # define a list with historizable classes
  historizable = [Employee]

  # before advice, any attribute access via reader, and cut method on employee and subtypes gets advised
  Aspect.new :before, :attribute => /.*/, :attribute_options => [:writers], :calls_to => [:cut], :on_types_and_descendents => historizable do |join_point, emp, *args|
    @history.historize emp
  end

end

# reopen employee class to add new method
class Employee
  def copy
    copy = Employee.new(@name,@address,@salary)
    copy
  end
end