/**
 * @author diego
 *
 */
package de.n26.transaction.statistic;

import com.jayway.restassured.http.ContentType;
import de.n26.transaction.statistic.model.TransactionStatisticModel;
import de.n26.transaction.statistic.repository.TransactionRepository;
import de.n26.transaction.statistic.util.TimestampUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StaticsSystemApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class StatisticsAPIIT {
	
	
	@LocalServerPort
    private int port;

	@Autowired
	private TransactionRepository transactionRepository;

	@Before
	public void setup() {

		transactionRepository.updateSummaryTransaction(new TransactionStatisticModel());
	}
	
	@Test
	public void getStatisticsTestSuccess() {
		
		given()
			.when()
			.get("http://127.0.0.1:" + port + "/statistics")
			.then()
				.assertThat()
				.statusCode(200)
				.body("count", equalTo(0))
				.body("sum", is(0.0f))
				.body("avg", is(0.0f))
				.body("max", is(0.0f))
				.body("min", is(0.0f));
	}
	
	@Test
	public void createStatisticTestSuccess() {

		long timestamp = TimestampUtils.generateTimestampUnix();
		given()
			.contentType(ContentType.JSON)
			.body("{ \"amount\": 200, \"timestamp\": " + timestamp + "}")
			.when()
			.post("http://127.0.0.1:" + port + "/transactions")
			.then()
			.assertThat()
			.statusCode(201);

		timestamp = TimestampUtils.generateTimestampUnix();
		given()
				.contentType(ContentType.JSON)
				.body("{ \"amount\": 10, \"timestamp\": " + timestamp + "}")
				.when()
				.post("http://127.0.0.1:" + port + "/transactions")
				.then()
				.assertThat()
				.statusCode(201);

		timestamp = TimestampUtils.generateTimestampUnix();
		given()
				.contentType(ContentType.JSON)
				.body("{ \"amount\": 10, \"timestamp\": " + timestamp + "}")
				.when()
				.post("http://127.0.0.1:" + port + "/transactions")
				.then()
				.assertThat()
				.statusCode(201);

		timestamp = TimestampUtils.generateTimestampUnix();
		given()
				.contentType(ContentType.JSON)
				.body("{ \"amount\": 330, \"timestamp\": " + timestamp + "}")
				.when()
				.post("http://127.0.0.1:" + port + "/transactions")
				.then()
				.assertThat()
				.statusCode(201);

		given()
				.when()
				.get("http://127.0.0.1:" + port + "/statistics")
				.then()
				.assertThat()
				.statusCode(200)
				.body("count", equalTo(4))
				.body("sum", is(550.0f))
				.body("avg", is(137.5f))
				.body("max", is(330.0f))
				.body("min", is(10.0f));
	}

	@Test
	public void tryInsertOldTimestampTestSuccess() {

		long timestamp = TimestampUtils.decreaseDate(TimestampUtils.generateTimestampUnix(), 1);
		given()
				.contentType(ContentType.JSON)
				.body("{ \"amount\": 200, \"timestamp\": " + timestamp + "}")
				.when()
				.post("http://127.0.0.1:" + port + "/transactions")
				.then()
				.assertThat()
				.statusCode(204);
	}
}