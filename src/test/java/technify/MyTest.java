package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.ReturnValue;
import technify.business.Song;
import technify.business.User;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;


public class MyTest extends AbstractTest {
    @Test
    public void userCRUD() {

        // ~addUser
        ReturnValue res;
        User badUser = new User();
        res = Solution.addUser(badUser);
        assertEquals(BAD_PARAMS, res);
        badUser.setName("Monica");
        badUser.setCountry("Spain");
        // bad ID
        res = Solution.addUser(badUser);
        assertEquals(BAD_PARAMS, res);
        // bad Name
        badUser.setId(1);
        badUser.setName(null);
        res = Solution.addUser(badUser);
        assertEquals(BAD_PARAMS, res);
        // bad Country
        badUser.setName("Monica");
        badUser.setCountry(null);
        res = Solution.addUser(badUser);
        assertEquals(BAD_PARAMS, res);

        User newUser = new User();
        newUser.setId(1);
        newUser.setName("Monica");
        newUser.setCountry("Spain");
        res = Solution.addUser(newUser);
        assertEquals(OK, res);

        // insert the same user again
        res = Solution.addUser(newUser);
        assertEquals(ALREADY_EXISTS, res);
        // insert the same ID again
        User newUser2 = new User();
        newUser2.setId(1);
        newUser2.setName("Shahaf");
        newUser2.setCountry("Israel");
        res = Solution.addUser(newUser2);
        assertEquals(ALREADY_EXISTS, res);
        newUser2.setId(2);
        res = Solution.addUser(newUser2);
        assertEquals(OK, res);

        // ~getUserProfile
        User resUser;
        // bad ID
        resUser = Solution.getUserProfile(-1);
        assertEquals(true, resUser.equals(User.badUser()));
        // not exist ID
        resUser = Solution.getUserProfile(3);
        assertEquals(true, resUser.equals(User.badUser()));
        // good ID
        resUser = Solution.getUserProfile(1);
        assertEquals(true, resUser.equals(newUser));

        // ~deleteUser
        // not exist
        res = Solution.deleteUser(User.badUser());
        assertEquals(NOT_EXISTS, res);
        User notExistUser = new User();
        notExistUser.setId(3);
        notExistUser.setName("Rachel");
        notExistUser.setCountry("USA");
        res = Solution.deleteUser(notExistUser);
        assertEquals(NOT_EXISTS, res);
        // exist
        res = Solution.deleteUser(newUser2);
        assertEquals(OK, res);
        resUser = Solution.getUserProfile(2);
        assertEquals(true, resUser.equals(User.badUser()));

        // ~updateUser
        // not exist
        res = Solution.updateUserPremium(9);
        assertEquals(NOT_EXISTS, res);
        res = Solution.updateUserNotPremium(9);
        assertEquals(NOT_EXISTS, res);
        // exist
        res = Solution.updateUserPremium(1);
        assertEquals(OK, res);
        res = Solution.updateUserPremium(1);
        assertEquals(ALREADY_EXISTS, res);
        res = Solution.updateUserNotPremium(1);
        assertEquals(OK, res);
        res = Solution.updateUserNotPremium(1);
        assertEquals(ALREADY_EXISTS, res);
    }

