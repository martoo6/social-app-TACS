package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.FlightsRepository;
import com.hax.connectors.RecommendationsRepository;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.Ticket;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class RecommendationsRepositoryTest extends GenericTest {

    @Test
    public void insertRecommendation(){
        RecommendationsRepository rr = new RecommendationsRepository();

        Recommendation recommendation = new Recommendation(null, null, null);

        ListenableFuture<Recommendation> lf = rr.insert(recommendation);

        try {
            lf.get();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }
}
