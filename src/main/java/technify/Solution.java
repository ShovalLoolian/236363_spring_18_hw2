package technify;

import technify.business.*;

import static technify.business.ReturnValue.*;

import technify.data.DBConnector;
import technify.data.PostgreSQLErrorCodes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {

    public static void createTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt_users = null;
        PreparedStatement pstmt_songs = null;
        PreparedStatement pstmt_playlists = null;

        try {
            pstmt_users = connection.prepareStatement("CREATE TABLE Users\n" +
                "(\n" +
                "    user_id integer,\n" +
                "    user_name text ,\n" +
                "    country text ,\n" +
                "    premium boolean ,\n" +
                "    PRIMARY KEY (user_id),\n" +
                "    CHECK (user_id > 0),\n" +
                "    CHECK (user_name != null),\n" +
                "    CHECK (country != null),\n" +
                "    CHECK (premium != null)\n" +
                ")");
            pstmt_users.execute();

            pstmt_songs = connection.prepareStatement("CREATE TABLE Songs\n" +
                    "(\n" +
                    "    song_id integer,\n" +
                    "    song_name text ,\n" +
                    "    genre text ,\n" +
                    "    country text ,\n" +
                    "    play_count integer ,\n" +
                    "    PRIMARY KEY (song_id),\n" +
                    "    CHECK (song_id > 0),\n" +
                    "    CHECK (song_name != null),\n" +
                    "    CHECK (genre != null),\n" +
                    "    CHECK (play_count >= 0)\n" +
                    ")");
            pstmt_songs.execute();

            pstmt_playlists = connection.prepareStatement("CREATE TABLE Playlists\n" +
                    "(\n" +
                    "    playlist_id integer,\n" +
                    "    genre text ,\n" +
                    "    description text ,\n" +
                    "    PRIMARY KEY (playlist_id),\n" +
                    "    CHECK (playlist_id > 0),\n" +
                    "    CHECK (genre != null),\n" +
                    "    CHECK (description != null)\n" +
                    ")");
            pstmt_playlists.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt_users.close();
                pstmt_songs.close();
                pstmt_playlists.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void clearTables()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt_users = null;
        PreparedStatement pstmt_songs = null;
        PreparedStatement pstmt_playlists = null;
        try {
            pstmt_users = connection.prepareStatement(
                    "DELETE FROM Users ");
            pstmt_users.execute();

            pstmt_songs = connection.prepareStatement(
                    "DELETE FROM Songs ");
            pstmt_songs.execute();

            pstmt_playlists = connection.prepareStatement(
                    "DELETE FROM Playlists ");
            pstmt_playlists.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt_users.close();
                pstmt_songs.close();
                pstmt_playlists.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void dropTables()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt_users = null;
        PreparedStatement pstmt_songs = null;
        PreparedStatement pstmt_playlists = null;

        try {
            pstmt_users = connection.prepareStatement("DROP TABLE IF EXISTS Users");
            pstmt_users.execute();
            pstmt_songs = connection.prepareStatement("DROP TABLE IF EXISTS Songs");
            pstmt_songs.execute();
            pstmt_playlists = connection.prepareStatement("DROP TABLE IF EXISTS Playlists");
            pstmt_playlists.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt_users.close();
                pstmt_songs.close();
                pstmt_playlists.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static ReturnValue addUser(User user)
    {
        if(user.getId() <= 0 || user.getName() == null || user.getCountry() == null /*|| user.getPremium() == null*/) {
            return BAD_PARAMS;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt_users = null;
        ReturnValue return_value = OK;

        try {
            pstmt_users = connection.prepareStatement("SELECT * FROM Users WHERE user_id = " +
                    user.getId());
            ResultSet results = pstmt_users.executeQuery();

            // check if user already exists
            if(results.next() == true) {
                return_value = ALREADY_EXISTS;

            // else - add the user
            } else {
                pstmt_users = connection.prepareStatement("INSERT INTO Users" +
                        " VALUES (?, ?, ?, ?)");
                        pstmt_users.setInt(1,user.getId());
                        pstmt_users.setString(2, user.getName());
                        pstmt_users.setString(3,user.getCountry());
                        pstmt_users.setBoolean(4,user.getPremium());
                        pstmt_users.execute();
            }
            results.close();
        } catch (SQLException e) {
            return_value = ERROR;   // TODO: remove or add in other functions?
        }
        finally {
            try {
                pstmt_users.close();
            } catch (SQLException e) {
                return_value = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return_value = ERROR;
            }
        }
        return return_value;

    }

    public static User getUserProfile(Integer userId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        User return_user = new User();

        try {
            pstmt = connection.prepareStatement("SELECT * FROM Users WHERE user_id = " + userId);
            ResultSet results = pstmt.executeQuery();
            if(results.next() == true) {
                return_user.setId(results.getInt("user_id"));
                return_user.setName(results.getString("user_name"));
                return_user.setCountry(results.getString("country"));
                return_user.setPremium(results.getBoolean("premium"));
            }
            results.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return return_user;
    }

    public static ReturnValue deleteUser(User user)
    {
        return null;
    }

    public static ReturnValue updateUserPremium(Integer userId)
    {
        User user = getUserProfile(userId);
        if(user.equals(User.badUser())) {
            return NOT_EXISTS;
        }
        if(user.getPremium() == true) {
            return ALREADY_EXISTS;
        }

        // update premium
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "UPDATE Users " +
                            "SET premium = ? " +
                            "where user_id = ?");
            pstmt.setInt(2,userId);
            pstmt.setBoolean(1, true);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return ERROR;   // TODO: check
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return OK;
    }

    public static ReturnValue updateUserNotPremium(Integer userId)
    {
        User user = getUserProfile(userId);
        if(user.equals(User.badUser())) {
            return NOT_EXISTS;
        }
        if(user.getPremium() == false) {
            return ALREADY_EXISTS;
        }

        // update not premium
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "UPDATE Users " +
                            "SET premium = ? " +
                            "where user_id = ?");
            pstmt.setInt(2,userId);
            pstmt.setBoolean(1, false);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return OK;
    }

    public static ReturnValue addSong(Song song)
    {
        if(song.getId() <= 0 || song.getName() == null || song.getGenre() == null) {
            return BAD_PARAMS;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt_songs = null;
        ReturnValue return_value = OK;

        try {
            pstmt_songs = connection.prepareStatement("SELECT * FROM Songs WHERE song_id = " +
                    song.getId());
            ResultSet results = pstmt_songs.executeQuery();

            // check if song already exists
            if(results.next() == true) {
                return_value = ALREADY_EXISTS;

                // else - add the song
            } else {
                pstmt_songs = connection.prepareStatement("INSERT INTO Songs" +
                        " VALUES (?, ?, ?, ?, ?)");
                pstmt_songs.setInt(1,song.getId());
                pstmt_songs.setString(2, song.getName());
                pstmt_songs.setString(3, song.getGenre());
                pstmt_songs.setString(4, song.getCountry());
                pstmt_songs.setInt(5, 0);
                pstmt_songs.execute();
            }
            results.close();
        } catch (SQLException e) {
            return_value = ERROR;   // TODO: remove or add in other functions?
        }
        finally {
            try {
                pstmt_songs.close();
            } catch (SQLException e) {
                return_value = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return_value = ERROR;
            }
        }
        return return_value;
    }

    public static Song getSong(Integer songId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        Song return_song = new Song();

        try {
            pstmt = connection.prepareStatement("SELECT * FROM Songs WHERE song_id = " + songId);
            ResultSet results = pstmt.executeQuery();
            if(results.next() == true) {
                return_song.setId(results.getInt("song_id"));
                return_song.setName(results.getString("song_name"));
                return_song.setGenre(results.getString("genre"));
                return_song.setCountry(results.getString("country"));
                return_song.setPlayCount(results.getInt("play_count"));
            }
            results.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return return_song;
    }

    public static ReturnValue deleteSong(Song song)
    {
        return null;
    }

    public static ReturnValue updateSongName(Song song)
    {
        return null;
    }

    public static ReturnValue addPlaylist(Playlist playlist)
    {
        return null;
    }

    public static Playlist getPlaylist(Integer playlistId)
    {
        return null;
    }

    public static ReturnValue deletePlaylist(Playlist playlist)
    {
        return null;
    }

    public static ReturnValue updatePlaylist(Playlist playlist)
    {
        return null;
    }

    public static ReturnValue addSongToPlaylist(Integer songid, Integer playlistId){
        return null;
    }

    public static ReturnValue removeSongFromPlaylist(Integer songid, Integer playlistId){
        return null;
    }

    public static ReturnValue followPlaylist(Integer userId, Integer playlistId){
        return null;
    }

    public static ReturnValue stopFollowPlaylist(Integer userId, Integer playlistId){
        return null;
    }

    public static ReturnValue songPlay(Integer songId, Integer times){
        return null;
    }

    public static Integer getPlaylistTotalPlayCount(Integer playlistId){
        return null;
    }

    public static Integer getPlaylistFollowersCount(Integer playlistId){
        return null;
    }

    public static String getMostPopularSong(){
        return null;
    }

    public static Integer getMostPopularPlaylist(){
        return null;
    }

    public static ArrayList<Integer> hottestPlaylistsOnTechnify(){
        return null;
    }

    public static ArrayList<Integer> getSimilarUsers(Integer userId){
        return null;
    }

    public static ArrayList<Integer> getTopCountryPlaylists(Integer userId) {
        return null;
    }

    public static ArrayList<Integer> getPlaylistRecommendation (Integer userId){
        return null;
    }

    public static ArrayList<Integer> getSongsRecommendationByGenre(Integer userId, String genre){
        return null;
    }


}