    @Test
    public void songCRUD() {

        // ~addSong
        ReturnValue res;
        Song badSong= Song.badSong();
        res = Solution.addSong(badSong);
        assertEquals(BAD_PARAMS, res);
        badSong.setName("Toy");
        badSong.setGenre("Pop");
        badSong.setCountry("Israel");
        // bad ID
        res = Solution.addSong(badSong);
        assertEquals(BAD_PARAMS, res);
        // bad Name
        badSong.setId(1);
        badSong.setName(null);
        res = Solution.addSong(badSong);
        assertEquals(BAD_PARAMS, res);
        // bad Genre
        badSong.setName("Toy");
        badSong.setGenre(null);
        res = Solution.addSong(badSong);
        assertEquals(BAD_PARAMS, res);
        // bad Country
        badSong.setName("Toy");
        badSong.setCountry(null);
        res = Solution.addSong(badSong);
        assertEquals(BAD_PARAMS, res);

        Song newSong = new Song();
        newSong.setId(1);
        newSong.setName("Toy");
        newSong.setGenre("Pop");
        newSong.setCountry("Israel");
        res = Solution.addSong(newSong);
        assertEquals(OK, res);

        // insert the same song again
        res = Solution.addSong(newSong);
        assertEquals(ALREADY_EXISTS, res);
        // insert the same ID again
        Song newSong2 = new Song();
        newSong2.setId(1);
        newSong2.setName("Despacito");
        newSong2.setGenre("Latin");
        newSong2.setCountry("Spain");
        res = Solution.addSong(newSong2);
        assertEquals(ALREADY_EXISTS, res);
        newSong2.setId(2);
        res = Solution.addSong(newSong2);
        assertEquals(OK, res);

        // ~getSong
        Song resSong;
        // bad ID
        resSong = Solution.getSong(-1);
        assertEquals(true, resSong.equals(Song.badSong()));
        // not exist ID
        resSong = Solution.getSong(3);
        assertEquals(true, resSong.equals(Song.badSong()));
        // good ID
        resSong = Solution.getSong(1);
        assertEquals(true, resSong.equals(newSong));
        resSong = Solution.getSong(2);
        assertEquals(true, resSong.equals(newSong2));

        // ~deleteSong
        // not exist
        res = Solution.deleteSong(Song.badSong());
        assertEquals(NOT_EXISTS, res);
        Song notExistSong = new Song();
        notExistSong.setId(3);
        notExistSong.setName("Despacito");
        notExistSong.setGenre("Latin");
        notExistSong.setCountry("Spain");
        res = Solution.deleteSong(notExistSong);
        assertEquals(NOT_EXISTS, res);
        // exist
        res = Solution.deleteSong(newSong);
        assertEquals(OK, res);
        resSong = Solution.getSong(1);
        assertEquals(true, resSong.equals(Song.badSong()));


        // ~updateSongName
        res = Solution.updateSongName(Song.badSong());
        assertEquals(NOT_EXISTS, res);
        newSong2.setName(null);
        res = Solution.updateSongName(newSong2);
        assertEquals(BAD_PARAMS, res);
        newSong2.setName("Echame La Culpa");
        res = Solution.updateSongName(newSong2);
        assertEquals(OK, res);

        // check init playCount
        Song song2 = new Song();
        Song songFromDB;
        song2.setId(9);
        song2.setName("Toy");
        song2.setGenre("Pop");
        song2.setCountry("Israel");
        song2.setPlayCount(3);
        res = Solution.addSong(song2);
        assertEquals(OK, res);
        songFromDB = Solution.getSong(9);
        assertEquals(0, songFromDB.getPlayCount());
    }
//
//    @Test
//    public void followPlaylistTest() {
//
//        ReturnValue res;
//        Playlist p = new Playlist();
//        p.setId(10);
//        p.setGenre("Pop");
//        p.setDescription("Best pop songs of 2018");
//
//        res = Solution.addPlaylist(p);
//        assertEquals(OK, res);
//
//        User u = new User();
//        u.setId(100);
//        u.setName("Nir");
//        u.setCountry("Israel");
//        u.setPremium(false);
//
//        res = Solution.addUser(u);
//        assertEquals(OK, res);
//
//        res = Solution.followPlaylist(100, 10);
//        assertEquals(OK , res);
//
//        res = Solution.followPlaylist(100, 10);
//        assertEquals(ALREADY_EXISTS , res);
//
//        res = Solution.followPlaylist(101, 10);
//        assertEquals(NOT_EXISTS , res);
//    }
    
    @Test
    public void addPlaylistTest() 
    {
    	ReturnValue res;
        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("play1");
        
        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Pop");
        playlist2.setDescription("play2");
        
        res = Solution.addPlaylist(playlist1);
        assertEquals(OK , res);
        res = Solution.addPlaylist(playlist2);
        assertEquals(OK , res);
        
        res = Solution.addPlaylist(playlist1);
        assertEquals(ALREADY_EXISTS , res);
        
        Playlist playlist_bad = new Playlist();
        playlist_bad.setId(-1);
        playlist_bad.setGenre("Pop");
        playlist_bad.setDescription("play1");
        
        res = Solution.addPlaylist(playlist_bad);
        assertEquals(BAD_PARAMS , res);
        
        playlist_bad.setId(14);
        playlist_bad.setGenre(null);
        playlist_bad.setDescription("play1");
        
        res = Solution.addPlaylist(playlist_bad);
        assertEquals(BAD_PARAMS , res);
        
        playlist_bad.setId(14);
        playlist_bad.setGenre("genere");
        playlist_bad.setDescription(null);
        
        res = Solution.addPlaylist(playlist_bad);
        assertEquals(BAD_PARAMS , res);
        
        playlist_bad.setId(0);
        playlist_bad.setGenre(null);
        playlist_bad.setDescription(null);
        
        res = Solution.addPlaylist(playlist_bad);
        assertEquals(BAD_PARAMS , res);
        
        playlist_bad.setId(14);
        playlist_bad.setGenre("genere");
        playlist_bad.setDescription("song22");
        
        res = Solution.addPlaylist(playlist_bad);
        assertEquals(OK , res);
    }

