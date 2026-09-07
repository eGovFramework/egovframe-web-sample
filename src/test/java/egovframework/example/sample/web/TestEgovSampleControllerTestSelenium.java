package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.abort;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판] 셀레늄 단위 테스트
 * 
 * @author 이백행
 * @since 2024-09-03
 *
 */
@Slf4j
@NoArgsConstructor
class TestEgovSampleControllerTestSelenium {

	/**
	 * 웹 드라이버
	 */
	private WebDriver driver;

	/**
	 * 설정
	 */
	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
	}

	@Test
	void test() {
		if (log.isDebugEnabled()) {
			log.debug("[게시판] 셀레늄 단위 테스트");
		}

		// gevin
		if (log.isDebugEnabled()) {
			log.debug("게시판 목록 화면 이동");
		}
		try {
			driver.get("http://localhost:8080/egovframe-web");
		} catch (WebDriverException e) {
			abort("테스트 서버가 실행 중이 아니므로 테스트를 건너뜁니다.");
		}

		final JavascriptExecutor executor = (JavascriptExecutor) driver;

		if (log.isDebugEnabled()) {
			log.debug("등록 버튼 클릭");
		}
		sleep();
		executor.executeScript("fn_egov_addView();");

		if (log.isDebugEnabled()) {
			log.debug("카테고리명 입력");
		}
		sleep();
		final WebElement name = driver.findElement(By.id("name"));
		final String now = LocalDateTime.now().toString();
		final String td3String = "test 이백행 카테고리명 " + now;
		name.sendKeys(td3String);

		if (log.isDebugEnabled()) {
			log.debug("사용여부 선택");
		}
		sleep();
		final WebElement useYn = driver.findElement(By.id("useYn"));
		useYn.sendKeys("N");

		if (log.isDebugEnabled()) {
			log.debug("설명 입력");
		}
		sleep();
		final WebElement description = driver.findElement(By.id("description"));
		final String td5String = "test 이백행 설명 " + now;
		description.sendKeys(td5String);

		if (log.isDebugEnabled()) {
			log.debug("등록자 입력");
		}
		sleep();
		final WebElement regUser = driver.findElement(By.id("regUser"));
		regUser.sendKeys("test");

		// when
		if (log.isDebugEnabled()) {
			log.debug("등록 버튼 클릭");
		}
		sleep();
		executor.executeScript("sampleAdd();");

		// Switch to the alert
		Alert alert = driver.switchTo().alert();

		// Accept the alert (click "Yes" or "OK")
		alert.accept();

		// or Dismiss the alert (click "No" or "Cancel")
		// alert.dismiss();

		sleep();

		// then
		final WebElement td5WebElement = driver.findElement(By
				.cssSelector("#content_pop > div.krds-table-wrap > table > tbody > tr:nth-child(1) > td:nth-child(4)"));
		assertEquals(td5String, td5WebElement.getText().trim(), "게시판 등록 화면 실패");
	}

	private void sleep() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			fail("InterruptedException: Thread.sleep");
		}
	}

}