class HistoryEntry

  attr_reader :employee, :copy
  def initialize(employee)
    @copy = employee.copy
  end

end