    @Test
    public void getPlaylistTest() 
    {
    	ReturnValue res;
        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("play1");
        
        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Rock");
        playlist2.setDescription("play2");
        
        Playlist playlist_bad = new Playlist();
        playlist_bad.setId(-2);
        playlist_bad.setGenre("Rock");
        playlist_bad.setDescription("play2");
        
        res = Solution.addPlaylist(playlist1);
        assertEquals(OK , res);
        res = Solution.addPlaylist(playlist2);
        assertEquals(OK , res);
        res = Solution.addPlaylist(playlist_bad);
        assertEquals(BAD_PARAMS , res);
        res = Solution.addPlaylist(playlist2);
        assertEquals(ALREADY_EXISTS , res);
      
        Playlist temp_playlist = Solution.getPlaylist(1);
        assertEquals(true, temp_playlist.getId() == 1);
        assertEquals(true, temp_playlist.getGenre().equals("Pop"));
        assertEquals(true, temp_playlist.getDescription().equals("play1"));
        
       temp_playlist = Solution.getPlaylist(2);
        assertEquals(true, temp_playlist.getId() == 2);
        assertEquals(true, temp_playlist.getGenre().equals("Rock"));
        assertEquals(true, temp_playlist.getDescription().equals("play2"));
        
        temp_playlist = Solution.getPlaylist(-2);
        assertEquals(true, temp_playlist.getId() == -1);
        assertEquals(true, temp_playlist.getGenre() == (null));
        assertEquals(true, temp_playlist.getDescription() == (null));
        
        Playlist updated_playlist = Solution.getPlaylist(1);
        updated_playlist.setDescription("diff");
        Solution.updatePlaylist(updated_playlist);
        temp_playlist = Solution.getPlaylist(1);
        assertEquals(true, temp_playlist.getId() == 1);
        assertEquals(true, temp_playlist.getGenre().equals("Pop"));
        assertEquals(true, temp_playlist.getDescription().equals("diff"));
    }
    
   
    @Test
    public void deletePlaylistTest() 
    {
    	ReturnValue res;
        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("play1");
        
        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Rock");
        playlist2.setDescription("play2");
        
        Playlist playlist_bad = new Playlist();
        playlist_bad.setId(-2);
        playlist_bad.setGenre("Rock");
        playlist_bad.setDescription("play2");
        
        res = Solution.addPlaylist(playlist1);
        assertEquals(OK , res);
        res = Solution.addPlaylist(playlist2);
        assertEquals(OK , res);
        res = Solution.addPlaylist(playlist_bad);
        assertEquals(BAD_PARAMS , res);
        res = Solution.addPlaylist(playlist2);
        assertEquals(ALREADY_EXISTS , res);
      
        Playlist temp_playlist = Solution.getPlaylist(1);
        assertEquals(true, temp_playlist.getId() == 1);
        res = Solution.deletePlaylist(temp_playlist);
        assertEquals(OK , res);
        temp_playlist = Solution.getPlaylist(1);
        assertEquals(true, temp_playlist.getId() == -1);
        res = Solution.addPlaylist(playlist1);
        assertEquals(OK , res);
        temp_playlist = Solution.getPlaylist(1);
        assertEquals(true, temp_playlist.getId() == 1);
        res = Solution.deletePlaylist(temp_playlist);
        assertEquals(OK , res);
        temp_playlist = Solution.getPlaylist(1);
        assertEquals(true, temp_playlist.getId() == -1);
        
        temp_playlist = Solution.getPlaylist(2);
        assertEquals(true, temp_playlist.getId() == 2);
        res = Solution.deletePlaylist(temp_playlist);
        assertEquals(OK , res);
        temp_playlist = Solution.getPlaylist(2);
        assertEquals(true, temp_playlist.getId() == -1);
        
        temp_playlist = Solution.getPlaylist(-2);
        assertEquals(true, temp_playlist.getId() == -1);
        
        res = Solution.deletePlaylist(temp_playlist);
        assertEquals(NOT_EXISTS , res);
        
        Playlist temp2 = new Playlist();
        temp2.setId(10);
        temp2.setGenre("genre");
        temp2.setDescription("Description");
        
        res = Solution.deletePlaylist(temp2);
        assertEquals(NOT_EXISTS , res);
    }

