import javax.jcr.Node

rootFolders = ["/content/vonage/language-masters/en/",
              //  "/content/vonage/language-masters/en-gb/",
              //  "/content/vonage/en-gb-masters/en-gb/",
              //  "/content/vonage/en-gb-masters/ja/",
              //  "/content/vonage/en-gb-masters/ko-kr/",
              //  "/content/vonage/en-gb-masters/zh-cn/",
              //  "/content/vonage/en-gb-masters/ru-ru/",
              //  "/content/vonage/en-gb-masters/de-de/",
              //  "/content/vonage/en-gb-masters/fr-fr/",
              //  "/content/vonage/en-gb-masters/es-es/",
              //  "/content/vonage/es-es-masters/es/",
              "/content/vonage/live-copies/us/en/"
              // "/content/vonage/live-copies/ph/en/",
              // "/content/vonage/live-copies/au/en/",
              // "/content/vonage/live-copies/gb/en/",
              // "/content/vonage/live-copies/tw/en/",
              // "/content/vonage/live-copies/sg/en/",
              // "/content/vonage/live-copies/my/en/",
              // "/content/vonage/live-copies/id/en/",
              // "/content/vonage/live-copies/hk/en/",
              // "/content/vonage/live-copies/ca/en/",
              // "/content/vonage/live-copies/ca/fr/",
              // "/content/vonage/live-copies/jp/ja/",
              // "/content/vonage/live-copies/kr/ko/",
              // "/content/vonage/live-copies/cn/zh/",
              // "/content/vonage/live-copies/nz/en/",
              // "/content/vonage/live-copies/ru/ru/",
              // "/content/vonage/live-copies/de/de/",
              // "/content/vonage/live-copies/fr/fr/",
              // "/content/vonage/live-copies/cl/es/",
              // "/content/vonage/live-copies/co/es/",
              // "/content/vonage/live-copies/mx/es/",
              // "/content/vonage/live-copies/es/es/",
              //  "/content/vonage/live-copies/ar/es/"
               ]

pagePaths = ["resources","about-us/vonage-stories","events"]
 
// path='/content/vonage/live-copies/us/en/about-us/vonage-stories'


