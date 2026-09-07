package egovframework.example.sample.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.egovframe.rte.fdl.cmmn.exception.BaseRuntimeException;
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
 * [게시판][EgovSampleServiceImpl.updateSample] ServiceImpl 단위 테스트
 *
 * @author 이백행
 * @since 2024-09-21
 *
 */

@ContextConfiguration(classes = { EgovSampleServiceImplTestUpdateSampleTest.class, EgovTestAbstractSpring.class })

@Configuration

@ImportResource({ "classpath*:egovframework/spring/context-idgen.xml", })

@ComponentScan(useDefaultFilters = false, basePackages = {
		"egovframework.example.sample.service.impl", }, includeFilters = {
				@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { EgovSampleServiceImpl.class,
						SampleMapper.class, }) })

@RequiredArgsConstructor
@Slf4j
class EgovSampleServiceImplTestUpdateSampleTest extends EgovTestAbstractSpring {

	/**
	 *
	 */
	@Autowired
	private EgovSampleService egovSampleService;

	@Test
	void test() throws BaseRuntimeException, Exception {
		// given
		final SampleVO sampleVO = new SampleVO();

		sampleVO.setName("test 등록명");
		sampleVO.setUseYn("Y");
		sampleVO.setDescription("test 등록 설명");
		sampleVO.setRegUser("test");

		egovSampleService.insertSample(sampleVO);

		final String id = sampleVO.getId();

		// when
		final SampleVO updateVO = new SampleVO();
		updateVO.setId(id);
		updateVO.setName("test 수정명");
		updateVO.setUseYn("N");
		updateVO.setDescription("test 수정 설명");
		updateVO.setRegUser("test");

		egovSampleService.updateSample(updateVO);

		// then
		final SampleVO resultVO = new SampleVO();
		resultVO.setId(id);

		final SampleVO resultSampleVO = egovSampleService.selectSample(resultVO);

		if (log.isDebugEnabled()) {
			log.debug("id={}", id);
			log.debug("resultSampleVO={}", resultSampleVO);
			log.debug("getName={}", resultSampleVO.getName());
		}

		assertEquals("test 수정명", resultSampleVO.getName(), "글을 수정한다.");
		assertEquals("N", resultSampleVO.getUseYn(), "사용여부를 수정한다.");
	}

}
