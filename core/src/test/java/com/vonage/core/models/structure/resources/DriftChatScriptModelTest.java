package com.vonage.core.models.structure.resources;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import com.day.cq.wcm.api.Page;

import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class DriftChatScriptModelTest {


	DriftChatScriptModel model;

	@Mock
	Page currentPage;
	
	@Mock
	Node currentPageNode;
	
	
	List<String> pageTagsList ;


	@BeforeEach
	public void setUp() throws Exception {
		model = new DriftChatScriptModel();
		pageTagsList = new ArrayList<>();
		PrivateAccessor.setField(model,"pageTagsList",pageTagsList);
		PrivateAccessor.setField(model,"currentPage",currentPage);
		
	}

	@Test
	void testDriftChatScriptModelWithHomepage() throws RepositoryException {
		pageTagsList.add("vonage:product/communications-apis");
		Mockito.when(currentPage.adaptTo(Node.class)).thenReturn(currentPageNode);
		Mockito.when(currentPageNode.getName()).thenReturn("homepage");
		Mockito.when(currentPage.getPath()).thenReturn("/content/vonage/homepage");
		model.setDriftChatScript();
		assertNotNull(model.getPageTagList());
		assertNotNull(model.getApiScript());
		assertNotNull(model.getApplicationsScript());
		assertNull(model.getTagValues());
		assertNotNull(model.getPagePath());
		assertNotNull(model.getPageName());
	}
	
	
	@Test
	void testDriftChatScriptModelWithResource() throws RepositoryException {
		pageTagsList.add("vonage:test/test1");
		Mockito.when(currentPage.adaptTo(Node.class)).thenReturn(currentPageNode);
		Mockito.when(currentPageNode.getName()).thenReturn("homepage1");
		Mockito.when(currentPage.getPath()).thenReturn("/content/vonage/resources");
		model.setDriftChatScript();
		assertNotNull(model.getPageTagList());
		assertNotNull(model.getApiScript());
		assertNotNull(model.getApplicationsScript());
		assertNull(model.getTagValues());
		assertNotNull(model.getPagePath());
		assertNotNull(model.getPageName());
	}
	
	
	@Test
	void testDriftChatScriptModelWithAboutus() throws RepositoryException {
		pageTagsList.add("vonage:test/test1");
		Mockito.when(currentPage.adaptTo(Node.class)).thenReturn(currentPageNode);
		Mockito.when(currentPageNode.getName()).thenReturn("homepage1");
		Mockito.when(currentPage.getPath()).thenReturn("/content/vonage/about-us");
		model.setDriftChatScript();
		assertNotNull(model.getPageTagList());
		assertNotNull(model.getApiScript());
		assertNotNull(model.getApplicationsScript());
		assertNull(model.getTagValues());
		assertNotNull(model.getPagePath());
		assertNotNull(model.getPageName());
	}
	@Test
	void testDriftChatScriptModelWithUC() throws RepositoryException {
		pageTagsList.add("vonage:test/test1");
		Mockito.when(currentPage.adaptTo(Node.class)).thenReturn(currentPageNode);
		Mockito.when(currentPageNode.getName()).thenReturn("homepage1");
		Mockito.when(currentPage.getPath()).thenReturn("/content/vonage/unified-communications");
		model.setDriftChatScript();
		assertNotNull(model.getPageTagList());
		assertNotNull(model.getApiScript());
		assertNotNull(model.getApplicationsScript());
		assertNull(model.getTagValues());
		assertNotNull(model.getPagePath());
		assertNotNull(model.getPageName());
	}
	@Test
	void testDriftChatScriptModelWithCC() throws RepositoryException {
		pageTagsList.add("vonage:test/test1");
		Mockito.when(currentPage.adaptTo(Node.class)).thenReturn(currentPageNode);
		Mockito.when(currentPageNode.getName()).thenReturn("homepage1");
		Mockito.when(currentPage.getPath()).thenReturn("/content/vonage/contact-centers");
		model.setDriftChatScript();
		assertNotNull(model.getPageTagList());
		assertNotNull(model.getApiScript());
		assertNotNull(model.getApplicationsScript());
		assertNull(model.getTagValues());
		assertNotNull(model.getPagePath());
		assertNotNull(model.getPageName());
	}
	@Test
	void testDriftChatScriptModelWithCA() throws RepositoryException {
		pageTagsList.add("vonage:test/test1");
		Mockito.when(currentPage.adaptTo(Node.class)).thenReturn(currentPageNode);
		Mockito.when(currentPageNode.getName()).thenReturn("homepage1");
		Mockito.when(currentPage.getPath()).thenReturn("/content/vonage/communications-apis");
		model.setDriftChatScript();
		assertNotNull(model.getPageTagList());
		assertNotNull(model.getApiScript());
		assertNotNull(model.getApplicationsScript());
		assertNull(model.getTagValues());
		assertNotNull(model.getPagePath());
		assertNotNull(model.getPageName());
	}
	
	@Test
	void testDriftChatScriptModelWithNull() throws RepositoryException, NoSuchFieldException {
		currentPageNode = null;
		PrivateAccessor.setField(model,"pageTagsList",null);
		Mockito.when(currentPage.adaptTo(Node.class)).thenReturn(null);
		Mockito.when(currentPage.getPath()).thenReturn("/content/vonage/communications-apis");
		model.setDriftChatScript();
	}
	@Test
    public void testNull() {
        assertTrue(pageTagsList.size() == 0);
    }
}
