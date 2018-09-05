package org.stormnetdev.utils;


import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.domain.BasicProject;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.utils.configuration.Configurator;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Jira {
	
	private static JiraRestClient jiraLogIn;
	private String urlJira = Configurator.urlJira;
	private String emailJira = Configurator.emailJira;
	private String passwordJira = Configurator.passwordJira;
	private List<Integer> rootCausesBugs = new ArrayList();
	private List<Object> allProjects = new ArrayList();
	private List<Object> allProjectsNames = new ArrayList();
	
		
	public Jira() {
		try{
        	JerseyJiraRestClientFactory f = new JerseyJiraRestClientFactory();	        
        	jiraLogIn = f.createWithBasicHttpAuthentication(new URI(urlJira), emailJira, passwordJira);
        }catch(URISyntaxException e){
    		System.out.println("Invalid jira URL");
    		e.printStackTrace();
        }
	}
        
	private int getCountOfIssues(String filter, Object project) {
		String issuesFilter = String.format(filter, project);
	    SearchResult r = jiraLogIn.getSearchClient().searchJql(issuesFilter, 500, 1, null);
	    int total = r.getTotal();
		System.out.println("Total issues = " + total);
	    return total;
	}
	
	private void getCountOfBugsWithRootCause(String filter, List<Object> rootCauses, Object project) {
		rootCausesBugs.clear();
		for(int i = 0; i < rootCauses.size(); i++){
    		String rootCauseFilter = String.format(filter, project, rootCauses.get(i));
    		SearchResult r = jiraLogIn.getSearchClient().searchJql(rootCauseFilter, 500, 1, null);
            int total = r.getTotal();
    		System.out.println("Root Cause = '" + rootCauses.get(i) + "'");
    		System.out.println("	Total issues = " + total);
    		rootCausesBugs.add(total);
        }
	}
	
    private List<Object> getAllProjects() {
	    Iterable<BasicProject> projects;
        System.out.println("Start projects searching...");
       	projects = jiraLogIn.getProjectClient().getAllProjects(null);
        System.out.println("Success!");        
        Iterator<BasicProject> iterator = projects.iterator();       
		while (iterator.hasNext()) {             
        	BasicProject element = iterator.next();
            System.out.println("Project key: " + element.getKey());
            allProjects.add(element.getKey());
            allProjectsNames.add(element.getName());
        }
		return allProjects;
    }

	private Long issueStatus(String issueName){
		Reporter.logOperation("Get " + issueName + " issue status.");
		final NullProgressMonitor pm = new NullProgressMonitor();
		final Issue issue = jiraLogIn.getIssueClient().getIssue(issueName, pm);
		String issueStatusName = issue.getStatus().getName();
		Long issueStatusId = issue.getStatus().getId();
		Reporter.logInfo("Issue status name is: " + issueStatusName);
		Reporter.logInfo("Issue status Id is: " + issueStatusId);
		Reporter.logPassedOperation();
		return issueStatusId;
	}

	public Boolean checkThatIssueClosed(String issueName){
		if(issueStatus(issueName) == 10001){
			return true;
		}else {
			return false;
		}
	}

	
}
