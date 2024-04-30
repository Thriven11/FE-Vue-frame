package com.vonage.core.workflows.process;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
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
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

// Workflow APIs
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
// import com.day.cq.wcm.api.Page;
// import com.day.cq.wcm.api.PageManager;
// import com.day.cq.wcm.api.WCMException;
import com.day.cq.commons.LanguageUtil;
import com.vonage.core.constants.VonageConstants;

/**
 * Vonage Unlock Page Process
 *
 */
@Component(service = WorkflowProcess.class, property = { "process.label=Vonage - Update XF Links in Sites" })
public class UpdateXfLinksInSites implements WorkflowProcess {

    /** default log. */
    protected static final Logger LOGGER = LoggerFactory.getLogger(UpdateXfLinksInSites.class);

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


        LOGGER.info("Starting UpdateXfLinksInSites workflow");

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

        if (StringUtils.contains(payload, "/content/vonage/")
            || StringUtils.contains(payload, ".")) {

          try {

            destinationList = this.calculateDetails(item, wfsession, args, wfModelId);
            if (destinationList.size() > 0) {
              for (Entry<String, String> entry : destinationList.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (resourceResolver.getResource(key) != null) {

                      if (resourceResolver != null) {
                        try {
                            final Node pageNode = resourceResolver.getResource(key).adaptTo(Node.class);
                              getChildNodes(pageNode, value);
                          } catch (final Exception e) {
                              // TODO Auto-generated catch block
                              LOGGER.error(e.getStackTrace().toString());
                          }
                      }
                  }
              }
            }
          } catch (PersistenceException | RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

        LOGGER.info("updateXFLinksInSites Completed");
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

      // LOGGER.info("targetLangList " + targetLangList.toString());
      // LOGGER.info("targetPagePath " + targetPagePath.toString());

      // for (int i = 0; i < targetPagePath.length; i++) {
      //   for (int j = 0; j < targetLangList.length; j++) {
      //     destinationList.put(targetPagePath[i], targetLangList[j]);
      //   }
      // }

      LOGGER.info("@@@@@@@@@@@@@@@@@@@ Update XF Links in Sites @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

      for (Entry<String, String> entry : destinationList.entrySet()) {
        LOGGER.info(entry.getKey() + " , " + entry.getValue());
      }

      LOGGER.info("@@@@@@@@@@@@@@@@@@@ Update XF Links in Sites @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

      LOGGER.info("CALCULATE DESTINATION DETAILS DONE ");

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

        final NodeIterator ndIt = nd.getNodes();
        while (ndIt.hasNext()) {
            final Node node = ndIt.nextNode();

            updateXfLinks(node, targetDialect);

            if (node.hasNodes()) {
                getChildNodes(node, targetDialect);
            }
        }
    }

    /**
     * UpdateXfLinks in Sites specifically in XF component and lightboxCTA component
     *
     * @throws IllegalStateException - Illegal state exception
     * @throws RepositoryException   - Repository Exception
     * @param nd Node
     * @param targetDialect Target Dialect
     * @throws PersistenceException - persistence exception
     */
    public static void updateXfLinks(final Node nd, final String targetDialect) throws PersistenceException {

        try {

          updateXfLinksinLightboxCta(nd, targetDialect);

        } catch (IllegalStateException | RepositoryException | PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * UpdateXfLinks in lightboxCTA component
     *
     * @throws IllegalStateException - Illegal state exception
     * @throws RepositoryException   - Repository Exception
     * @param nd Node
     * @param targetDialectParam Target Dialect
     * @throws PersistenceException         - persistence exception
     * @throws NoSuchNodeTypeException      - no such node
     * @throws ConstraintViolationException - constraint validation
     * @throws LockException                - lock exception
     * @throws VersionException             - version exception
     * @throws PathNotFoundException        - path not found
     * @throws ValueFormatException         - value format exception
     */
    public static void updateXfLinksinLightboxCta(final Node nd, final String targetDialectParam)
            throws PersistenceException, ValueFormatException, PathNotFoundException, VersionException, LockException,
            ConstraintViolationException, NoSuchNodeTypeException, RepositoryException {

                String targetDialect = targetDialectParam;
                String targetLanguage = "";
                String targetCountry = "";
                if (org.apache.commons.lang3.StringUtils.contains(targetDialect, "-")) {
                  targetCountry = targetDialect.split("-")[1];
                  targetLanguage = targetDialect.split("-")[0];
                } else {
                  targetLanguage = targetDialect;
                }

        if (nd.hasProperty("sling:resourceType") && nd.getProperty("sling:resourceType").getString()
                .equalsIgnoreCase("vonage/components/utils/lightboxCta")) {

            if (nd.hasProperty("confMessagePath")) {

                LOGGER.debug("update lightbox path " + nd.getPath());
                final String confPath = nd.getProperty("confMessagePath").getString();
                final String newUrlPath = StringUtils.replace(confPath,  LanguageUtil.getLanguageRoot(confPath),
                  VonageConstants.XF_ROOT_PATH + "/" + targetLanguage + "-" + targetCountry);

                nd.setProperty("confMessagePath", newUrlPath);
                // nd.addMixin("cq:LiveSyncCancelled");
                resourceResolver.commit();
            }
            if (nd.hasProperty("formPath")) {
                final String formPath = nd.getProperty("formPath").getString();
                final String newUrlPath = StringUtils.replace(formPath,  LanguageUtil.getLanguageRoot(formPath),
                VonageConstants.XF_ROOT_PATH + "/" + targetLanguage + "-" + targetCountry);

                LOGGER.debug("update lightbox path " + nd.getPath());
                nd.setProperty("formPath", newUrlPath);
                resourceResolver.commit();
            }
        }
    }
}
