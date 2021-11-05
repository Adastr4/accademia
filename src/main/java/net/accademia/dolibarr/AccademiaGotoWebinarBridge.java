package net.accademia.dolibarr;

public class AccademiaGotoWebinarBridge extends GotoWebinarBridge {

    public AccademiaGotoWebinarBridge(DemoneMediator dm) {
        super(dm);
        GotoWebinarKeyClient = "6d8b54b0-787c-418c-b20a-e1009247d124";

        GotoWebinarKeyClientSecret = "5mbvleugSuVgAg46qSrA0ueQ";
        authheader = "NmQ4YjU0YjAtNzg3Yy00MThjLWIyMGEtZTEwMDkyNDdkMTI0OjVtYnZsZXVnU3VWZ0FnNDZxU3JBMHVlUQ==";
        refreshtoken =
            "eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsImF1ZCI6IjZkOGI1NGIwLTc4N2MtNDE4Yy1iMjBhLWUxMDA5MjQ3ZDEyNCIsInN1YiI6Ijg4OTg4NTA0OTMxNTQxOTc4ODgiLCJqdGkiOiJlYTI1NDYzNi1jMTgwLTQ1OTctODBkMS00OWJlYThiNDFkZGUiLCJleHAiOjE2Mzc4Mjk3NzgsImlhdCI6MTYzNTIzNzc3OCwidHlwIjoiciJ9.Q0aYIq0LNHxWllRQuUCau6z27q-ep5I0sGxo3LiwAwL6CqUPKSVmlQ4ZVDluuYtqhLRamWPxa2tgOg-LJoVHcQKv-etKq4mX7HEImEzfhlgWaq64uIHHM-pz0JlqjZCdGbMEmg1WMwQlDvQF1VD-B1OoXOleWW64qmJfb6NsCvu68q0V9st-lmheErXabfeAXNokvV5OyI0f6y0ft6MnSw6w_bvlEgQa97Bf9pcKGo6m6cOGQ-qvkVMVFU5bGKoc2Zj9CAMVlpZV2ri63OD8WE6841CS8eAvZUg1tJWLpZB_4rm_qTDncTyY39q80HmhrXeIxRVfubObmm3ItLkdaQ";
        accesstoken =
            "eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJvZ24iOiJwd2QiLCJhdWQiOiI2ZDhiNTRiMC03ODdjLTQxOGMtYjIwYS1lMTAwOTI0N2QxMjQiLCJzdWIiOiI4ODk4ODUwNDkzMTU0MTk3ODg4IiwianRpIjoiZDE4YTgxNjUtMTBmNy00NzM0LWJmMjMtNzRjZTk0MmE4MDMzIiwiZXhwIjoxNjM1Mjc2MTUzLCJpYXQiOjE2MzUyNzI1NTMsInR5cCI6ImEifQ.dII63cG1UMiSCnR4HOs_DfxNwzbvA-I4A0FQ-te0bqBBhe0Yy6zYrrCfKgb_jVq0VbzphkOIpLV0PSPMLqXlL68b7ho5kZsRdWCCELt1WNoeRzP2pYCJMiLFUNeH0f8mlPZJjGJmakl6WbTag3Lqak9Ygrc5965_nPNkhM-C6BT0V5JL2K6ApuldQNAMLywaliBE2gz61j1dm-Ori3g3VrQZeAhrClkeanwzJvsgiczUpMcyTjlnOco9ugU753kuB3KxWW76lWMHOZeAfd7zuI3Ut7xRtLg6RnE-onYHJQNJOCpjZzvZ6Ex-02vxkt8XJe1KlNO18NWcmKXFkd7Clg";

        oaut =
            "https://api.getgo.com/oauth/v2/authorize?client_id=6d8b54b0-787c-418c-b20a-e1009247d124&response_type=code&redirect_uri=https://www.accademiaeuropea.net/gotowebinar/oauth";
    }
}