    @Test
    public void addSongToPlaylistTest()
    {
    	ReturnValue res;
        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("play1");
        res = Solution.addPlaylist(playlist1);
        assertEquals(OK , res);
        
        Song song1 = new Song();
        song1.setId(1);
        song1.setName("Despacito");
        song1.setGenre("Pop");
        song1.setCountry("Spain");
        res = Solution.addSong(song1);
        assertEquals(OK, res);
        
        res = Solution.addSongToPlaylist(1, 1);
        assertEquals(OK, res);
    }

    @Test
    public void songPlayTest()
    {
        ReturnValue res;
        Song newSong = new Song();
        Song songFromDB;
        newSong.setId(1);
        newSong.setName("Toy");
        newSong.setGenre("Pop");
        newSong.setCountry("Israel");
        res = Solution.addSong(newSong);
        assertEquals(OK, res);
        res = Solution.songPlay(1, 5);
        assertEquals(OK, res);
        songFromDB = Solution.getSong(1);
        assertEquals(5, songFromDB.getPlayCount());
        res = Solution.songPlay(-1, 5);
        assertEquals(NOT_EXISTS, res);
        res = Solution.songPlay(1, -6);
        assertEquals(BAD_PARAMS, res);
        songFromDB = Solution.getSong(1);
        assertEquals(5, songFromDB.getPlayCount());
        res = Solution.songPlay(1, -2);
        assertEquals(OK, res);
        songFromDB = Solution.getSong(1);
        assertEquals(3, songFromDB.getPlayCount());
        res = Solution.songPlay(2, 5);
        assertEquals(NOT_EXISTS, res);
    }

    @Test
    public void followPlaylistTest()
    {
        ReturnValue res;
        User user = new User();
        user.setId(1);
        user.setName("Monica");
        user.setCountry("Spain");

        Playlist playlist = new Playlist();
        playlist.setId(2);
        playlist.setGenre("Latin");
        playlist.setDescription("Latin songs");

        res = Solution.followPlaylist(1, 2);
        assertEquals(NOT_EXISTS, res);
        Solution.addUser(user);
        res = Solution.followPlaylist(1, 2);
        assertEquals(NOT_EXISTS, res);

        Solution.addPlaylist(playlist);
        res = Solution.followPlaylist(1, 2);
        assertEquals(OK, res);

        res = Solution.followPlaylist(1, 2);
        assertEquals(ALREADY_EXISTS, res);

        // stop follow
        res = Solution.stopFollowPlaylist(1, 3);
        assertEquals(NOT_EXISTS, res);
        res = Solution.stopFollowPlaylist(3, 2);
        assertEquals(NOT_EXISTS, res);
        res = Solution.stopFollowPlaylist(1, 2);
        assertEquals(OK, res);
        res = Solution.stopFollowPlaylist(1, 2);
        assertEquals(NOT_EXISTS, res);
    }

    @Test
    public void getPlaylistFollowersCountTest()
    {
        // follow playlist
        int res;
        User user = new User();
        user.setId(1);
        user.setName("Monica");
        user.setCountry("Spain");

        Playlist playlist = new Playlist();
        playlist.setId(2);
        playlist.setGenre("Latin");
        playlist.setDescription("Latin songs");

        Solution.addUser(user);
        Solution.addPlaylist(playlist);

        res = Solution.getPlaylistFollowersCount(2);
        assertEquals(0, res);
        Solution.followPlaylist(1, 2);

        res = Solution.getPlaylistFollowersCount(2);
        assertEquals(1, res);

        User user2 = new User();
        user2.setId(2);
        user2.setName("Jennifer");
        user2.setCountry("UK");
        Playlist playlist2 = new Playlist();
        playlist2.setId(3);
        playlist2.setGenre("Pop");
        playlist2.setDescription("Pop songs");
        Solution.addUser(user2);
        Solution.addPlaylist(playlist2);

        res = Solution.getPlaylistFollowersCount(2);
        assertEquals(1, res);
        res = Solution.getPlaylistFollowersCount(3);
        assertEquals(0, res);

        Solution.followPlaylist(2, 2);
        Solution.followPlaylist(2, 3);
        res = Solution.getPlaylistFollowersCount(2);
        assertEquals(2, res);
        res = Solution.getPlaylistFollowersCount(3);
        assertEquals(1, res);
    }

