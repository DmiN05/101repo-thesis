class Freelancer < Employee
  attr_accessor :amount_of_work
  
  # freelancer get paid by their amount of work they did
  def get_total_salary
    @salary * @amount_of_work
  end
end