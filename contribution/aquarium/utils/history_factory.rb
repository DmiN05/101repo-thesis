require 'history_entry'

class HistoryFactory

  attr_reader :history
  def initialize
    @history = Hash.new
  end

  def historize(to_historize)
    if @history[to_historize].nil?
      @history[to_historize] = Array.new
    end
    entry = HistoryEntry.new to_historize
    @history[to_historize].push entry
  end

end