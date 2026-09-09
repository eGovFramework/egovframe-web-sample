package egovframework.example.sample.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][EgovSampleServiceImpl.selectSampleList] ServiceImpl 단위 테스트
 *
 * @author 이백행
 * @since 2024-09-21
 *
 */

@ContextConfiguration(classes = { EgovSampleServiceImplTestSelectSampleListTest.class, EgovTestAbstractSpring.class })

@Configuration

@ImportResource({ "classpath*:egovframework/spring/context-idgen.xml", })

@ComponentScan(useDefaultFilters = false, basePackages = {
		"egovframework.example.sample.service.impl", }, includeFilters = {
				@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { EgovSampleServiceImpl.class,
						SampleMapper.class, }) })

@Slf4j
class EgovSampleServiceImplTestSelectSampleListTest extends EgovTestAbstractSpring {

	/**
	 *
	 */
	@Autowired
	EgovSampleService egovSampleService;

	@Test
	void test() {
		// given
		final SampleVO sampleVO1 = new SampleVO();
		sampleVO1.setName("test 목록조회1");
		sampleVO1.setUseYn("Y");
		sampleVO1.setDescription("test 목록조회 설명1");
		sampleVO1.setRegUser("test");
		egovSampleService.insertSample(sampleVO1);

		final SampleVO sampleVO2 = new SampleVO();
		sampleVO2.setName("test 목록조회2");
		sampleVO2.setUseYn("Y");
		sampleVO2.setDescription("test 목록조회 설명2");
		sampleVO2.setRegUser("test");
		egovSampleService.insertSample(sampleVO2);

		// when
		final SampleVO searchVO = new SampleVO();
		searchVO.setFirstIndex(0);
		searchVO.setRecordCountPerPage(10);

		final List<?> resultList = egovSampleService.selectSampleList(searchVO);
		final int totCnt = egovSampleService.selectSampleListTotCnt(searchVO);

		// then
		if (log.isDebugEnabled()) {
			log.debug("resultList.size={}", resultList.size());
			log.debug("totCnt={}", totCnt);
		}

//		assertNotNull(resultList, "목록 결과가 null이 아니어야 한다.");
//		assertTrue(resultList.size() >= 2, "등록한 건수 이상이어야 한다.");
//		assertTrue(totCnt >= 2, "전체 건수가 등록한 건수 이상이어야 한다.");

//		assertThat(resultList).as("목록 결과가 null이 아니어야 한다.").isNotNull();
//		assertThat(resultList.size()).as("등록한 건수 이상이어야 한다.").isGreaterThanOrEqualTo(2);
//		assertThat(totCnt).as("전체 건수가 등록한 건수 이상이어야 한다.").isGreaterThanOrEqualTo(2);

		assertThat(resultList).as("목록 결과가 null이 아니고 등록한 건수 이상이어야 한다.").isNotNull().hasSizeGreaterThanOrEqualTo(2);
		assertThat(totCnt).as("전체 건수가 등록한 건수 이상이어야 한다.").isGreaterThanOrEqualTo(2);
	}

}
