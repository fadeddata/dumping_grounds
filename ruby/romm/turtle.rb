class Turtle
  def initialize
    @path = []
  end
  def right(n=1)
    @path << "r"*n
  end
  def left(n=1)
    @path << "l"*n
  end
  def up(n=1)
    @path << "u"*n
  end
  def down(n=1)
    @path << "d"*n
  end
  def path
    @path.join
  end
  def move(&dir)
    instance_eval &dir
  end
end

t = Turtle.new
t.move do
  right(4)
  left
  down(2)
  up
  puts path
end
