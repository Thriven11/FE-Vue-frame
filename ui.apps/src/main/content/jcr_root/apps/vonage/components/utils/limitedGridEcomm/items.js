"use strict";
use(function() {
  let a = [],
      count = (this.numberOfItems != null) ? parseInt(this.numberOfItems) : parseInt(properties["numberOfItems"]);
  for (let i = 0; i < count; i++) {
    a.push(i);
  }
  return {
    items: a,
    colspan: 12 / a.length
  };
});
