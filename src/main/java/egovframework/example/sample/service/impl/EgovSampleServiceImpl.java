/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.sample.service.impl;

import java.util.List;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovSampleServiceImpl.java
 * @Description : Sample Business Implement Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Service("sampleService")
public class EgovSampleServiceImpl extends EgovAbstractServiceImpl implements EgovSampleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);
    private static final String NO_DATA_MESSAGE = "info.nodata.msg";

    @Autowired
    private SampleMapper sampleDAO;

    @Autowired
    private EgovIdGnrService egovIdGnrService;

	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    @Override
    public String insertSample(SampleVO vo) throws Exception {
        LOGGER.info("Inserting new sample: {}", vo);
        String id = generateId();
        vo.setId(id);
        sampleDAO.insertSample(vo);
        return id;
    }

    private String generateId() {
        try {
            return egovIdGnrService.getNextStringId();
        } catch (Exception e) {
            LOGGER.error("Error generating ID", e);
            throw new IdGenerationException("Failed to generate ID", e);
        }
    }

	/**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
    @Override
    public void updateSample(SampleVO vo) throws Exception {
        LOGGER.info("Updating sample: {}", vo);
        sampleDAO.updateSample(vo);
    }

	/**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
    @Override
    public void deleteSample(SampleVO vo) throws Exception {
        LOGGER.info("Deleting sample: {}", vo);
        sampleDAO.deleteSample(vo);
    }

	/**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    @Override
    public SampleVO selectSample(SampleVO vo) throws Exception {
        LOGGER.info("Selecting sample: {}", vo);
        SampleVO resultVO = sampleDAO.selectSample(vo);
        if (resultVO == null) {
            throw new NoDataFoundException(NO_DATA_MESSAGE);
        }
        return resultVO;
    }

	/**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    @Override
    public List<?> selectSampleList(SampleDefaultVO searchVO) throws Exception {
        LOGGER.info("Selecting sample list with search criteria: {}", searchVO);
        return sampleDAO.selectSampleList(searchVO);
    }

	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
    @Override
    public int selectSampleListTotCnt(SampleDefaultVO searchVO) {
        LOGGER.info("Counting total samples with search criteria: {}", searchVO);
        return sampleDAO.selectSampleListTotCnt(searchVO);
    }
}

class IdGenerationException extends RuntimeException {
    public IdGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}

class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String message) {
        super(message);
    }
}
