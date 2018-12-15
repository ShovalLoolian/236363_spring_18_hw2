package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.ReturnValue;
import technify.business.Song;
import technify.business.User;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;

import java.util.ArrayList;


public class MyTest extends AbstractTest {
	
	//Cancel other test so it will run faster:
	/*
    @Test
    public void addUserTest() {

        ReturnValue res;
        User u = new User();
        User u2;
        u.setId(1);
        u.setName("Monica");
        u.setCountry("Spain");
        u.setPremium(false);

        res = Solution.addUser(u);
        assertEquals(OK, res);
        res = Solution.addUser(u);
        assertEquals(ALREADY_EXISTS, res);
        u2 = Solution.getUserProfile(1);
        assertEquals(true, u2.equals(u));
        u2 = Solution.getUserProfile(2);
        assertEquals(true, u2.equals(new User()));
        Solution.updateUserPremium(1);
        u2 = Solution.getUserProfile(1);
        assertEquals(true, u2.getPremium());
        Solution.updateUserNotPremium(1);
        u2 = Solution.getUserProfile(1);
        assertEquals(false, u2.getPremium());
        res = Solution.updateUserNotPremium(1);
        assertEquals(ALREADY_EXISTS, res);
        res = Solution.updateUserNotPremium(110);
        assertEquals(NOT_EXISTS, res);

    }
    @Test
    public void addSongTest() {

        ReturnValue res;
        Song s = new Song();
        s.setId(1);
        s.setName("Despacito");
        s.setGenre("Latin");
        s.setCountry("Spain");

        res = Solution.addSong(s);
        assertEquals(OK, res);

        res = Solution.addSong(s);
        assertEquals(ALREADY_EXISTS, res);

        Song s2 = new Song();
        s2.setId(-1);
        s2.setName("Despacito");
        s2.setGenre("Latin");
        s2.setCountry("Spain");

        res = Solution.addSong(s2);
        assertEquals(BAD_PARAMS, res);

        s2.setId(2);
        s2.setGenre(null);

        res = Solution.addSong(s2);
        assertEquals(BAD_PARAMS, res);

        s2.setGenre("Latinos");

        res = Solution.addSong(s2);
        assertEquals(OK, res);

        Song s_by_id = Solution.getSong(1);
        assertEquals(true, s_by_id.equals(s));

        Song s_by_id2 = Solution.getSong(30);
        assertEquals(true, s_by_id2.equals(new Song()));

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
        
        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Rock");
        playlist2.setDescription("play2");
        res = Solution.addPlaylist(playlist2);
        assertEquals(OK , res);
        
        Song song1 = new Song();
        song1.setId(1);
        song1.setName("Despacito");
        song1.setGenre("Pop");
        song1.setCountry("Spain");
        res = Solution.addSong(song1);
        assertEquals(OK, res);
        
        Song song2 = new Song();
        song2.setId(2);
        song2.setName("somename");
        song2.setGenre("Rock");
        song2.setCountry("Poland");
        res = Solution.addSong(song2);
        assertEquals(OK, res);
        
        res = Solution.addSongToPlaylist(1, 1);
        assertEquals(OK, res);
        
        res = Solution.addSongToPlaylist(2, 2);
        assertEquals(OK, res);
        
        res = Solution.addSongToPlaylist(1, 2);
        assertEquals(BAD_PARAMS, res);
        
        res = Solution.addSongToPlaylist(2, 1);
        assertEquals(BAD_PARAMS, res);
        
        res = Solution.addSongToPlaylist(3, 1);
        assertEquals(NOT_EXISTS, res);
        
        res = Solution.addSongToPlaylist(2, 3);
        assertEquals(NOT_EXISTS, res);
        
        res = Solution.addSongToPlaylist(2, 2);
        assertEquals(ALREADY_EXISTS, res);
    }

    @Test
    public void removeSongFromPlaylistTest()
    {
    	ReturnValue res;
        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("play1");
        res = Solution.addPlaylist(playlist1);
        assertEquals(OK , res);
        
        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Rock");
        playlist2.setDescription("play2");
        res = Solution.addPlaylist(playlist2);
        assertEquals(OK , res);
        
        Song song1 = new Song();
        song1.setId(1);
        song1.setName("Despacito");
        song1.setGenre("Pop");
        song1.setCountry("Spain");
        res = Solution.addSong(song1);
        assertEquals(OK, res);
        
        Song song2 = new Song();
        song2.setId(2);
        song2.setName("somename");
        song2.setGenre("Rock");
        song2.setCountry("Poland");
        res = Solution.addSong(song2);
        assertEquals(OK, res);
        
        res = Solution.addSongToPlaylist(1, 1);
        assertEquals(OK, res);
        res = Solution.addSongToPlaylist(2, 2);
        assertEquals(OK, res);
        res = Solution.addSongToPlaylist(1, 1);
        assertEquals(ALREADY_EXISTS, res);
        
        res = Solution.removeSongFromPlaylist(1, 1);
        assertEquals(OK, res);
        res = Solution.addSongToPlaylist(1, 1);
        assertEquals(OK, res);
        
        res = Solution.removeSongFromPlaylist(1, 1);
        assertEquals(OK, res);
        res = Solution.removeSongFromPlaylist(1, 2);
        assertEquals(NOT_EXISTS, res);
        res = Solution.removeSongFromPlaylist(3, 2);
        assertEquals(NOT_EXISTS, res);
        res = Solution.removeSongFromPlaylist(1, 3);
        assertEquals(NOT_EXISTS, res);
        res = Solution.removeSongFromPlaylist(3, 6);
        assertEquals(NOT_EXISTS, res);
        res = Solution.removeSongFromPlaylist(-1, 2);
        assertEquals(NOT_EXISTS, res);
        
    }
    
    @Test
    public void getPlaylistTotalPlayCountTest()
    {
    	//Need SongPlay function to check this test....
    	ReturnValue res;
        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setGenre("Pop");
        playlist1.setDescription("play1");
        res = Solution.addPlaylist(playlist1);
        assertEquals(OK , res);
        
        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setGenre("Rock");
        playlist2.setDescription("play2");
        res = Solution.addPlaylist(playlist2);
        assertEquals(OK , res);
        
        Song song1 = new Song();
        song1.setId(1);
        song1.setName("Despacito");
        song1.setGenre("Pop");
        song1.setCountry("Spain");
        res = Solution.addSong(song1);
        assertEquals(OK, res);
        Solution.songPlay(1, 10);
        
        Song song2 = new Song();
        song2.setId(2);
        song2.setName("somename");
        song2.setGenre("Rock");
        song2.setCountry("Poland");
        res = Solution.addSong(song2);
        assertEquals(OK, res);
        Solution.songPlay(2, 10);
        
        Song song3 = new Song();
        song3.setId(3);
        song3.setName("somename2");
        song3.setGenre("Rock");
        song3.setCountry("Holand");
        song3.setPlayCount(20);
        res = Solution.addSong(song3);
        assertEquals(OK, res);
        Solution.songPlay(3, 10);
        
        res = Solution.addSongToPlaylist(1, 1);
        assertEquals(OK, res);
        res = Solution.addSongToPlaylist(2, 2);
        assertEquals(OK, res);
        res = Solution.addSongToPlaylist(3, 2);
        assertEquals(OK, res);
        
        Integer sum1 = Solution.getPlaylistTotalPlayCount(1);
        assertEquals(true, sum1 == 10);
        
        Integer sum2 = Solution.getPlaylistTotalPlayCount(2);
        assertEquals(true, sum2 == 20);
    }
    
	
	 @Test
	    public void getMostPopularSongTest()
	    {
		 	ReturnValue res;
	        Playlist playlist1 = new Playlist();
	        playlist1.setId(1);
	        playlist1.setGenre("Pop");
	        playlist1.setDescription("play1");
	        res = Solution.addPlaylist(playlist1);
	        assertEquals(OK , res);
	        
	        Playlist playlist2 = new Playlist();
	        playlist2.setId(2);
	        playlist2.setGenre("Pop");
	        playlist2.setDescription("play2");
	        res = Solution.addPlaylist(playlist2);
	        assertEquals(OK , res);
	        
	        Song song1 = new Song();
	        song1.setId(1);
	        song1.setName("song1");
	        song1.setGenre("Pop");
	        song1.setCountry("Spain");
	        res = Solution.addSong(song1);
	        assertEquals(OK, res);
	        Solution.songPlay(1, 10);
	        
	        Song song2 = new Song();
	        song2.setId(2);
	        song2.setName("song2");
	        song2.setGenre("Pop");
	        song2.setCountry("Poland");
	        res = Solution.addSong(song2);
	        assertEquals(OK, res);
	        Solution.songPlay(2, 10);
	        
	        Song song3 = new Song();
	        song3.setId(3);
	        song3.setName("song3");
	        song3.setGenre("Pop");
	        song3.setCountry("Holand");
	        song3.setPlayCount(20);
	        res = Solution.addSong(song3);
	        assertEquals(OK, res);
	        Solution.songPlay(3, 10);
	        
	        res = Solution.addSongToPlaylist(1, 1);
	        assertEquals(OK, res);
	        res = Solution.addSongToPlaylist(2, 2);
	        assertEquals(OK, res);
	        res = Solution.addSongToPlaylist(3, 2);
	        assertEquals(OK, res);
	        res = Solution.addSongToPlaylist(2, 1);
	        assertEquals(OK, res);
	        res = Solution.addSongToPlaylist(3, 1);
	        assertEquals(OK, res);
	        
	        String mostPopular = Solution.getMostPopularSong();
	        assertEquals(true, song3.getName().equals(mostPopular));
	        
	        Playlist playlist3 = new Playlist();
	        playlist3.setId(3);
	        playlist3.setGenre("Pop");
	        playlist3.setDescription("play3");
	        res = Solution.addPlaylist(playlist3);
	        assertEquals(OK , res);
	        res = Solution.addSongToPlaylist(2, 3);
	        assertEquals(OK, res);
	        
	        mostPopular = Solution.getMostPopularSong();
	        assertEquals(true, song2.getName().equals(mostPopular));
	        
	        res = Solution.addSongToPlaylist(3, 3);
	        assertEquals(OK, res);
	        
	        mostPopular = Solution.getMostPopularSong();
	        assertEquals(true, song3.getName().equals(mostPopular));
	    }
	    
	    */
	 
