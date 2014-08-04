require 'aquarium'
require 'employee'
require 'ranking_error'

module Ranking
  include Aquarium::Aspects
  def self.enforce_ranking(to_rank, company)
    Aspect.new :after, :object => to_rank, :calls_to => [:cut,:salary=] do |jp, emp, *args|
      ranking_constraint = RankingConstraint.new(company)
      ranking_constraint.align to_rank
    end
  end
end