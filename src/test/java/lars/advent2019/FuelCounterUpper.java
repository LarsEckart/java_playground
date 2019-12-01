package lars.advent2019;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class FuelCounterUpper {

    @ParameterizedTest
    @CsvSource({"12,2", "14,2", "1969,654", "100756,33583"})
    void examples(int mass, int requiredFuel) {
        assertThat(calculateRequiredFuel(mass)).isEqualTo(requiredFuel);
    }

    private long calculateRequiredFuel(long i) {
        long l = i / 3;
        double floor = Math.ceil(l);
        return (long) (floor - 2);
    }

    @Test
    void solution() {
        String input = "104489\n"
                + "69854\n"
                + "93424\n"
                + "103763\n"
                + "119636\n"
                + "130562\n"
                + "121744\n"
                + "84851\n"
                + "143661\n"
                + "94519\n"
                + "116576\n"
                + "148771\n"
                + "74038\n"
                + "131735\n"
                + "95594\n"
                + "125198\n"
                + "92217\n"
                + "84471\n"
                + "53518\n"
                + "97787\n"
                + "55422\n"
                + "137807\n"
                + "78806\n"
                + "74665\n"
                + "103930\n"
                + "121642\n"
                + "123008\n"
                + "104598\n"
                + "97383\n"
                + "129359\n"
                + "85372\n"
                + "88930\n"
                + "106944\n"
                + "118404\n"
                + "126095\n"
                + "67230\n"
                + "116697\n"
                + "85950\n"
                + "148291\n"
                + "123171\n"
                + "82736\n"
                + "52753\n"
                + "134746\n"
                + "53238\n"
                + "74952\n"
                + "105933\n"
                + "104613\n"
                + "115283\n"
                + "80320\n"
                + "139152\n"
                + "76455\n"
                + "66729\n"
                + "81209\n"
                + "130176\n"
                + "116843\n"
                + "67292\n"
                + "74262\n"
                + "131694\n"
                + "92817\n"
                + "51540\n"
                + "58957\n"
                + "143342\n"
                + "76896\n"
                + "129631\n"
                + "77148\n"
                + "129784\n"
                + "115307\n"
                + "96214\n"
                + "110538\n"
                + "51492\n"
                + "124376\n"
                + "78161\n"
                + "59821\n"
                + "58184\n"
                + "132524\n"
                + "130714\n"
                + "112653\n"
                + "137988\n"
                + "112269\n"
                + "62214\n"
                + "115989\n"
                + "123073\n"
                + "119711\n"
                + "82258\n"
                + "67695\n"
                + "81023\n"
                + "70012\n"
                + "93438\n"
                + "131749\n"
                + "103652\n"
                + "63557\n"
                + "88224\n"
                + "117414\n"
                + "75579\n"
                + "146422\n"
                + "139852\n"
                + "85116\n"
                + "124902\n"
                + "143167\n"
                + "147781\n";

        long sum = input.lines().mapToLong(((Long::parseLong))).map(this::calculateRequiredFuel).sum();

        assertThat(sum).isEqualTo(3376997);
    }
}
