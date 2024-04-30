package com.vonage.core.models.structure.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AnalyticsDataModelTest {

    private AnalyticsDataModel model;

    @Mock
    private Node currentNode;
    @Mock
    private Node usEnPageNode;
    @Mock
    private Resource currentResource;
    @Mock
    private Resource usEnResource;
    @Mock
    private Page currentPage;
    @Mock
    private Page resourcePage;
    @Mock
    private Page resourceParent1;
    @Mock
    private Page resourceParent2;
    @Mock
    private PageInfoBean pageInfoBean;
    @Mock
    private ContentBean contentBean;
    @Mock
    private SlingHttpServletRequest request;
    @Mock
    private InheritanceValueMap inheritedProp;
    @Mock
    private HierarchyNodeInheritanceValueMap herarchyNodeInheritanceValueMap;
    @Mock
    private ResourceResolver resourceResolver;
    @Mock
    private Session session;
    @Mock
    private Resource resource;
    @Mock
    private Property property;
    @Mock
    private TagManager tagManager;
    @Mock
    private Tag tag;


    String ecommPartnerType ="UCX";
    String currentPagePath="/content/vonage/live-copies/us/en/homepage";
    String thisNodePath="/content/vonage/live-copies/us/en/homepage";
    String usEnNodePath="/content/vonage/live-copies/us/en/homepage";
    String pathtoFind = usEnNodePath + "/jcr:content/";
    String usEnTitle = "Home Page";


    @BeforeEach
    public void setUp() throws Exception {
        model = new AnalyticsDataModel();
        PrivateAccessor.setField(model,"currentPage",currentPage);
        PrivateAccessor.setField(model,"resource",currentResource);

    }
    @Test
    public void testSetAnalyticsTitle() throws NoSuchFieldException, RepositoryException {

        PrivateAccessor.setField(model,"currentPage",currentPage);
        PrivateAccessor.setField(model,"resourceResolver",resourceResolver);
        when(this.currentPage.adaptTo(Node.class)).thenReturn(currentNode);
        when(currentNode.getPath()).thenReturn(thisNodePath);
        when(resourceResolver.getResource(pathtoFind)).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(usEnPageNode);
        when(usEnPageNode.hasProperty(JcrConstants.JCR_TITLE)).thenReturn(true);
        when(usEnPageNode.getProperty(JcrConstants.JCR_TITLE)).thenReturn(property);
        when(property.getString()).thenReturn("Example title");
        model.setAnalyticsTitle();
        assertEquals("Example title", model.getAnalyticsTitle());
    }
    @Test
    public void testSetAuthorNotNull() throws NoSuchFieldException {
        String author = "vonage:authors/heny-ford";
        String authorTest = "heny-ford";
        PrivateAccessor.setField(model,"author",author);
        model.setAuthor();
        assertEquals(authorTest, model.getAuthor());
        assertNotNull(model.getAuthor());
    }
    @Test
    public void testSetAuthorNull() throws NoSuchFieldException {
        String author = null;
        String authorTest = "";
        PrivateAccessor.setField(model,"author",author);
        model.setAuthor();
        assertEquals(authorTest, model.getAuthor());

    }
    @Test
    public void testSetAuthorNotNullAndNoParts() throws NoSuchFieldException {
        String author = "vonage:heny-ford";
        String authorTest = "";
        PrivateAccessor.setField(model,"author",author);
        model.setAuthor();
        assertEquals(authorTest, model.getAuthor());

    }
    @Test
    public void testSetContentType() throws NoSuchFieldException {
        String contentCategory = "vonage:articles/publications";
        String contenttype = "publications";
        PrivateAccessor.setField(model,"contentCategory",contentCategory);
        model.setContentType();
        assertEquals(contenttype, model.getContentCategory());
        assertNotNull(model.getContentCategory());
    }
    @Test
    public void testSetContentTypeNull() throws NoSuchFieldException {
        String contentCategory = null;
        String contenttype = "";
        PrivateAccessor.setField(model,"contentCategory",contentCategory);
        model.setContentType();
        assertEquals(contenttype, model.getContentCategory());

    }
    @Test
    public void testSetContentTypeNotNullAndNoParts() throws NoSuchFieldException {
        String contentCategory = "vonage:articles";
        String contenttype = "";
        PrivateAccessor.setField(model,"contentCategory",contentCategory);
        model.setContentType();
        assertEquals(contenttype, model.getContentCategory());

    }

    @Test
    public void testSetDatePublished() throws NoSuchFieldException {
        String datePublished = "10-10-2021";
        String dateformatted = "10/2021/10";
        PrivateAccessor.setField(model,"datePublished",datePublished);
        model.setDatePublished();
        assertEquals(dateformatted, model.getDatePublished());
        assertNotNull(model.getDatePublished());
    }
    @Test
    public void testSetDatePublishedNull() throws NoSuchFieldException {
        String datePublished = null;
        String dateformatted = "";
        PrivateAccessor.setField(model,"datePublished",datePublished);
        model.setDatePublished();
        assertEquals(dateformatted, model.getDatePublished());
    }
    @Test
    public void testSetDatePublishedLengthLesserThanTen() throws NoSuchFieldException {
        String datePublished = "10-10-21";
        String dateformatted = "";
        PrivateAccessor.setField(model,"datePublished",datePublished);
        model.setDatePublished();
        assertEquals(dateformatted, model.getDatePublished());

    }

    @Test
    public void testCreateContentBean() throws NoSuchFieldException {
        String analyticsTitle = "Test Title";
        PrivateAccessor.setField(model,"contentBean",contentBean);
        PrivateAccessor.setField(model,"analyticsTitle",analyticsTitle);
        model.createContentBean();
        assertNotNull(model.getContentBean());
    }
    @Test
    public void testCreateContentBeanNullAnalyticsTitle() throws NoSuchFieldException {
        String analyticsTitle = null;
        String title ="SomeTitle";
        PrivateAccessor.setField(model,"analyticsTitle",analyticsTitle);
        PrivateAccessor.setField(model,"contentBean",contentBean);
        PrivateAccessor.setField(model,"title",title);
        model.createContentBean();
        assertNotNull(model.getContentBean());
    }
    @Test
    public void testCreateContentBeanNullAnalyticandNullTitle() throws NoSuchFieldException {
        String analyticsTitle = null;
        String title ="null";
        PrivateAccessor.setField(model,"analyticsTitle",analyticsTitle);
        PrivateAccessor.setField(model,"contentBean",contentBean);
        PrivateAccessor.setField(model,"title",title);
        model.createContentBean();
        assertNotNull(model.getContentBean());
    }
    @Test
    public void testsetContentTagsAndpageTagsListNotNull() throws NoSuchFieldException {
        PrivateAccessor.setField(model,"resourceResolver",resourceResolver);
        when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
        when(tagManager.resolve("vonage:topic/AI")).thenReturn(tag);
        when(tag.getTitle()).thenReturn("Artificial Intelligence");
        List<String> pageTagsList = new ArrayList<>();
        pageTagsList.add("vonage:topic/AI");
        PrivateAccessor.setField(model,"pageTagsList",pageTagsList);
        model.setContentTags();
        assertNotNull(model.getTagMap());
    }
    @Test
    public void testsetContentTagsAndpageTagsListEmpty() throws NoSuchFieldException {
        PrivateAccessor.setField(model,"resourceResolver",resourceResolver);
        when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
        List<String> pageTagsList = new ArrayList<>();
        PrivateAccessor.setField(model,"pageTagsList",pageTagsList);
        model.setContentTags();
        assertNotNull(model.getTagMap());
    }
    @Test
    public void testsetContentTagsAndconcatenateFalse() throws NoSuchFieldException {
        PrivateAccessor.setField(model,"resourceResolver",resourceResolver);
        when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
        when(tagManager.resolve("abc:xyz/pq")).thenReturn(tag);
        when(tag.getTitle()).thenReturn("Artificial Intelligence");
        List<String> pageTagsList = new ArrayList<>();
        pageTagsList.add("abc:xyz/pq");
        PrivateAccessor.setField(model,"pageTagsList",pageTagsList);
        model.setContentTags();
        assertNotNull(model.getTagMap());
    }
    /*@Test
    public void testcreatePageInfoBean() throws NoSuchFieldException {
        PrivateAccessor.setField(model,"resourceResolver",resourceResolver);
        PrivateAccessor.setField(model,"resourcePage",resourcePage);
        List list = new ArrayList();
        when(resourcePage.getAbsoluteParent(1)).thenReturn(resourceParent1);
        when(model.getPageHierarchy()).thenReturn(list);
        when(tagManager.resolve("abc:xyz/pq")).thenReturn(tag);
        when(tag.getTitle()).thenReturn("Artificial Intelligence");
        List<String> pageTagsList = new ArrayList<>();
        pageTagsList.add("abc:xyz/pq");
        PrivateAccessor.setField(model,"pageTagsList",pageTagsList);
        model.createPageInfoBean();
        assertNotNull(model.getPageInfoBean());
    }*/
}
