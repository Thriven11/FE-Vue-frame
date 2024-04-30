"use strict";
use(function () {
    var parent = resource.getParent();
    var props = parent.adaptTo(Packages.org.apache.sling.api.resource.ValueMap);
    var info = {};
    info.fourthLobSection = props.get("fourthLobSection","");

    return info;
})