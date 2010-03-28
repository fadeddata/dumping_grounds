#
#  rb_main.rb
#  RucolaTest
#
#  Created by Rucola Test on 2008-09-11.
#  Copyright (c) 2007 Rucola Test. All rights reserved.
#

# First check if we might be running a bundled release build.
# Otherwise load rubygems.

third_party_dir = File.expand_path('../vendor/third_party/', __FILE__)
if File.exist?(File.join(third_party_dir, 'rucola/dependencies/override_require_and_gem.rb'))
  $LOAD_PATH.unshift(third_party_dir)
  require 'rucola/dependencies/override_require_and_gem'
  Rucola::Dependencies.override_require_and_gem!
else
  require 'rubygems'
end

require 'rucola'

# Start the application
Rucola::Initializer.boot