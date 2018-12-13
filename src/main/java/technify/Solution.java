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
        PreparedStatement pstmt_follows = null;
        PreparedStatement pstmt_consistOf = null;

        try {
            pstmt_users = connection.prepareStatement("CREATE TABLE Users\n" +
                "(\n" +
                "    user_id integer,\n" +
                "    user_name text NOT NULL,\n" +
                "    country text NOT NULL,\n" +
                "    premium boolean NOT NULL,\n" +
                "    PRIMARY KEY (user_id),\n" +
                "    CHECK (user_id > 0)\n" +
                ")");
            pstmt_users.execute();

            pstmt_songs = connection.prepareStatement("CREATE TABLE Songs\n" +
                    "(\n" +
                    "    song_id integer,\n" +
                    "    song_name text NOT NULL,\n" +
                    "    genre text NOT NULL,\n" +
                    "    country text ,\n" +
                    "    play_count integer ,\n" +
                    "    PRIMARY KEY (song_id),\n" +
                    "    CHECK (song_id > 0),\n" +
                    "    CHECK (play_count >= 0)\n" +
                    ")");
            pstmt_songs.execute();

            pstmt_playlists = connection.prepareStatement("CREATE TABLE Playlists\n" +
                    "(\n" +
                    "    playlist_id integer,\n" +
                    "    genre text NOT NULL,\n" +
                    "    description text NOT NULL,\n" +
                    "    PRIMARY KEY (playlist_id),\n" +
                    "    CHECK (playlist_id > 0)\n" +
                    ")");
            pstmt_playlists.execute();

            pstmt_follows = connection.prepareStatement("CREATE TABLE Follows\n" +
                    "(\n" +
                    "    user_id integer,\n" +
                    "    playlist_id integer ,\n" +
                    "    PRIMARY KEY (user_id, playlist_id),\n" +
                    "    FOREIGN KEY (playlist_id) REFERENCES Playlists (playlist_id)\n" +
                    ")");
            pstmt_follows.execute();

            pstmt_consistOf = connection.prepareStatement("CREATE TABLE ConsistOf\n" +
                    "(\n" +
                    "    playlist_id integer,\n" +
                    "    song_id integer ,\n" +
                    "    PRIMARY KEY (playlist_id, song_id),\n" +
                    "    FOREIGN KEY (song_id) REFERENCES Songs (song_id)\n" +
                    ")");
            pstmt_consistOf.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt_consistOf.close();
                pstmt_follows.close();
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
        PreparedStatement pstmt_follows = null;
        PreparedStatement pstmt_consistOf = null;

        try {

            pstmt_consistOf = connection.prepareStatement(
                    "DELETE FROM ConsistOf ");
            pstmt_consistOf.execute();

            pstmt_songs = connection.prepareStatement(
                    "DELETE FROM Songs ");
            pstmt_songs.execute();

            pstmt_follows = connection.prepareStatement(
                    "DELETE FROM Follows ");
            pstmt_follows.execute();

            pstmt_playlists = connection.prepareStatement(
                    "DELETE FROM Playlists ");
            pstmt_playlists.execute();

            pstmt_users = connection.prepareStatement(
                    "DELETE FROM Users ");
            pstmt_users.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt_follows.close();
                pstmt_consistOf.close();
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
        PreparedStatement pstmt_follows = null;
        PreparedStatement pstmt_consistOf = null;

        try {
            pstmt_consistOf = connection.prepareStatement("DROP TABLE IF EXISTS ConsistOf");
            pstmt_consistOf.execute();
            pstmt_follows = connection.prepareStatement("DROP TABLE IF EXISTS Follows");
            pstmt_follows.execute();
            pstmt_playlists = connection.prepareStatement("DROP TABLE IF EXISTS Playlists");
            pstmt_playlists.execute();
            pstmt_users = connection.prepareStatement("DROP TABLE IF EXISTS Users");
            pstmt_users.execute();
            pstmt_songs = connection.prepareStatement("DROP TABLE IF EXISTS Songs");
            pstmt_songs.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt_follows.close();
                pstmt_consistOf.close();
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
    	//TODO: NIV
    	Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt_playlists = null;
        ReturnValue return_value = OK;
        
        try {
            	pstmt_playlists = connection.prepareStatement("INSERT INTO Playlists" +
                        " VALUES (?, ?, ?)");
            	pstmt_playlists.setInt(1,playlist.getId());
            	pstmt_playlists.setString(2, playlist.getGenre());
            	pstmt_playlists.setString(3,playlist.getDescription());
            	pstmt_playlists.execute();
            	return_value = OK;
        } 
        catch (SQLException e) 
        {
            return_value = getErrorValue(e);
        }
        finally {
            try {
            	pstmt_playlists.close();
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

    public static Playlist getPlaylist(Integer playlistId)
    {
    	//TODO: NIV
    	 Connection connection = DBConnector.getConnection();
         PreparedStatement pstmt = null;
         Playlist return_playlist = new Playlist();

         try {
             pstmt = connection.prepareStatement("SELECT * FROM Playlists WHERE playlist_id = " + playlistId);
             ResultSet results = pstmt.executeQuery();
             if(results.next() == true) {
            	 return_playlist.setId(results.getInt("playlist_id"));
            	 return_playlist.setGenre(results.getString("genre"));
            	 return_playlist.setDescription(results.getString("description"));
             }
             results.close();
         } catch (SQLException e) {
             //e.printStackTrace();
             return_playlist = return_playlist.badPlaylist(); 
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
         return return_playlist;
    }

    public static ReturnValue deletePlaylist(Playlist playlist)
    {
			//TODO: NIV
			if(playlist == null)
			{
				return BAD_PARAMS;
			}
    	   Connection connection = DBConnector.getConnection();
           PreparedStatement pstmt = null;
           ReturnValue return_value = OK;
           try {
               pstmt = connection.prepareStatement(
                       "DELETE FROM Playlists " +
                               "where playlist_id = ?");
               pstmt.setInt(1,playlist.getId());
               int affectedRows = pstmt.executeUpdate();
               if(affectedRows > 0)
            	   return_value = OK;
               else
            	   return_value = NOT_EXISTS;
           } catch (SQLException e) {
               //e.printStackTrace()();
        	   return_value = getErrorValue(e);
           }
           finally {
               try {
                   pstmt.close();
               } catch (SQLException e) {
                   //e.printStackTrace()();
               }
               try {
                   connection.close();
               } catch (SQLException e) {
                   //e.printStackTrace()();
               }
           }
        return return_value;
    }

    public static ReturnValue updatePlaylist(Playlist playlist)
    {
    	//TODO: NIV
    	//check if playlist is null?
    	 Connection connection = DBConnector.getConnection();
         PreparedStatement pstmt = null;
         ReturnValue return_value = OK;
         try {
             pstmt = connection.prepareStatement(
                     "UPDATE Playlists " +
                             "SET description = ? " +
                             "where playlist_id = ?");
             pstmt.setInt(2,playlist.getId());
             pstmt.setString(1, playlist.getDescription());
             int affectedRows = pstmt.executeUpdate();
             if(affectedRows > 0)
          	   return_value = OK;
             else
          	   return_value = NOT_EXISTS;
         } catch (SQLException e) {
             //e.printStackTrace()();
        	 return_value = getErrorValue(e);
         }
         finally {
             try {
                 pstmt.close();
             } catch (SQLException e) {
                 //e.printStackTrace()();
            	 return_value = ERROR;
             }
             try {
                 connection.close();
             } catch (SQLException e) {
                 //e.printStackTrace()();
            	 return_value = ERROR;
             }
         }
        return return_value;
    }

    public static ReturnValue addSongToPlaylist(Integer songid, Integer playlistId)
    {
    	//TODO: NIV
    	 Connection connection = DBConnector.getConnection();
         PreparedStatement pstmt = null;
         ReturnValue return_value = OK;
         
         String queryForPlaylist = "SELECT * FROM Playlists WHERE playlist_id = "+ playlistId
        		 + " AND genre IN (SELECT genre FROM Songs WHERE song_id = "+songid + ")";
         
         try {
             pstmt = connection.prepareStatement(queryForPlaylist);
             ResultSet results = pstmt.executeQuery();
             if(results.next() == true) 
             {
            	 return_value = addToConsistOf(playlistId,songid);
             }
             else
             {
            	 return_value = NOT_EXISTS;
             }
             results.close();
         } catch (SQLException e) {
             // shouldn't get here....
             return_value = getErrorValue(e);
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
         
        return return_value;
    }

    public static ReturnValue removeSongFromPlaylist(Integer songid, Integer playlistId)
    {
    	//TODO: NIV
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

    public static Integer getPlaylistTotalPlayCount(Integer playlistId)
    {
    	//TODO: NIV
        return null;
    }

    public static Integer getPlaylistFollowersCount(Integer playlistId){
        return null;
    }

    public static String getMostPopularSong()
    {
    	//TODO: NIV
        return null;
    }

    public static Integer getMostPopularPlaylist(){
        return null;
    }

    public static ArrayList<Integer> hottestPlaylistsOnTechnify()
    {
    	//TODO: NIV
        return null;
    }

    public static ArrayList<Integer> getSimilarUsers(Integer userId){
        return null;
    }

    public static ArrayList<Integer> getTopCountryPlaylists(Integer userId) {
        return null;
    }

    public static ArrayList<Integer> getPlaylistRecommendation (Integer userId)
    {
    	//TODO: NIV
        return null;
    }

    public static ArrayList<Integer> getSongsRecommendationByGenre(Integer userId, String genre)
    {
    	//TODO: NIV
        return null;
    }

    //========================= Helper Functions =======================================
    private static ReturnValue getErrorValue(SQLException e)
    {
    	if(Integer.valueOf(e.getSQLState()) ==
    			PostgreSQLErrorCodes.CHECK_VIOLATION.getValue() ||
    			Integer.valueOf(e.getSQLState()) ==
    			PostgreSQLErrorCodes.NOT_NULL_VIOLATION.getValue())
		{
    		return BAD_PARAMS;
		}
    	if(Integer.valueOf(e.getSQLState()) ==
    			PostgreSQLErrorCodes.UNIQUE_VIOLATION.getValue())
		{
    		return ALREADY_EXISTS;
		}
    	if(Integer.valueOf(e.getSQLState()) ==
    			PostgreSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue())
		{
    		return BAD_PARAMS; //????
		}
    	return ERROR;
    }
    
    private static ReturnValue addToConsistOf(Integer playlist_id, Integer song_id)
    {
    	Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt_co = null;
        ReturnValue return_value = OK;
        
        try {
        	pstmt_co = connection.prepareStatement("INSERT INTO ConsistOf" +
                        " VALUES (?, ?)");
        	pstmt_co.setInt(1,playlist_id);
        	pstmt_co.setInt(2, song_id);
        	pstmt_co.execute();
            return_value = OK;
        } 
        catch (SQLException e) 
        {
            return_value = getErrorValue(e);
        }
        finally {
            try {
            	pstmt_co.close();
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
}

