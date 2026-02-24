package egovframework.example.sample.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;
import egovframework.test.EgovTestAbstractSpring;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][EgovSampleServiceImpl.insertSample] ServiceImpl 단위 테스트
 * 
 * @author 이백행
 * @since 2024-09-21
 *
 */

@ContextConfiguration(classes = { EgovSampleServiceImplTestInsertSampleTest.class, EgovTestAbstractSpring.class })

@Configuration

@ImportResource({ "classpath*:egovframework/spring/context-idgen.xml", })

@ComponentScan(useDefaultFilters = false, basePackages = {
		"egovframework.example.sample.service.impl", }, includeFilters = {
				@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { EgovSampleServiceImpl.class,
						SampleMapper.class, }) })

@RequiredArgsConstructor
@Slf4j
class EgovSampleServiceImplTestInsertSampleTest extends EgovTestAbstractSpring {

	/**
	 * 
	 */
	@Autowired
	private EgovSampleService egovSampleService;

	@Test
	void test() throws Exception {
		// given
		final SampleVO sampleVO = new SampleVO();

		final LocalDateTime now = LocalDateTime.now();
		final String now2 = now.format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss"));
		final String now3 = now.format(DateTimeFormatter.ofPattern("uuuuMMddHHmmssS"));

		sampleVO.setName("test 이백행 카테고리명 " + now);

		sampleVO.setUseYn("Y"); // 사용여부

		sampleVO.setDescription("test 이백행 설명 " + now);

		sampleVO.setRegUser("test");

		// when
		egovSampleService.insertSample(sampleVO);

		// then
		final SampleVO resultSampleVO = egovSampleService.selectSample(sampleVO);

		if (log.isDebugEnabled()) {
			log.debug("now={}", now);
			log.debug("now2={}", now2);
			log.debug("now3={}", now3);

			log.debug("sampleVO={}", sampleVO);
			log.debug("getId={}", sampleVO.getId());

			log.debug("resultSampleVO={}", resultSampleVO);
			log.debug("getId={}", resultSampleVO.getId());
		}
	}

}