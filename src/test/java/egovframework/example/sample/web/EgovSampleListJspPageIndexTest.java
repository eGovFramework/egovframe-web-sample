package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
import org.junit.jupiter.api.Test;

/**
 * [게시판][egovSampleList.jsp] 목록 화면의 pageIndex 왕복 테스트
 *
 * <p>
 * EgovSampleController 는 updateSampleView.do 에서 detail.setPageIndex(...) 로,
 * updateSample.do / deleteSample.do 에서 redirectAttributes 로 pageIndex 를 되돌려
 * 준다. 목록 화면의 히든 필드가 상수를 내보내면 이 왕복이 끊어져 사용자가 항상 1페이지로
 * 돌아온다. 형제 필드인 searchCondition, searchKeyword 및 egovSampleRegister.jsp 와
 * 동일하게 sampleVO 값을 실어 보내야 한다.
 * </p>
 */
class EgovSampleListJspPageIndexTest {

	/** 목록 화면 JSP */
	private static final Path LIST_JSP = Path
			.of("src/main/webapp/WEB-INF/jsp/egovframework/example/sample/egovSampleList.jsp");

	/** listForm 의 pageIndex 히든 필드 */
	private static final Pattern PAGE_INDEX_HIDDEN = Pattern
			.compile("<input[^>]*name=\"pageIndex\"[^>]*value=\"([^\"]*)\"");

	@Test
	void test() {
		// given
		String jsp;
		try {
			jsp = Files.readString(LIST_JSP, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new BaseRuntimeException(e);
		}

		// when
		final Matcher matcher = PAGE_INDEX_HIDDEN.matcher(jsp);
		assertTrue(matcher.find(), "listForm 에 pageIndex 히든 필드가 있어야 한다.");
		final String value = matcher.group(1);

		// then
		assertTrue("${sampleVO.pageIndex}".equals(value),
				"목록 화면의 pageIndex 는 조회한 페이지를 그대로 실어 보내야 한다. value=" + value);
	}

}
