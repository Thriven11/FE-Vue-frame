"use strict";
use(function () {
    let a = [] , count = parseInt(properties["numberOfLogos"])
    for (let i = 0; i < count; i++) {
        a.push(i)
    }
    return {
        array: a,
        length: a.length,
        columns: 12/a.length
    }
});