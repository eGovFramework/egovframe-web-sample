package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
 * [게시판][EgovSampleServiceImpl.deleteSample] ServiceImpl 단위 테스트
 *
 * @author 이백행
 * @since 2024-09-21
 *
 */

@ContextConfiguration(classes = { EgovSampleServiceImplTestDeleteSampleTest.class, EgovTestAbstractSpring.class })

@Configuration

@ImportResource({ "classpath*:egovframework/spring/context-idgen.xml", })

@ComponentScan(useDefaultFilters = false, basePackages = {
		"egovframework.example.sample.service.impl", }, includeFilters = {
				@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { EgovSampleServiceImpl.class,
						SampleMapper.class, }) })

@RequiredArgsConstructor
@Slf4j
class EgovSampleServiceImplTestDeleteSampleTest extends EgovTestAbstractSpring {

	/**
	 *
	 */
	@Autowired
	private EgovSampleService egovSampleService;

	@Test
	void test() {
		// given
		final SampleVO sampleVO = new SampleVO();

		sampleVO.setName("test 등록명");
		sampleVO.setUseYn("Y");
		sampleVO.setDescription("test 등록 설명");
		sampleVO.setRegUser("test");

		egovSampleService.insertSample(sampleVO);

		final String id = sampleVO.getId();

		if (log.isDebugEnabled()) {
			log.debug("id={}", id);
		}

		// when
		final SampleVO deleteVO = new SampleVO();
		deleteVO.setId(id);

		egovSampleService.deleteSample(deleteVO);

		// then
		final SampleVO selectVO = new SampleVO();
		selectVO.setId(id);

		assertThrows(Exception.class, () -> egovSampleService.selectSample(selectVO), "글을 삭제한다.");
	}

}
