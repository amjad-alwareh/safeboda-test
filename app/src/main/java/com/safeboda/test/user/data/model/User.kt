package com.safeboda.test.user.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "USER")
data class User(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo @SerializedName("login") val login: String?,
    @ColumnInfo @SerializedName("node_id") val nodeId: String?,
    @ColumnInfo @SerializedName("avatar_url") val avatarUrl: String?,
    @ColumnInfo @SerializedName("gravatar_id") val gravatarId: String?,
    @ColumnInfo @SerializedName("html_url") val htmlUrl: String?,
    @ColumnInfo @SerializedName("type") val type: String?,
    @ColumnInfo @SerializedName("name") val name: String?,
    @ColumnInfo @SerializedName("company") val company: String?,
    @ColumnInfo @SerializedName("blog") val blog: String?,
    @ColumnInfo @SerializedName("location") val location: String?,
    @ColumnInfo @SerializedName("email") val email: String?,
    @ColumnInfo @SerializedName("hireable") val hireable: Boolean,
    @ColumnInfo @SerializedName("bio") val bio: String?,
    @ColumnInfo @SerializedName("twitter_username") val twitterUsername: String?,
    @ColumnInfo @SerializedName("public_repos") val publicRepos: Int,
    @ColumnInfo @SerializedName("public_gists") val publicGists: Int,
    @ColumnInfo @SerializedName("followers") val followers: Int,
    @ColumnInfo @SerializedName("following") val following: Int,
    @ColumnInfo @SerializedName("created_at") val createdAt: String?,
    @ColumnInfo @SerializedName("updated_at") val updatedAt: String?
)