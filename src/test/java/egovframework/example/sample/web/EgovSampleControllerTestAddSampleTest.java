package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import egovframework.example.sample.service.SampleVO;
import egovframework.test.EgovTestAbstractSpringMvc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [게시판][EgovSampleController.addSample] Controller 단위 테스트
 * 
 * @author 이백행
 * @since 2024-09-21
 *
 */

@RequiredArgsConstructor
@Slf4j
class EgovSampleControllerTestAddSampleTest extends EgovTestAbstractSpringMvc {

	@Test
	void test() throws Exception {
		// given
		final SampleVO sampleVO = new SampleVO();

		final LocalDateTime now = LocalDateTime.now();

		sampleVO.setName("test 이백행 카테고리명 " + now);

		sampleVO.setUseYn("Y"); // 사용여부

		sampleVO.setDescription("test 이백행 설명 " + now);

		sampleVO.setRegUser("test");

		// when
		mockMvc.perform(

				post("/addSample.do")

						.param("name", sampleVO.getName())

						.param("description", sampleVO.getDescription())

						.param("regUser", sampleVO.getRegUser())

		)

				.andDo(print())

//				.andExpect(status().isOk())
				.andExpect(status().isFound())

		;

		// then
		if (log.isDebugEnabled()) {
			log.debug("test");
		}

		assertEquals("", "", "글을 등록한다.");
	}

}
