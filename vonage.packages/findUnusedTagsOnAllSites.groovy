/** @author Hashim Khan */
 
 import org.apache.sling.api.resource.Resource
 import com.day.cq.tagging.Tag
 import com.day.cq.tagging.TagManager
 import org.apache.sling.api.resource.ResourceResolver
 import java.lang.Thread.*;
 import javax.jcr.Node;
 
def tagpath = "/content/cq:tags/vonage/authors";
def delay = 10 ; //in Milliseconds.
 
def query = getAllTags(tagpath)
def result = query.execute()
 
def rows = result.rows
def unusedTags = 0
 
rows.each { row ->
 Resource res = resourceResolver.getResource(row.path)
 if(res!=null){
 Tag tag = res.adaptTo(com.day.cq.tagging.Tag)
 Node tempNode = res.adaptTo(javax.jcr.Node);
 
 if(tag.getCount()==0){
 if(!tempNode.hasNodes()){
 unusedTags++
 println "Deleted Tag : " + tag.getPath()
//  tempNode.remove()
 }
 }
 Thread.currentThread().sleep((long)(delay));
 }
 
}
println "Total Unused Tags :"+unusedTags
//session.save() //Uncomment this to make it working.
 
def getAllTags(tagpath) {
 def queryManager = session.workspace.queryManager
 def statement = "/jcr:root"+tagpath+"//element(*, cq:Tag)"
 def query = queryManager.createQuery(statement, "xpath")
}