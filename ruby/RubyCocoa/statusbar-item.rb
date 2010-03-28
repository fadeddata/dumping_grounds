#!/usr/bin/env ruby

require 'osx/cocoa'
include OSX

class App < NSObject
  def applicationDidFinishLaunching(aNotification)
    statusbar = NSStatusBar.systemStatusBar
    item = statusbar.statusItemWithLength(NSVariableStatusItemLength)
    image = NSImage.alloc.initWithContentsOfFile("stretch.tiff")
#     item.setTitle "Dustin"
    item.setImage(image)
    SpeechController.alloc.init.add_menu_to(item)
  end
end

class SpeechController < NSObject
  def init
    super_init
    @synthesizer = NSSpeechSynthesizer.alloc.init
    self
  end
  def add_menu_to(container)
    menu = NSMenu.alloc.init
    container.setMenu(menu)

    item = menu.addItemWithTitle_action_keyEquivalent("Speak", "speak", '')
    item.setTarget(self)

    item = menu.addItemWithTitle_action_keyEquivalent("Quit", "terminate:", 'q')
    item.setKeyEquivalentModifierMask(NSCommandKeyMask)
    item.setTarget(NSApp)
  end

  def speak(sender)
    @synthesizer.startSpeakingString("I have nothing to say.")
  end
end

NSApplication.sharedApplication
NSApp.setDelegate(App.alloc.init)
NSApp.run
