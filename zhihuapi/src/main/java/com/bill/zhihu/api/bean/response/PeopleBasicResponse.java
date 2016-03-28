package com.bill.zhihu.api.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/3/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeopleBasicResponse {

    @JsonProperty("uid")
    public String uid;
    @JsonProperty("draft_count")
    public String draftCount;
    @JsonProperty("following_topic_count")
    public String followingTopicCount;
    @JsonProperty("description")
    public String description;
    @JsonProperty("avatar_url")
    public String avatarUrl;
    @JsonProperty("is_active")
    public String isActive;
    @JsonProperty("shared_count")
    public String sharedCount;
    @JsonProperty("columns_count")
    public String columnsCount;
    @JsonProperty("answer_count")
    public String answerCount;
    @JsonProperty("friendly_score")
    public String friendlyScore;
    @JsonProperty("id")
    public String id;
    @JsonProperty("is_locked")
    public String isLocked;
    @JsonProperty("favorite_count")
    public String favoriteCount;
    @JsonProperty("voteup_count")
    public String voteupCount;
    @JsonProperty("is_baned")
    public String isBaned;
    @JsonProperty("following_columns_count")
    public String followingColumnsCount;
    @JsonProperty("has_daily_recommend_permission")
    public String hasDailyRecommendPermission;
    @JsonProperty("name")
    public String name;
    // people home page
    @JsonProperty("url")
    public String url;
    @JsonProperty("gender")
    public String gender;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("headline")
    public String headline;
    @JsonProperty("is_bind_sina")
    public String isBindSina;
    @JsonProperty("favorited_count")
    public String favoritedCount;
    @JsonProperty("following_question_count")
    public String followingQuestionCount;
    @JsonProperty("question_count")
    public String questionCount;
    @JsonProperty("thanked_count")
    public String thankedCount;
    @JsonProperty("follower_count")
    public String followerCount;
    @JsonProperty("articles_count")
    public String articlesCount;
    @JsonProperty("type")
    public String type;
    @JsonProperty("email")
    public String email;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDraftCount() {
        return draftCount;
    }

    public void setDraftCount(String draftCount) {
        this.draftCount = draftCount;
    }

    public String getFollowingTopicCount() {
        return followingTopicCount;
    }

    public void setFollowingTopicCount(String followingTopicCount) {
        this.followingTopicCount = followingTopicCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(String sharedCount) {
        this.sharedCount = sharedCount;
    }

    public String getColumnsCount() {
        return columnsCount;
    }

    public void setColumnsCount(String columnsCount) {
        this.columnsCount = columnsCount;
    }

    public String getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(String answerCount) {
        this.answerCount = answerCount;
    }

    public String getFriendlyScore() {
        return friendlyScore;
    }

    public void setFriendlyScore(String friendlyScore) {
        this.friendlyScore = friendlyScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getVoteupCount() {
        return voteupCount;
    }

    public void setVoteupCount(String voteupCount) {
        this.voteupCount = voteupCount;
    }

    public String getIsBaned() {
        return isBaned;
    }

    public void setIsBaned(String isBaned) {
        this.isBaned = isBaned;
    }

    public String getFollowingColumnsCount() {
        return followingColumnsCount;
    }

    public void setFollowingColumnsCount(String followingColumnsCount) {
        this.followingColumnsCount = followingColumnsCount;
    }

    public String getHasDailyRecommendPermission() {
        return hasDailyRecommendPermission;
    }

    public void setHasDailyRecommendPermission(String hasDailyRecommendPermission) {
        this.hasDailyRecommendPermission = hasDailyRecommendPermission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getIsBindSina() {
        return isBindSina;
    }

    public void setIsBindSina(String isBindSina) {
        this.isBindSina = isBindSina;
    }

    public String getFavoritedCount() {
        return favoritedCount;
    }

    public void setFavoritedCount(String favoritedCount) {
        this.favoritedCount = favoritedCount;
    }

    public String getFollowingQuestionCount() {
        return followingQuestionCount;
    }

    public void setFollowingQuestionCount(String followingQuestionCount) {
        this.followingQuestionCount = followingQuestionCount;
    }

    public String getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }

    public String getThankedCount() {
        return thankedCount;
    }

    public void setThankedCount(String thankedCount) {
        this.thankedCount = thankedCount;
    }

    public String getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(String followerCount) {
        this.followerCount = followerCount;
    }

    public String getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(String articlesCount) {
        this.articlesCount = articlesCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
