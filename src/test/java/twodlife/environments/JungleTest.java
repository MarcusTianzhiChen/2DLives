package twodlife.environments;

import org.junit.Test;
import twodlife.life.Bug;
import twodlife.life.BugFactory;
import twodlife.life.MaleBug;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JungleTest {

    int X = 10;
    int Y = 10;
    Jungle j = new Jungle(X, Y);

    @Test
    public void testLifeCreationAndDeletionInJungle() {
        Bug b = BugFactory.makeDefaultBug(j);
        j.create(b);
        Point p = j.reverseMap.get(b);
        assertNotEquals(null, p);
        assertTrue(p.x < X && p.x >= 0);
        assertTrue(p.y < Y && p.y >= 0);
        assertTrue(j.internalJungle[p.x][p.y].objects.contains(b));

        j.dies(b);

        Point notExistent = j.reverseMap.get(b);
        assertNull(notExistent);
        assertFalse(j.internalJungle[p.x][p.y].objects.contains(b));

    }
}