    @Test
    public void getMostPopularPlaylistTest()
    {
        int res;

        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("Pop songs");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Latin");
        playlist2.setDescription("Latin songs");

        Playlist playlist3 = new Playlist();
        playlist3.setId(3);
        playlist3.setGenre("Latin");
        playlist3.setDescription("Latin songs2");

        Solution.addPlaylist(playlist1);
        Solution.addPlaylist(playlist2);
        Solution.addPlaylist(playlist3);

        Song song11 = new Song();
        song11.setId(11);
        song11.setName("Toy");
        song11.setGenre("Pop");
        song11.setCountry("Israel");

        Song song21 = new Song();
        song21.setId(21);
        song21.setName("Despacito");
        song21.setGenre("Latin");
        song21.setCountry("Spain");

        Song song22 = new Song();
        song22.setId(22);
        song22.setName("Baby");
        song22.setGenre("Latin");
        song22.setCountry("USA");

        Song song31 = new Song();
        song31.setId(31);
        song31.setName("Baby");
        song31.setGenre("Latin");
        song31.setCountry("USA");

        Solution.addSong(song11);
        Solution.addSong(song21);
        Solution.addSong(song22);
        Solution.addSong(song31);

        Solution.addSongToPlaylist(11,1);
        Solution.addSongToPlaylist(21,2);
        Solution.addSongToPlaylist(22,2);
        Solution.addSongToPlaylist(31,3);

        Solution.songPlay(11, 100);
        Solution.songPlay(21, 65);
        Solution.songPlay(22, 45);
        Solution.songPlay(31, 110);

        res = Solution.getMostPopularPlaylist();
        assertEquals(3, res);
    }

    @Test
    public void getMostPopularPlaylistCornerCaseTest()
    {
        int res;

        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("Pop songs");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Latin");
        playlist2.setDescription("Latin songs");

        Solution.addPlaylist(playlist2);
        Solution.addPlaylist(playlist1);

        Song song11 = new Song();
        song11.setId(11);
        song11.setName("Toy");
        song11.setGenre("Pop");
        song11.setCountry("Israel");

        Solution.addSong(song11);

        Solution.addSongToPlaylist(11,1);

        res = Solution.getMostPopularPlaylist();
        assertEquals(2, res);
    }

