package com.vonage.core.workflows.process;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
// Sling APIs
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

// OSGi APIs
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
// Logger APIs
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

// Workflow APIs
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import com.day.cq.commons.LanguageUtil;
// import com.vonage.core.constants.VonageConstants;

/**
 * Vonage Update links in XF when creating/updating Language Copies
 *
 */
@Component(service = WorkflowProcess.class, property = {
    "process.label=Vonage - Update URLs in XF for create Language Copy" })
public class UpdateURLsInXF implements WorkflowProcess {

  /** default log. */
  protected static final Logger LOGGER = LoggerFactory.getLogger(UpdateURLsInXF.class);

  /**
   * resourceResolverFactory
   */
  @Reference
  private ResourceResolverFactory resourceResolverFactory;

  /**
   * resourceResolver
   */
  private static ResourceResolver resourceResolver;

  @Override
  public final void execute(final WorkItem item, final WorkflowSession wfsession, final MetaDataMap args)
      throws WorkflowException {

    LOGGER.info("Starting UpdateURLsInXF workflow");
    String wfModelId = item.getWorkflow().getWorkflowModel().getId();
    final WorkflowData wfData = item.getWorkflowData();
    final String payLoadType = wfData.getPayloadType();
    final String payload = wfData.getPayload().toString();
    HashMap<String, String> destinationList = new HashMap<String, String>();
    resourceResolver = wfsession.adaptTo(ResourceResolver.class);

    LOGGER.info(" Workflow Model " + item.getWorkflow().getWorkflowModel().getId());
    LOGGER.info("payloadType " + payLoadType);
    LOGGER.info("payload " + wfData.getPayload().toString());
    LOGGER.info(" Project Folder Path " + wfData.getMetaDataMap().get("projectFolderPath"));

    if (StringUtils.contains(payload, "/content/experience-fragments/vonage/")
        || StringUtils.contains(payload, ".")) {
        try {

          destinationList = this.calculateDetails(item, wfsession, args, wfModelId);
          if (destinationList.size() > 0) {
            for (Entry<String, String> entry : destinationList.entrySet()) {
              String key = entry.getKey();
              String value = entry.getValue();

              if (resourceResolver.getResource(key) != null
              && StringUtils.contains(key, "/content/experience-fragments/vonage/")) {
                final Node pageNode = resourceResolver.getResource(key).adaptTo(Node.class);
                try {
                  this.getChildNodes(pageNode, value);
                } catch (final Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              } else {
                LOGGER.error(" resource does not exist: " + key);
              }
            }
          }

        } catch (PersistenceException | RepositoryException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }


    LOGGER.info("UpdateURLsInXF Completed");
  }

   /**
   * Calculate targetLanguages and SourcePaths
   *
   * @param item workfow item
   * @param wfsession workflow session
   * @param args workflow args
   * @param wfModelId workflow model id
   * @throws RepositoryException  - Repository exception
   * @throws PersistenceException - Persistence Exception
   * @return destinationLanguages - list of launcher pages and languages to be updated
   */
  public static HashMap<String, String> calculateDetails(final WorkItem item, final WorkflowSession wfsession,
    final MetaDataMap args, final String wfModelId) throws RepositoryException, PersistenceException {

      LOGGER.info("CALCULATE DESTINATION DETAILS ");

      String[] targetLangList;
      String[] targetPagePath;
      HashMap<String, String> destinationList = new HashMap<String, String>();
      MetaDataMap wfMetadataMap = item.getWorkflowData().getMetaDataMap();

      switch (wfModelId) {
          case "/var/workflow/models/wcm-translation/create_language_copy":

            LOGGER.info("Switch to Create Language Copy Workflow");

            if (StringUtils.contains(wfMetadataMap.get("srcPathList").toString(), ",")) {

              targetLangList = wfMetadataMap.get("languageList").toString().split(",");
              targetPagePath = wfMetadataMap.get("srcPathList").toString().split(",");

              for (int i = 0; i < targetPagePath.length; i++) {
                for (int j = 0; j < targetLangList.length; j++) {
                  if (StringUtils.contains(targetPagePath[i], "/en-gb/")
                  || StringUtils.contains(targetPagePath[i], "/fr-fr/")
                  ||  StringUtils.contains(targetPagePath[i], "/es-es/")) {
                      String targetPath = targetPagePath[i].replace("en-gb", targetLangList[j])
                      .replace("es-es", targetLangList[j])
                      .replace("fr-fr", targetLangList[j]);
                      destinationList.put(targetPath, targetLangList[j]);
                  }
                }
              }

            } else {

              targetLangList = wfMetadataMap.get("languageList").toString().split(",");
              targetPagePath = new String[targetLangList.length];
              String srcPath = wfMetadataMap.get("srcPathList").toString();

              if (StringUtils.contains(srcPath, "/en-gb/")
                  ||  StringUtils.contains(srcPath, "/fr-fr/")
                  ||  StringUtils.contains(srcPath, "/es-es/")) {

                      for (int cnt = 0; cnt < targetLangList.length; cnt++) {

                        // Replace only en-gb, es-es, fr-fr as we will only need to do
                        // language copies from those dialects in XF
                        String pagePath = srcPath.replace("en-gb", targetLangList[cnt])
                        .replace("es-es", targetLangList[cnt])
                        .replace("fr-fr", targetLangList[cnt]);
                        targetPagePath[cnt] = pagePath;
                      }

                      for (int i = 0; i < targetPagePath.length; i++) {
                        destinationList.put(targetPagePath[i], targetLangList[i]);
                      }
              }
            }

            break;

          case "/var/workflow/models/wcm-translation/prepare_translation_project":

              LOGGER.info("Switch to Prepare Translation Project Workflow");
              if (wfMetadataMap.containsKey("projectFolderLanguageRefCount")
                && wfMetadataMap.get("projectFolderLanguageRefCount").toString() == "1") {
                targetLangList = wfMetadataMap.get("destinationLanguage").toString().split(",");
                targetPagePath = item.getWorkflowData().getPayload().toString().split(",");
              } else {
                targetLangList = wfMetadataMap.get("languageList").toString().split(";");
                targetPagePath = wfMetadataMap.get("sourcePathList").toString().split(";");
              }

              for (int i = 0; i < targetPagePath.length; i++) {
                destinationList.put(targetPagePath[i], targetLangList[i]);
              }

              break;

          case "/var/workflow/models/wcm-translation/update_language_copy":

              LOGGER.info("Switch to Update Language Copy Workflow");
              if (wfMetadataMap.containsKey("projectFolderLanguageRefCount")
                && wfMetadataMap.get("projectFolderLanguageRefCount").toString() == "1") {
                targetLangList = wfMetadataMap.get("destinationLanguage").toString().split(",");
                targetPagePath = item.getWorkflowData().getPayload().toString().split(",");
              } else {
                targetLangList = wfMetadataMap.get("languageList").toString().split(",");
                targetPagePath = wfMetadataMap.get("sourcePathList").toString().split(";");
              }

              for (int i = 0; i < targetPagePath.length; i++) {
                destinationList.put(targetPagePath[i], targetLangList[i]);
              }

              break;

          default:
              LOGGER.info("$$$$$$$$ New Workflow Model id " + wfModelId);
              return null;
      }

      // for (int i = 0; i < targetPagePath.length; i++) {
      //   for (int j = 0; j < targetLangList.length; j++) {
      //     destinationList.put(targetPagePath[i], targetLangList[j]);
      //   }
      // }

      LOGGER.info("######################## Update Hrefs in XF #################################");

      for (Entry<String, String> entry : destinationList.entrySet()) {
        LOGGER.info(entry.getKey() + " , " + entry.getValue());
      }

      LOGGER.info("######################## Update Hrefs in XF #################################");

      LOGGER.info("CALCULATE DESTINATION DETAILS DONE");

      return destinationList;
  }

  /**
   * getChildNodes
   *
   * @param nd Node
   * @param targetDialect Target Dialect
   * @throws RepositoryException  - Repository exception
   * @throws PersistenceException - Persistence Exception
   */
  public static void getChildNodes(final Node nd, final String targetDialect)
  throws RepositoryException, PersistenceException {

    LOGGER.debug("getChildNodes nd path is " + nd.getPath());
    final NodeIterator ndIt = nd.getNodes();
    while (ndIt.hasNext()) {
      final Node node = ndIt.nextNode();
      LOGGER.debug("getChildNodes: updated links at " + node.getPath() + " with target dialect " + targetDialect);
      updateLinks(node, targetDialect);

      if (node.hasNodes()) {
        getChildNodes(node, targetDialect);
      }
    }
  }

  /**
   * UpdateLinks
   *
   * @throws IllegalStateException - Illegal state exception
   * @throws RepositoryException   - Repository Exception
   * @param nd Node
   * @param targetDialect Target Dialect
   * @throws PersistenceException - persistence exception
   */
  public static void updateLinks(final Node nd, final String targetDialect)
  throws PersistenceException, RepositoryException {

    if (nd.hasProperty("href")) {
      LOGGER.debug("updateLinks for node has property href ");
    }


    if (nd.hasProperty("linkURL")) {
      LOGGER.debug("updateLinks for node has property linkURL ");
    }

    String oldLinkUrl = "";
    String targetLanguage = "";
    String targetCountry = "";
    if (org.apache.commons.lang3.StringUtils.contains(targetDialect, "-")) {
      targetCountry = targetDialect.split("-")[1];
      targetLanguage = targetDialect.split("-")[0];
    } else {
      targetLanguage = targetDialect;
    }

    try {

      if (nd.hasProperty("href") || nd.hasProperty("linkURL")) {

        if (nd.hasProperty("href")) {
          oldLinkUrl = nd.getProperty("href").getString();
        } else {
          oldLinkUrl = nd.getProperty("linkURL").getString();
        }

        LOGGER.debug("updateLinks: oldLinkUrl " + oldLinkUrl);

        final Resource oldRes = resourceResolver.getResource(oldLinkUrl.replace(".html", ""));

        if (oldRes != null) {
          // String oldLangRootPath = languageManager.getLanguageRoot(oldRes).getPath();
          final String oldLangRootPath = LanguageUtil.getLanguageRoot(oldLinkUrl);
          LOGGER.debug("updateLinks: node path is " + nd.getPath());
          LOGGER.debug("updateLinks: old page exists " + oldRes.getPath());
          LOGGER.debug("updateLinks: old language root exists " + oldLangRootPath);

          final String newUrlPath = StringUtils.replace(oldLinkUrl, oldLangRootPath,
                    "/content/vonage/live-copies/" + targetCountry + "/" + targetLanguage);
                LOGGER.debug("updateLinks: new url path is  " + newUrlPath);

                if (nd.hasProperty("href")) {
                    nd.setProperty("href", newUrlPath);
                } else {
                    nd.setProperty("linkURL", newUrlPath);
                }
                resourceResolver.commit();
        } else {
          final String newUrlPath = StringUtils.replace(oldLinkUrl, "in/en", targetCountry + "/" + targetLanguage);
          LOGGER.debug("new url path is  " + newUrlPath);
          if (nd.hasProperty("href")) {
              nd.setProperty("href", newUrlPath);
          } else {
              nd.setProperty("linkURL", newUrlPath);
          }
          resourceResolver.commit();
        }
      }

        } catch (IllegalStateException | RepositoryException  | PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
