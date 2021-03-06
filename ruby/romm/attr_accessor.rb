module Accessor
  def my_attr_accessor(var)
    ivar_name = "@#{var}"
    
    define_method(var) do
      instance_variable_get(ivar_name)
    end
    
    define_method("#{var}=") do |val|
      puts "#{var} is getting set to #{val}"
      instance_variable_set(ivar_name, val)
    end
  end
end

class Example
  extend Accessor
  my_attr_accessor :somevar
end

e = Example.new
e.somevar = "asdf"
puts e.somevar
