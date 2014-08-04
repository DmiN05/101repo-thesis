require 'log_entry'

class CompanyLogger

  attr_accessor :logs
  def initialize
    @logs = Array.new
  end

  def log(name, old_salary, new_salary)
    entry = LogEntry.new(name,old_salary,new_salary)
    @logs.push entry

    entry
  end

  def to_deltas
    deltas = Array.new
    @logs.each { |log| deltas.push(log.new_salary - log.old_salary) }
    deltas.sort.reverse
  end
  
  def median
    deltas = self.to_deltas
    deltas[deltas.length / 2]
  end

end