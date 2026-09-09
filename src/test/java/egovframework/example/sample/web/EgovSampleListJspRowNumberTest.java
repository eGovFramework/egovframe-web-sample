package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.junit.jupiter.api.Test;

import egovframework.example.sample.service.SampleDefaultVO;
import jakarta.el.ELProcessor;

/**
 * [게시판][egovSampleList.jsp] 목록 No 칼럼의 한 페이지 행수 테스트
 *
 * <p>
 * EgovSampleController 는 한 페이지 행수를 pageUnit 으로 정해
 * PaginationInfo.recordCountPerPage 에 넣고, 매퍼가 그 값으로 조회 범위를 자른다.
 * pageSize 는 페이저에 찍을 페이지 번호 개수라 행수가 아니다. 목록 No 칼럼이
 * 행수 자리에 pageSize 를 쓰면 두 값이 다른 순간부터 번호가 어긋난다.
 * </p>
 */
class EgovSampleListJspRowNumberTest {

	/** 목록 화면 JSP */
	private static final Path LIST_JSP = Path
			.of("src/main/webapp/WEB-INF/jsp/egovframework/example/sample/egovSampleList.jsp");

	/** No 칼럼의 EL 식 — 반복 상태(status.count)를 쓰는 유일한 식이다. */
	private static final Pattern ROW_NUMBER_EL = Pattern.compile("\\$\\{([^}]*status\\.count[^}]*)\\}");

	/** 전체 건수 */
	private static final int TOTAL_RECORD_COUNT = 114;

	/** 한 페이지 행수 (pageUnit) */
	private static final int RECORD_COUNT_PER_PAGE = 5;

	/** 페이저에 찍을 페이지 번호 개수 (pageSize) */
	private static final int PAGE_SIZE = 10;

	/** 조회한 페이지 */
	private static final int PAGE_INDEX = 2;

	/** 반복 상태 — JSTL varStatus 중 이 식이 쓰는 것은 count 뿐이다. */
	public static class LoopStatus {

		private final int count;

		public LoopStatus(int count) {
			this.count = count;
		}

		public int getCount() {
			return count;
		}

	}

	@Test
	void test() {
		// given
		final String jsp;
		try {
			jsp = Files.readString(LIST_JSP, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new BaseRuntimeException(e);
		}

		final Matcher matcher = ROW_NUMBER_EL.matcher(jsp);
		assertTrue(matcher.find(), "목록 화면에 No 칼럼 EL 식이 있어야 한다.");
		final String expression = matcher.group(1);

		final SampleDefaultVO sampleVO = new SampleDefaultVO();
		sampleVO.setPageIndex(PAGE_INDEX);
		sampleVO.setPageUnit(RECORD_COUNT_PER_PAGE);
		sampleVO.setPageSize(PAGE_SIZE);
		sampleVO.setRecordCountPerPage(RECORD_COUNT_PER_PAGE);

		final PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(PAGE_INDEX);
		paginationInfo.setRecordCountPerPage(RECORD_COUNT_PER_PAGE);
		paginationInfo.setPageSize(PAGE_SIZE);
		paginationInfo.setTotalRecordCount(TOTAL_RECORD_COUNT);

		final ELProcessor processor = new ELProcessor();
		processor.defineBean("sampleVO", sampleVO);
		processor.defineBean("paginationInfo", paginationInfo);
		processor.defineBean("status", new LoopStatus(1));

		// when
		final Number rowNumber = processor.eval(expression);

		// then
		assertEquals(TOTAL_RECORD_COUNT - RECORD_COUNT_PER_PAGE, rowNumber.intValue(),
				"2쪽 첫 행의 No 는 전체 건수에서 앞 쪽 행수만큼 뺀 값이어야 한다. 식=" + expression);
	}

}
