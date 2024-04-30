package com.vonage.core.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.PricingConstants;
import com.vonage.core.constants.VonageConstants;

/**
 * ServiceUtils class
 *
 * @author Vonage
 *
 */
public  final class ServiceUtils {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtils.class);

    /**
     * Static class should not be initialized.
     */
    private ServiceUtils() {
    }

    /**
     * Get Resolver
     *
     * @param resolverFactory - resolver factory
     * @param subservice      - sub-service
     * @return ResourceResolver resolver
     */
    private static ResourceResolver getServiceResourceResolver(final ResourceResolverFactory resolverFactory,
            final String subservice) {
        HashMap<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, subservice);
        try {
            return resolverFactory.getServiceResourceResolver(param);
        } catch (LoginException e) {
            LOGGER.error("Login Excpetion in getting service resource resolver. Error: {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * getWriteResourceResolver method
     *
     * @param resolverFactory - resolver factory
     * @return ResourceResolver
     */
    public static ResourceResolver getWriteResourceResolver(final ResourceResolverFactory resolverFactory) {
        return getServiceResourceResolver(resolverFactory, VonageConstants.WRITE_SUBSERVICE);
    }

    /**
     * getReadResourceResolver method
     *
     * @param resolverFactory - resolver factory
     * @return ResourceResolver
     */
    public static ResourceResolver getReadResourceResolver(final ResourceResolverFactory resolverFactory) {
        return getServiceResourceResolver(resolverFactory, VonageConstants.READ_SUBSERVICE);
    }

    /**
     * This method commit resourceResolver object after session refresh
     *
     * @param resourceResolver resolver
     * @throws PersistenceException - Exception
     * @throws RepositoryException  - Exception
     */
    public static void commit(final ResourceResolver resourceResolver)
            throws PersistenceException, RepositoryException {
        if (!resourceResolver.hasChanges()) {
            return;
        }
        final Session session = resourceResolver.adaptTo(Session.class);
        resourceResolver.refresh();
        session.refresh(true);
        resourceResolver.commit();
    }

    /**
     * Replicate Paths
     *
     * @param resolver          - resolver object
     * @param replicate         - replicate service
     * @param type              - Activate/ Deactivate
     * @param replicationAgents - Agent IDs
     * @param targetPaths       - List of paths to replicate
     * @throws ReplicationException - Exception
     */
    public static void replicatePaths(final ResourceResolver resolver, final Replicator replicate,
            final ReplicationActionType type, final String[] replicationAgents, final List<String> targetPaths)
            throws ReplicationException {
        if (null == replicationAgents) {
            throw new ReplicationException("replicationAgents is null. Please check Config service.");
        }
        if (!targetPaths.isEmpty() && replicationAgents.length > 0) {
            LOGGER.debug("Replicating content with number of agents: {}", replicationAgents.length);
            ReplicationOptions options = new ReplicationOptions();
            options.setFilter(agent -> Arrays.asList(replicationAgents).contains(agent.getId()));
            replicate.replicate(resolver.adaptTo(Session.class), type,
                    targetPaths.toArray(new String[targetPaths.size()]), options);
            LOGGER.info("Replication complete for paths: [{}]", targetPaths);
        } else {
            throw new ReplicationException("Target path- [" + targetPaths.size()
                    + "] is empty or no replication agents [" + replicationAgents.length + "] found!");
        }
    }

    /**
     * Generate node with properties
     *
     * @param resolver   - resolver
     * @param parentPath - Parent path
     * @param properties - node properties
     * @return true if successful
     */
    public static boolean createProductNode(final ResourceResolver resolver, final String parentPath,
            final Map<String, String> properties) {
        Session session = resolver.adaptTo(Session.class);
        try {
            if (session.nodeExists(parentPath)
                    && session.getNode(parentPath).hasProperty(PricingConstants.PN_DISABLE_SYNC) && "true".equals(
                            session.getNode(parentPath).getProperty(PricingConstants.PN_DISABLE_SYNC).getString())) {
                return false;
            }
            ResourceUtil.getOrCreateResource(resolver, parentPath, JcrConstants.NT_UNSTRUCTURED, (String) null, false);
            Node contentNode = session.getNode(parentPath);
            properties.forEach((key, value) -> {
                try {
                    String[] parts = key.split(AppConstants.SLASH);
                    if (parts.length == 2) {
                        String parent = parts[0];
                        String priceProp = parts[1];
                        ResourceUtil.getOrCreateResource(resolver, parentPath + "/" + parent, (String) null,
                                JcrConstants.NT_UNSTRUCTURED, false);
                        Node productNode = contentNode.getNode(parent);
                        productNode.setProperty(JcrConstants.JCR_TITLE, parent.replace("-", " "));
                        productNode.setProperty(priceProp, value);
                    } else {
                        contentNode.setProperty(key, value);
                    }
                } catch (RepositoryException | PersistenceException e) {
                    LOGGER.error("Unable to create product node. Error : {}", e.getMessage(), e);
                }
            });
            if (!contentNode.getPath().equals(PricingConstants.PRODUCTS_BASE_PATH)) {
                contentNode.setProperty(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY,
                        "commerce/components/product");
                contentNode.setProperty("cq:commerceType", "product");
            }
            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(lastModified.getTimeInMillis());
            contentNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
            ServiceUtils.commit(resolver);
            LOGGER.info("Node successfully created at {}", parentPath);
            return true;
        } catch (RepositoryException | PersistenceException e) {
            LOGGER.error("Error : {}", e.getMessage(), e);
        }
        return false;
    }
}
