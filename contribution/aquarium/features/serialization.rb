require 'yaml'

module Serialization
  
  def self.write(company, file_name)
    company_as_string = YAML::dump(company)
    File.open(file_name, 'w+') { |file| file.write(company_as_string) }
  end
  
  def self.load(file_name)
    content = File.read(file_name)
    
    YAML::load(content)
  end
  
end