package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;

/**
 * [게시판][EgovSampleController.addSample] 등록 후 목록 상태 왕복 테스트
 *
 * <p>
 * updateSample.do, deleteSample.do 는 redirectAttributes 로 searchCondition,
 * searchKeyword, pageIndex 를 목록 화면에 돌려준다. addSample.do 도 egovSampleRegister.jsp
 * 가 같은 세 값을 히든 필드로 실어 보내므로 형제 핸들러와 동일하게 돌려줘야 검색조건과 페이지가
 * 유지된다.
 * </p>
 */
class EgovSampleControllerAddSampleRedirectTest {

	/** 아무것도 하지 않는 EgovSampleService 대역 */
	private static final EgovSampleService STUB_SERVICE = new EgovSampleService() {

		@Override
		public void insertSample(final SampleVO vo) {
			// 등록 성공으로 간주한다.
		}

		@Override
		public void updateSample(final SampleVO vo) {
			// 수정 성공으로 간주한다.
		}

		@Override
		public void deleteSample(final SampleVO vo) {
			// 삭제 성공으로 간주한다.
		}

		@Override
		public SampleVO selectSample(final SampleVO vo) {
			return vo;
		}

		@Override
		public List<?> selectSampleList(final SampleVO vo) {
			return Collections.emptyList();
		}

		@Override
		public int selectSampleListTotCnt(final SampleVO vo) {
			return 0;
		}

	};

	private static EgovSampleController controller() throws Exception {
		final EgovSampleController controller = new EgovSampleController();
		final Field field = EgovSampleController.class.getDeclaredField("sampleService");
		field.setAccessible(true);
		field.set(controller, STUB_SERVICE);
		return controller;
	}

	private static SampleVO sampleVO() {
		final SampleVO sampleVO = new SampleVO();
		sampleVO.setId("SAMPLE-00011");
		sampleVO.setName("test");
		sampleVO.setDescription("test");
		sampleVO.setUseYn("Y");
		sampleVO.setRegUser("test");
		sampleVO.setSearchCondition("0");
		sampleVO.setSearchKeyword("0011");
		sampleVO.setPageIndex(3);
		return sampleVO;
	}

	@Test
	void test() throws Exception {
		// given
		final EgovSampleController controller = controller();

		final SampleVO updateVO = sampleVO();
		final RedirectAttributesModelMap updateAttributes = new RedirectAttributesModelMap();

		final SampleVO addVO = sampleVO();
		final RedirectAttributesModelMap addAttributes = new RedirectAttributesModelMap();

		// when
		final String updateView = controller.updateSample(updateVO,
				new BeanPropertyBindingResult(updateVO, "sampleVO"), new ExtendedModelMap(), updateAttributes,
				new SimpleSessionStatus());

		final String addView = controller.addSample(addVO, new BeanPropertyBindingResult(addVO, "sampleVO"),
				new ExtendedModelMap(), addAttributes, new SimpleSessionStatus());

		// then
		assertEquals(updateView, addView, "등록도 수정과 같은 목록 화면으로 돌아가야 한다.");

		assertEquals(updateAttributes, addAttributes,
				"등록도 수정과 같은 목록 상태를 돌려줘야 한다. update=" + updateAttributes + ", add=" + addAttributes);
	}

}
