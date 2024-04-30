/**@author Hashim Khan */
 
import javax.jcr.Node
 
/*Flag to count the number of used authors*/
noOfUsedAuthors = 0
noOfUnusedAuthors = 0
def usedAuthorsList = []
def unUsedAuthorsList = []
/*Pathfield which needs to be iterated for an operation*/
path='/content/vonage/live-copies/us/en/resources'

findAllUsedTags(usedAuthorsList)
findAllUnusedTags(usedAuthorsList, unUsedAuthorsList)

def findAllUsedTags(usedAuthorsList) {
    println "List of Used Authors in " + path
    getPage(path).recurse
     { page ->
          def content = page.node
          def property= content.get('author')
          if(property!=null) {
           
            
            if(!(property.toString() in usedAuthorsList)) {
                 noOfUsedAuthors++
                usedAuthorsList << property.toString()
                println property.toString()
            }
          }
      }
      println 'No Of Used Authors::'+usedAuthorsList.size()
}

def findAllUnusedTags(usedAuthorsList, unUsedAuthorsList) {
    println " "
    println "List of Unused Authors in " + path
    def queryManager = session.workspace.queryManager
    def statement = "/jcr:root/content/cq:tags/vonage/authors//element(*, cq:Tag)"
    def query = queryManager.createQuery(statement, "xpath")
    def result = query.execute()
    def rows = result.rows
    rows.each { row ->
        def tagName = row.path.replaceAll("/content/cq:tags/vonage/","vonage:")
        if(!(tagName in usedAuthorsList)) {
            unUsedAuthorsList << tagName
            noOfUnusedAuthors++
            println tagName
        }
    }
    println 'No Of Unused Authors::'+unUsedAuthorsList.size()
}



