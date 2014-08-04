$LOAD_PATH << File.dirname(__FILE__) + "/company/"
$LOAD_PATH << File.dirname(__FILE__) + "/features/"
$LOAD_PATH << File.dirname(__FILE__) + "/utils/"

require 'employee'
require 'company'
require 'department'
require 'freelancer'
require 'logging'
require 'history'
require 'ranking'
require 'serialization'
require 'history_factory'
require 'ranking_constraint'

# deserialize sample company
sample_company =  Serialization.load('sampleCompany.yaml')

# ensure log entries get created
sample_company.cut

# test historization
e1 = sample_company.departments[0].manager
e1.address="Moscow"
p History.history[e1] # prints object status with 2 different history entries

# show that freelancer also get historization on all their attributes
f1 = Freelancer.new("Dmitri","St.Petersburg",2342.0)
f1.amount_of_work=23
p History.history[f1] # shows freelancer's history

# enforce e1 to get ranked for sample_company
Ranking.enforce_ranking(e1, sample_company)

# no ranking error as salary is low enough
e1.salary=10059.0

# not ranked employee doesn't raise ranking errors with his high salary
sample_company.departments[0].employees << f1
f1.salary=12434.0

# this one will throw a ranking error
begin
  e1.salary=10669.0 # 1 money unit too much
rescue RankingError => e
  p e.message
end
