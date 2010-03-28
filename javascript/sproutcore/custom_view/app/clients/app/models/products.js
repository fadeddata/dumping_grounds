// ==========================================================================
// App.Products
// ==========================================================================

require('core');

/** @class

  (Document your class here)

  @extends SC.Record
  @author AuthorName
  @version 0.1
*/
App.Products = SC.Record.extend(
/** @scope App.Products.prototype */ {

  dataSource: App.server,
  properties: ['name', 'manufacturer', 'price']

}) ;
