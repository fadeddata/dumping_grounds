/* Start ----------------------------------------------------- controllers/app.js*/

// ==========================================================================
// Test.AppController
// ==========================================================================

require('core');

/** @class

  (Document Your View Here)

  @extends SC.Object
  @author AuthorName
  @version 0.1
  @static
*/
Test.appController = SC.Object.create(
/** @scope Test.appController */ {

    greeting: "Hello World!",
    
    toggleGreeting: function () {
	var currentGreeting = this.get('greeting');
	var newGreeting = (currentGreeting === 'Hello World!') ? 'I am on Sproutcore!' : 'Hello World!';
	this.set('greeting', newGreeting);
    },

    isClockShowing: NO,

    isClockShowingObserver: function() {
	var isClockShowing = this.get('isClockShowing');
	var newGreeting = (isClockShowing) ? 'CLOCK!' : 'Hello World!';
	this.set('greeting', newGreeting);
    }.observes('isClockShowing')

}) ;


/* End ------------------------------------------------------- controllers/app.js*/

