package eu.cartsc.demone;

import com.taskadapter.redmineapi.RedmineManager;

public class CARTRedmineBridge extends RedmineBridge {
    {
        uri = "http://redmine.cartsc.eu";
        apiAccessKey = "82ae851a411005d165a17982fa48489f26f0f9b7";
        projectKey = "cart";
        //		queryId = null; // any
        //
        //		activityId;

    }

    public CARTRedmineBridge() {
        init();
    }
}
