package com.loadbalancer.HaversineTest;

import com.loadbalancer.Utils.DistanceUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HaversineUtilTest {

    @Test
    public void testZeroDistance() {
        double lat = 28.6139;
        double lon = 77.2090;
        double dist = DistanceUtil.haversine(lat, lon, lat, lon);
        assertEquals(0.0, dist, 0.0001, "Distance between same points should be 0");
    }

    @Test
    public void testDelhiToNoidaDistance() {
        double delhiLat = 28.6139;
        double delhiLon = 77.2090;
        double noidaLat = 28.5355;
        double noidaLon = 77.3910;

        double dist = DistanceUtil.haversine(delhiLat, delhiLon, noidaLat, noidaLon);
        assertTrue(dist > 10 && dist < 25, "Delhi to Noida should be approx. 20 km");
    }

    @Test
    public void testDelhiToChennaiDistance() {
        double delhiLat = 28.6139;
        double delhiLon = 77.2090;
        double chennaiLat = 13.0827;
        double chennaiLon = 80.2707;

        double dist = DistanceUtil.haversine(delhiLat, delhiLon, chennaiLat, chennaiLon);
        assertTrue(dist > 1700 && dist < 2200, "Delhi to Chennai should be around 1750–2100 km");
    }
}