findAllPages()
 
    def findAllPages(){
    def  mp  = ["vonage:topic/infrastructure" : "vonage:topic/business-continuity",
                "vonage:topic/call-center" : "vonage:topic/customer-engagement",
                "vonage:topic/business-productivity" : "vonage:topic/productivity",
                "vonage:topic/customer-experience" : "vonage:topic/customer-engagement",
                "vonage:topic/customer-360" : "vonage:topic/customer-experience",
                "vonage:topic/industry-trends" : "",
                "vonage:product-subcategory/messaging-api":"vonage:product/communications-apis/sms-mms",
                "vonage:product-subcategory/sms-api":"vonage:product/communications-apis/sms-mms",
                "vonage:product-subcategory/voice-api":"vonage:product/communications-apis/voice",
                "vonage:product-subcategory/verify-api":"vonage:product/communications-apis/two-factor-authentication",
                "vonage:product-subcategory/video-api":"vonage:product/communications-apis/video",
                "vonage:product-subcategory/vbc":"vonage:product/unified-communications",
                "vonage:product-subcategory/sd-wan":"vonage:product/unified-communications",
                "vonage:product-subcategory/nvm":"vonage:product/contact-centers",
                "vonage:product-subcategory/conversation-analyzer":"vonage:product/contact-centers",
                "vonage:product-subcategory/tts":"vonage:product/communications-apis/voice",
                "vonage:product-subcategory/2fa":"vonage:product/communications-apis/two-factor-authentication",
                "vonage:product-subcategory/ivr":"vonage:product/communications-apis/voice",
                "vonage:product-subcategory/virtual-numbers":"vonage:product/communications-apis/sms-mms",
                "vonage:product-subcategory/virtual-numbers":"vonage:product/communications-apis/voice",
                "vonage:product-subcategory/number-insight":"vonage:product/communications-apis/two-factor-authentication",
                "vonage:product-subcategory/sip-trunking":"vonage:product/communications-apis/sip-trunking",
                "vonage:product-subcategory/smart-numbers":"",
                "vonage:industry/accounting":"vonage:industry/finance-insurance",
                "vonage:industry/finance":"vonage:industry/finance-insurance",
                "vonage:industry/insurance":"vonage:industry/finance-insurance",
                "vonage:industry/logistics":"vonage:industry/transportation-logistics",
                "vonage:industry/marketing":"",
                "vonage:industry/retail":"vonage:industry/retail-ecommerce",
                "vonage:industry/transportation":"vonage:industry/transportation-logistics",
                "vonage:resources/article" : "",
                "vonage:resources/publications" : "",
                "vonage:resources/publications/whitepaper" : "",
                "vonage:resources/publications/ebook" : "",
                "vonage:resources/publications/guide" : "",
                "vonage:resources/interactive-media" : "",
                "vonage:resources/interactive-media/webinar" : "",
                "vonage:resources/interactive-media/video" : "",
                "vonage:resources/interactive-media/infographic":"",
                "vonage:resources/company-news" : "",
                "vonage:resources/company-news/from-vonage" : "",
                "vonage:resources/company-news/from-analysts" : "",
                "vonage:resources/company-news/from-the-media" : "",
                "vonage:resources/company-news/press-releases" : "",
                "vonage:resources/customer-stories":"",
                "vonage:resources/customer-stories/case-studies":"",
                "vonage:resources/customer-stories/testimonials":"",
                "vonage:business-size":"",
                "vonage:business-size/small-micro":"",
                "vonage:business-size/mid-size":"",
                "vonage:business-size/enterprise":"",
                "vonage:professional-role/c-suite":"",
                "vonage:professional-role/contact-center-leads":"",
                "vonage:professional-role/customer-experience-leads":"",
                "vonage:professional-role/developers":"",
                "vonage:professional-role/hr-leads":"",
                "vonage:professional-role/it-leads":"",
                "vonage:professional-role/operations-leads":"",
                "vonage:professional-role/products-leads":"",
                "vonage:professional-role/engineering-leads":"",
                "vonage:professional-role/sales-leads":"",
                "vonage:professional-role/small-business-owners":""]
                
     def  contentCategoryMap  = ["vonage:resources/article" : "vonage:content-type/article",
                "vonage:resources/publications" : "vonage:content-type/whitepapers-and-guides",
                "vonage:resources/publications/whitepaper" : "vonage:content-type/whitepapers-and-guides",
                "vonage:resources/publications/ebook" : "vonage:content-type/whitepapers-and-guides",
                "vonage:resources/publications/guide" : "vonage:content-type/whitepapers-and-guides",
                "vonage:resources/interactive-media" : "vonage:content-type/webinars",
                "vonage:resources/interactive-media/webinar" : "vonage:content-type/webinars",
                "vonage:resources/interactive-media/video" : "vonage:content-type/webinars",
                "vonage:resources/interactive-media/infographic":"",
                "vonage:resources/company-news" : "vonage:content-type/vonage-stories",
                "vonage:resources/company-news/from-vonage" : "vonage:content-type/vonage-stories",
                "vonage:resources/company-news/from-analysts" : "vonage:content-type/vonage-stories",
                "vonage:resources/company-news/from-the-media" : "vonage:content-type/vonage-stories",
                "vonage:resources/company-news/press-releases" : "vonage:content-type/vonage-stories",
                "vonage:resources/customer-stories":"vonage:content-type/customer-stories",
                "vonage:resources/customer-stories/case-studies":"vonage:content-type/customer-stories",
                "vonage:resources/customer-stories/testimonials":"vonage:content-type/customer-stories"]
                
               
        
         my_keys = mp.keySet()  as ArrayList
         cc_keys = contentCategoryMap.keySet()  as ArrayList
         
rootFolders.each {
    def rootPath = it
    pagePaths.each {
        def pagePath = rootPath + it         
       if(getPage(pagePath) != null) {
          getPage(pagePath).recurse
              { page ->
               println page.node.path
              def jcrContent = page.node 
              
              // ##### cq:tags ##### 
              
                def cqTags=jcrContent.get("cq:tags")
                def contentCategory = jcrContent.get("contentCategory")
               
                     for(int i=0;i<my_keys.size;i++)
                    {
                    
                    if(cqTags&&cqTags.contains(my_keys[i])){
                      def mavValue = mp.get(my_keys[i])
                      cqTags.remove(my_keys[i]);
                      if(!cqTags.contains(mavValue) && mavValue != ""){
                        //   println page.path
                          cqTags.add(mavValue);
                        }
                      }
                    }
                 
                if(cqTags){
                      def data = cqTags as String[]
                        
                          
                    jcrContent.setProperty("cq:tags",data);
                     session.save()
                }
                
                //   println "cqTags after Update"+cqTags
                  
                // ##### cq:tags end ##### 
                
                // ##### contentCategory ##### 
                
                def ccTag=jcrContent.get("contentCategory")
                
                if(cc_keys.contains(ccTag)) {
                    
                    def newTag = contentCategoryMap.get(cc_keys[cc_keys.indexOf(ccTag)])
                    jcrContent.setProperty("contentCategory",newTag);
                     session.save()
                    //  println "Content Category Updated"
                    // println jcrContent.path
                }
                
                // ##### contentCategory end ##### 
                  
              }
            }
        }
    }
}      