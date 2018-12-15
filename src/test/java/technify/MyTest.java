package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.ReturnValue;
import technify.business.Song;
import technify.business.User;

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



        // ~updateSongName
        res = Solution.updateSongName(Song.badSong());
        assertEquals(NOT_EXISTS, res);
        newSong2.setName(null);
        res = Solution.updateSongName(newSong2);
        assertEquals(BAD_PARAMS, res);
        newSong2.setName("Echame La Culpa");
        res = Solution.updateSongName(newSong2);
        assertEquals(OK, res);

//        s.setId(1);
//        s.setName("Despacito");
//        s.setGenre("Latin");
//        s.setCountry("Spain");
//
//        res = Solution.addSong(s);
//        assertEquals(OK, res);
//
//        res = Solution.addSong(s);
//        assertEquals(ALREADY_EXISTS, res);
//
//        Song s2 = new Song();
//        s2.setId(-1);
//        s2.setName("Despacito");
//        s2.setGenre("Latin");
//        s2.setCountry("Spain");
//
//        res = Solution.addSong(s2);
//        assertEquals(BAD_PARAMS, res);
//
//        s2.setId(2);
//        s2.setGenre(null);
//
//        res = Solution.addSong(s2);
//        assertEquals(BAD_PARAMS, res);
//
//        s2.setGenre("Latinos");
//
//        res = Solution.addSong(s2);
//        assertEquals(OK, res);
//
//        Song s_by_id = Solution.getSong(1);
//        assertEquals(true, s_by_id.equals(s));
//
//        Song s_by_id2 = Solution.getSong(30);
//        assertEquals(true, s_by_id2.equals(new Song()));

//        res = Solution.songPlay(1, 1);
//        assertEquals(OK, res);
//
//        res = Solution.songPlay(1, -3);
//        assertEquals(BAD_PARAMS, res);
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
        newSong.setId(1);
        newSong.setName("Toy");
        newSong.setGenre("Pop");
        newSong.setCountry("Israel");
        res = Solution.addSong(newSong);
        assertEquals(OK, res);
        res = Solution.songPlay(1, 5);
        assertEquals(OK, res);
        res = Solution.songPlay(-1, 5);
        assertEquals(NOT_EXISTS, res);
        res = Solution.songPlay(1, -6);
        assertEquals(BAD_PARAMS, res);
        res = Solution.songPlay(1, -2);
        assertEquals(OK, res);
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

}//End class

