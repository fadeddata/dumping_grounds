#!/usr/bin/env ruby

require 'osx/cocoa'
include OSX

class AppDelegate < NSObject
  def applicationWillFinishLaunching(aNotification)
    puts "#{aNotification.name} makes me say: Hello, world!"
    puts aNotification.object.to_s
  end
end

NSApplication.sharedApplication
NSApp.setDelegate(AppDelegate.alloc.init)
NSApp.run
