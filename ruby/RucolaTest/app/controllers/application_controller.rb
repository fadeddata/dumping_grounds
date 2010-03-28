class ApplicationController < Rucola::RCController
  include OSX
  ib_outlet :main_window
  ib_outlet :browser
  ib_outlet :code_field
  
  def awakeFromNib
    # All the application delegate methods will be called on this object.
    OSX::NSApp.delegate = self
    
    puts "ApplicationController awoke."
    puts "Edit: app/controllers/application_controller.rb"
    puts  "\nIts window is: #{@main_window.inspect}"
    @methods = []
    
  end
  
  # NSApplication delegate methods
  def applicationDidFinishLaunching(notification)
    Kernel.puts "\nApplication finished launching."
  end
  
  def applicationWillTerminate(notification)
    Kernel.puts "\nApplication will terminate."
  end
  
  def browser_willDisplayCell_atRow_column(sender, cell, row, column)
    cell.setStringValue @methods[row]
  end
  
  def browser_numberOfRowsInColumn(sender, column)
    @methods.length
  end
  
  def onInspect
    @methods = eval(@code_field.stringValue).methods.sort
    @browser.loadColumnZero
  end
  ib_action :onInspect
end
