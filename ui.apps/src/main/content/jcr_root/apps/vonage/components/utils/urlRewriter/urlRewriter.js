use(function(){
    var externalUrl = "";
    if(this.url){
        var externalizer = sling.getService(Packages.com.day.cq.commons.Externalizer);
        var runModes = sling.getService(Packages.org.apache.sling.settings.SlingSettingsService).getRunModes().toString();
        var domain = runModes.includes("author")? "author" : "publish"
        externalUrl = externalizer.externalLink(resource.getResourceResolver(), domain, this.url); 
    }
    
    return {
        externalUrl : externalUrl
    }
});