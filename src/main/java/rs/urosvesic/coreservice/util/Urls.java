package rs.urosvesic.coreservice.util;

public interface Urls {

    //User routes
    static final String SAVE_USER = "/api/user";
    static final String UPDATE_USER = "/api/user";
    static final String FIND_USER_BY_USERNAME = "/api/user/{username}";
    static final String FIND_USER_BY_ID = "/api/user/{id}";
    static final String GET_PROFILE_INFO = "/api/user/profile-info/{username}";
    static final String GET_SUGGESTED_USERS = "/api/user/suggested";
    static final String GET_ALL_REPORTED_USERS = "/api/user/reported";
    static final String FOLLOW = "/api/user/follow/{username}";
    static final String UNFOLLOW = "/api/user/unfollow/{username}";
    static final String ENABLE = "/api/user/enable/{username}";
    static final String DISABLE = "/api/user/disable/{username}";
    static final String GET_ALL_FOLLOWERS_FOR_USER = "/api/user/{username}/followers";
    static final String GET_ALL_FOLLOWING_FOR_USER = "/api/user/{username}/following";

    //Post
    static final String REPORT_POST = "/api/post/{postId}/report";
    static final String GET_ALL_COMMENTS_FOR_POST = "/api/post/{postId}/comment";
    static final String GET_ALL_POSTS_FOR_TOPIC = "/api/post/topic/{topicName}";
    static final String GET_ALL_POSTS_FOR_FOLLOWING_USERS = "/api/post/authAll";
    static final String GET_ALL_UNSOLVED_REPORTED_POSTS = "/api/post/secured/reported";
    static final String GET_ALL_SOLVED_POSTS = "/api/post/reported-solved";
    static final String SOFT_DELETE = "/api/post/soft-delete";
    static final String CREATE_POST = "/api/post";
    static final String GET_POST_BY_ID = "/api/post/{id}";
    static final String GET_ALL_POSTS_FOR_USER = "/api/post/user/{username}";
    static final String DELETE_POST = "/api/post/{id}";
    static final String UPDATE_POST = "/api/post/update/{id}";

    //Topic routes
    static final String CREATE_TOPIC = "/api/topic";
    static final String GET_ALL_TOPICS = "/api/topic";

    //Reaction
    static final String REACT = "/api/react";

    //Post report
    static final String CHANGE_REPORT_STATUS = "/api/report";
}
