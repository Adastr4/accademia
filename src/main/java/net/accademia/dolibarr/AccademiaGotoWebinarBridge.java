package net.accademia.dolibarr;

public class AccademiaGotoWebinarBridge extends GotoWebinarBridge {

    public AccademiaGotoWebinarBridge(DemoneMediator dm) throws Exception {
        super(dm);
        GotoWebinarKeyClient = "6d8b54b0-787c-418c-b20a-e1009247d124";

        GotoWebinarKeyClientSecret = "5mbvleugSuVgAg46qSrA0ueQ";
        authheader = "NmQ4YjU0YjAtNzg3Yy00MThjLWIyMGEtZTEwMDkyNDdkMTI0OjVtYnZsZXVnU3VWZ0FnNDZxU3JBMHVlUQ==";
        refreshtoken =
            "eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJzdWIiOiI4ODk4ODUwNDkzMTU0MTk3ODg4IiwiYXVkIjoiNmQ4YjU0YjAtNzg3Yy00MThjLWIyMGEtZTEwMDkyNDdkMTI0Iiwib2duIjoicHdkIiwidHlwIjoiciIsImV4cCI6MTYzOTY2MDU2MCwiaWF0IjoxNjM3MDY4NTYwLCJqdGkiOiI4MzJjZDU4ZS1jNTNjLTQ2MjQtYmMxYS1mYzJmODg5YTVkNjMifQ.ZZ58nLQHe2I0eMbSaxJAhPj3Qx39mCV1LhFwo70mrmxVi4PEwgXSa4LDak2xd_uRNgUDQVIM2W3aIS9C7WKTWT8tpKm3iIEt6LaT641qU0ODyR_T_gXpjmCw9nNo0Um5HQzaoUQJMJAOMmVvznk9ddELm6kdS5IhRq5qe_cTfLTpogKZBKw3EyKyW4vsuRuQKm_DApzIGKakb6OOfTpaHQmPtqP6RSP-xsdka4KDt6UNhnxia9KCrLD5HMGqJ8r2iZMCmJlLqm95LRkkTCH7Pam4CvpkwrtqMcV5gOFFcieyQru6g3qktEkouzKNqAMY8OvT5GsJLQXfcQZVgYPWpw";
        accesstoken =
            "eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJzdWIiOiI4ODk4ODUwNDkzMTU0MTk3ODg4IiwiYXVkIjoiNmQ4YjU0YjAtNzg3Yy00MThjLWIyMGEtZTEwMDkyNDdkMTI0Iiwib2duIjoicHdkIiwibHMiOiI3ZTNjYzlhNS01NGI3LTQzNjEtODgzZi03ODk4YWZiNzIyYTYiLCJ0eXAiOiJjIiwiZXhwIjoxNjM3MDY5MTEyLCJpYXQiOjE2MzcwNjg1MTIsImp0aSI6IjEzMjgxYWEzLTJjODgtNGI2NC04YWNhLTdkYjc0NzgyN2I5NCJ9.djjLysMkH6x5mOKlkWIxpyowaepHGCblOI9HKOQqf2DLZTYtRg3cktAcihG35K-qaX76fN3q8rNi_77YNyAy3ozNqpivPihJaNML-bztR7Zjj93pl5Fc88Hy_D2d5kSq_WI0lvSKEuB__-HfA4WHC6V8csYiPnuWXEAf5Pc9HOT24-gczsrR7RYSOdVbydB7mfjFqQ-u0LP1IypYjx2uTRj-XGNLYPuMAzhqEQc8s7HsxMRepmxOe5ozadyzrdYi8g6dDQPcDQ7jPxc_rYg1X--1aUxQMZ6KJgn7NeXhHexNnccZl03_0mrIGUqPDES9jfeVK1tuZsNBbXDFbolbiQ";

        oaut =
            "https://api.getgo.com/oauth/v2/authorize?client_id=6d8b54b0-787c-418c-b20a-e1009247d124&response_type=code&redirect_uri=https://www.accademiaeuropea.net/gotowebinar/oauth";
    }
}