	 	@Test
	    public void hottestPlaylistsOnTechnifyTest()
	    {
	 		ReturnValue res;
	        Playlist playlist1 = new Playlist();
	        playlist1.setId(1);
	        playlist1.setGenre("Pop");
	        playlist1.setDescription("play1");
	        res = Solution.addPlaylist(playlist1);
	        assertEquals(OK , res);
	        
	        Playlist playlist2 = new Playlist();
	        playlist2.setId(2);
	        playlist2.setGenre("Pop");
	        playlist2.setDescription("play2");
	        res = Solution.addPlaylist(playlist2);
	        assertEquals(OK , res);
	        
	        Song song1 = new Song();
	        song1.setId(1);
	        song1.setName("song1");
	        song1.setGenre("Pop");
	        song1.setCountry("Spain");
	        res = Solution.addSong(song1);
	        assertEquals(OK, res);
	        Solution.songPlay(1, 10);
	        
	        Song song2 = new Song();
	        song2.setId(2);
	        song2.setName("song2");
	        song2.setGenre("Pop");
	        song2.setCountry("Poland");
	        res = Solution.addSong(song2);
	        assertEquals(OK, res);
	        Solution.songPlay(2, 10);
	        
	        Song song3 = new Song();
	        song3.setId(3);
	        song3.setName("song3");
	        song3.setGenre("Pop");
	        song3.setCountry("Holand");
	        song3.setPlayCount(20);
	        res = Solution.addSong(song3);
	        assertEquals(OK, res);
	        Solution.songPlay(3, 10);
	        
	        res = Solution.addSongToPlaylist(1, 1);
	        assertEquals(OK, res);
	        res = Solution.addSongToPlaylist(2, 1);
	        assertEquals(OK, res);
	        res = Solution.addSongToPlaylist(3, 1);
	        assertEquals(OK, res);
	        res = Solution.addSongToPlaylist(2, 2);
	        assertEquals(OK, res);
	        res = Solution.addSongToPlaylist(3, 2);
	        assertEquals(OK, res);
	        
	        ArrayList<Integer> list = Solution.hottestPlaylistsOnTechnify();
	        assertEquals(true, list.get(0) == 2);
	        assertEquals(true, list.get(1) == 1);
	        assertEquals(true, list.size() == 2);
	        
	    }
}//End class

