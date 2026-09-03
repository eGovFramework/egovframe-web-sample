package egovframework.example.cmmn;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
import org.junit.jupiter.api.Test;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * [공통] 에러 화면이 참조하는 메시지 코드와 정적 자원 테스트
 *
 * <p>
 * 에러 화면은 다른 화면이 실패했을 때 마지막으로 뜨는 화면이라 자기가 참조하는 자원이 없으면
 * 사용자에게 보여줄 것이 남지 않는다. 화면이 쓰는 spring:message 코드는
 * context-common.xml 의 messageSource(classpath:/egovframework/message/message-common)
 * 로 해석돼야 하고, 링크하는 정적 자원은 src/main/webapp 에 실재해야 한다.
 * </p>
 */
class EgovErrorViewResourceTest {

	/** 에러 화면 */
	private static final List<Path> ERROR_VIEWS = List.of(
			Path.of("src/main/webapp/WEB-INF/jsp/egovframework/example/cmmn/dataAccessFailure.jsp"),
			Path.of("src/main/webapp/WEB-INF/jsp/egovframework/example/cmmn/egovBizException.jsp"),
			Path.of("src/main/webapp/WEB-INF/jsp/egovframework/example/cmmn/egovError.jsp"),
			Path.of("src/main/webapp/WEB-INF/jsp/egovframework/example/cmmn/transactionFailure.jsp"),
			Path.of("src/main/webapp/common/error.jsp"));

	/** spring:message 의 code 속성 */
	private static final Pattern MESSAGE_CODE = Pattern.compile("<spring:message[^>]*code=['\"]([^'\"]+)['\"]");

	/** c:url 의 정적 자원 경로 */
	private static final Pattern STATIC_URL = Pattern.compile("<c:url[^>]*value=['\"](/[^'\"]+)['\"]");

	/** context-common.xml 의 messageSource */
	private static ReloadableResourceBundleMessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/egovframework/message/message-common");
		return messageSource;
	}

	@Test
	void test() {
		// given
		final ReloadableResourceBundleMessageSource messageSource = messageSource();
		final Set<Locale> locales = new LinkedHashSet<>(List.of(Locale.KOREAN, Locale.ENGLISH));

		final List<String> missing = new ArrayList<>();

		// when
		for (final Path view : ERROR_VIEWS) {
			String source;
			try {
				source = Files.readString(view, StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new BaseRuntimeException(e);
			}

			final Matcher codes = MESSAGE_CODE.matcher(source);
			while (codes.find()) {
				for (final Locale locale : locales) {
					try {
						messageSource.getMessage(codes.group(1), null, locale);
					} catch (final NoSuchMessageException e) {
						missing.add(view + " : 메시지 코드 " + codes.group(1) + " (" + locale + ")");
					}
				}
			}

			final Matcher urls = STATIC_URL.matcher(source);
			while (urls.find()) {
				final Path resource = Path.of("src/main/webapp", urls.group(1));
				if (!Files.isRegularFile(resource)) {
					missing.add(view + " : 정적 자원 " + urls.group(1));
				}
			}
		}

		// then
		assertTrue(missing.isEmpty(), "에러 화면이 없는 자원을 참조하면 화면 대신 예외가 뜬다. " + missing);
	}

}
