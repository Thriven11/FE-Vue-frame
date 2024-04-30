"use strict";
use(function () {
    let a = [] , count = parseInt(properties["numberOfFeatures"])
    for (let i = 0; i < count; i++) {
        a.push(i)
    }

    const alignmentClassMap  =  {
        'zigzag-start-image-right':'',
        'zigzag-start-image-left':'details-grid--sort-reverse',
        'all-images-right':'details-grid--regular',
        'all-images-left':'details-grid--reverse'
    }

    return {
        array: a,
        length: a.length,
        columns: 12/a.length,
        themeClass:properties["theme"] === 'light' ? 'details-grid--light' : '',
        alignmentClass:alignmentClassMap[properties["alignment"]]
    }
});