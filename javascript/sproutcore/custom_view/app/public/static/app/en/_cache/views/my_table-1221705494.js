/* Start ----------------------------------------------------- views/my_table.js*/

// ==========================================================================
// App.MyTableView
// ==========================================================================

require('core');

/** @class

  (Document Your View Here)

  @extends SC.View
  @author AuthorName
  @version 0.1
*/
App.MyTableView = SC.View.extend(
/** @scope App.MyTableView.prototype */ {

    emptyElement: '<table><tr><td></td></tr></table>',

    // Properties
    content: [],
    contentBindingDefault: SC.Binding.MultipleNotEmpty,


}) ;


/* End ------------------------------------------------------- views/my_table.js*/

