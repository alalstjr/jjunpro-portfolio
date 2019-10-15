import React from "react"
import $script from 'scriptjs'
import { Link } from "react-router-dom"
import { func } from "prop-types";

/****************************************
    전역변수
****************************************/
let markers = [];
let newthat = null;
let openModal;

/****************************************
    카카오 지도를 생성합니다.
****************************************/
export const setKakaoMap = (that, latLng, apiKey, open) => {
    let x = latLng.x;
    let y = latLng.y;
    newthat = that;
    openModal = open;
    const kakao_url = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${apiKey}&libraries=services`;
    
    $script(kakao_url, () => {
        /*global kakao*/ 
         
        kakao.maps.load(function () {
            // v3가 모두 로드된 후, 이 콜백 함수가 실행됩니다.
            that.kakao = kakao;
            that.map = new kakao.maps.Map(that.appRef.current, {
                center: new that.kakao.maps.LatLng(x, y), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            });
        });
    });
}

/****************************************
    키워드 검색을 요청하는 함수입니다.
****************************************/
export const searchPlaces = () => {
    let places = new kakao.maps.services.Places();

    let keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }

    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    places.keywordSearch( keyword, searchPlacesCB); 
    return false;
} 

/****************************************
    장소검색이 완료됐을 때 호출되는 콜백함수 입니다.
****************************************/
const searchPlacesCB = (data, status, pagination) => {
    if (status === kakao.maps.services.Status.OK) {

        // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
        let infowindow = new kakao.maps.InfoWindow({
            zIndex:1
        });

        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        displayPlaces(data, infowindow);

        // 페이지 번호를 표출합니다
        // displayPagination(pagination);

    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

        alert('검색 결과가 존재하지 않습니다.');
        return;

    } else if (status === kakao.maps.services.Status.ERROR) {

        alert('검색 결과 중 오류가 발생했습니다.');
        return;

    }
}

/****************************************
    검색 결과 목록과 마커를 표출하는 함수입니다.
****************************************/
const displayPlaces = (places, infowindow) => {

    let listEl = document.getElementById('placesList'), 
    menuEl = document.getElementById('menu_wrap'),
    fragment = document.createDocumentFragment(), 
    bounds = new kakao.maps.LatLngBounds(), 
    listStr = '';
    
    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();
    
    for ( let i=0; i<places.length; i++ ) {

        // 마커를 생성하고 지도에 표시합니다
        let placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i), 
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function(marker, title) {
            // 인포윈도우
            kakao.maps.event.addListener(marker, 'mouseover', function() {
                infowindow.close();
                displayInfowindow(infowindow, marker, title);
            });
            kakao.maps.event.addListener(marker, 'mouseout', function() {
                infowindow.close();
            });

            // 커스텀 오버레이
            kakao.maps.event.addListener(marker, 'click', function() {
                infowindow.close();
                displayOverlay(placePosition);
            });

            itemEl.onmouseover =  function () {
                displayInfowindow(infowindow, marker, title);
            };

            itemEl.onmouseout =  function () {
                infowindow.close();
            };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    newthat.map.setBounds(bounds);
}

/****************************************
    검색결과 항목을 Element로 반환하는 함수입니다.
****************************************/
function getListItem(index, places) {

    let el = document.createElement('li'),
    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
                '<div class="info">' +
                '   <h5>' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
    } else {
        itemStr += '    <span>' +  places.address_name  + '</span>'; 
    }
                
    itemStr += '  <span class="tel">' + places.phone  + '</span>' +
                '</div>';           

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

/****************************************
    마커를 생성하고 지도 위에 마커를 표시하는 함수입니다.
****************************************/
function addMarker(position, idx, title) {
    let imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage 
        });

    marker.setMap(newthat.map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}

/****************************************
    지도 위에 표시되고 있는 마커를 모두 제거합니다.
****************************************/
function removeMarker() {
    for ( let i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }   
    markers = [];
}

/****************************************
    검색결과 목록 하단에 페이지번호를 표시는 함수입니다.
****************************************/
function displayPagination(pagination) {
    let paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i; 

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild (paginationEl.lastChild);
    }

    for (i=1; i<=pagination.last; i++) {
        let el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i===pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function(i) {
                return function() {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

/****************************************
    검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다.
    인포윈도우에 장소명을 표시합니다.
****************************************/
function displayInfowindow(infowindow, marker, title) {
    let content = `
        <div style="padding:5px;z-index:1;">
            <div>${title}</div>
        </div>
    `;
    infowindow.setContent(content);
    infowindow.open(newthat.map, marker);
}

/****************************************
    커스텀 오버레이를 생성합니다
****************************************/
const displayOverlay = (placePosition) => {

    let newDiv = document.createElement("div"); 
    let newContent = document.createTextNode("환영합니다!"); 
    newDiv.appendChild(newContent);
    newDiv.addEventListener('click', openModal());

    let content = `
        <div style="padding:5px;z-index:1;background:#fff;">
            <div>커스텀 오버레이</div>
            <div>평점 0점 | 리뷰 0개</div>
            <div class='review-write'>리뷰 작성하기</div>
        </div>
    `;

    let customOverlay = new kakao.maps.CustomOverlay({
        position: placePosition,
        content: newDiv,
        xAnchor: 0.3,
        yAnchor: 0.91
    });

    customOverlay.setMap(newthat.map);
    console.log(customOverlay.getContent());

    customOverlay.addListener('click', function(){
        console.log("도달");
    })
    // kakao.maps.event.addListener(marker, 'mouseover', function() {
    //     infowindow.close();
    //     displayInfowindow(infowindow, marker, title);
    // });
}

/****************************************
    검색결과 목록의 자식 Element를 제거하는 함수입니다.
****************************************/
function removeAllChildNods(el) {   
    while (el.hasChildNodes()) {
        el.removeChild (el.lastChild);
    }
}