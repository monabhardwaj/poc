package com.capitalone.dashboard.rest;

import com.capitalone.dashboard.config.TestConfig;
import com.capitalone.dashboard.config.WebMVCConfig;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.model.Feature;
import com.capitalone.dashboard.service.FeatureService;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class, WebMVCConfig.class })
@WebAppConfiguration
public class TestFeatureController {
	private static Feature mockV1Feature;
	private static Feature mockJiraFeature;
	private static Feature mockJiraFeature2;
	private static Component mockComponent;
	private static Collector mockCollector;
	private static CollectorItem mockItem;
	private static final String generalUseDate = "2015-11-01T00:00:00Z";
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private static Calendar cal = Calendar.getInstance();
	private static final String maxDateWinner = df.format(new Date());
	private static String maxDateLoser = new String();
	private static String currentSprintEndDate = new String();
	private static final ObjectId jiraCollectorId = new ObjectId();
	private static final ObjectId jiraCollectorId2 = new ObjectId();
	private static final ObjectId v1CollectorId = new ObjectId();
	private static final ObjectId mockComponentId = new ObjectId();
	private static final ObjectId mockCollectorItemId = new ObjectId();

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private FeatureService featureService;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		// Date-time modifications
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, -1);
		maxDateLoser = df.format(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR, +13);
		currentSprintEndDate = df.format(cal.getTime());

		// Helper mock data
		List<String> sOwnerNames = new ArrayList<String>();
		sOwnerNames.add("Goku");
		sOwnerNames.add("Gohan");
		sOwnerNames.add("Picolo");
		List<String> sOwnerDates = new ArrayList<String>();
		sOwnerNames.add(generalUseDate);
		sOwnerNames.add(generalUseDate);
		sOwnerNames.add(generalUseDate);
		List<String> sOwnerStates = new ArrayList<String>();
		sOwnerNames.add("Active");
		sOwnerNames.add("Active");
		sOwnerNames.add("Deleted");
		List<String> sOwnerIds = new ArrayList<String>();
		sOwnerNames.add("9001");
		sOwnerNames.add("8999");
		sOwnerNames.add("7999");
		List<String> sOwnerBools = new ArrayList<String>();
		sOwnerNames.add("True");
		sOwnerNames.add("False");
		sOwnerNames.add("True");

		// VersionOne Mock Feature
		mockV1Feature = new Feature();
		mockV1Feature.setCollectorId(jiraCollectorId);
		mockV1Feature.setIsDeleted("True");
		mockV1Feature.setChangeDate(generalUseDate);
		mockV1Feature.setsEpicAssetState("Active");
		mockV1Feature.setsEpicBeginDate(generalUseDate);
		mockV1Feature.setsEpicChangeDate(generalUseDate);
		mockV1Feature.setsEpicEndDate(generalUseDate);
		mockV1Feature.setsEpicHPSMReleaseID("CO312615921");
		mockV1Feature.setsEpicID("E-12345");
		mockV1Feature.setsEpicIsDeleted("False");
		mockV1Feature.setsEpicName("Test Epic 1");
		mockV1Feature.setsEpicNumber("12938715");
		mockV1Feature.setsEpicPDD(generalUseDate);
		mockV1Feature.setsEpicType("Portfolio Feature");
		mockV1Feature.setsEstimate("5");
		mockV1Feature.setsId("B-12345");
		mockV1Feature.setsName("Test Story 1");
		mockV1Feature.setsNumber("12345416");
		mockV1Feature.setsOwnersChangeDate(sOwnerDates);
		mockV1Feature.setsOwnersFullName(sOwnerNames);
		mockV1Feature.setsOwnersID(sOwnerIds);
		mockV1Feature.setsOwnersIsDeleted(sOwnerBools);
		mockV1Feature.setsOwnersShortName(sOwnerNames);
		mockV1Feature.setsOwnersState(sOwnerStates);
		mockV1Feature.setsOwnersUsername(sOwnerNames);
		mockV1Feature.setsProjectBeginDate(generalUseDate);
		mockV1Feature.setsProjectChangeDate(generalUseDate);
		mockV1Feature.setsProjectEndDate(generalUseDate);
		mockV1Feature.setsProjectID("Scope:231870");
		mockV1Feature.setsProjectIsDeleted("False");
		mockV1Feature.setsProjectName("Test Scope 1");
		mockV1Feature.setsProjectPath("Top -> Middle -> Bottome -> "
				+ mockV1Feature.getsProjectName());
		mockV1Feature.setsProjectState("Active");
		mockV1Feature.setsSoftwareTesting("True");
		mockV1Feature.setsSprintAssetState("Inactive");
		mockV1Feature.setsSprintBeginDate(generalUseDate);
		mockV1Feature.setsSprintChangeDate(generalUseDate);
		mockV1Feature.setsSprintEndDate(maxDateWinner);
		mockV1Feature.setsSprintID("Timebox:12781205");
		mockV1Feature.setsSprintIsDeleted("False");
		mockV1Feature.setsSprintName("Test Sprint 1");
		mockV1Feature.setsState("Inactive");
		mockV1Feature.setsStatus("Accepted");
		mockV1Feature.setsTeamAssetState("Active");
		mockV1Feature.setsTeamChangeDate(generalUseDate);
		mockV1Feature.setsTeamID("Team:124127");
		mockV1Feature.setsTeamIsDeleted("False");
		mockV1Feature.setsTeamName("Protectors of Earth");

		// Jira Mock Feature
		// Mock feature 1
		mockJiraFeature = new Feature();
		mockJiraFeature.setCollectorId(jiraCollectorId);
		mockJiraFeature.setIsDeleted("False");
		mockJiraFeature.setChangeDate(maxDateWinner);
		mockJiraFeature.setsEpicAssetState("Active");
		mockJiraFeature.setsEpicBeginDate("");
		mockJiraFeature.setsEpicChangeDate(maxDateWinner);
		mockJiraFeature.setsEpicEndDate("");
		mockJiraFeature.setsEpicHPSMReleaseID("");
		mockJiraFeature.setsEpicID("32112345");
		mockJiraFeature.setsEpicIsDeleted("");
		mockJiraFeature.setsEpicName("Test Epic 1");
		mockJiraFeature.setsEpicNumber("12938715");
		mockJiraFeature.setsEpicPDD("");
		mockJiraFeature.setsEpicType("");
		mockJiraFeature.setsEstimate("40");
		mockJiraFeature.setsId("0812345");
		mockJiraFeature.setsName("Test Story 2");
		mockJiraFeature.setsNumber("12345416");
		mockJiraFeature.setsOwnersChangeDate(sOwnerDates);
		mockJiraFeature.setsOwnersFullName(sOwnerNames);
		mockJiraFeature.setsOwnersID(sOwnerIds);
		mockJiraFeature.setsOwnersIsDeleted(sOwnerBools);
		mockJiraFeature.setsOwnersShortName(sOwnerNames);
		mockJiraFeature.setsOwnersState(sOwnerStates);
		mockJiraFeature.setsOwnersUsername(sOwnerNames);
		mockJiraFeature.setsProjectBeginDate(maxDateWinner);
		mockJiraFeature.setsProjectChangeDate(maxDateWinner);
		mockJiraFeature.setsProjectEndDate(maxDateWinner);
		mockJiraFeature.setsProjectID("583482");
		mockJiraFeature.setsProjectIsDeleted("False");
		mockJiraFeature.setsProjectName("Saiya-jin Warriors");
		mockJiraFeature.setsProjectPath("");
		mockJiraFeature.setsProjectState("Active");
		mockJiraFeature.setsSoftwareTesting("");
		mockJiraFeature.setsSprintAssetState("Active");
		mockJiraFeature.setsSprintBeginDate(maxDateLoser);
		mockJiraFeature.setsSprintChangeDate(maxDateWinner);
		mockJiraFeature.setsSprintEndDate(currentSprintEndDate);
		mockJiraFeature.setsSprintID("1232512");
		mockJiraFeature.setsSprintIsDeleted("False");
		mockJiraFeature.setsSprintName("Test Sprint 2");
		mockJiraFeature.setsState("Active");
		mockJiraFeature.setsStatus("In Progress");
		mockJiraFeature.setsTeamAssetState("Active");
		mockJiraFeature.setsTeamChangeDate(maxDateWinner);
		mockJiraFeature.setsTeamID("08374321");
		mockJiraFeature.setsTeamIsDeleted("False");
		mockJiraFeature.setsTeamName("Saiya-jin Warriors");

		// Mock feature 2
		mockJiraFeature2 = new Feature();
		mockJiraFeature2.setCollectorId(jiraCollectorId2);
		mockJiraFeature2.setIsDeleted("False");
		mockJiraFeature2.setChangeDate(maxDateLoser);
		mockJiraFeature2.setsEpicAssetState("Active");
		mockJiraFeature2.setsEpicBeginDate("");
		mockJiraFeature2.setsEpicChangeDate(maxDateLoser);
		mockJiraFeature2.setsEpicEndDate("");
		mockJiraFeature2.setsEpicHPSMReleaseID("");
		mockJiraFeature2.setsEpicID("32112345");
		mockJiraFeature2.setsEpicIsDeleted("");
		mockJiraFeature2.setsEpicName("Test Epic 1");
		mockJiraFeature2.setsEpicNumber("12938715");
		mockJiraFeature2.setsEpicPDD("");
		mockJiraFeature2.setsEpicType("");
		mockJiraFeature2.setsEstimate("40");
		mockJiraFeature2.setsId("0812346");
		mockJiraFeature2.setsName("Test Story 3");
		mockJiraFeature2.setsNumber("12345417");
		mockJiraFeature2.setsOwnersChangeDate(sOwnerDates);
		mockJiraFeature2.setsOwnersFullName(sOwnerNames);
		mockJiraFeature2.setsOwnersID(sOwnerIds);
		mockJiraFeature2.setsOwnersIsDeleted(sOwnerBools);
		mockJiraFeature2.setsOwnersShortName(sOwnerNames);
		mockJiraFeature2.setsOwnersState(sOwnerStates);
		mockJiraFeature2.setsOwnersUsername(sOwnerNames);
		mockJiraFeature2.setsProjectBeginDate(maxDateLoser);
		mockJiraFeature2.setsProjectChangeDate(maxDateLoser);
		mockJiraFeature2.setsProjectEndDate(maxDateLoser);
		mockJiraFeature2.setsProjectID("583483");
		mockJiraFeature2.setsProjectIsDeleted("False");
		mockJiraFeature2.setsProjectName("Not Cell!");
		mockJiraFeature2.setsProjectPath("");
		mockJiraFeature2.setsProjectState("Active");
		mockJiraFeature2.setsSoftwareTesting("");
		mockJiraFeature2.setsSprintAssetState("Active");
		mockJiraFeature2.setsSprintBeginDate(maxDateLoser);
		mockJiraFeature2.setsSprintChangeDate(maxDateWinner);
		mockJiraFeature2.setsSprintEndDate(currentSprintEndDate);
		mockJiraFeature2.setsSprintID("1232512");
		mockJiraFeature2.setsSprintIsDeleted("False");
		mockJiraFeature2.setsSprintName("Test Sprint 3");
		mockJiraFeature2.setsState("Active");
		mockJiraFeature2.setsStatus("In Progress");
		mockJiraFeature2.setsTeamAssetState("Active");
		mockJiraFeature2.setsTeamChangeDate(maxDateLoser);
		mockJiraFeature2.setsTeamID("08374329");
		mockJiraFeature2.setsTeamIsDeleted("False");
		mockJiraFeature2.setsTeamName("Interlopers");

		// Creating Collector and Component relationship artifacts
		mockCollector = new Collector();
		mockCollector.setCollectorType(CollectorType.Feature);
		mockCollector.setEnabled(true);
		mockCollector.setName("VersionOne Collector");
		mockCollector.setOnline(true);
		mockCollector.setId(v1CollectorId);

		mockItem = new CollectorItem();
		mockItem.setCollectorId(v1CollectorId);
		mockItem.setDescription("Sample Dashboard Collector Item");
		mockItem.setEnabled(true);
		mockItem.setId(mockCollectorItemId);
		mockItem.setCollector(mockCollector);

		mockComponent = new Component();
		mockComponent.addCollectorItem(CollectorType.Feature, mockItem);
	}

	@After
	public void after() {
		mockV1Feature = null;
		mockJiraFeature = null;
		mockJiraFeature2 = null;
		mockCollector = null;
		mockItem = null;
		mockComponent = null;
		mockMvc = null;
	}
	
	@Test
	public void testRelevantStories_HappyPath() throws Exception {
		String testTeamId = "Team:124127";
		List<Feature> features = new ArrayList<Feature>();
		features.add(mockV1Feature);
		features.add(mockJiraFeature);
		features.add(mockJiraFeature2);
		DataResponse<List<Feature>> response = new DataResponse<>(features,
				mockCollector.getLastExecuted());

		when(featureService.getFeatureEstimates(v1CollectorId, testTeamId)).thenReturn(response);
		mockMvc.perform(
				get("/feature/{teamId}", testTeamId).param("component",
						mockComponentId.toString())).andExpect(status().isOk());
	}
}
