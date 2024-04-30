import javax.jcr.Node

path='/content/vonage/language-masters/en/events'
 def pathsToSearch = ["/content/vonage/language-masters/en/events",
					 "/content/vonage/language-masters/en/unified-communications/events",
					 "/content/vonage/language-masters/en/communications-apis/partners" ,
					 "/content/vonage/language-masters/en/communications-apis/events",
					  "/content/vonage/language-masters/en/contact-centers/events",

					 ] as String[]

Set stringSetCQTags = []
Set stringSetContentCategory = []

for(int j=0;j<pathsToSearch.length;j++)
	{

		  getPage(pathsToSearch[j]).recurse
			  { page ->

			  def jcrContent = page.node

			  // ##### cq:tags #####

				def cqTags=jcrContent.get("cq:tags")
				def contentCategory = jcrContent.get("contentCategory")


					// ##### cq tags #####
				   if(cqTags){
						def data = cqTags as String[]
							for(int i=0;i<data.length;i++)
							{
							 stringSetCQTags.add(data[i])

							}
					}

					  // #####  contentCategory #####
					if(contentCategory){
						def data = contentCategory
						stringSetContentCategory.add(data)
					  }
			  }


	}
	 println "Unique Tags used at cq:tags property "+stringSetCQTags
	println "Unique Tags used at contentCategory property  "+stringSetContentCategory