require 'aquarium'
require 'company_logger'

module Logging
  include Aquarium::Aspects
  
  @logger = CompanyLogger.new

  def self.logger
    @logger
  end
  
  call_cut = JoinPoint.new :type => Employee, :method => :cut, :on_types => [Employee]
  write_salary = JoinPoint.new :type => /^Employee/, :method => :salary=, :on_types => [Employee]
  
  Aspect.new :around, :pointcuts => [call_cut, write_salary] do |join_point, emp, *args|
    old_salary = emp.salary
    join_point.proceed
    p @logger.log(emp.name,old_salary,emp.salary)
  end
end