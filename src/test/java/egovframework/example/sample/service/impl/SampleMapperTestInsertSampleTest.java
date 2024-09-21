package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

import egovframework.example.sample.service.SampleVO;
import egovframework.test.EgovTestAbstractSpring;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][SampleMapper.insertSample] DAO 단위 테스트
 * 
 * @author 이백행
 * @since 2024-09-21
 *
 */

@ContextConfiguration(classes = { SampleMapperTestInsertSampleTest.class, EgovTestAbstractSpring.class })

@Configuration

@ImportResource({ "classpath*:egovframework/spring/context-idgen.xml", })

@ComponentScan(useDefaultFilters = false, basePackages = {
		"egovframework.example.sample.service.impl", }, includeFilters = {
				@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { SampleMapper.class, }) })

@RequiredArgsConstructor
@Slf4j
class SampleMapperTestInsertSampleTest extends EgovTestAbstractSpring {

	/**
	 * sample에 관한 데이터처리 매퍼 클래스
	 */
	@Autowired
	private SampleMapper sampleMapper;

	/**
	 * 
	 */
	@Autowired
	private EgovIdGnrService egovIdGnrService;

	@Test
	void test() throws Exception {
		// given
		final SampleVO sampleVO = new SampleVO();

		sampleVO.setId(egovIdGnrService.getNextStringId());

		final LocalDateTime now = LocalDateTime.now();
		final String now2 = now.format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss"));
		final String now3 = now.format(DateTimeFormatter.ofPattern("uuuuMMddHHmmssS"));

		sampleVO.setName("test 이백행 카테고리명 " + now);

		sampleVO.setUseYn("Y"); // 사용여부

		sampleVO.setDescription("test 이백행 설명 " + now);

		sampleVO.setRegUser("test");

		// when
		sampleMapper.insertSample(sampleVO);

		// then
		final SampleVO resultSampleVO = sampleMapper.selectSample(sampleVO);

		if (log.isDebugEnabled()) {
			log.debug("now={}", now);
			log.debug("now2={}", now2);
			log.debug("now3={}", now3);

			log.debug("sampleVO={}", sampleVO);
			log.debug("getId={}", sampleVO.getId());

			log.debug("resultSampleVO={}", resultSampleVO);
			log.debug("getId={}", resultSampleVO.getId());
		}

		assertEquals(sampleVO.getId(), resultSampleVO.getId(), "글을 등록한다.");
	}

}
