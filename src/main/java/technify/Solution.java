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
                    "    play_count integer default 0,\n" +
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
                    "    FOREIGN KEY (playlist_id) REFERENCES Playlists (playlist_id),\n" +
                    "    FOREIGN KEY (user_id) REFERENCES Users (user_id)\n" +
                    ")");
            pstmt_follows.execute();

            pstmt_consistOf = connection.prepareStatement("CREATE TABLE ConsistOf\n" +
                    "(\n" +
                    "    playlist_id integer,\n" +
                    "    song_id integer ,\n" +
                    "    PRIMARY KEY (playlist_id, song_id),\n" +
                    "    FOREIGN KEY (song_id) REFERENCES Songs (song_id),\n" +
                    "    FOREIGN KEY (playlist_id) REFERENCES Playlists (playlist_id)\n" +
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
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue return_value = OK;

        try {
            pstmt = connection.prepareStatement("INSERT INTO Users" +
                    " VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getCountry());
            pstmt.setBoolean(4, user.getPremium());
            pstmt.execute();
        } catch (SQLException e) {
            return_value = getErrorValue(e);
        }
        finally {
            try {
                pstmt.close();
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
        User return_user = User.badUser();

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
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ReturnValue return_value = OK;

        try {
            pstmt1 = connection.prepareStatement(
                    "DELETE FROM Follows " +
                            "where user_id = ?");
            pstmt1.setInt(1,user.getId());
            pstmt1.executeUpdate();
            pstmt2 = connection.prepareStatement(
                    "DELETE FROM Users " +
                            "where user_id = ?");
            pstmt2.setInt(1, user.getId());
            int affectedRows = pstmt2.executeUpdate();
            if(affectedRows == 0) {
                return_value = NOT_EXISTS;
            }
        } catch (SQLException e) {
            return_value = ERROR;
        }
        finally {
            try {
                pstmt1.close();
                pstmt2.close();
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

    public static ReturnValue updateUserPremium(Integer userId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue return_value = OK;
        User user = getUserProfile(userId);

        if(!user.equals(User.badUser()) && user.getPremium() == true) {
            return_value = ALREADY_EXISTS;
        } else {
            try {
                pstmt = connection.prepareStatement(
                        "UPDATE Users " +
                                "SET premium = ? " +
                                "where user_id = ?");
                pstmt.setInt(2, userId);
                pstmt.setBoolean(1, true);
                int affectedRows = pstmt.executeUpdate();
                if(affectedRows == 0) {
                    return_value = NOT_EXISTS;
                }
            } catch (SQLException e) {
                return_value = ERROR;
            }
            finally {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    return_value = ERROR;

                }
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return return_value;
    }

    public static ReturnValue updateUserNotPremium(Integer userId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue return_value = OK;
        User user = getUserProfile(userId);

        if(!user.equals(User.badUser()) && user.getPremium() == false) {
            return_value = ALREADY_EXISTS;
        } else {
            try {
                pstmt = connection.prepareStatement(
                        "UPDATE Users " +
                                "SET premium = ? " +
                                "where user_id = ?");
                pstmt.setInt(2,userId);
                pstmt.setBoolean(1, false);
                int affectedRows = pstmt.executeUpdate();
                if(affectedRows == 0) {
                    return_value = NOT_EXISTS;
                }
            } catch (SQLException e) {
                return_value = ERROR;
            }
            finally {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    return_value = ERROR;
                }
                try {
                    connection.close();
                } catch (SQLException e) {
                    return_value = ERROR;
                }
            }
        }
        return return_value;
    }

    public static ReturnValue addSong(Song song)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt_songs = null;
        ReturnValue return_value = OK;

        try {
            pstmt_songs = connection.prepareStatement("INSERT INTO Songs" +
                    " VALUES (?, ?, ?, ?, ?)");
            pstmt_songs.setInt(1,song.getId());
            pstmt_songs.setString(2, song.getName());
            pstmt_songs.setString(3, song.getGenre());
            pstmt_songs.setString(4, song.getCountry());
            pstmt_songs.setInt(5, 0);
            pstmt_songs.execute();
        } catch (SQLException e) {
            return_value = getErrorValue(e);
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
        Song return_song = Song.badSong();

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
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ReturnValue return_value = OK;

        try {
            pstmt1 = connection.prepareStatement(
                    "DELETE FROM ConsistOf " +
                            "where song_id = ?");
            pstmt1.setInt(1, song.getId());
            pstmt1.executeUpdate();
            pstmt2 = connection.prepareStatement(
                    "DELETE FROM Songs " +
                            "where song_id = ?");
            pstmt2.setInt(1, song.getId());
            int affectedRows = pstmt2.executeUpdate();
            if(affectedRows == 0) {
                return_value = NOT_EXISTS;
            }
        } catch (SQLException e) {
            return_value = ERROR;
            e.printStackTrace();
        }
        finally {
            try {
                pstmt1.close();
                pstmt2.close();
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

    public static ReturnValue updateSongName(Song song)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue return_value = OK;

        try {
            pstmt = connection.prepareStatement(
                    "UPDATE Songs " +
                            "SET song_name = ? " +
                            "where song_id = ?");
            pstmt.setString(1, song.getName());
            pstmt.setInt(2, song.getId());
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0) {
                return_value = NOT_EXISTS;
            }
        } catch (SQLException e) {
            return_value = getErrorValue(e);
        }
        finally {
            try {
                pstmt.close();
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
    	   Connection connection = DBConnector.getConnection();
           PreparedStatement pstmt = null;
           PreparedStatement pstmt_consistOf = null;
           PreparedStatement pstmt_follows = null;
           
           ReturnValue return_value = OK;
           try {
        	   pstmt_consistOf = connection.prepareStatement(
                       "DELETE FROM ConsistOf " +
                               "where playlist_id = ?");
        	   pstmt_consistOf.setInt(1, playlist.getId());
        	   pstmt_consistOf.executeUpdate();
        	   pstmt_follows =  connection.prepareStatement(
                       "DELETE FROM follows " +
                       "where playlist_id = ?");
        	   pstmt_follows.setInt(1, playlist.getId());
        	   pstmt_follows.executeUpdate();
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
                   pstmt_consistOf.close();
                   pstmt_follows.close();
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
         PreparedStatement pstmt_playlist = null;
         PreparedStatement pstmt_song = null;
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
            	 //need to check why it's false 
            	 /*
            	 pstmt_playlist = connection.prepareStatement("SELECT * FROM Playlists WHERE "
            	 		+ "playlist_id = "+ playlistId);
            	 pstmt_song = connection.prepareStatement("SELECT * FROM Songs WHERE "
             	 		+ "song_id = "+ songid);
            	 ResultSet results_playlist = pstmt_playlist.executeQuery();
            	 ResultSet results_song = pstmt_song.executeQuery();
            	 if(results_playlist.next() != true || results_song.next() != true)
            		 return_value = NOT_EXISTS;
            	 else
            		 return_value = BAD_PARAMS;
            	 * */
            	 return_value = BAD_PARAMS;
             }
             results.close();
         } catch (SQLException e) {
             // shouldn't get here....
             return_value = getErrorValue(e);
         }
         finally {
             try {
                 pstmt.close();
                 if(pstmt_playlist != null) pstmt_playlist.close();
                 if(pstmt_song != null) pstmt_song.close();
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
    	  Connection connection = DBConnector.getConnection();
          PreparedStatement pstmt = null;
          ReturnValue return_value = OK;
          try {
              pstmt = connection.prepareStatement(
                      "DELETE FROM consistOf " +
                              "where playlist_id = ?"
                              + "AND song_id = ?");
              pstmt.setInt(1,playlistId);
              pstmt.setInt(2,songid);
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

    public static ReturnValue followPlaylist(Integer userId, Integer playlistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue return_value = OK;

        if(getUserProfile(userId).equals(User.badUser()) || getPlaylist(playlistId).equals(Playlist.badPlaylist())) {
            return_value = NOT_EXISTS;
        } else {
            try {
                pstmt = connection.prepareStatement("INSERT INTO Follows" + " VALUES (?, ?)");
                pstmt.setInt(1, userId);
                pstmt.setInt(2, playlistId);
                pstmt.execute();

            } catch (SQLException e) {
                if(Integer.valueOf(e.getSQLState()) == PostgreSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
                    return_value = ALREADY_EXISTS;
                } else {
                    return_value = ERROR;
                }
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
        }
        return return_value;
    }

    public static ReturnValue stopFollowPlaylist(Integer userId, Integer playlistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue return_value = OK;

        if(getUserProfile(userId).equals(User.badUser()) || getPlaylist(playlistId).equals(Playlist.badPlaylist())) {
            return_value = NOT_EXISTS;
        } else {
            try {
                pstmt = connection.prepareStatement(
                        "DELETE FROM Follows " +
                                "where user_id = ? AND playlist_id = ?");
                pstmt.setInt(1, userId);
                pstmt.setInt(2, playlistId);
                int affectedRows = pstmt.executeUpdate();
                if(affectedRows == 0) {
                    return_value = NOT_EXISTS;
                }
            } catch (SQLException e) {
                return_value = ERROR;
            }
            finally {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return return_value;
    }

    public static ReturnValue songPlay(Integer songId, Integer times){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue return_value = OK;

        try {
            pstmt = connection.prepareStatement(
                    "UPDATE Songs SET play_count = ? + (SELECT play_count FROM Songs WHERE song_id = ?)" +
                            "WHERE song_id = ?");
            pstmt.setInt(1, times);
            pstmt.setInt(2, songId);
            pstmt.setInt(3, songId);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0) {
                return_value = NOT_EXISTS;
            }
        } catch (SQLException e) {
            if(Integer.valueOf(e.getSQLState()) == PostgreSQLErrorCodes.CHECK_VIOLATION.getValue())
            {
                return_value = BAD_PARAMS;
            } else {
                return_value = ERROR;
            }
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return_value = ERROR;
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return_value = ERROR;
                e.printStackTrace();
            }
        }
        return return_value;
    }

    public static Integer getPlaylistTotalPlayCount(Integer playlistId)
    {
    	//TODO: NIV
    	String queryForPlaylist = "SELECT SUM(play_count) FROM Songs WHERE song_id IN "
    			+ "(SELECT song_id FROM consistOf WHERE playlist_id = "+playlistId+")";
    	
   	 	Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        Integer return_value = 0;
        try {
            pstmt = connection.prepareStatement(queryForPlaylist);
            ResultSet results = pstmt.executeQuery();
            if(results.next() == true) 
            {
            	return_value = results.getInt("sum");
            }
        } catch (SQLException e) {
            //e.printStackTrace()();
       	 	return_value = 0;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
           	 return_value = 0;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
           	 return_value = 0;
            }
        }
       return return_value;
    }

    public static Integer getPlaylistFollowersCount(Integer playlistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        Integer count = 0;

        try {
            pstmt = connection.prepareStatement("SELECT COUNT(user_id) FROM Follows WHERE playlist_id = " + playlistId);
            ResultSet results = pstmt.executeQuery();
            if(results.next() == true) {
                count = results.getInt(1);
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
        return count;
    }

    public static String getMostPopularSong()
    {
    	//TODO: NIV
    	String playlistsAreEmpty = "No songs";
    	String return_value = playlistsAreEmpty;
    	String queryForPlaylist = "SELECT song_id, COUNT(song_id) FROM consistOf GROUP BY song_id"
    			+ " ORDER BY COUNT(song_id) DESC, song_id DESC";
    	
   	 	Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        
        int biggest_song_id = -1;
        
        try {
            pstmt = connection.prepareStatement(queryForPlaylist);
            ResultSet results = pstmt.executeQuery();
            if(results.next() == true)
            {
            	biggest_song_id = results.getInt("song_id");
            	return_value = (getSong(biggest_song_id)).getName();
            }
            else
            {
            	return_value = playlistsAreEmpty;
            }
        } catch (SQLException e) {
            //e.printStackTrace()();
       	 	return_value = null;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
           	 return_value = null;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
           	 return_value = null;
            }
        }
        
        return return_value;
    }

    public static Integer getMostPopularPlaylist(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        Integer popular_playlist_id = 0;

        try {
            pstmt = connection.prepareStatement("SELECT playlist_id, coalesce(sum(play_count), 0) AS co FROM " +
                    "(SELECT playlists.playlist_id, song_id " +
                    "FROM playlists " +
                    "LEFT JOIN consistOf ON consistOf.playlist_id = playlists.playlist_id) AS foo " +
                    "LEFT JOIN songs ON foo.song_id = songs.song_id " +
                    "GROUP BY playlist_id " +
                    "ORDER BY co DESC, playlist_id DESC");
                    ResultSet results = pstmt.executeQuery();
            if(results.next() == true) {
                popular_playlist_id = results.getInt("playlist_id");
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
        return popular_playlist_id;
    }

    public static ArrayList<Integer> hottestPlaylistsOnTechnify()
    {
    	//TODO: NIV			
    	String queryForPlaylist = "SELECT playlist_id, AVG(play_count) FROM Songs RIGHT OUTER JOIN consistOf ON"
    			+ "(Songs.song_id = consistOf.song_id) GROUP BY playlist_id"
    			+ " ORDER BY AVG(play_count) DESC, playlist_id ASC limit 10";
    					
    	ArrayList<Integer> playlistIds = new ArrayList<Integer>();
    	
   	 	Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        
        try {
            pstmt = connection.prepareStatement(queryForPlaylist);
            ResultSet results = pstmt.executeQuery();
            while(results.next() == true) 
            {
            	playlistIds.add(results.getInt("playlist_id"));
            }
        } catch (SQLException e) {
            //e.printStackTrace()();
       	 	
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

        return playlistIds;
    }

    public static ArrayList<Integer> getSimilarUsers(Integer userId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt_threshold = null;
        PreparedStatement pstmt = null;
        ArrayList<Integer> similar_users = new ArrayList<>();
        double threshold = 0;

        try {
            pstmt_threshold = connection.prepareStatement("SELECT COUNT(playlist_id) FROM Follows WHERE user_id = " + userId);
            ResultSet results_threshold = pstmt_threshold.executeQuery();
            if(results_threshold.next() == true) {
                threshold = results_threshold.getInt(1)*0.75;
            }
            results_threshold.close();
            pstmt = connection.prepareStatement("SELECT user_id FROM " +
                    "(SELECT user_id, COUNT(user_id) " +
                    "FROM (SELECT playlist_id FROM Follows WHERE user_id = " + userId + ") AS chosen " +
                    "LEFT JOIN Follows ON (chosen.playlist_id = Follows.playlist_id) " +
                    "WHERE NOT user_id = " +userId + " " +
                    "GROUP BY user_id) AS above_thre " +
                    "WHERE count >=" + threshold + " " +
                    "ORDER BY user_id ASC " +
                    "LIMIT 10");
            ResultSet results = pstmt.executeQuery();
            while(results.next() == true)
            {
                similar_users.add(results.getInt("user_id"));
            }
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt_threshold.close();
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
        return similar_users;
    }

    public static ArrayList<Integer> getTopCountryPlaylists(Integer userId) {

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ArrayList<Integer> topCountryPlaylists = new ArrayList<>();

        if(getUserProfile(userId).getPremium() == true) {   //TODO: change this to SQL
            try {
                String country = getUserProfile(userId).getCountry();   //TODO: SQL
                pstmt = connection.prepareStatement("SELECT playlist_id FROM " +
                        "(SELECT playlistsSongs.playlist_id, play_count FROM " +
                        "(SELECT ConsistOf.playlist_id, song_id FROM " +
                        "(SELECT DISTINCT ConsistOf.playlist_id " +
                        "FROM ConsistOf " +
                        "INNER JOIN Songs ON ConsistOf.song_id=Songs.song_id " +
                        "WHERE country='" + country + "') AS containCountry " +
                        "LEFT JOIN ConsistOf ON containCountry.playlist_id = ConsistOf.playlist_id) AS playlistsSongs " +
                        "LEFT JOIN Songs ON playlistsSongs.song_id = Songs.song_id) AS sums " +
                        "GROUP BY playlist_id " +
                        "ORDER BY SUM(play_count) DESC, playlist_id ASC " +
                        "LIMIT 10");
                ResultSet results = pstmt.executeQuery();
                while(results.next() == true)
                {
                    topCountryPlaylists.add(results.getInt("playlist_id"));
                }
                results.close();

            } catch (SQLException e) {
                //e.printStackTrace()();
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
        }

        return topCountryPlaylists;
    }

    public static ArrayList<Integer> getPlaylistRecommendation (Integer userId)
    {
        //TODO: NIV
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ArrayList<Integer> playlistsIds = new ArrayList<>();

        String getThreshold = "(SELECT COUNT(playlist_id) FROM Follows WHERE"
                + " user_id = " + userId+")*0.75";
        String getSimilarUsersId_noLimit = "SELECT user_id FROM " +
                "(SELECT user_id, COUNT(user_id) " +
                "FROM (SELECT playlist_id FROM Follows WHERE user_id = " + userId + ") AS chosen " +
                "LEFT JOIN Follows ON (chosen.playlist_id = Follows.playlist_id) " +
                "WHERE NOT user_id = " +userId + " " +
                "GROUP BY user_id) AS above_thre " +
                "WHERE count >=" + getThreshold + " " +
                "ORDER BY user_id ASC";

        String playlistIdsOfUser = "SELECT playlist_id FROM follows WHERE user_id = "+userId;

        String stringQuery = "SELECT playlist_id,COUNT(playlist_id) FROM follows"
                + " WHERE user_id IN ("+getSimilarUsersId_noLimit+") AND playlist_id NOT IN ("+playlistIdsOfUser+")"
                + "GROUP BY playlist_id ORDER BY COUNT(playlist_id) DESC, playlist_id ASC LIMIT 5";


        try {
            pstmt = connection.prepareStatement(stringQuery);
            ResultSet results = pstmt.executeQuery();
            while(results.next() == true)
            {
                playlistsIds.add(results.getInt("playlist_id"));
            }
            results.close();
        } catch (SQLException e) {
            //e.printStackTrace()();
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
        return playlistsIds;
    }

    public static ArrayList<Integer> getSongsRecommendationByGenre(Integer userId, String genre)
    {
    	//TODO: NIV
    	Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        
        String chooseAllPlaylistIdOfUserId = "SELECT consistOf.playlist_id FROM consistOf INNER JOIN follows ON "
        		+ "(consistOf.playlist_id = follows.playlist_id) WHERE user_id = " + userId;
        
       String songsThatNotInPlaylistIdByGenre = "SELECT Songs.song_id FROM Songs LEFT OUTER JOIN consistOf ON " 
        		    	+ "(Songs.song_id = consistOf.song_id) WHERE (playlist_id NOT IN ("
        		    	+chooseAllPlaylistIdOfUserId+") OR playlist_id is NULL) AND (genre = '"+genre+"')";
        
       String SongsRecommendationByGenre =	songsThatNotInPlaylistIdByGenre+ " ORDER BY play_count DESC,"
       		+ " song_id ASC limit 10";
        
       ArrayList<Integer> songsIds = new ArrayList<Integer>();
        try {
            pstmt = connection.prepareStatement(SongsRecommendationByGenre);
            ResultSet results = pstmt.executeQuery();
            while(results.next() == true) 
            {
            	songsIds.add(results.getInt("song_id"));
            }
            results.close();
        } catch (SQLException e) {
        	
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
        return songsIds;
    }

    //========================= Helper Functions =======================================

    private static ReturnValue getErrorValue(SQLException e)
    {
    	if(Integer.valueOf(e.getSQLState()) ==
    			PostgreSQLErrorCodes.CHECK_VIOLATION.getValue() ||
    			Integer.valueOf(e.getSQLState()) == PostgreSQLErrorCodes.NOT_NULL_VIOLATION.getValue())
		{
    		return BAD_PARAMS;
		}
    	if(Integer.valueOf(e.getSQLState()) == PostgreSQLErrorCodes.UNIQUE_VIOLATION.getValue())
		{
    		return ALREADY_EXISTS;
		}
    	if(Integer.valueOf(e.getSQLState()) == PostgreSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue())
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

