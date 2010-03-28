def count_by(start, step)
  start -= step
  lambda { start += step}
end

a = count_by(10, 3)

puts a.call
puts a.call
puts a.call
