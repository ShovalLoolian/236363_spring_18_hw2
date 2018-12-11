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
}

