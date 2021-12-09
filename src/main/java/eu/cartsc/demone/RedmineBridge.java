package eu.cartsc.demone;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.TimeEntryManager;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.TimeEntry;
import com.taskadapter.redmineapi.internal.ResultsWrapper;
import com.taskadapter.redmineapi.internal.Transport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author adastra https://github.com/taskadapter/redmine-java-api
 */
public class RedmineBridge {

    String uri = null;
    String apiAccessKey = null;
    String projectKey = null;
    Integer queryId = null; // any
    RedmineManager mgr = null;
    String activityId;
    Transport transport = null;

    protected RedmineBridge() {}

    protected void init() {
        mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        transport = mgr.getTransport();
    }

    List<Issue> getIssue() {
        try {
            // override default page size if needed
            mgr.setObjectsPerPage(100);
            List<Issue> issues = mgr.getIssueManager().getIssues(projectKey, queryId);
            for (Issue issue : issues) {
                System.out.println(issue.toString());
            }
            return issues;
        } catch (RedmineException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    ResultsWrapper<TimeEntry> getTimeEntries() {
        try {
            TimeEntryManager timeEntryManager = mgr.getTimeEntryManager();
            final Map<String, String> params = new HashMap<>();
            params.put("project_id", projectKey);
            //params.put("activity_id", activityId);
            ResultsWrapper<TimeEntry> elements = timeEntryManager.getTimeEntries(params);
            return elements;
        } catch (RedmineException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
