<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
  /**
  * @Class Name : egovSampleRegister.jsp
  * @Description : Sample Register 화면
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2009.02.01				최초 생성
  *  2025.07.01	유지보수	KRDS 디자인 적용
  *
  * author 실행환경 개발팀
  * since 2009.02.01
  *
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:set var="registerFlag" value="${empty sampleVO.id ? 'create' : 'modify'}"/>
    <title>Sample <c:if test="${registerFlag == 'create'}"><spring:message code="button.create" /></c:if>
                  <c:if test="${registerFlag == 'modify'}"><spring:message code="button.modify" /></c:if>
    </title>
    
    <!-- KRDS CSS -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/component/output.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframe.css'/>" />
    <script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/component/ui-script.js'/>" defer></script>
    <script type="text/javascript" src="<c:url value='/js/egovframework/common.js'/>" defer></script>
    
    <!--For Custom Validation-->
    <script type="text/javascript" src="<c:url value='/js/egovframework/EgovValidation.js'/>" defer></script>
    
    <script type="text/javascript" defer>
    $(document).ready(function() {
        // 초기 글자 수 설정
        $('#textCount').text($('#description').val().length);
        
        // 입력할 때마다 글자 수 갱신
        $('#description').on('input', function() {
            $('#textCount').text($(this).val().length);
        });
    });
    
    function sampleList() {
    	document.detailForm.action = "<c:url value='/egovSampleList.do'/>";
       	document.detailForm.method = 'get';
       	document.detailForm.submit();
    }

    function sampleAdd() {
        if (confirm('등록하시겠습니까?')) {
            let frm = document.detailForm;
            if (!validateSampleVO(frm)) {
                return;
            } else {
            	frm.action = "<c:url value='/addSample.do'/>";
                frm.submit();
            }
        }
    }

    function sampleUpdate() {
        if (confirm('수정하시겠습니까?')) {
            let frm = document.detailForm;
            if (!validateSampleVO(frm)) {
                return;
            } else {
            	frm.action = "<c:url value='/updateSample.do'/>";
                frm.submit();
            }
        }
    }

    function sampleDelete() {
        if (confirm('삭제하시겠습니까?')) {
        	document.detailForm.action = "<c:url value='/deleteSample.do'/>";
           	document.detailForm.submit();
        }
    }

    function sampleReset() {
        $('form').each(function() {
            this.reset();
        });
    }
    </script>
</head>

<body>
<div id="container" class="inner">

	<!-- Page Title -->
	<h2 class="heading-large">
		<c:if test="${registerFlag == 'create'}"><spring:message code="button.create" /></c:if>
		<c:if test="${registerFlag == 'modify'}"><spring:message code="button.modify" /></c:if>
	</h2>

	<form:form id="detailForm" name="detailForm" modelAttribute="sampleVO">
	<input type="hidden" id="searchCondition" name="searchCondition" value="${sampleVO.searchCondition}" />
	<input type="hidden" id="searchKeyword" name="searchKeyword" value="${sampleVO.searchKeyword}" />
	<input type="hidden" id="pageIndex" name="pageIndex" value="${sampleVO.pageIndex}" />

	<spring:message code="confirm.required.name" var="placeholderName"/>
	<spring:message code="confirm.required.description" var="placeholderDescription"/>
	<spring:message code="confirm.required.user" var="placeholderUser"/>

		<div class="conts-wrap">
			<div class="fieldset input-form">
                <c:if test="${not empty sampleVO.id}">
                <div class="form-group">
                    <div class="form-tit">
                        <label for="id"><spring:message code="title.sample.id" /></label>
                    </div>
                    <div class="form-conts">
                        <form:input path="id" maxlength="10" readonly="true" cssClass="krds-input"/>
                    </div>
                </div>
                </c:if>

                <div class="form-group">
                    <div class="form-tit">
                        <label for="name"><spring:message code="title.sample.name" /> <span class="required">*</span></label>
                    </div>
                    <div class="form-conts">
                        <form:input path="name" maxlength="60" cssClass="krds-input" placeholder="${placeholderName}" />
                        <form:errors path="name" cssClass="error-message" />
                    </div>
                </div>

                <div class="form-group">
                    <div class="form-tit">
                        <label for="useYn"><spring:message code="title.sample.useYn" /></label>
                    </div>
                    <div class="form-conts">
                        <form:select path="useYn" cssClass="krds-form-select">
                            <form:option value="Y"><spring:message code="input.yes" /></form:option>
                            <form:option value="N"><spring:message code="input.no" /></form:option>
                        </form:select>
                    </div>
                </div>

                <div class="form-group">
                    <div class="form-tit">
                        <label for="description"><spring:message code="title.sample.description" /> <span class="required">*</span></label>
                    </div>
                    <div class="form-conts">
                        <div class="textarea-wrap">
                            <form:textarea path="description" cssClass="krds-input" maxlength="200" placeholder="${placeholderDescription}" />
                            <p class="textarea-count">
                                <span id="textCount" class="text-primary">0</span>
                                <span class="text-num">/200</span>
                            </p>
                        </div>
                        <form:errors path="description" cssClass="error-message" />
                    </div>
                </div>

                <div class="form-group">
                    <div class="form-tit">
                        <label for="regUser"><spring:message code="title.sample.regUser" /> <span class="required">*</span></label>
                    </div>
                    <div class="form-conts">
                        <c:choose>
                            <c:when test="${not empty sampleVO.id}">
                                <form:input path="regUser" maxLength="60" readonly="true" cssClass="krds-input" placeholder="${confirm.required.user}"/>
                            </c:when>
                            <c:otherwise>
                                <form:input path="regUser" maxLength="60" cssClass="krds-input" placeholder="${placeholderUser}"/>
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="regUser" cssClass="error-message" />
                    </div>
                </div>

            </div>

			<!-- Action Buttons -->
			<div class="page-btn-wrap">
                <div class="btn-wrap">
	                <button type="button" class="krds-btn medium secondary" onclick="sampleList()"><spring:message code="button.list" /></button>
	                <button type="button" class="krds-btn medium tertiary" onclick="sampleReset()"><spring:message code="button.reset" /></button>
                </div>
				<div class="btn-wrap">
					<c:if test="${not empty sampleVO.id}">
						<button type="button" class="krds-btn medium" onclick="sampleUpdate()"><spring:message code="button.modify" /></button>
						<button type="button" class="krds-btn medium danger" onclick="sampleDelete()"><spring:message code="button.delete" /></button>
					</c:if>
					<c:if test="${empty sampleVO.id}">
						<button type="button" class="krds-btn medium" onclick="sampleAdd()"><spring:message code="button.create" /></button>
					</c:if>
				</div>
	        </div>
		</div>

	</form:form>

</div>

</body>
</html>
