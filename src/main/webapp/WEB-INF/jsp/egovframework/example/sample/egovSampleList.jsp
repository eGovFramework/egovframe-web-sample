<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
/**
* @Class Name : egovSampleList.jsp
* @Description : Sample List 화면
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><spring:message code="title.sample" /></title>

	<!-- KRDS CSS -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/component/output.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframe.css'/>" />
	<script type="text/javaScript" language="javascript" src="<c:url value='/js/jquery.min.js'/>" defer="defer"></script>
	<script type="text/javaScript" language="javascript" src="<c:url value='/js/egovframework/common.js'/>" defer="defer"></script>

	<script type="text/javaScript" language="javascript" defer="defer">
	<!--
		/* 글 수정 화면 function */
		function fn_egov_select(id) {
			document.listForm.id.value = id;
			document.listForm.action = "<c:url value='/updateSampleView.do'/>";
			document.listForm.submit();
		}
	
		/* 글 등록 화면 function */
		function fn_egov_addView() {
			document.listForm.action = "<c:url value='/addSampleView.do'/>";
			document.listForm.submit();
		}

		/* 글 목록 화면 function */
		function fn_egov_selectList() {
			if(document.listForm.searchKeyword.value == '') {
				alert("<spring:message code='search.error'/>");
				return false;
			}
			document.listForm.action = "<c:url value='/egovSampleList.do'/>";
			document.listForm.method = "get";
			document.listForm.submit();
		}

		/* pagination 페이지 링크 function */
		function fn_egov_link_page(pageNo) {
			document.listForm.pageIndex.value = pageNo;
			document.listForm.action = "<c:url value='/egovSampleList.do'/>";
			document.listForm.method = "get";
			document.listForm.submit();
		}
	//-->
	</script>
</head>

<body>
<div id="container" class="inner">

	<!-- 타이틀 -->
	<h2 class="heading-large"><spring:message code="list.sample" /></h2>
	<!-- // 타이틀 -->
	
	<div id="content_pop">
		<form:form modelAttribute="sampleVO" id="listForm" name="listForm" method="post">
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="pageIndex" name="pageIndex" value="1" />
	
			<!-- Search Form -->
			<div class="form-group">
				<div class="search-wrap">
					<div class="search-body">
						<div class="form-conts searchOption">
							<select id="searchCondition" name="searchCondition" class="krds-form-select medium" title="<spring:message code='search.choose'/>">
								<option value="1" <c:if test ="${not empty sampleVO.searchCondition and sampleVO.searchCondition eq 1}">selected="selected"</c:if>>Name</option>
								<option value="0" <c:if test ="${not empty sampleVO.searchCondition and sampleVO.searchCondition eq 0}">selected="selected"</c:if>>ID</option>
							</select>
						</div>
						<div class="form-conts btn-ico-wrap searchKeyword">
							<input type="text" id="searchKeyword" name="searchKeyword" value="${sampleVO.searchKeyword}" class="krds-input medium" placeholder="<spring:message code='search.keyword'/>">
							<button type="button" class="krds-btn medium icon" onclick="fn_egov_selectList()">
								<span class="sr-only"><spring:message code="button.search" /></span>
								<i class="svg-icon ico-sch"></i>
							</button>
						</div>
					</div>
					<div class="page-btn-wrap">
						<button type="button" class="krds-btn medium" onclick="fn_egov_addView()"><spring:message code="button.create" /></button>
					</div>
				</div>
			</div>
		</form:form>
	
		<!-- List -->
		<div class="krds-table-wrap">
			<table class="tbl col data">
				<colgroup>
					<col style="width: 10%;">
					<col style="width: 20%;">
					<col style="width: 20%;">
					<col style="width: 30%;">
					<col style="width: 10%;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="text-center">No</th>
						<th scope="col" class="text-center"><spring:message code="title.sample.id" /></th>
						<th scope="col" class="text-center"><spring:message code="title.sample.name" /></th>
						<th scope="col" class="text-center"><spring:message code="title.sample.description" /></th>
						<th scope="col" class="text-center"><spring:message code="title.sample.useYn" /></th>
						<th scope="col" class="text-center"><spring:message code="title.sample.regUser" /></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty resultList}">
							<c:forEach var="result" items="${resultList}" varStatus="status">
								<tr>
									<td class="text-center"><c:out value="${paginationInfo.totalRecordCount+1 - ((sampleVO.pageIndex-1) * sampleVO.pageSize + status.count)}"/></td>
									<td class="text-center"><a href="javascript:fn_egov_select('<c:out value="${result.id}"/>')"><c:out value="${result.id}" /></a></td>
									<td class="text-center"><c:out value="${result.name}"/></td>
									<td class="text-center"><c:out value="${result.description}"/></td>
									<td class="text-center">
										<c:choose>
											<c:when test="${result.useYn == 'Y'}"><spring:message code="input.yes" /></c:when>
											<c:when test="${result.useYn == 'N'}"><spring:message code="input.no" /></c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</td>
									<td class="text-center"><c:out value="${result.regUser}"/></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="text-center" colspan="6"><spring:message code="info.nodata.msg" /></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- // List -->

		<!-- Pagination -->
		<div id="paging" class="krds-pagination w-page">
			<ui:pagination paginationInfo="${paginationInfo}" type="krds" jsFunction="fn_egov_link_page" />
		</div>

	</div>

</div>

</body>
</html>
