package com.vonage.core.models.structure.header;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.vonage.core.models.CTAModel;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Sling model for L3 Nav
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class L3NavModel {

    /**
     * currentPage variable
     */
    @Inject
    private Page currentPage;

    /**
     * resourcePage l3NavResource
     */
    private Resource l3NavResource;

    /**
     * List<CTAModel> linksList
     */
    private List<CTAModel> linksList = new ArrayList<>();

    /**
     * CTAModel activeCTANavLink
     */
    private CTAModel activeCategoryCTA = null;

    /**
     * boolean is l3 nav active
     */
    private boolean removeL3Nav;

    /**
     * init method
     */
    @PostConstruct
    protected final void init() {
        this.setNavLinksResource(currentPage);
        if (this.l3NavResource != null) {
          this.setLinksList();
          for (CTAModel link : this.linksList) {
            if (StringUtils.contains(currentPage.getPath(), link.getLink().getPath())
              || StringUtils.equals(currentPage.getPath(), link.getLink().getPath())) {
              this.activeCategoryCTA = link;
            }
          }
        }
        InheritanceValueMap inheritedProp = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
        this.removeL3Nav = Boolean.parseBoolean(inheritedProp.getInherited("removeL3Nav", ""));
    }

    /**
     * @param page where we check if l3NavLinks exist
     */
    public final void setNavLinksResource(final Page page) {
        if (page != null) {
            if (null != page.getContentResource("l3NavLinks")) {
                this.l3NavResource = page.getContentResource("l3NavLinks").getChild("links");
            } else if (page.getDepth() > 1) {
                this.setNavLinksResource(page.getParent());
            }
        }
    }

    /**
     * List<CTAModel> which holds the L3 Navs
     */
    public final void setLinksList() {
        Iterator<Resource> linksRes = this.l3NavResource.listChildren();
        while (linksRes.hasNext()) {
            CTAModel link = linksRes.next().adaptTo(CTAModel.class);
            this.linksList.add(link);
        }
    }

    /**
     * @return List<CTAModel> which holds the L3 Navs
     */
    public final List<CTAModel> getNavLinkModel() {
        return new ArrayList<>(this.linksList);
    }

    /**
     * @return String which holds the active L3 Nav
     */
    public final String getActiveNavLinkLabel() {
        if (null != this.activeCategoryCTA) {
            return this.activeCategoryCTA.getLink().getLabel();
        } else {
            return StringUtils.EMPTY;
        }

    }

    /**
     * @return boolean to say if L3 nav is active or not
     */
    public final boolean isL3NavActive() {
        return (this.linksList.size() > 0 && !this.removeL3Nav);
      }
}