    @Test
    public void getSimilarUsersTest()
    {
        ArrayList<Integer> res;
        int userId;

        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("Pop songs");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Latin");
        playlist2.setDescription("Latin songs");

        Playlist playlist3 = new Playlist();
        playlist3.setId(3);
        playlist3.setGenre("Rock");
        playlist3.setDescription("Rock songs");

        Solution.addPlaylist(playlist1);
        Solution.addPlaylist(playlist2);
        Solution.addPlaylist(playlist3);

        User user11 = new User();
        user11.setId(11);
        user11.setName("Monica");
        user11.setCountry("Spain");

        User user12 = new User();
        user12.setId(12);
        user12.setName("Neta");
        user12.setCountry("Israel");

        User user13 = new User();
        user13.setId(13);
        user13.setName("Josef");
        user13.setCountry("UK");

        User user14 = new User();
        user14.setId(14);
        user14.setName("Anton");
        user14.setCountry("UK");

        Solution.addUser(user11);
        Solution.addUser(user12);
        Solution.addUser(user13);
        Solution.addUser(user14);

        res = Solution.getSimilarUsers(11);
        assertEquals(0, res.size());

        Solution.followPlaylist(11, 1);
        Solution.followPlaylist(11, 2);
        Solution.followPlaylist(12, 1);
        Solution.followPlaylist(12, 2);
        Solution.followPlaylist(12, 3);
        Solution.followPlaylist(13, 1);
        res = Solution.getSimilarUsers(14);
        assertEquals(0, res.size());
        Solution.followPlaylist(14, 3);

        // only 12 is similar to 11
        res = Solution.getSimilarUsers(11);
        assertEquals(1, res.size());
        userId = res.get(0);
        assertEquals(12, userId);
        // no users similar to 12
        res = Solution.getSimilarUsers(12);
        assertEquals(0, res.size());
        // 11 and 12 similar to 13
        res = Solution.getSimilarUsers(13);
        assertEquals(2, res.size());
        userId = res.get(0);
        assertEquals(11, userId);
        userId = res.get(1);
        assertEquals(12, userId);
        // only 12 is similar to 14
        res = Solution.getSimilarUsers(14);
        assertEquals(1, res.size());
        userId = res.get(0);
        assertEquals(12, userId);
        Solution.followPlaylist(11, 3);
        // now 11 and 12 similar to 14
        res = Solution.getSimilarUsers(14);
        assertEquals(2, res.size());
        userId = res.get(0);
        assertEquals(11, userId);
        userId = res.get(1);
        assertEquals(12, userId);

        // add new user - not follow any playlist
        res = Solution.getSimilarUsers(15);
        assertEquals(0, res.size());
        User user15 = new User();
        user15.setId(15);
        user15.setName("Bernard");
        user15.setCountry("Spain");
        Solution.addUser(user15);
        res = Solution.getSimilarUsers(15);
        assertEquals(0, res.size());
    }

    @Test
    public void getTopCountryPlaylistsTest()
    {
        ArrayList<Integer> res;

        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("Pop songs");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Latin");
        playlist2.setDescription("Latin songs");

        Solution.addPlaylist(playlist1);
        Solution.addPlaylist(playlist2);

        // 5 before 7
        playlist2.setId(5);
        Solution.addPlaylist(playlist2);
        for(int i = 3; i < 13; i++) {
            if(i!=5) {
                playlist2.setId(i);
                Solution.addPlaylist(playlist2);
            }
        }

        Song song11 = new Song();
        song11.setId(11);
        song11.setName("Echame La culpa");
        song11.setGenre("Pop");
        song11.setCountry("Spain");

        Song song21 = new Song();
        song21.setId(21);
        song21.setName("Despacito");
        song21.setGenre("Latin");
        song21.setCountry("Spain");

        Song song22 = new Song();
        song22.setId(22);
        song22.setName("Baby");
        song22.setGenre("Latin");
        song22.setCountry("USA");

        Solution.addSong(song11);
        Solution.addSong(song21);
        Solution.addSong(song22);

        Solution.addSongToPlaylist(11,1);
        Solution.addSongToPlaylist(21,2);
        Solution.addSongToPlaylist(22,2);

        Solution.songPlay(11, 100);
        Solution.songPlay(21, 30);
        Solution.songPlay(22, 45);

        song22.setCountry("Spain");
        for(int i = 3; i < 13; i++) {
            song22.setId(i+20);
            Solution.addSong(song22);
            Solution.addSongToPlaylist(i+20, i);
            if(i%2 == 0) {
                Solution.songPlay(i+20, i+20);
            } else {
                if(i == 5) {
                    Solution.songPlay(i+20, 7);
                } else {
                    Solution.songPlay(i+20, i);
                }
            }
        }


        User user1 = new User();
        user1.setId(1);
        user1.setName("Monica");
        user1.setCountry("Spain");
        user1.setPremium(true);
        Solution.addUser(user1);

        res = Solution.getTopCountryPlaylists(1);
        assertEquals(10, res.size());

        int plID;
        plID = res.get(0);
        assertEquals(1, plID);
        plID = res.get(1);
        assertEquals(2, plID);
        plID = res.get(2);
        assertEquals(12, plID);
        plID = res.get(3);
        assertEquals(10, plID);
        plID = res.get(4);
        assertEquals(8, plID);
        plID = res.get(5);
        assertEquals(6, plID);
        plID = res.get(6);
        assertEquals(4, plID);
        plID = res.get(7);
        assertEquals(11, plID);
        plID = res.get(8);
        assertEquals(9, plID);
        plID = res.get(9);
        assertEquals(7, plID);

    }

}//End class

