package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
import org.junit.jupiter.api.Test;

/**
 * [게시판][egovSampleRegister.jsp] 등록 폼 maxlength 와 컬럼폭 테스트
 *
 * <p>
 * SAMPLE 테이블은 문자 컬럼에 길이를 두고, HSQLDB 는 그 길이를 넘는 값에
 * string data, right truncation 을 던진다. 등록 폼이 컬럼폭보다 큰 maxlength 를
 * 내보내면 브라우저가 통과시킨 입력이 INSERT 에서 죽고 화면은 오류 페이지가 된다.
 * </p>
 */
class EgovSampleRegisterJspMaxLengthTest {

	/** 등록·수정 화면 JSP */
	private static final Path REGISTER_JSP = Path
			.of("src/main/webapp/WEB-INF/jsp/egovframework/example/sample/egovSampleRegister.jsp");

	/** SAMPLE 테이블 DDL */
	private static final Path SAMPLE_DDL = Path.of("src/main/resources/db/sampledb.sql");

	/** DDL 의 문자 컬럼 — 이름과 길이 */
	private static final Pattern DDL_COLUMN = Pattern.compile("([A-Z_]+) VARCHAR\\((\\d+)\\)");

	/** 폼 필드 — path 와 maxlength (대소문자 구분 없음) */
	private static final Pattern FORM_FIELD = Pattern
			.compile("path=\"(\\w+)\"[^>]*?maxlength=\"(\\d+)\"", Pattern.CASE_INSENSITIVE);

	@Test
	void test() {
		// given
		final String ddl = read(SAMPLE_DDL);
		final String jsp = read(REGISTER_JSP);

		final Map<String, Integer> columnWidths = new LinkedHashMap<>();
		final Matcher column = DDL_COLUMN.matcher(ddl);
		while (column.find()) {
			columnWidths.put(column.group(1), Integer.parseInt(column.group(2)));
		}
		assertFalse(columnWidths.isEmpty(), "DDL 에서 문자 컬럼을 찾지 못했다.");

		// when
		final List<String> over = new ArrayList<>();
		final Matcher field = FORM_FIELD.matcher(jsp);
		boolean matched = false;
		while (field.find()) {
			final String columnName = toColumnName(field.group(1));
			final Integer width = columnWidths.get(columnName);
			if (width == null) {
				continue;
			}
			matched = true;
			final int maxLength = Integer.parseInt(field.group(2));
			if (maxLength > width) {
				over.add(field.group(1) + " maxlength=" + maxLength + " > " + columnName + " VARCHAR(" + width + ")");
			}
		}
		assertTrue(matched, "등록 폼에서 DDL 컬럼에 대응하는 필드를 찾지 못했다.");

		// then
		assertTrue(over.isEmpty(), "등록 폼의 maxlength 는 컬럼폭을 넘지 않아야 한다. " + over);
	}

	/** regUser 처럼 낙타표기인 폼 path 를 REG_USER 형태의 컬럼명으로 바꾼다. */
	private static String toColumnName(String path) {
		return path.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
	}

	private static String read(Path path) {
		try {
			return Files.readString(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new BaseRuntimeException(e);
		}
	}

}
