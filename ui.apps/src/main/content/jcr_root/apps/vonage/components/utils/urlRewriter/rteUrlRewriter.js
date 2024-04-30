use(function(){
    var externalizedHtml = "";
    if(this.html){
        var externalizer = sling.getService(Packages.com.day.cq.commons.Externalizer);
        var runModes = sling.getService(Packages.org.apache.sling.settings.SlingSettingsService).getRunModes().toString();
        var domain = runModes.includes("author")? "author" : "publish"
        externalizedHtml  = this.html.toString().replace(/href="(.*?)"/gi, function(m, url){

            var externalUrl = externalizer.externalLink(resource.getResourceResolver(), domain, url); 
            return "href='"+externalUrl+"'"
          });
    }
    
    return {
        externalizedHtml : externalizedHtml
    }
});